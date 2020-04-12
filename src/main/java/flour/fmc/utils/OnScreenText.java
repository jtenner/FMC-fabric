package flour.fmc.utils;

import flour.fmc.FMC;
import net.minecraft.world.LightType;

public class OnScreenText
{
	public OnScreenText()
	{

	}

	public void drawCoordinatesTextLower()
	{
		final String curLocText = String.format("X: %.01f Z: %.01f, Y: %.01f", FMC.MC.player.getX(), FMC.MC.player.getZ(), FMC.MC.player.getY());
		FMC.MC.textRenderer.drawWithShadow(curLocText, 2, FMC.MC.getWindow().getScaledHeight() - FMC.MC.textRenderer.fontHeight, Color.WHITE.getPacked());
	}

	public void drawCoordinatesTextUpper()
	{
		final String curLocText = String.format("X: %.01f Z: %.01f, Y: %.01f", FMC.MC.player.getX(), FMC.MC.player.getZ(), FMC.MC.player.getY());
		FMC.MC.textRenderer.drawWithShadow(curLocText, 2, 1, Color.WHITE.getPacked());
	}

	public void drawPFTextLower()
	{
		String direction = FMC.MC.getCameraEntity().getHorizontalFacing().asString();
		direction = direction.substring(0, 1).toUpperCase() + direction.substring(1);

		final String PFText = String.format("P: %.02f (%s)", FMC.MC.player.pitch, direction);
		FMC.MC.textRenderer.drawWithShadow(PFText, FMC.MC.getWindow().getScaledWidth() - FMC.MC.textRenderer.getStringWidth(PFText) - 1, FMC.MC.getWindow().getScaledHeight() - FMC.MC.textRenderer.fontHeight, Color.WHITE.getPacked());
	}

	public void drawPFTextUpper()
	{
		String direction = FMC.MC.getCameraEntity().getHorizontalFacing().asString();
		direction = direction.substring(0, 1).toUpperCase() + direction.substring(1);

		final String PFText = String.format("P: %.02f (%s)", FMC.MC.player.pitch, direction);
		FMC.MC.textRenderer.drawWithShadow(PFText, FMC.MC.getWindow().getScaledWidth() - FMC.MC.textRenderer.getStringWidth(PFText) - 1, 1, Color.WHITE.getPacked());
	}

	public void drawLightLevelTextLower()
	{
		int blockLightLevel = FMC.MC.world.getChunkManager().getLightingProvider().get(LightType.BLOCK).getLightLevel(FMC.MC.getCameraEntity().getBlockPos());

		final String curYPRText = String.format("BL: %d", blockLightLevel);
		FMC.MC.textRenderer.drawWithShadow(curYPRText, FMC.MC.getWindow().getScaledWidth() - FMC.MC.textRenderer.getStringWidth(curYPRText) - 1, FMC.MC.getWindow().getScaledHeight() - 2*FMC.MC.textRenderer.fontHeight - 1, Color.WHITE.getPacked());
	}

	public void drawLightLevelTextUpper()
	{
		int blockLightLevel = FMC.MC.world.getChunkManager().getLightingProvider().get(LightType.BLOCK).getLightLevel(FMC.MC.getCameraEntity().getBlockPos());

		final String curYPRText = String.format("BL: %d", blockLightLevel);
		FMC.MC.textRenderer.drawWithShadow(curYPRText, FMC.MC.getWindow().getScaledWidth() - FMC.MC.textRenderer.getStringWidth(curYPRText) - 1, FMC.MC.textRenderer.fontHeight + 2, Color.WHITE.getPacked());
	}
}