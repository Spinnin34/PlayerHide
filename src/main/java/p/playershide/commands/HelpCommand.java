package p.playershide.commands;

import org.bukkit.command.CommandSender;

import p.playershide.Main;
import p.playershide.utils.MessageManager;
import p.playershide.utils.ValidationManager;



public class HelpCommand {

    private final String helpPerm = "phide.help";
    private ValidationManager validationManager;

    public HelpCommand(Main main) {
        this.validationManager = new ValidationManager(main);
    }

    public boolean execute(CommandSender sender, String[] args) {
        if (!validationManager.hasPermission(helpPerm, sender)) {
            return true;
        }

        try {
            int pageNum = Integer.parseInt(args[1]);
            MessageManager.showHelpBoard(sender, pageNum);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            MessageManager.showHelpBoard(sender, 1);
        }
        return true;
    }
}

