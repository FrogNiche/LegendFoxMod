package com.frogniche.legendfoxes.entity.test.unbreakable;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TestRenderer extends GeoEntityRenderer<TestEntity> {
    public TestRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TestModel());
    }

    @Override
    protected float getDeathMaxRotation(TestEntity entityLivingBaseIn) {
        return 0f;
    }
}
