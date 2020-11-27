package me.flourick.fmc;

import java.util.UUID;

import org.lwjgl.glfw.GLFW;

import me.flourick.fmc.options.FMCOptions;
import me.flourick.fmc.utils.FMCVars;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.MathHelper;

public class FMC implements ModInitializer
{
	public static final boolean DEBUG = false;

	public static FMC INSTANCE;
	public static MinecraftClient MC;
	public static FMCOptions OPTIONS;
	public static final FMCVars VARS = new FMCVars();

	private KeyBinding toolBreakingOverrideKeybind;

	@Override
	public void onInitialize()
	{
		INSTANCE = this;
		MC = MinecraftClient.getInstance();
		OPTIONS = new FMCOptions();

		registerKeys();
	}

	public boolean isToolBreakingOverriden()
	{
		return toolBreakingOverrideKeybind.isPressed();
	}

	private void registerKeys()
	{
		KeyBinding fullbrightKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding("Fullbright", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "FMC"));
		KeyBinding freecamKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding("Freecam", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "FMC"));
		KeyBinding randomPlacementKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding("Random Placement", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "FMC"));
		KeyBinding entityOutlineKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding("Entity Outline", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "FMC"));
		KeyBinding autoAttackKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding("Auto Attack", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "FMC"));
		toolBreakingOverrideKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding("Tool Breaking Override", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_ALT, "FMC"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if(FMC.MC.player == null && FMC.VARS.freecam) {
				FMC.VARS.freecam = false;
			}

			while(fullbrightKeybind.wasPressed()) {
				FMC.VARS.fullbright = !FMC.VARS.fullbright;
			}

			while(entityOutlineKeybind.wasPressed()) {
				FMC.VARS.entityOutline = !FMC.VARS.entityOutline;
			}

			while(autoAttackKeybind.wasPressed()) {
				FMC.OPTIONS.triggerBot = !FMC.OPTIONS.triggerBot;
			}

			while(freecamKeybind.wasPressed()) {
				FMC.VARS.freecam = !FMC.VARS.freecam;

				if(FMC.VARS.freecam && FMC.MC.player != null) {
					FMC.VARS.freecamPitch = FMC.MC.player.pitch;
					FMC.VARS.freecamYaw = FMC.MC.player.yaw;

					FMC.VARS.playerPitch = FMC.MC.player.pitch;
					FMC.VARS.playerYaw = FMC.MC.player.yaw;

					FMC.VARS.freecamX = FMC.VARS.prevFreecamX = FMC.MC.gameRenderer.getCamera().getPos().getX();
					FMC.VARS.freecamY = FMC.VARS.prevFreecamY = FMC.MC.gameRenderer.getCamera().getPos().getY();
					FMC.VARS.freecamZ = FMC.VARS.prevFreecamZ = FMC.MC.gameRenderer.getCamera().getPos().getZ();
				}
				else {
					FMC.MC.player.pitch = (float) FMC.VARS.playerPitch;
					FMC.MC.player.yaw = (float) FMC.VARS.playerYaw;

					FMC.VARS.freecamForwardSpeed = 0.0f;
					FMC.VARS.freecamUpSpeed = 0.0f;
					FMC.VARS.freecamSideSpeed = 0.0f;
				}
			}

			while(randomPlacementKeybind.wasPressed()) {
				FMC.OPTIONS.randomPlacement = !FMC.OPTIONS.randomPlacement;

				if(FMC.OPTIONS.randomPlacement) {
					FMC.MC.inGameHud.addChatMessage(MessageType.CHAT, new LiteralText("Random Block Placement is now enabled!"), UUID.fromString("00000000-0000-0000-0000-000000000000"));
				}
				else {
					FMC.MC.inGameHud.addChatMessage(MessageType.CHAT, new LiteralText("Random Block Placement is now disabled!"), UUID.fromString("00000000-0000-0000-0000-000000000000"));
				}
			}

			if(FMC.VARS.autoReconnectTicks > 0 && FMC.OPTIONS.autoReconnect) {
				if(FMC.MC.currentScreen instanceof DisconnectedScreen) {
					FMC.VARS.autoReconnectTicks -= 1;

					if(FMC.VARS.autoReconnectTicks == 0 && FMC.VARS.lastJoinedServer != null) {
						if(FMC.VARS.autoReconnectTries < FMC.OPTIONS.autoReconnectMaxTries + 1) {
							FMC.MC.disconnect();
							FMC.MC.openScreen(new ConnectScreen(new TitleScreen(), FMC.MC, FMC.VARS.lastJoinedServer));
						}
						else {
							// we get here when all tries are due
							FMC.VARS.autoReconnectTries = 0;
						}
					}
				}
				else {
					// we get here when player decides to go back to multiplayer screen before all tries are due
					FMC.VARS.autoReconnectTicks = 0;
					FMC.VARS.autoReconnectTries = 0;
				}
			}
		});

		ClientTickEvents.END_WORLD_TICK.register(client -> {
			if(FMC.OPTIONS.toolWarning) {
				ItemStack mainHandItem = FMC.MC.player.getStackInHand(Hand.MAIN_HAND);
				ItemStack offHandItem = FMC.MC.player.getStackInHand(Hand.OFF_HAND);
	
				int mainHandDurability = mainHandItem.getMaxDamage() - mainHandItem.getDamage();;
				int offHandDurability = offHandItem.getMaxDamage() - offHandItem.getDamage();

				if(mainHandItem.isDamaged() && mainHandItem != FMC.VARS.mainHandToolItemStack) {
					if(MathHelper.floor(mainHandItem.getMaxDamage() * 0.9f) < mainHandItem.getDamage() + 1 && mainHandDurability < 13) {
						FMC.VARS.toolDurability = mainHandDurability;
						FMC.VARS.toolHand = Hand.MAIN_HAND;
						FMC.VARS.resetToolWarningTicks();
					}
				}

				if(offHandItem.isDamaged() && offHandItem != FMC.VARS.offHandToolItemStack) {
					if(MathHelper.floor(offHandItem.getMaxDamage() * 0.9f) < offHandItem.getDamage() + 1 && offHandDurability < 13) {
						if(mainHandDurability == 0 || offHandDurability < mainHandDurability) {
							FMC.VARS.toolDurability = offHandDurability;
							FMC.VARS.toolHand = Hand.OFF_HAND;
							FMC.VARS.resetToolWarningTicks();
						}
					}
				}

				FMC.VARS.mainHandToolItemStack = mainHandItem;
				FMC.VARS.offHandToolItemStack = offHandItem;
			}

			if(FMC.OPTIONS.autoEat) {
				if(FMC.MC.player.getHungerManager().getFoodLevel() <= 18 && FMC.MC.player.getOffHandStack().isFood()) {
					FMC.MC.options.keyUse.setPressed(true);
					FMC.VARS.eating = true;
				}
				else if(FMC.VARS.eating == true) {
					FMC.VARS.eating = false;

					FMC.MC.options.keyUse.setPressed(false);
				}
			}
		});

		ClientTickEvents.START_WORLD_TICK.register(client -> {
			if(FMC.OPTIONS.triggerBot && FMC.MC.currentScreen == null) {
				if(FMC.MC.crosshairTarget != null && FMC.MC.crosshairTarget.getType() == Type.ENTITY && FMC.MC.player.getAttackCooldownProgress(0.0f) >= 1.0f) {
					if(((EntityHitResult)FMC.MC.crosshairTarget).getEntity() instanceof LivingEntity) {
						LivingEntity livingEntity = (LivingEntity)((EntityHitResult)FMC.MC.crosshairTarget).getEntity();

						if(livingEntity.isAttackable() && livingEntity.hurtTime == 0 && livingEntity.isAlive()) {
							FMC.MC.interactionManager.attackEntity(FMC.MC.player, livingEntity);
							FMC.MC.player.swingHand(Hand.MAIN_HAND);
						}
					}
				}
			}
		});
	}
}
