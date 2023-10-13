package com.frogniche.legendfoxes.server.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;

public class ItemBoneCludgel extends Item {
    public ItemBoneCludgel(Properties p_41383_) {
        super(p_41383_);
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        return ItemUtils.startUsingInstantly(world, player, hand);
    }
}
