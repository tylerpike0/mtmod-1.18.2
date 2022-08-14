package net.tntninja2.mtmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.tntninja2.mtmod.MTMod;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.tntninja2.mtmod.entity.ModEntities;
import net.tntninja2.mtmod.item.custom.MythrilIngotItem;
import net.tntninja2.mtmod.item.custom.armor.*;
import net.tntninja2.mtmod.item.custom.weapons.KnockbackHammer1Item;

public class ModItems {
    public static final Item MYTHRIL_INGOT = registerItem("mythril_ingot",
            new MythrilIngotItem(new FabricItemSettings().group(ItemGroup.MISC)));
//    add more items by copying ^^ , lines in en_us, models/item/mythril_ingot.json, mythril_ingot.png
//    and replace all instances of mythril_ingot with new item name


    public static final Item MYTHRIL_GOLEM_SPAWN_EGG = registerItem("mythril_golem_spawn_egg",
            new SpawnEggItem(ModEntities.MYTHRIL_GOLEM, 999999, 000000,
                    new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item KNOCKBACK_HAMMER_1_ITEM = registerItem("knockback_hammer_1_item",
            new KnockbackHammer1Item(ToolMaterials.NETHERITE,3.5f,0.9f,
                    new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item TEST_HELMET = registerItem("test_helmet",
            new TestArmorItem(ModArmorMaterials.TEST, EquipmentSlot.HEAD,
                    new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item TEST_CHESTPLATE = registerItem("test_chestplate",
            new TestArmorItem(ModArmorMaterials.TEST, EquipmentSlot.CHEST,
                    new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item TEST_LEGGINGS = registerItem("test_leggings",
            new TestArmorItem(ModArmorMaterials.TEST, EquipmentSlot.LEGS,
                    new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item TEST_BOOTS = registerItem("test_boots",
            new TestArmorItem(ModArmorMaterials.TEST, EquipmentSlot.FEET,
                    new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item TIER1_HELMET = registerItem("tier1_helmet",
            new Tier1ArmorItem(ModArmorMaterials.TEST, EquipmentSlot.HEAD,
                    new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item TIER1_CHESTPLATE = registerItem("tier1_chestplate",
            new Tier1ArmorItem(ModArmorMaterials.TEST, EquipmentSlot.CHEST,
                    new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item TIER1_LEGGINGS = registerItem("tier1_leggings",
            new Tier1ArmorItem(ModArmorMaterials.TEST, EquipmentSlot.LEGS,
                    new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item TIER1_BOOTS = registerItem("tier1_boots",
            new Tier1ArmorItem(ModArmorMaterials.TEST, EquipmentSlot.FEET,
                    new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item TIER2_HELMET = registerItem("tier2_helmet",
            new Tier2ArmorItem(ModArmorMaterials.TEST, EquipmentSlot.HEAD,
                    new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item TIER2_CHESTPLATE = registerItem("tier2_chestplate",
            new Tier2ArmorItem(ModArmorMaterials.TEST, EquipmentSlot.CHEST,
                    new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item TIER2_LEGGINGS = registerItem("tier2_leggings",
            new Tier2ArmorItem(ModArmorMaterials.TEST, EquipmentSlot.LEGS,
                    new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item TIER2_BOOTS = registerItem("tier2_boots",
            new Tier2ArmorItem(ModArmorMaterials.TEST, EquipmentSlot.FEET,
                    new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item TIER3_HELMET = registerItem("tier3_helmet",
            new Tier3ArmorItem(ModArmorMaterials.TEST, EquipmentSlot.HEAD,
                    new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item TIER3_CHESTPLATE = registerItem("tier3_chestplate",
            new Tier3ArmorItem(ModArmorMaterials.TEST, EquipmentSlot.CHEST,
                    new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item TIER3_LEGGINGS = registerItem("tier3_leggings",
            new Tier3ArmorItem(ModArmorMaterials.TEST, EquipmentSlot.LEGS,
                    new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item TIER3_BOOTS = registerItem("tier3_boots",
            new Tier3ArmorItem(ModArmorMaterials.TEST, EquipmentSlot.FEET,
                    new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item TIER4_HELMET = registerItem("tier4_helmet",
            new Tier4ArmorItem(ModArmorMaterials.TEST, EquipmentSlot.HEAD,
                    new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item TIER4_CHESTPLATE = registerItem("tier4_chestplate",
            new Tier4ArmorItem(ModArmorMaterials.TEST, EquipmentSlot.CHEST,
                    new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item TIER4_LEGGINGS = registerItem("tier4_leggings",
            new Tier4ArmorItem(ModArmorMaterials.TEST, EquipmentSlot.LEGS,
                    new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item TIER4_BOOTS = registerItem("tier4_boots",
            new Tier4ArmorItem(ModArmorMaterials.TEST, EquipmentSlot.FEET,
                    new FabricItemSettings().group(ItemGroup.MISC)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(MTMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MTMod.LOGGER.info("Registering Mod Items for " + MTMod.MOD_ID);
    }
}
