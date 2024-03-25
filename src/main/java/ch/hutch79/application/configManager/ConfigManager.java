package ch.hutch79.application.configManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.apache.commons.lang.NullArgumentException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ConfigManager {

    private String _pluginPath;
    private ObjectMapper writeMapper;
    private ObjectMapper readMapper;
    private HashMap<Class<?>, Object> configCache = new HashMap<>();
    public ConfigManager(@NotNull File pluginPath) {
        _pluginPath = pluginPath.toString();

        writeMapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        writeMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        writeMapper.findAndRegisterModules();

        readMapper = new ObjectMapper(new YAMLFactory());
        readMapper.findAndRegisterModules();
    }


    public void writeConfig (Object configClass, String localPath) {
        try {
            writeMapper.writeValue(new File(_pluginPath + File.separator + localPath), configClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> ConfigManager loadConfig(Class<?> configClass, String localPath) {
        T config;
        try {
            config = (T) readMapper.readValue(new File(_pluginPath + File.separator + localPath), configClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        configCache.put(configClass, config);
        return this;
    }

    public <T> T getConfig(Class<?> configClass) {
        return (T) configCache.get(configClass);
    }
}
