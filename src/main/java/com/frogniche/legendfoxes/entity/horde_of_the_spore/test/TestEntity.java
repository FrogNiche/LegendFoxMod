package com.frogniche.legendfoxes.entity.horde_of_the_spore.test;

import com.frogniche.legendfoxes.entity.horde_of_the_spore.devourer.DevourerEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TestEntity extends Monster implements GeoEntity {

    public AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public static final AttributeSupplier makeAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 200)
                .add(Attributes.ARMOR, 15000)
                .add(Attributes.MOVEMENT_SPEED, 0.4d)
                .add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.1d).build();


    }
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        //make anonymous class so the entity will stop attacking when exploding
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.6f, true) {
        });
        this.goalSelector.addGoal(3, new NearestAttackableTargetGoal(this, Player.class, true));

        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 10f));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.5d));

    }
    protected AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public TestEntity
            (EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this,
                "attack", 5, this::attackPredicate));
        controllerRegistrar.add(new AnimationController<>(this,
                "controller", 5, this::predicate));
    }

    private PlayState predicate(AnimationState<TestEntity> devourerEntityAnimationState) {

        if (devourerEntityAnimationState.isMoving()) {
            devourerEntityAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.devourer.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            devourerEntityAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.devourer.idle", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;

    }

    protected <T extends GeoAnimatable> PlayState attackPredicate(AnimationState<T> event) {
        if (this.swinging && event.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
            event.getController().forceAnimationReset();
            event.getController().setAnimation(RawAnimation.begin().then("animation.devourer.smash",
                    Animation.LoopType.PLAY_ONCE));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public AnimatableInstanceCache getFactory() {
        return this.factory;
    }
    }
