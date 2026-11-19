package com.portfolio.models;
import lombok.*;

@Setter
@Getter
@Data
@Builder
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
