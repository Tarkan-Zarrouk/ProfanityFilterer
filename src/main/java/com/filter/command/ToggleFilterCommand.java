package com.filter.command;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;

public class ToggleFilterCommand {
    public static void init() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, __) -> {
            dispatcher.register(ClientCommandManager.literal("toggleProfanityFilter").executes(context -> {
                ClientSendMessageEvents.MODIFY_CHAT.register((message) -> {
                    return message;
                });
                return 1;
            }));
        });
    }
}
