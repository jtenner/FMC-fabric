package flour.fmc.options;

import java.util.function.BiConsumer;
import java.util.function.Predicate;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.OptionButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;

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
		return new OptionButtonWidget(x, y, width, 20, this, this.getDisplayString(options), (buttonWidget) -> {
			set(options);
			buttonWidget.setMessage(getDisplayString(options));
		});
	}

	public String getDisplayString(GameOptions options)
	{
		return name + ": " + (this.get(options) ? "ON" : "OFF");
	}
}