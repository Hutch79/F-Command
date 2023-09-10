package ch.hutch79.utility;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class ConfigManager {
    private final List<String> commandOptions;
    private FileConfiguration cfg;

    public ConfigManager(List<String> commandOptions) {
        this.commandOptions = commandOptions;
    }

    public String getInfo(int count, String value) {

        cfg = YamlConfiguration.loadConfiguration(new File("plugins" + File.separator + "F-Command", "config.yml"));
        String result = cfg.getString("command." + commandOptions.get(count) + "." + value);
        if (result == null) {
            Debugger.debug("The Value " + value + " for the Command " + commandOptions.get(count) + " is not set!");

            if (value.equals("key")) {
                return "f";
            }

            if (value.equals("permission")) {
                return "none";
            }

            if (value.equals("requireShift")) {
                return "true";
            }

            if (value.equals("cancel")) {
                return "false";
            }

            if (value.equals("executeAsServer")) {
                return "false";
            }

            if (value.equals("command")) {
                return "say hi, im a default command. Please edit the config.yml to set your own command.";
            }
        }

        return result;
    }

    public FileConfiguration getCfg () {
        return cfg;
    }
}