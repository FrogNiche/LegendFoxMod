package com.frogniche.legendfoxes.entity.horde_of_the_spore.devourer;

import com.frogniche.legendfoxes.entity.horde_of_the_bastion.unbreakable.UnbreakableEntity;
import com.frogniche.legendfoxes.sound.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

import static oshi.util.UserGroupInfo.getUser;


public class DevourerEntity extends Monster implements GeoEntity, RoarEntity {

    protected ServerBossEvent bossBar = (ServerBossEvent) new ServerBossEvent(this.getDisplayName(),

            BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.PROGRESS).setDarkenScreen(false);

    public static final EntityDataAccessor<Boolean> ROAR = SynchedEntityData.defineId(DevourerEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> EXPLODING = SynchedEntityData.defineId(DevourerEntity.class, EntityDataSerializers.BOOLEAN);

    public static final String CONTROLLER_NAME = "controller";

    public static final AttributeSupplier makeAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 200)
                .add(Attributes.ARMOR, 15000)
                .add(Attributes.MOVEMENT_SPEED, 0.4d)
                .add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1000000).build();


    }

    private int roarCooldown, explodingCooldown;
    protected AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected boolean dead;

    public DevourerEntity(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Override
    public boolean doHurtTarget(Entity opfer) {
        if (super.doHurtTarget(opfer)) {
            if (opfer instanceof LivingEntity) {
                //((LivingEntity) opfer).addEffect(new MobEffectInstance(MobEffects.POISON, 100), this);
            }
            //this corresponds to a 10% chance because 1/10 = 10%, u can chanche the chance here to whatever u like
            if (!this.entityData.get(EXPLODING) && this.explodingCooldown <= 0 && this.random.nextInt(30) == 0) {
                this.entityData.set(EXPLODING, true);
                //this should correspond to the animation length in ticks
                this.explodingCooldown = 4 * 20;
                this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 6 * 20, 99));
            }
            return true;
        }
        return false;
    }

    @Override
    public void tickDeath() {
        ++this.deathTime;
        if (!dead)
            dead = true;
        if (this.deathTime >= 20 * 16 && !this.level.isClientSide() && !this.isRemoved()) {
            this.level.broadcastEntityEvent(this, (byte) 60);
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
            if (this.explodingCooldown > 0) {
                this.explodingCooldown--;
                if (this.explodingCooldown == 0) {
                    explode();
                }
            }
            bossBar.setProgress(this.getHealth() / this.getMaxHealth());
        }

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
                this.maybeDisableShield(player, this.getMainHandItem(), player.isUsingItem() ? player.getUseItem() : ItemStack.EMPTY);
            }
        }
    }

    private void maybeDisableShield(Player p_21425_, ItemStack p_21426_, ItemStack p_21427_) {
        if (!p_21426_.isEmpty() && !p_21427_.isEmpty() && p_21426_.getItem() instanceof AxeItem && p_21427_.is(Items.SHIELD)) {
            float f = 0.25F + (float) EnchantmentHelper.getBlockEfficiency(this) * 0.05F;
            if (this.random.nextFloat() < f) {
                p_21425_.getCooldowns().addCooldown(Items.SHIELD, 100);
                this.level.broadcastEntityEvent(p_21425_, (byte) 30);
            }
        }

    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RoarGoal<>(this));
        //make anonymous class so the entity will stop attacking when exploding
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
        this.goalSelector.addGoal(3, new NearestAttackableTargetGoal(this, Villager.class, true));

        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 10f));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.5d));

    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ROAR, false);
        this.entityData.define(EXPLODING, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.roarCooldown = tag.getInt("roar_cooldown");
        this.explodingCooldown = tag.getInt("exploding_cooldown");
        this.entityData.set(ROAR, tag.getBoolean("roar"));
        this.entityData.set(EXPLODING, tag.getBoolean("exploding"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("roar_cooldown", this.roarCooldown);
        tag.putInt("exploding_cooldown", this.explodingCooldown);
        tag.putBoolean("roar", this.entityData.get(ROAR));
        tag.putBoolean("exploding", this.entityData.get(EXPLODING));
    }

    private PlayState predicate(AnimationState<DevourerEntity> event) {
        if (dead) {

            event.getController().setAnimation(RawAnimation.begin().thenPlayAndHold("animation.horde_spore_chief.death"));
            return PlayState.CONTINUE;
        }
        if (isExploding()) {
            event.getController().forceAnimationReset();
            event.getController().setAnimation(RawAnimation.begin().thenPlayAndHold("animation.devourer.pustule_spew"));
            return PlayState.CONTINUE;
        }
        if (entityData.get(ROAR)) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.devourer.new_roar",
                    Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;

        }
        if (event.isMoving()) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.devourer.new2", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            event.getController().setAnimation(RawAnimation.begin().then("animation.devourer.idle", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;

    }

    private PlayState attackPredicate(AnimationState<DevourerEntity> event) {
        if (this.swinging && event.getController().getAnimationState().equals(AnimationController.State.RUNNING)) {
            event.getController().forceAnimationReset();
            event.getController().setAnimation(RawAnimation.begin().then("animation.devourer.smash",
                    Animation.LoopType.PLAY_ONCE));


            if (!isExploding() && event.getController().getAnimationState() != AnimationController.State.RUNNING) {
                event.getController().stop();
            }
            if (!isExploding() && this.swinging && event.getController().getAnimationState() == AnimationController.State.RUNNING) {
                event.getController().forceAnimationReset();
                event.getController().setAnimation(RawAnimation.begin().then("animation.devourer.pustule_spew",
                        Animation.LoopType.PLAY_ONCE));
                this.swinging = false;

            }
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;

    }
 /*   @Override
    public void controllerRegistrar(AnimatableManager controllerRegistrar) {
        controllerRegistrar.addController(new AnimationController<>(this, "attackcontroller",
                0, this::attackPredicate));
        controllerRegistrar.addController(new AnimationController<>(this, "controller",
                0, this::predicate));
    }
*/
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "attackcontroller",
                0, this::attackPredicate));
        controllerRegistrar.add(new AnimationController<>(this, "controller",
                0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
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
        int i = Mth.nextInt(random, 0, ModSounds.ENTITY_DEVOURER_IDLE.size());
        if (i < ModSounds.ENTITY_DEVOURER_IDLE.size()) {
            return ModSounds.ENTITY_DEVOURER_IDLE.get(i).get();
        }
        return null;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        int i = Mth.nextInt(random, 0, ModSounds.ENTITY_DEVOURER_HURT.size());
        if (i < ModSounds.ENTITY_DEVOURER_HURT.size()) {
            return ModSounds.ENTITY_DEVOURER_HURT.get(i).get();
        }
        return null;
    }

    public SoundEvent getStepSound() {
        int i = Mth.nextInt(random, 0, ModSounds.ENTITY_DEVOURER_WALK.size());
        if (i < ModSounds.ENTITY_DEVOURER_WALK.size()) {
            return ModSounds.ENTITY_DEVOURER_WALK.get(i).get();
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
}
