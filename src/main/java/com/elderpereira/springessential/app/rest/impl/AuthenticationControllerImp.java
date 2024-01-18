package com.elderpereira.springessential.app.rest.impl;

import com.elderpereira.springessential.app.request.AccountCredentialRequest;
import com.elderpereira.springessential.app.response.TokenResponse;
import com.elderpereira.springessential.app.rest.AuthenticationController;
import com.elderpereira.springessential.infra.configurations.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationControllerImp implements AuthenticationController {

    @Autowired
    AuthenticationService authServices;

    @Override
    public ResponseEntity<TokenResponse> signin(AccountCredentialRequest accountCredentialRequest) {
        return new ResponseEntity<>(authServices.signin(accountCredentialRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<TokenResponse> refreshToken(String refreshToken) {
        return new ResponseEntity<>(authServices.refreshToken(refreshToken), HttpStatus.OK);
    }
}
