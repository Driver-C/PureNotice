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
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.plugin.getLogger().warn(
                        LanguageConfig.rootNode.getNode("messages", "configOut").getString()
                );
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
                rootNode.getNode("main", "prefix").getString("&e[&a&lPureNotice&e]&r")
        );
        rootNode.getNode("main", "language").setComment("en_US or zh_CN");
        rootNode.getNode("main", "language").setValue(
                rootNode.getNode("main", "language").getString("en_US")
        );
        rootNode.getNode("main", "firstDelay").setValue(
                rootNode.getNode("main", "firstDelay").getInt(20)
        );
        rootNode.getNode("main", "interval").setValue(
                rootNode.getNode("main", "interval").getInt(60)
        );
        rootNode.getNode("main", "random").setValue(
                rootNode.getNode("main", "random").getBoolean(false)
        );
        rootNode.getNode("messages", "1", "message").setValue(
                rootNode.getNode("messages", "1", "message").getString(
                        "&fThanks for using &aPureNotice&f, this is a normal message with no function."
                )
        );
        rootNode.getNode("messages", "2", "message").setValue(
                rootNode.getNode("messages", "2", "message").getString(
                        "&fThanks for using &aPureNotice&f, this message is used to show the &eclick&f to " +
                                "execute command function."
                )
        );
        rootNode.getNode("messages", "2", "hover").setValue(
                rootNode.getNode("messages", "2", "hover").getString("&eClick&f to execute &a/help")
        );
        rootNode.getNode("messages", "2", "click").setValue(
                rootNode.getNode("messages", "2", "click").getString("/help")
        );
        rootNode.getNode("messages", "3", "message").setValue(
                rootNode.getNode("messages", "3", "message").getString(
                        "&fThanks for using &aPureNotice&f, this message is used to show the " +
                                "&eshift + click&f to insert a message or command into the chat box function."
                )
        );
        rootNode.getNode("messages", "3", "hover").setValue(
                rootNode.getNode("messages", "3", "hover").getString(
                        "&eShift + click&f to insert &a/list&f to the chat box"
                )
        );
        rootNode.getNode("messages", "3", "shiftInsert").setValue(
                rootNode.getNode("messages", "3", "shiftInsert").getString("/list")
        );
        rootNode.getNode("messages", "4", "message").setValue(
                rootNode.getNode("messages", "4", "message").getString(
                        "&fThanks for using &aPureNotice&f, this message is used to show the &eclick&f to " +
                                "open a url function."
                )
        );
        rootNode.getNode("messages", "4", "hover").setValue(
                rootNode.getNode("messages", "4", "hover").getString(
                        "&eClick&f to open &ehttps://www.google.com/"
                )
        );
        rootNode.getNode("messages", "4", "clickUrl").setValue(
                rootNode.getNode("messages", "4", "clickUrl").getString("https://www.google.com/")
        );
        rootNode.getNode("messages", "5", "message").setComment(
                "When both click and clickUrl exist, only click will take effect."
        );
        rootNode.getNode("messages", "5", "message").setValue(
                rootNode.getNode("messages", "5", "message").getString(
                        "&fThanks for using &aPureNotice&f, this message is used to show &eall functions"
                )
        );
        rootNode.getNode("messages", "5", "hover").setValue(
                rootNode.getNode("messages", "5", "hover").getString(
                        "When both &eclick&f and &eclickUrl&f exist, only &cclick&f will take effect."
                )
        );
        rootNode.getNode("messages", "5", "click").setValue(
                rootNode.getNode("messages", "5", "click").getString("/help")
        );
        rootNode.getNode("messages", "5", "shiftInsert").setValue(
                rootNode.getNode("messages", "5", "shiftInsert").getString("/list")
        );
        rootNode.getNode("messages", "5", "clickUrl").setValue(
                rootNode.getNode("messages", "5", "clickUrl").getString("https://www.google.com/")
        );
        rootNode.getNode("messages", "10", "message").setValue(
                rootNode.getNode("messages", "10", "message").getString(
                        "&fThanks for using &aPureNotice&f, This message is used to show that the " +
                                "&emessage index&f does not need to be incremented by 1 at a time."
                )
        );
    }
}
