package org.example.tests;

import org.example.utils.PasswordUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordUtilTest {

    @Test
    void hashAndCheckPassword() {
        String raw = "monMotDePasse123";
        String hash = PasswordUtil.hashPassword(raw);

        assertNotNull(hash);
        assertTrue(hash.startsWith("$2a$") || hash.startsWith("$2y$") || hash.startsWith("$2b$"));

        assertTrue(PasswordUtil.checkPassword(raw, hash));
        assertFalse(PasswordUtil.checkPassword("wrong", hash));
    }
}
