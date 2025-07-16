package com.bts.model.common.jwt;

import java.util.Map;

public interface JwtUtilGateway {
    String generateToken(String user, Map<String, Object> claims);
}
