package ch.hutch79;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandTab implements org.bukkit.command.TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;
        if (args.length == 1) {
            List<String> tab1 = new ArrayList<>();

            if (player.hasPermission("fcommand.admin")){
                tab1.add("reload");
            }
            return tab1;
        }

        return null;
    }
}
