package org.waltz4line.rocksdb.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class EndianUtilTest {

    private static final Random random = new Random();
    @Test
    public void long_변환_테스트() {
        for (int i = 0; i < 100000; i++) {
            long testNum = random.nextLong();
            long expected = testNum & 0x0000FFFFFFFFFFFFL;
            byte[] longToBytes = EndianUtil.long2Bytes(testNum);
            byte[] bounded = EndianUtil.long2Bytes(expected);
            Assertions.assertEquals(testNum, EndianUtil.toInt64(longToBytes, 0, 8));
            Assertions.assertEquals(expected, EndianUtil.toInt64(bounded, 2, 6));
        }
    }

    @Test
    public void bytes_변환_테스트() {
        for (int i = 0; i < 1000000; i++) {
            long expected = random.nextLong() & 0x0000FFFFFFFFFFFFL;
            byte[] longToBytes = EndianUtil.long2Bytes(expected);
            byte[] result = new byte[8];
            EndianUtil.partialLongToBytes(result, expected, 2, 8);
            Assertions.assertArrayEquals(longToBytes, result);
        }
    }

}
