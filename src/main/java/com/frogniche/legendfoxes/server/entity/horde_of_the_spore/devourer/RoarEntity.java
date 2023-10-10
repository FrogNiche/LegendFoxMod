package com.frogniche.legendfoxes.server.entity.horde_of_the_spore.devourer;

import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public interface RoarEntity {


    /**
     * must work on server side, not on client side
     */
    public void roar();

    /**
     * @return whether the entity can roar again or not, only correct on the server side
     */
    public boolean canRoar();

}