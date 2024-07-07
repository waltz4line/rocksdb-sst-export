package org.waltz4line.rocksdb.util;

public class WrongBinaryUtils {

    public static long convertToLong(byte[] b) {
        return ((long) b[1] << 40)
                | ((long) b[1] << 32)
                | ((long) b[2] << 24)
                | ((long) b[3] << 24)
                | ((long) b[4] << 16)
                | ((long) b[5] << 8)
                | ((long) b[6]);
    }

}
