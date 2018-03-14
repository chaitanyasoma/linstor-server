package com.linbit.linstor.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import com.linbit.linstor.api.ApiConsts;
import com.linbit.linstor.dbdrivers.DatabaseDriverInfo;
import com.linbit.linstor.dbdrivers.DerbyDriver;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import picocli.CommandLine;

public class LinstorConfig
{
    private static CommandLine commandLine;

    private static final String dbUser = "linstor";
    private static final String dbPassword = "linstor";

    @CommandLine.Command(name = "linstor-config", subcommands = {
        CmdCreateDb.class,
        CmdSetPlainPort.class,
        CmdSetPlainListen.class,
        CmdCreateDbXMLConfig.class
    })
    private static class LinstorConfigCmd implements Callable
    {
        @Override
        public Object call() throws Exception
        {
            commandLine.usage(System.err);
            return null;
        }
    }

    @CommandLine.Command(
        name = "create-db-file",
        description = "Write a database xml configuration file to standard out."
    )
    private static class CmdCreateDbXMLConfig implements Callable
    {
        @CommandLine.Option(names = {"--dbtype"}, description = "Specify the database type. ['h2','hsqldb','derby']")
        private String dbtype = "h2";

        @CommandLine.Parameters(description = "Path to the database")
        private File dbpath;

        @Override
        public Object call() throws Exception
        {
            OutputStream os = System.out;

            final String dbCfg = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
                + "<!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">\n"
                + "<properties>\n"
                + "  <comment>LinStor database configuration</comment>\n"
	            + "  <entry key=\"user\">%s</entry>\n"
                + "  <entry key=\"password\">%s</entry>\n"
                + "  <entry key=\"connection-url\">%s</entry>\n"
                + "</properties>\n";

            os.write(String.format(
                dbCfg,
                dbUser,
                dbPassword,
                DatabaseDriverInfo.CreateDriverInfo(dbtype).jdbcUrl(dbpath.getAbsolutePath())).getBytes()
            );
            return null;
        }
    }

    @CommandLine.Command(name = "create-db", description = "Creates a database.")
    private static class CmdCreateDb implements Callable
    {
        static private List<String> supportedDbs = Arrays.asList("h2", "hsqldb", "derby");

        @CommandLine.Option(names = {"--recreate"}, description = "Delete an already existing database")
        private boolean recreate = false;

        @CommandLine.Option(names = {"--dbtype"}, description = "Specify the database type. ['h2','hsqldb','derby']")
        private String dbtype = "h2";

        @CommandLine.Option(names = {"--initsql"}, description = "Specifiy init sql script")
        private File initSQL;

        @CommandLine.Parameters(description = "Path to the database")
        private Path dbpath;

        @Override
        public Object call() throws Exception
        {
            if (supportedDbs.contains(dbtype))
            {
                InputStream is = RecreateDb.class.getResourceAsStream("/resource/drbd-init-derby.sql");
                if (initSQL != null)
                {
                    is = new FileInputStream(initSQL);
                }

                if (is != null)
                {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(is)))
                    {
                        DatabaseDriverInfo dbdriver = DatabaseDriverInfo.CreateDriverInfo(dbtype);

                        // if recreate delete old database
                        if (recreate && Files.exists(dbpath))
                        {
                            Path rootPath = Paths.get(dbpath.getParent().toString(), dbpath.getFileName().toString());
                            Files.list(dbpath.getParent())
                                .filter(file -> file.startsWith(rootPath))
                                .forEach(p ->
                                {
                                    try {
                                        Files.walk(p, FileVisitOption.FOLLOW_LINKS)
                                            .sorted(Comparator.reverseOrder())
                                            .map(Path::toFile)
                                            .forEach(File::delete);
                                    }
                                    catch(IOException io) {
                                        System.err.println(io.toString());
                                    }
                                });
                        }

                        try (PoolingDataSource<PoolableConnection> dataSource =
                                 initConnectionProvider(
                                     dbdriver.jdbcUrl(dbpath.toAbsolutePath().toString()), "linstor", "linstor");
                             Connection con = dataSource.getConnection())
                        {
                            DerbyDriver.executeStatement(con, dbdriver.isolationStatement());
                            DerbyDriver.runSql(
                                con,
                                dbdriver.prepareInit(br.lines().collect(Collectors.joining("\n")))
                            );
                        }

                        System.out.println("Database created at " + dbpath);
                    }
                }
                else
                {
                    System.err.println("No suitable db setup script could be found.");
                }
            }
            else
            {
                System.err.println(
                    String.format(
                        "Database type '%s' not supported. Use one of: '%s'",
                        dbtype,
                        String.join("', '", supportedDbs))
                );
            }
            return null;
        }
    }

    @CommandLine.Command(name = "set-plain-port", description = "Set the controller plain tcp port.")
    private static class CmdSetPlainPort implements Callable
    {
        @CommandLine.Parameters(index = "0", description = "Database configuration file.")
        private File dbCfgFile = new File("./database.cfg");

        @CommandLine.Parameters(index = "1", description = "New Port number.")
        private int controllerPort = ApiConsts.DFLT_CTRL_PORT_PLAIN;

        @Override
        public Object call() throws Exception
        {
            try (PoolingDataSource<PoolableConnection> dataSource =
                     initConnectionProviderFromCfg(dbCfgFile);
                 Connection con = dataSource.getConnection())
            {
                final String stmt = "UPDATE PROPS_CONTAINERS SET PROP_VALUE='%d' "
                    + "WHERE PROPS_INSTANCE='CTRLCFG' AND PROP_KEY='netcom/PlainConnector/port'";
                DerbyDriver.executeStatement(con, String.format(stmt, controllerPort));
                con.commit();
            }
            System.out.println("Controller plain port set to " + controllerPort);
            return null;
        }
    }

    @CommandLine.Command(name = "set-plain-listen", description = "Set the controller plain listen/bind address.")
    private static class CmdSetPlainListen implements Callable
    {
        @CommandLine.Parameters(index = "0", description = "Database configuration file.")
        private File dbCfgFile = new File("./database.cfg");

        @CommandLine.Parameters(index = "1", description = "new Port number.")
        private String listenAddress = "::0";

        @Override
        public Object call() throws Exception
        {
            try (PoolingDataSource<PoolableConnection> dataSource =
                     initConnectionProviderFromCfg(dbCfgFile);
                 Connection con = dataSource.getConnection())
            {
                final String stmt = "UPDATE PROPS_CONTAINERS SET PROP_VALUE='%s' "
                    + "WHERE PROPS_INSTANCE='CTRLCFG' AND PROP_KEY='netcom/PlainConnector/bindaddress'";
                DerbyDriver.executeStatement(con, String.format(stmt, listenAddress));
                con.commit();
            }
            System.out.println("Controller plain listen address set to " + listenAddress);
            return null;
        }
    }

    public static void main(String[] args)
    {
        commandLine = new CommandLine(new LinstorConfigCmd());

        commandLine.parseWithHandler(new CommandLine.RunLast(), System.err, args);
    }

    private static PoolingDataSource<PoolableConnection> initConnectionProvider(
        final String connUrl,
        final String user,
        final String password)
    {
        Properties dbProps = new Properties();
        dbProps.setProperty("user", user);
        dbProps.setProperty("password", password);
        ConnectionFactory connFactory = new DriverManagerConnectionFactory(
            connUrl,
            dbProps
        );
        PoolableConnectionFactory poolConnFactory = new PoolableConnectionFactory(connFactory, null);

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setBlockWhenExhausted(true);
        poolConfig.setFairness(true);

        GenericObjectPool<PoolableConnection> connPool = new GenericObjectPool<>(poolConnFactory, poolConfig);

        poolConnFactory.setPool(connPool);

        return new PoolingDataSource<>(connPool);
    }

    private static PoolingDataSource<PoolableConnection> initConnectionProviderFromCfg(final File cfg)
        throws IOException
    {
        Properties dbProps = new Properties();
        try (FileInputStream fis = new FileInputStream(cfg))
        {
            dbProps.loadFromXML(fis);
        }

        return initConnectionProvider(
            dbProps.getProperty("connection-url"),
            dbProps.getProperty("user"),
            dbProps.getProperty("password")
        );
    }
}
