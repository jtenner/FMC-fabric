package me.flourick.fmc.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.CombatEventS2CPacket;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.flourick.fmc.FMC;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin
{
	@Inject(method = "onCombatEvent", at = @At("HEAD"))
	private void onOnCombatEvent(CombatEventS2CPacket packet, CallbackInfo info)
	{
		if(packet.type == CombatEventS2CPacket.Type.ENTITY_DIED) {
			Entity entity = FMC.MC.world.getEntityById(packet.entityId);
			
			if(entity == FMC.MC.player) {
				FMC.VARS.setLastDeathCoordinates(FMC.MC.player.getX(), FMC.MC.player.getY(), FMC.MC.player.getZ(), FMC.MC.player.clientWorld.getRegistryKey().getValue().toString().split(":")[1].replace('_', ' '));
				FMC.VARS.setIsAfterDeath(true);
			}
		}
	}
}
