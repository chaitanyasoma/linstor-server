package com.linbit.linstor.dbdrivers.interfaces;

import java.sql.SQLException;

import com.linbit.linstor.Resource;
import com.linbit.linstor.VolumeData;
import com.linbit.linstor.VolumeDefinition;
import com.linbit.linstor.stateflags.StateFlagsPersistence;

/**
 * Database driver for {@link com.linbit.linstor.VolumeData}.
 *
 * @author Gabor Hernadi &lt;gabor.hernadi@linbit.com&gt;
 */
public interface VolumeDataDatabaseDriver
{
    /**
     * Loads the {@link com.linbit.linstor.VolumeData} specified by the parameters
     *
     * @param resource
     *  Part of the primary key specifying the database entry
     * @param volumeDefinition
     *  Part of the primary key specifying the database entry
     * @param logWarnIfNotExists
     *  If true a warning is logged if the requested entry does not exist
     *
     * @throws SQLException
     */
    VolumeData load(Resource resource, VolumeDefinition volumeDefinition, boolean logWarnIfNotExists)
        throws SQLException;

    /**
     * Persists the given {@link com.linbit.linstor.VolumeData} into the database.
     *
     * @param volume
     *  The data to be stored (including the primary key)
     *
     * @throws SQLException
     */
    void create(VolumeData volume) throws SQLException;

    /**
     * Removes the given {@link com.linbit.linstor.VolumeData} from the database
     *
     * @param volume
     *  The data identifying the row to delete
     *
     * @throws SQLException
     */
    void delete(VolumeData volume) throws SQLException;

    /**
     * A special sub-driver to update the persisted flags
     */
    StateFlagsPersistence<VolumeData> getStateFlagsPersistence();
}
