package com.github.driver_c.purenotice.task;


import com.github.driver_c.purenotice.config.ConfigHandler;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.slf4j.Logger;
import org.spongepowered.api.scheduler.Task;
import com.github.driver_c.purenotice.PureNotice;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class AutoNotice {
    private final PureNotice plugin;
    private final int totalMessage;
    private final List<Object> messageList;
    private int index;

    public AutoNotice(PureNotice plugin) {
        this.plugin = plugin;
        CommentedConfigurationNode messages = ConfigHandler.rootNode.getNode("messages");
        this.messageList = new ArrayList<>(messages.getChildrenMap().keySet());
        this.totalMessage = messages.getChildrenMap().keySet().size();
        this.index = 0;
    }

    public Task submit() {
        Logger logger = this.plugin.getLogger();
        logger.info(String.valueOf(this.messageList));
        return Task.builder()
                .execute(() -> {
                    String message = ConfigHandler.rootNode.getNode(
                            "messages", this.messageList.get(this.index), "message"
                    ).getString();
                    if (message != null) {
                        MessageChannel.TO_ALL.send(
                            TextSerializers.FORMATTING_CODE.deserialize(message)
                        );
                    }
                    counter();
                })
                .interval(3, TimeUnit.SECONDS)
                .delay(1,TimeUnit.SECONDS)
                .async()
                .submit(this.plugin);
    }

    private void counter() {
        if (this.index >= totalMessage - 1) {
            this.index = 0;
        } else {
            this.index++;
        }
    }
}
