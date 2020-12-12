package io.github.nircuito.blassedgkit.commands;

import io.github.nircuito.blassedgkit.BlassedGkit;
import io.github.nircuito.blassedgkit.configuration.KitConfig;
import io.github.nircuito.blassedgkit.utils.CooldownKit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GkitCommand implements CommandExecutor {
    public String nameGkit;
    private long cooldowntime;
    String blassed = BlassedGkit.CC("&6[&eBlassed&6]");
    String no_permisos = BlassedGkit.CC("&6[BlassedGkit] &cUsted no tiene permisos para usar este comando");
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            BlassedGkit.BukkitConsole(ChatColor.YELLOW+"[BlassedGkit] No puedes usar este comando en la consola");
        }else
            if(args.length < 1) {

                Player p = (Player) sender;
                if(!p.hasPermission("blassed.admin.gkit")) {
                    p.sendMessage(BlassedGkit.CC("&6[&e&lBlassed Gkit&6]"));
                    p.sendMessage(BlassedGkit.CC("&e/Gkit claim <NameGkit>"));
                    p.sendMessage(BlassedGkit.CC("&e/Gkit info <NameGkit>"));

                }else {
                    p.sendMessage(BlassedGkit.CC("&6[&e&lBlassed Gkit Admin&6]"));
                    p.sendMessage(BlassedGkit.CC("&e/Gkit create <NameGkit>"));
                    p.sendMessage(BlassedGkit.CC("&e/Gkit delete <NameGkit>"));
                    p.sendMessage(BlassedGkit.CC("&e/Gkit fill <NameGkit>"));
                    p.sendMessage(BlassedGkit.CC("&e/Gkit cooldown <NameGkit> <Tiempo en segundos>"));

                }
            }
            else if (args[0].equalsIgnoreCase("create")) {
                Player p = (Player) sender;
                if(p.hasPermission("blassed.gkit.create")) {
                    if (args.length < 2) {
                        p.sendMessage(BlassedGkit.CC(blassed+" Use /Gkit create <NewGkit>"));
                    } else if (args.length < 3) {
                        nameGkit = args[1];
                        if (!(KitConfig.getConfig().isSet("Gkit." + nameGkit))) {
                            KitConfig.getConfig().set("Gkit." + nameGkit, "");
                            KitConfig.save();
                            p.sendMessage(BlassedGkit.CC(blassed + " El Gkit " + nameGkit + " se creo con exito"));
                        }else {
                            p.sendMessage(blassed+" Este gkit ya existe");
                        }
                    }
                }else {
                    p.sendMessage(no_permisos);
                }
            }
            else if(args[0].equalsIgnoreCase("delete")) {
                Player p = (Player) sender;
                if (p.hasPermission("blassed.gkit.delete")) {
                    if (args.length < 2) {
                        p.sendMessage(BlassedGkit.CC(blassed+" Use /Gkit delete <NewGkit>"));
                    } else if (args.length < 3) {
                        nameGkit = args[1];
                        if (KitConfig.getConfig().isSet("Gkit." + nameGkit)){
                            KitConfig.getConfig().set("Gkit." + nameGkit, null);
                        KitConfig.save();
                        p.sendMessage(BlassedGkit.CC(blassed + " El gkit " + nameGkit + " se borro con exito"));
                     }else {
                            p.sendMessage(blassed+" El gkit no existe");
                        }
                    }
                }else {
                    p.sendMessage(no_permisos);
                }
            }
            else if (args[0].equalsIgnoreCase("fill")) {
                Player p = (Player) sender;
                if (p.hasPermission("blassed.gkit.fill")){
                    if (args.length < 2) {
                        p.sendMessage(BlassedGkit.CC(blassed+" Use /Gkit fill <NameGKIT>"));
                    } else if (args.length < 3) {
                        nameGkit = args[1];
                        if (KitConfig.getConfig().isSet("Gkit." + nameGkit)) {
                            ArrayList<ItemStack> fill = new ArrayList<>();
                            ItemStack[] content = p.getInventory().getContents();
                            for (int i = 0; i < content.length; i++) {
                                ItemStack item = content[i];
                                if (!(item == null)) {
                                    fill.add(item);
                                }
                            }
                            KitConfig.getConfig().set("Gkit." + nameGkit + ".items", fill);
                            KitConfig.save();
                            p.sendMessage(blassed + " Rellenaste el gkit " + nameGkit);
                        }else {
                            p.sendMessage(blassed+" Este gkit no existe");
                        }
                    }
                }else{
                    p.sendMessage(no_permisos);
                }
            }
            else if (args[0].equalsIgnoreCase("claim")) {
                if(args.length < 2) {
                    Player p = (Player) sender;
                    p.sendMessage(BlassedGkit.CC(blassed+" Use /Gkit claim <NameGkit>"));
                }
                else if (args.length < 3) {
                    nameGkit = args[1];
                    Player p = (Player) sender;
                    if (KitConfig.getConfig().isSet("Gkit." + nameGkit)) {
                        if (p.hasPermission("blassed.gkit.claim." + nameGkit)) {
                            if (!(KitConfig.getConfig().isSet("Gkit." + nameGkit + ".cooldowntime"))) {
                                p.sendMessage(blassed + " Temporize su gkit");
                                return true;
                            }
                            cooldowntime = Long.parseLong(Objects.requireNonNull(KitConfig.getConfig().getString("Gkit." + nameGkit + ".cooldowntime")));
                            CooldownKit cd = new CooldownKit();
                            String pathtime = "Gkit." + nameGkit + "." + p.getUniqueId() + ".cooldown-gkit";
                            if (cd.getCooldown(p, nameGkit, cooldowntime).equals("-1")) {
                                long milis = System.currentTimeMillis();
                                KitConfig.getConfig().set(pathtime, milis);
                                KitConfig.save();
                                p.sendMessage(blassed + " Reclamaste el gkit " + nameGkit);
                                List<?> items = KitConfig.getConfig().getList("Gkit." + nameGkit + ".items");
                                for (int i = 0; i < Objects.requireNonNull(items).size(); i++) {
                                    if (!(p.getInventory().firstEmpty() == -1)) {
                                        p.getInventory().addItem((ItemStack) items.get(i));
                                    }else {
                                        p.getWorld().dropItemNaturally(p.getLocation(), (ItemStack) items.get(i));
                                    }
                                }

                            } else {
                                p.sendMessage(BlassedGkit.CC(blassed + " Puedes reclamar este kit en " + cd.getCooldown(p, nameGkit, cooldowntime)));
                            }
                        } else {
                            p.sendMessage(no_permisos);
                        }
                  }else {
                        p.sendMessage(blassed+" El Gkit "+nameGkit+" no existe");
                    }
                }
                else {
                    Player p = (Player) sender;
                    p.sendMessage(BlassedGkit.CC(blassed+" Ese comando no existe"));
                }
            }
            else if (args[0].equalsIgnoreCase("cooldown")) {
                Player p = (Player) sender;
                if (p.hasPermission("blassed.gkit.cooldowncustom")) {
                    if (args.length < 2) {
                        p.sendMessage(BlassedGkit.CC(blassed+" Use /Gkit cooldown <GkitName> <cooldown>"));
                    } else if (args.length < 3) {
                        p.sendMessage(BlassedGkit.CC(blassed+" Use /Gkit cooldown <GkitName> <cooldown>"));
                    } else if (args.length < 4) {
                        nameGkit = args[1];
                        cooldowntime = Long.parseLong(args[2]);
                        KitConfig.getConfig().set("Gkit." + nameGkit + ".cooldowntime", cooldowntime);
                      p.sendMessage(blassed+" Temporizaste el gkit"+ nameGkit);
                    }
               }else {
                    p.sendMessage(no_permisos);
                }
            }
            else if(args[0].equalsIgnoreCase("info")) {
                Player p = (Player) sender;
                if(args.length < 2) {
                    p.sendMessage(BlassedGkit.CC(blassed+" Use /Gkit info <NameGkit>"));
                }
                else if (args.length < 3) {
                    nameGkit = args[1];
                        if (p.hasPermission("blassed.gkit.claim." + nameGkit)) {
                            if (!(KitConfig.getConfig().isSet("Gkit." + nameGkit + ".cooldowntime"))) {
                                p.sendMessage(blassed+" Temporize su gkit");
                                return true;
                            } else{
                                cooldowntime = Long.parseLong(Objects.requireNonNull(KitConfig.getConfig().getString("Gkit." + nameGkit + ".cooldowntime")));
                                CooldownKit cd = new CooldownKit();
                                if (cd.getCooldown(p, nameGkit, cooldowntime).equals("-1")){
                                    p.sendMessage(blassed+" Puedes reclamar el kit "+ nameGkit);
                                }else {
                                    p.sendMessage(blassed+" Te falta "+cooldowntime+" para reclamar el kit "+nameGkit);
                                }
                            }
                        }else {
                            p.sendMessage(blassed+" Tu no puedes reclamar el Gkit "+nameGkit);
                        }
                }
            }
        return false;
    }
}
