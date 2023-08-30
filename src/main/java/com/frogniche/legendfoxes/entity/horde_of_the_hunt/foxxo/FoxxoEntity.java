package com.frogniche.legendfoxes.entity.horde_of_the_hunt.foxxo;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;


public class FoxxoEntity extends Monster implements GeoEntity, PrepareEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public static final EntityDataAccessor<Boolean> PREPARE = SynchedEntityData.defineId(FoxxoEntity.class, EntityDataSerializers.BOOLEAN);
    public static final String CONTROLLER_NAME = "controller";

    public static final AttributeSupplier makeAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 15)
                .add(Attributes.ARMOR, 15000)
                .add(Attributes.MOVEMENT_SPEED, 0.4d)
                .add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1000000).build();


    }
    protected AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private int prepareCooldown;

    public FoxxoEntity(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Override
    public boolean doHurtTarget(Entity opfer) {
        if (super.doHurtTarget(opfer)) {
            this.level.broadcastEntityEvent(this, (byte) 10);
            return true;
        } else {
            if (opfer instanceof LivingEntity) {
                ((LivingEntity) opfer).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100), this);
            }
        }
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide()) {
            if (this.prepareCooldown > 0) {
                prepareCooldown--;
                if (prepareCooldown == 0)
                    this.entityData.set(PREPARE, false);
            }
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RoarGoal<>(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.6f, true));
        this.goalSelector.addGoal(3, new NearestAttackableTargetGoal(this, Player.class, true));
        this.goalSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));

        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 10f));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.5d));

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PREPARE, false);
    }
//bruh
    private PlayState predicate(AnimationState<FoxxoEntity> event) {
        if (entityData.get(PREPARE)) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.foxxo.prepare", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        } if(event.isMoving()) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.foxxo.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            event.getController().setAnimation(RawAnimation.begin().then("animation.foxxo.idle", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    }
    private PlayState attackPredicate(AnimationState<FoxxoEntity> event) {
        if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().getAnimationState();
            event.getController().setAnimation(RawAnimation.begin().then("animation.foxxo.attack", Animation.LoopType.PLAY_ONCE));
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController(this, "controller", 0, this::predicate));
        data.add(new AnimationController(this, "attack_controller", 0, this::attackPredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public AnimatableInstanceCache getFactory() {
        return this.factory;
    }

    @Override
    public void prepare() {
        if (!level.isClientSide()) {
            this.entityData.set(PREPARE, true);
            this.prepareCooldown = 84;
            this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 84, 99, false, false));
        }
    }
    protected SoundEvent getAmbientSound() {
        return SoundEvents.FOX_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.FOX_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.FOX_DEATH;
    }

    protected float getSoundVolume() {
        return 0.2F;
    }
    @Override
    public boolean canPrepare() {
        return this.prepareCooldown <= 0;
    }

    public static class RoarGoal<T extends Mob & PrepareEntity> extends Goal {

        protected final T entity;
        protected LivingEntity prevTarget = null;

        /**
         * @param entity must implement the RoarEntity interface
         */
        public RoarGoal(T entity) {
            this.entity = entity;
        }

        @Override
        public boolean canUse() {
            if (entity.getTarget() == null)
                prevTarget = null;
            return entity.canPrepare() && prevTarget == null && entity.getTarget() != prevTarget;
        }

        @Override
        public void start() {
            entity.prepare();
            prevTarget = entity.getTarget();
        }
    }
}
