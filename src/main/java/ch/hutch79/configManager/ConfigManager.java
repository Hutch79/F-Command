package ch.hutch79.configManager;

import ch.hutch79.utility.Debugger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private static String pluginPath;
    private ObjectMapper writeMapper;
    private ObjectMapper readMapper;
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

    public <T> T getConfig (Class<?> configClass, String localPath) {
        T config;
        try {
            Debugger.debug(pluginPath + "/" + localPath);
            config = (T) readMapper.readValue(new File(pluginPath + "/" + localPath), configClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return config;
    }
}
