package com.github.driver_c.purenotice.task;


import com.github.driver_c.purenotice.config.ConfigHandler;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.slf4j.Logger;
import org.spongepowered.api.scheduler.Task;
import com.github.driver_c.purenotice.PureNotice;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.security.SecureRandom;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class AutoNotice {
    private final PureNotice plugin;
    private final int totalMessage;
    private final List<String> messageList;
    private int index;
    private final Boolean configRandom;

    public AutoNotice(PureNotice plugin) {
        this.plugin = plugin;
        CommentedConfigurationNode messages = ConfigHandler.rootNode.getNode("messages");
        this.messageList = messages.getChildrenMap().keySet().stream().map(Object::toString).collect(Collectors.toList());
        this.messageList.sort(Comparator.comparingInt(Integer::parseInt));
        this.totalMessage = messages.getChildrenMap().keySet().size();
        this.configRandom = ConfigHandler.rootNode.getNode("main", "random").getBoolean();
        this.index = 0;
    }

    public Task submit() {
        Logger logger = this.plugin.getLogger();
        logger.info(String.valueOf(this.messageList));
        int interval = ConfigHandler.rootNode.getNode("main", "interval").getInt();
        int delay = ConfigHandler.rootNode.getNode("main", "firstDelay").getInt();
        return Task.builder()
                .execute(() -> {
                    if (this.configRandom) {
                        SecureRandom randomIndex = new SecureRandom();
                        this.index = randomIndex.nextInt(this.totalMessage);
                    }
                    String message = ConfigHandler.rootNode.getNode("main", "prefix").getString()
                            + ConfigHandler.rootNode.getNode(
                                    "messages", this.messageList.get(this.index), "message"
                    ).getString();
                    MessageChannel.TO_ALL.send(TextSerializers.FORMATTING_CODE.deserialize(message));
                    counter();
                })
                .interval(interval, TimeUnit.SECONDS)
                .delay(delay,TimeUnit.SECONDS)
                .async()
                .submit(this.plugin);
    }

    private void counter() {
        if (!this.configRandom) {
            if (this.index >= totalMessage - 1) {
                this.index = 0;
            } else {
                this.index++;
            }
        }
    }
}
