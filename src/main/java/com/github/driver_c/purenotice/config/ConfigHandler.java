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

    public void load(File defaultConfig, Path configDir) throws IOException {
        ConfigurationLoader<CommentedConfigurationNode> config =
                HoconConfigurationLoader.builder().setFile(defaultConfig).build();
        rootNode = config.load();
        boolean configExists = defaultConfig.createNewFile();

        if (configExists) {
            initConfig();
            config.save(rootNode);
            LanguageConfig languageConfig = new LanguageConfig(this.plugin);
            languageConfig.load(configDir);

            this.plugin.getLogger().info(
                    LanguageConfig.rootNode.getNode("messages", "firstRun").getString()
            );
        } else {
            LanguageConfig languageConfig = new LanguageConfig(this.plugin);
            languageConfig.load(configDir);

            this.plugin.getLogger().info(
                    LanguageConfig.rootNode.getNode("messages", "noInit").getString()
            );
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
                            LanguageConfig.rootNode.getNode("messages", "configOut").getString()
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
        rootNode.getNode("main", "prefix").setValue(
                rootNode.getNode("main", "prefix").getString("&e[&aPureNotice&e]&r")
        );
        rootNode.getNode("main", "language").setComment("en_US or zh_CN");
        rootNode.getNode("main", "language").setValue(
                rootNode.getNode("main", "language").getString("en_US")
        );
        rootNode.getNode("main", "firstDelay").setValue(
                rootNode.getNode("main", "firstDelay").getInt(30)
        );
        rootNode.getNode("main", "interval").setValue(
                rootNode.getNode("main", "interval").getInt(120)
        );
        rootNode.getNode("main", "random").setValue(
                rootNode.getNode("main", "random").getBoolean(false)
        );
        rootNode.getNode("messages", "1", "message").setValue(
                rootNode.getNode("messages", "1", "message").getString(
                        "&eThanks for using &aPureNotice&e, this is a default message 1."
                )
        );
        rootNode.getNode("messages", "2", "message").setValue(
                rootNode.getNode("messages", "2", "message").getString(
                        "&eThanks for using &aPureNotice&e, this is a default message 2."
                )
        );
    }
}
