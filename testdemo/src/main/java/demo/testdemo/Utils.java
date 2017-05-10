package demo.testdemo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class Utils {

    public static String md5(String content) {

        MessageDigest digest;

        byte[] hash = null;

        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(content.getBytes());
            hash = digest.digest(content.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

        return bytes2HexString(hash);
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytes2HexString(byte[] bytes) {

        char[] hexChars = new char[bytes.length * 2];

        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }

        return new String(hexChars);
    }

    public static String encode(String content) {

        if (content == null) return "";

        try {
            return URLEncoder.encode(content, "utf-8").toLowerCase();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return content;
        }

    }

    public static String calcHmac(String src) {

        String key = "eade56028e252b77f7a0b8792e58b9cc";

        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec sk = new SecretKeySpec(key.getBytes(),mac.getAlgorithm());
            mac.init(sk);
            byte[] result = mac.doFinal(src.getBytes());
            return Utils.bytes2HexString(result);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }
}
