package com.frogniche.legendfoxes.client.datagen;


import com.frogniche.legendfoxes.LegendFoxes;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = LegendFoxes.MOD_ID)
public class DataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        net.minecraft.data.DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        PackOutput output = gen.getPackOutput();

        if (event.includeClient())
            gatherClientData(output, gen, helper);
        if (event.includeServer())
            gatherServerData(output, gen, helper);
        try {
            gen.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void gatherClientData(PackOutput output, net.minecraft.data.DataGenerator gen, ExistingFileHelper helper) {
        gen.addProvider(true, new ModItemModelsProvider(output, helper));
    }

    private static void gatherServerData(PackOutput output, net.minecraft.data.DataGenerator gen, ExistingFileHelper helper) {

    }


}
