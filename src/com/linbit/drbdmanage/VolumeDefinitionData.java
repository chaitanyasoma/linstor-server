package com.linbit.drbdmanage;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

import com.linbit.Checks;
import com.linbit.ErrorCheck;
import com.linbit.ImplementationError;
import com.linbit.TransactionMgr;
import com.linbit.TransactionSimpleObject;
import com.linbit.ValueOutOfRangeException;
import com.linbit.drbd.md.MaxSizeException;
import com.linbit.drbd.md.MdException;
import com.linbit.drbd.md.MetaData;
import com.linbit.drbd.md.MinSizeException;
import com.linbit.drbdmanage.dbdrivers.interfaces.VolumeDefinitionDataDatabaseDriver;
import com.linbit.drbdmanage.propscon.Props;
import com.linbit.drbdmanage.propscon.PropsAccess;
import com.linbit.drbdmanage.propscon.SerialGenerator;
import com.linbit.drbdmanage.propscon.SerialPropsContainer;
import com.linbit.drbdmanage.security.AccessContext;
import com.linbit.drbdmanage.security.AccessDeniedException;
import com.linbit.drbdmanage.security.AccessType;
import com.linbit.drbdmanage.security.ObjectProtection;
import com.linbit.drbdmanage.stateflags.StateFlags;
import com.linbit.drbdmanage.stateflags.StateFlagsBits;
import com.linbit.drbdmanage.stateflags.StateFlagsPersistence;

/**
 *
 * @author Robert Altnoeder &lt;robert.altnoeder@linbit.com&gt;
 */
public class VolumeDefinitionData extends BaseTransactionObject implements VolumeDefinition
{
    // Object identifier
    private final UUID objId;

    // Resource definition this VolumeDefinition belongs to
    private final ResourceDefinition resourceDfn;

    // DRBD volume number
    private final VolumeNumber volumeNr;

    // DRBD device minor number
    private final TransactionSimpleObject<MinorNumber> minorNr;

    // Net volume size in kiB
    private final TransactionSimpleObject<Long> volumeSize;

    // Properties container for this volume definition
    private final Props vlmDfnProps;

    // State flags
    private final StateFlags<VlmDfnFlags> flags;

    private final VolumeDefinitionDataDatabaseDriver dbDriver;

    private boolean deleted = false;

    /*
     * used by getInstance
     */
    private VolumeDefinitionData(
        AccessContext accCtx,
        ResourceDefinition resDfnRef,
        VolumeNumber volNr,
        MinorNumber minor,
        long volSize,
        long initFlags,
        SerialGenerator srlGen,
        TransactionMgr transMgr
    )
        throws MdException, AccessDeniedException, SQLException
    {
        this(
            UUID.randomUUID(),
            accCtx,
            resDfnRef,
            volNr,
            minor,
            volSize,
            initFlags,
            srlGen,
            transMgr
        );
    }

    /*
     * used by database drivers and tests
     */
    VolumeDefinitionData(
        UUID uuid,
        AccessContext accCtx,
        ResourceDefinition resDfnRef,
        VolumeNumber volNr,
        MinorNumber minor,
        long volSize,
        long initFlags,
        SerialGenerator srlGen,
        TransactionMgr transMgr
    )
        throws MdException, AccessDeniedException, SQLException
    {

        ErrorCheck.ctorNotNull(VolumeDefinitionData.class, ResourceDefinition.class, resDfnRef);
        ErrorCheck.ctorNotNull(VolumeDefinitionData.class, VolumeNumber.class, volNr);
        ErrorCheck.ctorNotNull(VolumeDefinitionData.class, MinorNumber.class, minor);

        // Creating a new volume definition requires CHANGE access to the resource definition
        resDfnRef.getObjProt().requireAccess(accCtx, AccessType.CHANGE);

        try
        {
            Checks.genericRangeCheck(
                volSize, MetaData.DRBD_MIN_NET_kiB, MetaData.DRBD_MAX_kiB,
                "Volume size value %d is out of range [%d - %d]"
            );
        }
        catch (ValueOutOfRangeException valueExc)
        {
            String excMessage = String.format(
                "Volume size value %d is out of range [%d - %d]",
                volSize, MetaData.DRBD_MIN_NET_kiB, MetaData.DRBD_MAX_kiB
            );
            if (valueExc.getViolationType() == ValueOutOfRangeException.ViolationType.TOO_LOW)
            {
                throw new MinSizeException(excMessage);
            }
            else
            {
                throw new MaxSizeException(excMessage);
            }
        }

        objId = uuid;
        resourceDfn = resDfnRef;

        dbDriver = DrbdManage.getVolumeDefinitionDataDatabaseDriver(resDfnRef, volNr);

        volumeNr = volNr;
        minorNr = new TransactionSimpleObject<>(
            minor,
            dbDriver.getMinorNumberDriver()
        );
        volumeSize = new TransactionSimpleObject<>(
            volSize,
            dbDriver.getVolumeSizeDriver()
        );

        vlmDfnProps = SerialPropsContainer.getInstance(dbDriver.getPropsDriver(), transMgr, srlGen);

        flags = new VlmDfnFlagsImpl(
            resDfnRef.getObjProt(),
            dbDriver.getStateFlagsPersistence(),
            initFlags
        );

        transObjs = Arrays.asList(
            vlmDfnProps,
            resourceDfn,
            minorNr,
            volumeSize,
            flags
        );
    }

    public static VolumeDefinitionData getInstance(
        AccessContext accCtx,
        ResourceDefinition resDfn,
        VolumeNumber volNr,
        MinorNumber minor,
        long volSize,
        VlmDfnFlags[] initFlags,
        SerialGenerator serialGen,
        TransactionMgr transMgr,
        boolean createIfNotExists
    )
        throws SQLException, AccessDeniedException, MdException
    {
        VolumeDefinitionData volDfn = null;

        VolumeDefinitionDataDatabaseDriver driver = DrbdManage.getVolumeDefinitionDataDatabaseDriver(resDfn, volNr);
        if (transMgr != null)
        {
            volDfn = driver.load(transMgr.dbCon, transMgr, serialGen);
        }

        if (volDfn == null)
        {
            if (createIfNotExists)
            {
                volDfn = new VolumeDefinitionData(
                    accCtx,
                    resDfn,
                    volNr,
                    minor,
                    volSize,
                    StateFlagsBits.getMask(initFlags),
                    serialGen,
                    transMgr
                );
                if (transMgr != null)
                {
                    driver.create(transMgr.dbCon, volDfn);
                }
            }
        }

        if (volDfn != null)
        {
            ((ResourceDefinitionData) resDfn).putVolumeDefinition(accCtx, volDfn);

            volDfn.initialized();
        }


        return volDfn;
    }

    @Override
    public UUID getUuid()
    {
        checkDeleted();
        return objId;
    }

    @Override
    public Props getProps(AccessContext accCtx)
        throws AccessDeniedException
    {
        checkDeleted();
        return PropsAccess.secureGetProps(accCtx, resourceDfn.getObjProt(), vlmDfnProps);
    }

    @Override
    public ResourceDefinition getResourceDfn()
    {
        checkDeleted();
        return resourceDfn;
    }

    @Override
    public VolumeNumber getVolumeNumber(AccessContext accCtx)
        throws AccessDeniedException
    {
        checkDeleted();
        resourceDfn.getObjProt().requireAccess(accCtx, AccessType.VIEW);
        return volumeNr;
    }

    @Override
    public MinorNumber getMinorNr(AccessContext accCtx)
        throws AccessDeniedException
    {
        checkDeleted();
        resourceDfn.getObjProt().requireAccess(accCtx, AccessType.VIEW);
        return minorNr.get();
    }

    @Override
    public void setMinorNr(AccessContext accCtx, MinorNumber newMinorNr)
        throws AccessDeniedException, SQLException
    {
        checkDeleted();
        resourceDfn.getObjProt().requireAccess(accCtx, AccessType.CHANGE);
        minorNr.set(newMinorNr);
    }

    @Override
    public long getVolumeSize(AccessContext accCtx)
        throws AccessDeniedException
    {
        checkDeleted();
        resourceDfn.getObjProt().requireAccess(accCtx, AccessType.VIEW);
        return volumeSize.get();
    }

    @Override
    public void setVolumeSize(AccessContext accCtx, long newVolumeSize)
        throws AccessDeniedException, SQLException
    {
        checkDeleted();
        resourceDfn.getObjProt().requireAccess(accCtx, AccessType.CHANGE);
        volumeSize.set(newVolumeSize);
    }

    @Override
    public StateFlags<VlmDfnFlags> getFlags()
    {
        checkDeleted();
        return flags;
    }

    @Override
    public void delete(AccessContext accCtx)
        throws AccessDeniedException, SQLException
    {
        checkDeleted();
        resourceDfn.getObjProt().requireAccess(accCtx, AccessType.CHANGE);

        dbDriver.delete(dbCon);
        deleted = true;
    }

    private void checkDeleted()
    {
        if (deleted)
        {
            throw new ImplementationError("Access to deleted node", null);
        }
    }

    private static final class VlmDfnFlagsImpl extends StateFlagsBits<VlmDfnFlags>
    {
        VlmDfnFlagsImpl(
            ObjectProtection objProtRef,
            StateFlagsPersistence persistenceRef,
            long initFlags
        )
        {
            super(
                objProtRef,
                StateFlagsBits.getMask(VlmDfnFlags.values()),
                persistenceRef,
                initFlags
            );
        }
    }
}
