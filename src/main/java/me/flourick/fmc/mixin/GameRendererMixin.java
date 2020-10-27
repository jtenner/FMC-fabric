package me.flourick.fmc.mixin;

import net.minecraft.client.render.GameRenderer;

import me.flourick.fmc.FMC;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin
{
	@Inject(method = "renderHand", at = @At("HEAD"), cancellable = true)
    private void removeHandRendering(CallbackInfo info)
    {
        if(FMC.VARS.freecam) {
            info.cancel();
        }
    }
}
