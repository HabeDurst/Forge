package habe.forge;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ModEvents {

    public static void initialize() {

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(((world, entity, killedEntity, damageSource) -> {
            if (entity instanceof ServerPlayer player && killedEntity instanceof ServerPlayer) {
                Database.addPoints(player.getUUID().toString(), player.getName().getString(), 1);
                player.sendSystemMessage(Component.literal("YOYOYO DU GEILER WICHS BROKEN DAS WAR EPPPPPIC HIER SIND 1 PUUIUUUUUNKTEEEEEEEEE"));
            }
        }));

    }
}
