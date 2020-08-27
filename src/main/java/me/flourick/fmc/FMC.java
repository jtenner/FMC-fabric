package me.flourick.fmc;

import org.lwjgl.glfw.GLFW;

import me.flourick.fmc.options.FMCOptions;
import me.flourick.fmc.utils.FMCVars;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

public class FMC implements ModInitializer
{
	public static final boolean DEBUG = false;

	public static FMC INSTANCE;
	public static MinecraftClient MC;
	public static FMCOptions OPTIONS;
	public static final FMCVars VARS = new FMCVars();

	@Override
	public void onInitialize()
	{
		INSTANCE = this;
		MC = MinecraftClient.getInstance();
		OPTIONS = new FMCOptions();

		registerKeys();
	}

	private void registerKeys()
	{
		KeyBinding fullbrightKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding("Fullbright", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "FMC"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while(fullbrightKeybind.wasPressed()) {
				FMC.OPTIONS.fullbright = !FMC.OPTIONS.fullbright;
			}
		});

		ClientTickEvents.END_WORLD_TICK.register(client -> {
			if(FMC.OPTIONS.toolWarning) {
				ItemStack mainHandItem = FMC.MC.player.getStackInHand(Hand.MAIN_HAND);
				ItemStack offHandItem = FMC.MC.player.getStackInHand(Hand.OFF_HAND);
	
				int mainHandDurability = mainHandItem.getMaxDamage() - mainHandItem.getDamage();;
				int offHandDurability = offHandItem.getMaxDamage() - offHandItem.getDamage();

				if(mainHandItem.isDamaged() && mainHandItem != FMC.VARS.getMainHandToolItemStack()) {
					if(MathHelper.floor(mainHandItem.getMaxDamage() * 0.9f) < mainHandItem.getDamage() + 1 && mainHandDurability < 13) {
						FMC.VARS.setToolDurability(mainHandDurability);
						FMC.VARS.setToolHand(Hand.MAIN_HAND);
						FMC.VARS.resetToolWarningTicks();
					}
				}

				if(offHandItem.isDamaged() && offHandItem != FMC.VARS.getOffHandToolItemStack()) {
					if(MathHelper.floor(offHandItem.getMaxDamage() * 0.9f) < offHandItem.getDamage() + 1 && offHandDurability < 13) {
						if(mainHandDurability == 0 || offHandDurability < mainHandDurability) {
							FMC.VARS.setToolDurability(offHandDurability);
							FMC.VARS.setToolHand(Hand.OFF_HAND);
							FMC.VARS.resetToolWarningTicks();
						}
					}
				}

				FMC.VARS.setMainHandToolItemStack(mainHandItem);
				FMC.VARS.setOffHandToolItemStack(offHandItem);
			}
		});
	}
}
