package com.frogniche.legendfoxes.entity.horde_of_the_spore.devourer;


import com.frogniche.legendfoxes.LegendFoxes;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DevourerModel extends GeoModel<EntityDevourer> {


    @Override
    public ResourceLocation getModelResource(EntityDevourer object) {
        return LegendFoxes.modLoc("geo/entity/horde_of_the_spore/devourer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityDevourer object) {
        return LegendFoxes.modLoc("textures/entity/horde_of_the_spore/devourer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityDevourer animatable) {
        return LegendFoxes.modLoc("animations/horde_of_the_spore/devourer.animation.json");
    }
}
