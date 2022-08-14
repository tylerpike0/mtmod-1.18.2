package net.tntninja2.mtmod.item.custom.armor;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;

public class Tier2ArmorItem extends ModArmorItem {

    public Tier2ArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
        super.tier = 2;

    }

}
