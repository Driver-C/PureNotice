package com.github.driver_c.purenotice.task;


import com.github.driver_c.purenotice.config.ConfigHandler;
import org.spongepowered.api.scheduler.Task;
import com.github.driver_c.purenotice.PureNotice;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.concurrent.TimeUnit;


public class AutoNotice {
    private final PureNotice plugin;

    public AutoNotice(PureNotice plugin) {
        this.plugin = plugin;
    }

    public Task submit() {
        return Task.builder()
                .execute(() -> {
                    String testMsg = ConfigHandler.rootNode.getNode("messages", "msg1", "message").getString();
                        if (testMsg != null) {
                            MessageChannel.TO_ALL.send(
                                TextSerializers.FORMATTING_CODE.deserialize(testMsg)
                            );
                        }
                })
                .interval(3, TimeUnit.SECONDS)
                .delay(1,TimeUnit.SECONDS)
                .async()
                .submit(this.plugin);
    }
}
