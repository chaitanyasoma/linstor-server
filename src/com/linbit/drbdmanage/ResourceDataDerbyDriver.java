package com.linbit.drbdmanage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.linbit.ImplementationError;
import com.linbit.InvalidNameException;
import com.linbit.TransactionMgr;
import com.linbit.ValueOutOfRangeException;
import com.linbit.drbdmanage.core.DrbdManage;
import com.linbit.drbdmanage.dbdrivers.DerbyDriver;
import com.linbit.drbdmanage.dbdrivers.derby.DerbyConstants;
import com.linbit.drbdmanage.dbdrivers.interfaces.ResourceDataDatabaseDriver;
import com.linbit.drbdmanage.dbdrivers.interfaces.ResourceDefinitionDataDatabaseDriver;
import com.linbit.drbdmanage.logging.ErrorReporter;
import com.linbit.drbdmanage.propscon.SerialGenerator;
import com.linbit.drbdmanage.security.AccessContext;
import com.linbit.drbdmanage.security.AccessDeniedException;
import com.linbit.drbdmanage.security.ObjectProtection;
import com.linbit.drbdmanage.security.ObjectProtectionDatabaseDriver;
import com.linbit.drbdmanage.stateflags.StateFlagsPersistence;
import com.linbit.utils.UuidUtils;

public class ResourceDataDerbyDriver implements ResourceDataDatabaseDriver
{
    private static final String TBL_RES = DerbyConstants.TBL_NODE_RESOURCE;

    private static final String RES_UUID = DerbyConstants.UUID;
    private static final String RES_NODE_NAME = DerbyConstants.NODE_NAME;
    private static final String RES_NAME = DerbyConstants.RESOURCE_NAME;
    private static final String RES_NODE_ID = DerbyConstants.NODE_ID;
    private static final String RES_FLAGS = DerbyConstants.RESOURCE_FLAGS;

    private static final String RES_SELECT =
        " SELECT " + RES_UUID + ", " + RES_NODE_NAME + ", " + RES_NAME + ", " + RES_NODE_ID + ", " + RES_FLAGS +
        " FROM " + TBL_RES +
        " WHERE " + RES_NODE_NAME + " = ? AND " +
        RES_NAME      + " = ?";
    private static final String RES_SELECT_BY_NODE =
        " SELECT " + RES_UUID + ", " + RES_NODE_NAME + ", " + RES_NAME + ", " + RES_NODE_ID + ", " + RES_FLAGS +
        " FROM " + TBL_RES +
        " WHERE " + RES_NODE_NAME + " = ?";
    private static final String RES_SELECT_BY_RES_DFN =
        " SELECT " + RES_UUID + ", " + RES_NODE_NAME + ", " + RES_NAME + ", " + RES_NODE_ID + ", " + RES_FLAGS +
        " FROM " + TBL_RES +
        " WHERE " + RES_NAME + " = ?";

    private static final String RES_INSERT =
        " INSERT INTO " + TBL_RES + " VALUES (?, ?, ?, ?, ?)";
    private static final String RES_DELETE =
        " DELETE FROM " + TBL_RES +
        " WHERE " + RES_NODE_NAME + " = ? AND " +
                    RES_NAME      + " = ?";
    private static final String RES_UPDATE_FLAG =
        " UPDATE " + TBL_RES +
        " SET " + RES_FLAGS + " = ? " +
        " WHERE " + RES_NODE_NAME + " = ? AND " +
                    RES_NAME      + " = ?";

    private final AccessContext dbCtx;
    private final ErrorReporter errorReporter;
    private final FlagDriver flagDriver;

    private final VolumeDataDerbyDriver volumeDataDerbyDriver;

    public ResourceDataDerbyDriver(
        AccessContext accCtx,
        ErrorReporter errorReporterRef,
        VolumeDataDerbyDriver volumeDriver
    )
    {
        dbCtx = accCtx;
        errorReporter = errorReporterRef;

        flagDriver = new FlagDriver();
        volumeDataDerbyDriver = volumeDriver;
    }

    @Override
    public void create(ResourceData res, TransactionMgr transMgr) throws SQLException
    {
        create(dbCtx, res, transMgr);
    }

    private void create(AccessContext accCtx, ResourceData res, TransactionMgr transMgr) throws SQLException
    {
        errorReporter.logTrace("Creating Resource %s", getTraceId(res));
        try (PreparedStatement stmt = transMgr.dbCon.prepareStatement(RES_INSERT))
        {
            stmt.setBytes(1, UuidUtils.asByteArray(res.getUuid()));
            stmt.setString(2, res.getAssignedNode().getName().value);
            stmt.setString(3, res.getDefinition().getName().value);
            stmt.setInt(4, res.getNodeId().value);
            stmt.setLong(5, res.getStateFlags().getFlagsBits(accCtx));
            stmt.executeUpdate();
        }
        catch (AccessDeniedException accessDeniedExc)
        {
            DerbyDriver.handleAccessDeniedException(accessDeniedExc);
        }
        errorReporter.logDebug("Resource created %s", getDebugId(res));
    }

    public void ensureResExists(AccessContext accCtx, ResourceData res, TransactionMgr transMgr)
        throws SQLException
    {
        errorReporter.logTrace("Ensuring Resource exists %s", getTraceId(res));
        try (PreparedStatement stmt = transMgr.dbCon.prepareStatement(RES_SELECT))
        {
            stmt.setString(1, res.getAssignedNode().getName().value);
            stmt.setString(2, res.getDefinition().getName().value);

            try (ResultSet resultSet = stmt.executeQuery())
            {
                if (!resultSet.next())
                {
                    create(accCtx, res, transMgr);
                }
            }
        }
    }

    @Override
    public ResourceData load(
        Node node,
        ResourceName resourceName,
        SerialGenerator serialGen,
        TransactionMgr transMgr
    )
        throws SQLException
    {
        errorReporter.logTrace("Loading Resource %s", getTraceId(node, resourceName));
        ResourceData ret = null;
        try (PreparedStatement stmt = transMgr.dbCon.prepareStatement(RES_SELECT))
        {
            stmt.setString(1, node.getName().value);
            stmt.setString(2, resourceName.value);
            try (ResultSet resultSet = stmt.executeQuery())
            {
                List<ResourceData> list = load(resultSet, dbCtx, node, serialGen, transMgr);

                if (!list.isEmpty())
                {
                    ret = list.get(0);
                    // logDebug about "Resource loaded" was printed in the load method above
                }
                else
                {
                    errorReporter.logWarning("Resource could not be found %s", getDebugId(node, resourceName));
                }
            }
        }
        return ret;
    }

    public List<ResourceData> loadResourceDataByResourceDefinition(
        ResourceDefinitionData resDfn,
        SerialGenerator serialGen,
        TransactionMgr transMgr,
        AccessContext accCtx
    )
        throws SQLException
    {
        errorReporter.logTrace("Loading all Resources by ResourceDefinition %s", getTraceResDfnId(resDfn));
        List<ResourceData> resList;
        try (PreparedStatement stmt = transMgr.dbCon.prepareStatement(RES_SELECT_BY_RES_DFN))
        {
            stmt.setString(1, resDfn.getName().value);
            try (ResultSet resultSet = stmt.executeQuery())
            {
                resList = load(resultSet, accCtx, null, serialGen, transMgr);
            }
        }
        errorReporter.logTrace(
            "Loaded %d Resources for ResourceDefinition %s",
            resList.size(),
            getTraceResDfnId(resDfn)
        );
        return resList;
    }

    public List<ResourceData> loadResourceData(AccessContext dbCtx, NodeData node, SerialGenerator serialGen, TransactionMgr transMgr)
        throws SQLException
    {
        errorReporter.logTrace("Loading all Resources by Node %s", getTraceNodeId(node));
        List<ResourceData> ret;
        try (PreparedStatement stmt = transMgr.dbCon.prepareStatement(RES_SELECT_BY_NODE))
        {
            stmt.setString(1, node.getName().value);
            try (ResultSet resultSet = stmt.executeQuery())
            {
                ret = load(resultSet, dbCtx, node, serialGen, transMgr);
            }
        }
        errorReporter.logTrace(
            "Loaded %d Resources for Node %s",
            ret.size(),
            getTraceNodeId(node)
        );
        return ret;
    }

    private List<ResourceData> load(
        ResultSet resultSet,
        AccessContext dbCtx,
        Node globalNode,
        SerialGenerator serialGen,
        TransactionMgr transMgr
    )
        throws SQLException
    {
        List<ResourceData> resList = new ArrayList<>();
        while (resultSet.next())
        {
            Node node = null;
            ResourceName resName = null;
            try
            {
                if (globalNode != null)
                {
                    node = globalNode;
                }
                else
                {
                    try
                    {
                        node = DrbdManage.getNodeDataDatabaseDriver().load(
                            new NodeName(resultSet.getString(RES_NODE_NAME)),
                            serialGen,
                            transMgr
                        );
                    }
                    catch (InvalidNameException invalidNameExc)
                    {
                        throw new DrbdSqlRuntimeException(
                            String.format(
                                "A NodeName in the table %s has an illegal value. NodeName=%s",
                                TBL_RES,
                                resultSet.getString(RES_NODE_NAME)
                            ),
                            invalidNameExc
                        );
                    }
                }
                try
                {
                    resName = new ResourceName(resultSet.getString(RES_NAME));
                }
                catch (InvalidNameException invalidNameExc)
                {
                    throw new DrbdSqlRuntimeException(
                        String.format(
                            "A ResourceName in the table %s has an illegal value. ResourceName=%s",
                            TBL_RES,
                            resultSet.getString(RES_NAME)
                        ),
                        invalidNameExc
                    );
                }

                ResourceData resData = cacheGet(node, resName);
                if (resData == null)
                {
                    ResourceDefinitionDataDatabaseDriver resDfnDriver = DrbdManage.getResourceDefinitionDataDatabaseDriver();
                    ResourceDefinition resDfn = resDfnDriver.load(resName, serialGen, transMgr);

                    Resource loadedRes = resDfn.getResource(dbCtx, node.getName());
                    // although we just asked the cache, we also just loaded the resDfn.
                    // which loads all its resources.
                    if (loadedRes == null)
                    {
                        // here we are currently loading our own resDfn, and it is loading us

                        ObjectProtection objProt = getObjectProection(node, resName, transMgr);

                        NodeId nodeId;
                        try
                        {
                            nodeId = new NodeId(resultSet.getInt(RES_NODE_ID));
                        }
                        catch (ValueOutOfRangeException valueOutOfRangeExc)
                        {
                            throw new DrbdSqlRuntimeException(
                                String.format(
                                    "A NodeId in the table %s has an illegal value. %s Illegal value: %d",
                                    TBL_RES,
                                    getDebugId(node, resName),
                                    resultSet.getInt(RES_NODE_ID)
                                ),
                                valueOutOfRangeExc
                            );
                        }

                        resData = new ResourceData(
                            UuidUtils.asUuid(resultSet.getBytes(RES_UUID)),
                            objProt,
                            resDfn,
                            node,
                            nodeId,
                            resultSet.getLong(RES_FLAGS),
                            serialGen,
                            transMgr
                        );
                        errorReporter.logTrace("Resource instance created %s", getTraceId(resData));

                        // restore volumes
                        List<VolumeData> volList = volumeDataDerbyDriver.loadAllVolumesByResource(resData, transMgr, serialGen, dbCtx);
                        for (VolumeData volData : volList)
                        {
                            resData.putVolume(dbCtx, volData);
                        }
                        errorReporter.logTrace("Resource's Volumes restored %s", getTraceId(resData));
                        errorReporter.logDebug("Resource loaded from DB %s", getTraceId(resData));
                    }
                    else
                    {
                        // here we are back in our own loading (resDfn is finished)
                        // which means we simply use the already loaded version of us
                        resData = (ResourceData) loadedRes;
                    }
                }
                else
                {
                    errorReporter.logDebug("Resource loaded from cache %s", getDebugId(resData));
                }
                resList.add(resData);
            }
            catch (AccessDeniedException accessDeniedExc)
            {
                DerbyDriver.handleAccessDeniedException(accessDeniedExc);
            }
        }
        return resList;
    }

    private ObjectProtection getObjectProection(Node node, ResourceName resName, TransactionMgr transMgr)
        throws SQLException
    {
        ObjectProtectionDatabaseDriver objProtDriver = DrbdManage.getObjectProtectionDatabaseDriver();
        ObjectProtection objProt = objProtDriver.loadObjectProtection(
            ObjectProtection.buildPath(resName),
            transMgr
        );
        if (objProt == null)
        {
            throw new ImplementationError(
                "Resource's DB entry exists, but is missing an entry in ObjProt table! " + getTraceId(node, resName),
                null
            );
        }
        return objProt;
    }

    @Override
    public void delete(ResourceData resource, TransactionMgr transMgr) throws SQLException
    {
        errorReporter.logTrace("Deleting Resource %s", getTraceId(resource));
        try (PreparedStatement stmt = transMgr.dbCon.prepareStatement(RES_DELETE))
        {
            stmt.setString(1, resource.getAssignedNode().getName().value);
            stmt.setString(2, resource.getDefinition().getName().value);

            stmt.executeUpdate();
        }
        errorReporter.logDebug("Resource deleted %s", getTraceId(resource));
    }

    @Override
    public StateFlagsPersistence<ResourceData> getStateFlagPersistence()
    {
        return flagDriver;
    }

    private ResourceData cacheGet(Node node, ResourceName resName)
    {
        ResourceData ret = null;
        try
        {
            ret = (ResourceData) node.getResource(dbCtx, resName);
        }
        catch (AccessDeniedException accDeniedExc)
        {
            DerbyDriver.handleAccessDeniedException(accDeniedExc);
        }
        return ret;
    }

    private String getTraceId(Node node, ResourceName resourceName)
    {
        return getId(
            node.getName().value,
            resourceName.value
        );
    }

    private String getTraceId(ResourceData res)
    {
        return getId(
            res.getAssignedNode().getName().value,
            res.getDefinition().getName().value
        );
    }

    private String getDebugId(Node node, ResourceName resourceName)
    {
        return getId(
            node.getName().displayValue,
            resourceName.displayValue
        );
    }

    private String getDebugId(ResourceData res)
    {
        return getId(
            res.getAssignedNode().getName().displayValue,
            res.getDefinition().getName().displayValue
        );
    }

    private String getId(String nodeName, String resName)
    {
        return "(NodeName=" + nodeName + " ResName=" + resName + ")";
    }

    private String getTraceResDfnId(ResourceDefinitionData resDfn)
    {
        return "(ResName=" + resDfn.getName().value + ")";
    }

    private String getTraceNodeId(NodeData node)
    {
        return "(NodeName=" + node.getName().value + ")";
    }

    private class FlagDriver implements StateFlagsPersistence<ResourceData>
    {
        @Override
        public void persist(ResourceData resource, long flags, TransactionMgr transMgr) throws SQLException
        {
            try
            {
                errorReporter.logTrace("Updating Reource's flags from [%s] to [%s] %s",
                    Long.toBinaryString(resource.getStateFlags().getFlagsBits(dbCtx)),
                    Long.toBinaryString(flags),
                    getTraceId(resource)
                );
                try (PreparedStatement stmt = transMgr.dbCon.prepareStatement(RES_UPDATE_FLAG))
                {
                    stmt.setLong(1, flags);

                    stmt.setString(2, resource.getAssignedNode().getName().value);
                    stmt.setString(3, resource.getDefinition().getName().value);

                    stmt.executeUpdate();

                    errorReporter.logDebug("Reource's flags updated from [%s] to [%s] %s",
                        Long.toBinaryString(resource.getStateFlags().getFlagsBits(dbCtx)),
                        Long.toBinaryString(flags),
                        getDebugId(resource)
                    );
                }
            }
            catch (AccessDeniedException accDeniedExc)
            {
                DerbyDriver.handleAccessDeniedException(accDeniedExc);
            }
        }
    }
}