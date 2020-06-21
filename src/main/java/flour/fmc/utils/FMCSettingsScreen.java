package flour.fmc.utils;

import flour.fmc.FMC;
import flour.fmc.options.FMCOptions;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.client.resource.language.I18n;
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
		this.list = new ButtonListWidget(this.minecraft, this.width, this.height, 32, this.height - 32, 25);
		this.list.addAll(new Option[] {FMCOptions.BUTTON_POSITION, FMCOptions.CROSSHAIR_SCALE});
		this.list.addSingleOptionEntry(FMCOptions.CROSSHAIR_RED_COMPONENT);
		this.list.addSingleOptionEntry(FMCOptions.CROSSHAIR_GREEN_COMPONENT);
		this.list.addSingleOptionEntry(FMCOptions.CROSSHAIR_BLUE_COMPONENT);
		this.list.addAll(new Option[] {FMCOptions.DISABLE_W_TO_SPRINT, FMCOptions.SHOW_DEATH_COORDINATES, FMCOptions.VERTICAL_COORDINATES, FMCOptions.SHOW_HUD_INFO});
		this.children.add(this.list);
		this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, I18n.translate("gui.done"), (buttonWidget) -> {
			FMC.OPTIONS.write();
			this.minecraft.openScreen(this.parent);
		}));
	}

	public void render(int mouseX, int mouseY, float delta)
	{
		this.renderBackground();
		this.list.render(mouseX, mouseY, delta);
		this.drawCenteredString(this.font, this.title.asFormattedString(), this.width / 2, 8, 16777215);

		super.render(mouseX, mouseY, delta);
	}

	public void removed()
	{
		FMC.OPTIONS.write();
	}
}
