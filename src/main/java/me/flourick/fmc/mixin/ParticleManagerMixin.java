package me.flourick.fmc.mixin;

import net.minecraft.client.particle.ParticleManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.flourick.fmc.FMC;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin
{
	@Inject(method = "addBlockBreakParticles", at = @At("HEAD"), cancellable = true)
    private void disableBlockBreakParticles(CallbackInfo info)
    {
        if(FMC.OPTIONS.noBlockBreakParticles) {
			info.cancel();
		}
    }
}
