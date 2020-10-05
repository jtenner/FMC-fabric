package me.flourick.fmc;

import java.util.UUID;

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
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

public class FMC implements ModInitializer
{
	public static final boolean DEBUG = false;

	public static FMC INSTANCE;
	public static MinecraftClient MC;
	public static FMCOptions OPTIONS;
	public static final FMCVars VARS = new FMCVars();

	private KeyBinding toolBreakingOverrideKeybind;

	@Override
	public void onInitialize()
	{
		INSTANCE = this;
		MC = MinecraftClient.getInstance();
		OPTIONS = new FMCOptions();

		registerKeys();
	}

	public boolean isToolBreakingOverriden()
	{
		return toolBreakingOverrideKeybind.isPressed();
	}

	private void registerKeys()
	{
		KeyBinding fullbrightKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding("Fullbright", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "FMC"));
		KeyBinding randomPlacementKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding("Random Placement", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "FMC"));
		toolBreakingOverrideKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding("Tool Breaking Override", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_ALT, "FMC"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while(fullbrightKeybind.wasPressed()) {
				FMC.OPTIONS.fullbright = !FMC.OPTIONS.fullbright;
			}

			while(randomPlacementKeybind.wasPressed()) {
				FMC.OPTIONS.randomPlacement = !FMC.OPTIONS.randomPlacement;

				if(FMC.OPTIONS.randomPlacement) {
					FMC.MC.inGameHud.addChatMessage(MessageType.CHAT, new LiteralText("Random Block Placement is now enabled!"), UUID.fromString("00000000-0000-0000-0000-000000000000"));
				}
				else {
					FMC.MC.inGameHud.addChatMessage(MessageType.CHAT, new LiteralText("Random Block Placement is now disabled!"), UUID.fromString("00000000-0000-0000-0000-000000000000"));
				}
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
