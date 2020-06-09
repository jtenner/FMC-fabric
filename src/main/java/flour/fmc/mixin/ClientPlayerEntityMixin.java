package flour.fmc.mixin;

import flour.fmc.FMC;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffects;

import com.mojang.authlib.GameProfile;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity
{
	@Shadow
	int field_3935;

	public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile)
	{
		super(world, profile);
	}

	@Inject(method = { "tickMovement" }, at = @At("HEAD"))
	private void onTickMovement(CallbackInfo info)
	{
		// REMOVES THE HORRENDOUS DOUBLE-TAP W TO SPRINT FEATURETTE
		// boolean b1 = (float)this.getHungerManager().getFoodLevel() > 6.0F || this.abilities.allowFlying;
		// boolean b2 = this.isSubmergedInWater() ? FMC.MC.player.input.hasForwardMovement() : (double)FMC.MC.player.input.movementForward >= 0.8D;

		// if(!this.isSprinting() && (!this.isTouchingWater() || this.isSubmergedInWater()) && b1 && b2 && !this.isUsingItem() && !this.hasStatusEffect(StatusEffects.BLINDNESS) && FMC.MC.options.keySprint.isPressed()) {
		// 	//this.setSprinting(true);
		// }
		// else {
		// 	this.setSprinting(false);
		// }

		this.field_3935 = -1;
	}
}
