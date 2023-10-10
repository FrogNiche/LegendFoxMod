package com.frogniche.legendfoxes.server.item.custom;
import com.frogniche.legendfoxes.server.entity.horde_of_the_bastion.unbreakable.UnbreakableEntity;
import com.frogniche.legendfoxes.server.entity.horde_of_the_spore.devourer.EntityDevourer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemMobRemover extends Item {
    public ItemMobRemover(Item.Properties properties) {
        super(properties);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (entity instanceof EntityDevourer) {
            if (!entity.level.isClientSide) {
                entity.discard();
            }
            return true;
        }
            if (entity instanceof UnbreakableEntity) {
                if (!entity.level.isClientSide) {
                    entity.discard();
                }
                return true;
            }
            return false;
        }
    }
