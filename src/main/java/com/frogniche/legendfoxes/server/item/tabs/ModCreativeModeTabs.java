package com.frogniche.legendfoxes.server.item.tabs;

import com.frogniche.legendfoxes.LegendFoxes;
import com.frogniche.legendfoxes.server.item.LFItemHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LegendFoxes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {
    public static CreativeModeTab LEGEND_FOXES_TAB;

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        LEGEND_FOXES_TAB = event.registerCreativeModeTab(new ResourceLocation(LegendFoxes.MOD_ID, "legend_foxes_tab"),
                builder -> builder.icon(() -> new ItemStack(LFItemHandler.LOGO.get()))
                        .title(Component.translatable("creativemodetab.legend_foxes_tab")));
    }
}