package me.flourick.fmc.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.MathHelper;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.flourick.fmc.FMC;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity
{
	public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile)
	{
		super(world, profile);
	}

	@Shadow
	int ticksLeftToDoubleTapSprint;

	@Inject(method = "setShowsDeathScreen", at = @At("HEAD"))
	private void onSetShowsDeathScreen(CallbackInfo info)
	{
		if(FMC.VARS.getIsAfterDeath() && FMC.OPTIONS.sendDeathCoordinates) {
			FMC.VARS.setIsAfterDeath(false);
			FMC.MC.inGameHud.addChatMessage(MessageType.CHAT, new LiteralText(String.format("You died at X: %.01f Z: %.01f Y: %.01f in %s!", FMC.VARS.getLastDeathX(), FMC.VARS.getLastDeathZ(), FMC.VARS.getLastDeathY(), FMC.VARS.getLastDeathWorld())), UUID.fromString("00000000-0000-0000-0000-000000000000"));
		}
	}

	@Inject(method = "tickMovement", at = @At("HEAD"), cancellable = true)
	private void onTickMovement(CallbackInfo info)
	{
		if(FMC.OPTIONS.disableWToSprint) {
			this.ticksLeftToDoubleTapSprint = -1;
		}

		if(FMC.VARS.freecam) {
			float forward = FMC.MC.player.input.movementForward;
			float up = (FMC.MC.player.input.jumping ? 1.0f : 0.0f) - (FMC.MC.player.input.sneaking ? 1.0f : 0.0f);
            float side = FMC.MC.player.input.movementSideways;
			
            FMC.VARS.freecamForwardSpeed = forward != 0 ? updateMotion(FMC.VARS.freecamForwardSpeed, forward) : FMC.VARS.freecamForwardSpeed * 0.5f;
            FMC.VARS.freecamUpSpeed = up != 0 ?  updateMotion(FMC.VARS.freecamUpSpeed, up) : FMC.VARS.freecamUpSpeed * 0.5f;
            FMC.VARS.freecamSideSpeed = side != 0 ?  updateMotion(FMC.VARS.freecamSideSpeed , side) : FMC.VARS.freecamSideSpeed * 0.5f;

            double rotateX = Math.sin(FMC.VARS.freecamYaw * Math.PI / 180.0D);
			double rotateZ = Math.cos(FMC.VARS.freecamYaw * Math.PI / 180.0D);
			double speed = FMC.MC.player.isSprinting() ? 1.2D : 0.55D;

			FMC.VARS.prevFreecamX = FMC.VARS.freecamX;
			FMC.VARS.prevFreecamY = FMC.VARS.freecamY;
			FMC.VARS.prevFreecamZ = FMC.VARS.freecamZ;

			FMC.VARS.freecamX += (FMC.VARS.freecamSideSpeed * rotateZ - FMC.VARS.freecamForwardSpeed * rotateX) * speed;
			FMC.VARS.freecamY += FMC.VARS.freecamUpSpeed * speed;
			FMC.VARS.freecamZ += (FMC.VARS.freecamForwardSpeed * rotateZ + FMC.VARS.freecamSideSpeed * rotateX) * speed;
		}
	}

	private float updateMotion(float motion, float direction)
    {
        return (direction + motion == 0) ? 0.0f : MathHelper.clamp(motion + ((direction < 0) ? -0.35f : 0.35f), -1f, 1f);
    }

	// PREVENTS MOVEMENT (freecam)
	@Inject(method = "isCamera", at = @At("HEAD"), cancellable = true)
	private void onIsCamera(CallbackInfoReturnable<Boolean> info)
	{
		if(FMC.VARS.freecam) {
			info.setReturnValue(false);
		}
	}

	// PREVENTS SNEAKING (freecam)
	@Inject(method = "isSneaking", at = @At("HEAD"), cancellable = true)
    private void onIsSneaking(CallbackInfoReturnable<Boolean> info)
    {
        if(FMC.VARS.freecam) {
            info.setReturnValue(false);
        }
    }

	@Override
	public void changeLookDirection(double cursorDeltaX, double cursorDeltaY)
	{
		if(FMC.VARS.freecam) {
			FMC.VARS.freecamYaw += cursorDeltaX * 0.15D;
			FMC.VARS.freecamPitch = MathHelper.clamp(FMC.VARS.freecamPitch + cursorDeltaY * 0.15D, -90, 90);
		}
		else {
			super.changeLookDirection(cursorDeltaX, cursorDeltaY);
		}
	}
}
