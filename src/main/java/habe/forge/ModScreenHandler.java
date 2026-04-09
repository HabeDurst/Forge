package habe.forge;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModScreenHandler {

    private static boolean hasItem(ServerPlayer player, java.util.function.Predicate<ItemStack> check) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            if (check.test(player.getInventory().getItem(i))) return true;
        }
        return false;
    }

    public static void openPointsShop(ServerPlayer player) {
        try {
            net.minecraft.world.SimpleContainer inv = new SimpleContainer(54);

            ItemStack filler = new ItemStack(Items.GRAY_STAINED_GLASS_PANE);
            filler.set(DataComponents.CUSTOM_NAME, Component.literal(" "));
            for (int i = 0; i < 54; i++) inv.setItem(i, filler.copy());
            inv.setItem(1, ModItems.createDisplayResonator());
            inv.setItem(3, ModItems.createStoneBreaker());
            inv.setItem(5, ModItems.createEarthShard());

            player.openMenu(new net.minecraft.world.MenuProvider() {
                public Component getDisplayName() {
                    return Component.literal("Punkte Shop");
                }

                public net.minecraft.world.inventory.AbstractContainerMenu createMenu(
                        int syncId, net.minecraft.world.entity.player.Inventory playerInv, net.minecraft.world.entity.player.Player p) {
                    return new ChestMenu(MenuType.GENERIC_9x6, syncId, playerInv, inv, 6) {
                        @Override
                        public void clicked(int slotId, int button, ClickType clickType, net.minecraft.world.entity.player.Player pl) {
                            if (slotId >= 0 && slotId < 54) {
                                if (!(pl instanceof ServerPlayer sp)) return;

                                String uuid = sp.getUUID().toString();
                                String name = sp.getName().getString();
                                int points = Database.getPoints(uuid);

                                if (slotId == 1) {
                                    boolean hasReq  = hasItem(sp, s -> s .getItem() == Items.NETHERITE_SWORD);
                                    if (!hasReq) {
                                        sp.sendSystemMessage(Component.literal("§c Du brauchst vorher ein Netherite Schwert!"));
                                        return;
                                    }
                                    if (points < 500) {
                                        sp.sendSystemMessage(Component.literal("§c Nicht Genug Punkte (500)"));
                                        return;
                                    }
                                    Database.removePoints(uuid, name, 500);
                                    sp.getInventory().add(ModItems.createResonator());
                                    sp.sendSystemMessage(Component.literal("§c Erfolgreich gekauft!"));
                                }
                            }
                        }
                    };
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            player.sendSystemMessage(Component.literal("ERROR: " + e.getMessage()));


        }
    }
}