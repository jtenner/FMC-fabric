package me.flourick.fmc.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class FMCVars
{
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

	private boolean fullbright;
	private boolean entityOutline;

	private double deathX;
	private double deathY;
	private double deathZ;
	private String deathWorld;
	private boolean isAfterDeath;

	private int toolWarningTextTicksLeft;
	private int toolDurability;
	private ItemStack mainHandToolItemStack;
	private ItemStack offHandToolItemStack;
	private Hand toolHand;

	public FMCVars()
	{
		this.deathX = 0;
		this.deathY = 0;
		this.deathZ = 0;
		this.deathWorld = "";
		this.isAfterDeath = false;
		
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

	public void setIsAfterDeath(boolean isAfterDeath)
	{
		this.isAfterDeath = isAfterDeath;
	}

	public boolean getIsAfterDeath()
	{
		return this.isAfterDeath;
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

	public int getToolDurability()
	{
		return toolDurability;
	}

	public void setToolDurability(int toolDurability)
	{
		this.toolDurability = toolDurability;
	}

	public Hand getToolHand()
	{
		return toolHand;
	}

	public void setToolHand(Hand toolHand)
	{
		this.toolHand = toolHand;
	}

	public ItemStack getMainHandToolItemStack()
	{
		return mainHandToolItemStack;
	}

	public void setMainHandToolItemStack(ItemStack mainHandToolItemStack)
	{
		this.mainHandToolItemStack = mainHandToolItemStack;
	}

	public ItemStack getOffHandToolItemStack()
	{
		return offHandToolItemStack;
	}

	public void setOffHandToolItemStack(ItemStack offHandToolItemStack)
	{
		this.offHandToolItemStack = offHandToolItemStack;
	}

	public boolean fullbright() {
		return fullbright;
	}

	public void setFullbright(boolean fullbright) {
		this.fullbright = fullbright;
	}

	public boolean entityOutline() {
		return entityOutline;
	}

	public void setEntityOutline(boolean entityOutline) {
		this.entityOutline = entityOutline;
	}
}
