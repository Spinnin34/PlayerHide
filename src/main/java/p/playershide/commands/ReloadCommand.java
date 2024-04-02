package p.playershide.commands;

import org.bukkit.command.CommandSender;

import p.playershide.Main;
import p.playershide.utils.MessageManager;
import p.playershide.utils.ValidationManager;

public class ReloadCommand {

    private final String reloadPerm = "phide.reload";
    private Main main;
    private ValidationManager validationManager;
    public ReloadCommand(Main main) {
        this.main = main;
        this.validationManager = new ValidationManager(main);
    }

    public boolean execute(CommandSender sender) {
        if (!validationManager.hasPermission(reloadPerm, sender)) {
            return true;
        }

        try {
            // reload files
            main.getConfigManager().createConfig();
            main.getConfigManager().createMessageFile();

            // reinitialize manager values
            main.getPlayerManager().initializeValues();

            // re-register events
            main.getEventManager().unregisterEvents();
            main.getEventManager().registerEvents();

            MessageManager.sendMessage(sender, "reload-success");
        } catch (Exception e) {
            main.getLogger().info(e.getMessage());
            MessageManager.sendMessage(sender, "reload-fail");
        }
        return true;
    }
}