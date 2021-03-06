package com.linbit.linstor;

import com.linbit.InvalidNameException;
import com.linbit.ValueOutOfRangeException;
import com.linbit.linstor.Volume.VlmFlags;
import com.linbit.linstor.annotation.SystemContext;
import com.linbit.linstor.dbdrivers.DerbyDriver;
import com.linbit.linstor.dbdrivers.derby.DerbyConstants;
import com.linbit.linstor.dbdrivers.interfaces.VolumeDataDatabaseDriver;
import com.linbit.linstor.logging.ErrorReporter;
import com.linbit.linstor.propscon.PropsContainerFactory;
import com.linbit.linstor.security.AccessContext;
import com.linbit.linstor.security.AccessDeniedException;
import com.linbit.linstor.stateflags.FlagsHelper;
import com.linbit.linstor.stateflags.StateFlags;
import com.linbit.linstor.stateflags.StateFlagsPersistence;
import com.linbit.linstor.transaction.TransactionMgr;
import com.linbit.linstor.transaction.TransactionObjectFactory;
import com.linbit.utils.StringUtils;
import com.linbit.utils.UuidUtils;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Singleton
public class VolumeDataDerbyDriver implements VolumeDataDatabaseDriver
{
    private static final String TBL_VOL = DerbyConstants.TBL_VOLUMES;

    private static final String VOL_UUID = DerbyConstants.UUID;
    private static final String VOL_NODE_NAME = DerbyConstants.NODE_NAME;
    private static final String VOL_RES_NAME = DerbyConstants.RESOURCE_NAME;
    private static final String VOL_ID = DerbyConstants.VLM_NR;
    private static final String VOL_STOR_POOL = DerbyConstants.STOR_POOL_NAME;
    private static final String VOL_BLOCK_DEVICE = DerbyConstants.BLOCK_DEVICE_PATH;
    private static final String VOL_META_DISK = DerbyConstants.META_DISK_PATH;
    private static final String VOL_FLAGS = DerbyConstants.VLM_FLAGS;

    private static final String SELECT_BY_RES =
        " SELECT " + VOL_UUID + ", " + VOL_NODE_NAME + ", " + VOL_RES_NAME + ", " +
                     VOL_ID + ", " + VOL_STOR_POOL + ", " + VOL_BLOCK_DEVICE + ", " +
                     VOL_META_DISK + ", " + VOL_FLAGS +
        " FROM " + TBL_VOL +
        " WHERE " + VOL_NODE_NAME + " = ? AND " +
                    VOL_RES_NAME  + " = ?";
    private static final String SELECT =  SELECT_BY_RES +
        " AND " +   VOL_ID +        " = ?";
    private static final String SELECT_BY_STOR_POOL =
        " SELECT " + VOL_UUID + ", " + VOL_NODE_NAME + ", " + VOL_RES_NAME + ", " +
                     VOL_ID + ", " + VOL_STOR_POOL + ", " + VOL_BLOCK_DEVICE + ", " +
                     VOL_META_DISK + ", " + VOL_FLAGS +
        " FROM " + TBL_VOL +
        " WHERE " + VOL_NODE_NAME + " = ? AND " +
                    VOL_STOR_POOL + " = ?";
    private static final String INSERT =
        " INSERT INTO " + TBL_VOL +
        " (" +
            VOL_UUID + ", " + VOL_NODE_NAME + ", " + VOL_RES_NAME + ", " +
            VOL_ID + ", " + VOL_STOR_POOL + ", " + VOL_BLOCK_DEVICE + ", " +
            VOL_META_DISK + ", " + VOL_FLAGS +
        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_FLAGS =
        " UPDATE " + TBL_VOL +
        " SET " + VOL_FLAGS + " = ? " +
        " WHERE " + VOL_NODE_NAME + " = ? AND " +
                    VOL_RES_NAME  + " = ? AND " +
                    VOL_ID        + " = ?";
    private static final String DELETE =
        " DELETE FROM " + TBL_VOL +
        " WHERE " + VOL_NODE_NAME + " = ? AND " +
                    VOL_RES_NAME  + " = ? AND " +
                    VOL_ID        + " = ?";

    private final AccessContext dbCtx;
    private final ErrorReporter errorReporter;

    private final StateFlagsPersistence<VolumeData> flagPersistenceDriver;

    private final Provider<NodeDataDerbyDriver> nodeDriverProvider;
    private final Provider<ResourceDataDerbyDriver> resourceDriverProvider;
    private final Provider<VolumeConnectionDataDerbyDriver> volumeConnectionDriverProvider;
    private final Provider<VolumeDefinitionDataDerbyDriver> volDfnDriverProvider;
    private final Provider<StorPoolDefinitionDataDerbyDriver> storPoolDfnDriverProvider;
    private final Provider<StorPoolDataDerbyDriver> storPoolDriverProvider;
    private final PropsContainerFactory propsContainerFactory;
    private final TransactionObjectFactory transObjFactory;
    private final Provider<TransactionMgr> transMgrProvider;

    private HashMap<VolPrimaryKey, VolumeData> volCache;
    private boolean cacheCleared = false;

    @Inject
    public VolumeDataDerbyDriver(
        @SystemContext AccessContext privCtx,
        ErrorReporter errorReporterRef,
        Provider<NodeDataDerbyDriver> nodeDriverProviderRef,
        Provider<ResourceDataDerbyDriver> resourceDriverProviderRef,
        Provider<VolumeConnectionDataDerbyDriver> volumeConnectionDriverProviderRef,
        Provider<VolumeDefinitionDataDerbyDriver> volDfnDriverProviderRef,
        Provider<StorPoolDefinitionDataDerbyDriver> storPoolDfnDriverProviderRef,
        Provider<StorPoolDataDerbyDriver> storPoolDriverProviderRef,
        PropsContainerFactory propsContainerFactoryRef,
        TransactionObjectFactory transObjFactoryRef,
        Provider<TransactionMgr> transMgrProviderRef
    )
    {
        dbCtx = privCtx;
        errorReporter = errorReporterRef;
        nodeDriverProvider = nodeDriverProviderRef;
        resourceDriverProvider = resourceDriverProviderRef;
        volumeConnectionDriverProvider = volumeConnectionDriverProviderRef;
        volDfnDriverProvider = volDfnDriverProviderRef;
        storPoolDfnDriverProvider = storPoolDfnDriverProviderRef;
        storPoolDriverProvider = storPoolDriverProviderRef;
        propsContainerFactory = propsContainerFactoryRef;
        transObjFactory = transObjFactoryRef;
        transMgrProvider = transMgrProviderRef;

        flagPersistenceDriver = new VolFlagsPersistence();

        volCache = new HashMap<>();
    }

    @Override
    @SuppressWarnings("checkstyle:magicnumber")
    public VolumeData load(
        Resource resource,
        VolumeDefinition volumeDefintion,
        boolean logWarnIfNotExists
    )
        throws SQLException
    {
        VolumeData ret = null;
        try (PreparedStatement stmt = getConnection().prepareStatement(SELECT))
        {
            errorReporter.logTrace(
                "Loading Volume %s",
                getId(resource)
            );

            stmt.setString(1, resource.getAssignedNode().getName().value);
            stmt.setString(2, resource.getDefinition().getName().value);
            stmt.setInt(3, volumeDefintion.getVolumeNumber().value);

            try (ResultSet resultSet = stmt.executeQuery())
            {
                List<VolumeData> volList = load(
                    dbCtx,
                    resource,
                    resultSet
                );

                if (!volList.isEmpty())
                {
                    ret = volList.get(0);
                    errorReporter.logTrace("Volume loaded from DB %s", getId(ret));
                }
                else
                if (logWarnIfNotExists)
                {
                    errorReporter.logWarning(
                        "Volume not found in DB %s",
                        getId(
                            resource,
                            volumeDefintion
                        )
                    );
                }
            }
        }
        return ret;
    }

    public List<VolumeData> loadAllVolumesByResource(Resource resRef) throws SQLException
    {
        List<VolumeData> ret;
        try (PreparedStatement stmt = getConnection().prepareStatement(SELECT_BY_RES))
        {
            errorReporter.logTrace(
                "Loading all Volumes by resource %s",
                getId(resRef)
            );
            stmt.setString(1, resRef.getAssignedNode().getName().value);
            stmt.setString(2, resRef.getDefinition().getName().value);
            try (ResultSet resultSet = stmt.executeQuery())
            {
                ret = load(dbCtx, resRef, resultSet);
            }
        }
        errorReporter.logTrace("%d volumes loaded for resource %s", ret.size(), getId(resRef));
        return ret;
    }

    public List<VolumeData> getVolumesByStorPool(StorPoolData storPoolData)
        throws SQLException
    {
        List<VolumeData> ret;
        try (PreparedStatement stmt = getConnection().prepareStatement(SELECT_BY_STOR_POOL))
        {
            errorReporter.logTrace(
                "Loading all Volumes by StorPool %s",
                getId(storPoolData)
            );
            stmt.setString(1, storPoolData.getNode().getName().value);
            stmt.setString(2, getStorPoolDfn(storPoolData).getName().value);
            try (ResultSet resultSet = stmt.executeQuery())
            {
                ret = load(dbCtx, null, resultSet);
            }
        }
        errorReporter.logTrace("%d volumes loaded for StorPool %s", ret.size(), getId(storPoolData));
        return ret;
    }

    private List<VolumeData> load(AccessContext accCtx, Resource resRef, ResultSet resultSet)
        throws SQLException
    {
        List<VolumeData> volList = new ArrayList<>();
        while (resultSet.next())
        {
            VolumeDefinition volDfn = null;
            VolumeNumber volNr;
            StorPoolName storPoolName;

            Resource res;
            if (resRef != null)
            {
                res = resRef;
            }
            else
            {
                NodeName nodeName = null;
                ResourceName resName;
                try
                {
                    nodeName = new NodeName(resultSet.getString(VOL_NODE_NAME));
                    resName = new ResourceName(resultSet.getString(VOL_RES_NAME));
                }
                catch (InvalidNameException invalidNameExc)
                {
                    if (nodeName == null)
                    {
                        throw new LinStorSqlRuntimeException(
                            String.format(
                                "A NodeName of a stored Volume in table %s could not be restored. " +
                                    "(invalid NodeName=%s, ResName=%s, VolumeNumber=%d)",
                                TBL_VOL,
                                resultSet.getString(VOL_NODE_NAME),
                                resultSet.getString(VOL_RES_NAME),
                                resultSet.getInt(VOL_ID)
                            ),
                            invalidNameExc
                        );
                    }
                    else
                    {
                        throw new LinStorSqlRuntimeException(
                            String.format(
                                "A ResourceName of a stored Volume in table %s could not be restored. " +
                                    "(NodeName=%s, invalid ResName=%s, VolumeNumber=%d)",
                                TBL_VOL,
                                resultSet.getString(VOL_NODE_NAME),
                                resultSet.getString(VOL_RES_NAME),
                                resultSet.getInt(VOL_ID)
                            ),
                            invalidNameExc
                        );
                    }
                }
                Node node = nodeDriverProvider.get().load(nodeName, true);
                res = resourceDriverProvider.get().load(node, resName, true);
            }

            try
            {
                volNr = new VolumeNumber(resultSet.getInt(VOL_ID));
                storPoolName = new StorPoolName(resultSet.getString(VOL_STOR_POOL));
            }
            catch (ValueOutOfRangeException valueOutOfRangeExc)
            {
                throw new LinStorSqlRuntimeException(
                    String.format(
                        "A VolumeNumber of a stored Volume in table %s could not be restored. " +
                            "(NodeName=%s, ResName=%s, invalid VolumeNumber=%d)",
                        TBL_VOL,
                        res.getAssignedNode().getName().value,
                        res.getDefinition().getName().value,
                        resultSet.getInt(VOL_ID)
                    ),
                    valueOutOfRangeExc
                );
            }
            catch (InvalidNameException invalidNameExc)
            {
                throw new LinStorSqlRuntimeException(
                    String.format(
                        "A StorPoolName of a stored Volume in table %s could not be restored. " +
                            "(NodeName=%s, ResName=%s, VolumeNumber=%d, invalid StorPoolName=%s)",
                        TBL_VOL,
                        res.getAssignedNode().getName().value,
                        res.getDefinition().getName().value,
                        resultSet.getInt(VOL_ID),
                        resultSet.getString(VOL_STOR_POOL)
                    ),
                    invalidNameExc
                );
            }
            volDfn = volDfnDriverProvider.get().load(
                res.getDefinition(),
                volNr,
                true
            );

            StorPoolDefinitionData storPoolDfn = storPoolDfnDriverProvider.get().load(
                storPoolName,
                true
            );

            StorPoolData storPool = storPoolDriverProvider.get().load(
                res.getAssignedNode(),
                storPoolDfn,
                true
            );

            VolumeData volData = cacheGet(res.getAssignedNode(), res.getDefinition(), volNr);
            VolPrimaryKey primaryKey = null;
            if (volData == null && !cacheCleared)
            {
                primaryKey = new VolPrimaryKey(res, volDfn);
                volData = volCache.get(primaryKey);
            }
            if (volData == null)
            {
                try
                {
                    volData = new VolumeData(
                        java.util.UUID.fromString(resultSet.getString(VOL_UUID)),
                        accCtx,
                        res,
                        volDfn,
                        storPool,
                        resultSet.getString(VOL_BLOCK_DEVICE),
                        resultSet.getString(VOL_META_DISK),
                        resultSet.getLong(VOL_FLAGS),
                        this,
                        propsContainerFactory,
                        transObjFactory,
                        transMgrProvider
                    );
                    errorReporter.logTrace("Volume created %s", getId(volData));
                    if (!cacheCleared)
                    {
                        volCache.put(primaryKey, volData);
                    }

                    // restore flags
                    StateFlags<VlmFlags> flags = volData.getFlags();
                    long lFlags = resultSet.getLong(VOL_FLAGS);
                    for (VlmFlags flag : VlmFlags.values())
                    {
                        if ((lFlags & flag.flagValue) == flag.flagValue)
                        {
                            flags.enableFlags(accCtx, flag);
                        }
                    }
                    errorReporter.logTrace(
                        "Volume's flags restored to %d %s",
                        lFlags,
                        getId(volData)
                    );

                    // restore volCon
                    List<VolumeConnectionData> volConDfnList =
                        volumeConnectionDriverProvider.get().loadAllByVolume(volData);
                    for (VolumeConnectionData volConDfn : volConDfnList)
                    {
                        volData.setVolumeConnection(dbCtx, volConDfn);
                    }
                    errorReporter.logTrace(
                        "%d VolumeConnections restored %s",
                        volConDfnList.size(),
                        getId(volData)
                    );

                    errorReporter.logTrace("Volume restored %s", getId(volData));
                }
                catch (AccessDeniedException accessDeniedExc)
                {
                    DerbyDriver.handleAccessDeniedException(accessDeniedExc);
                }
            }
            else
            {
                errorReporter.logTrace("Volume loaded from cache %s", getId(volData));
            }
            volList.add(volData);
        }
        // resultSet should be closed by caller of this method
        return volList;
    }

    @Override
    @SuppressWarnings("checkstyle:magicnumber")
    public void create(VolumeData vol) throws SQLException
    {
        try (PreparedStatement stmt = getConnection().prepareStatement(INSERT))
        {
            errorReporter.logTrace("Creating Volume %s", getId(vol));

            stmt.setString(1, vol.getUuid().toString());
            stmt.setString(2, vol.getResource().getAssignedNode().getName().value);
            stmt.setString(3, vol.getResourceDefinition().getName().value);
            stmt.setInt(4, vol.getVolumeDefinition().getVolumeNumber().value);
            stmt.setString(5, vol.getStorPool(dbCtx).getName().value);
            stmt.setString(6, vol.getBlockDevicePath(dbCtx));
            stmt.setString(7, vol.getMetaDiskPath(dbCtx));
            stmt.setLong(8, vol.getFlags().getFlagsBits(dbCtx));
            stmt.executeUpdate();

            errorReporter.logTrace("Volume created %s", getId(vol));
        }
        catch (AccessDeniedException accessDeniedExc)
        {
            DerbyDriver.handleAccessDeniedException(accessDeniedExc);
        }
    }

    @Override
    @SuppressWarnings("checkstyle:magicnumber")
    public void delete(VolumeData volume) throws SQLException
    {
        try (PreparedStatement stmt = getConnection().prepareStatement(DELETE))
        {
            errorReporter.logTrace("Deleting Volume %s", getId(volume));

            stmt.setString(1, volume.getResource().getAssignedNode().getName().value);
            stmt.setString(2, volume.getResource().getDefinition().getName().value);
            stmt.setInt(3, volume.getVolumeDefinition().getVolumeNumber().value);
            stmt.executeUpdate();

            errorReporter.logTrace("Volume deleted %s", getId(volume));
        }
    }

    @Override
    public StateFlagsPersistence<VolumeData> getStateFlagsPersistence()
    {
        return flagPersistenceDriver;
    }

    private VolumeData cacheGet(Node node, ResourceDefinition resDfn, VolumeNumber volNr)
    {
        VolumeData ret = null;
        try
        {
            if (node != null)
            {
                Resource res = node.getResource(dbCtx, resDfn.getName());
                if (res != null)
                {
                    ret = (VolumeData) res.getVolume(volNr);
                }
            }
        }
        catch (AccessDeniedException accessDeniedExc)
        {
            DerbyDriver.handleAccessDeniedException(accessDeniedExc);
        }
        return ret;
    }

    private String getId(Resource resource, VolumeDefinition volumeDefintion)
    {
        return getVolId(
            resource.getAssignedNode().getName().displayValue,
            resource.getDefinition().getName().displayValue,
            volumeDefintion.getVolumeNumber()
        );
    }

    private String getId(VolumeData volume)
    {
        return getVolId(
            volume.getResource().getAssignedNode().getName().displayValue,
            volume.getResource().getDefinition().getName().displayValue,
            volume.getVolumeDefinition().getVolumeNumber()
        );
    }

    private String getVolId(String nodeName, String resName, VolumeNumber volNum)
    {
        return "(NodeName=" + nodeName + " ResName=" + resName + " VolNum=" + volNum.value + ")";
    }

    private String getId(Resource resRef)
    {
        return getResId(
            resRef.getAssignedNode().getName().displayValue,
            resRef.getDefinition().getName().displayValue
        );
    }

    private String getResId(String nodeName, String resName)
    {
        return "(NodeName=" + nodeName + " ResName=" + resName + ")";
    }

    private String getId(StorPoolData storPool)
    {
        return getStorPoolId(
            getStorPoolDfn(storPool).getName().displayValue
        );
    }

    private String getStorPoolId(String name)
    {
        return "(StorPoolName=" + name + ")";
    }

    private StorPoolDefinitionData getStorPoolDfn(StorPoolData storPool)
    {
        StorPoolDefinitionData ret = null;
        try
        {
            ret = (StorPoolDefinitionData) storPool.getDefinition(dbCtx);
        }
        catch (AccessDeniedException accDeniedExc)
        {
            DerbyDriver.handleAccessDeniedException(accDeniedExc);
        }
        return ret;
    }

    public void clearCache()
    {
        cacheCleared = true;
        volCache.clear();
    }

    private Connection getConnection()
    {
        return transMgrProvider.get().getConnection();
    }

    private class VolFlagsPersistence implements StateFlagsPersistence<VolumeData>
    {
        @Override
        @SuppressWarnings("checkstyle:magicnumber")
        public void persist(VolumeData volume, long flags)
            throws SQLException
        {
            try (PreparedStatement stmt = getConnection().prepareStatement(UPDATE_FLAGS))
            {
                String fromFlags = StringUtils.join(
                    FlagsHelper.toStringList(
                        VlmFlags.class,
                        volume.getFlags().getFlagsBits(dbCtx)
                    ),
                    ", "
                );
                String toFlags = StringUtils.join(
                    FlagsHelper.toStringList(
                        VlmFlags.class,
                        flags
                    ),
                    ", "
                );

                errorReporter.logTrace(
                    "Updating Volume's flags from [%s] to [%s] %s",
                    fromFlags,
                    toFlags,
                    getId(volume)
                );

                stmt.setLong(1, flags);
                stmt.setString(2, volume.getResource().getAssignedNode().getName().value);
                stmt.setString(3, volume.getResource().getDefinition().getName().value);
                stmt.setInt(4, volume.getVolumeDefinition().getVolumeNumber().value);
                stmt.executeUpdate();

                errorReporter.logTrace(
                    "Volume's flags updated from [%s] to [%s] %s",
                    fromFlags,
                    toFlags,
                    getId(volume)
                );
            }
            catch (AccessDeniedException accessDeniedExc)
            {
                DerbyDriver.handleAccessDeniedException(accessDeniedExc);
            }
        }
    }

    public static class VolPrimaryKey
    {
        private Resource resource;
        private VolumeDefinition volDfn;

        public VolPrimaryKey(Resource rscRef, VolumeDefinition volDfnRef)
        {
            resource = rscRef;
            volDfn = volDfnRef;
        }

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((resource == null) ? 0 : resource.hashCode());
            result = prime * result + ((volDfn == null) ? 0 : volDfn.hashCode());
            return result;
        }

        @Override
        // Single exit point exception: Automatically generated code
        @SuppressWarnings("DescendantToken")
        public boolean equals(Object obj)
        {
            if (this == obj)
            {
                return true;
            }
            if (obj == null)
            {
                return false;
            }
            if (getClass() != obj.getClass())
            {
                return false;
            }
            VolPrimaryKey other = (VolPrimaryKey) obj;
            if (resource == null)
            {
                if (other.resource != null)
                {
                    return false;
                }
            }
            else
            {
                if (!resource.equals(other.resource))
                {
                    return false;
                }
            }
            if (volDfn == null)
            {
                if (other.volDfn != null)
                {
                    return false;
                }
            }
            else
            {
                if (!volDfn.equals(other.volDfn))
                {
                    return false;
                }
            }
            return true;
        }
    }
}
