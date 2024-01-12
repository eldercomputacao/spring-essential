package com.elderpereira.springessential.app.rest;

import com.elderpereira.springessential.app.request.AccountCredentialRequest;
import com.elderpereira.springessential.app.response.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@Tag(name = "AuthController Endpoint")
public interface AuthenticationController {

    @PostMapping(value = "/signin")
    @Operation(summary = "Authenticates a user and returns a token'", responses = {
            @ApiResponse(description = "Success", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    ResponseEntity<TokenResponse> signin(@Valid @RequestBody AccountCredentialRequest accountCredentialRequest);

    @PutMapping(value = "/refresh")
    @Operation(summary = "Refresh token for authenticated user and returns a token'", responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    ResponseEntity<TokenResponse> refreshToken(@RequestHeader("Authorization") String refreshToken);

}
