package com.mit.util.security;

import com.nimbusds.jose.shaded.gson.JsonObject;

import java.util.Map;
import java.util.Optional;

public interface JwtSecured {

    Optional<Boolean> verifyJWT(JsonObject jsonObject);

    Optional<String> jwtGeneratorPlain(Map<String,Object> dataEncrypt,String keyPublic);

    <T> T jwtGeneratorCustom(Map<String,Object> dataEncrypt,String keyPublic,Class<T> instance);

    Optional<Map<String,Object>> jwtDecryptData(JsonObject jsonObject);
}
