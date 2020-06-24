package flour.fmc.options;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.OptionButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class MyBooleanOption extends Option
{
	private final String name;

	private final Predicate<GameOptions> getter;
	private final BiConsumer<GameOptions, Boolean> setter;

	public MyBooleanOption(String name, Predicate<GameOptions> getter, BiConsumer<GameOptions, Boolean> setter)
	{
		super("nope");
		this.name = name;
		this.getter = getter;
		this.setter = setter;
	}

	public void set(GameOptions options)
	{
		set(options, !get(options));
	}

	private void set(GameOptions options, boolean value)
	{
		setter.accept(options, value);
	}

	public boolean get(GameOptions options)
	{
		return getter.test(options);
	}

	public AbstractButtonWidget createButton(GameOptions options, int x, int y, int width)
	{
		return new OptionButtonWidget(x, y, width, 20, this, getDisplayString(options), (buttonWidget) -> {
			set(options);
			buttonWidget.setMessage(getDisplayString(options));
		});
	}

	public Text getDisplayString(GameOptions options)
	{
		return new LiteralText(name + ": ").append(ScreenTexts.getToggleText(this.get(options)));
	}
}