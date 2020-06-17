package com.github.driver_c.purenotice;


import java.io.File;

import com.github.driver_c.purenotice.command.CommandMain;
import com.github.driver_c.purenotice.task.AutoNotice;
import org.slf4j.Logger;
import java.io.IOException;
import com.google.inject.Inject;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.config.DefaultConfig;
import com.github.driver_c.purenotice.config.ConfigHandler;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.scheduler.Task;


@Plugin(
        id = "purenotice",
        name = "PureNotice",
        description = "A pure and simple auto notice plugin.",
        authors = {
                "Driver_C"
        }
)
public class PureNotice {

    public final static String VERSION = "1.0.0";

    @Inject
    private Logger logger;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private File defaultConfig;

    private final ConfigHandler configHandler = new ConfigHandler(this);
    private final CommandMain commandMain = new CommandMain(this);
    private Task autoNoticeTask;

    @Listener
    public void onServerStarting(GameStartingServerEvent event) {
        configInit();
    }

    @Listener
    public void onServerStarted(GameStartedServerEvent event) {
        this.logger.info("\u00A7aPureNotice is loading.\u00A7r");
        allInit();
        commandInit();
        this.logger.info("\u00A7aPureNotice loaded.\u00A7r");
    }

    @Listener
    public void onServerReload(GameReloadEvent event) {
        this.logger.info("\u00A7aPureNotice is reloading.\u00A7r");
        allInit();
        this.logger.info("\u00A7aPureNotice reloaded.\u00A7r");
    }

    @Listener
    public void onServerStopping(GameStoppingServerEvent event) {
        taskDown();
        this.logger.info("\u00A7cPureNotice is disabled.\u00A7r");
    }

    private void configInit() {
        try {
            this.configHandler.load(defaultConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void taskInit() {
        taskDown();
        AutoNotice autoNotice = new AutoNotice(this);
        this.autoNoticeTask = autoNotice.submit();
    }

    private void taskDown() {
        if (this.autoNoticeTask != null) {
            this.autoNoticeTask.cancel();
        }
    }

    private void commandInit() {
        Sponge.getCommandManager().register(this, this.commandMain.pureNotice,"purenotice", "pn");
    }

    public void allInit() {
        configInit();
        taskInit();
    }

    public Logger getLogger() {
        return this.logger;
    }
}
