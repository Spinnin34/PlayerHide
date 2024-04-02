package p.playershide.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import p.playershide.Main;
import p.playershide.player.PlayerManager;


public class PlayerHopOnEvent implements Listener {
    private Main main;


    public PlayerHopOnEvent(Main main) {
        this.main = main;
    }

    @EventHandler
    private void onPlayerJoinServer(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        PlayerManager playerManager = this.main.getPlayerManager();
        if (main.getWorldGuardManager() == null || main.getWorldGuardManager().checkApplyPlayerHide(player)) {
            playerManager.togglePlayer(player);
        }

        playerManager.hidePlayerForThoseInHiddenState(player);

        boolean giveItem = this.main.getConfig().getBoolean("item-on-join");
        if (!giveItem) {
            return;
        }

        main.getPlayerManager().givePlayerItem(player, true);
    }
}
