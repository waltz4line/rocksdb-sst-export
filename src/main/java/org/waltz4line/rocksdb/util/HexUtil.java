package org.waltz4line.rocksdb.util;

public class HexUtil {

    /**
     * output as Hex
     */
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F'};

    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f'};

    /**
     * Convert the byte array to HexString
     */
    public static String toHexString(byte[] bytes) {
        return toHexString(bytes, 0, bytes.length);
    }

    public static String toHexString(byte[] bytes, int offset, int len) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        int endIdx = offset + len;
        if (bytes.length < endIdx) {
            return "";
        }

        StringBuilder sb = new StringBuilder(len << 1);

        try {
            for (int i = offset; i < endIdx; i++) {
                sb.append(DIGITS[(0xF0 & bytes[i]) >>> 4]).append(DIGITS[0x0F & bytes[i]]);
            }

            return sb.toString();
        } finally {
            sb.setLength(0);
        }
    }

    public static String toHexString(byte[] bytes, boolean lowercase) {
        return toHexString(bytes, 0, bytes.length, lowercase);
    }

    public static String toHexString(byte[] bytes, int offset, int len, boolean lowercase) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        int endIdx = offset + len;
        if (bytes.length < endIdx) {
            return "";
        }

        StringBuilder sb = new StringBuilder(len << 1);

        try {

            char[] digits = lowercase ? DIGITS_LOWER : DIGITS;

            for (int i = offset; i < endIdx; i++) {
                sb.append(digits[(0xF0 & bytes[i]) >>> 4]).append(digits[0x0F & bytes[i]]);
            }

            return sb.toString();
        } finally {
            sb.setLength(0);
        }
    }

    /**
     * Convert the byte to HexString
     */
    public static String toHexString(byte b) {

        char[] digits = new char[] { DIGITS[(0xF0 & b) >>> 4], DIGITS[0x0F & b] };

        return new String(digits);
    }

    /**
     * Convert the byte(4-bit) high or low to HexString
     */
    public static char toHexChar(byte b, boolean low) {

        if (low) {
            return DIGITS[0x0F & b];
        }
        return DIGITS[(0xF0 & b) >>> 4];
    }


    /**
     * Convert the short to HexString
     */
    public static String toHexString(short b) {

        char[] digits = new char[] { DIGITS[(0xF000 & b) >>> 12], DIGITS[(0x0F00 & b) >>> 8],
                DIGITS[(0x00F0 & b) >>> 4], DIGITS[(0x000F & b)] };

        return new String(digits);
    }

    /**
     * Convert the short to HexString
     */
    public static String toHexString(int b) {

        char[] digits = new char[] { DIGITS[(0xF0000000 & b) >>> 28], DIGITS[(0x0F000000 & b) >>> 24],
                DIGITS[(0x00F00000 & b) >>> 20], DIGITS[(0x000F0000 & b) >>> 16], DIGITS[(0x0000F000 & b) >>> 12],
                DIGITS[(0x00000F00 & b) >>> 8], DIGITS[(0x000000F0 & b) >>> 4], DIGITS[(0x0000000F & b)] };

        return new String(digits);
    }

    /**
     * Convert the short to HexString
     */
    public static String toHexString(long b) {

        char[] digits = new char[16];

        int shift = 0;
        for (int i = 15; i >= 0; i--) {
            digits[i] = DIGITS[(int) (b >>> shift) & 0x0F];
            shift += 4;
        }

        return new String(digits);
    }

    /**
     * Convert the HexString to byte array
     */
    public static byte[] fromHexString(final String hex) throws NumberFormatException {

        final int len = hex.length();

        if ((len & 0x01) != 0) {
            throw new NumberFormatException("Odd number of characters.");
        }

        final byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int cvt = toDigit(hex.charAt(j++)) << 4 | toDigit(hex.charAt(j++));
            out[i] = (byte) (cvt & 0xFF);
        }

        return out;
    }

    private static int toDigit(final char ch) throws NumberFormatException {
        final int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new NumberFormatException("Illegal hexadecimal character " + ch);
        }
        return digit;
    }

}
