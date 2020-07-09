package flour.fmc.mixin;

import flour.fmc.FMC;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin
{
	@Shadow
	int ticksLeftToDoubleTapSprint;

	@Inject(method = "tickMovement", at = @At("HEAD"))
	private void onTickMovement(CallbackInfo info)
	{
		if(FMC.OPTIONS.disableWToSprint) {
			this.ticksLeftToDoubleTapSprint = -1;
		}
	}

	@Inject(method = "setShowsDeathScreen", at = @At("HEAD"))
	private void afterDeath(CallbackInfo info)
	{
		if(FMC.INSTANCE.isAfterDeath && FMC.OPTIONS.showDeathCoordinates) {
			FMC.INSTANCE.isAfterDeath = false;
			FMC.MC.inGameHud.addChatMessage(MessageType.CHAT, new LiteralText(String.format("You died at X: %.01f Z: %.01f Y: %.01f in %s!", FMC.INSTANCE.getLastDeathX(), FMC.INSTANCE.getLastDeathZ(), FMC.INSTANCE.getLastDeathY(), FMC.INSTANCE.getLastDeathWorld())), UUID.fromString("00000000-0000-0000-0000-000000000000"));
		}
	}
}
