package me.flourick.fmc.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.flourick.fmc.FMC;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.TridentItem;
import net.minecraft.util.Hand;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin
{
	@Shadow
	private ServerInfo currentServerEntry;

	@Inject(method = "handleBlockBreaking", at = @At("HEAD"), cancellable = true)
	private void onHandleBlockBreaking(boolean bl, CallbackInfo info)
	{
		if(FMC.OPTIONS.noToolBreaking && !FMC.INSTANCE.isToolBreakingOverriden()) {
			ItemStack mainHandItem = FMC.MC.player.getStackInHand(Hand.MAIN_HAND);

			if(mainHandItem.isDamaged()) {
				if(mainHandItem.getItem() instanceof SwordItem) {
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 3) {
						info.cancel();
					}
				}
				else if(mainHandItem.getItem() instanceof TridentItem) {
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 3) {
						info.cancel();
					}
				}
				else if(mainHandItem.getItem() instanceof MiningToolItem){
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 3) {
						info.cancel();
					}
				}
			}
		}

		if(FMC.VARS.freecam) {
			info.cancel();
		}
	}

	@Inject(method = "doAttack", at = @At("HEAD"), cancellable = true)
	private void onDoAttack(CallbackInfo info)
	{
		if(FMC.OPTIONS.noToolBreaking && !FMC.INSTANCE.isToolBreakingOverriden()) {
			ItemStack mainHandItem = FMC.MC.player.getStackInHand(Hand.MAIN_HAND);

			if(mainHandItem.isDamaged()) {
				if(mainHandItem.getItem() instanceof SwordItem) {
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 3) {
						info.cancel();
					}
				}
				else if(mainHandItem.getItem() instanceof TridentItem) {
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 3) {
						info.cancel();
					}
				}
				else if(mainHandItem.getItem() instanceof MiningToolItem){
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 3) {
						info.cancel();
					}
				}
			}
		}

		if(FMC.VARS.freecam) {
			info.cancel();
		}
	}

	@Inject(method = "doItemUse", at = @At("HEAD"), cancellable = true)
	private void onDoItemUse(CallbackInfo info)
	{
		if(FMC.OPTIONS.noToolBreaking && !FMC.INSTANCE.isToolBreakingOverriden()) {
			ItemStack mainHandItem = FMC.MC.player.getStackInHand(Hand.MAIN_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.MAIN_HAND);
			ItemStack offHandItem = FMC.MC.player.getStackInHand(Hand.OFF_HAND).isEmpty() ? null : FMC.MC.player.getStackInHand(Hand.OFF_HAND);

			if(mainHandItem != null && mainHandItem.isDamaged()) {
				if(mainHandItem.getItem() instanceof MiningToolItem){
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 3) {
						info.cancel();
					}
				}
				else if(mainHandItem.getItem() instanceof CrossbowItem) {
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 10) {
						info.cancel();
					}
				}
				else if(mainHandItem.getItem() instanceof TridentItem) {
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 3) {
						info.cancel();
					}
				}
				else if(mainHandItem.getItem() instanceof BowItem) {
					if(mainHandItem.getMaxDamage() - mainHandItem.getDamage() < 3) {
						info.cancel();
					}
				}
			}

			if(offHandItem != null && offHandItem.isDamaged()) {
				if(offHandItem.getItem() instanceof MiningToolItem) {
					if(offHandItem.getMaxDamage() - offHandItem.getDamage() < 3) {
						info.cancel();
					}
				}
				else if(offHandItem.getItem() instanceof CrossbowItem) {
					if(offHandItem.getMaxDamage() - offHandItem.getDamage() < 10) {
						info.cancel();
					}
				}
				else if(offHandItem.getItem() instanceof TridentItem) {
					if(offHandItem.getMaxDamage() - offHandItem.getDamage() < 3) {
						info.cancel();
					}
				}
				else if(offHandItem.getItem() instanceof BowItem) {
					if(offHandItem.getMaxDamage() - offHandItem.getDamage() < 3) {
						info.cancel();
					}
				}
			}
		}

		if(FMC.VARS.freecam) {
			info.cancel();
		}
	}

	@Inject(method = "hasOutline", at = @At("HEAD"), cancellable = true)
	public void onHasOutline(Entity entity, CallbackInfoReturnable<Boolean> info)
	{
		if(FMC.VARS.entityOutline && entity.getType() != EntityType.PLAYER || (FMC.VARS.freecam && entity.equals(FMC.MC.player))) {
			info.setReturnValue(true);
		}
	}

	@Inject(method = "disconnect", at = @At("HEAD"), cancellable = true)
	public void onDisconnect(CallbackInfo info)
	{
		if(this.currentServerEntry != null) {
			FMC.VARS.lastJoinedServer = this.currentServerEntry;
		}
	}
}
