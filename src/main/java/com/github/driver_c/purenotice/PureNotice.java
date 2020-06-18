package com.github.driver_c.purenotice;


import java.io.File;

import com.github.driver_c.purenotice.command.CommandMain;
import com.github.driver_c.purenotice.config.LanguageConfig;
import com.github.driver_c.purenotice.task.AutoNotice;
import org.slf4j.Logger;
import java.io.IOException;
import java.nio.file.Path;

import com.google.inject.Inject;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
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
    @ConfigDir(sharedRoot = false)
    public Path configDir;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private File defaultConfig;

    private Task autoNoticeTask;

    @Listener
    public void onServerStarting(GameStartingServerEvent event) {
        configInit();
    }

    @Listener
    public void onServerStarted(GameStartedServerEvent event) {
        this.logger.info(
                LanguageConfig.rootNode.getNode("messages", "loading").getString()
        );
        taskInit();
        commandInit();
        this.logger.info(
                LanguageConfig.rootNode.getNode("messages", "loaded").getString()
        );
    }

    @Listener
    public void onServerReload(GameReloadEvent event) {
        this.logger.info(
                LanguageConfig.rootNode.getNode("messages", "reloading").getString()
        );
        configInit();
        taskInit();
        this.logger.info(
                LanguageConfig.rootNode.getNode("messages", "reloaded").getString()
        );
    }

    @Listener
    public void onServerStopping(GameStoppingServerEvent event) {
        taskDown();
        this.logger.info(
                LanguageConfig.rootNode.getNode("messages", "disabled").getString()
        );
    }

    public void configInit() {
        ConfigHandler configHandler = new ConfigHandler(this);
        try {
            configHandler.load(defaultConfig, configDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void taskInit() {
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
        CommandMain commandMain = new CommandMain(this);
        Sponge.getCommandManager().register(
                this, commandMain.pureNotice,"purenotice", "pn"
        );
    }

    public Logger getLogger() {
        return this.logger;
    }
}
