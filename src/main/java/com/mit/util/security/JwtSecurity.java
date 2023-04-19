package com.mit.util.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.shaded.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.util.*;

@Component
public class JwtSecurity implements JwtSecured{

    @Value("")
    private Resource resource;

    private RSAPrivateKey privateKey;

    private static final String PB =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvMXB6O0ekIKYQP6TKTf6" +
            "1Q0+Xef/BOLDSkcma7qZ7ZD05l3juzEcQDTiwJUbWsk/LZ7tt3JtejGofKK6imrr" +
            "v2p7TVILwrn/JtSHPvDyB7UcKlD3gNgqTadfjcpgj0ATNLkPTHVWAfzy1CL1Ek7a" +
            "fLu8fFzYlS1ndk42XHC5ql6CHRoZ/fQ/BPvsm6C27F+q+1wYY9hi/j1wHO9ZGFY2" +
            "Zt09/66DxkMxfKaeNqXnifkbCWDvTRH4g6vQcBU2TuWyluGL4MCCWqqIrTc61nPc" +
            "reLe6b1R8FZ8CR8qMcscquZY2PieIgg6mBLjy3u07pQKTUcqVW4MQ3Q4vwInUIVz" +
            "vQIDAQAB";

    private static final JWEHeader HEADER = new JWEHeader(JWEAlgorithm.RSA_OAEP_256,EncryptionMethod.A128GCM);

    private static final Logger LOGGER = LogManager.getLogger(JwtSecurity.class.getName());

    @Override
    public Optional<Boolean> verifyJWT(JsonObject jsonObject) {
        return Optional.empty();
    }

    @Override
    public Optional<String> jwtGeneratorPlain(Map<String, Object> dataEncrypt, String keyPublic) {
        JWSObject jwt =  null;
        try {
           Date date = new Date();
            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).keyID("mit").build();
            dataEncrypt.put("sub",1234);
            dataEncrypt.put("aud","mittest");
            dataEncrypt.put("exp",new Date(date.getTime()+ 1000*60*60));
            dataEncrypt.put("iat",date.getTime());
            dataEncrypt.put("iss","mittest");
            Map<String,Object> otherData = new HashMap<>();
            otherData.put("pan",dataEncrypt.get("pan"));
            dataEncrypt.remove("pan");
            dataEncrypt.put("other_data",otherData);
             Payload payload = new Payload(dataEncrypt);
            jwt=new JWSObject(header,payload);
            KeyPairGenerator kpg = null;
                kpg = KeyPairGenerator.getInstance("RSA");
                kpg.initialize(2048);
            JWSSigner signer = new RSASSASigner(kpg.generateKeyPair().getPrivate());
            jwt.sign(signer);
            System.out.println(jwt.serialize());

           /*

           JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .claim("Tarjeta de credito",dataEncrypt.get("Tarjeta de credito"))
                    .notBeforeTime(date)
                    .expirationTime(new Date(date.getTime()+ 1000*60*60))
                    .jwtID(UUID.randomUUID().toString())
                    .issueTime(date)
                    .audience("")
                    .jwtID("")
                    .subject("")
                    .issuer("http://localhost:8080")se
                    .build();

            RSAEncrypter encrypt = new RSAEncrypter((RSAPublicKey) KeyFactory
                    .getInstance("RSA").generatePublic(spec));
            jwt = new EncryptedJWT(HEADER,claims);
            jwt.encrypt(encrypt);*/
        }catch (Exception ex){
            LOGGER.info(ex.getMessage(),ex);
        }
        return Optional.ofNullable(Objects.isNull(jwt) ? null : jwt.serialize());
    }

    @Override
    public <T> T jwtGeneratorCustom(Map<String, Object> dataEncrypt, String keyPublic, Class<T> instance) {
        return null;
    }

    @Override
    public Optional<Map<String, Object>> jwtDecryptData(JsonObject jsonObject) {
        return Optional.empty();
    }
}
