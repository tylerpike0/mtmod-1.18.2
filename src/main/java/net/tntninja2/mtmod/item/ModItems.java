package net.tntninja2.mtmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemGroup;
import net.tntninja2.mtmod.MTMod;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.tntninja2.mtmod.entity.ModEntities;
import net.tntninja2.mtmod.item.custom.MythrilIngotItem;
import net.tntninja2.mtmod.item.custom.armor.ModArmorMaterials;
import net.tntninja2.mtmod.item.custom.armor.TestArmorItem;

public class ModItems {
    public static final Item MYTHRIL_INGOT = registerItem("mythril_ingot",
            new MythrilIngotItem(new FabricItemSettings().group(ItemGroup.MISC)));
//    add more items by copying ^^ , lines in en_us, models/item/mythril_ingot.json, mythril_ingot.png
//    and replace all instances of mythril_ingot with new item name


    public static final Item MYTHRIL_GOLEM_SPAWN_EGG = registerItem("mythril_golem_spawn_egg",
            new SpawnEggItem(ModEntities.MYTHRIL_GOLEM, 999999, 000000,
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


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(MTMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MTMod.LOGGER.info("Registering Mod Items for " + MTMod.MOD_ID);
    }
}
