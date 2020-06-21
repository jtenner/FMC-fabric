package flour.fmc.mixin;

import flour.fmc.FMC;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.options.AttackIndicator;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.render.Camera;
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
	private void onRenderStatusEffectOverlay(CallbackInfo info)
	{
		// renders on screen text only if not in debug or hud is hidden or if options don't say so
		if(FMC.MC.options.debugEnabled || FMC.MC.options.hudHidden || !FMC.OPTIONS.showHUDInfo) {
			return;	
		}

		// HUD info moves to the top if chat is open
		if(FMC.MC.currentScreen instanceof ChatScreen) {
			FMC.INSTANCE.getOnScreenText().drawCoordinatesTextUpper();
			FMC.INSTANCE.getOnScreenText().drawLightLevelTextUpper();
			FMC.INSTANCE.getOnScreenText().drawPFTextUpper();
		}
		else {
			FMC.INSTANCE.getOnScreenText().drawCoordinatesTextLower();
			FMC.INSTANCE.getOnScreenText().drawLightLevelTextLower();
			FMC.INSTANCE.getOnScreenText().drawPFTextLower();
		}
	}

	@Overwrite
	private void renderCrosshair()
	{
		// completele overwrite rendering the crosshair
		final GameOptions gameOptions = FMC.MC.options;

        if(gameOptions.perspective != 0) {
			return;
		}

		int scaledWidth = FMC.MC.getWindow().getScaledWidth();
		int scaledHeight = FMC.MC.getWindow().getScaledHeight();

		// crosshair in debug screen
        if(gameOptions.debugEnabled && !gameOptions.hudHidden && !FMC.MC.player.getReducedDebugInfo() && !gameOptions.reducedDebugInfo) {
            RenderSystem.pushMatrix();
            RenderSystem.translatef((float)(scaledWidth / 2), (float)(scaledHeight / 2), (float)this.getBlitOffset());
            final Camera camera = FMC.MC.gameRenderer.getCamera();
            RenderSystem.rotatef(camera.getPitch(), -1.0f, 0.0f, 0.0f);
            RenderSystem.rotatef(camera.getYaw(), 0.0f, 1.0f, 0.0f);
            RenderSystem.scalef(-1.0f, -1.0f, -1.0f);
            RenderSystem.renderCrosshair(10);
            RenderSystem.popMatrix();
        }
        else {
			// renders the crosshair itself
			RenderSystem.pushMatrix();
			RenderSystem.translatef((float)(scaledWidth / 2), (float)(scaledHeight / 2), (float)this.getBlitOffset());
			RenderSystem.enableBlend();
			RenderSystem.blendColor(FMC.OPTIONS.crosshairColor.getNormRed(), FMC.OPTIONS.crosshairColor.getNormGreen(), FMC.OPTIONS.crosshairColor.getNormBlue(), FMC.OPTIONS.crosshairColor.getNormAlpha());
			RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.CONSTANT_COLOR, GlStateManager.DstFactor.ZERO, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
			RenderSystem.scaled(FMC.OPTIONS.crosshairScale, FMC.OPTIONS.crosshairScale, 1.0d);
			this.blit((int)(-15 / 2), (int)(-15 / 2), 0, 0, 15, 15);
			RenderSystem.disableBlend();
			RenderSystem.popMatrix();
			
			// renders the attack indicator if set to crosshair
            if(FMC.MC.options.attackIndicator == AttackIndicator.CROSSHAIR) {
                final float f = FMC.MC.player.getAttackCooldownProgress(0.0f);
				boolean bl = false;
				
                if(FMC.MC.targetedEntity != null && FMC.MC.targetedEntity instanceof LivingEntity && f >= 1.0f) {
                    bl = (FMC.MC.player.getAttackCooldownProgressPerTick() > 5.0f);
                    bl &= FMC.MC.targetedEntity.isAlive();
                }
                final int s = scaledHeight / 2 - 7 + 16;
				final int k = scaledWidth / 2 - 8;
				
                if(bl) {
                    this.blit(k, s, 68, 94, 16, 16);
                }
                else if(f < 1.0f) {
                    final int l = (int)(f * 17.0f);
                    this.blit(k, s, 36, 94, 16, 4);
                    this.blit(k, s, 52, 94, l, 4);
                }
			}
		}
	}
}
