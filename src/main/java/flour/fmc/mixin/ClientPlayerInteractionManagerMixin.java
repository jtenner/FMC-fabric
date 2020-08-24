package flour.fmc.mixin;

import flour.fmc.FMC;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.OnAStickItem;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.TridentItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin
{
	@Shadow
	private void syncSelectedSlot() {};

	@Inject(method = "interactItem", at = @At("HEAD"), cancellable = true)
	public void onInteractItem(CallbackInfoReturnable<ActionResult> info)
	{
		if(FMC.DEBUG) {
			System.out.println("Interact item!");
		}

		this.syncSelectedSlot();

		if(FMC.OPTIONS.toolWarning) {
			ItemStack mainHandItem = FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.MAIN_HAND);
			ItemStack offHandItem = FMC.MC.player.getStackInHand(Hand.OFF_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.OFF_HAND);

			int mainHandDurability = Integer.MAX_VALUE;
			int offHandDurability = Integer.MAX_VALUE;

			if(mainHandItem != null && mainHandItem.isDamaged()) {
				mainHandDurability = mainHandItem.getMaxDamage() - mainHandItem.getDamage();

				if(MathHelper.floor(mainHandItem.getMaxDamage() * 0.9f) < mainHandItem.getDamage() + 1 && mainHandDurability < 13) {
					FMC.VARS.setToolDurability(mainHandDurability);
					FMC.VARS.setToolHand(Hand.MAIN_HAND);
					FMC.VARS.resetToolWarningTicks();
				}
			}

			if(offHandItem != null && offHandItem.isDamaged()) {
				offHandDurability = offHandItem.getMaxDamage() - offHandItem.getDamage();

				if(MathHelper.floor(offHandItem.getMaxDamage() * 0.9f) < offHandItem.getDamage() + 1 && offHandDurability < 13) {
					if(offHandDurability < mainHandDurability) {
						FMC.VARS.setToolDurability(offHandDurability);
						FMC.VARS.setToolHand(Hand.OFF_HAND);
					}
					FMC.VARS.resetToolWarningTicks();
				}
			}
		}

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
				else if(mainHandItem.getItem() instanceof OnAStickItem) {
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 2) {
						info.setReturnValue(ActionResult.FAIL);
					}
				}
				else if(mainHandItem.getItem() instanceof TridentItem) {
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 2) {
						info.setReturnValue(ActionResult.FAIL);
					}
				}
				else if(mainHandItem.getItem() instanceof BowItem) {
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
				else if(offHandItem.getItem() instanceof OnAStickItem) {
					if(offHandItem.getMaxDamage() - offHandItem.getDamage() < 2) {
						info.setReturnValue(ActionResult.FAIL);
					}
				}
				else if(offHandItem.getItem() instanceof TridentItem) {
					if(offHandItem.getMaxDamage() - offHandItem.getDamage() < 2) {
						info.setReturnValue(ActionResult.FAIL);
					}
				}
				else if(offHandItem.getItem() instanceof BowItem) {
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
		if(FMC.DEBUG) {
			System.out.println("Interact block!");
		}

		this.syncSelectedSlot();

		if(FMC.OPTIONS.toolWarning) {
			ItemStack mainHandItem = FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.MAIN_HAND);
			ItemStack offHandItem = FMC.MC.player.getStackInHand(Hand.OFF_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.OFF_HAND);

			int mainHandDurability = Integer.MAX_VALUE;
			int offHandDurability = Integer.MAX_VALUE;

			if(mainHandItem != null && mainHandItem.isDamaged()) {
				mainHandDurability = mainHandItem.getMaxDamage() - mainHandItem.getDamage();

				if(MathHelper.floor(mainHandItem.getMaxDamage() * 0.9f) < mainHandItem.getDamage() + 1 && mainHandDurability < 13) {
					FMC.VARS.setToolDurability(mainHandDurability);
					FMC.VARS.setToolHand(Hand.MAIN_HAND);
					FMC.VARS.resetToolWarningTicks();
				}
			}

			if(offHandItem != null && offHandItem.isDamaged()) {
				offHandDurability = offHandItem.getMaxDamage() - offHandItem.getDamage();

				if(MathHelper.floor(offHandItem.getMaxDamage() * 0.9f) < offHandItem.getDamage() + 1 && offHandDurability < 13) {
					if(offHandDurability < mainHandDurability) {
						FMC.VARS.setToolDurability(offHandDurability);
						FMC.VARS.setToolHand(Hand.OFF_HAND);
					}
					FMC.VARS.resetToolWarningTicks();
				}
			}
		}

		if(FMC.OPTIONS.noToolBreaking) {
			ItemStack mainHandItem = FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.MAIN_HAND);
			ItemStack offHandItem = FMC.MC.player.getStackInHand(Hand.OFF_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.OFF_HAND);

			if(mainHandItem != null && mainHandItem.isDamaged()) {
				if(mainHandItem.getItem() instanceof MiningToolItem){
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 2) {
						info.setReturnValue(ActionResult.FAIL);
					}
				}
				else if(mainHandItem.getItem() instanceof FlintAndSteelItem){
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 2) {
						info.setReturnValue(ActionResult.FAIL);
					}
				}
			}

			if(offHandItem != null && offHandItem.isDamaged()) {
				if(offHandItem.getItem() instanceof MiningToolItem) {
					if(offHandItem.getMaxDamage() - offHandItem.getDamage() < 2) {
						info.setReturnValue(ActionResult.FAIL);
					}
				}
				else if(offHandItem.getItem() instanceof FlintAndSteelItem){
					if(offHandItem.getMaxDamage() - offHandItem.getDamage() < 2) {
						info.setReturnValue(ActionResult.FAIL);
					}
				}
			}
		}
	}

	@Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
	public void onAttackBlock(CallbackInfoReturnable<Boolean> info)
	{
		if(FMC.DEBUG) {
			System.out.println("Attack block!");
		}

		if(FMC.OPTIONS.toolWarning) {
			ItemStack mainHandItem = FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.MAIN_HAND);
			ItemStack offHandItem = FMC.MC.player.getStackInHand(Hand.OFF_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.OFF_HAND);

			int mainHandDurability = Integer.MAX_VALUE;
			int offHandDurability = Integer.MAX_VALUE;

			if(mainHandItem != null && mainHandItem.isDamaged()) {
				mainHandDurability = mainHandItem.getMaxDamage() - mainHandItem.getDamage();

				if(MathHelper.floor(mainHandItem.getMaxDamage() * 0.9f) < mainHandItem.getDamage() + 1 && mainHandDurability < 13) {
					FMC.VARS.setToolDurability(mainHandDurability);
					FMC.VARS.setToolHand(Hand.MAIN_HAND);
					FMC.VARS.resetToolWarningTicks();
				}
			}

			if(offHandItem != null && offHandItem.isDamaged()) {
				offHandDurability = offHandItem.getMaxDamage() - offHandItem.getDamage();

				if(MathHelper.floor(offHandItem.getMaxDamage() * 0.9f) < offHandItem.getDamage() + 1 && offHandDurability < 13) {
					if(offHandDurability < mainHandDurability) {
						FMC.VARS.setToolDurability(offHandDurability);
						FMC.VARS.setToolHand(Hand.OFF_HAND);
					}
					FMC.VARS.resetToolWarningTicks();
				}
			}
		}

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
				else if(mainHandItem.getItem() instanceof MiningToolItem){
					// ONE DURA
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 2) {
						info.setReturnValue(false);
					}
				}
				else if(mainHandItem.getItem() instanceof ShearsItem){
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
		if(FMC.DEBUG) {
			System.out.println("Break block!");
		}

		if(FMC.OPTIONS.toolWarning) {
			ItemStack mainHandItem = FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.MAIN_HAND);
			ItemStack offHandItem = FMC.MC.player.getStackInHand(Hand.OFF_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.OFF_HAND);

			int mainHandDurability = Integer.MAX_VALUE;
			int offHandDurability = Integer.MAX_VALUE;

			if(mainHandItem != null && mainHandItem.isDamaged()) {
				mainHandDurability = mainHandItem.getMaxDamage() - mainHandItem.getDamage();

				if(MathHelper.floor(mainHandItem.getMaxDamage() * 0.9f) < mainHandItem.getDamage() + 1 && mainHandDurability < 13) {
					FMC.VARS.setToolDurability(mainHandDurability);
					FMC.VARS.setToolHand(Hand.MAIN_HAND);
					FMC.VARS.resetToolWarningTicks();
				}
			}

			if(offHandItem != null && offHandItem.isDamaged()) {
				offHandDurability = offHandItem.getMaxDamage() - offHandItem.getDamage();

				if(MathHelper.floor(offHandItem.getMaxDamage() * 0.9f) < offHandItem.getDamage() + 1 && offHandDurability < 13) {
					if(offHandDurability < mainHandDurability) {
						FMC.VARS.setToolDurability(offHandDurability);
						FMC.VARS.setToolHand(Hand.OFF_HAND);
					}
					FMC.VARS.resetToolWarningTicks();
				}
			}
		}

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
				else if(mainHandItem.getItem() instanceof MiningToolItem){
					// ONE DURA
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 2) {
						info.setReturnValue(false);
					}
				}
				else if(mainHandItem.getItem() instanceof ShearsItem){
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
		if(FMC.DEBUG) {
			System.out.println("Attack entity!");
		}

		this.syncSelectedSlot();

		if(FMC.OPTIONS.toolWarning) {
			ItemStack mainHandItem = FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.MAIN_HAND);
			ItemStack offHandItem = FMC.MC.player.getStackInHand(Hand.OFF_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.OFF_HAND);

			int mainHandDurability = Integer.MAX_VALUE;
			int offHandDurability = Integer.MAX_VALUE;

			if(mainHandItem != null && mainHandItem.isDamaged()) {
				mainHandDurability = mainHandItem.getMaxDamage() - mainHandItem.getDamage();

				if(MathHelper.floor(mainHandItem.getMaxDamage() * 0.9f) < mainHandItem.getDamage() + 1 && mainHandDurability < 13) {
					FMC.VARS.setToolDurability(mainHandDurability);
					FMC.VARS.setToolHand(Hand.MAIN_HAND);
					FMC.VARS.resetToolWarningTicks();
				}
			}

			if(offHandItem != null && offHandItem.isDamaged()) {
				offHandDurability = offHandItem.getMaxDamage() - offHandItem.getDamage();

				if(MathHelper.floor(offHandItem.getMaxDamage() * 0.9f) < offHandItem.getDamage() + 1 && offHandDurability < 13) {
					if(offHandDurability < mainHandDurability) {
						FMC.VARS.setToolDurability(offHandDurability);
						FMC.VARS.setToolHand(Hand.OFF_HAND);
					}
					FMC.VARS.resetToolWarningTicks();
				}
			}
		}

		if(FMC.OPTIONS.toolWarning) {
			ItemStack mainHandItem = FMC.MC.player.getStackInHand(Hand.MAIN_HAND);

			if(mainHandItem.isDamaged()) {
				if(mainHandItem.getItem() instanceof SwordItem) {
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 2) {
						info.cancel();
					}
				}
				else if(mainHandItem.getItem() instanceof MiningToolItem) {
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 3) {
						info.cancel();
					}
				}
				else if(mainHandItem.getItem() instanceof TridentItem) {
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 2) {
						info.cancel();
					}
				}
			}
		}
	}

	@Inject(method = "interactEntity", at = @At("HEAD"), cancellable = true)
	public void onInteractEntity(CallbackInfoReturnable<ActionResult> info)
	{
		if(FMC.DEBUG) {
			System.out.println("Interact entity!");
		}

		this.syncSelectedSlot();

		if(FMC.OPTIONS.toolWarning) {
			ItemStack mainHandItem = FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.MAIN_HAND);
			ItemStack offHandItem = FMC.MC.player.getStackInHand(Hand.OFF_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.OFF_HAND);

			int mainHandDurability = Integer.MAX_VALUE;
			int offHandDurability = Integer.MAX_VALUE;

			if(mainHandItem != null && mainHandItem.isDamaged()) {
				mainHandDurability = mainHandItem.getMaxDamage() - mainHandItem.getDamage();

				if(MathHelper.floor(mainHandItem.getMaxDamage() * 0.9f) < mainHandItem.getDamage() + 1 && mainHandDurability < 13) {
					FMC.VARS.setToolDurability(mainHandDurability);
					FMC.VARS.setToolHand(Hand.MAIN_HAND);
					FMC.VARS.resetToolWarningTicks();
				}
			}

			if(offHandItem != null && offHandItem.isDamaged()) {
				offHandDurability = offHandItem.getMaxDamage() - offHandItem.getDamage();

				if(MathHelper.floor(offHandItem.getMaxDamage() * 0.9f) < offHandItem.getDamage() + 1 && offHandDurability < 13) {
					if(offHandDurability < mainHandDurability) {
						FMC.VARS.setToolDurability(offHandDurability);
						FMC.VARS.setToolHand(Hand.OFF_HAND);
					}
					FMC.VARS.resetToolWarningTicks();
				}
			}
		}

		if(FMC.OPTIONS.noToolBreaking) {
			ItemStack mainHandItem = FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.MAIN_HAND);
			ItemStack offHandItem = FMC.MC.player.getStackInHand(Hand.OFF_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.OFF_HAND);

			if(mainHandItem != null && mainHandItem.isDamaged()) {
				if(mainHandItem.getItem() instanceof ShearsItem){
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 2) {
						info.setReturnValue(ActionResult.FAIL);
					}
				}
			}

			if(offHandItem != null && offHandItem.isDamaged()) {
				if(offHandItem.getItem() instanceof ShearsItem) {
					if(offHandItem.getMaxDamage() - offHandItem.getDamage() < 2) {
						info.setReturnValue(ActionResult.FAIL);
					}
				}
			}
		}
	}

	@Inject(method = "interactEntityAtLocation", at = @At("HEAD"), cancellable = true)
	public void onInteractEntityAtLocation(CallbackInfoReturnable<ActionResult> info)
	{
		if(FMC.DEBUG) {
			System.out.println("Interact entity at location!");
		}

		this.syncSelectedSlot();

		if(FMC.OPTIONS.toolWarning) {
			ItemStack mainHandItem = FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.MAIN_HAND);
			ItemStack offHandItem = FMC.MC.player.getStackInHand(Hand.OFF_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.OFF_HAND);

			int mainHandDurability = Integer.MAX_VALUE;
			int offHandDurability = Integer.MAX_VALUE;

			if(mainHandItem != null && mainHandItem.isDamaged()) {
				mainHandDurability = mainHandItem.getMaxDamage() - mainHandItem.getDamage();

				if(MathHelper.floor(mainHandItem.getMaxDamage() * 0.9f) < mainHandItem.getDamage() + 1 && mainHandDurability < 13) {
					FMC.VARS.setToolDurability(mainHandDurability);
					FMC.VARS.setToolHand(Hand.MAIN_HAND);
					FMC.VARS.resetToolWarningTicks();
				}
			}

			if(offHandItem != null && offHandItem.isDamaged()) {
				offHandDurability = offHandItem.getMaxDamage() - offHandItem.getDamage();

				if(MathHelper.floor(offHandItem.getMaxDamage() * 0.9f) < offHandItem.getDamage() + 1 && offHandDurability < 13) {
					if(offHandDurability < mainHandDurability) {
						FMC.VARS.setToolDurability(offHandDurability);
						FMC.VARS.setToolHand(Hand.OFF_HAND);
					}
					FMC.VARS.resetToolWarningTicks();
				}
			}
		}

		if(FMC.OPTIONS.noToolBreaking) {
			ItemStack mainHandItem = FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.MAIN_HAND);
			ItemStack offHandItem = FMC.MC.player.getStackInHand(Hand.OFF_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.OFF_HAND);

			if(mainHandItem != null && mainHandItem.isDamaged()) {
				if(mainHandItem.getItem() instanceof ShearsItem){
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 2) {
						info.setReturnValue(ActionResult.FAIL);
					}
				}
			}

			if(offHandItem != null && offHandItem.isDamaged()) {
				if(offHandItem.getItem() instanceof ShearsItem) {
					if(offHandItem.getMaxDamage() - offHandItem.getDamage() < 2) {
						info.setReturnValue(ActionResult.FAIL);
					}
				}
			}
		}
	}
}
