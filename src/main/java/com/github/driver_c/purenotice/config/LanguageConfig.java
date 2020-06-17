package com.github.driver_c.purenotice.config;

import com.github.driver_c.purenotice.PureNotice;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class LanguageConfig {
    public final static String configVersion = "1.0";
    private final PureNotice plugin;
    public static CommentedConfigurationNode rootNode;

    public LanguageConfig(PureNotice plugin) {
        this.plugin = plugin;
    }
}
