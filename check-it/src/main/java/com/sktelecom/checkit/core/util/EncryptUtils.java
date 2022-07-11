package com.sktelecom.checkit.core.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * AES128 방식을 이용한 대칭키 암호화 함수
 * 2018-08-05
 * @author devkimsj
 *
 */
public class EncryptUtils {

	private static final String DEFAULT_KEY = "$gold%moon#fox$";
	private final static Log log = LogFactory.getLog(EncryptUtils.class);

	public static EncryptUtils rsa_instance = null;

	public static EncryptUtils getInstance() {
		if (rsa_instance == null) {
			synchronized (EncryptUtils.class) {
				if (rsa_instance == null)
					rsa_instance = new EncryptUtils();
			}
		}
		return rsa_instance;
	}

	private static SecretKeySpec getScretKeySpec(final String key) throws NoSuchAlgorithmException {
		byte[] seed = key.getBytes();
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(seed);

		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(128, random);

		SecretKey secretKey = generator.generateKey();
		return new SecretKeySpec(secretKey.getEncoded(), "AES");
	}

	public static String encryptAES128(final String text) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec keySpec = getScretKeySpec(DEFAULT_KEY);

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		return Hex.encodeHexString(cipher.doFinal(text.getBytes()));
	}

	public static String encryptAES128(final String key, final String text) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec keySpec = getScretKeySpec(key);

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		return Hex.encodeHexString(cipher.doFinal(text.getBytes()));
	}

	public static String decriptAES128(final String text) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, DecoderException {
		SecretKeySpec keySpec = getScretKeySpec(DEFAULT_KEY);

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, keySpec);
		return new String(cipher.doFinal(Hex.decodeHex(text.toCharArray())));
	}

	public static String decriptAES128(final String key, final String text) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, DecoderException {
		SecretKeySpec keySpec = getScretKeySpec(key);

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, keySpec);
		return new String(cipher.doFinal(Hex.decodeHex(text.toCharArray())));
	}

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, DecoderException {
		String plainText = "test-some-plain-text-!@#$";
		String password = "himart$2015!@";

		String encrypted = EncryptUtils.encryptAES128(password, plainText);
		System.out.println("encrypted: " + encrypted); // 생성 결과: e02504564589effb9aa18872fd103d3bf6f4966525383d26c7bff56d45c6f02a

		String decrypted = EncryptUtils.decriptAES128(password, encrypted);
		System.out.println("decrypted: " + decrypted);
		
		EncryptUtils.getHash("mobigen1234");
		System.out.println(EncryptUtils.getHash("mobigen1234"));
		
		
		System.out.println(EncryptUtils.getHash128("sdy1051|2021-08-31|03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4"));
		
	}

	/**
     * RSA 암호화 로그인
     */
    public static final int KEY_SIZE = 1024;

    public void RSA_Encrypt(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(KEY_SIZE);

            KeyPair keyPair = generator.genKeyPair();
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            HttpSession session = request.getSession();
            // 세션에 공개키의 문자열을 키로하여 개인키를 저장한다.
            session.setAttribute("__rsaPrivateKey__", privateKey);

            // 공개키를 문자열로 변환하여 JavaScript RSA 라이브러리 넘겨준다.
            RSAPublicKeySpec publicSpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);

            String publicKeyModulus = publicSpec.getModulus().toString(16);
            String publicKeyExponent = publicSpec.getPublicExponent().toString(16);

            session.setAttribute("publicKeyModulus", publicKeyModulus);
            session.setAttribute("publicKeyExponent", publicKeyExponent);

            //log.debug("__rsaPrivateKey__ ======> " + privateKey);
            //log.debug("publicKeyModulus =====> " + publicKeyModulus);
            //log.debug("publicKeyExponent =====> " + publicKeyExponent);
            log.debug("############### [RSA Key Created] ###############");
        } catch (Exception ex) {
            throw new ServletException(ex.getMessage(), ex);
        }
    }

	/**
     * 암호화된 비밀번호를 복호화 한다.
     */
    public HashMap<String, String> RSA_Decrypt(HttpServletRequest request, HttpServletResponse response, String userId, String passwd) throws ServletException, IOException {
        HttpSession session = request.getSession();
        PrivateKey privateKey = (PrivateKey) session.getAttribute("__rsaPrivateKey__");
        // 키의 재사용을 막는다. 항상 새로운 키를 받도록 처리하였다.
        session.removeAttribute("__rsaPrivateKey__");
        session.removeAttribute("publicKeyModulus");
        session.removeAttribute("publicKeyExponent");
        log.debug("############### [RSA Key Deleted] ###############");

        if (privateKey == null) {
            throw new RuntimeException("암호화 비밀키 정보를 찾을 수 없습니다.");
        }
        HashMap<String, String> rsa_hash = new HashMap<String, String>();
        try {
        	String username = decryptRsa(privateKey, userId);
            String password = decryptRsa(privateKey, passwd);
        	rsa_hash.put("userId", username);
        	rsa_hash.put("passwd", password);
        } catch (Exception ex) {
            throw new ServletException(ex.getMessage(), ex);
        }
        return rsa_hash;
    }

    private String decryptRsa(PrivateKey privateKey, String securedValue) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        byte[] encryptedBytes = hexToByteArray(securedValue);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedValue = new String(decryptedBytes, "euc-kr");
        return decryptedValue;
    }

    public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() % 2 != 0) {
            return new byte[]{};
        }

        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            byte value = (byte)Integer.parseInt(hex.substring(i, i + 2), 16);
            bytes[(int) Math.floor(i / 2)] = value;
        }
        return bytes;
    }

    public static String base64Encode(byte[] data) throws Exception {
        Encoder encoder = Base64.getEncoder();
        String encoded = String.valueOf(encoder.encode(data));
        return encoded;
    }

    public static byte[] base64Decode(String encryptedData) throws Exception {
        Decoder decoder = Base64.getDecoder();
        byte[] decoded = decoder.decode(encryptedData);
        return decoded;
    }

    /**
     * SHA256 암호화
     * @param iterationNb
     * @param password
     * @param salt
     * @return 암호화된 값
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
	public static String getHash(String str) {

		MessageDigest md;
		String SHA = "";
		try {
			if(str != null && str != "" && str.length()<64){
				md = MessageDigest.getInstance("SHA-256");

				md.update(str.getBytes());

				byte byteData[] = md.digest();

				StringBuffer hexString = new StringBuffer();

				for (int i=0;i<byteData.length;i++) {
					String hex=Integer.toHexString(0xff & byteData[i]);
					if(hex.length()==1) hexString.append("0");
					hexString.append(hex);
				}

				SHA = hexString.toString();

			}else{
				SHA = str;
			}

        } catch (NoSuchAlgorithmException e){
        	log.error(e);
        }

		return SHA;
	}
	
	/**
     * SHA256 암호화
     * @param iterationNb
     * @param password
     * @param salt
     * @return 암호화된 값
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
	public static String getHash128(String str) {

		MessageDigest md;
		String SHA = "";
		try {
			if(str != null && str != "" && str.length()<128){
				md = MessageDigest.getInstance("SHA-256");

				md.update(str.getBytes());

				byte byteData[] = md.digest();

				StringBuffer hexString = new StringBuffer();

				for (int i=0;i<byteData.length;i++) {
					String hex=Integer.toHexString(0xff & byteData[i]);
					if(hex.length()==1) hexString.append("0");
					hexString.append(hex);
				}

				SHA = hexString.toString();

			}else{
				SHA = str;
			}

        } catch (NoSuchAlgorithmException e){
        	log.error(e);
        }

		return SHA;
	}
	
	/**
	 * server side 패스워드 검증
	 * @param pwd
	 * @return
	 */
	public static boolean chkPatternPW(String pwd){
		
		String regx1 = "^(?=.*[A-Za-z])(?=.*[0-9])(?=\\S+$).{10,20}$"; // 문자, 숫자, 공백(없어야함), 10~20
		String regx2 = "^(?=.*[0-9])(?=.*\\W)(?=\\S+$).{10,20}$"; // 숫자, 특수문자, 공백(없어야함), 10~20
		String regx3 = "^(?=.*[A-Za-z])(?=.*\\W)(?=\\S+$).{10,20}$"; // 문자, 특수문자, 공백(없어야함), 10~20
		
		if(Pattern.matches(regx1, pwd)){
			return true;
		}
		if(Pattern.matches(regx2, pwd)){
			return true;
		}
		if(Pattern.matches(regx3, pwd)){
			return true;
		}
		
		return false;
	}

}
