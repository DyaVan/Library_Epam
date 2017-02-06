package com.diachuk.library.services.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Created by VA-N_ on 01.02.2017.
 */
public abstract class JsonResponseBuilder {

    private boolean successFlag = true;
    private boolean reloadPage;
    private final StringBuilder errorMessage = new StringBuilder();
    private final StringBuilder notificationMessage = new StringBuilder();
    private final StringBuilder successMessage = new StringBuilder();
    private JsonObject dataRoot = new JsonObject();

    public JsonResponse buildJsonResponse() {
        return new JsonResponse(successFlag, reloadPage, errorMessage.toString(), notificationMessage.toString(),
                successMessage.toString(), dataRoot);
    }

    public JsonResponseBuilder appendErrorMessage(String errorMessage) {
        this.errorMessage.append("- " + errorMessage + "<br/>");
        successFlag = false;
        return this;
    }

    public JsonResponseBuilder appendNotificationMessage(String notificationMessage) {
        this.notificationMessage.append("- " + notificationMessage+ "<br/>");
        return this;
    }

    public JsonResponseBuilder appendSuccessMessage(String successMessage) {
        this.successMessage.append("- " + successMessage+ "<br/>");
        return this;
    }

    public void addDataObject(String property, Object dataObject) {
        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
        addDataObject(property, dataObject, gson);
    }

    public void addDataObject(String property, Object dataObject, Gson gson) {
        dataRoot.add(property, gson.toJsonTree(dataObject));
    }

    public void setDataRoot(JsonObject dataRoot) {
        if (dataRoot != null) {
            this.dataRoot = dataRoot;
        }
    }

    public void setReloadPage(boolean reloadPage) {
        this.reloadPage = reloadPage;
    }

    public void setSuccessFlag(boolean successFlag) {
        this.successFlag = successFlag;
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
