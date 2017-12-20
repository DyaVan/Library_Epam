package com.diachuk.library.services.json;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Dummy
class JsonResponseTest {

    public JsonObject dataRoot;
    JsonResponse response;

    @BeforeEach
    public void setDataRoot(){
        dataRoot = new JsonObject();

    }

    @Test
    void extractJsonString() {
        response = new JsonResponse(true,
                 false,
                 null,
                 null,
                null,
                 dataRoot);
        assertEquals("{\"successFlag\":true,\"dataRoot\":{}}",
                response.extractJsonString());
    }

    @Test
    void extractJsonString1() {
        response = new JsonResponse(true,
                false,
                null,
                null,
                null,
                null);
        assertEquals("{\"successFlag\":true}",
                response.extractJsonString());
    }

    @Test
    void extractJsonString2() {
        response = new JsonResponse(true,
                false,
                null,
                null,
                null,
                dataRoot);
        assertEquals("{\"successFlag\":true,\"dataRoot\":{}}",
                response.extractJsonString());
    }

}