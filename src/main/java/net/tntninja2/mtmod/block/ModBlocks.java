package net.tntninja2.mtmod.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.block.custom.AcidBlock;
import net.tntninja2.mtmod.block.custom.MythrilBlock;

public class ModBlocks {

    public static final Block MYTHRIL_BLOCK = registerBlock("mythril_block",
            new MythrilBlock(FabricBlockSettings.of(Material.METAL).strength(6f).requiresTool()), ItemGroup.MISC);

    public static final Block ACID_BLOCK = registerBlock("acid_block",
            new AcidBlock(FabricBlockSettings.of(Material.CARPET).strength(6f).requiresTool()), ItemGroup.MISC);


    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(MTMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(MTMod.MOD_ID, name),
        new BlockItem(block, new FabricItemSettings().group(group)));
    }
    public static void registerModBlocks() {
        MTMod.LOGGER.info("Registering ModBlocks for " + MTMod.MOD_ID);
    }
}
