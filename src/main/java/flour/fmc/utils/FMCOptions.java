package flour.fmc.utils;

import java.util.Arrays;
import java.util.Comparator;

import flour.fmc.FMC;
import net.minecraft.client.options.CyclingOption;
import net.minecraft.util.math.MathHelper;

public class FMCOptions
{
	public FMCOptions(ButtonPosition buttonPosition)
	{
		this.buttonPosition = buttonPosition;
	}

	public ButtonPosition buttonPosition;

	public static final CyclingOption BUTTON_POSITION = new CyclingOption(
		"nope",
		(gameOptions, integer) -> {
			FMC.OPTIONS.buttonPosition = ButtonPosition.getOption(FMC.OPTIONS.buttonPosition.getId() + integer);
		},
		(gameOptions, cyclingOption) -> {
			return "Button position: " + FMC.OPTIONS.buttonPosition;
		}
	);

	public enum ButtonPosition {
		RIGHT(0, "Right"), LEFT(1, "Left"), CENTER(2, "Center");

		private static final ButtonPosition[] BUTTON_POSITIONS = (ButtonPosition[])Arrays.stream(values()).sorted(Comparator.comparingInt(ButtonPosition::getId)).toArray((i) -> {
			return new ButtonPosition[i];
		});

		private String position;
		private int id;

		private ButtonPosition(int id, String position) {
			this.position = position;
			this.id = id;
		}
		
		public static ButtonPosition getOption(int id) {
			return BUTTON_POSITIONS[MathHelper.floorMod(id, BUTTON_POSITIONS.length)];
		}
       
        @Override
        public String toString(){
            return position;
        }

		public int getId() {
			return id;
		}
	}
}