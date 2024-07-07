package org.waltz4line.rocksdb.util;

public class EndianUtil {

    public static byte[] short2Bytes(short num) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) ((num >> 8) & 0xFF);
        bytes[1] = (byte) (num & 0xFF);
        return bytes;
    }

    public static byte[] int2Bytes(int num) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) ((num >> 24) & 0xFF);
        bytes[1] = (byte) ((num >> 16) & 0xFF);
        bytes[2] = (byte) ((num >> 8) & 0xFF);
        bytes[3] = (byte) (num & 0xFF);
        return bytes;
    }

    public static byte[] int2Bytes(byte[] output, int offset, long num) {
        output[offset++] = (byte) ((num >> 24) & 0xFF);
        output[offset++] = (byte) ((num >> 16) & 0xFF);
        output[offset++] = (byte) ((num >> 8) & 0xFF);
        output[offset] = (byte) (num & 0xFF);
        return output;
    }

    public static byte[] long2Bytes(long num) {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) ((num >> 56) & 0xFF);
        bytes[1] = (byte) ((num >> 48) & 0xFF);
        bytes[2] = (byte) ((num >> 40) & 0xFF);
        bytes[3] = (byte) ((num >> 32) & 0xFF);
        bytes[4] = (byte) ((num >> 24) & 0xFF);
        bytes[5] = (byte) ((num >> 16) & 0xFF);
        bytes[6] = (byte) ((num >> 8) & 0xFF);
        bytes[7] = (byte) (num & 0xFF);
        return bytes;
    }

    public static void long2Bytes(byte[] output, int offset, long num) {
        output[offset++] = (byte) ((num >> 56) & 0xFF);
        output[offset++] = (byte) ((num >> 48) & 0xFF);
        output[offset++] = (byte) ((num >> 40) & 0xFF);
        output[offset++] = (byte) ((num >> 32) & 0xFF);
        output[offset++] = (byte) ((num >> 24) & 0xFF);
        output[offset++] = (byte) ((num >> 16) & 0xFF);
        output[offset++] = (byte) ((num >> 8) & 0xFF);
        output[offset] = (byte) (num & 0xFF);
    }

    public static void partialLongToBytes(byte[] output, long num, int offset, int endOffset) {
        long remainNum = num;
        for (int i = endOffset - 1; i >= offset; i--) {
            output[i] = (byte) (remainNum & 0xFF);
            remainNum >>= 8;
        }
    }

    public static int toBit8(byte[] b, int offset, int position) {
        if (position < 0 || position > 7) {
            throw new IllegalArgumentException("Position must be between 0 and 7.");
        }

        byte tmp = b[offset];
        return ((tmp >> position) & 0x01);
    }

    public static int toInt8(byte[] b, int offset) {
        return b[offset];
    }

    public static int toUInt8(byte[] b, int offset) {
        return b[offset] & 0xFF;
    }

    public static int toInt16(byte[] b, int offset) {
        byte low = b[offset];
        byte high = b[offset + 1];

        return (short) ((low << 8) & 0xFF00 | high & 0xFF);
    }

    public static int toUInt16(byte[] b, int offset) {
        byte low = b[offset];
        byte high = b[offset + 1];

        return (low << 8) & 0xFF00 | high & 0xFF;
    }

    public static int toInt32(byte[] b) {
        return toInt32(b, 0);
    }

    public static int toInt32(byte[] b, int offset) {
        byte b0 = b[offset];
        byte b1 = b[offset + 1];
        byte b2 = b[offset + 2];
        byte b3 = b[offset + 3];

        return ((b0 << 24) & 0xFF000000 | (b1 << 16) & 0xFF0000 | (b2 << 8) & 0xFF00 | b3 & 0xFF);
    }

    public static long toUInt32(byte[] b, int offset) {
        byte b0 = b[offset];
        byte b1 = b[offset + 1];
        byte b2 = b[offset + 2];
        byte b3 = b[offset + 3];

        return (b0 << 24) & 0xFF000000L | (b1 << 16) & 0xFF0000L | (b2 << 8) & 0xFF00L | b3 & 0xFFL;
    }

    public static long toInt64(byte[] b, int offset) {
        final long low = toInt32(b, offset);
        final long high = toInt32(b, offset + 4);
        return (low << 32) + (0xFFFFFFFFL & high);
    }

    public static long toInt64(byte[] b, int offset, int length) {
        if (offset < 0 || length > 8) {
            throw new IllegalArgumentException("check offset: " + offset + " or length:" + length);
        }
        long result = 0;
        for (int i = 0; i < length; i++) {
            result <<= 8;
            result |= (b[offset + i] & 0xFF);
        }
        return result;
    }

    public static int toBCD16(byte[] b, int offset) {
        int bcd = 0;
        int multiplier = 1000;
        int divider = 1;
        for (int i = 0; i < 2; i++) {
            byte one = b[offset + i];
            bcd += ((one >> 4) & 0x0F) * (multiplier / divider);
            divider *= 10;
            bcd += (one & 0x0F) * (multiplier / divider);
            divider *= 10;
        }

        return bcd;
    }

    public static int toBCD32(byte[] b, int offset) {
        int bcd = 0;
        int multiplier = 10000000;
        int divider = 1;
        for (int i = 0; i < 4; i++) {
            byte one = b[offset + i];
            bcd += ((one >> 4) & 0x0F) * (multiplier / divider);
            divider *= 10;
            bcd += (one & 0x0F) * (multiplier / divider);
            divider *= 10;
        }


        return bcd;
    }
}
