package habe.forge;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ModEvents {

    public static void initialize() {

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(((world, entity, killedEntity, damageSource) -> {
                if (killedEntity instanceof ServerPlayer) {
                    System.out.println(damageSource.getEntity());
                    if (damageSource.getEntity() instanceof ServerPlayer player) {
                        Database.addPoints(player.getUUID().toString(), player.getName().getString(), 100000000);
                        player.sendSystemMessage(Component.literal("YOYOYO DU GEILER WICHS BROKEN DAS WAR EPPPPPIC HIER SIND 100000000 PUUIUUUUUNKTEEEEEEEEE"));
                    }

                }
        }));
    }
}
