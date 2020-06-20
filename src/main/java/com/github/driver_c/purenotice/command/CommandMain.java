package com.github.driver_c.purenotice.command;

import com.github.driver_c.purenotice.PureNotice;
import com.github.driver_c.purenotice.config.LanguageConfig;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import java.util.Objects;

public class CommandMain {
    private PureNotice plugin;

    public CommandMain(PureNotice plugin) {
        this.plugin = plugin;
    }

    public final CommandSpec pureNoticeReload = CommandSpec.builder()
            .description(Text.of(Objects.requireNonNull(
                    LanguageConfig.rootNode.getNode("messages", "commandReloadDesc").getString()
            )))
            .permission("pn.command.admin.reload")
            .executor((src, args) -> {
                src.sendMessage(Text.of(Objects.requireNonNull(
                        LanguageConfig.rootNode.getNode("messages", "reloading").getString()
                )));
                this.plugin.configInit();
                this.plugin.taskInit();
                src.sendMessage(Text.of(Objects.requireNonNull(
                        LanguageConfig.rootNode.getNode("messages", "reloaded").getString()
                )));
                return CommandResult.success();
            })
            .build();

    public final CommandSpec pureNotice = CommandSpec.builder()
            .description(Text.of(Objects.requireNonNull(
                    LanguageConfig.rootNode.getNode("messages", "commandMainDesc").getString()
            )))
            .permission("pn.command.client.main")
            .executor((src, args) -> {
                src.sendMessage(Text.of("--------------------\n"
                                + LanguageConfig.rootNode.getNode("messages", "commandMain").getString()
                                + PureNotice.VERSION + "\n"
                                + LanguageConfig.rootNode.getNode("messages", "commandMainAuthors").getString()
                                + PureNotice.AUTHORS + "\n" + "--------------------\n"
                                + LanguageConfig.rootNode.getNode("messages", "commandMainHelp").getString()
                                + "\n--------------------\n"
                ));
                return CommandResult.success();
            })
            .child(pureNoticeReload, "reload")
            .build();
}
