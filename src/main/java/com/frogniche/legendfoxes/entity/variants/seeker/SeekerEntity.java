package com.frogniche.legendfoxes.entity.variants.seeker;

import com.frogniche.legendfoxes.LegendFoxes;
import com.frogniche.legendfoxes.entity.EntityInit;
import com.frogniche.legendfoxes.entity.NBTUtils;
import com.frogniche.legendfoxes.entity.horde_of_the_hunt.foxxo.FoxxoEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;


public class SeekerEntity extends Monster implements GeoEntity {

    public static final AttributeSupplier makeAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.ARMOR, 5)
                .add(Attributes.MOVEMENT_SPEED, 0.3d)
                .add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.1).build();



    }
    public static final EntityDataAccessor<Integer> TYPE = SynchedEntityData.defineId(SeekerEntity.class, EntityDataSerializers.INT);

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public static final String CONTROLLER_NAME = "controller";


    public SeekerEntity(EntityType<SeekerEntity> type, Level world) {
        super(type, world);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TYPE, getInitialType().ordinal());
    }

    protected SeekerEntity(Level world) {
        this(EntityInit.SEEKER.get(), world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.1d, true) {
        });
        this.goalSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true) {
        });
        this.goalSelector.addGoal(2, new NearestAttackableTargetGoal(this, IronGolem.class, true) {
        });

        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8f) {
        });

        this.goalSelector.addGoal(10, new RandomStrollGoal(this, 1f) {
        });

    }

    @Override
    public boolean doHurtTarget(Entity opfer) {
        if(super.doHurtTarget(opfer)){
            this.level.broadcastEntityEvent(this, (byte)10);
            return true;
        } else {
            if (opfer instanceof LivingEntity) {
                ((LivingEntity)opfer).addEffect(new MobEffectInstance(MobEffects.DARKNESS,100), this);
            }
        }
        return true;
    }

    private PlayState attackPredicate(AnimationState<FoxxoEntity> event) {
        if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().getAnimationState();
            event.getController().setAnimation(RawAnimation.begin().then("animation.brute.attack", Animation.LoopType.PLAY_ONCE));
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.brute.run", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.seeker.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController(this, "controller", 0, this::predicate));
        data.add(new AnimationController(this, "attack_controller", 0, this::attackPredicate));
    }
//bruh
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        NBTUtils.setIntIfPossible(v -> entityData.set(TYPE, v), "type", nbt);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("type", entityData.get(TYPE));
    }

    public void setTextureType(Type t){
        this.entityData.set(TYPE, t.ordinal());
    }

    public Type getTextureType(){
        return Type.values()[entityData.get(TYPE)];
    }

    protected Type getInitialType(){
        return Type.values()[this.random.nextInt(Type.values().length)];
    }


    public static enum Type {
        HORDE_OF_THE_HUNT(LegendFoxes.modLoc("textures/entity/variants/seeker_1.png")),
        HORDE_OF_THE_SPORE(LegendFoxes.modLoc("textures/entity/variants/seeker_2.png"));

        private final ResourceLocation texture;

        Type(ResourceLocation texture) {
            this.texture = texture;
        }

        public ResourceLocation getTexture() {
            return texture;
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
}

