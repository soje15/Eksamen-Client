package encrypter;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;



/**
 *
 * EncryptionHandler used for hashing aswell as encryption
 *
 * Credit goes to the entirety of the "Undervisnings-evaluerings" team.
 */
public class EncryptionHandler {

    private final static String KEY = "40674244454045cb9a70040a30e1c007";

    /**
     * Method to encrypt string
     * @param s
     * @return
     */
    public static String encrypt(String s) {

        String encrypted_string = s;

            encrypted_string = base64Encode(xorWithKey(encrypted_string.getBytes(), KEY.getBytes()));

        return encrypted_string;
    }

    /**
     * Method to decrypt string
     * @param s
     * @return
     */
    public static String decrypt(String s) {
        String decrypted_string = s;

            decrypted_string = new String(xorWithKey(base64Decode(s), KEY.getBytes()));

        return decrypted_string;
    }

    /**
     * Encryption using xor Algorithm
     * @param a
     * @param key
     * @return
     */
    private static byte[] xorWithKey(byte[] a, byte[] key) {
        byte[] out = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            out[i] = (byte) (a[i] ^ key[i%key.length]);
        }
        return out;
    }

    //Metode til at dekryptering
    private static byte[] base64Decode(String s) {
        try {
            BASE64Decoder d = new BASE64Decoder();
            return d.decodeBuffer(s);
        } catch (IOException e) {throw new RuntimeException(e);}
    }

    //metode til kryptering
    private static String base64Encode(byte[] bytes) {
        BASE64Encoder enc = new BASE64Encoder();
        return enc.encode(bytes).replaceAll("\\s", "");

    }
}