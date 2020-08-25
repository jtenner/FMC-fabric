package me.flourick.fmc;

import me.flourick.fmc.options.FMCOptions;
import me.flourick.fmc.utils.FMCVars;
import net.fabricmc.api.ModInitializer;

import net.minecraft.client.MinecraftClient;

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
	}
}
