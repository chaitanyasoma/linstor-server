package com.linbit.linstor;

import com.linbit.ImplementationError;
import com.linbit.linstor.core.StltSecurityObjects;
import com.linbit.linstor.dbdrivers.interfaces.VolumeDefinitionDataDatabaseDriver;
import com.linbit.linstor.propscon.PropsContainerFactory;
import com.linbit.linstor.security.AccessContext;
import com.linbit.linstor.stateflags.StateFlagsBits;
import com.linbit.linstor.transaction.TransactionMgr;
import com.linbit.linstor.transaction.TransactionObjectFactory;

import javax.inject.Inject;
import javax.inject.Provider;

import java.util.UUID;

public class VolumeDefinitionDataSatelliteFactory
{
    private final VolumeDefinitionDataDatabaseDriver driver;
    private final PropsContainerFactory propsContainerFactory;
    private final TransactionObjectFactory transObjFactory;
    private final Provider<TransactionMgr> transMgrProvider;
    private final StltSecurityObjects stltSecObjs;

    @Inject
    public VolumeDefinitionDataSatelliteFactory(
        VolumeDefinitionDataDatabaseDriver driverRef,
        PropsContainerFactory propsContainerFactoryRef,
        TransactionObjectFactory transObjFactoryRef,
        Provider<TransactionMgr> transMgrProviderRef,
        StltSecurityObjects stltSecObjsRef
    )
    {
        driver = driverRef;
        propsContainerFactory = propsContainerFactoryRef;
        transObjFactory = transObjFactoryRef;
        transMgrProvider = transMgrProviderRef;
        stltSecObjs = stltSecObjsRef;
    }

    public VolumeDefinitionData getInstanceSatellite(
        AccessContext accCtx,
        UUID vlmDfnUuid,
        ResourceDefinition rscDfn,
        VolumeNumber vlmNr,
        long vlmSize,
        MinorNumber minorNumber,
        VolumeDefinition.VlmDfnFlags[] flags
    )
        throws ImplementationError
    {
        VolumeDefinitionData vlmDfnData;
        try
        {
            vlmDfnData = driver.load(rscDfn, vlmNr, false);
            if (vlmDfnData == null)
            {
                vlmDfnData = new VolumeDefinitionData(
                    vlmDfnUuid,
                    accCtx,
                    rscDfn,
                    vlmNr,
                    minorNumber,
                    null,
                    vlmSize,
                    StateFlagsBits.getMask(flags),
                    driver,
                    propsContainerFactory,
                    transObjFactory,
                    transMgrProvider
                );
            }
        }
        catch (Exception exc)
        {
            throw new ImplementationError(
                "This method should only be called with a satellite db in background!",
                exc
            );
        }
        return vlmDfnData;
    }
}
