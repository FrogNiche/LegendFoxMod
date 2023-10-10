package com.frogniche.legendfoxes.server.entity.horde_of_the_hunt.foxxo;


import com.frogniche.legendfoxes.LegendFoxes;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FoxxoModel extends GeoModel<FoxxoEntity> {
    @Override
    public ResourceLocation getModelResource(FoxxoEntity object) {
        return LegendFoxes.modLoc("geo/entity/horde_of_the_hunt/foxxo.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FoxxoEntity object) {
        return LegendFoxes.modLoc("textures/entity/horde_of_the_hunt/foxxo.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FoxxoEntity animatable) {
        return LegendFoxes.modLoc("animations/horde_of_the_hunt/foxxo.animation.json");
    }
}
