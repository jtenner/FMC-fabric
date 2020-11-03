package me.flourick.fmc.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.flourick.fmc.FMC;

@Mixin(Camera.class)
public class CameraMixin
{
	@Shadow
	private float yaw;
	@Shadow
	private float pitch;
	@Shadow
	private boolean ready;
	@Shadow
	private BlockView area;

	@Shadow
	protected void setRotation(float yaw, float pitch) {};

	@Shadow
	protected void setPos(double x, double y, double z) {};

	@Inject(method = "update", at = @At("HEAD"), cancellable = true)
	private void onUpdate(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo info)
	{
		if(FMC.VARS.freecam) {
			this.ready = true;
			this.area = area;

			this.setRotation((float)FMC.VARS.freecamYaw, (float)FMC.VARS.freecamPitch);
			this.setPos(MathHelper.lerp((double)tickDelta, FMC.VARS.prevFreecamX, FMC.VARS.freecamX), MathHelper.lerp((double)tickDelta, FMC.VARS.prevFreecamY, FMC.VARS.freecamY), MathHelper.lerp((double)tickDelta, FMC.VARS.prevFreecamZ, FMC.VARS.freecamZ));

			info.cancel();
		}
	}

	@Inject(method = "isThirdPerson", at = @At("HEAD"), cancellable = true)
	public void onIsThirdPerson(CallbackInfoReturnable<Boolean> info)
	{
		if(FMC.VARS.freecam) {
			info.setReturnValue(true);
		}
	}
}
