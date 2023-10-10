package com.frogniche.legendfoxes;

import com.frogniche.legendfoxes.server.block.LFBlockHandler;
import com.frogniche.legendfoxes.server.entity.LFEntityHandler;
import com.frogniche.legendfoxes.server.entity.horde_of_the_bastion.unbreakable.UnbreakableRenderer;
import com.frogniche.legendfoxes.server.entity.horde_of_the_bastion.unbreakable.UnbreakableEntity;
import com.frogniche.legendfoxes.server.entity.horde_of_the_hunt.beast.BeastEntity;
import com.frogniche.legendfoxes.server.entity.horde_of_the_hunt.beast.BeastModel;
import com.frogniche.legendfoxes.server.entity.horde_of_the_hunt.foxxo.FoxxoEntity;
import com.frogniche.legendfoxes.server.entity.horde_of_the_hunt.foxxo.FoxxoModel;
import com.frogniche.legendfoxes.server.entity.horde_of_the_spore.devourer.EntityDevourer;
import com.frogniche.legendfoxes.server.entity.horde_of_the_spore.devourer.DevourerRenderer;
import com.frogniche.legendfoxes.server.entity.horde_of_the_spore.test.TestEntity;
import com.frogniche.legendfoxes.server.entity.horde_of_the_spore.test.TestModel;
import com.frogniche.legendfoxes.server.entity.variants.blaze_runt.BlazeRuntEntity;
import com.frogniche.legendfoxes.server.entity.variants.blaze_runt.BlazeRuntModel;
import com.frogniche.legendfoxes.server.entity.variants.mace_runt.MaceRuntEntity;
import com.frogniche.legendfoxes.server.entity.variants.mace_runt.MaceRuntModel;
import com.frogniche.legendfoxes.server.entity.variants.seeker.SeekerEntity;
import com.frogniche.legendfoxes.server.entity.variants.seeker.SeekerModel;
import com.frogniche.legendfoxes.server.entity.variants.spore_medic.SporeMedicEntity;
import com.frogniche.legendfoxes.server.entity.variants.spore_medic.SporeMedicModel;
import com.frogniche.legendfoxes.server.item.tabs.ModCreativeModeTabs;
import com.frogniche.legendfoxes.server.item.LFItemHandler;
import com.frogniche.legendfoxes.client.particle.ModParticles;
import com.frogniche.legendfoxes.client.sound.ModSounds;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LegendFoxes.MOD_ID)
public class LegendFoxes {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "legendfoxes";

    // Directly reference a slf4j logger
    public static ResourceLocation modLoc(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace

    public LegendFoxes() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        GeckoLib.initialize();
        LFItemHandler.register(modEventBus);
        LFBlockHandler.register(modEventBus);
        ModParticles.register(modEventBus);
        ModSounds.register(modEventBus);
        LFEntityHandler.ENTITIES.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerEntityAttributes);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::commonSetup);
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }
    private void clientSetup(FMLClientSetupEvent event) {

//Horde of the spore

        EntityRenderers.register(LFEntityHandler.DEVOURER.get(), DevourerRenderer::new);
        EntityRenderers.register(LFEntityHandler.FOXXO.get(), makeRenderer(new FoxxoModel()));
        EntityRenderers.register(LFEntityHandler.TEST.get(), makeRenderer(new TestModel()));
        EntityRenderers.register(LFEntityHandler.SEEKER.get(), makeRenderer(new SeekerModel()));
        EntityRenderers.register(LFEntityHandler.UNBREAKABLE.get(), UnbreakableRenderer::new);
        EntityRenderers.register(LFEntityHandler.SPORE_MEDIC.get(), makeRenderer(new SporeMedicModel()));
        EntityRenderers.register(LFEntityHandler.MACE_RUNT.get(), makeRenderer(new MaceRuntModel()));
        EntityRenderers.register(LFEntityHandler.BLAZE_RUNT.get(), makeRenderer(new BlazeRuntModel()));
        EntityRenderers.register(LFEntityHandler.BEAST.get(), makeRenderer(new BeastModel()));
        EntityRenderers.register(LFEntityHandler.BEAST.get(), makeRenderer(new BeastModel()));
    }
    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == ModCreativeModeTabs.LEGEND_FOXES_TAB) {
            event.accept(LFItemHandler.BONE_CLUDGEL);
            event.accept(LFItemHandler.UNBREAKABLE_CANNON);
            event.accept(LFItemHandler.DEVOURER_BANDAGE);
            event.accept(LFItemHandler.MOB_REMOVER);
            event.accept(LFItemHandler.UNBREAKABLE_HEAD);
            event.accept(LFItemHandler.UNBREAKABLE_CHESTPLATE);
            event.accept(LFItemHandler.DEVOURER_SKIRT);
            event.accept(LFBlockHandler.DEVOURER_SPORE);
          /* event.accept(ModBlocks.SPORE_BLOCK);
            event.accept(ModItems.UNBREAKABLE_CHESTPLATE);
            event.accept(ModItems.UNBREAKABLE_HEAD);
            event.accept(ModBlocks.SHROOMLIGHT);
            event.accept(ModBlocks.UNRIPE_SHROOMLIGHT);
            event.accept(ModBlocks.WARPED_GRASS);
            event.accept(ModBlocks.WARPED_VINES);
           event.accept(ModBlocks.WARPED_SPORES); */
            event.accept(LFItemHandler.DEVOURER_SPAWN_EGG);
            event.accept(LFItemHandler.FOXXO_SPAWN_EGG);
            event.accept(LFItemHandler.SEEKER_SPAWN_EGG);
            event.accept(LFItemHandler.SPORE_MEDIC_SPAWN_EGG);
            event.accept(LFItemHandler.UNBREAKABLE_SPAWN_EGG);
            event.accept(LFItemHandler.MACE_RUNT_SPAWN_EGG);
            event.accept(LFItemHandler.BLAZE_RUNT_SPAWN_EGG);
            event.accept(LFItemHandler.BEAST_SPAWN_EGG);
         /*   event.accept(ModBlocks.DIRT_WITH_MAGMA);
            event.accept(ModBlocks.DARK_DIRT);
            event.accept(ModBlocks.DARK_DIRT_PATH);
            event.accept(ModBlocks.LIGHT_DIRT_PATH);
            event.accept(ModBlocks.RED_DIRT_PATH);
            event.accept(ModBlocks.WARPED_BLOCK);
            event.accept(ModBlocks.CRIMSOM_BLOCK);
            event.accept(ModBlocks.WARPED_VINES);
            event.accept(ModBlocks.WARPED_WART);
            event.accept(ModBlocks.NETHERRACK);
            event.accept(ModBlocks.WARPED_WART);
            event.accept(ModBlocks.WARPED_SPROUT);
            event.accept(ModBlocks.FLAT_WARPED_WART);
            event.accept(ModBlocks.MEDIUM_WARPED_WART);
            event.accept(ModBlocks.SMALL_SHROOMLIGHT); */

        }
    }

    public static <T extends LivingEntity & GeoEntity> EntityRendererProvider<T> makeRenderer(GeoModel<T> model) {
        return m -> new HelperGeoRenderer<>(m, model);
    }


    public static class HelperGeoRenderer<T extends LivingEntity & GeoEntity> extends GeoEntityRenderer<T> {
        //example
        public HelperGeoRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> modelProvider) {
            super(renderManager, modelProvider);
        }
    }
    private void registerEntityAttributes(EntityAttributeCreationEvent event) {

        event.put(LFEntityHandler.DEVOURER.get(), EntityDevourer.makeAttributes());
        event.put(LFEntityHandler.TEST.get(), TestEntity.makeAttributes());


        event.put(LFEntityHandler.FOXXO.get(), FoxxoEntity.makeAttributes());
        event.put(LFEntityHandler.SEEKER.get(), SeekerEntity.makeAttributes());
        event.put(LFEntityHandler.UNBREAKABLE.get(), UnbreakableEntity.makeAttributes());
        event.put(LFEntityHandler.SPORE_MEDIC.get(), SporeMedicEntity.makeAttributes());
        event.put(LFEntityHandler.MACE_RUNT.get(), MaceRuntEntity.makeAttributes());
        event.put(LFEntityHandler.BLAZE_RUNT.get(), BlazeRuntEntity.makeAttributes());
        event.put(LFEntityHandler.BEAST.get(), BeastEntity.makeAttributes());
    }
   @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {
            event.register(LFEntityHandler.DEVOURER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        }
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("Hello from the Frog Swamp");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
