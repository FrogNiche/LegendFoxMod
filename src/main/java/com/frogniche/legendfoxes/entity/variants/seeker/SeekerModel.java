package com.frogniche.legendfoxes.entity.variants.seeker;


import com.frogniche.legendfoxes.LegendFoxes;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SeekerModel extends GeoModel<SeekerEntity> {
    @Override
    public ResourceLocation getModelResource(SeekerEntity object) {
        return LegendFoxes.modLoc("geo/entity/horde_of_the_spore/seeker.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SeekerEntity object) {
        return object.getTextureType().getTexture();
    }

    @Override
    public ResourceLocation getAnimationResource(SeekerEntity animatable) {
        return LegendFoxes.modLoc("animations/horde_of_the_spore/seeker.animation.json");
    }
}
