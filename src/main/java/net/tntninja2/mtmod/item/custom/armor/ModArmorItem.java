package net.tntninja2.mtmod.item.custom.armor;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.tntninja2.mtmod.mixinInterface.IMixinItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;


public class ModArmorItem extends ArmorItem {

    public boolean hasNbtSet;




    public void setSkill(String skill, int level) {
        this.skills.put(skill, level);
    }

    private Map<String, Integer> skills;


    public ModArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
        this.hasNbtSet = false;
    }






    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
    }
}
