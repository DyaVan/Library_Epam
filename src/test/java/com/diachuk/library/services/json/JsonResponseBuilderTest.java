package com.diachuk.library.services.json;

import com.diachuk.library.dao.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonResponseBuilderTest {

    private class JsonResponseBuilderStub extends JsonResponseBuilder {}

    JsonResponseBuilder responseBuilder;
    User user;

    @BeforeEach
     void init(){
        user = new User();
        responseBuilder = new JsonResponseBuilderStub();
    }


    @Test
    void addDataObject() {
        user.setName("Ivan");
        responseBuilder.addDataObject("user", user);
        String json = responseBuilder.buildJsonResponse().extractJsonString();
        assertEquals("{\"successFlag\":true,\"errorMessage\":\"\",\"notificationMessage\":\"\",\"successMessage\":\"\",\"dataRoot\":{\"user\":{\"id\":0,\"roleId\":0,\"name\":\"Ivan\"}}}",
                json);
    }

    @Test
    void addDataObject1() {
        user.setName("Vova");
        user.setSurname("lol");
        responseBuilder.addDataObject("user", user);
        String json = responseBuilder.buildJsonResponse().extractJsonString();
        assertEquals("{\"successFlag\":true,\"errorMessage\":\"\",\"notificationMessage\":\"\",\"successMessage\":\"\",\"dataRoot\":{\"user\":{\"id\":0,\"roleId\":0,\"name\":\"Vova\",\"surname\":\"lol\"}}}",
                json);
    }

    @Test
    void addDataObject3() {
        user.setName("Vova");
        user.setPassword("12345");
        responseBuilder.addDataObject("user", user);
        String json = responseBuilder.buildJsonResponse().extractJsonString();
        assertEquals("{\"successFlag\":true,\"errorMessage\":\"\",\"notificationMessage\":\"\",\"successMessage\":\"\",\"dataRoot\":{\"user\":{\"id\":0,\"roleId\":0,\"name\":\"Vova\",\"password\":\"12345\"}}}",
                json);
    }

    @Test
    void addDataObject4() {
        responseBuilder.setDataRoot(null);
        responseBuilder.addDataObject("user", user);
        assertNotNull(responseBuilder.getDataRoot());
    }

    //DUMMY
    @Test
    void addDataObject8() {
        responseBuilder.addDataObject("user", null);
        String json = responseBuilder.buildJsonResponse().extractJsonString();
        assertEquals("{\"successFlag\":true,\"errorMessage\":\"\",\"notificationMessage\":\"\",\"successMessage\":\"\",\"dataRoot\":{}}",
                json);
    }

}