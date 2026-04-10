package habe.forge;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;

import static net.minecraft.commands.Commands.literal;

public class ModCommands {

    private static void registerCommand(
            CommandDispatcher<CommandSourceStack> dispatcher, String name, java.util.function.Consumer<ServerPlayer> action) {
        dispatcher.register(literal(name)
                .executes(ctx -> {
                    if (!(ctx.getSource().getEntity() instanceof ServerPlayer player)) {
                        ctx.getSource().sendFailure(Component.literal("mach das ingame nicht in der konsole"));
                        return 0;
                    }
                    action.accept(player);
                    return 1;
                })
        );
    }


    public static void initialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            registerCommand(dispatcher, "shop", player -> ModScreenHandler.openPointsShop(player));
            registerCommand(dispatcher, "points", player -> {
                int points = Database.getPoints(player.getUUID().toString());
                player.sendSystemMessage(Component.literal("§6Du hast§e " + points + " §6Juli Points™"));});
            //registerCommand(dispatcher, "addpoints");
            //registerCommand(dispatcher, "removepoints");

            //swords
            registerCommand(dispatcher, "giveresonator", player -> player.getInventory().add(ModItems.createResonator()));
            registerCommand(dispatcher, "givefuryfang", player -> player.getInventory().add(ModItems.createFuryFang()));
            registerCommand(dispatcher, "giveeclipse", player -> player.getInventory().add(ModItems.createEclipse()));

            //pickaxes
            registerCommand(dispatcher, "givestonebreaker", player -> player.getInventory().add(ModItems.createStoneBreaker()));
            registerCommand(dispatcher, "giveearthshard", player -> player.getInventory().add(ModItems.createEarthShard()));
        });
    }
}