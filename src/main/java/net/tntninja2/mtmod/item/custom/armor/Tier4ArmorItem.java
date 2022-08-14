package net.tntninja2.mtmod.item.custom.armor;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;

public class Tier4ArmorItem extends ModArmorItem {

    public Tier4ArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
        super.tier = 4;

    }

}
