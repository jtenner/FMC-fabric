package me.flourick.fmc.options;

import me.flourick.fmc.FMC;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class SpacerOption extends Option {
	private String text;

	public SpacerOption(String text) {
		super("nope");
		this.text = text;
	}

	@Override
	public AbstractButtonWidget createButton(GameOptions options, int x, int y, int width) {
		return new SpacerButtonWidget(x, y, width, 20, new LiteralText(text), (buttonWidget) -> {
			// nada
		});
	}

	private class SpacerButtonWidget extends ButtonWidget {
		public SpacerButtonWidget(int x, int y, int width, int height, Text message, ButtonWidget.PressAction onPress) {
			super(x, y, width, height, message, onPress);
		}

		@Override
		public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			// nada
		}

		@Override
		public void renderToolTip(MatrixStack matrices, int mouseX, int mouseY) {
			// nada
		}

		@Override
		public boolean isFocused() {
			return false;
		}

		@Override
		public boolean isHovered() {
			return false;
		}

		@Override
		public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			if(this.visible) {
				this.drawCenteredText(matrices, FMC.MC.textRenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, 16777215);
			}
		}

		@Override
		protected void renderBg(MatrixStack matrices, MinecraftClient client, int mouseX, int mouseY) {
			// nada
		}
	}
}