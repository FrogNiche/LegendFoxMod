package com.frogniche.legendfoxes.entity.horde_of_the_bastion.unbreakable;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class UnbreakableRenderer extends GeoEntityRenderer<UnbreakableEntity> {
    public UnbreakableRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new UnbreakableModel());
    }

    @Override
    protected float getDeathMaxRotation(UnbreakableEntity entityLivingBaseIn) {
        return 0f;
    }
}
