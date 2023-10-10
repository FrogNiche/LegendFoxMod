package com.frogniche.legendfoxes.server.entity.horde_of_the_spore.test;


import com.frogniche.legendfoxes.LegendFoxes;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TestModel extends GeoModel<TestEntity> {
    @Override
    public ResourceLocation getModelResource(TestEntity object) {
        return LegendFoxes.modLoc("geo/entity/horde_of_the_spore/devourer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TestEntity object) {
        return LegendFoxes.modLoc("textures/entity/horde_of_the_spore/devourer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TestEntity animatable) {
        return LegendFoxes.modLoc("animations/horde_of_the_spore/devourer.animation.json");
    }
}
