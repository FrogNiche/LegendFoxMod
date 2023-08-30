package com.frogniche.legendfoxes.entity.horde_of_the_spore.devourer;


import com.frogniche.legendfoxes.LegendFoxes;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DevourerModel extends GeoModel<DevourerEntity> {
    @Override
    public ResourceLocation getModelResource(DevourerEntity object) {
        return LegendFoxes.modLoc("geo/entity/horde_of_the_spore/devourer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DevourerEntity object) {
        return LegendFoxes.modLoc("textures/entity/horde_of_the_spore/devourer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DevourerEntity animatable) {
        return LegendFoxes.modLoc("animations/horde_of_the_spore/devourer.animation.json");
    }
}
