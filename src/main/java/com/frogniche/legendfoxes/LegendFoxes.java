package com.frogniche.legendfoxes;

import com.frogniche.legendfoxes.block.ModBlocks;
import com.frogniche.legendfoxes.entity.EntityInit;
import com.frogniche.legendfoxes.entity.horde_of_the_bastion.unbreakable.UnbreakableRenderer;
import com.frogniche.legendfoxes.entity.horde_of_the_bastion.unbreakable.UnbreakableEntity;
import com.frogniche.legendfoxes.entity.horde_of_the_hunt.beast.BeastEntity;
import com.frogniche.legendfoxes.entity.horde_of_the_hunt.beast.BeastModel;
import com.frogniche.legendfoxes.entity.horde_of_the_hunt.foxxo.FoxxoEntity;
import com.frogniche.legendfoxes.entity.horde_of_the_hunt.foxxo.FoxxoModel;
import com.frogniche.legendfoxes.entity.horde_of_the_spore.devourer.DevourerEntity;
import com.frogniche.legendfoxes.entity.horde_of_the_spore.devourer.DevourerRenderer;
import com.frogniche.legendfoxes.entity.horde_of_the_spore.test.TestEntity;
import com.frogniche.legendfoxes.entity.horde_of_the_spore.test.TestModel;
import com.frogniche.legendfoxes.entity.variants.blaze_runt.BlazeRuntEntity;
import com.frogniche.legendfoxes.entity.variants.blaze_runt.BlazeRuntModel;
import com.frogniche.legendfoxes.entity.variants.mace_runt.MaceRuntEntity;
import com.frogniche.legendfoxes.entity.variants.mace_runt.MaceRuntModel;
import com.frogniche.legendfoxes.entity.variants.seeker.SeekerEntity;
import com.frogniche.legendfoxes.entity.variants.seeker.SeekerModel;
import com.frogniche.legendfoxes.entity.variants.spore_medic.SporeMedicEntity;
import com.frogniche.legendfoxes.entity.variants.spore_medic.SporeMedicModel;
import com.frogniche.legendfoxes.item.ModCreativeModeTabs;
import com.frogniche.legendfoxes.item.ModItems;
import com.frogniche.legendfoxes.particle.ModParticles;
import com.frogniche.legendfoxes.sound.ModSounds;
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
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModParticles.register(modEventBus);
        ModSounds.register(modEventBus);
        EntityInit.ENTITIES.register(modEventBus);
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

        EntityRenderers.register(EntityInit.DEVOURER.get(), DevourerRenderer::new);
        EntityRenderers.register(EntityInit.FOXXO.get(), makeRenderer(new FoxxoModel()));
        EntityRenderers.register(EntityInit.TEST.get(), makeRenderer(new TestModel()));
        EntityRenderers.register(EntityInit.SEEKER.get(), makeRenderer(new SeekerModel()));
        EntityRenderers.register(EntityInit.UNBREAKABLE.get(), UnbreakableRenderer::new);
        EntityRenderers.register(EntityInit.SPORE_MEDIC.get(), makeRenderer(new SporeMedicModel()));
        EntityRenderers.register(EntityInit.MACE_RUNT.get(), makeRenderer(new MaceRuntModel()));
        EntityRenderers.register(EntityInit.BLAZE_RUNT.get(), makeRenderer(new BlazeRuntModel()));
        EntityRenderers.register(EntityInit.BEAST.get(), makeRenderer(new BeastModel()));
        EntityRenderers.register(EntityInit.BEAST.get(), makeRenderer(new BeastModel()));
    }
    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == ModCreativeModeTabs.LEGEND_FOXES_TAB) {
            event.accept(ModItems.BONE_CLUDGEL);
            event.accept(ModItems.UNBREAKABLE_CANNON);
            event.accept(ModBlocks.SPORE_BLOCK);
            event.accept(ModBlocks.NETHER_PATH1);
            event.accept(ModBlocks.NETHER_PATH2);
            event.accept(ModBlocks.NETHER_PATH3);
            event.accept(ModBlocks.NETHER_PATH4);
            event.accept(ModBlocks.NETHER_PATH5);
            event.accept(ModBlocks.LAVA_DIRT);
         /*   event.accept(ModBlocks.LAVA_DIRT1);
            event.accept(ModBlocks.LAVA_DIRT2); */
            event.accept(ModBlocks.NETHER_WARPED);
            event.accept(ModBlocks.NETHER_WARPED_PATH);
            event.accept(ModBlocks.NO_LAVA_PATH);
            event.accept(ModBlocks.WARPED_WART);
            event.accept(ModBlocks.HALF_NETHER_BLOCK);
            event.accept(ModBlocks.NETHERRACK);
            event.accept(ModBlocks.WARPED_VINES);
            event.accept(ModBlocks.SPORES);
            event.accept(ModBlocks.WARPED_FRUIT);
            event.accept(ModBlocks.WARPED_HOLDER);

            event.accept(ModItems.UNBREAKABLE_CHESTPLATE);
            event.accept(ModItems.UNBREAKABLE_HEAD);
            event.accept(ModItems.DEVOURER_SPIT_CHARGE);
            event.accept(ModItems.DEVOURER_SPAWN_EGG);
            event.accept(ModItems.FOXXO_SPAWN_EGG);
            event.accept(ModItems.SEEKER_SPAWN_EGG);
            event.accept(ModItems.SPORE_MEDIC_SPAWN_EGG);
            event.accept(ModItems.UNBREAKABLE_SPAWN_EGG);
            event.accept(ModItems.MACE_RUNT_SPAWN_EGG);
            event.accept(ModItems.BLAZE_RUNT_SPAWN_EGG);
            event.accept(ModItems.BEAST_SPAWN_EGG);
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

        event.put(EntityInit.DEVOURER.get(), DevourerEntity.makeAttributes());
        event.put(EntityInit.TEST.get(), TestEntity.makeAttributes());


        event.put(EntityInit.FOXXO.get(), FoxxoEntity.makeAttributes());
        event.put(EntityInit.SEEKER.get(), SeekerEntity.makeAttributes());
        event.put(EntityInit.UNBREAKABLE.get(), UnbreakableEntity.makeAttributes());
        event.put(EntityInit.SPORE_MEDIC.get(), SporeMedicEntity.makeAttributes());
        event.put(EntityInit.MACE_RUNT.get(), MaceRuntEntity.makeAttributes());
        event.put(EntityInit.BLAZE_RUNT.get(), BlazeRuntEntity.makeAttributes());
        event.put(EntityInit.BEAST.get(), BeastEntity.makeAttributes());
    }
   @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {
            event.register(EntityInit.DEVOURER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
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
