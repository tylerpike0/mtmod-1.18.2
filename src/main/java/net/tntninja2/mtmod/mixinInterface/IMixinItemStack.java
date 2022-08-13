package net.tntninja2.mtmod.mixinInterface;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.tntninja2.mtmod.item.custom.armor.ModArmorItem;
import net.tntninja2.mtmod.item.custom.armor.TestArmorItem;
import net.tntninja2.mtmod.util.nbtUtil.ItemDisplayUtil;
import net.tntninja2.mtmod.util.nbtUtil.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface IMixinItemStack {

    boolean hasNbtSet = false;

    default void addOtherNbt() {
    }

    default void addSkillNbt() {
    }


}
