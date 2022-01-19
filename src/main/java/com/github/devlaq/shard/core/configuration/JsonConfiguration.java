package com.github.devlaq.shard.core.configuration;

import arc.files.Fi;
import com.google.gson.*;

public class JsonConfiguration {

    private final Gson gson;
    private JsonObject object;

    private Fi configFile;
    private Fi defaultFile;

    public JsonConfiguration() {
        gson = new Gson();
        object = new JsonObject();
    }

    public JsonConfiguration(Fi configFile, Fi defaultFile) {
        this();
        this.configFile = configFile;
        this.defaultFile = defaultFile;
    }

    public JsonConfiguration(Fi configFile) {
        this();
        this.configFile = configFile;
    }

    public void load(boolean nonExistLoadDefault) {
        if(nonExistLoadDefault) saveDefault(false);
        object = gson.fromJson(configFile.readString(), JsonObject.class);
    }

    public void save(boolean overwriteExisting) {
        if(configFile.exists() && ! overwriteExisting) return;
        configFile.writeString(gson.toJson(object), false);
    }

    public void saveDefault(boolean overwriteExisting) {
        if(configFile.exists() && !overwriteExisting) return;
        String data = defaultFile.readString();
        configFile.writeString(data, false);
    }

    public void setConfigFile(Fi configFile) {
        this.configFile = configFile;
    }
    public void setDefaultFile(Fi defaultFile) {
        this.defaultFile = defaultFile;
    }

    public Fi getConfigFile() {
        return configFile;
    }

    public Fi getDefaultFile() {
        return defaultFile;
    }

    public void addProperty(String property, Number value) {
        object.addProperty(property, value);
    }

    public void addProperty(String property, String value) {
        object.addProperty(property, value);
    }

    public void addProperty(String property, Boolean value) {
        object.addProperty(property, value);
    }

    public void addProperty(String property, Character value) {
        object.addProperty(property, value);
    }

    public void add(String property, JsonElement value) {
        object.add(property, value);
    }

    public JsonElement get(String memberName) {
        return object.get(memberName);
    }

    public JsonObject getAsJsonObject(String memberName) {
        return object.getAsJsonObject(memberName);
    }

    public JsonArray getAsJsonArray(String memberName) {
        return object.getAsJsonArray(memberName);
    }

    public JsonPrimitive getAsJsonPrimitive(String memberName) {
        return object.getAsJsonPrimitive(memberName);
    }

}
