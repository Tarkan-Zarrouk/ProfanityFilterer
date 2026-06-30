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
                syntaxResponseHelper();
                return 1;
            }).then(ClientCommandManager.literal("customFilter")).executes(context -> {
                customFilteringHelper();
                return 1;
            }));
        });
        clientChatMessageHelper();
    }
    private static void syntaxResponseHelper() {
        toggleFilter = !toggleFilter;
        if(toggleFilter) {
            MutableComponent clientTag = Component.literal("[ProfanityFilter] enabled").withStyle(ChatFormatting.LIGHT_PURPLE);
            Minecraft.getInstance().player.displayClientMessage(clientTag, false);
            } else {
                MutableComponent clientTag = Component.literal("[ProfanityFilter] disabled").withStyle(ChatFormatting.LIGHT_PURPLE);
                Minecraft.getInstance().player.displayClientMessage(clientTag, false);
            }
        
    }
    private static void customFilteringHelper() {

    }

    private static void clientChatMessageHelper() {
        ClientSendMessageEvents.MODIFY_CHAT.register((message) -> {
            if(!toggleFilter)
                return message;
            if(ProfanityAssister.isProfanity(message)) {
                return "*".repeat(message.length());
            } 
            return message;
        });
    }
}
