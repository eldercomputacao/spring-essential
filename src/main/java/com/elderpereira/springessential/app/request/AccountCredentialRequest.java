package com.elderpereira.springessential.app.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountCredentialRequest implements Serializable{
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "The field 'username' must not be empty")
    private String username;
    @NotBlank(message = "The field 'password' must not be empty")
    private String password;
}
