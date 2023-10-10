package com.frogniche.legendfoxes.server.entity;

import com.frogniche.legendfoxes.LegendFoxes;
import com.frogniche.legendfoxes.server.entity.horde_of_the_bastion.unbreakable.UnbreakableEntity;
import com.frogniche.legendfoxes.server.entity.horde_of_the_hunt.beast.BeastEntity;
import com.frogniche.legendfoxes.server.entity.horde_of_the_hunt.foxxo.FoxxoEntity;
import com.frogniche.legendfoxes.server.entity.horde_of_the_spore.devourer.EntityDevourer;
import com.frogniche.legendfoxes.server.entity.horde_of_the_spore.test.TestEntity;
import com.frogniche.legendfoxes.server.entity.variants.blaze_runt.BlazeRuntEntity;
import com.frogniche.legendfoxes.server.entity.variants.mace_runt.MaceRuntEntity;
import com.frogniche.legendfoxes.server.entity.variants.seeker.SeekerEntity;
import com.frogniche.legendfoxes.server.entity.variants.spore_medic.SporeMedicEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LFEntityHandler {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.
                    ENTITY_TYPES,
            LegendFoxes.MOD_ID);

     public static final RegistryObject<EntityType<EntityDevourer>> DEVOURER = register("devourer", EntityType.Builder.of(EntityDevourer::new,
                    MobCategory.MONSTER).sized(2f, 7f));

    public static final RegistryObject<EntityType<TestEntity>> TEST = register("test",
            EntityType.Builder.of(TestEntity::new,
            MobCategory.MONSTER).sized(2f, 7f));



    public static final RegistryObject<EntityType<BeastEntity>> BEAST = register("beast", EntityType.Builder.of(BeastEntity::new,
            MobCategory.MONSTER));


    public static final RegistryObject<EntityType<FoxxoEntity>> FOXXO = register("foxxo", EntityType.Builder.of(FoxxoEntity::new,
            MobCategory.MONSTER));

    public static final RegistryObject<EntityType<UnbreakableEntity>> UNBREAKABLE = register("unbreakable", EntityType.Builder.of(UnbreakableEntity::new,
            MobCategory.MONSTER).sized(2f, 7f));

    public static final RegistryObject<EntityType<SporeMedicEntity>> SPORE_MEDIC = register("spore_medic",EntityType.Builder.of(SporeMedicEntity::new,
            MobCategory.MONSTER));

    public static final RegistryObject<EntityType<MaceRuntEntity>> MACE_RUNT = register("mace_runt",EntityType.Builder.of(MaceRuntEntity::new,
            MobCategory.MONSTER));

    public static final RegistryObject<EntityType<BlazeRuntEntity>> BLAZE_RUNT = register("blaze_runt",EntityType.Builder.of(BlazeRuntEntity::new,
            MobCategory.MONSTER));

    public static final RegistryObject<EntityType<SeekerEntity>> SEEKER = register("seeker", EntityType.Builder.of(SeekerEntity::new,
            MobCategory.MONSTER));


    public static final <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder){
        return ENTITIES.register(name, () -> builder.build(LegendFoxes.modLoc(name).toString()));
    }
}