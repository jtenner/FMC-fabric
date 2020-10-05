package me.flourick.fmc.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.flourick.fmc.FMC;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin
{
	@Inject(method = "interactBlock", at = @At("HEAD"))
	private void onInteractBlock(CallbackInfoReturnable<ActionResult> info)
	{
		if(FMC.OPTIONS.randomPlacement) {
			PlayerInventory inventory  = FMC.MC.player.inventory;

			// need to hold a block first for it to pick a block
			if(inventory.getStack(inventory.selectedSlot).getItem() instanceof BlockItem || inventory.getStack(inventory.selectedSlot).getItem() == Items.AIR) {
				List<Integer> blockIndexes = new ArrayList<>();

				for(int i = 0; i < 9; i++) {
					if(inventory.getStack(i).getItem() instanceof BlockItem) {
						blockIndexes.add(i);
					}
				}

				if(blockIndexes.size() > 0) {
					inventory.selectedSlot = blockIndexes.get(new Random().nextInt(blockIndexes.size()));
				}
			}
		}
	}
}
