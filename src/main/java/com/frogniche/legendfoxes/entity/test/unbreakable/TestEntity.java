package com.frogniche.legendfoxes.entity.test.unbreakable;

import com.frogniche.legendfoxes.entity.horde_of_the_spore.devourer.DevourerEntity;
import com.frogniche.legendfoxes.entity.horde_of_the_spore.devourer.RoarEntity;
import com.frogniche.legendfoxes.sound.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
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
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

import static com.frogniche.legendfoxes.entity.horde_of_the_spore.devourer.DevourerEntity.EXPLODING;

public class TestEntity extends Monster implements GeoEntity, RoarEntity {
    private int explosionPower = 1;
    protected ServerBossEvent bossBar = (ServerBossEvent) new ServerBossEvent(this.getDisplayName(),

            BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.PROGRESS).setDarkenScreen(false);
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    public static final EntityDataAccessor<Boolean> EXPLODING =
            SynchedEntityData.defineId(TestEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> ROAR = SynchedEntityData.defineId(TestEntity.class, EntityDataSerializers.BOOLEAN);
    public static final String CONTROLLER_NAME = "controller";

    public static final AttributeSupplier makeAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 200)
                .add(Attributes.ARMOR, 15000)
                .add(Attributes.MOVEMENT_SPEED, 0.4d)
                .add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1000000).build();


    }
    protected AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private int roarCooldown, explodingCooldown;

    public TestEntity(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }
    public int getExplosionPower() {
        return this.explosionPower;
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
            this.remove(RemovalReason.KILLED);
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
            if (this.explodingCooldown > 0) {
                this.explodingCooldown--;
                if (this.explodingCooldown == 0) {
                    explode();
                }
            }
            bossBar.setProgress(this.getHealth() / this.getMaxHealth());
        }

    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TestEntity.RoarGoal<>(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.6f, true) {
            @Override
            public boolean canUse() {
                return !isExploding() && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return !isExploding() && super.canContinueToUse();
            }
        });
        this.goalSelector.addGoal(3, new NearestAttackableTargetGoal(this, Player.class, true));
        this.goalSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));

        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 10f));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.5d));

    }

    protected void explode() {
        this.entityData.set(EXPLODING, false);
        // this lets the enitty make 40% more damage with the explosion
        float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.4f;
        //this adds 300% more knockback to hit entities
        float f1 = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK) * 3f;
        List<LivingEntity> entites = this.level.getEntitiesOfClass(LivingEntity.class,
                new AABB(this.getOnPos()).inflate(20), living -> living != this);
        for (LivingEntity living : entites) {
            boolean flag = living.hurt(this.damageSources().mobAttack(this), f);
            if (flag) {
                living.knockback(f1 * 0.5F, Mth.sin(this.getYRot() * ((float) Math.PI / 180F)),
                        -Mth.cos(this.getYRot() * ((float) Math.PI / 180F)));
            }
            if (living instanceof Player) {
                Player player = (Player) living;
                this.DisableShield(player, this.getMainHandItem(), player.isUsingItem() ? player.getUseItem() : ItemStack.EMPTY);
            }
        }
    }

    private void DisableShield(Player p_21425_, ItemStack p_21426_, ItemStack p_21427_) {
        if (!p_21426_.isEmpty() && !p_21427_.isEmpty() && p_21426_.getItem() instanceof AxeItem && p_21427_.is(Items.SHIELD)) {
            float f = 0.25F + (float) EnchantmentHelper.getBlockEfficiency(this) * 0.05F;
            if (this.random.nextFloat() < f) {
                p_21425_.getCooldowns().addCooldown(Items.SHIELD, 100);
                this.level.broadcastEntityEvent(p_21425_, (byte) 30);
            }
        }

    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ROAR, false);
        this.entityData.define(EXPLODING, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("ExplosionPower", 99)) {
            this.explosionPower = tag.getByte("ExplosionPower");
            this.roarCooldown = tag.getInt("roar_cooldown");
            this.explodingCooldown = tag.getInt("exploding_cooldown");
            this.entityData.set(ROAR, tag.getBoolean("roar"));
            this.entityData.set(EXPLODING, tag.getBoolean("exploding"));
        }

        this.roarCooldown = tag.getInt("roar_cooldown");
        this.explodingCooldown = tag.getInt("exploding_cooldown");
        this.entityData.set(ROAR, tag.getBoolean("roar"));
        this.entityData.set(EXPLODING, tag.getBoolean("exploding"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putByte("ExplosionPower", (byte)this.explosionPower);
        tag.putInt("roar_cooldown", this.roarCooldown);
        tag.putInt("exploding_cooldown", this.explodingCooldown);
        tag.putBoolean("roar", this.entityData.get(ROAR));
        tag.putBoolean("exploding", this.entityData.get(EXPLODING));

    }

    private PlayState predicate(AnimationState<TestEntity> event) {
        if (dead){
            event.getController().setAnimation(RawAnimation.begin().thenPlayAndHold("animation.horde_spore_chief.death"));
            return PlayState.CONTINUE;
        }
        if (isExploding()) {
            event.getController().forceAnimationReset();
            event.getController().setAnimation(RawAnimation.begin().thenPlayAndHold("animation.devourer.pustule_spew"));
            return PlayState.CONTINUE;
        }
        if (entityData.get(ROAR)) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.devourer.new_roar", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        } if(event.isMoving()) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.devourer.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            event.getController().setAnimation(RawAnimation.begin().then("animation.devourer.idle", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    }
    private PlayState attackPredicate(AnimationState<TestEntity> event) {
        if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().forceAnimationReset();
            event.getController().setAnimation(RawAnimation.begin().then("animation.devourer.smash", Animation.LoopType.PLAY_ONCE));
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
            this.addEffect(new MobEffectInstance(MobEffects.POISON, 84, 99, true, true));
        }
    }
    protected SoundEvent getAmbientSound() {
        int i = Mth.nextInt(random, 0, ModSounds.DEVOURER_IDLE.size());
        if (i < ModSounds.DEVOURER_IDLE.size()) {
            return ModSounds.DEVOURER_IDLE.get(i).get();
        }
        return null;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        int i = Mth.nextInt(random, 0, ModSounds.DEVOURER_HURT.size());
        if (i < ModSounds.DEVOURER_HURT.size()) {
            return ModSounds.DEVOURER_HURT.get(i).get();
        }
        return null;
    }
    protected SoundEvent getDeathSound() {
        return ModSounds.DEVOURER_HURT5.get();
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
        public boolean isExploding(){
            return this.entityData.get(EXPLODING);
        }

    }
