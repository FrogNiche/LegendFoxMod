package com.frogniche.legendfoxes.entity.variants.blaze_runt;


import com.frogniche.legendfoxes.LegendFoxes;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BlazeRuntModel extends GeoModel<BlazeRuntEntity> {
    @Override
    public ResourceLocation getModelResource(BlazeRuntEntity object) {
        return LegendFoxes.modLoc("geo/entity/variants/blaze_runt/blaze_runt.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BlazeRuntEntity object) {
        return object.getTextureType().getTexture();
    }

    @Override
    public ResourceLocation getAnimationResource(BlazeRuntEntity animatable) {
        return LegendFoxes.modLoc("animations/variants/blaze_runt/blaze_runt.animation.json");
    }
}
