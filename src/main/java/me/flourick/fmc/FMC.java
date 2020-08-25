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

		KeyBinding fullbrightKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding("Fullbright", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "FMC"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while(fullbrightKeybind.wasPressed()) {
				FMC.OPTIONS.fullbright = !FMC.OPTIONS.fullbright;
			}
		});
	}
}
