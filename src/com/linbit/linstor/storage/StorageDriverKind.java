package com.linbit.linstor.storage;

import com.linbit.fsevent.FileSystemWatch;
import com.linbit.linstor.logging.ErrorReporter;
import com.linbit.linstor.timer.CoreTimer;

import java.util.Map;
import java.util.Set;

/**
 * Provides information about and creates drivers of a particular type.
 */
public interface StorageDriverKind
{
    /**
     * Gets the name of the driver constructed by this factory.
     *
     * @return The driver name
     */
    String getDriverName();

    /**
     * Constructs a storage driver.
     *
     * @return The new storage driver instance
     */
    StorageDriver makeStorageDriver(
        ErrorReporter errorReporter,
        FileSystemWatch fileSystemWatch,
        CoreTimer timer
    );

    /**
     * Returns a map of the general characteristics of this type of driver.
     *
     * @return Map of key/value strings describing the driver's general characteristics
     */
    Map<String, String> getStaticTraits();

    /**
     * Returns a set of this driver type's configuration keys.
     *
     * @return Set of key strings describing the configuration keys accepted by the
     *     {@link StorageDriver#setConfiguration(Map)} method
     */
    Set<String> getConfigurationKeys();

    /**
     * Return whether this driver type supports snapshots.
     *
     * @return true if and only if snapshots are supported by the driver
     */
    boolean isSnapshotSupported();

    /**
     * Returns whether this driver has a backing storage. If it has, the storPool has
     * to have a non-empty NAMESPC_STORAGE_DRIVER namespace.
     *
     * @return true if and only if this driver has a backing storage.
     */
    boolean hasBackingStorage();
}
