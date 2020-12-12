package io.github.nircuito.blassedgkit.utils;

import io.github.nircuito.blassedgkit.configuration.KitConfig;
import org.bukkit.entity.Player;

public class CooldownKit {
    public String getCooldown(Player p, String namegkit, long cooldown) {
        String pathtime = "Gkit." + namegkit + "." + p.getUniqueId() + ".cooldown-gkit";
        if (KitConfig.getConfig().contains(pathtime)) {
            String timecooldownString = KitConfig.getConfig().getString(pathtime);
            long timecooldown = Long.valueOf(timecooldownString);
            long millis = System.currentTimeMillis();

            long cooldownmil = cooldown * 1000;

            long espera = millis - timecooldown;
            long esperaDiv = espera / 1000;
            long esperatotalseg = cooldown - esperaDiv;
            long esperatotalmin = esperatotalseg / 60;
            long esperatotalhour = esperatotalmin / 60;
            if (((timecooldown + cooldownmil) > millis) && (timecooldown != 0)) {
                if (esperatotalseg > 59) {
                    esperatotalseg = esperatotalseg - 60 * esperatotalmin;
                }
                String time = "";
                if (esperatotalseg != 0) {
                    time = esperatotalseg + "s";
                }

                if (esperatotalmin > 59) {
                    esperatotalmin = esperatotalmin - 60 * esperatotalhour;
                }
                if (esperatotalmin > 0) {
                    time = esperatotalmin + "min" + " " + time;
                }

                if (esperatotalhour > 0) {
                    time = esperatotalhour + "h" + " " + time;
                }

                //Aun no se termina el cooldown
                return time;
            } else {
                //Ya se termino el cooldown
                KitConfig.getConfig().set(pathtime, millis);
                KitConfig.save();
                return "-1";
            }
        } else {
            //Usa el comando por primera vez, ya que no existe el path en la config
            long millis = System.currentTimeMillis();
            KitConfig.getConfig().set(pathtime, millis);
            KitConfig.save();
            return "-1";
         }
       }
     }


