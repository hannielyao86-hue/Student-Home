package org.example.tests;

import org.example.service.AuthService;

public class TestLogin {

    public static void main(String[] args) {

        AuthService auth = new AuthService();

        // TEST LOGIN
        boolean result = auth.login(
                "test@gmail.com",
                "123456"
        );

        System.out.println("Résultat login = " + result);
    }
}