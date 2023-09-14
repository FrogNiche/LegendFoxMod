package com.cosmo.dungeonfoxes.item;


import com.cosmo.dungeonfoxes.DungeonFoxes;
import com.cosmo.dungeonfoxes.item.item.CrownArmor;
import com.cosmo.dungeonfoxes.item.item.ModArmorMaterials;

import net.minecraft.world.item.*;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DungeonFoxes.MOD_ID);
    public static final DeferredRegister<Item> SPAWN_EGGS = DeferredRegister.create(ForgeRegistries.ITEMS, DungeonFoxes.MOD_ID);

    public static final RegistryObject<CrownArmor> CROWN = ITEMS.register("crown",
            () -> new CrownArmor(ModArmorMaterials.KING_PAWS_ARMOR, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> TASTY_BONE = ITEMS.register("tasty_bone",
            () -> new SwordItem(Tiers.NETHERITE, 3, -2.4F,
                    (new Item.Properties()).rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> DUNGEON_FOXES_LOGO = ITEMS.register("dungeon_foxes_logo",
            () -> new Item(new Item.Properties()));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        SPAWN_EGGS.register(eventBus);
    }
}
