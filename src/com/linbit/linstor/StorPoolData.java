package com.linbit.linstor;

import com.linbit.ImplementationError;
import com.linbit.fsevent.FileSystemWatch;
import com.linbit.linstor.api.pojo.StorPoolPojo;
import com.linbit.linstor.dbdrivers.interfaces.StorPoolDataDatabaseDriver;
import com.linbit.linstor.logging.ErrorReporter;
import com.linbit.linstor.propscon.InvalidKeyException;
import com.linbit.linstor.propscon.Props;
import com.linbit.linstor.propscon.PropsAccess;
import com.linbit.linstor.propscon.PropsContainer;
import com.linbit.linstor.propscon.PropsContainerFactory;
import com.linbit.linstor.security.AccessContext;
import com.linbit.linstor.security.AccessDeniedException;
import com.linbit.linstor.security.AccessType;
import com.linbit.linstor.storage.StorageDriver;
import com.linbit.linstor.storage.StorageDriverKind;
import com.linbit.linstor.storage.StorageDriverLoader;
import com.linbit.linstor.storage.StorageException;
import com.linbit.linstor.timer.CoreTimer;
import com.linbit.linstor.transaction.BaseTransactionObject;
import com.linbit.linstor.transaction.TransactionMap;
import com.linbit.linstor.transaction.TransactionMgr;
import com.linbit.linstor.transaction.TransactionObject;
import com.linbit.linstor.transaction.TransactionObjectFactory;
import com.linbit.linstor.transaction.TransactionSimpleObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;
import javax.inject.Provider;

import static com.linbit.linstor.api.ApiConsts.KEY_STOR_POOL_SUPPORTS_SNAPSHOTS;
import static com.linbit.linstor.api.ApiConsts.NAMESPC_STORAGE_DRIVER;

public class StorPoolData extends BaseTransactionObject implements StorPool
{
    private final UUID uuid;

    // Runtime instance identifier for debug purposes
    private final transient UUID dbgInstanceId;

    private final StorPoolDefinition storPoolDef;
    private final StorageDriverKind storageDriverKind;
    private final boolean allowStorageDriverCreation;
    private final Props props;
    private final Node node;
    private final StorPoolDataDatabaseDriver dbDriver;

    private final TransactionMap<String, Volume> volumeMap;

    private final TransactionSimpleObject<StorPoolData, Boolean> deleted;

    private transient StorageDriver storageDriver;

    private transient TransactionSimpleObject<StorPoolData, Long> freeSpace;

    StorPoolData(
        UUID id,
        AccessContext accCtx,
        Node nodeRef,
        StorPoolDefinition storPoolDefRef,
        String storageDriverName,
        boolean allowStorageDriverCreationRef,
        StorPoolDataDatabaseDriver dbDriverRef,
        PropsContainerFactory propsContainerFactory,
        TransactionObjectFactory transObjFactory,
        Provider<TransactionMgr> transMgrProviderRef
    )
        throws SQLException, AccessDeniedException
    {
        super(transMgrProviderRef);
        uuid = id;
        dbgInstanceId = UUID.randomUUID();
        storPoolDef = storPoolDefRef;
        storageDriverKind = StorageDriverLoader.getKind(storageDriverName);
        allowStorageDriverCreation = allowStorageDriverCreationRef;
        node = nodeRef;
        dbDriver = dbDriverRef;
        volumeMap = transObjFactory.createTransactionMap(new TreeMap<String, Volume>(), null);

        props = propsContainerFactory.getInstance(
            PropsContainer.buildPath(storPoolDef.getName(), node.getName())
        );
        deleted = transObjFactory.createTransactionSimpleObject(this, false, null);

        freeSpace = transObjFactory.createTransactionSimpleObject(this, null, null);

        transObjs = Arrays.<TransactionObject>asList(
            volumeMap,
            props,
            deleted,
            freeSpace
        );

        ((NodeData) nodeRef).addStorPool(accCtx, this);
        ((StorPoolDefinitionData) storPoolDefRef).addStorPool(accCtx, this);
        activateTransMgr();
    }

    @Override
    public UUID debugGetVolatileUuid()
    {
        return dbgInstanceId;
    }

    @Override
    public UUID getUuid()
    {
        checkDeleted();
        return uuid;
    }

    @Override
    public StorPoolName getName()
    {
        checkDeleted();
        return storPoolDef.getName();
    }

    @Override
    public Node getNode()
    {
        return node;
    }

    @Override
    public StorPoolDefinition getDefinition(AccessContext accCtx) throws AccessDeniedException
    {
        checkDeleted();
        node.getObjProt().requireAccess(accCtx, AccessType.VIEW);
        storPoolDef.getObjProt().requireAccess(accCtx, AccessType.VIEW);
        return storPoolDef;
    }

    @Override
    public StorageDriver getDriver(
        AccessContext accCtx,
        ErrorReporter errorReporter,
        FileSystemWatch fileSystemWatch,
        CoreTimer timer
    )
        throws AccessDeniedException
    {
        if (allowStorageDriverCreation)
        {
            checkDeleted();
            node.getObjProt().requireAccess(accCtx, AccessType.USE);
            if (storageDriver == null)
            {
                storageDriver = storageDriverKind.makeStorageDriver(
                    errorReporter,
                    fileSystemWatch,
                    timer
                );
            }
        }
        return storageDriver;
    }

    @Override
    public StorageDriverKind getDriverKind(AccessContext accCtx)
        throws AccessDeniedException
    {
        return storageDriverKind;
    }

    @Override
    public Props getProps(AccessContext accCtx) throws AccessDeniedException
    {
        checkDeleted();
        return PropsAccess.secureGetProps(accCtx, node.getObjProt(), storPoolDef.getObjProt(), props);
    }

    @Override
    public void reconfigureStorageDriver(StorageDriver storageDriverRef)
        throws StorageException
    {
        checkDeleted();
        try
        {
            if (storageDriverRef.getKind().hasBackingStorage())
            {
                Props namespace = props.getNamespace(NAMESPC_STORAGE_DRIVER);
                if (namespace == null)
                {
                    throw new ImplementationError(
                        "Missing required namespace for backing storage",
                        null
                    );
                }
                Map<String, String> map = namespace.map();
                storageDriverRef.setConfiguration(map);
            }
        }
        catch (InvalidKeyException invalidKeyExc)
        {
            throw new ImplementationError("Hard coded constant cause an invalid key exception", invalidKeyExc);
        }
    }

    @Override
    public String getDriverName()
    {
        checkDeleted();
        return storageDriverKind.getDriverName();
    }

    @Override
    public void putVolume(AccessContext accCtx, Volume volume) throws AccessDeniedException
    {
        node.getObjProt().requireAccess(accCtx, AccessType.USE);
        storPoolDef.getObjProt().requireAccess(accCtx, AccessType.USE);

        volumeMap.put(getVolumeKey(volume), volume);
    }

    @Override
    public void removeVolume(AccessContext accCtx, Volume volume) throws AccessDeniedException
    {
        node.getObjProt().requireAccess(accCtx, AccessType.USE);
        storPoolDef.getObjProt().requireAccess(accCtx, AccessType.USE);

        volumeMap.remove(getVolumeKey(volume));
    }

    @Override
    public Collection<Volume> getVolumes(AccessContext accCtx) throws AccessDeniedException
    {
        node.getObjProt().requireAccess(accCtx, AccessType.USE);
        storPoolDef.getObjProt().requireAccess(accCtx, AccessType.USE);

        return volumeMap.values();
    }

    private String getVolumeKey(Volume volume)
    {
        NodeName nodeName = volume.getResource().getAssignedNode().getName();
        ResourceName rscName = volume.getResourceDefinition().getName();
        VolumeNumber volNr = volume.getVolumeDefinition().getVolumeNumber();
        return nodeName.value + "/" + rscName.value + "/" + volNr.value;
    }

    public void setRealFreeSpace(AccessContext accCtx, long freeSpaceRef) throws AccessDeniedException, SQLException
    {
        node.getObjProt().requireAccess(accCtx, AccessType.USE);
        freeSpace.set(freeSpaceRef);
    }

    @Override
    public Optional<Long> getFreeSpace(AccessContext accCtx) throws AccessDeniedException
    {
        node.getObjProt().requireAccess(accCtx, AccessType.VIEW);
        return Optional.ofNullable(freeSpace.get());
    }

    @Override
    public void delete(AccessContext accCtx)
        throws AccessDeniedException, SQLException
    {
        if (!deleted.get())
        {
            node.getObjProt().requireAccess(accCtx, AccessType.USE);
            storPoolDef.getObjProt().requireAccess(accCtx, AccessType.USE);

            ((NodeData) node).removeStorPool(accCtx, this);
            ((StorPoolDefinitionData) storPoolDef).removeStorPool(accCtx, this);

            props.delete();

            activateTransMgr();
            dbDriver.delete(this);

            deleted.set(true);
        }
    }

    private void checkDeleted()
    {
        if (deleted.get())
        {
            throw new AccessToDeletedDataException("Access to deleted storage pool");
        }
    }

    private Map<String, String> getTraits(AccessContext accCtx)
        throws AccessDeniedException
    {
        Map<String, String> traits = new HashMap<>(storageDriverKind.getStaticTraits());

        traits.put(
            KEY_STOR_POOL_SUPPORTS_SNAPSHOTS,
            String.valueOf(storageDriverKind.isSnapshotSupported())
        );

        return traits;
    }

    @Override
    public String toString()
    {
        return "Node: '" + node.getName() + "', " +
               "StorPool: '" + storPoolDef.getName() + "'";
    }

    @Override
    public StorPoolApi getApiData(AccessContext accCtx, Long fullSyncId, Long updateId)
        throws AccessDeniedException
    {
        ArrayList<Volume.VlmApi> vlms = new ArrayList<>();
        for (Volume vlm : getVolumes(accCtx))
        {
            vlms.add(vlm.getApiData(accCtx));
        }
        return new StorPoolPojo(
            getUuid(),
            getNode().getUuid(),
            node.getName().getDisplayName(),
            getName().getDisplayName(),
            getDefinition(accCtx).getUuid(),
            getDriverName(),
            getProps(accCtx).map(),
            getDefinition(accCtx).getProps(accCtx).map(),
            vlms,
            getTraits(accCtx),
            fullSyncId,
            updateId,
            getFreeSpace(accCtx)
        );
    }
}
