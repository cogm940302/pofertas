package com.mit.controller;

import java.awt.geom.IllegalPathStateException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

import com.mit.util.security.JwtSecurity;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Main {

	private static final String ALGORITMO = "RSA";

	private static final short LONGITUD = 2048;

	private static final String ENCODING = "UTF-8";

	public static final String MODE = "ECB";

	public static final String PKCS1_PADDING = "PKCS1Padding";

	public static final String OAEP_PADDING = "OAEPWITHSHA-256ANDMGF1PADDING";

	public static final String DEFAULT_PADDING=PKCS1_PADDING;

	private static final String PB =
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvMXB6O0ekIKYQP6TKTf6" +
					"1Q0+Xef/BOLDSkcma7qZ7ZD05l3juzEcQDTiwJUbWsk/LZ7tt3JtejGofKK6imrr" +
					"v2p7TVILwrn/JtSHPvDyB7UcKlD3gNgqTadfjcpgj0ATNLkPTHVWAfzy1CL1Ek7a" +
					"fLu8fFzYlS1ndk42XHC5ql6CHRoZ/fQ/BPvsm6C27F+q+1wYY9hi/j1wHO9ZGFY2" +
					"Zt09/66DxkMxfKaeNqXnifkbCWDvTRH4g6vQcBU2TuWyluGL4MCCWqqIrTc61nPc" +
					"reLe6b1R8FZ8CR8qMcscquZY2PieIgg6mBLjy3u07pQKTUcqVW4MQ3Q4vwInUIVz" +
					"vQIDAQAB";

	public static void main(String[] args) {
		try {
			JwtSecurity jwtSecurity = new JwtSecurity();
			Map<String,Object> data = new HashMap<>();
			data.put("Tarjeta de credito", "12345677890");
			Optional<String> jwt=jwtSecurity.jwtGeneratorPlain(data,"");
			System.out.println(jwt);
			KeyPairGenerator kpg = null;
			try {
				kpg = KeyPairGenerator.getInstance("RSA");
				kpg.initialize(2048);
			} catch (NoSuchAlgorithmException e) {
				new IllegalStateException(e.getMessage());
			}

			KeyPair kpar = kpg.generateKeyPair();
			System.out.println("------------\n");
			System.out.println(cifrar("hola santander",PKCS1_PADDING));
			System.out.println("------------\n");


			String publicKeyPEM = "";

			byte[] encoded = org.apache.commons.codec.binary.Base64.decodeBase64(publicKeyPEM);

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);

			//return (RSAPublicKey) keyFactory.generatePublic(keySpec);
//		RSAKey rsaJWK = new RSAKeyGenerator(2048)
//			    .keyID("123")
//			    .generate();
//			RSAKey rsaPublicJWK = rsaJWK.toPublicJWK();
//			PrivateKey rsaPrivateJWK = rsaJWK.toRSAPrivateKey();

			// Create RSA-signer with the private key
//			JWSSigner signer = new RSASSASigner(rsaJWK);
			System.out.println(Base64.getEncoder().encodeToString( kpar.getPrivate().getEncoded() ));
			System.out.println(Base64.getEncoder().encodeToString(kpar.getPublic().getEncoded() )  );
			JWSSigner signer = new RSASSASigner(kpar.getPrivate());

			// Prepare JWT with claims set
			JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
					.subject("mit")
					.issuer("mitec.com.mx")
					.claim("Tarjeta de credito", "12345677890")
					.expirationTime(new Date(new Date().getTime() + 60 * 1000))
					.build();

//			SignedJWT signedJWT = new SignedJWT(
//			    new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaJWK.getKeyID()).build(),
//			    claimsSet);
			SignedJWT signedJWT = new SignedJWT(
					new JWSHeader.Builder(JWSAlgorithm.RS256).keyID("miguel").build(),
					claimsSet);

			// Compute the RSA signature
			signedJWT.sign(signer);

			// To serialize to compact form, produces something like
			// eyJhbGciOiJSUzI1NiJ9.SW4gUlNBIHdlIHRydXN0IQ.IRMQENi4nJyp4er2L
			// mZq3ivwoAjqa1uUkSBKFIX7ATndFF5ivnt-m8uApHO4kfIFOrW7w2Ezmlg3Qd
			// maXlS9DhN0nUk_hGI3amEjkKd0BWYCB8vfUbUv0XGjQip78AI4z1PrFRNidm7
			// -jPDm5Iq0SZnjKjCNS5Q15fokXZc8u0A
			String s = signedJWT.serialize();
			System.out.println("la s: " + s);
			// On the consumer side, parse the JWS and verify its RSA signature
			signedJWT = SignedJWT.parse(s);

//			JWSVerifier verifier = new RSASSAVerifier(rsaPublicJWK);
		}catch(Exception e) {
			e.getStackTrace();
		}
	}


	public static String cifrar(String clearText, String padding) {
		if (clearText == null) {

			throw new IllegalArgumentException("clearText es nulo");

		}

		byte[] cifrado = null;
		String rsaMode = loadTransformation(MODE, padding);

		Cipher cipher;

		try {

			byte[] encoded = org.apache.commons.codec.binary.Base64.decodeBase64(PB);

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
			cipher = Cipher.getInstance(rsaMode);

			cipher.init(Cipher.ENCRYPT_MODE, (RSAPublicKey) keyFactory.generatePublic(keySpec));

			cifrado = cipher.doFinal(clearText.getBytes(ENCODING));

		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException

				 | UnsupportedEncodingException e) {

			throw new IllegalPathStateException(e.getMessage());

		} catch (BadPaddingException e) {


		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
		return org.apache.commons.codec.binary.Base64.encodeBase64String(cifrado);

	}

	private static String loadTransformation(String mode, String padding) {

		String transformation = ALGORITMO;

		transformation += mode == null ? MODE : "/" + mode;

		transformation += padding == null ? "/" + DEFAULT_PADDING : "/" + padding;

		return transformation;

	}

}
