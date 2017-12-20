package com.diachuk.library.services;

import junit.framework.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class InputValidationServiceTest {

    InputValidationService validationService;

    @BeforeEach
    void setUp() {
        validationService = new InputValidationService();
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345qwe", "1234567","superPassword"})
    public void passwordValidationPassTest(String password) {
        boolean val = validationService.validateNewPassword(password);
        assertTrue(val);
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345", "","qwerty"})
    public void passwordValidationFailTest(String password) {
        boolean val = validationService.validateNewPassword(password);
        assertFalse(val);
    }

    @Test
    public void emailValidationTest_0() {
        String email = "";
        boolean val = validationService.validateNewEmail(email);
        Assert.assertEquals(false, val);
    }

    @Test
    public void emailValidationTest_1() {
        String email = "123";
        boolean val = validationService.validateNewEmail(email);
        Assert.assertEquals(false, val);
    }

    @Test
    public void emailValidationTest_2() {
        String email = "123@gmail.com";
        boolean val = validationService.validateNewEmail(email);
        Assert.assertEquals(true, val);
    }

    @Test
    public void emailValidationTest_3() {
        String email = "@ukr.net";
        boolean val = validationService.validateNewEmail(email);
        Assert.assertEquals(false, val);
    }

    @Test
    public void emailValidationTest_4() {
        String email = "123@fignya";
        boolean val = validationService.validateNewEmail(email);
        Assert.assertEquals(false, val);
    }

    @Test
    public void emailErrorMessageTest() {
        validationService.validateNewEmail("");
        String json = validationService.buildJsonResponse().extractJsonString();
        assertEquals("{\"successFlag\":false,\"errorMessage\":\"- Empty input.\\u003cbr/\\u003e- Email is not valid.\\u003cbr/\\u003e\",\"notificationMessage\":\"\",\"successMessage\":\"\",\"dataRoot\":{}}",
                json);
    }

    @Test
    public void successFlagTest() {
        validationService.validateNewEmail("");
        String json = validationService.buildJsonResponse().extractJsonString();
        assertFalse(validationService.isSuccessFlag());
    }

    @Test
    public void successFlagTest1() {
        validationService.validateNewEmail("123@gmail.com");
        String json = validationService.buildJsonResponse().extractJsonString();
        assertTrue(validationService.isSuccessFlag());
    }


    @RepeatedTest(2)
    void errorMessageTest() {
        validationService.validateNewEmail("gmail.com");
        String json = validationService.buildJsonResponse().extractJsonString();
        assertFalse(validationService.getErrorMessage().isEmpty());
    }

    @Test
    void errorMessageTest1() {
        validationService.validateNewEmail("123@gmail.com");
        String json = validationService.buildJsonResponse().extractJsonString();
        assertTrue(validationService.getErrorMessage().isEmpty());
    }


}