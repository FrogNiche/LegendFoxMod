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

    public static final ImmutableList<Supplier<SoundEvent>> DEVOURER_HURT = ImmutableList.of(
            DEVOURER_HURT1::get,
            DEVOURER_HURT2::get,
            DEVOURER_HURT3::get,
            DEVOURER_HURT4::get,
            DEVOURER_HURT5::get

    );

    public static final RegistryObject<SoundEvent> DEVOURER_IDLE_1 = registerSoundEvent("devourer.idle1");
    public static final RegistryObject<SoundEvent> DEVOURER_IDLE_2 = registerSoundEvent("devourer.idle2");
    public static final RegistryObject<SoundEvent> DEVOURER_IDLE_3 = registerSoundEvent("devourer.idle3");
    public static final RegistryObject<SoundEvent> DEVOURER_IDLE_4 = registerSoundEvent("devourer.idle4");
    public static final RegistryObject<SoundEvent> DEVOURER_IDLE_5 = registerSoundEvent("devourer.idle5");
    public static final RegistryObject<SoundEvent> DEVOURER_IDLE_6 = registerSoundEvent("devourer.idle6");
    public static final RegistryObject<SoundEvent> DEVOURER_IDLE_7 = registerSoundEvent("devourer.idle7");
    public static final RegistryObject<SoundEvent> DEVOURER_IDLE_8 = registerSoundEvent("devourer.idle8");
    public static final RegistryObject<SoundEvent> DEVOURER_IDLE_9 = registerSoundEvent("devourer.idle9");
    public static final ImmutableList<Supplier<SoundEvent>> DEVOURER_IDLE = ImmutableList.of(
            DEVOURER_IDLE_1::get,
            DEVOURER_IDLE_2::get,
            DEVOURER_IDLE_3::get,
            DEVOURER_IDLE_4::get,
            DEVOURER_IDLE_5::get,
            DEVOURER_IDLE_6::get,
            DEVOURER_IDLE_7::get,
            DEVOURER_IDLE_8::get,
            DEVOURER_IDLE_9::get
    );
    public static final RegistryObject<SoundEvent> DEVOURER_WALK1 = registerSoundEvent("_walk1");

    public static final RegistryObject<SoundEvent> DEVOURER_WALK2 = registerSoundEvent("devourer_walk2");

    public static final RegistryObject<SoundEvent> DEVOURER_WALK3 = registerSoundEvent("devourer_walk3");

    public static final RegistryObject<SoundEvent> DEVOURER_WALK4 = registerSoundEvent("devourer_walk4");

    public static final RegistryObject<SoundEvent> DEVOURER_WALK5 = registerSoundEvent("devourer_walk5");

    public static final RegistryObject<SoundEvent> DEVOURER_WALK6 = registerSoundEvent("devourer_walk6");
    public static final RegistryObject<SoundEvent> DEVOURER_WALK7 = registerSoundEvent("devourer_walk7");
    public static final RegistryObject<SoundEvent> DEVOURER_WALK8 = registerSoundEvent("devourer_walk8");
    public static final RegistryObject<SoundEvent> DEVOURER_WALK9 = registerSoundEvent("devourer_walk9");
    public static final RegistryObject<SoundEvent> DEVOURER_WALK10 = registerSoundEvent("devourer_walk10");

    public static final ImmutableList<Supplier<SoundEvent>> DEVOURER_WALK = ImmutableList.of(
            DEVOURER_WALK1::get,
            DEVOURER_WALK2::get,
            DEVOURER_WALK3::get,
            DEVOURER_WALK4::get,
            DEVOURER_WALK5::get,
            DEVOURER_WALK6::get,
            DEVOURER_WALK7::get,
            DEVOURER_WALK8::get,
            DEVOURER_WALK9::get,
            DEVOURER_WALK10::get
    );
    public static final RegistryObject<SoundEvent> UNBREAKABLE_HURT = registerSoundEvent("unbreakable_hurt");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = new ResourceLocation(LegendFoxes.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}