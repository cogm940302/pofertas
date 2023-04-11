package com.mit.controller;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class Main {

	public static void main(String[] args) {
		try {
			
			KeyPairGenerator kpg = null;
			try {
				kpg = KeyPairGenerator.getInstance("RSA");
				kpg.initialize(2048);
			} catch (NoSuchAlgorithmException e) {
				new IllegalStateException(e.getMessage());
			}

			KeyPair kpar = kpg.generateKeyPair();
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

}
