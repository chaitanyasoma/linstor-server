package com.linbit.linstor.dbdrivers.satellite;

import com.linbit.linstor.StorPoolDefinitionData;
import com.linbit.linstor.StorPoolName;
import com.linbit.linstor.core.CoreModule;
import com.linbit.linstor.dbdrivers.interfaces.StorPoolDefinitionDataDatabaseDriver;
import javax.inject.Inject;

public class SatelliteStorPoolDfnDriver implements StorPoolDefinitionDataDatabaseDriver
{
    private final CoreModule.StorPoolDefinitionMap storPoolDfnMap;

    @Inject
    public SatelliteStorPoolDfnDriver(CoreModule.StorPoolDefinitionMap storPoolDfnMapRef)
    {
        storPoolDfnMap = storPoolDfnMapRef;
    }

    @Override
    public void create(StorPoolDefinitionData storPoolDefinitionData)
    {
        // no-op
    }

    @Override
    public StorPoolDefinitionData load(StorPoolName storPoolName, boolean logWarnIfNotExists)
    {
        return (StorPoolDefinitionData) storPoolDfnMap.get(storPoolName);
    }

    @Override
    public void delete(StorPoolDefinitionData data)
    {
        // no-op
    }
}
