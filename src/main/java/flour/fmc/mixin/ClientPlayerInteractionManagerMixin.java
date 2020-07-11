package flour.fmc.mixin;

import flour.fmc.FMC;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.TridentItem;
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
		System.out.println("Interact item!");

		if(FMC.OPTIONS.noToolBreaking) {
			ItemStack mainHandItem = FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.MAIN_HAND);
			ItemStack offHandItem = FMC.MC.player.getStackInHand(Hand.OFF_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.OFF_HAND);

			if(mainHandItem != null && mainHandItem.isDamaged()) {
				if(mainHandItem.getItem() instanceof CrossbowItem) {
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 10) {
						info.setReturnValue(ActionResult.FAIL);
					}
				}
				else if(mainHandItem.getItem() instanceof FishingRodItem) {
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 6) {
						info.setReturnValue(ActionResult.FAIL);
					}
				}
				else {
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 2) {
						info.setReturnValue(ActionResult.FAIL);
					}
				}
			}

			if(offHandItem != null && offHandItem.isDamaged()) {
				if(offHandItem.getItem() instanceof CrossbowItem) {
					if(offHandItem.getMaxDamage() - offHandItem.getDamage() < 10) {
						info.setReturnValue(ActionResult.FAIL);
					}
				}
				else if(offHandItem.getItem() instanceof FishingRodItem) {
					if(offHandItem.getMaxDamage() - offHandItem.getDamage() < 6) {
						info.setReturnValue(ActionResult.FAIL);
					}
				}
				else {
					if(offHandItem.getMaxDamage() - offHandItem.getDamage() < 2) {
						info.setReturnValue(ActionResult.FAIL);
					}
				}
			}
		}
	}

	@Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
	public void onInteractBlock(CallbackInfoReturnable<ActionResult> info)
	{
		System.out.println("Interact block!");

		if(FMC.OPTIONS.noToolBreaking) {
			ItemStack mainHandItem = FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.MAIN_HAND);
			ItemStack offHandItem = FMC.MC.player.getStackInHand(Hand.OFF_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.OFF_HAND);

			if(mainHandItem != null && mainHandItem.isDamaged()) {
				if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 2) {
					info.setReturnValue(ActionResult.FAIL);
				}
			}

			if(offHandItem != null && offHandItem.isDamaged()) {
				if(offHandItem.getMaxDamage() - offHandItem.getDamage() < 2) {
					info.setReturnValue(ActionResult.FAIL);
				}
			}
		}
	}

	@Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
	public void onAttackBlock(CallbackInfoReturnable<Boolean> info)
	{
		System.out.println("Attack block!");

		if(FMC.OPTIONS.noToolBreaking) {
			//ItemStack handItem = FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isEmpty() ? FMC.MC.player.getStackInHand(Hand.OFF_HAND) : FMC.MC.player.getStackInHand(Hand.MAIN_HAND);
			ItemStack mainHandItem = FMC.MC.player.getStackInHand(Hand.MAIN_HAND);

			if(mainHandItem.isDamaged()) {
				if(mainHandItem.getItem() instanceof SwordItem) {
					// TWO DURA
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 3) {
						info.setReturnValue(false);
					}
				}
				else if(mainHandItem.getItem() instanceof TridentItem) {
					// TWO DURA
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 3) {
						info.setReturnValue(false);
					}
				}
				else {
					// ONE DURA
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 2) {
						info.setReturnValue(false);
					}
				}
			}
		}
	}

	@Inject(method = "breakBlock", at = @At("HEAD"), cancellable = true)
	public void onBreakBlock(CallbackInfoReturnable<Boolean> info)
	{
		System.out.println("Break block!");

		if(FMC.OPTIONS.noToolBreaking) {
			ItemStack mainHandItem = FMC.MC.player.getStackInHand(Hand.MAIN_HAND);

			if(mainHandItem.isDamaged()) {
				if(mainHandItem.getItem() instanceof SwordItem) {
					// TWO DURA
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 3) {
						info.setReturnValue(false);
					}
				}
				else if(mainHandItem.getItem() instanceof TridentItem) {
					// TWO DURA
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 3) {
						info.setReturnValue(false);
					}
				}
				else {
					// ONE DURA
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 2) {
						info.setReturnValue(false);
					}
				}
			}
		}
	}

	@Inject(method = "attackEntity", at = @At("HEAD"), cancellable = true)
	public void onAttackEntity(CallbackInfo info)
	{
		System.out.println("Attack entity!");

		if(FMC.OPTIONS.noToolBreaking && FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isDamaged() && (FMC.MC.player.getStackInHand(Hand.MAIN_HAND).getMaxDamage() - FMC.MC.player.getStackInHand(Hand.MAIN_HAND).getDamage() < 3)) {
			info.cancel();
		}
	}

	@Inject(method = "interactEntity", at = @At("HEAD"), cancellable = true)
	public void onInteractEntity(CallbackInfoReturnable<ActionResult> info)
	{
		System.out.println("Interact entity!");

		if(FMC.OPTIONS.noToolBreaking && FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isDamaged() && (FMC.MC.player.getStackInHand(Hand.MAIN_HAND).getMaxDamage() - FMC.MC.player.getStackInHand(Hand.MAIN_HAND).getDamage() < 3)) {
			info.setReturnValue(ActionResult.FAIL);
		}
	}

	@Inject(method = "interactEntityAtLocation", at = @At("HEAD"), cancellable = true)
	public void onInteractEntityAtLocation(CallbackInfoReturnable<ActionResult> info)
	{
		System.out.println("Interact entity at location!");

		if(FMC.OPTIONS.noToolBreaking && FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isDamaged() && (FMC.MC.player.getStackInHand(Hand.MAIN_HAND).getMaxDamage() - FMC.MC.player.getStackInHand(Hand.MAIN_HAND).getDamage() < 3)) {
			info.setReturnValue(ActionResult.FAIL);
		}
	}
}
