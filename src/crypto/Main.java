/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

import java.security.Security;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author User
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        byte[] input;
        byte[] output;
        byte[] keyBytes = "12345678".getBytes(); // min of 8 characters
        byte[] ivBytes = "inpu".getBytes(); // min of 4 characters
        SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        Cipher cipher;
        byte[] cipherText;
        int ctLength;

        try {
            // Encrypt
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            input = "Sample Text".getBytes(); // message being encrypted
            System.out.println("Message being encrypted: \"" + new String(input) + "\"");
            cipher = Cipher.getInstance("DES/CTR/NoPadding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            cipherText = new byte[cipher.getOutputSize(input.length)];
            ctLength = cipher.update(input, 0, input.length, cipherText, 0);
            ctLength += cipher.doFinal(cipherText, ctLength);
            System.out.println("Ciphered: " + new String(cipherText));

            // Decrypt
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            output = new byte[cipher.getOutputSize(ctLength)];
            int outputLength = cipher.update(cipherText, 0, ctLength, output, 0);
            outputLength += cipher.doFinal(output, outputLength);
            System.out.println("Deciphered: " + new String(output));
        } catch (Exception e) {
            System.out.println("Algum erro ocorreu");
        }
    }
}
