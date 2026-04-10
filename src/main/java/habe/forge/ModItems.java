package habe.forge;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.List;

public class ModItems {



    private static ItemStack createCustomItem(
            Item baseItem,
            String name,
            String lore,
            int modelData,
            net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute,
            String modifierKey,
            double modifierValue
    ) {
        ItemStack stack =new ItemStack(baseItem);

        stack.set(DataComponents.CUSTOM_NAME,
                Component.literal(name).withStyle(s -> s.withItalic(false)));

        stack.set(DataComponents.LORE, new net.minecraft.world.item.component.ItemLore(
                List.of(Component.literal(lore).withStyle(s -> s.withItalic(false)))));

        CompoundTag tag = new CompoundTag();
        tag.putBoolean("stonebreaker", true);
        tag.putInt("CustomModelData", 101);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));

        stack.set(DataComponents.ITEM_MODEL, Identifier.fromNamespaceAndPath("forge", modelPath));

        stack.set(DataComponents.ATTRIBUTE_MODIFIERS,
                ItemAttributeModifiers.builder()
                        .add(attribute,
                                new AttributeModifier(
                                        Identifier.fromNamespaceAndPath("forge", modifierKey),
                                        modifierValue,
                                        AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND)
                        .build());

        return stack;
    }

    //display

    //swords

    public static ItemStack createDisplayResonator() {
        return createCustomItem(Items.NETHERITE_SWORD, "Resonator",
                "Eine AOE Shockwave alle 3 Hits und natürlich mehr Damage Points9999",100, Attributes.ATTACK_DAMAGE, "resonator_damage", 1.0);
    }



    //ingame

    //swords
    public static ItemStack createResonator() {
        return createCustomItem(Items.NETHERITE_SWORD, "Resonator",
                "Eine AOE Shockwave alle 3 Hits und natürlich mehr Damage",100, Attributes.ATTACK_DAMAGE, "resonator_damage", 1.0);
    }



    //pickaxes
    public static ItemStack createStoneBreaker() {
        return createCustomItem(Items.NETHERITE_PICKAXE, "Stone Breaker",
                "Veinminer & 3x3 Radius und natürlich schneller abbauen (/veinminertoggle)",103, Attributes.BLOCK_BREAK_SPEED, "stonebreaker_speed", 1.0);
    }


    public static ItemStack createEarthShard() {
        return createCustomItem(Items.NETHERITE_PICKAXE, "Earth Shard",
                "Veinminer & 5x5 Radius und natürlich noch schneller abbauen (/veinminertoggle)",104, Attributes.BLOCK_BREAK_SPEED, "earthshard_speed", 1.5);
    }

    public static void initialize() {}
}