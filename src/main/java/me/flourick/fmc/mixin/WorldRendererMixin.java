package me.flourick.fmc.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.WorldRenderer;

import me.flourick.fmc.FMC;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin
{
	@Shadow
	private void setupTerrain(Camera camera, Frustum frustum, boolean hasForcedFrustum, int frame, boolean spectator) {};

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;setupTerrain(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/Frustum;ZIZ)V"))
	private void hijackSetupTerrain(WorldRenderer renderer, Camera camera, Frustum frustum, boolean hasForcedFrustum, int frame, boolean spectator)
	{
		this.setupTerrain(camera, frustum, hasForcedFrustum, frame, FMC.VARS.freecam ? true : spectator);
	}
}
