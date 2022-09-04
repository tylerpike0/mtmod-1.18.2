
        package net.tntninja2.mtmod.util;

        import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
        import net.tntninja2.mtmod.entity.ModEntities;
        import net.tntninja2.mtmod.entity.custom.*;

        public class ModRegistries {
    public static void registerModStuffs() {
        registerAttributes();
    }



    private static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntities.MYTHRIL_GOLEM, MythrilGolemEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.ACID_SLIME, AcidSlimeEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.WINGED_BEAST, AcidSlimeEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.PET_TEST, PetTestEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SLATE_JUMPER, SlateJumperEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SLATE_CANNON, SlateCannonEntity.setAttributes());
    }
}
