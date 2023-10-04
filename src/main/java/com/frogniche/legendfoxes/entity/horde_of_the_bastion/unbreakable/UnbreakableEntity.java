package com.frogniche.legendfoxes.entity.horde_of_the_bastion.unbreakable;

import com.frogniche.legendfoxes.entity.horde_of_the_spore.devourer.EntityDevourer;
import com.frogniche.legendfoxes.entity.horde_of_the_spore.devourer.RoarEntity;
import com.frogniche.legendfoxes.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
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
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class UnbreakableEntity extends Monster implements GeoEntity, RoarEntity {
    protected ServerBossEvent bossBar = (ServerBossEvent) new ServerBossEvent(this.getDisplayName(),

            BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.PROGRESS).setDarkenScreen(false);
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public static final EntityDataAccessor<Boolean> ROAR = SynchedEntityData.defineId(UnbreakableEntity.class, EntityDataSerializers.BOOLEAN);
    public static final String CONTROLLER_NAME = "controller";

    public static final AttributeSupplier makeAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 200)
                .add(Attributes.ARMOR, 15000)
                .add(Attributes.MOVEMENT_SPEED, 0.4d)
                .add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1000000).build();


    }
    protected AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private int  roarCooldown;

    public UnbreakableEntity(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Override
    public boolean doHurtTarget(Entity opfer) {
        if (super.doHurtTarget(opfer)) {
            this.level.broadcastEntityEvent(this, (byte) 10);
            return true;
        } else {
            if (opfer instanceof LivingEntity) {
                ((LivingEntity) opfer).addEffect(new MobEffectInstance(MobEffects.POISON, 100), this);
            }
        }
        return true;
    }
    @Override
    public void tickDeath() {
        ++this.deathTime;
        if (!dead)
            dead = true;
        if (this.deathTime >= 20 * 9.54167 && !this.level.isClientSide() && !this.isRemoved()) {
            this.level.broadcastEntityEvent(this, (byte)60);
            this.remove(Entity.RemovalReason.KILLED);
        }
    }
    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide()) {
            if (this.roarCooldown > 0) {
                roarCooldown--;
                if (roarCooldown == 0)
                    this.entityData.set(ROAR, false);
            }
            bossBar.setProgress(this.getHealth() / this.getMaxHealth());
        }
            }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new EntityDevourer.RoarGoal<>(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.6f, true));
        this.goalSelector.addGoal(3, new NearestAttackableTargetGoal(this, Player.class, true));
        this.goalSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));

        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 10f));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.5d));

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ROAR, false);
    }

    private PlayState predicate(AnimationState<UnbreakableEntity> event) {
        if (dead){
            event.getController().setAnimation(RawAnimation.begin().thenPlayAndHold("animation.unbreakable.death"));
            return PlayState.CONTINUE;
        }
        if (entityData.get(ROAR)) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.unbreakable.roar", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        } if(event.isMoving()) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.unbreakable.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            event.getController().setAnimation(RawAnimation.begin().then("animation.unbreakable.idle", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    }
    private PlayState attackPredicate(AnimationState<UnbreakableEntity> event) {
        if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().forceAnimationReset();
            event.getController().setAnimation(RawAnimation.begin().then("animation.unbreakable.fight_swipe", Animation.LoopType.PLAY_ONCE));
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
    public void roar() {
        if (!level.isClientSide()) {
            this.entityData.set(ROAR, true);
            this.roarCooldown = 84;
            this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 84, 99, false, false));
        }
    }
    protected SoundEvent getAmbientSound() {
        return SoundEvents.FOX_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSounds.UNBREAKABLE_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.FOX_DEATH;
    }

    protected float getSoundVolume() {
        return 0.2F;
    }

    @Override
    public boolean canRoar() {
        return this.roarCooldown <= 0;
    }

    public static class RoarGoal<T extends Mob & RoarEntity> extends Goal {

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
            return entity.canRoar() && prevTarget == null && entity.getTarget() != prevTarget;
        }

        @Override
        public void start() {
            entity.roar();
            prevTarget = entity.getTarget();
        }
    }
    @Override
    public void startSeenByPlayer(ServerPlayer p31483) {
        super.startSeenByPlayer(p31483);
        this.bossBar.addPlayer(p31483);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer p31488) {
        super.stopSeenByPlayer(p31488);
        this.bossBar.removePlayer(p31488);
    }

    public void setCustomName(@Nullable Component p31476) {
        super.setCustomName(p31476);
        this.bossBar.setName(this.getDisplayName());


    }
}
