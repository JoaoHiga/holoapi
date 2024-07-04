package com.takomuraragi.holoapi.holoapi.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class GetApiKey {
    public String retrieveApiKey (String configFileName) {
        Properties prop = new Properties();
        try {
            FileInputStream input = new FileInputStream("config.properties");
            prop.load(input);
            return prop.getProperty("api_key");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
