package by.clevertec.check.jdbc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesManager {

    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(Files.newInputStream(Paths.get("C:/Users/ERIK/IdeaProjects/BackendTest/src/main/resources/application.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PropertiesManager() {
    }

    public static String getPropertyByKey(String key) {
        return properties.getProperty(key);
    }
}
