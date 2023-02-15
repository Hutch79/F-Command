package ch.hutch79;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length != 1) return false;

        if (sender instanceof Player) {
            if (!sender.hasPermission("fcommand.admin")) {
                sender.sendMessage("§dF-Command §8> §cYou don't have Permission to execute this command");
                return false;
            }
        }


        if (args[0].equalsIgnoreCase("reload")) {
            Main.getListener().EventListenerInit();
            sender.sendMessage("§dF-Command §8> §7Config has been reloaded");
        }


        return false;
    }
}
