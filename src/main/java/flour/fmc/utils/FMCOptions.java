package flour.fmc.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;

import com.google.common.io.Files;

import org.apache.logging.log4j.LogManager;

import flour.fmc.FMC;
import net.minecraft.client.options.CyclingOption;
import net.minecraft.util.math.MathHelper;

public class FMCOptions
{
	private File optionsFile;

	public FMCOptions()
	{
		this.optionsFile = new File(optionsFile, "fmcoptions.txt");

		init();
	}

	private ButtonPosition buttonPosition;

	public ButtonPosition getButtonPosition()
	{
		return buttonPosition;
	}

	public void setButtonPosition(ButtonPosition buttonPosition)
	{
		this.buttonPosition = buttonPosition;
		write();
	}

	//region OPTIONS & ENUMS

	public static final CyclingOption BUTTON_POSITION = new CyclingOption("nope", (gameOptions, integer) -> {
		FMC.OPTIONS.setButtonPosition(ButtonPosition.getOption(FMC.OPTIONS.buttonPosition.getId() + integer));
	}, (gameOptions, cyclingOption) -> {
		return "FMC Button Position: " + FMC.OPTIONS.buttonPosition;
	});

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

	private void init()
	{
		loadDefaults();

		if(!optionsFile.exists()) {
			write();
			return;
		}

		// read
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

						break;
				}
			});
		}
		catch(IOException e) {
			LogManager.getLogger().error("Failed to write to FMCOptions", e);
		}
	}

	private void write()
	{
		try(PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(optionsFile), StandardCharsets.UTF_8));) {
			printWriter.println("buttonPosition:" + this.buttonPosition);
		}
		catch(FileNotFoundException e) {
			LogManager.getLogger().error("Failed to load FMCOptions", e);
		}
	}

	private void loadDefaults()
	{
		this.buttonPosition = ButtonPosition.RIGHT;
	}
}