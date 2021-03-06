package com.linbit.linstor.dbdrivers;

public class PostgresqlDatabaseInfo implements DatabaseDriverInfo
{
    public PostgresqlDatabaseInfo()
    {
        DatabaseDriverInfo.loadDriver("org.postgresql.Driver");
    }

    @Override
    public String jdbcUrl(String dbPath)
    {
        return "jdbc:postgresql:" + dbPath;
    }

    @Override
    public String jdbcInMemoryUrl()
    {
        return null;
    }

    @Override
    public String isolationStatement()
    {
        return "SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;";
    }

    @Override
    public String prepareInit(String initSQL)
    {
        return initSQL.replace("SET SCHEMA LINSTOR", "SET SEARCH_PATH TO LINSTOR");
    }

}
