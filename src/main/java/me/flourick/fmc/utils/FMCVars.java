package me.flourick.fmc.utils;

import net.minecraft.client.network.ServerInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class FMCVars
{
	public boolean eating;

	public boolean freecam;

	public double freecamYaw;
	public double freecamPitch;

	public double playerYaw;
	public double playerPitch;

	public double freecamX;
	public double freecamY;
	public double freecamZ;

	public double prevFreecamX;
	public double prevFreecamY;
	public double prevFreecamZ;

	public float freecamForwardSpeed;
	public float freecamSideSpeed;
	public float freecamUpSpeed;

	public int autoreconnectTries;
	public int autoreconnectTicks;
	public ServerInfo lastJoinedServer;

	public boolean fullbright;
	public boolean entityOutline;

	private double deathX;
	private double deathY;
	private double deathZ;
	private String deathWorld;
	public boolean isAfterDeath;

	private int toolWarningTextTicksLeft;
	public int toolDurability;
	public ItemStack mainHandToolItemStack;
	public ItemStack offHandToolItemStack;
	public Hand toolHand;

	public FMCVars()
	{
		this.deathX = 0;
		this.deathY = 0;
		this.deathZ = 0;
		this.deathWorld = "";
		this.isAfterDeath = false;

		this.autoreconnectTicks = 0;
		this.autoreconnectTries = 0;
		
		this.toolWarningTextTicksLeft = 0;
		this.toolDurability = 0;
		this.mainHandToolItemStack = ItemStack.EMPTY;
		this.offHandToolItemStack = ItemStack.EMPTY;
		this.toolHand = Hand.MAIN_HAND;
	}

	public void setLastDeathCoordinates(double x, double y, double z, String world)
	{
		this.deathX = x;
		this.deathY = y;
		this.deathZ = z;
		this.deathWorld = world;
	}
  
	public double getLastDeathX()
	{
		return this.deathX;
	}

	public double getLastDeathY()
	{
		return this.deathY;
	}

	public double getLastDeathZ()
	{
		return this.deathZ;
	}

	public String getLastDeathWorld()
	{
		return this.deathWorld;
	}

	public int getToolWarningTextTicksLeft()
	{
		return toolWarningTextTicksLeft;
	}

	public void resetToolWarningTicks()
	{
		toolWarningTextTicksLeft = 40;
	}

	public void tickToolWarningTicks()
	{
		if(toolWarningTextTicksLeft > 0) {
			toolWarningTextTicksLeft -= 1;
		}
	}
}
