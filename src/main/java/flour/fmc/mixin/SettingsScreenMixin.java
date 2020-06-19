package flour.fmc.mixin;

import flour.fmc.FMC;

import flour.fmc.utils.FMCSettingsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.text.TranslatableText;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SettingsScreen.class)
public class SettingsScreenMixin extends Screen
{
	@Shadow
	private final Screen parent;

	@Shadow
	private final GameOptions settings;

	// IGNORED
	public SettingsScreenMixin(Screen parent, GameOptions gameOptions)
	{
		super(new TranslatableText("options.title", new Object[0]));
		this.parent = parent;
		this.settings = gameOptions;
	}

	@Inject(method = "init", at = @At("RETURN"))
	private void initNewButtons(CallbackInfo info)
	{
		int x, y, l;

		switch(FMC.OPTIONS.getButtonPosition()) {
			case CENTER:
				x = this.width / 2 - 155;
				y = this.height / 6 + 24 - 6;
				l = 310;
				break;

			case LEFT:
				x = this.width / 2 - 155;
				y = this.height / 6 + 24 - 6;
				l = 150;
				break;

			default: // RIGHT
				x = this.width / 2 + 5;
				y = this.height / 6 + 24 - 6;
				l = 150;
				break;
		}

		this.addButton(new ButtonWidget(x, y, l, 20, "FMC...", (buttonWidget) -> {
			this.minecraft.openScreen(new FMCSettingsScreen(this, this.settings));
		}));
	}
}
