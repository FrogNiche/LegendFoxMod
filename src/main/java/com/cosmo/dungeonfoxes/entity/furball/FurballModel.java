package com.cosmo.dungeonfoxes.entity.furball;


import com.cosmo.dungeonfoxes.DungeonFoxes;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FurballModel extends GeoModel<FurballEntity> {
    @Override
    public ResourceLocation getModelResource(FurballEntity object) {
        return DungeonFoxes.modLoc("geo/entity/dungeons_mobs/furball.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FurballEntity object) {
        return DungeonFoxes.modLoc("textures/entity/dungeons/tex_starball.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FurballEntity animatable) {
        return DungeonFoxes.modLoc("animations/dungeons_mobs/furball.animation.json");
    }
}
