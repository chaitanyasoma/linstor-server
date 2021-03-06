package com.linbit.linstor.numberpool;

import com.linbit.ExhaustedPoolException;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class BitmapPoolTest
{
    @Test
    @Parameters(method = "poolConfigurations")
    public void testAllocate(PoolConfiguration poolConfiguration)
    {
        BitmapPool bitmapPool = new BitmapPool(poolConfiguration.getSize());

        boolean allocated = bitmapPool.allocate(poolConfiguration.getOffset());
        assertThat(allocated).as("allocate return value").isTrue();

        assertThat(bitmapPool.isAllocated(poolConfiguration.getOffset()))
            .as("isAllocated").isTrue();
    }

    @Test
    @Parameters(method = "poolConfigurations")
    public void testDoubleAllocate(PoolConfiguration poolConfiguration)
    {
        BitmapPool bitmapPool = new BitmapPool(poolConfiguration.getSize());

        bitmapPool.allocate(poolConfiguration.getOffset());

        boolean allocated = bitmapPool.allocate(poolConfiguration.getOffset());
        assertThat(allocated).as("second allocate return value").isFalse();
    }

    @Test
    @Parameters(method = "poolConfigurations")
    public void testDeallocate(PoolConfiguration poolConfiguration)
    {
        BitmapPool bitmapPool = new BitmapPool(poolConfiguration.getSize());

        bitmapPool.allocate(poolConfiguration.getOffset());

        assertThat(bitmapPool.isAllocated(poolConfiguration.getOffset()))
            .as("isAllocated before").isTrue();

        bitmapPool.deallocate(poolConfiguration.getOffset());

        assertThat(bitmapPool.isAllocated(poolConfiguration.getOffset()))
            .as("isAllocated after").isFalse();
    }

    @Test
    @Parameters(method = "poolConfigurations")
    public void testAllocateAll(PoolConfiguration poolConfiguration)
    {
        BitmapPool bitmapPool = new BitmapPool(poolConfiguration.getSize());

        bitmapPool.allocateAll(
            generateNumberRange(poolConfiguration.getRangeStart(), poolConfiguration.getRangeEnd()));

        for (int nr = poolConfiguration.getRangeStart(); nr <= poolConfiguration.getRangeEnd(); nr++)
        {
            assertThat(bitmapPool.isAllocated(nr))
                .as("isAllocated 0x%x", nr).isTrue();
        }

        if (poolConfiguration.getRangeStart() > 0)
        {
            assertThat(bitmapPool.isAllocated(poolConfiguration.getRangeStart() - 1))
                .as("isAllocated before range").isFalse();
        }

        if (poolConfiguration.getRangeEnd() < poolConfiguration.getSize() - 1)
        {
            assertThat(bitmapPool.isAllocated(poolConfiguration.getRangeEnd() + 1))
                .as("isAllocated after range").isFalse();
        }
    }

    @Test
    @Parameters(method = "poolConfigurations")
    public void testDeallocateAll(PoolConfiguration poolConfiguration)
    {
        BitmapPool bitmapPool = new BitmapPool(poolConfiguration.getSize());

        bitmapPool.allocateAll(
            generateNumberRange(poolConfiguration.getRangeStart(), poolConfiguration.getRangeEnd()));
        bitmapPool.deallocateAll(
            generateNumberRange(poolConfiguration.getRangeStart(), poolConfiguration.getRangeEnd()));

        for (int nr = poolConfiguration.getRangeStart(); nr <= poolConfiguration.getRangeEnd(); nr++)
        {
            assertThat(bitmapPool.isAllocated(nr))
                .as("isAllocated 0x%x", nr).isFalse();
        }
    }

    @Test
    @Parameters(method = "poolConfigurations")
    public void testMultiAllocate(PoolConfiguration poolConfiguration)
    {
        BitmapPool bitmapPool = new BitmapPool(poolConfiguration.getSize());

        bitmapPool.allocate(poolConfiguration.getOffset());

        Map<Integer, Boolean> allocationMap = bitmapPool.multiAllocate(
            generateNumberRangeList(poolConfiguration.getRangeStart(), poolConfiguration.getRangeEnd()));

        for (int nr = poolConfiguration.getRangeStart(); nr <= poolConfiguration.getRangeEnd(); nr++)
        {
            if (nr == poolConfiguration.getOffset())
            {
                assertThat(allocationMap.get(nr))
                    .as("allocationMap 0x%x", nr).isFalse();
            }
            else
            {
                assertThat(allocationMap.get(nr))
                    .as("allocationMap 0x%x", nr).isTrue();
            }
        }
    }

    @Test
    @Parameters(method = "poolConfigurationsNonTrivial")
    public void testFindUnallocated(PoolConfiguration poolConfiguration)
        throws Exception
    {
        BitmapPool bitmapPool = new BitmapPool(poolConfiguration.getSize());

        bitmapPool.allocate(poolConfiguration.getRangeStart());

        assertThat(bitmapPool.findUnallocated(poolConfiguration.getRangeStart(), poolConfiguration.getRangeEnd()))
            .isEqualTo(poolConfiguration.getRangeStart() + 1);
    }

    @Test(expected = ExhaustedPoolException.class)
    @Parameters(method = "poolConfigurations")
    public void testFindUnallocatedExhausted(PoolConfiguration poolConfiguration)
        throws Exception
    {
        BitmapPool bitmapPool = new BitmapPool(poolConfiguration.getSize());

        bitmapPool.allocateAll(
            generateNumberRange(poolConfiguration.getRangeStart(), poolConfiguration.getRangeEnd()));

        bitmapPool.findUnallocated(poolConfiguration.getRangeStart(), poolConfiguration.getRangeEnd());
    }

    @Test
    @Parameters(method = "poolConfigurationsNonTrivial")
    public void testFindUnallocatedFromOffset(PoolConfiguration poolConfiguration)
        throws Exception
    {
        BitmapPool bitmapPool = new BitmapPool(poolConfiguration.getSize());

        bitmapPool.allocate(poolConfiguration.getOffset());

        assertThat(bitmapPool.findUnallocatedFromOffset(
            poolConfiguration.getRangeStart(),
            poolConfiguration.getRangeEnd(),
            poolConfiguration.getOffset())
        ).isEqualTo(poolConfiguration.getOffset() + 1);
    }

    @Test
    @Parameters(method = "poolConfigurationsNonTrivial")
    public void testAutoAllocate(PoolConfiguration poolConfiguration)
        throws Exception
    {
        BitmapPool bitmapPool = new BitmapPool(poolConfiguration.getSize());

        bitmapPool.allocate(poolConfiguration.getRangeStart());

        int expected = poolConfiguration.getRangeStart() + 1;
        assertThat(bitmapPool.isAllocated(expected)).as("isAllocated before").isFalse();

        int allocated = bitmapPool.autoAllocate(
            poolConfiguration.getRangeStart(), poolConfiguration.getRangeEnd());

        assertThat(allocated).isEqualTo(expected);
        assertThat(bitmapPool.isAllocated(expected)).as("isAllocated after").isTrue();
    }

    @Test
    @Parameters(method = "poolConfigurationsNonTrivial")
    public void testAutoAllocateFromOffset(PoolConfiguration poolConfiguration)
        throws Exception
    {
        BitmapPool bitmapPool = new BitmapPool(poolConfiguration.getSize());

        bitmapPool.allocate(poolConfiguration.getOffset());

        int expected = poolConfiguration.getOffset() + 1;
        assertThat(bitmapPool.isAllocated(expected)).as("isAllocated before").isFalse();

        int allocated = bitmapPool.autoAllocateFromOffset(
            poolConfiguration.getRangeStart(),
            poolConfiguration.getRangeEnd(),
            poolConfiguration.getOffset()
        );

        assertThat(allocated).isEqualTo(expected);
        assertThat(bitmapPool.isAllocated(expected)).as("isAllocated after").isTrue();
    }


    @Test
    @Parameters(method = "poolConfigurations")
    public void testClear(PoolConfiguration poolConfiguration)
    {
        BitmapPool bitmapPool = new BitmapPool(poolConfiguration.getSize());

        bitmapPool.allocateAll(
            generateNumberRange(poolConfiguration.getRangeStart(), poolConfiguration.getRangeEnd()));

        bitmapPool.clear();

        for (int nr = poolConfiguration.getRangeStart(); nr <= poolConfiguration.getRangeEnd(); nr++)
        {
            assertThat(bitmapPool.isAllocated(nr))
                .as("isAllocated 0x%x", nr).isFalse();
        }
    }

    private List<PoolConfiguration> poolConfigurations()
    {
        return Arrays.asList(
            new PoolConfiguration(1, 0, 0, 0),
            new PoolConfiguration(2, 0, 0, 0),

            // Around size of 1 level
            new PoolConfiguration(0xfff, 0, 0xfff - 1, 0x567),
            new PoolConfiguration(0x1000, 0, 0x1000 - 1, 0x567),
            new PoolConfiguration(0x1001, 0, 0x1001 - 1, 0x567),

            // Around size of 2 levels
            new PoolConfiguration(0x3ffff, 0x20000, 0x30000 - 1, 0x28372),
            new PoolConfiguration(0x40000, 0x20000, 0x30000 - 1, 0x28372),
            new PoolConfiguration(0x40001, 0x20000, 0x30000 - 1, 0x28372),

            // Around size of 3 levels
            new PoolConfiguration(0xffffff, 0x520000, 0x530000 - 1, 0x522019),
            new PoolConfiguration(0x1000000, 0x520000, 0x530000 - 1, 0x522019),
            new PoolConfiguration(0x1000001, 0x520000, 0x530000 - 1, 0x522019),

            // Up to size of 4 levels
            new PoolConfiguration(0x3fffffff, 0x5020000, 0x5030000 - 1, 0x5023333),
            new PoolConfiguration(0x40000000, 0x5020000, 0x5030000 - 1, 0x5023333),

            // Size like the TCP port numbers pool
            new PoolConfiguration(0x10000, 0, 0x10000 - 1, 0x6283),

            // Size like the minor numbers pool
            new PoolConfiguration(0x100000, 0x80000, 0x81000 - 1, 0x80982)
        );
    }

    @SuppressWarnings("unused")
    private List<PoolConfiguration> poolConfigurationsNonTrivial()
    {
        List<PoolConfiguration> poolConfigurations = new ArrayList<>();

        for (PoolConfiguration poolConfiguration : poolConfigurations())
        {
            if (poolConfiguration.getRangeEnd() != poolConfiguration.getRangeStart())
            {
                poolConfigurations.add(poolConfiguration);
            }
        }

        return poolConfigurations;
    }

    private int[] generateNumberRange(int rangeStart, int rangeEndInclusive)
    {
        int rangeSize = rangeEndInclusive - rangeStart + 1;
        int[] numbers = new int[rangeSize];
        for (int i = 0; i < rangeSize; i++)
        {
            numbers[i] = rangeStart + i;
        }
        return numbers;
    }

    private List<Integer> generateNumberRangeList(int rangeStart, int rangeEndInclusive)
    {
        int[] numbers = generateNumberRange(rangeStart, rangeEndInclusive);
        List<Integer> numberList = new ArrayList<>(numbers.length);
        for (int number : numbers)
        {
            numberList.add(number);
        }
        return numberList;
    }

    private static class PoolConfiguration
    {
        private final int size;
        private final int rangeStart;
        private final int rangeEnd;
        private final int offset;

        public PoolConfiguration(final int size, final int rangeStart, final int rangeEnd, final int offset)
        {
            this.size = size;
            this.rangeStart = rangeStart;
            this.rangeEnd = rangeEnd;
            this.offset = offset;
        }

        public int getSize()
        {
            return size;
        }

        public int getRangeStart()
        {
            return rangeStart;
        }

        public int getRangeEnd()
        {
            return rangeEnd;
        }

        public int getOffset()
        {
            return offset;
        }

        @Override
        public String toString()
        {
            return String.format("Pool: (Size: 0x%x, Range: 0x%x-0x%x, Offset: 0x%x)",
                size, rangeStart, rangeEnd, offset);
        }
    }
}
