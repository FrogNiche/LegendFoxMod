package com.frogniche.legendfoxes.sound;

import com.frogniche.legendfoxes.LegendFoxes;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, LegendFoxes.MOD_ID);

// Devourer sounds
    public static final RegistryObject<SoundEvent> DEVOURER_HURT1 = registerSoundEvent("devourer_hurt1");
    public static final RegistryObject<SoundEvent> DEVOURER_HURT2 = registerSoundEvent("devourer_hurt2");
    public static final RegistryObject<SoundEvent> DEVOURER_HURT3 = registerSoundEvent("devourer_hurt3");
    public static final RegistryObject<SoundEvent> DEVOURER_HURT4 = registerSoundEvent("devourer_hurt4");
    public static final RegistryObject<SoundEvent> DEVOURER_HURT5 = registerSoundEvent("devourer_hurt5");

    public static final ImmutableList<Supplier<SoundEvent>> ENTITY_DEVOURER_HURT = ImmutableList.of(
            DEVOURER_HURT1::get,
            DEVOURER_HURT2::get,
            DEVOURER_HURT3::get,
            DEVOURER_HURT4::get,
            DEVOURER_HURT5::get

    );
    public static final RegistryObject<SoundEvent> ENTITY_DEVOURER_IDLE_1 = registerSoundEvent("devourer.idle1");
    public static final RegistryObject<SoundEvent> ENTITY_DEVOURER_IDLE_2 = registerSoundEvent("devourer.idle2");
    public static final RegistryObject<SoundEvent> ENTITY_DEVOURER_IDLE_3 = registerSoundEvent("devourer.idle3");
    public static final RegistryObject<SoundEvent> ENTITY_DEVOURER_IDLE_4 = registerSoundEvent("devourer.idle4");
    public static final RegistryObject<SoundEvent> ENTITY_DEVOURER_IDLE_5 = registerSoundEvent("devourer.idle5");
    public static final RegistryObject<SoundEvent> ENTITY_DEVOURER_IDLE_6 = registerSoundEvent("devourer.idle6");
    public static final RegistryObject<SoundEvent> ENTITY_DEVOURER_IDLE_7 = registerSoundEvent("devourer.idle7");
    public static final RegistryObject<SoundEvent> ENTITY_DEVOURER_IDLE_8 = registerSoundEvent("devourer.idle8");
    public static final RegistryObject<SoundEvent> ENTITY_DEVOURER_IDLE_9 = registerSoundEvent("devourer.idle9");
    public static final ImmutableList<Supplier<SoundEvent>> ENTITY_DEVOURER_IDLE = ImmutableList.of(
            ENTITY_DEVOURER_IDLE_1::get,
            ENTITY_DEVOURER_IDLE_2::get,
            ENTITY_DEVOURER_IDLE_3::get,
            ENTITY_DEVOURER_IDLE_4::get,
            ENTITY_DEVOURER_IDLE_5::get,
            ENTITY_DEVOURER_IDLE_6::get,
            ENTITY_DEVOURER_IDLE_7::get,
            ENTITY_DEVOURER_IDLE_8::get,
            ENTITY_DEVOURER_IDLE_9::get
    );
    public static final RegistryObject<SoundEvent> ENTITY_DEVOURER_WALK1 = registerSoundEvent("devourer.walk1");

    public static final RegistryObject<SoundEvent> ENTITY_DEVOURER_WALK2 = registerSoundEvent("devourer.walk2");

    public static final RegistryObject<SoundEvent> ENTITY_DEVOURER_WALK3 = registerSoundEvent("devourer.walk3");

    public static final RegistryObject<SoundEvent> ENTITY_DEVOURER_WALK4 = registerSoundEvent("devourer.walk4");

    public static final RegistryObject<SoundEvent> ENTITY_DEVOURER_WALK5 = registerSoundEvent("devourer.walk5");

    public static final RegistryObject<SoundEvent> ENTITY_DEVOURER_WALK6 = registerSoundEvent("devourer.walk6");
    public static final RegistryObject<SoundEvent> ENTITY_DEVOURER_WALK7 = registerSoundEvent("devourer.walk7");
    public static final RegistryObject<SoundEvent> ENTITY_DEVOURER_WALK8 = registerSoundEvent("devourer.walk8");
    public static final RegistryObject<SoundEvent> ENTITY_DEVOURER_WALK9 = registerSoundEvent("devourer.walk9");
    public static final RegistryObject<SoundEvent> ENTITY_DEVOURER_WALK10 = registerSoundEvent("devourer.walk10");

    public static final ImmutableList<Supplier<SoundEvent>> ENTITY_DEVOURER_WALK = ImmutableList.of(
            ENTITY_DEVOURER_WALK1::get,
            ENTITY_DEVOURER_WALK2::get,
            ENTITY_DEVOURER_WALK3::get,
            ENTITY_DEVOURER_WALK4::get,
            ENTITY_DEVOURER_WALK5::get,
            ENTITY_DEVOURER_WALK6::get,
            ENTITY_DEVOURER_WALK7::get,
            ENTITY_DEVOURER_WALK8::get,
            ENTITY_DEVOURER_WALK9::get,
            ENTITY_DEVOURER_WALK10::get
    );
    public static final RegistryObject<SoundEvent> UNBREAKABLE_HURT = registerSoundEvent("unbreakable_hurt");
//
    public static final ForgeSoundType BOSS_SOUNDS = new ForgeSoundType(50f, 0.20f,
        (java.util.function.Supplier<SoundEvent>) ModSounds.UNBREAKABLE_HURT, (java.util.function.Supplier<SoundEvent>) ModSounds.ENTITY_DEVOURER_HURT, (java.util.function.Supplier<SoundEvent>) ModSounds.ENTITY_DEVOURER_WALK,
            ModSounds.UNBREAKABLE_HURT, ModSounds.UNBREAKABLE_HURT);

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = new ResourceLocation(LegendFoxes.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}