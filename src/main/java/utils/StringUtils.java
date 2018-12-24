package utils;

import java.util.zip.CRC32;

public class StringUtils {
    public static String getCRC32Hash(final String stringToEncode) {
        CRC32 crc = new CRC32();
        crc.update(stringToEncode.getBytes());
        return String.valueOf(crc.getValue());
    }
}
