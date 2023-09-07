package com.frogniche.legendfoxes.event;

import com.frogniche.legendfoxes.particle.ModParticles;
import com.frogniche.legendfoxes.particle.SpitParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModEventsBusEvents {

    @SubscribeEvent
    public static void registerParticleFactories(final ModEventsBusEvents event) {
        Minecraft.getInstance().particleEngine.register(ModParticles.SPIT_PARTICLES.get(),
                SpitParticles.Provider::new);
    }
}
