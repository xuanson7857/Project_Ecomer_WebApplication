package com.ra.dto.request;

import com.ra.dto.validate.PasswordConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserLoginDTO {
    @NotEmpty(message = "không được rỗng")
    @Email(message = "Sai dinh dạng email")
    private String email;

    @PasswordConstraint
    private String password;

    public UserLoginDTO() {
    }

    public UserLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
