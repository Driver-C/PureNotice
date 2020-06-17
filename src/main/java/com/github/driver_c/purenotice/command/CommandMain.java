package com.github.driver_c.purenotice.command;

import com.github.driver_c.purenotice.PureNotice;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class CommandMain {
    private PureNotice plugin;

    public CommandMain(PureNotice plugin) {
        this.plugin = plugin;
    }

    public final CommandSpec pureNoticeReload = CommandSpec.builder()
            .description(Text.of("Reload this plugin."))
            .permission("pn.command.admin.reload")
            .executor((src, args) -> {
                src.sendMessage(Text.of("\u00A7aPureNotice is reloading.\u00A7r"));
                this.plugin.allInit();
                src.sendMessage(Text.of("\u00A7aPureNotice reloaded.\u00A7r"));
                return CommandResult.success();
            })
            .build();

    public final CommandSpec pureNotice = CommandSpec.builder()
            .description(Text.of("PureNotice Version."))
            .permission("pn.command.client.main")
            .executor((src, args) -> {
                src.sendMessage(Text.of("PureNotice version:" + PureNotice.VERSION));
                return CommandResult.success();
            })
            .child(pureNoticeReload, "reload")
            .build();
}
