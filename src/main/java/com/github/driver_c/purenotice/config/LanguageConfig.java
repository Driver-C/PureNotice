package com.github.driver_c.purenotice.config;

import com.github.driver_c.purenotice.PureNotice;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class LanguageConfig {
    public final static String configVersion = "1.0";
    private final PureNotice plugin;
    public static CommentedConfigurationNode rootNode;
    private final String languageStr;

    public LanguageConfig(PureNotice plugin) {
        this.plugin = plugin;
        this.languageStr = ConfigHandler.rootNode.getNode("main", "language").getString();
    }

    public void load(Path configDir) throws IOException {
        Path languagePath = Paths.get(configDir.toString(), "lang");
        File languageFile = Paths.get(
                languagePath.toString(), this.languageStr + ".conf"
        ).toFile();
        ConfigurationLoader<CommentedConfigurationNode> config =
                HoconConfigurationLoader.builder().setFile(languageFile).build();
        rootNode = config.load();
        Files.createDirectories(languagePath);
        if (languageFile.exists()) {
            String configVersionNow = rootNode.getNode("main", "version").getString();
            if (!configVersion.equals(configVersionNow)) {
                initConfig();
                this.plugin.getLogger().warn(rootNode.getNode("messages", "languageOverWritten").getString());
                config.save(rootNode);
            }
        } else {
            boolean fileCreate = languageFile.createNewFile();
            if (fileCreate) {
                initConfig();
                config.save(rootNode);
            }
        }
    }

    private void initConfig() {
        if (this.languageStr.equals("en_US")) {
            rootNode.getNode("main", "version").setComment("Do not change version!!!");
            rootNode.getNode("main", "version").setValue(configVersion);
            rootNode.getNode("messages", "loading").setValue(
                    rootNode.getNode("messages", "loading").getString(
                            "\u00A7aPureNotice is loading.\u00A7r"
                    )
            );
            rootNode.getNode("messages", "loaded").setValue(
                    rootNode.getNode("messages", "loaded").getString(
                            "\u00A7aPureNotice loaded.\u00A7r"
                    )
            );
            rootNode.getNode("messages", "reloading").setValue(
                    rootNode.getNode("messages", "reloading").getString(
                            "\u00A7aPureNotice is reloading.\u00A7r"
                    )
            );
            rootNode.getNode("messages", "reloaded").setValue(
                    rootNode.getNode("messages", "reloaded").getString(
                            "\u00A7aPureNotice reloaded.\u00A7r"
                    )
            );
            rootNode.getNode("messages", "disabled").setValue(
                    rootNode.getNode("messages", "disabled").getString(
                            "\u00A7aPureNotice disabled.\u00A7r"
                    )
            );
            rootNode.getNode("messages", "commandMain").setValue(
                    rootNode.getNode("messages", "commandMain").getString(
                            "\u00A7aPureNotice\u00A7r version:"
                    )
            );
            rootNode.getNode("messages", "commandMainAuthors").setValue(
                    rootNode.getNode("messages", "commandMainAuthors").getString(
                            "\u00A7aPureNotice\u00A7r authors: \u00A7a\u00A7l"
                    )
            );
            rootNode.getNode("messages", "commandMainHelp").setValue(
                    rootNode.getNode("messages", "commandMainHelp").getString(
                            "\u00A7epurenotice(pn) reload \u00A7fReload PureNotice"
                    )
            );
            rootNode.getNode("messages", "commandMainDesc").setValue(
                    rootNode.getNode("messages", "commandMainDesc").getString(
                            "Main."
                    )
            );
            rootNode.getNode("messages", "commandReloadDesc").setValue(
                    rootNode.getNode("messages", "commandReloadDesc").getString(
                            "Reload this plugin."
                    )
            );
            rootNode.getNode("messages", "firstRun").setValue(
                    rootNode.getNode("messages", "firstRun").getString(
                            "This may be the first running. Config initialized."
                    )
            );
            rootNode.getNode("messages", "noInit").setValue(
                    rootNode.getNode("messages", "noInit").getString(
                            "Config already exists. No initialization required."
                    )
            );
            rootNode.getNode("messages", "configOut").setValue(
                    rootNode.getNode("messages", "configOut").getString(
                            "Config is out of date, config has been overwritten and" +
                                    " the older has been saved to the config directory."
                    )
            );
            rootNode.getNode("messages", "languageOverWritten").setValue(
                    rootNode.getNode("messages", "languageOverWritten").getString(
                            "Language config is out of date, it has been overwritten."
                    )
            );
        } else if (this.languageStr.equals("zh_CN")) {
            rootNode.getNode("main", "version").setComment("不要更改配置版本号！！！");
            rootNode.getNode("main", "version").setValue(configVersion);
            rootNode.getNode("messages", "loading").setValue(
                    rootNode.getNode("messages", "loading").getString(
                            "\u00A7aPureNotice 正在加载\u00A7r"
                    )
            );
            rootNode.getNode("messages", "loaded").setValue(
                    rootNode.getNode("messages", "loaded").getString(
                            "\u00A7aPureNotice 已加载\u00A7r"
                    )
            );
            rootNode.getNode("messages", "reloading").setValue(
                    rootNode.getNode("messages", "reloading").getString(
                            "\u00A7aPureNotice 正在重载\u00A7r"
                    )
            );
            rootNode.getNode("messages", "reloaded").setValue(
                    rootNode.getNode("messages", "reloaded").getString(
                            "\u00A7aPureNotice 已重载\u00A7r"
                    )
            );
            rootNode.getNode("messages", "disabled").setValue(
                    rootNode.getNode("messages", "disabled").getString(
                            "\u00A7aPureNotice 已关闭\u00A7r"
                    )
            );
            rootNode.getNode("messages", "commandMain").setValue(
                    rootNode.getNode("messages", "commandMain").getString(
                            "\u00A7aPureNotice\u00A7r 版本："
                    )
            );
            rootNode.getNode("messages", "commandMainAuthors").setValue(
                    rootNode.getNode("messages", "commandMainAuthors").getString(
                            "\u00A7aPureNotice\u00A7r 作者：\u00A7a\u00A7l"
                    )
            );
            rootNode.getNode("messages", "commandMainHelp").setValue(
                    rootNode.getNode("messages", "commandMainHelp").getString(
                            "\u00A7epurenotice(pn) reload \u00A7f重载PureNotice"
                    )
            );
            rootNode.getNode("messages", "commandMainDesc").setValue(
                    rootNode.getNode("messages", "commandMainDesc").getString(
                            "主命令"
                    )
            );
            rootNode.getNode("messages", "commandReloadDesc").setValue(
                    rootNode.getNode("messages", "commandReloadDesc").getString(
                            "重载本插件"
                    )
            );
            rootNode.getNode("messages", "firstRun").setValue(
                    rootNode.getNode("messages", "firstRun").getString(
                            "本次可能是第一次运行本插件，配置文件已初始化"
                    )
            );
            rootNode.getNode("messages", "noInit").setValue(
                    rootNode.getNode("messages", "noInit").getString(
                            "配置文件已存在，无须初始化"
                    )
            );
            rootNode.getNode("messages", "configOut").setValue(
                    rootNode.getNode("messages", "configOut").getString(
                            "配置文件已过期并已被覆盖，旧的配置文件已保存在配置文件目录"
                    )
            );
            rootNode.getNode("messages", "languageOverWritten").setValue(
                    rootNode.getNode("messages", "languageOverWritten").getString(
                            "语言配置文件已过期并已被覆盖"
                    )
            );
        }
    }
}
