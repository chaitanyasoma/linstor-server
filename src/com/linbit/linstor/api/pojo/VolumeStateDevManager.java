package com.linbit.linstor.api.pojo;

import com.linbit.linstor.StorPoolName;
import com.linbit.linstor.VolumeNumber;
import com.linbit.linstor.storage.StorageDriver;

public class VolumeStateDevManager extends VolumeState
{

    /**
     * Indicates whether the resource should be deleted
     */
    protected boolean markedForDelete = false;

    /**
     * Whether to skip/ignore the volume in following steps
     */
    protected boolean skip = false;

    /**
     * Indicates whether a lookup for the volume's StorageDriver has already been performed
     * Note that this does not imply that the driver reference is non-null
     */
    protected boolean driverKnown = false;

    /**
     * Reference to the storage driver for the storage backend volume
     */
    protected StorageDriver driver = null;

    /**
     * Name of the storage pool that is selected for the storage backend volume
     */
    protected StorPoolName storPoolName = null;

    /**
     * Name of the storage backend volume as known to the storage driver
     */
    protected String storVlmName = null;

    public VolumeStateDevManager(VolumeNumber volNrRef, long netSizeSpec)
    {
        vlmNr = volNrRef;
        netSize = netSizeSpec;
    }

    public boolean isMarkedForDelete()
    {
        return markedForDelete;
    }

    public void setMarkedForDelete(boolean markedForDeleteRef)
    {
        markedForDelete = markedForDeleteRef;
    }

    public boolean isSkip()
    {
        return skip;
    }

    public void setSkip(boolean skipRef)
    {
        skip = skipRef;
    }

    public boolean isDriverKnown()
    {
        return driverKnown;
    }

    public void setDriverKnown(boolean driverKnownRef)
    {
        driverKnown = driverKnownRef;
    }

    public StorageDriver getDriver()
    {
        return driver;
    }

    public void setDriver(StorageDriver driverRef)
    {
        driver = driverRef;
    }

    public StorPoolName getStorPoolName()
    {
        return storPoolName;
    }

    public void setStorPoolName(StorPoolName storPoolNameRef)
    {
        storPoolName = storPoolNameRef;
    }

    public String getStorVlmName()
    {
        return storVlmName;
    }

    public void setStorVlmName(String storVlmNameRef)
    {
        storVlmName = storVlmNameRef;
    }

    @Override
    public String toString()
    {
        StringBuilder vlmStateString = new StringBuilder();
        vlmStateString.append(super.toString());
        vlmStateString.append("        skip            = ").append(isSkip()).append("\n");
        vlmStateString.append("        driverKnown     = ").append(isDriverKnown()).append("\n");
        vlmStateString.append("        driver          = ").append(getDriver()).append("\n");
        vlmStateString.append("        storPoolName    = ").append(getStorPoolName()).append("\n");
        vlmStateString.append("        storPoolVlmName = ").append(getStorVlmName()).append("\n");
        return vlmStateString.toString();
    }
}
