package me.flourick.fmc.utils;

import me.flourick.fmc.FMC;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;

public class OnScreenText
{
	public static void drawCoordinatesTextLower(MatrixStack matrixStack)
	{
		if(FMC.OPTIONS.verticalCoordinates) {
			final String X = String.format("X: %.01f", FMC.VARS.freecam ? FMC.VARS.freecamX : FMC.MC.player.getX());	
			final String Y = String.format("Y: %.01f", FMC.VARS.freecam ? FMC.VARS.freecamY : FMC.MC.player.getY());
			final String Z = String.format("Z: %.01f", FMC.VARS.freecam ? FMC.VARS.freecamZ : FMC.MC.player.getZ());

			FMC.MC.textRenderer.drawWithShadow(matrixStack, X, 2, FMC.MC.getWindow().getScaledHeight() - 3*FMC.MC.textRenderer.fontHeight - 2, Color.WHITE.getPacked());
			FMC.MC.textRenderer.drawWithShadow(matrixStack, Y, 2, FMC.MC.getWindow().getScaledHeight() - 2*FMC.MC.textRenderer.fontHeight - 1, Color.WHITE.getPacked());
			FMC.MC.textRenderer.drawWithShadow(matrixStack, Z, 2, FMC.MC.getWindow().getScaledHeight() - FMC.MC.textRenderer.fontHeight, Color.WHITE.getPacked());
		}
		else {
			final String curLocText = String.format("XYZ: %.01f %.01f %.01f", FMC.MC.player.getX(), FMC.MC.player.getY(), FMC.MC.player.getZ());
			FMC.MC.textRenderer.drawWithShadow(matrixStack, curLocText, 2, FMC.MC.getWindow().getScaledHeight() - FMC.MC.textRenderer.fontHeight, Color.WHITE.getPacked());
		}
	}

	public static void drawCoordinatesTextUpper(MatrixStack matrixStack)
	{
		if(FMC.OPTIONS.verticalCoordinates) {
			final String X = String.format("X: %.01f", FMC.VARS.freecam ? FMC.VARS.freecamX : FMC.MC.player.getX());		
			final String Y = String.format("Y: %.01f", FMC.VARS.freecam ? FMC.VARS.freecamY : FMC.MC.player.getY());
			final String Z = String.format("Z: %.01f", FMC.VARS.freecam ? FMC.VARS.freecamZ : FMC.MC.player.getZ());

			FMC.MC.textRenderer.drawWithShadow(matrixStack, X, 2, 2, Color.WHITE.getPacked());
			FMC.MC.textRenderer.drawWithShadow(matrixStack, Y, 2, 3 + FMC.MC.textRenderer.fontHeight, Color.WHITE.getPacked());
			FMC.MC.textRenderer.drawWithShadow(matrixStack, Z, 2, 4 + 2*FMC.MC.textRenderer.fontHeight, Color.WHITE.getPacked());
		}
		else {
			final String curLocText = String.format("XYZ: %.01f %.01f %.01f", FMC.MC.player.getX(), FMC.MC.player.getY(), FMC.MC.player.getZ());
			FMC.MC.textRenderer.drawWithShadow(matrixStack, curLocText, 2, 2, Color.WHITE.getPacked());
		}
	}

	public static void drawPFTextLower(MatrixStack matrixStack)
	{
		String direction = FMC.MC.getCameraEntity().getHorizontalFacing().asString();
		direction = direction.substring(0, 1).toUpperCase() + direction.substring(1);

		final String PFText = String.format("P: %.02f (%s)", FMC.MC.gameRenderer.getCamera().getPitch(), direction);
		FMC.MC.textRenderer.drawWithShadow(matrixStack, PFText, FMC.MC.getWindow().getScaledWidth() - FMC.MC.textRenderer.getWidth(PFText) - 1, FMC.MC.getWindow().getScaledHeight() - FMC.MC.textRenderer.fontHeight, Color.WHITE.getPacked());
	}

	public static void drawPFTextUpper(MatrixStack matrixStack)
	{
		String direction = FMC.MC.getCameraEntity().getHorizontalFacing().asString();
		direction = direction.substring(0, 1).toUpperCase() + direction.substring(1);

		final String PFText = String.format("P: %.02f (%s)", FMC.MC.gameRenderer.getCamera().getPitch(), direction);
		FMC.MC.textRenderer.drawWithShadow(matrixStack, PFText, FMC.MC.getWindow().getScaledWidth() - FMC.MC.textRenderer.getWidth(PFText) - 1, 2, Color.WHITE.getPacked());
	}

	public static void drawLightLevelTextLower(MatrixStack matrixStack)
	{
		int blockLightLevel = FMC.MC.world.getChunkManager().getLightingProvider().get(LightType.BLOCK).getLightLevel(FMC.MC.getCameraEntity().getBlockPos());

		final String curYPRText = String.format("BL: %d", blockLightLevel);
		FMC.MC.textRenderer.drawWithShadow(matrixStack, curYPRText, FMC.MC.getWindow().getScaledWidth() - FMC.MC.textRenderer.getWidth(curYPRText) - 1, FMC.MC.getWindow().getScaledHeight() - 2*FMC.MC.textRenderer.fontHeight - 1, Color.WHITE.getPacked());
	}

	public static void drawLightLevelTextUpper(MatrixStack matrixStack)
	{
		int blockLightLevel = FMC.MC.world.getChunkManager().getLightingProvider().get(LightType.BLOCK).getLightLevel(FMC.MC.getCameraEntity().getBlockPos());

		final String curYPRText = String.format("BL: %d", blockLightLevel);
		FMC.MC.textRenderer.drawWithShadow(matrixStack, curYPRText, FMC.MC.getWindow().getScaledWidth() - FMC.MC.textRenderer.getWidth(curYPRText) - 1, FMC.MC.textRenderer.fontHeight + 3, Color.WHITE.getPacked());
	}

	public static void drawToolWarningText(MatrixStack matrixStack)
	{
		// last half a second fade-out
		int alpha = MathHelper.clamp(MathHelper.ceil(25.5f * FMC.VARS.getToolWarningTextTicksLeft()), 0, 255);

		int y;
		if(FMC.OPTIONS.upperToolBreakingWarning) {
			y = (int)((-(FMC.MC.getWindow().getScaledHeight() / 2 * 1/FMC.OPTIONS.toolBreakingWarningScale)) + (2/FMC.OPTIONS.toolBreakingWarningScale));
		}
		else {
			y = (int)(((FMC.MC.getWindow().getScaledHeight() / 2 * 1/FMC.OPTIONS.toolBreakingWarningScale)) - (FMC.MC.textRenderer.fontHeight + 60/FMC.OPTIONS.toolBreakingWarningScale));
		}

		final String ToolWarningText = (FMC.VARS.getToolHand().equals(Hand.MAIN_HAND) ? "Main hand" : "Offhand") + " tool has " + FMC.VARS.getToolDurability() + " durability left!";
		FMC.MC.textRenderer.drawWithShadow(matrixStack, ToolWarningText, (float) -(FMC.MC.textRenderer.getWidth(ToolWarningText) / 2), y, new Color(alpha, 255, 0, 0).getPacked());
	}
}