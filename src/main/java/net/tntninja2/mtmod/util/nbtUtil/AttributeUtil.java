package net.tntninja2.mtmod.util.nbtUtil;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.text.TranslatableText;

import java.util.UUID;

public abstract class AttributeUtil extends EntityAttribute{

    private static final DynamicCommandExceptionType ENTITY_FAILED_EXCEPTION = new DynamicCommandExceptionType((name) -> {
        return new TranslatableText("commands.attribute.failed.entity", new Object[]{name});
    });
    private static final Dynamic2CommandExceptionType NO_ATTRIBUTE_EXCEPTION = new Dynamic2CommandExceptionType((entityName, attributeName) -> {
        return new TranslatableText("commands.attribute.failed.no_attribute", new Object[]{entityName, attributeName});
    });
    private static final Dynamic3CommandExceptionType NO_MODIFIER_EXCEPTION = new Dynamic3CommandExceptionType((entityName, attributeName, uuid) -> {
        return new TranslatableText("commands.attribute.failed.no_modifier", new Object[]{attributeName, entityName, uuid});
    });
    private static final Dynamic3CommandExceptionType MODIFIER_ALREADY_PRESENT_EXCEPTION = new Dynamic3CommandExceptionType((entityName, attributeName, uuid) -> {
        return new TranslatableText("commands.attribute.failed.modifier_already_present", new Object[]{uuid, attributeName, entityName});
    });

    public AttributeUtil(String translationKey, double fallback) {
        super(translationKey, fallback);
    }

    public static EntityAttributeInstance getAttributeInstance(Entity entity, EntityAttribute attribute) throws CommandSyntaxException {
        EntityAttributeInstance entityAttributeInstance = getLivingEntity(entity).getAttributes().getCustomInstance(attribute);
        if (entityAttributeInstance == null) {
            throw NO_ATTRIBUTE_EXCEPTION.create(entity.getName(), new TranslatableText(attribute.getTranslationKey()));
        } else {
            return entityAttributeInstance;
        }
    }

    public static LivingEntity getLivingEntity(Entity entity) throws CommandSyntaxException {
        if (!(entity instanceof LivingEntity)) {
            throw ENTITY_FAILED_EXCEPTION.create(entity.getName());
        } else {
            return (LivingEntity)entity;
        }
    }

    public static LivingEntity getLivingEntityWithAttribute(Entity entity, EntityAttribute attribute) throws CommandSyntaxException {
        LivingEntity livingEntity = getLivingEntity(entity);
        if (!livingEntity.getAttributes().hasAttribute(attribute)) {
            throw NO_ATTRIBUTE_EXCEPTION.create(entity.getName(), new TranslatableText(attribute.getTranslationKey()));
        } else {
            return livingEntity;
        }
    }

    public static void attributeModifierAddOrReplace(LivingEntity target, EntityAttribute attribute, UUID uuid, String name, double value, EntityAttributeModifier.Operation operation) throws CommandSyntaxException {
        EntityAttributeInstance entityAttributeInstance = getAttributeInstance(target, attribute);
        EntityAttributeModifier entityAttributeModifier = new EntityAttributeModifier(uuid, name, value, operation);
        if (entityAttributeInstance.hasModifier(entityAttributeModifier)) {
            if (entityAttributeInstance.getValue() != value) {
                target.getAttributeInstance(attribute).tryRemoveModifier(uuid);
                entityAttributeInstance.addPersistentModifier(entityAttributeModifier);
            }
        } else {
            entityAttributeInstance.addPersistentModifier(entityAttributeModifier);
        }
    }

    public static void attributeModifierRemove(Entity target, EntityAttribute attribute, UUID uuid) throws CommandSyntaxException {
        EntityAttributeInstance entityAttributeInstance = getAttributeInstance(target, attribute);
        entityAttributeInstance.tryRemoveModifier(uuid);
    }
}
