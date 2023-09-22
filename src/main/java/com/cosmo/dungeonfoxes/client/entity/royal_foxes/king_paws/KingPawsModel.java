package com.cosmo.dungeonfoxes.client.entity.royal_foxes.king_paws;


import com.cosmo.dungeonfoxes.DungeonFoxes;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class KingPawsModel extends GeoModel<KingPawsEntity> {
    @Override
    public ResourceLocation getModelResource(KingPawsEntity object) {
        return DungeonFoxes.modLoc("geo/entity/dungeons_mobs/king_paws.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(KingPawsEntity object) {
        return DungeonFoxes.modLoc("textures/entity/dungeons/king_paws.png");
    }

    @Override
    public ResourceLocation getAnimationResource(KingPawsEntity animatable) {
        return DungeonFoxes.modLoc("animations/dungeons_mobs/king_paws.animation.json");
    }
}
