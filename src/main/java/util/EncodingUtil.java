package util;

public class EncodingUtil {
    public static String encodeString(String content, int offset) {
        char[] contentChars  = content.toCharArray();
        char[] encodedString = new char[contentChars.length];
        for (int i = 0; i < contentChars.length; i++) {
            if (contentChars[i] != ' ') {
                encodedString[i] = (char) ((int) contentChars[i] + offset);
            } else {
                encodedString[i] = ' ';
            }
        }
        return new String(encodedString);
    }
}
