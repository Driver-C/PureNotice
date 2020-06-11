package com.github.driver_c.purenotice;


import java.io.File;
import org.slf4j.Logger;
import java.nio.file.Path;
import java.io.IOException;
import com.google.inject.Inject;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import com.github.driver_c.purenotice.config.ConfigHandler;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;


@Plugin(
        id = "purenotice",
        name = "PureNotice",
        description = "A pure and simple auto notice plugin.",
        authors = {
                "Driver_C"
        }
)
public class PureNotice {

    @Inject
    private Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configDir;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private File defaultConfig;

    private final ConfigHandler configHandler = new ConfigHandler(this);

    @Listener
    public void onServerStarted(GameStartedServerEvent event) {
        this.logger.info("\u00A7aPureNotice is loading.\u00A7r");
        try {
            this.configHandler.load(defaultConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.logger.info("\u00A7aPureNotice loaded.\u00A7r");
    }

    @Listener
    public void onServerStopping(GameStoppingServerEvent event) {
        this.logger.info("\u00A7cPureNotice is disabled.\u00A7r");
    }

    public Logger getLogger() {
        return this.logger;
    }
}
