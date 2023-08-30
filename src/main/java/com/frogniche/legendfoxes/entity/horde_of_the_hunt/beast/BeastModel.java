package com.frogniche.legendfoxes.entity.horde_of_the_hunt.beast;


import com.frogniche.legendfoxes.LegendFoxes;
import com.frogniche.legendfoxes.entity.horde_of_the_hunt.foxxo.FoxxoEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BeastModel extends GeoModel<BeastEntity> {
    @Override
    public ResourceLocation getModelResource(BeastEntity object) {
        return LegendFoxes.modLoc("geo/entity/horde_of_the_hunt/the_beast.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BeastEntity object) {
        return LegendFoxes.modLoc("textures/entity/horde_of_the_hunt/the_beast.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BeastEntity animatable) {
        return LegendFoxes.modLoc("animations/horde_of_the_hunt/the_beast.animation.json");
    }
}
