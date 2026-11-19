package com.portfolio.utils;
import java.util.UUID;

public class RegistrationUtils {

    public String createRandomEmail(){
        String randomPrefix = UUID.randomUUID().toString().substring(0,8);
        return "user_" + randomPrefix + "@faker.com";
    }

}
