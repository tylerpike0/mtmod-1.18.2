package net.tntninja2.mtmod.mixin;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.World;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.item.custom.armor.TestArmorItem;
import net.tntninja2.mtmod.item.custom.armor.Tier3ArmorItem;
import net.tntninja2.mtmod.mixinInterface.IMixinItemStack;
import net.tntninja2.mtmod.item.custom.armor.ModArmorItem;
import net.tntninja2.mtmod.util.nbtUtil.ItemDisplayUtil;
import net.tntninja2.mtmod.util.nbtUtil.Util;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(ItemStack.class)
public abstract class MixinItemStack implements IMixinItemStack {





    @Shadow
    private final Item item;

    @Shadow
    private final NbtCompound nbt;

    protected MixinItemStack(Item item, NbtCompound nbt) {
        this.item = item;
        this.nbt = nbt;
    }

    @Inject(method = "inventoryTick", at = @At("HEAD"))
    protected void injectInventoryTick(World world, Entity entity, int slot, boolean selected, CallbackInfo info) {
        if (item instanceof ModArmorItem) {
            if (!this.nbt.getBoolean("has_mtmod_armor_nbt_set")) {
                this.addSkillNbt();
                this.addOtherNbt();
                this.nbt.putBoolean("has_mtmod_armor_nbt_set", true);
            }

        }

    }

    public void addOtherNbt() {
        Item item = ((ItemStack) (Object) this).getItem();
        if (item instanceof ModArmorItem) {
            EquipmentSlot equipmentSlot = ((ArmorItem) item).getSlotType();

            List<String> adjectives = new ArrayList<>();
            adjectives.add("Tasty");
            adjectives.add("Cruel");
            adjectives.add("Destructive");
            adjectives.add("Fancy");
            adjectives.add("Playful");
            adjectives.add("Stinky");
            adjectives.add("Majestic");
            adjectives.add("Godlike");
            adjectives.add("Spicy");
            adjectives.add("Painful");
            adjectives.add("Explosive");
            adjectives.add("Radioactive");
            adjectives.add("Fashionable");
            adjectives.add("Dubious");
            adjectives.add("Illegal");
            adjectives.add("Terrible");
            adjectives.add("Sparkly");
            adjectives.add("Smelly");
            adjectives.add("Fluffy");
            adjectives.add("Angelic");
            adjectives.add("Demonic");
            adjectives.add("Holy");
            adjectives.add("Satanic");
            adjectives.add("Unholy");
            adjectives.add("Fragrant");
            adjectives.add("Pleasant");
            adjectives.add("Unpleasant");
            adjectives.add("Disastrous");
            adjectives.add("Comfy");
            adjectives.add("Sus");
            adjectives.add("Bloody");
            adjectives.add("Stalking");
            adjectives.add("Useless");
            adjectives.add("Forsaken");
            adjectives.add("Profane");
            adjectives.add("Forboding");
            adjectives.add("Brooding");
            adjectives.add("Uncomfortable");


            List<String> armorPieces = new ArrayList<>();
            if (Objects.equals(equipmentSlot.getName(), "head")) {
                armorPieces.add("Cap");
                armorPieces.add("Head");
                armorPieces.add("Eyes");
                armorPieces.add("Helmet");
                armorPieces.add("Beanie");
                armorPieces.add("Hat");
                armorPieces.add("Wig");
                armorPieces.add("Hair");
                armorPieces.add("Head");
                armorPieces.add("Ski Mask");
                armorPieces.add("Goggles");
                armorPieces.add("Snorkel");
                armorPieces.add("Hood");
                armorPieces.add("Top Hat");
                armorPieces.add("Fedora");
                armorPieces.add("Bowler Hat");
                armorPieces.add("Visor");
                armorPieces.add("Sunglasses");
                armorPieces.add("Beard");
                armorPieces.add("Moustache");
                armorPieces.add("Scalp");
            } else if (Objects.equals(equipmentSlot.getName(), "chest")) {
                armorPieces.add("Shirt");
                armorPieces.add("Suit");
                armorPieces.add("Robe");
                armorPieces.add("Chestplate");
                armorPieces.add("Sweater");
            } else if (Objects.equals(equipmentSlot.getName(), "legs")) {
                armorPieces.add("Pants");
                armorPieces.add("Swim Trunks");
                armorPieces.add("Slacks");
                armorPieces.add("Leggings");
                armorPieces.add("Knee Pads");
                armorPieces.add("Kneecaps");
                armorPieces.add("Shin Guards");
                armorPieces.add("Underwear");
                armorPieces.add("Diaper");
                armorPieces.add("Soiled Diaper");

            } else if (Objects.equals(equipmentSlot.getName(), "feet")) {
                armorPieces.add("Flip Flops");
                armorPieces.add("Boots");
                armorPieces.add("Feet");
                armorPieces.add("Socks");
                armorPieces.add("Flippers");
                armorPieces.add("Toe Nail Fungus");
                armorPieces.add("Toe Nails");
                armorPieces.add("Toes");
                armorPieces.add("Claws");
            }

            List<String> firstNames = new ArrayList<>();
            firstNames.add("Joe");
            firstNames.add("Alex");
            firstNames.add("Laura");
            firstNames.add("Ythrix");
            firstNames.add("Jane");
            firstNames.add("Bob");

            List<String> suffixes = new ArrayList<>();
            suffixes.add("Johnson");
            suffixes.add("The Rat King");
            suffixes.add("The Prickly Pear");
            suffixes.add("Zjangio");
            suffixes.add("Doe");
            suffixes.add("The Impostor");
            suffixes.add("The Terminator");
            suffixes.add("The Third");

            String adjective = Util.randomStringElement(adjectives);
            String armorPiece = Util.randomStringElement(armorPieces);
            String firstName = Util.randomStringElement(firstNames);
            String suffix = Util.randomStringElement(suffixes);

            ((ItemStack) (Object) this).getOrCreateSubNbt("display").putString("Name", ItemDisplayUtil.NameJson("The " + adjective + " " +  armorPiece + " of " + firstName + " " + suffix , "#FF00FF", true));
        }
    }

    public void incrementSkill(String skill) {
        int skillLevel = ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").getInt(skill);
        if (skillLevel < 3) {
            skillLevel++;
        }
        ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").putInt(skill, skillLevel);

    }


    public void addSkillNbt() {

        ModArmorItem item = ((ModArmorItem) ((ItemStack) ((Object) this)).getItem());
        List<String> skills = getSkills();
        int tier = item.getTier();

        addRandomArmorAmount(item, tier);

        addRandomSkills(skills, tier);
        if (item instanceof TestArmorItem) {
            double armor = 3 + Math.random();

            ((ItemStack) (Object) this).addAttributeModifier(EntityAttributes.GENERIC_ARMOR,
                    new EntityAttributeModifier("armor", armor, EntityAttributeModifier.Operation.ADDITION),
                    item.getSlotType()
            );
            addAllSkillsLevel2();
        }
        NbtList lore = new NbtList();
        for (String skillKey : ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").getKeys()) {
            int level = ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").getInt(skillKey);
            if (level > 0) {
                String skillName = skillKey.replace('_', ' ');
                String capitalizedSkillName = Util.firstLettersToUpper(skillName);
                lore.add(ItemDisplayUtil.LoreLineJson(capitalizedSkillName + " " + level, "#86147e", false, false));
            }
        }
        ((ItemStack) (Object) this).getOrCreateSubNbt("display").put("Lore", lore);

    }

    private void addRandomArmorAmount(ModArmorItem item, int tier) {
        double armor = 0;
        if (tier == 1) {
            armor = 1 + Math.random() * 2;
        } else if (tier == 2) {
            armor = 3 + Math.random() * 2;
        } else if (tier == 3) {
            armor = 5 + Math.random() * 2;
        } else if (tier == 4) {
            armor = 7 + Math.random() * 2;
        }


        ((ItemStack) (Object) this).addAttributeModifier(EntityAttributes.GENERIC_ARMOR,
                new EntityAttributeModifier("armor", armor, EntityAttributeModifier.Operation.ADDITION),
                item.getSlotType()
        );
    }

    private void addAllSkillsLevel2() {
        for (String s : Arrays.asList("health_boost", "healing", "berserker", "peak_performance", "melee_boost", "luck", "pain_for_power", "magic_damage", "mana_regen", "resistance", "leeching", "evasion", "preserving", "afflicting", "combo", "fury", "fearless", "fearful", "greedy", "shattering")) {
            ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").putInt(s, 2);
        }

    }

    @NotNull
    private List<String> getSkills() {
        List<String> skills = new ArrayList<>();
        skills.add("health_boost");
        skills.add("healing");
        skills.add("berserker");
        skills.add("peak_performance");
        skills.add("melee_boost");
        skills.add("luck");
        skills.add("pain_for_power");
        skills.add("magic_damage");
        skills.add("mana_regen");
        skills.add("resistance");
        skills.add("leeching");
        skills.add("evasion");
        skills.add("preserving");
        skills.add("afflicting");
        skills.add("combo");
        skills.add("fury");
        skills.add("fearless");
        skills.add("fearful");
        skills.add("greedy");
        skills.add("shattering");
        return skills;
    }

    private void addRandomSkills(List<String> skills, int tier) {
        if (tier == 1) {
            int skillRolls = Util.randomIntInRange(4,6);
            while (skillRolls > 0) {
                String skill = Util.randomStringElement(skills);
                this.incrementSkill(skill);
                boolean additionalLevel = Math.random() < 0.5;
                if (additionalLevel) {this.incrementSkill(skill);}
                skillRolls--;
            }
        } else if (tier == 2) {
            int skillRolls = Util.randomIntInRange(6,8);
            while (skillRolls > 0) {
                String skill = Util.randomStringElement(skills);
                this.incrementSkill(skill);
                boolean additionalLevel = Math.random() < 0.5;
                if (additionalLevel) {
                    this.incrementSkill(skill);
                }
                skillRolls--;
            }
        } else if (tier == 3) {
            int skillRolls = Util.randomIntInRange(8, 10);
            while (skillRolls > 0) {
                String skill = Util.randomStringElement(skills);
                this.incrementSkill(skill);
                boolean additionalLevel = Math.random() < 0.5;
                if (additionalLevel) {
                    this.incrementSkill(skill);
                }
                skillRolls--;
            }
        } else if (tier == 4) {
            int skillRolls = Util.randomIntInRange(10, 12);
            while (skillRolls > 0) {
                String skill = Util.randomStringElement(skills);
                this.incrementSkill(skill);
                boolean additionalLevel = Math.random() < 0.5;
                if (additionalLevel) {
                    this.incrementSkill(skill);
                }
                skillRolls--;
            }
        }
    }

    public void addOrChangeArmorAttributeModifier(double changeAmount, String name,  EntityAttribute attribute, EntityAttributeModifier modifier, @Nullable EquipmentSlot slot) {
        (((ItemStack) (Object) this)).getOrCreateNbt();
        if (!this.nbt.contains("AttributeModifiers", 9)) {
            this.nbt.put("AttributeModifiers", new NbtList());
        }

        NbtList nbtList = this.nbt.getList("AttributeModifiers", 10);
        boolean hasChangedModifier = false;
        for (NbtElement attributeModifier: nbtList) {
            if (attributeModifier instanceof NbtCompound) {
                if (Objects.equals(((NbtCompound) attributeModifier).getString("Name"), name)) {
                    double amount = ((NbtCompound) attributeModifier).getDouble("Amount");
                    amount = amount + changeAmount;
                    ((NbtCompound) attributeModifier).putDouble("Amount", amount);
                    hasChangedModifier = true;
                }
            }
        }
        this.nbt.put("AttributeModifiers", nbtList);

        if (!hasChangedModifier) {
            ((ItemStack) ((Object) this)).addAttributeModifier(attribute, modifier, slot);
        }

    }
}
