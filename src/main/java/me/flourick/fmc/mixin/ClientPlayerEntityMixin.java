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
