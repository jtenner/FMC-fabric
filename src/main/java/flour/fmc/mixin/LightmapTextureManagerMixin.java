package flour.fmc.mixin;

import flour.fmc.FMC;

import net.minecraft.client.render.LightmapTextureManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LightmapTextureManager.class)
public class LightmapTextureManagerMixin
{
	@Inject(method = "getBrightness", at = @At("HEAD"), cancellable = true)
	private void onGetBrightness(CallbackInfoReturnable<Float> info)
	{
		if(FMC.OPTIONS.fullbright) {
			info.setReturnValue(1f);
		}
	}
}
