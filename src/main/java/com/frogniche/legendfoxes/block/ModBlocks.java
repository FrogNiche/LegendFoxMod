package com.frogniche.legendfoxes.block;
import com.frogniche.legendfoxes.LegendFoxes;
import com.frogniche.legendfoxes.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, LegendFoxes.MOD_ID);

    public static final RegistryObject<Block> SPORE_BLOCK = registerBlock("spore_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.of(Material.CLAY,
                    MaterialColor.GRASS).friction(0.8F).sound(SoundType.SLIME_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> DIRT_WITH_MAGMA = registerBlock("dirt_with_magma",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT)
                    .strength(9f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> DARK_DIRT = registerBlock("dark_dirt",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT)
                    .strength(9f).requiresCorrectToolForDrops()));

        public static final RegistryObject<Block> WARPED_BLOCK = registerBlock("warped_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT)
                    .strength(9f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> CRIMSOM_BLOCK = registerBlock("crimsom_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.GRASS)
                    .strength(9f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> FLAT_WARPED_WART = registerBlock("flat_warped_wart",
            () -> new Block(BlockBehaviour.Properties.of(Material.GRASS)
                    .strength(9f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> WARPED_WART = registerBlock("warped_wart",
            () -> new RotatedPillarBlock((BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)
                    .strength(9f).strength(0.3F).lightLevel((p_220873_) -> {
                        return 15;
                    }).sound(SoundType.WART_BLOCK))));

    public static final RegistryObject<Block> MEDIUM_WARPED_WART = registerBlock("medium_warped_wart",
            () -> new RotatedPillarBlock((BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)
                    .strength(9f).strength(0.3F).lightLevel((p_220873_) -> {
                        return 15;
                    }).sound(SoundType.WART_BLOCK))));

    public static final RegistryObject<Block> SMALL_SHROOMLIGHT = registerBlock("small_shroomlight",
            () -> new RotatedPillarBlock((BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)
                    .strength(9f).strength(0.3F).lightLevel((p_220873_) -> {
                        return 15;
                    }).sound(SoundType.WART_BLOCK))));

    public static final RegistryObject<Block> WARPED_VINES = registerBlock("warped_vines",
            () -> new GlowLichenBlock(
                    (BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT,
                                    MaterialColor.GLOW_LICHEN).noCollission().strength(0.2F)
                            .sound(SoundType.GLOW_LICHEN).lightLevel(GlowLichenBlock.emission(7)))));

    public static final RegistryObject<Block> WARPED_GRASS = registerBlock("warped_grass",
            () -> new Block(BlockBehaviour.Properties.of(Material.GRASS)
                    .strength(9f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> LIGHT_DIRT_PATH = registerBlock("light_dirt_path",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT)
                    .strength(9f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> RED_DIRT_PATH = registerBlock("red_dirt_path",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT)
                    .strength(9f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> DARK_DIRT_PATH = registerBlock("dark_dirt_path",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT)
                    .strength(9f).requiresCorrectToolForDrops()));



    public static final RegistryObject<Block> NETHERRACK = registerBlock("netherrack",
            () -> new Block(BlockBehaviour.Properties.of(Material.ICE_SOLID)
                    .strength(9f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> SHROOMLIGHT = registerBlock("shroomlight",
            () -> new Block(BlockBehaviour.Properties.of(Material.ICE_SOLID)
                    .strength(9f).requiresCorrectToolForDrops().noCollission()));

    public static final RegistryObject<Block> UNRIPE_SHROOMLIGHT = registerBlock("unripe_shroomlight",
            () -> new Block(BlockBehaviour.Properties.of(Material.ICE_SOLID)
                    .strength(9f).requiresCorrectToolForDrops().noCollission()));

    public static final RegistryObject<Block> WARPED_SPORES = registerBlock("warped_spores",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT)
                    .strength(9f).requiresCorrectToolForDrops()));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}