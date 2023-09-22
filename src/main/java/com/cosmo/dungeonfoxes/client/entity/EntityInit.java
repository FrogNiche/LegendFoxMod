package com.cosmo.dungeonfoxes.client.entity;

import com.cosmo.dungeonfoxes.DungeonFoxes;

import com.cosmo.dungeonfoxes.client.entity.royal_foxes.wolfie_mounder.WolfieMounderEntity;
import com.cosmo.dungeonfoxes.client.entity.royal_foxes.furball.FurballEntity;
import com.cosmo.dungeonfoxes.client.entity.royal_foxes.king_paws.KingPawsEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.
                    ENTITY_TYPES,
            DungeonFoxes.MOD_ID);

     public static final RegistryObject<EntityType<KingPawsEntity>> KING_PAWS = register("king_paws", EntityType.Builder.of(KingPawsEntity::new,
                    MobCategory.MONSTER));

    public static final RegistryObject<EntityType<WolfieMounderEntity>> WOLFIE_MOUNDER = register("wolfie_mounder", EntityType.Builder.of(WolfieMounderEntity::new,
            MobCategory.MONSTER));

    public static final RegistryObject<EntityType<FurballEntity>> FURBALL = register("furball", EntityType.Builder.of(FurballEntity::new,
            MobCategory.MONSTER));


    public static final <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder){
        return ENTITIES.register(name, () -> builder.build(DungeonFoxes.modLoc(name).toString()));
    }
}