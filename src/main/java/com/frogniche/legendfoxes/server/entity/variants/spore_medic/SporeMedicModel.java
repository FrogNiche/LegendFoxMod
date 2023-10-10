package com.frogniche.legendfoxes.server.entity.variants.spore_medic;


import com.frogniche.legendfoxes.LegendFoxes;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SporeMedicModel extends GeoModel<SporeMedicEntity> {
    @Override
    public ResourceLocation getModelResource(SporeMedicEntity object) {
        return LegendFoxes.modLoc("geo/entity/variants/medics/spore_medic.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SporeMedicEntity object) {
        return object.getTextureType().getTexture();
    }

    @Override
    public ResourceLocation getAnimationResource(SporeMedicEntity animatable) {
        return LegendFoxes.modLoc("animations/variants/medics/spore_medic.animation.json");
    }
}
