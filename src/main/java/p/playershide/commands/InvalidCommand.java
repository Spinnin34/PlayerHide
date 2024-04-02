package p.playershide.commands;

import org.bukkit.command.CommandSender;

import p.playershide.Main;
import p.playershide.utils.MessageManager;


public class InvalidCommand {

  public InvalidCommand() {}

  public boolean execute(CommandSender sender) {
    MessageManager.sendMessage(sender, "invalid-command");
    return true;
  }
}
