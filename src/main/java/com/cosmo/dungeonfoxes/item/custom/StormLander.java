package com.cosmo.dungeonfoxes.item.custom;
import com.cosmo.dungeonfoxes.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
public class StormLander extends SwordItem {
    public StormLander(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pTarget.addEffect(new MobEffectInstance(ModEffects.FREEZE_EFFECT.get(), 600), pAttacker);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}

