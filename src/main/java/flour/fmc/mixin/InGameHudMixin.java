package flour.fmc.mixin;

import flour.fmc.FMC;
import flour.fmc.utils.OnScreenText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.options.AttackIndicator;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin extends DrawableHelper
{
	@Shadow
	MinecraftClient client;

	@Inject(method = "tick", at = @At("HEAD"))
	private void onTick(CallbackInfo info)
	{
		if(FMC.INSTANCE.toolWarningTextTicksLeft > 0) {
			FMC.INSTANCE.toolWarningTextTicksLeft -= 1;
		}
	}

	@Inject(method = "render", at = @At("HEAD"))
	private void onRender(MatrixStack matrixStack, float f, CallbackInfo info)
	{
		// renders on screen text only if not in debug or hud is hidden or if options don't say so
		if(this.client.options.debugEnabled || this.client.options.hudHidden || !FMC.OPTIONS.showHUDInfo) {
			return;	
		}

		// HUD info moves to the top if chat is open
		if(FMC.MC.currentScreen instanceof ChatScreen) {
			OnScreenText.drawCoordinatesTextUpper(matrixStack);
			OnScreenText.drawLightLevelTextUpper(matrixStack);
			OnScreenText.drawPFTextUpper(matrixStack);
		}
		else {
			OnScreenText.drawCoordinatesTextLower(matrixStack);
			OnScreenText.drawLightLevelTextLower(matrixStack);
			OnScreenText.drawPFTextLower(matrixStack);
		}

		if(FMC.INSTANCE.toolWarningTextTicksLeft > 0) {
			RenderSystem.pushMatrix();
			RenderSystem.translatef((float)(this.client.getWindow().getScaledWidth() / 2), (float)(this.client.getWindow().getScaledHeight() / 2), (float)this.getZOffset());
			RenderSystem.scaled(FMC.OPTIONS.toolBreakingWarningScale, FMC.OPTIONS.toolBreakingWarningScale, 1.0d);
			OnScreenText.drawToolWarningText(matrixStack);
			RenderSystem.popMatrix();
		}
	}

	@Overwrite
	private void renderCrosshair(MatrixStack matrixStack)
	{
		final GameOptions gameOptions = this.client.options;

		int scaledWidth = this.client.getWindow().getScaledWidth();
		int scaledHeight = this.client.getWindow().getScaledHeight();

		if(gameOptions.perspective == 0) {
			if(gameOptions.debugEnabled && !gameOptions.hudHidden && !this.client.player.getReducedDebugInfo() && !gameOptions.reducedDebugInfo) {
				RenderSystem.pushMatrix();
				RenderSystem.translatef((float) (scaledWidth / 2), (float) (scaledHeight / 2), (float) this.getZOffset());
				Camera camera = this.client.gameRenderer.getCamera();
				RenderSystem.rotatef(camera.getPitch(), -1.0F, 0.0F, 0.0F);
				RenderSystem.rotatef(camera.getYaw(), 0.0F, 1.0F, 0.0F);
				RenderSystem.popMatrix();
				RenderSystem.renderCrosshair(10);
				RenderSystem.popMatrix();
			}
			else {
				RenderSystem.pushMatrix();
				RenderSystem.translatef((float)(scaledWidth / 2), (float)(scaledHeight / 2), (float)this.getZOffset());

				RenderSystem.enableBlend();
				if(FMC.OPTIONS.crosshairStaticColor) {
					RenderSystem.blendColor(FMC.OPTIONS.crosshairColor.getNormRed(), FMC.OPTIONS.crosshairColor.getNormGreen(), FMC.OPTIONS.crosshairColor.getNormBlue(), FMC.OPTIONS.crosshairColor.getNormAlpha());
					RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.CONSTANT_COLOR, GlStateManager.DstFactor.ZERO, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
				}
				else {
					RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.ONE_MINUS_DST_COLOR, GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
				}
				
				RenderSystem.scaled(FMC.OPTIONS.crosshairScale, FMC.OPTIONS.crosshairScale, 1.0d);
				this.drawTexture(matrixStack, -15/2, -15/2, 0, 0, 15, 15);
				RenderSystem.disableBlend();
				RenderSystem.popMatrix();

				if(gameOptions.attackIndicator == AttackIndicator.CROSSHAIR) {
					float f = this.client.player.getAttackCooldownProgress(0.0F);
					boolean bl = false;

					if(this.client.targetedEntity != null && this.client.targetedEntity instanceof LivingEntity && f >= 1.0F) {
						bl = this.client.player.getAttackCooldownProgressPerTick() > 5.0F;
						bl &= this.client.targetedEntity.isAlive();
					}
					int j = scaledHeight / 2 - 7 + 16;
					int k = scaledWidth / 2 - 8;

					if(bl) {
						this.drawTexture(matrixStack, k, j, 68, 94, 16, 16);
					}
					else if(f < 1.0F) {
						int l = (int) (f * 17.0F);
						this.drawTexture(matrixStack, k, j, 36, 94, 16, 4);
						this.drawTexture(matrixStack, k, j, 52, 94, l, 4);
					}
				}
			}
		}
	}
}
