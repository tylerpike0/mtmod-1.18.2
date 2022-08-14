package net.tntninja2.mtmod.item.custom.armor;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;

public class Tier1ArmorItem extends ModArmorItem {

    public Tier1ArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
        super.tier = 1;
    }



}
