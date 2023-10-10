package com.frogniche.legendfoxes.event;

import com.frogniche.legendfoxes.client.particle.ModParticles;
import com.frogniche.legendfoxes.client.particle.SpitParticles;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModEventsBusEvents {

    @SubscribeEvent
    public static void RegisterParticleProvidersEvent(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.SPIT_PARTICLES.get(), SpitParticles.Provider::new);

    }
}
