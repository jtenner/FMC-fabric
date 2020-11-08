package me.flourick.fmc.mixin;

import net.minecraft.client.render.SkyProperties;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.flourick.fmc.FMC;

@Mixin(SkyProperties.Nether.class)
public class SkyPropertiesNetherMixin
{
	@Inject(method = "useThickFog", at = @At("HEAD"), cancellable = true)
    private void disableNetherFog(CallbackInfoReturnable<Boolean> info)
    {
        if(FMC.OPTIONS.noNetherFog) {
			info.setReturnValue(false);
		}
    }
}
