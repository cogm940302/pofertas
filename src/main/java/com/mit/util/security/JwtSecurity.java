package com.mit.util.security;

import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.shaded.gson.JsonObject;

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
    
    static String stringPrivateKey =  //"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCndL3ZfvYdB8TleS6f5p3L5SIRHcDIKAJ/z5KCGc7vjJGmkjzgQQelpfVm7NCsvc8oyftHyZMwsuic1FvfCdlRgS9je3W/GH7wx+nVYw05oKr2pDE1yzK88TSQY4aK3z8qwLO3JDnxo/yBa56689gZQC7UPRs2Mpu/vwgK6//OQ/zLPism2akmRDGP+yRJQQaEce4pR0yZ0uVsm5Z2O3gecU9xXJNbJjZ4w3Ug/p6aQWFb5g11zCPfnv5xgk7OAbpGs6UjZibueiuBEoxNeC/w3h5VU731f5y7Pborw2GkuxgFGxnaqqfyajZFblWc+p3AA6oySfPCLXEHOieikz0rAgMBAAECggEAWt7zF0/aZq6MnqpALu2Ku9a/weIPd46erJULwCYZMc1qTKYW+urPJ4kDvrLMGrF19YCGJ9KvWUH9EP38TPCfSad8+9NHdkfouukBb5as2pThsa4wNizqf7W0/JXFsR6svJqN69hwyTxgDitEm9cvcaRuxSXT2nlkI42a47JlwpwnKiMli5zMiUtW8DdNsaDfkJLpNYL4MKzaNizITixHGfcag3bQOJsIb6mntCU3Ji8tJQaKgF9ep5taT2MMgswaTrWaBxTTJlJzAaJPR7vZHUSwaonv0AVQSnZ4jsnGU2f+uFZ9l1Exf3VZ9f1nzUFL5Lwx3UUDIdkzcag28Pa9YQKBgQDwBkoxIpgAw7ee4a9EJl+s4JTeaojRxGy4dlagA79YHxsp2gLoYZLMH1LKWHSldq7O5CU8rX+gjOTXNIRd3MMv5JXzcq89gSuxDP/bOiM6W5MOVqJQ1Jsxh7QoJDZ1ECRRTMaNL3lpFLWVqY9R/bp+xm50JaSgQ/bUNI793DlI1wKBgQCymfocootd/0VsY56dAz81D/bKLYKT6jzk7bI+YUqIs7W1OMiyUkOfQHYdFEmCqM+A2EyF/FCNEAcahU+DUEHfY6SG9NGO6EynoRiWdLd0YVZdtpP7lTzBAa0x0o3lJK6aB9koKuYW7NvredA6+OAshZp2KUY4LS/dnaJRwpE/zQKBgQCAeH1hi0icsCUqL1XmPjvbhmFoItGUpXQjz4NSJm9WNWAOqJgB1agDBYkEsQ/OOJYcOtULgznar08VORDYRH9ctRbltS7C1s6R94lCx/1vEPDHLeu3I/TlL+Nn0OotAjTOJy/nBdAQRHtk3drOYeZeUMtx82wINBh7QJ6Alk07BQKBgGjSdjjYLXO9nsE+tpjwjuhVtkJy83BcyeCSoUp7T4Nr9fmmmiQOpCGTc78daQeGjELSsBbzP1OLVJ/8He6z6/VwRd/vUA7PyC2aHxQu7H0ho87cmX3O2RluInhbW2xhWxiMAqfQhbpuyaQqmkwCRY/b2gv/rXbLDozDN8FEsHsBAoGAbpJ1ODPscIHDF0iNZ/1eRrdYtKKGV+SYFU32QJpFqewzuPmub72pJYQAX+fZGiXUqZayk2Y3rmdHqA/nR6g18ixoKOXvP8WCie1QN8CxAnUohB+oLmRH4l9HcbhpTeUXuo22YnhXaEU4V90ybtJX7j4O9h6FmLs/NxKLeDbGuc8=";
     "MIIEowIBAAKCAQEA8V7CKAxlPI5HHzPa1RxurhDRHD4edZQnMqN2mDXLPZQafnPbx3LZ3QIbCpuuLVGpkAxODdUB92bujTGxwfVBRpyNQXu+cS13C2oc/aU9fjLZadhcwQbKtbPoOyQJruard9mv/RrG2P9FrwvW2wzXcCiN9d+9RNBJSGa12lVTwBGZw6tCQMShsh68TyjQekTslT0EuWRhAUHBX4tYdB5203miA0bMxjQ7cT924GyzoT9So4beBnvC/KpyDcNmL9l9jcytKgN0TEnZ7VMGB1K5P8RH6h6tvRiALvMR+xiNDnjsE58F4R2qKBIs4pErJBWi9JU1+6JEqXY5caCgsk1BJQIDAQABAoIBAF4y9iE9HTFx8Y3a5fev/f4O8B1OnQKUTGv14a3rqKMiruhU/Rws1ZvsW3NbaLGlIkDmnHvHetn8IPOXxmEE/3G4XWSGD8AJ12DsC95t477hx3oIh8KJjQk26lZSbTkGKzs+CPIFxj8f+SkkvLiGnXnXSfgAVtSJWWBZPXW1QVc+hTITktwo7tELflZyfOCw66YWUanWMm187swyNonhkd5C05p2j6Ddk4trHGdUcQXXoJXqJNDB0AOHISPFa9v2cu5Rf20RyuULXXun9/kh6zV6Rovz5Yym6j5Ww1OLVyZrXKC6B+XF79VF0mFN5UC5CRgmrwkMDsrDZg4puaT9i20CgYEA+9Qs7IwWDE0qagc3WSUJfNa+QniBPBhAFlJw9ugqiCeh6mmbeuU44VVaX5fH8sBubfRb8tpAqW4dVDFqCc1FHgCh9bnVizOHSER/chuMdWhIfweyYNC5p7epSBRBcBgA3fmU89njDIIMZdQYAl4nSIkV/VTjon2vGO9EFJuoeMsCgYEA9V48PGz9WDWopkwPBPp4/5fheuC2V15g3F/MF+ZK/jeH/KY/tlW29NkZK2W9wyrbI2KaAErxMhsBiNkM7Qf8MAVj/ZXmqrjpTGrEJc0RZisp83C/GY/K7Hit7pdzyXXMWbBRWSTjJCMjcAwO8DI3KRLemumNwty3A6ruHrP5H88CgYAG8Ye3kfk9p5cGy2IkTBgh4A3o6Ueu74A83VMOC8K4dFdLjlPiJJXX6LGQNzAlKlchIv6yCICF62pVmvMwh/9i32OX0eLobqhJPtJAxC+wQQrWrdCmSLsFu9OoEB7px0+mzy3jN1vCSYer+xgaYJw2rMj1bjTEkzCTwG0hIFWZ1wKBgQCjb2qGAT7a85rrcfwLH99auErrcyRhm66++1xRiwbmwAxnYEFN7VgZTUYznuhASJqAvlu/2CtCabSi95hhhhEX129b6O4lR1e2m+MdHrP5LsUZrz7LAvlye4u3QdBRH5LHJud4jlZxqqofJxkIKxqM1FHMM8dGRYft/HvxnYVSBwKBgAsa7SR4rc3yabhJ2pO34OHfUeOiSSkf1wYcsu1D0p5ggIoWL9yxoLAXvgOTwKlF4Wqnje8fU637pPKJPS306IIeRUSCLy6aQVx+5onsWb/R0X79dLO+o73o2P9k2uzrFR+WUSB/+Sy7F2/AwKjI8T1OXkmirHq8FMfQ6K6IsV0r";
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
        	RSAPrivateKey privateK= readPrivateKey(stringPrivateKey);
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
            
//            byte[] keyBytes = Base64.getDecoder().decode(stringPrivateKey.getBytes("utf-8"));
//            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
//            KeyFactory fact = KeyFactory.getInstance("RSA");
//            PrivateKey priv = fact.generatePrivate(keySpec);
                
//            JWSSigner signer = new RSASSASigner(kpg.generateKeyPair().getPrivate());
            JWSSigner signer = new RSASSASigner(privateK);
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
            System.out.println(ex);
        }
        return Optional.ofNullable(Objects.isNull(jwt) ? null : jwt.serialize());
    }

    public RSAPrivateKey readPrivateKey(String key) throws Exception {

    	java.security.Security.addProvider(
    	         new org.bouncycastle.jce.provider.BouncyCastleProvider()
    	);
        String privateKeyPEM = key
          .replace("-----BEGIN PRIVATE KEY-----", "")
          .replaceAll(System.lineSeparator(), "")
          .replace("-----END PRIVATE KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
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
