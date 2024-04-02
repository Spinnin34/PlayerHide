package p.playershide.utils;

import org.bukkit.command.CommandSender;

import p.playershide.Main;

public class ValidationManager {
    private Main main;

    public ValidationManager(Main main) {
        this.main = main;
    }

    public boolean hasPermission(String permission, CommandSender sender) {
        if (sender.hasPermission(permission)) {
            return true;
        }
        MessageManager.sendMessage(sender, "no-permission");
        return false;
    }
    public boolean playerExist(String name, CommandSender sender) {
        if (name.length() > 16 || this.main.getServer().getOfflinePlayer(name).getFirstPlayed() == 0L) {
            MessageManager.sendMessage(sender, "player-not-exist",
                new String[]{"%player%"},
                new String[]{name});
            return false;
        }
        return true;
    }
}
