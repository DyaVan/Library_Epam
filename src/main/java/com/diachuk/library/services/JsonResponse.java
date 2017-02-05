package com.diachuk.library.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Created by VA-N_ on 01.02.2017.
 */
public class JsonResponse {
    private boolean successFlag;
    private boolean reloadPage;
    private String errorMessage ;
    private String notificationMessage;
    private String successMessage ;
    private JsonObject dataRoot;

    private JsonResponse(){}

    public JsonResponse(boolean successFlag,
                        boolean reloadPage,
                        String errorMessage,
                        String notificationMessage,
                        String successMessage,
                        JsonObject dataRoot) {
        this.successFlag = successFlag;
        this.reloadPage = reloadPage;
        this.errorMessage = errorMessage;
        this.notificationMessage = notificationMessage;
        this.successMessage = successMessage;
        this.dataRoot = dataRoot;

    }

    public String extractJsonString() {
        Gson gson = new Gson();
        JsonObject rootJson = new JsonObject();
        rootJson.addProperty("successFlag", successFlag);
        rootJson.addProperty("errorMessage", errorMessage);
        rootJson.addProperty("notificationMessage", notificationMessage);
        rootJson.addProperty("successMessage", successMessage);
        rootJson.add("dataRoot", dataRoot);
        return gson.toJson(rootJson);
    }

    public boolean isSuccessFlag() {
        return successFlag;
    }

    public String getErrorMessage() {
        return errorMessage.toString();
    }

    public String getNotificationMessage() {
        return notificationMessage.toString();
    }

    public String getSuccessMessage() {
        return successMessage.toString();
    }

    public JsonObject getDataRoot() {
        return dataRoot;
    }

    public boolean isReloadPage() {
        return reloadPage;
    }

}
