package com.frogniche.legendfoxes.server.entity.horde_of_the_bastion.unbreakable;


import com.frogniche.legendfoxes.LegendFoxes;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class UnbreakableModel extends GeoModel<UnbreakableEntity> {
    @Override
    public ResourceLocation getModelResource(UnbreakableEntity object) {
        return LegendFoxes.modLoc("geo/entity/horde_of_the_bastion/unbreakable/unbreakable.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(UnbreakableEntity object) {
        return LegendFoxes.modLoc("textures/entity/horde_of_the_bastion/unbreakable.png");
    }

    @Override
    public ResourceLocation getAnimationResource(UnbreakableEntity animatable) {
        return LegendFoxes.modLoc("animations/horde_of_the_bastion/unbreakable/unbreakable.animation.json");
    }
}
