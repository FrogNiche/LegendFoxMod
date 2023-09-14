package com.cosmo.dungeonfoxes.item;

import com.cosmo.dungeonfoxes.DungeonFoxes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonFoxes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {
    public static CreativeModeTab DUNGEON_FOXES_TAB;

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        DUNGEON_FOXES_TAB = event.registerCreativeModeTab(new ResourceLocation(DungeonFoxes.MOD_ID, "legend_foxes_tab"),
                builder -> builder.icon(() -> new ItemStack(ModItems.TASTY_BONE.get()))
                        .title(Component.translatable("creativemodetab.dungeon_foxes_tab")));
    }
}