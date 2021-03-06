/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.chestcommands.hook;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import me.filoghost.chestcommands.ChestCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public enum BungeeCordHook implements PluginHook {

    INSTANCE;

    @Override
    public void setup() {
        if (!Bukkit.getMessenger().isOutgoingChannelRegistered(ChestCommands.getPluginInstance(), "BungeeCord")) {
            Bukkit.getMessenger().registerOutgoingPluginChannel(ChestCommands.getPluginInstance(), "BungeeCord");
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static void connect(Player player, String server) {
        INSTANCE.checkEnabledState();

        if (server.length() == 0) {
            player.sendMessage(ChatColor.RED + "Target server was an empty string, cannot connect to it.");
            return;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        try {
            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(server); // Target Server
        } catch (IOException ex) {
            throw new AssertionError();
        }

        player.sendPluginMessage(ChestCommands.getPluginInstance(), "BungeeCord", byteArrayOutputStream.toByteArray());
    }

}
