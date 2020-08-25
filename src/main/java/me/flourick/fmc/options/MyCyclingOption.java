package me.flourick.fmc.options;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.OptionButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.text.Text;

public class MyCyclingOption extends Option
{
	private final BiConsumer<GameOptions, Integer> setter;
	private final BiFunction<GameOptions, MyCyclingOption, Text> messageProvider;

	public MyCyclingOption(BiConsumer<GameOptions, Integer> setter, BiFunction<GameOptions, MyCyclingOption, Text> messageProvider)
	{
		super("nope");
		this.setter = setter;
		this.messageProvider = messageProvider;
	}

	public void cycle(GameOptions options, int amount)
	{
		setter.accept(options, amount);
	}

	public AbstractButtonWidget createButton(GameOptions options, int x, int y, int width)
	{
		return new OptionButtonWidget(x, y, width, 20, this, getMessage(options), (buttonWidget) -> {
			cycle(options, 1);
			buttonWidget.setMessage(getMessage(options));
		});
	}

	public Text getMessage(GameOptions options)
	{
		return (Text) messageProvider.apply(options, this);
	}
}
