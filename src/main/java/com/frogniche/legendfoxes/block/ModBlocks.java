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

    public static final RegistryObject<Block> LAVA_DIRT = registerBlock("lava_dirt",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT)
                    .strength(9f).requiresCorrectToolForDrops()));

    /* public static final RegistryObject<Block> LAVA_DIRT1 = registerBlock("lava_dirt1",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT)
                    .strength(9f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> LAVA_DIRT2 = registerBlock("lava_dirt2",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT)
                    .strength(9f).requiresCorrectToolForDrops())); */
    public static final RegistryObject<Block> NETHER_PATH1 = registerBlock("nether_path1",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT)
                    .strength(9f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> NETHER_PATH2 = registerBlock("nether_path2",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT)
                    .strength(9f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> NETHER_PATH3 = registerBlock("nether_path3",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT)
                    .strength(9f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> NETHER_PATH4 = registerBlock("nether_path4",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT)
                    .strength(9f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> NETHER_PATH5 = registerBlock("nether_path5",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT)
                    .strength(9f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> NETHER_WARPED_PATH = registerBlock("nether_warped_path",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT)
                    .strength(9f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> NETHER_WARPED = registerBlock("nether_warped",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT)
                    .strength(9f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> NETHERRACK = registerBlock("netherrack",
            () -> new Block(BlockBehaviour.Properties.of(Material.ICE_SOLID)
                    .strength(9f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> WARPED_FRUIT = registerBlock("warped_fruit",
            () -> new Block(BlockBehaviour.Properties.of(Material.ICE_SOLID)
                    .strength(9f).requiresCorrectToolForDrops().noCollission().lightLevel((p_220873_) -> {
                        return 15;
                    }).sound(SoundType.CHERRY_LEAVES)));

    public static final RegistryObject<Block> WARPED_HOLDER = registerBlock("warped_holder",
            () -> new Block(BlockBehaviour.Properties.of(Material.ICE_SOLID)
                    .strength(9f).requiresCorrectToolForDrops().noCollission().lightLevel((p_220873_) -> {
                return 15;
                    }).sound(SoundType.CHERRY_LEAVES)));

    public static final RegistryObject<Block> WARPED_WART = registerBlock("warped_wart",
            () -> new RotatedPillarBlock((BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)
                    .strength(9f).strength(0.3F).lightLevel((p_220873_) -> {
                        return 15;
                    }).sound(SoundType.WART_BLOCK))));

    public static final RegistryObject<Block> HALF_NETHER_BLOCK = registerBlock("half_nether_block",
            () -> new RotatedPillarBlock((BlockBehaviour.Properties.copy(Blocks.WARPED_NYLIUM)
                    .strength(9f).strength(0.3F).lightLevel((p_220873_) -> {
                        return 15;
                    }).sound(SoundType.WART_BLOCK))));

    public static final RegistryObject<Block> WARPED_VINES = registerBlock("warped_vines",
            () -> new GlowLichenBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT,
                    MaterialColor.GLOW_LICHEN).noCollission().strength(0.2F)
                    .sound(SoundType.GLOW_LICHEN).lightLevel(GlowLichenBlock.emission(7))));

    public static final RegistryObject<Block> NO_LAVA_PATH = registerBlock("no_lava_path",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT)
                    .strength(9f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> SPORES = registerBlock("spores",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT)
                    .strength(9f).requiresCorrectToolForDrops().noCollission()));


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