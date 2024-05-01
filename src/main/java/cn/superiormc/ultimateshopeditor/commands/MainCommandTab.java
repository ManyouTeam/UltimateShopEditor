package cn.superiormc.ultimateshopeditor.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class MainCommandTab implements TabCompleter {

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tempVal1 = new ArrayList<>();
        switch (args.length) {
            case 1 :
                if (sender.hasPermission("ultimateshop.editor")) {
                    tempVal1.add("open");
                }
        }
        return tempVal1;
    }
}
