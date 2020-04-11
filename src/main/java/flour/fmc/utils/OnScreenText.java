package flour.fmc.utils;

import flour.fmc.FMC;

public class OnScreenText
{
	public OnScreenText()
	{

	}

	public void drawCoordinatesTextLower()
	{
		String curLocText = String.format("X: %.01f Z: %.01f, Y: %.01f", FMC.MC.player.getX(), FMC.MC.player.getZ(), FMC.MC.player.getY());
		FMC.MC.textRenderer.drawWithShadow(curLocText, 2, FMC.MC.getWindow().getScaledHeight() - FMC.MC.textRenderer.fontHeight, Color.WHITE.getPacked());
	}

	public void drawCoordinatesTextUpper()
	{
		String curLocText = String.format("X: %.01f Z: %.01f, Y: %.01f", FMC.MC.player.getX(), FMC.MC.player.getZ(), FMC.MC.player.getY());
		FMC.MC.textRenderer.drawWithShadow(curLocText, 2, 1, Color.WHITE.getPacked());
	}

	public void drawYPRTextLower()
	{
		String direction;
		float yaw = (FMC.MC.player.yaw % 360) < 0 ? (FMC.MC.player.yaw % 360) + 360 : FMC.MC.player.yaw % 360;

		if(yaw >= 315 || yaw < 45) {
			direction = "South";
		}
		else if(yaw < 135) {
			direction = "West";
		}
		else if(yaw < 225) {
			direction = "North";
		}
		else {
			direction = "East";
		}

		String curYPRText = String.format("P: %.02f (%s)", FMC.MC.player.pitch, direction);
		FMC.MC.textRenderer.drawWithShadow(curYPRText, FMC.MC.getWindow().getScaledWidth() - FMC.MC.textRenderer.getStringWidth(curYPRText) - 1, FMC.MC.getWindow().getScaledHeight() - FMC.MC.textRenderer.fontHeight, Color.WHITE.getPacked());
	}

	public void drawYPRTextUpper()
	{
		String direction;
		float yaw = (FMC.MC.player.yaw % 360) < 0 ? (FMC.MC.player.yaw % 360) + 360 : FMC.MC.player.yaw % 360;

		if(yaw >= 315 || yaw < 45) {
			direction = "South";
		}
		else if(yaw < 135) {
			direction = "West";
		}
		else if(yaw < 225) {
			direction = "North";
		}
		else {
			direction = "East";
		}

		String curYPRText = String.format("P: %.02f (%s)", FMC.MC.player.pitch, direction);
		FMC.MC.textRenderer.drawWithShadow(curYPRText, FMC.MC.getWindow().getScaledWidth() - FMC.MC.textRenderer.getStringWidth(curYPRText) - 1, 1, Color.WHITE.getPacked());
	}
}