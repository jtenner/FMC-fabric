package flour.fmc.mixin;

import flour.fmc.FMC;

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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin extends DrawableHelper
{
	@Inject(method = "renderStatusEffectOverlay", at = @At("RETURN"))
	private void onRenderStatusEffectOverlay(MatrixStack matrixStack, CallbackInfo info)
	{
		// renders on screen text only if not in debug or hud is hidden or if options don't say so
		if(FMC.MC.options.debugEnabled || FMC.MC.options.hudHidden || !FMC.OPTIONS.showHUDInfo) {
			return;	
		}

		// HUD info moves to the top if chat is open
		if(FMC.MC.currentScreen instanceof ChatScreen) {
			FMC.INSTANCE.getOnScreenText().drawCoordinatesTextUpper(matrixStack);
			FMC.INSTANCE.getOnScreenText().drawLightLevelTextUpper(matrixStack);
			FMC.INSTANCE.getOnScreenText().drawPFTextUpper(matrixStack);
		}
		else {
			FMC.INSTANCE.getOnScreenText().drawCoordinatesTextLower(matrixStack);
			FMC.INSTANCE.getOnScreenText().drawLightLevelTextLower(matrixStack);
			FMC.INSTANCE.getOnScreenText().drawPFTextLower(matrixStack);
		}
	}

	@Overwrite
	private void renderCrosshair(MatrixStack matrixStack)
	{
		final GameOptions gameOptions = FMC.MC.options;

		int scaledWidth = FMC.MC.getWindow().getScaledWidth();
		int scaledHeight = FMC.MC.getWindow().getScaledHeight();

		if(gameOptions.perspective == 0) {
			if(gameOptions.debugEnabled && !gameOptions.hudHidden && !FMC.MC.player.getReducedDebugInfo() && !gameOptions.reducedDebugInfo) {
				RenderSystem.pushMatrix();
				RenderSystem.translatef((float) (scaledWidth / 2), (float) (scaledHeight / 2), (float) this.getZOffset());
				Camera camera = FMC.MC.gameRenderer.getCamera();
				RenderSystem.rotatef(camera.getPitch(), -1.0F, 0.0F, 0.0F);
				RenderSystem.rotatef(camera.getYaw(), 0.0F, 1.0F, 0.0F);
				RenderSystem.scalef(-1.0F, -1.0F, -1.0F);
				RenderSystem.renderCrosshair(10);
				RenderSystem.popMatrix();
			}
			else {
				RenderSystem.pushMatrix();
				RenderSystem.translatef((float)(scaledWidth / 2), (float)(scaledHeight / 2), (float)this.getZOffset());
				RenderSystem.enableBlend();
				RenderSystem.blendColor(FMC.OPTIONS.crosshairColor.getNormRed(), FMC.OPTIONS.crosshairColor.getNormGreen(), FMC.OPTIONS.crosshairColor.getNormBlue(), FMC.OPTIONS.crosshairColor.getNormAlpha());
				RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.CONSTANT_COLOR, GlStateManager.DstFactor.ZERO, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
				RenderSystem.scaled(FMC.OPTIONS.crosshairScale, FMC.OPTIONS.crosshairScale, 1.0d);
				this.drawTexture(matrixStack, -15/2, -15/2, 0, 0, 15, 15);
				RenderSystem.disableBlend();
				RenderSystem.popMatrix();

				if(FMC.MC.options.attackIndicator == AttackIndicator.CROSSHAIR) {
					float f = FMC.MC.player.getAttackCooldownProgress(0.0F);
					boolean bl = false;

					if(FMC.MC.targetedEntity != null && FMC.MC.targetedEntity instanceof LivingEntity && f >= 1.0F) {
						bl = FMC.MC.player.getAttackCooldownProgressPerTick() > 5.0F;
						bl &= FMC.MC.targetedEntity.isAlive();
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
