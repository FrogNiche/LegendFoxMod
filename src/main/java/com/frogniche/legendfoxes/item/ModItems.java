package com.frogniche.legendfoxes.item;


import com.frogniche.legendfoxes.LegendFoxes;
import com.frogniche.legendfoxes.entity.EntityInit;
import com.frogniche.legendfoxes.item.item.DevourerSpitChargeItem;
import com.frogniche.legendfoxes.item.item.ModArmorMaterials;
import com.frogniche.legendfoxes.item.item.UnbreakableArmor;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.frogniche.legendfoxes.block.ModBlocks.BLOCKS;


public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LegendFoxes.MOD_ID);
    public static final DeferredRegister<Item> SPAWN_EGGS = DeferredRegister.create(ForgeRegistries.ITEMS, LegendFoxes.MOD_ID);

    public static final RegistryObject<UnbreakableArmor> UNBREAKABLE_HEAD = ITEMS.register("unbreakable_head",
            () -> new UnbreakableArmor(ModArmorMaterials.UNBREAKABLE_ARMOR, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<UnbreakableArmor> UNBREAKABLE_CHESTPLATE = ITEMS.register("unbreakable_chestplate",
            () -> new UnbreakableArmor(ModArmorMaterials.UNBREAKABLE_ARMOR, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<UnbreakableArmor> BEAST_SKIRT = ITEMS.register("beast_skirt",
            () -> new UnbreakableArmor(ModArmorMaterials.UNBREAKABLE_ARMOR, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> DEVOURER_SPIT_CHARGE = ITEMS.register("devourer_spit_charge", () ->
            new DevourerSpitChargeItem(new Item.Properties()));

    public static final RegistryObject<Item> UNBREAKABLE_CANNON = ITEMS.register("unbreakable_cannon",
            () -> new SwordItem(Tiers.NETHERITE, 3, -2.4F,
                    (new Item.Properties()).rarity(Rarity.UNCOMMON)));


    public static final RegistryObject<Item> LEGEND_LOGO = ITEMS.register("legend_logo",
            () -> new Item(new Item.Properties()));


    public static final RegistryObject<Item> BONE_CLUDGEL = ITEMS.register("bone_cludgel",
            () -> new SwordItem(Tiers.NETHERITE, 3, -2.4F,
                    (new Item.Properties()).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> DEVOURER_SPAWN_EGG = ITEMS.register("devourer_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.DEVOURER, 0x531b3a, 0x1bae82,
                    new Item.Properties()));

    public static final RegistryObject<Item> MACE_RUNT_SPAWN_EGG = ITEMS.register("mace_runt_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.MACE_RUNT, 0x9e6238, 0x3d332b,
                    new Item.Properties()));

    public static final RegistryObject<Item> BLAZE_RUNT_SPAWN_EGG = ITEMS.register("blaze_runt_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.BLAZE_RUNT, 0x9e6238, 0x3d332b,
                    new Item.Properties()));

    public static final RegistryObject<Item> UNBREAKABLE_SPAWN_EGG = ITEMS.register("unbreakable_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.UNBREAKABLE, 0xff5400, 0xffb700,
                    new Item.Properties()));

    public static final RegistryObject<Item> BEAST_SPAWN_EGG = ITEMS.register("beast_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.BEAST, 0x9e6238, 0xe5d4bc,
                    new Item.Properties()));

    public static final RegistryObject<Item> FOXXO_SPAWN_EGG = ITEMS.register("foxxo_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.FOXXO, 0xcac38f, 0xe27c21,
                    new Item.Properties()));

    public static final RegistryObject<Item> SEEKER_SPAWN_EGG = ITEMS.register("seeker_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.SEEKER, 0xbdaea8, 0x440f2c,
                    new Item.Properties()));

    public static final RegistryObject<Item> SPORE_MEDIC_SPAWN_EGG = ITEMS.register("spore_medic_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.SPORE_MEDIC, 0xe5d4bc, 0x282828,
                    new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        SPAWN_EGGS.register(eventBus);
    }
}
