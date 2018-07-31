package com.xiaomi.iot.example.operater;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;

public class DatabaseOperater {

    public String databaseReader(String fileName) {
        String Path;
        if (fileName.matches("devices") || fileName.matches("account")) {
            Path = "./" + fileName + ".json";
        } else {
            Path = "./device/" + fileName + ".json";
        }

        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(Path);
        String string = "";
        try {
            string = IOUtils.toString(resourceAsStream, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return string;
    }
}
