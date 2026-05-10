package org.example.tests;

import org.example.utils.PasswordUtil;

public class TestBCrypt {

    public static void main(String[] args) {

        String password = "123456";

        String hash = PasswordUtil.hashPassword(password);

        System.out.println("Hash généré : " + hash);

        boolean check = PasswordUtil.checkPassword(password, hash);

        System.out.println("Test BCrypt = " + check);
    }
}