package flour.fmc.utils;

import net.minecraft.util.math.MathHelper;

public class Color
{
	public static final Color WHITE = new Color(255, 255, 255);
	public static final Color GRAY = new Color(127, 127, 127);
	public static final Color BLACK = new Color(0, 0, 0);

	public static final Color RED = new Color(255, 0, 0);
	public static final Color GREEN = new Color(0, 255, 0);
	public static final Color BLUE = new Color(0, 0, 255);

	private final int alpha, red, green, blue;
	private final int packed;

	public Color(int red, int green, int blue)
	{
		this(255, red, green, blue);
	}

	public Color(int alpha, int red, int green, int blue)
	{
		this.alpha = MathHelper.clamp(alpha, 0, 255);
		this.red = MathHelper.clamp(red, 0, 255);
		this.green = MathHelper.clamp(green, 0, 255);
		this.blue = MathHelper.clamp(blue, 0, 255);
		this.packed = (this.alpha << 24) | (this.red << 16) | (this.green << 8) | this.blue;
	}

	public Color(int packed)
	{
		this.alpha = packed >> 24;
		this.red = (packed >> 16) & 0xff;
		this.green = (packed >> 8) & 0xff;
		this.blue = packed & 0xff;
		this.packed = packed;
	}

	public float getNormAlpha()
	{
		return (float)alpha / 255;
	}
	public float getNormRed()
	{
		return (float)red / 255;
	}
	public float getNormGreen()
	{
		return (float)green / 255;
	}
	public float getNormBlue()
	{
		return (float)blue / 255;
	}

	public int getAlpha()
	{
		return alpha;
	}
	public int getRed()
	{
		return red;
	}
	public int getGreen()
	{
		return green;
	}
	public int getBlue()
	{
		return blue;
	}

	public int getPacked()
	{
		return packed;
	}
}