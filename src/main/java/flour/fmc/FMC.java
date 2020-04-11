package flour.fmc;

import flour.fmc.utils.OnScreenText;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

public class FMC implements ModInitializer
{
	public static FMC INSTANCE;
	public static MinecraftClient MC;

	// 									  R  G  B  A
	public static int[] crosshairColor = {0, 1, 0, 1};

	private OnScreenText oscText;

	@Override
	public void onInitialize()
	{
		INSTANCE = this;
		MC = MinecraftClient.getInstance();

		oscText = new OnScreenText();
	}

	public OnScreenText getOnScreenText()
	{
		return oscText;
	}
}
