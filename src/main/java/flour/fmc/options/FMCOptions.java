package flour.fmc.options;

import flour.fmc.FMC;
import flour.fmc.utils.Color;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;

import com.google.common.io.Files;

import org.apache.logging.log4j.LogManager;

import net.minecraft.client.options.DoubleOption;
import net.minecraft.util.math.MathHelper;

public class FMCOptions
{
	private File optionsFile;

	public FMCOptions()
	{
		this.optionsFile = new File(FMC.MC.runDirectory, "fmcoptions.txt");

		init();
	}

	public ButtonPosition buttonPosition;
	public Color crosshairColor;
	public double crosshairScale;
	public boolean disableWToSprint;
	public boolean showDeathCoordinates;
	public boolean verticalCoordinates;
	public boolean showHUDInfo;

	//region OPTIONS & ENUMS

	public static final MyCyclingOption SHOW_HUD_INFO = new MyCyclingOption(
		(gameOptions, integer) -> {
			FMC.OPTIONS.showHUDInfo = !FMC.OPTIONS.showHUDInfo;
		},
		(gameOptions, cyclingOption) -> {
			return "HUD Info: " + (FMC.OPTIONS.showHUDInfo ? "Visible" : "Hidden");
		}
	);

	public static final MyCyclingOption VERTICAL_COORDINATES = new MyCyclingOption(
		(gameOptions, integer) -> {
			FMC.OPTIONS.verticalCoordinates = !FMC.OPTIONS.verticalCoordinates;
		},
		(gameOptions, cyclingOption) -> {
			return "HUD Coordinates: " + (FMC.OPTIONS.verticalCoordinates ? "Vertical" : "Horizontal");
		}
	);

	public static final MyBooleanOption SHOW_DEATH_COORDINATES = new MyBooleanOption("Show Death Coordinates",
		(gameOptions) -> {
			return FMC.OPTIONS.showDeathCoordinates;
		},
		(gameOptions, bool) -> {
			FMC.OPTIONS.showDeathCoordinates = bool;
		}
	);

	public static final MyBooleanOption DISABLE_W_TO_SPRINT = new MyBooleanOption("Disable 'W' To Sprint",
		(gameOptions) -> {
			return FMC.OPTIONS.disableWToSprint;
		},
		(gameOptions, bool) -> {
			FMC.OPTIONS.disableWToSprint = bool;
		}
	);

	public static final DoubleOption CROSSHAIR_RED_COMPONENT = new DoubleOption("nope", 0.0D, 255.0D, 1.0F,
		(gameOptions) -> {
			return (double)FMC.OPTIONS.crosshairColor.getRed();
		},
		(gameOptions, red) -> {
			FMC.OPTIONS.crosshairColor = new Color(FMC.OPTIONS.crosshairColor.getAlpha(), red.intValue(), FMC.OPTIONS.crosshairColor.getGreen(), FMC.OPTIONS.crosshairColor.getBlue());
		},
		(gameOptions, doubleOption) -> {
			return "Crosshair Red Component: " + FMC.OPTIONS.crosshairColor.getRed();
		}
	);

	public static final DoubleOption CROSSHAIR_GREEN_COMPONENT = new DoubleOption("nope", 0.0D, 255.0D, 1.0F,
		(gameOptions) -> {
			return (double)FMC.OPTIONS.crosshairColor.getGreen();
		},
		(gameOptions, green) -> {
			FMC.OPTIONS.crosshairColor = new Color(FMC.OPTIONS.crosshairColor.getAlpha(), FMC.OPTIONS.crosshairColor.getRed(), green.intValue(), FMC.OPTIONS.crosshairColor.getBlue());
		},
		(gameOptions, doubleOption) -> {
			return "Crosshair Green Component: " + FMC.OPTIONS.crosshairColor.getGreen();
		}
	);

	public static final DoubleOption CROSSHAIR_BLUE_COMPONENT = new DoubleOption("nope", 0.0D, 255.0D, 1.0F,
		(gameOptions) -> {
			return (double)FMC.OPTIONS.crosshairColor.getBlue();
		},
		(gameOptions, blue) -> {
			FMC.OPTIONS.crosshairColor = new Color(FMC.OPTIONS.crosshairColor.getAlpha(), FMC.OPTIONS.crosshairColor.getRed(), FMC.OPTIONS.crosshairColor.getGreen(), blue.intValue());
		},
		(gameOptions, doubleOption) -> {
			return "Crosshair Blue Component: " + FMC.OPTIONS.crosshairColor.getBlue();
		}
	);

	public static final DoubleOption CROSSHAIR_SCALE = new DoubleOption("nope", 0.0D, 2.0D, 0.01F,
		(gameOptions) -> {
			return FMC.OPTIONS.crosshairScale;
		},
		(gameOptions, scale) -> {
			FMC.OPTIONS.crosshairScale = scale;
		},
		(gameOptions, doubleOption) -> {
			return "Crosshair Scale: " + BigDecimal.valueOf(FMC.OPTIONS.crosshairScale).setScale(2, RoundingMode.HALF_UP);
		}
	);

	public static final MyCyclingOption BUTTON_POSITION = new MyCyclingOption(
		(gameOptions, integer) -> {
			FMC.OPTIONS.buttonPosition = ButtonPosition.getOption(FMC.OPTIONS.buttonPosition.getId() + integer);
		},
		(gameOptions, cyclingOption) -> {
			return "FMC Button Position: " + FMC.OPTIONS.buttonPosition;
		}
	);

	public enum ButtonPosition
	{
		RIGHT(0, "Right"), LEFT(1, "Left"), CENTER(2, "Center");

		private static final ButtonPosition[] BUTTON_POSITIONS = (ButtonPosition[]) Arrays.stream(values()).sorted(Comparator.comparingInt(ButtonPosition::getId)).toArray((i) -> {
			return new ButtonPosition[i];
		});

		private String position;
		private int id;

		private ButtonPosition(int id, String position)
		{
			this.position = position;
			this.id = id;
		}

		public static ButtonPosition getOption(int id)
		{
			return BUTTON_POSITIONS[MathHelper.floorMod(id, BUTTON_POSITIONS.length)];
		}

		@Override
		public String toString()
		{
			return position;
		}

		public int getId()
		{
			return id;
		}

		public static ButtonPosition match(String m)
		{
			switch(m) {
				case "Right":
					return ButtonPosition.RIGHT;

				case "Left":
					return ButtonPosition.LEFT;

				case "Center":
					return ButtonPosition.CENTER;

				default:
					return null;
			}
		}
	}

	//endregion

	public void write()
	{
		try(PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(optionsFile), StandardCharsets.UTF_8));) {
			printWriter.println("buttonPosition:" + this.buttonPosition);
			printWriter.println("crosshairScale:" + BigDecimal.valueOf(this.crosshairScale).setScale(2, RoundingMode.HALF_UP));
			printWriter.println("crosshairColor:" + this.crosshairColor.getPacked());
			printWriter.println("disableWToSprint:" + this.disableWToSprint);
			printWriter.println("showDeathCoordinates:" + this.showDeathCoordinates);
			printWriter.println("verticalCoordinates:" + this.verticalCoordinates);
			printWriter.println("showHUDInfo:" + this.showHUDInfo);
		}
		catch(FileNotFoundException e) {
			LogManager.getLogger().error("Failed to load FMCOptions", e);
		}
	}

	private void init()
	{
		loadDefaults();

		if(!optionsFile.exists()) {
			write();
			return;
		}

		read();
	}

	private void read()
	{
		try(BufferedReader bufferedReader = Files.newReader(this.optionsFile, StandardCharsets.UTF_8)) {
			bufferedReader.lines().forEach((line) -> {
				String[] v = line.split(":");
				String key = v[0];
				String value = v[1];

				switch(key) {
					case "buttonPosition":
						ButtonPosition bPos = ButtonPosition.match(value);

						if(bPos != null) {
							this.buttonPosition = bPos;
						}
						else {
							LogManager.getLogger().warn("Skipping bad option (" + value + ")" + " for " + key);
						}

						break;
					
					case "crosshairScale":
						try {
							this.crosshairScale = MathHelper.clamp(Double.parseDouble(value), 0.0D, 2.0D);
						}
						catch(NumberFormatException e) {
							LogManager.getLogger().warn("Skipping bad option (" + value + ")" + " for " + key);
						}

						break;

					case "crosshairColor":
						try {
							this.crosshairColor = new Color(Integer.parseInt(value));
						}
						catch(NumberFormatException e) {
							LogManager.getLogger().warn("Skipping bad option (" + value + ")" + " for " + key);
						}
						
						break;

					case "disableWToSprint":
						this.disableWToSprint = "true".equalsIgnoreCase(value);

						break;
					
					case "showDeathCoordinates":
						this.showDeathCoordinates = "true".equalsIgnoreCase(value);

						break;

					case "verticalCoordinates":
						this.verticalCoordinates = "true".equalsIgnoreCase(value);

						break;

					case "showHUDInfo":
						this.showHUDInfo = "true".equalsIgnoreCase(value);

						break;
				}
			});
		}
		catch(IOException e) {
			LogManager.getLogger().error("Failed to write to FMCOptions", e);
		}
	}

	private void loadDefaults()
	{
		this.buttonPosition = ButtonPosition.RIGHT;
		this.crosshairScale = 1.0D;
		this.crosshairColor = new Color(255, 255, 255);
		this.disableWToSprint = true;
		this.showDeathCoordinates = true;
		this.verticalCoordinates = false;
		this.showHUDInfo = true;
	}
}