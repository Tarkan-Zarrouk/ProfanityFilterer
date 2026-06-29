package com.filter.command;

import com.filter.helper.ProfanityAssister;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ToggleFilterCommand {
    private static boolean toggleFilter = false; // by default
    public static void init() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, __) -> {
            dispatcher.register(ClientCommandManager.literal("toggleProfanityFilter").executes(context -> {
                toggleFilter = !toggleFilter;
                if(toggleFilter) {
                    MutableComponent clientTag = Component.literal("[ProfanityFilter] enabled").withStyle(ChatFormatting.LIGHT_PURPLE);
                    Minecraft.getInstance().player.displayClientMessage(clientTag, false);
                } else {
                    MutableComponent clientTag = Component.literal("[ProfanityFilter] disabled").withStyle(ChatFormatting.LIGHT_PURPLE);
                    Minecraft.getInstance().player.displayClientMessage(clientTag, false);
                }
                return 1;
            }));
        });
        ClientSendMessageEvents.MODIFY_CHAT.register((message) -> {
            if(!toggleFilter)
                return message;
            if(ProfanityAssister.isProfanity(message)) {
                return message.replace(message, "[REDACTED]");
            } 
            return message;
        });
    }
}
