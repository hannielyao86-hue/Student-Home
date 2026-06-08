package org.example.tests;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class DbPropertiesTest {

    @Test
    void loadDbProperties() throws Exception {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
        assertNotNull(is, "db.properties must be present in test resources or main resources");

        Properties p = new Properties();
        p.load(is);

        assertTrue(p.containsKey("db.url"));
        assertTrue(p.containsKey("db.user"));
        assertTrue(p.containsKey("db.password"));
    }
}
