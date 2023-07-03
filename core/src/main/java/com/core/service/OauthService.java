package com.core.service;

import com.core.dto.oauth.UserTokenDTO;
import com.core.integration.OauthIntegration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Slf4j
@Service
public class OauthService {

    private final OauthIntegration oauthIntegration;

    public UserTokenDTO getUserData(String token){
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", token);

            return oauthIntegration.findUserByToken(httpHeaders);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERROR on find user data by token: " + token);
        }
    }
}
