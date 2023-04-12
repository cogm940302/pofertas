package com.mit.commons.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AESEncriptacion {
	private static final String ALGORITMO = "AES";
	private static final int LONGITUD = 128;

	public static final String MODE = "CBC";
	public static final String PADDING = "PKCS5PADDING";

	private static final Logger log = LogManager.getLogger();

	private final String a;
	private final String b;

	public AESEncriptacion(String a, String b) {
		this.a = a;
		this.b = b;
	}

	public static String generaKey() throws NoSuchAlgorithmException {
		return generaKey(LONGITUD);
	}

	public static String generaKey(int longitud) throws NoSuchAlgorithmException {
		KeyGenerator kgen = KeyGenerator.getInstance(ALGORITMO);
		kgen.init(longitud);
		SecretKey skey = kgen.generateKey();
		return Hex.encodeHexString(skey.getEncoded());
	}

	public static String cifrar(String cadena, String llave, BinaryEncoder resultEncoder)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, DecoderException {

		byte[] raw = Hex.decodeHex(llave.toCharArray());
		String result = null;
		try {
			result = cifrar(cadena, raw, MODE, PADDING, resultEncoder);
		} catch (IOException | EncoderException e) {
			log.error(e, e);
		}
		return result;
	}

	public static String cifrar(String cadena, byte[] llave, String mode, String padding, BinaryEncoder encoder)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, IOException, EncoderException {

		SecretKeySpec skeySpec = new SecretKeySpec(llave, ALGORITMO);

		String transformation = loadTransformation(mode, padding);

		Cipher cipher = Cipher.getInstance(transformation);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(cadena.getBytes(StandardCharsets.UTF_8));

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		if (cipher.getIV() != null) {
			outputStream.write(cipher.getIV());
		}
		outputStream.write(encrypted);
		encrypted = outputStream.toByteArray();
		outputStream.close();

		encrypted = encoder.encode(encrypted);
		return new String(encrypted);
	}

	public String cifrar(String cadena) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, IOException, EncoderException {

		byte[] key = obtenerLLave();
		return cifrar(cadena, key, MODE, null, new Base64());
	}

	public static String descifrar(String encriptado, String llave, BinaryDecoder textDecoder)
			throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException,
			NoSuchPaddingException, DecoderException {
		byte[] raw = Hex.decodeHex(llave.toCharArray());

		String originalString = null;
		try {
			originalString = descifrar(encriptado, raw, MODE, PADDING, textDecoder);
		} catch (InvalidAlgorithmParameterException e) {
			log.error(e, e);
		}
		return originalString;
	}

	public static String descifrar(String encriptado, byte[] llave, String mode, String padding, BinaryDecoder decoder)
			throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException,
			NoSuchPaddingException, DecoderException, InvalidAlgorithmParameterException {
		SecretKeySpec skeySpec = new SecretKeySpec(llave, ALGORITMO);

		String transformation = loadTransformation(mode, padding);

		byte[] initialData = decoder.decode(encriptado.getBytes(StandardCharsets.UTF_8));

		IvParameterSpec ivSpecs = null;
		if (MODE.equalsIgnoreCase(mode)) {
			byte[] iv = Arrays.copyOfRange(initialData, 0, 16);
			ivSpecs = new IvParameterSpec(iv);
			initialData = Arrays.copyOfRange(initialData, 16, initialData.length);
		}

		Cipher cipher = Cipher.getInstance(transformation);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpecs);
		byte[] original = cipher.doFinal(initialData);
		return new String(original, StandardCharsets.UTF_8);
	}

	private static String loadTransformation(String mode, String padding) {
		String transformation = ALGORITMO;
		transformation += mode == null ? "/ECB" : "/" + mode;
		transformation += padding == null ? "/PKCS5PADDING" : "/" + padding;
		return transformation;
	}

	private byte[] obtenerLLave() {
		if (a == null) {
			throw new IllegalStateException("Se esperaba a");
		}
		if (b == null) {
			throw new IllegalStateException("Se esperaba b");
		}
		byte[] raw = null;
		String llaveDecrypted = "";

		String keyA = leerficheros5(a);

		String keyB = leerficheros5(b);
		try {
			llaveDecrypted = desencriptarBytes(keyA, keyB);
			raw = Hex.decodeHex(llaveDecrypted.toCharArray());
		} catch (Exception e) {
			log.error(e, e);
		}

		return raw;
	}

	private String desencriptarBytes(String encriptado, String llave)
			throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException,
			NoSuchPaddingException, DecoderException, InvalidAlgorithmParameterException {

		byte[] raw = Hex.decodeHex(llave.toCharArray());

		String originalString = null;
		originalString = descifrar(encriptado, raw, MODE, null, new Hex());
		return originalString;
	}

	private String leerficheros5(String filename) {
		String salida = "";
		File file = new File(filename);
		try (FileInputStream fin = new FileInputStream(file);) {
			byte[] fileContent = new byte[(int) file.length()];
			int read = fin.read(fileContent);
			if (read == file.length()) {
				salida = new String(Hex.encodeHex(fileContent));
			}
		} catch (IOException ioe) {
			log.error(ioe, ioe);
		}
		return salida;
	}
}
