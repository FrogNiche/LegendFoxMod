package com.frogniche.legendfoxes.entity.horde_of_the_spore.devourer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DevourerRenderer extends GeoEntityRenderer<DevourerEntity> {
    public DevourerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DevourerModel());
    }

    @Override
    protected float getDeathMaxRotation(DevourerEntity entityLivingBaseIn) {
        return 0f;
    }
}
