package ch.hutch79.configManager;

import ch.hutch79.configManager.configClass.Config;
import ch.hutch79.utility.Debugger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ConfigManager {

    private static String pluginPath;

    public ConfigManager(File _pluginPath) {
        this.pluginPath = _pluginPath.toString();
    }


    public void configStuff () {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.findAndRegisterModules();

//        mapper.readValue(new File("test.yaml"), Config.class);

        Config config = new Config();
        config.setVersion(1);
        config.setDebug(false);


        try {
            mapper.writeValue(new File(pluginPath + "/test.yaml"), config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Configuration getConfig (Class<?> configClass, String localPath) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();


        Config hui = new Config();
        try {
            Debugger.debug(pluginPath + "/" + localPath);
            hui = mapper.readValue(new File(pluginPath + "/" + localPath), Config.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return hui;
    }
}
