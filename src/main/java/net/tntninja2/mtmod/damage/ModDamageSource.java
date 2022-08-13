package net.tntninja2.mtmod.damage;

import net.minecraft.entity.damage.DamageSource;

public class ModDamageSource extends DamageSource {
    public static final ModDamageSource ARMOR_SKILL = (ModDamageSource) (new ModDamageSource("armorSkill")).setBypassesArmor();

    public ModDamageSource(String armorSkill) {
        super(armorSkill);
    }
}
