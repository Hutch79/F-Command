package ch.hutch79.configManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ConfigManager {

    private static String pluginPath;
    private static ObjectMapper writeMapper;
    private static ObjectMapper readMapper;
    private static HashMap<Class<?>, Object> configCache = new HashMap<>();
    public ConfigManager(File _pluginPath) {
        pluginPath = _pluginPath.toString();

        writeMapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        writeMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        writeMapper.findAndRegisterModules();

        readMapper = new ObjectMapper(new YAMLFactory());
        readMapper.findAndRegisterModules();
    }


    public void writeConfig (Class<?> configClass, String localPath) {
        try {
            writeMapper.writeValue(new File(pluginPath + "/" + localPath), configClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void loadConfig(Class<?> configClass, String localPath) {
        T config;
        try {
            config = (T) readMapper.readValue(new File(pluginPath + "/" + localPath), configClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        configCache.put(configClass, config);
    }

    public <T> T getConfig(Class<?> configClass) {
        return (T) configCache.get(configClass);
    }
}
