package flour.fmc.utils;

import flour.fmc.FMC;
import flour.fmc.options.FMCOptions;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.options.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class FMCSettingsScreen extends GameOptionsScreen
{
	private ButtonListWidget list;

	public FMCSettingsScreen(Screen parent, GameOptions gameOptions)
	{
		super(parent, gameOptions, new LiteralText("FMC Options"));
	}

	protected void init()
	{
		this.list = new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
		this.list.addAll(new Option[] {FMCOptions.BUTTON_POSITION, FMCOptions.CROSSHAIR_SCALE});
		this.list.addSingleOptionEntry(FMCOptions.CROSSHAIR_RED_COMPONENT);
		this.list.addSingleOptionEntry(FMCOptions.CROSSHAIR_GREEN_COMPONENT);
		this.list.addSingleOptionEntry(FMCOptions.CROSSHAIR_BLUE_COMPONENT);
		this.list.addAll(new Option[] {FMCOptions.DISABLE_W_TO_SPRINT, FMCOptions.SHOW_DEATH_COORDINATES, FMCOptions.VERTICAL_COORDINATES, FMCOptions.SHOW_HUD_INFO, FMCOptions.NO_TOOL_BREAKING});
		this.children.add(this.list);
		this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, ScreenTexts.DONE, (buttonWidget) -> {
			FMC.OPTIONS.write();
			this.client.openScreen(this.parent);
		}));
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta)
	{
		this.renderBackground(matrixStack);
		this.list.render(matrixStack, mouseX, mouseY, delta);
		this.drawCenteredString(matrixStack, this.textRenderer, this.title.asString(), this.width / 2, 8, 16777215);

		super.render(matrixStack, mouseX, mouseY, delta);
	}

	@Override
	public void removed()
	{
		FMC.OPTIONS.write();
	}
}
