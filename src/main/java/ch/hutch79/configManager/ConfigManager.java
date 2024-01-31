package ch.hutch79.configManager;

import ch.hutch79.configManager.configClass.config.v0.Config;
import ch.hutch79.utility.Debugger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private static String pluginPath;

    public ConfigManager(File _pluginPath) {
        this.pluginPath = _pluginPath.toString();
    }


//    public void configStuff () {
//        ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        mapper.findAndRegisterModules();
//
////        mapper.readValue(new File("test.yaml"), Config.class);
//
//        Config config = new Config();
//        config.setVersion(1);
//        config.setDebug(false);
//
//
//        try {
//            mapper.writeValue(new File(pluginPath + "/test.yaml"), config);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public <T> T getConfig (Class<?> configClass, String localPath) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();

        T hui;
        try {
            Debugger.debug(pluginPath + "/" + localPath);
            hui = (T) mapper.readValue(new File(pluginPath + "/" + localPath), configClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return hui;
    }
}
