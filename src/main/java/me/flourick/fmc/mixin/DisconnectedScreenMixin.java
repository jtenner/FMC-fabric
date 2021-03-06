package me.flourick.fmc.mixin;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import me.flourick.fmc.FMC;
import me.flourick.fmc.utils.Color;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisconnectedScreen.class)
public class DisconnectedScreenMixin
{
	@Shadow
    private int reasonHeight;

	@Inject(method = "init", at = @At("HEAD"))
    private void onInit(CallbackInfo info)
    {
        if(FMC.OPTIONS.autoReconnect) {
			FMC.VARS.autoReconnectTicks = FMC.OPTIONS.autoReconnectTimeout * 20;
			FMC.VARS.autoReconnectTries += 1;
		}
	}
	
	@Inject(method = "render", at = @At("RETURN"))
    private void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info)
    {
        if(FMC.OPTIONS.autoReconnect && FMC.MC.currentScreen != null && (FMC.VARS.autoReconnectTries <= FMC.OPTIONS.autoReconnectMaxTries && FMC.VARS.autoReconnectTries > 0)) {
			DrawableHelper.drawCenteredString(matrices, FMC.MC.textRenderer, "Reconnecting in: " + MathHelper.ceil(FMC.VARS.autoReconnectTicks / 20.0f) + "s (" + (FMC.OPTIONS.autoReconnectMaxTries + 1 - FMC.VARS.autoReconnectTries) + " tries left)", FMC.MC.currentScreen.width / 2, FMC.MC.currentScreen.height - this.reasonHeight / 2 - 2*FMC.MC.textRenderer.fontHeight, Color.WHITE.getPacked());
		}
    }
}
