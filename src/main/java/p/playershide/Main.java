package p.playershide;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import p.playershide.commands.CommandParser;
import p.playershide.commands.CommandTabCompleter;
import p.playershide.events.DependencyLoadEvent;
import p.playershide.events.EventManager;
import p.playershide.player.PlayerManager;
import p.playershide.utils.ConfigManager;
import p.playershide.utils.PapiManager;
import p.playershide.worldguard.WorldGuardManager;

public class Main extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");

    // Config
    private FileConfiguration config;

    // managers
    private ConfigManager configManager;
    private PlayerManager playerManager;
    private EventManager eventManager;
    private WorldGuardManager worldGuardManager;

    @Override
    public void onLoad() {
        if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null) {
            this.worldGuardManager = new WorldGuardManager(this);
            getLogger().info("Successfully integrated with WorldGuard!");
        }
    }

    @Override
    public void onDisable() {
        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }

    @Override
    public void onEnable() {


        this.configManager = new ConfigManager(this);

        // config setup
        configManager.createConfig();
        configManager.createMessageFile();

        // initialize events
        this.eventManager = new EventManager(this);

        // initialize worldguard session handler
        if (worldGuardManager != null) {
            worldGuardManager.initializeHandler();
        }

        //this.createScheduleConfig();
        this.getCommand("phide").setTabCompleter(new CommandTabCompleter());
        this.getCommand("phide").setExecutor(new CommandParser(this));

        try {
            this.playerManager = new PlayerManager(this);
        } catch (NullPointerException e) {
            this.getLogger().info(e.getMessage());
            this.getLogger().info("[PlayerHide] Es tuyo config.yml " +
                "actualizado/configurado correctamente");
            getServer().getPluginManager().disablePlugin(this);
        }

        if (Bukkit.getVersion().contains("1.8") || Bukkit.getVersion().contains("1.9")
            || Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11")
            || Bukkit.getVersion().contains("1.12")) {
            Bukkit.getScheduler().runTaskLater(this, this::loadDependencies, 1);
        } else {
            this.getServer().getPluginManager().registerEvents(new DependencyLoadEvent(this), this);
        }
    }


    public void loadDependencies() {
        checkPlaceholderAPI();
    }

    private void checkPlaceholderAPI() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info(String.format("[%s] - PlaceholderAPI encontrado, integrado con el complemento!", getDescription().getName()));
            new PapiManager(this).register();
        }
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public void setConfig(FileConfiguration config) {
        this.config = config;
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    public EventManager getEventManager() {
        return this.eventManager;
    }

    public WorldGuardManager getWorldGuardManager() {
        return this.worldGuardManager;
    }
}