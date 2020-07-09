package flour.fmc.mixin;

import flour.fmc.FMC;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin
{
	@Inject(method = "interactItem", at = @At("HEAD"), cancellable = true)
	public void onInteractItem(CallbackInfoReturnable<ActionResult> info)
	{
		//System.out.println("Interact item!");

		if(FMC.OPTIONS.noToolBreaking && FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isDamaged() && (FMC.MC.player.getStackInHand(Hand.MAIN_HAND).getMaxDamage() - FMC.MC.player.getStackInHand(Hand.MAIN_HAND).getDamage() < 2)) {
			info.setReturnValue(ActionResult.FAIL);
		}
	}

	@Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
	public void onInteractBlock(CallbackInfoReturnable<ActionResult> info)
	{
		//System.out.println("Interact block!");

		if(FMC.OPTIONS.noToolBreaking && FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isDamaged() && (FMC.MC.player.getStackInHand(Hand.MAIN_HAND).getMaxDamage() - FMC.MC.player.getStackInHand(Hand.MAIN_HAND).getDamage() < 2)) {
			info.setReturnValue(ActionResult.FAIL);
		}
	}

	@Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
	public void onAttackBlock(CallbackInfoReturnable<Boolean> info)
	{
		//System.out.println("Attack block!");

		if(FMC.OPTIONS.noToolBreaking && FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isDamaged() && (FMC.MC.player.getStackInHand(Hand.MAIN_HAND).getMaxDamage() - FMC.MC.player.getStackInHand(Hand.MAIN_HAND).getDamage() < 2)) {
			info.setReturnValue(false);
		}
	}

	@Inject(method = "breakBlock", at = @At("HEAD"), cancellable = true)
	public void onBreakBlock(CallbackInfoReturnable<Boolean> info)
	{
		//System.out.println("Break block!");

		if(FMC.OPTIONS.noToolBreaking && FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isDamaged() && (FMC.MC.player.getStackInHand(Hand.MAIN_HAND).getMaxDamage() - FMC.MC.player.getStackInHand(Hand.MAIN_HAND).getDamage() < 2)) {
			info.setReturnValue(false);
		}
	}

	@Inject(method = "attackEntity", at = @At("HEAD"), cancellable = true)
	public void onAttackEntity(CallbackInfo info)
	{
		//System.out.println("Attack entity!");

		if(FMC.OPTIONS.noToolBreaking && FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isDamaged() && (FMC.MC.player.getStackInHand(Hand.MAIN_HAND).getMaxDamage() - FMC.MC.player.getStackInHand(Hand.MAIN_HAND).getDamage() < 2)) {
			info.cancel();
		}
	}

	@Inject(method = "interactEntity", at = @At("HEAD"), cancellable = true)
	public void onInteractEntity(CallbackInfoReturnable<ActionResult> info)
	{
		//System.out.println("Interact entity!");

		if(FMC.OPTIONS.noToolBreaking && FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isDamaged() && (FMC.MC.player.getStackInHand(Hand.MAIN_HAND).getMaxDamage() - FMC.MC.player.getStackInHand(Hand.MAIN_HAND).getDamage() < 2)) {
			info.setReturnValue(ActionResult.FAIL);
		}
	}

	@Inject(method = "interactEntityAtLocation", at = @At("HEAD"), cancellable = true)
	public void onInteractEntityAtLocation(CallbackInfoReturnable<ActionResult> info)
	{
		//System.out.println("Interact entity at location!");

		if(FMC.OPTIONS.noToolBreaking && FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isDamaged() && (FMC.MC.player.getStackInHand(Hand.MAIN_HAND).getMaxDamage() - FMC.MC.player.getStackInHand(Hand.MAIN_HAND).getDamage() < 2)) {
			info.setReturnValue(ActionResult.FAIL);
		}
	}
}
