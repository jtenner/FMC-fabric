package me.flourick.fmc.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
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

            float forward = 0; float up = 0; float side = 0;
			
            if(FMC.MC.options.keyForward.isPressed()) {
				forward++;
			}
            if(FMC.MC.options.keyBack.isPressed()) {
				forward--;
			}
            if(FMC.MC.options.keyLeft.isPressed()) {
				side++;
			}
            if(FMC.MC.options.keyRight.isPressed()) {
				side--;
			}
            if(FMC.MC.options.keyJump.isPressed()) {
				up++;
			}
            if(FMC.MC.options.keySneak.isPressed()) {
				up--;
			}

			double rotateX = Math.sin(this.yaw * Math.PI / 180D);
			double rotateZ = Math.cos(this.yaw * Math.PI / 180D);
			double speed = (FMC.MC.player.isSprinting() ? 0.16D : 0.07D);

			FMC.VARS.freecamX += (side * rotateZ - forward * rotateX) * speed;
			FMC.VARS.freecamY += up * speed;
			FMC.VARS.freecamZ += (forward * rotateZ + side * rotateX) * speed;

			this.setPos(FMC.VARS.freecamX, FMC.VARS.freecamY, FMC.VARS.freecamZ);

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
