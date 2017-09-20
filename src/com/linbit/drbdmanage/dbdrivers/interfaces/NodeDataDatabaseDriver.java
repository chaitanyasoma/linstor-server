package com.linbit.drbdmanage.dbdrivers.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

import com.linbit.SingleColumnDatabaseDriver;
import com.linbit.TransactionMgr;
import com.linbit.drbdmanage.Node.NodeFlag;
import com.linbit.drbdmanage.Node.NodeType;
import com.linbit.drbdmanage.BaseTransactionObject;
import com.linbit.drbdmanage.Node;
import com.linbit.drbdmanage.NodeData;
import com.linbit.drbdmanage.NodeName;
import com.linbit.drbdmanage.propscon.SerialGenerator;
import com.linbit.drbdmanage.propscon.SerialPropsContainer;
import com.linbit.drbdmanage.stateflags.StateFlagsPersistence;

/**
 * Database driver for {@link NodeData}.
 *
 * @author Gabor Hernadi <gabor.hernadi@linbit.com>
 */
public interface NodeDataDatabaseDriver
{
    /**
     * Loads the {@link NodeData} specified by the parameter {@code nodeName}
     *
     * @param nodeName
     *  The primary key identifying the row to load
     * @param serialGen
     *  The {@link SerialGenerator}, used to initialize the {@link SerialPropsContainer}
     * @param transMgr
     *  The {@link TransactionMgr}, used to restore references, like {@link Node},
     *  {@link Resource}, and so on
     * @return
     *  An instance which contains valid references, but is not
     *  initialized yet in regards of {@link BaseTransactionObject#initialized()}
     *
     * @throws SQLException
     */
    public NodeData load(
        NodeName nodeName,
        SerialGenerator serialGen,
        TransactionMgr transMgr
    )
        throws SQLException;

    /**
     * Persists the given {@link NodeData} into the database.
     *
     * @param nodeData
     *  The data to be stored (including the primary key)
     * @param transMgr
     *  The {@link TransactionMgr} containing the used database {@link Connection}
     * @throws SQLException
     */
    public void create(NodeData nodeData, TransactionMgr transMgr) throws SQLException;

    /**
     * Removes the given {@link NodeData} from the database
     *
     * @param node
     *  The data identifying the database entry to delete
     * @param transMgr
     *  The {@link TransactionMgr} containing the used database {@link Connection}
     * @throws SQLException
     */
    public void delete(NodeData node, TransactionMgr transMgr) throws SQLException;

    /**
     * A special sub-driver to update the persisted {@link NodeFlag}s. The data record
     * is specified by the primary key stored as instance variables.
     */
    public StateFlagsPersistence<NodeData> getStateFlagPersistence();

    /**
     * A special sub-driver to update the persisted {@link NodeType}. The data record
     * is specified by the primary key stored as instance variables.
     */
    public SingleColumnDatabaseDriver<NodeData, NodeType> getNodeTypeDriver();
}