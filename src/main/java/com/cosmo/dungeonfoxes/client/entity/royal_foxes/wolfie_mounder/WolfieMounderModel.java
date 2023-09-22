package com.cosmo.dungeonfoxes.client.entity.royal_foxes.wolfie_mounder;


import com.cosmo.dungeonfoxes.DungeonFoxes;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WolfieMounderModel extends GeoModel<WolfieMounderEntity> {
    @Override
    public ResourceLocation getModelResource(WolfieMounderEntity object) {
        return DungeonFoxes.modLoc("geo/entity/dungeons_mobs/wolfie_mounder.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WolfieMounderEntity object) {
        return DungeonFoxes.modLoc("textures/entity/dungeons/tex_fox_stalwart.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WolfieMounderEntity animatable) {
        return DungeonFoxes.modLoc("animations/dungeons_mobs/wolfie_mounder.animation.json");
    }
}
