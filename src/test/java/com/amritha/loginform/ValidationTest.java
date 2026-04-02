package com.amritha.loginform;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {
    @AfterEach
    void cleanup() {
        System.clearProperty("login.username");
        System.clearProperty("login.password");
    }

    @Test
    void usernameValidation() {
        App a = new App();
        assertEquals("must not be empty", a.validateUsername(""));
        assertEquals("must be at least 3 characters", a.validateUsername("ab"));
        assertNull(a.validateUsername("alice_01"));
        assertEquals("only letters, digits, dot, underscore and hyphen allowed", a.validateUsername("a b"));
    }

    @Test
    void passwordValidation() {
        App a = new App();
        assertEquals("must not be empty", a.validatePassword(""));
        assertEquals("must be at least 8 characters", a.validatePassword("Aa1!aa"));
        assertEquals("must contain upper, lower, digit and special character", a.validatePassword("abcdefgh"));
        assertNull(a.validatePassword("S3cure!x"));
    }

    @Test
    void authenticationWithConfiguredCredentials() {
        App a = new App();
        System.setProperty("login.username", "admin");
        System.setProperty("login.password", "S3cure!x");
        assertTrue(a.authenticate("admin", "S3cure!x"));
        assertFalse(a.authenticate("admin", "wrong"));
    }

    @Test
    void authenticationWithoutConfigAcceptsValidated() {
        App a = new App();
        assertTrue(a.authenticate("alice", "S3cure!x"));
    }
}
