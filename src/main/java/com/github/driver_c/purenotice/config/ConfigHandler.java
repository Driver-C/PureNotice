package com.github.driver_c.purenotice.config;


import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import com.github.driver_c.purenotice.PureNotice;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;


public class ConfigHandler {
    public final static String configVersion = "1.0";
    private final PureNotice plugin;
    public static CommentedConfigurationNode rootNode;

    public ConfigHandler(PureNotice plugin) {
        this.plugin = plugin;
    }

    public void load(File defaultConfig) throws IOException {
        ConfigurationLoader<CommentedConfigurationNode> config =
                HoconConfigurationLoader.builder().setFile(defaultConfig).build();
        rootNode = config.load();
        boolean configExists = defaultConfig.createNewFile();

        if (configExists) {
            initConfig();
            config.save(rootNode);
            this.plugin.getLogger().info("This may be the first running. Config initialized");
        } else {
            this.plugin.getLogger().info("Config already exists. No initialization required");
            String configVersionNow = rootNode.getNode("main", "version").getString();
            File backupConfig = new File(
                    defaultConfig.getParent() + "/" + defaultConfig.getName() + "_old"
            );
            Path sourceConfigPath = Paths.get(defaultConfig.getPath());
            Path destinationConfigPath = Paths.get(backupConfig.getPath());
            if (!configVersion.equals(configVersionNow)) {
                try {
                    Files.copy(sourceConfigPath, destinationConfigPath, StandardCopyOption.REPLACE_EXISTING);
                    this.plugin.getLogger().warn(
                            "Config is out of date, The old config has been overwritten and saved to the config directory"
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Files.delete(sourceConfigPath);
                initConfig();
                config.save(rootNode);
            }
        }
    }

    private void initConfig() {
        rootNode.getNode("main", "version").setComment("Do not change version!!!");
        rootNode.getNode("main", "version").setValue(configVersion);
        rootNode.getNode("main", "firstDelay").setValue(10);
        rootNode.getNode("main", "interval").setValue(120);
        rootNode.getNode("messages", "1", "message").setValue(
                "&eThanks for using &aPureNotice&e, this is a default message 1."
        );
        rootNode.getNode("messages", "2", "message").setValue(
                "&eThanks for using &aPureNotice&e, this is a default message 2."
        );
    }
}
