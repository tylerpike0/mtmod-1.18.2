package net.tntninja2.mtmod.mixin;

import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.item.custom.armor.TestArmorItem;
import net.tntninja2.mtmod.mixinInterface.IMixinItemStack;
import net.tntninja2.mtmod.item.custom.armor.ModArmorItem;
import net.tntninja2.mtmod.util.nbtUtil.ItemDisplayUtil;
import net.tntninja2.mtmod.util.nbtUtil.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mixin(ItemStack.class)
public abstract class MixinItemStack implements IMixinItemStack {

    boolean hasNbtSet = false;




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
            if (!hasNbtSet) {
                this.addSkillNbt();
                this.addOtherNbt();
                hasNbtSet = true;
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


            List<String> armorPieces = new ArrayList<>();
            if (equipmentSlot.getName() == "head") {
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
            } else if (equipmentSlot.getName() == "chest") {
                armorPieces.add("Shirt");
                armorPieces.add("Suit");
                armorPieces.add("Robe");
                armorPieces.add("Chestplate");
                armorPieces.add("Sweater");
            } else if (equipmentSlot.getName() == "legs") {
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

            } else if (equipmentSlot.getName() == "feet") {
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
            suffixes.add("The Slaughterer of Dreams");
            suffixes.add("Zjangio");
            suffixes.add("Doe");

            String adjective = Util.randomStringElement(adjectives);
            String armorPiece = Util.randomStringElement(armorPieces);
            String firstName = Util.randomStringElement(firstNames);
            String suffix = Util.randomStringElement(suffixes);

            ((ItemStack) (Object) this).getOrCreateSubNbt("display").putString("Name", ItemDisplayUtil.NameJson("The " + adjective + " " +  armorPiece + " of " + firstName + " " + suffix , "#FF00FF", true));
        }
    }


    public void addSkillNbt() {

        Item item = ((ItemStack) ((Object) this)).getItem();
        if (item instanceof TestArmorItem) {
            ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").putInt("health_boost", 2);
            ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").putInt("healing", 2);
            ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").putInt("berserker", 2);
            ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").putInt("peak_performance", 2);
            ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").putInt("melee_boost", 2);
            ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").putInt("luck", 2);
            ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").putInt("pain_for_power", 2);
            ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").putInt("magic_damage", 2);
            ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").putInt("mana_regen", 2);
            ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").putInt("resistance", 2);

            ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").putInt("leeching", 2);
            ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").putInt("evasion", 2);
            ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").putInt("preserving", 2);
            ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").putInt("afflicting", 2);
            ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").putInt("combo", 2);
            ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").putInt("fury", 2);
            ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").putInt("fearless", 2);
            ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").putInt("fearful", 2);
            ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").putInt("greedy", 2);
            ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").putInt("shattering", 2);



            NbtList lore = new NbtList();
//            NbtElement loreLine1 = ItemDisplayUtil.LoreLineJson("line1","#00FF00",false,true);
//            NbtElement loreLine3 = ItemDisplayUtil.LoreLineJson("line3","#0000FF",false,true);
//            NbtElement loreLine2 = ItemDisplayUtil.LoreLineJson("line2","#00FFFF",false,true);
//            lore.add(loreLine1);
//            lore.add(loreLine2);
//            lore.add(loreLine3);

            Iterator<String> skillKeys = ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").getKeys().iterator();
            while (skillKeys.hasNext()) {
                String skillKey = skillKeys.next();
                int level = ((ItemStack) (Object) this).getOrCreateSubNbt("mtmod:armor_skills").getInt(skillKey);
                if (level > 0) {
                    String skillName = skillKey.replace('_',' ');
                    String capitalizedSkillName = Util.firstLettersToUpper(skillName);
                    lore.add(ItemDisplayUtil.LoreLineJson(capitalizedSkillName + " " + level, "#86147e", false,  false));
                }
            }
            ((ItemStack) (Object) this).getOrCreateSubNbt("display").put("Lore", lore);

        }


    }
}
