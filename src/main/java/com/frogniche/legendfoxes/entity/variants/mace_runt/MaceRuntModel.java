package com.frogniche.legendfoxes.entity.variants.mace_runt;


import com.frogniche.legendfoxes.LegendFoxes;
import com.frogniche.legendfoxes.entity.variants.blaze_runt.BlazeRuntEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MaceRuntModel extends GeoModel<MaceRuntEntity> {
    @Override
    public ResourceLocation getModelResource(MaceRuntEntity object) {
        return LegendFoxes.modLoc("geo/entity/variants/mace_runts/mace_runt.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MaceRuntEntity object) {
        return object.getTextureType().getTexture();
    }

    @Override
    public ResourceLocation getAnimationResource(MaceRuntEntity animatable) {
        return LegendFoxes.modLoc("animations/variants/mace_runts/mace_runt.animation.json");
    }
}
