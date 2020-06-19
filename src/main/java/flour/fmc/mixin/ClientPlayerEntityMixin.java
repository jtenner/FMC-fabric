package flour.fmc.mixin;

import flour.fmc.FMC;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin
{
	@Shadow
	int field_3935;

	@Inject(method = "tickMovement", at = @At("HEAD"))
	private void onTickMovement(CallbackInfo info)
	{
		if(FMC.OPTIONS.disableWToSprint) {
			this.field_3935 = -1;
		}
	}

	@Inject(method = "setShowsDeathScreen", at = @At("HEAD"))
	private void afterDeath(CallbackInfo info) {
		if(FMC.INSTANCE.isAfterDeath) {
			FMC.INSTANCE.isAfterDeath = false;
			FMC.MC.inGameHud.addChatMessage(MessageType.CHAT, new LiteralText(String.format("You died at X: %.01f Z: %.01f, Y: %.01f in %s!", FMC.INSTANCE.getLastDeathX(), FMC.INSTANCE.getLastDeathZ(), FMC.INSTANCE.getLastDeathY(), FMC.INSTANCE.getLastDeathWorld())));
		}
	}
}
