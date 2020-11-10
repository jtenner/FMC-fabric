# FMC

Various tweaks fabric mod, compatible with Sodium.

## Installation

1. Download fabric loader from [here](https://fabricmc.net/use/) (**REQUIRES** Fabric-API! Get it [here](https://www.curseforge.com/minecraft/mc-mods/fabric-api))

2. Download latest FMC-fabric release from [here](https://github.com/Flourick/FMC-fabric/releases).

3. Once you run fabric loader a mods folder will be created in your *.minecraft* directory. Move the `fmc-fabric-*.jar` and `fabric-api-*.jar` there.

## Features

List of main features. Configuration is in in-game options menu called 'FMC...'. Keybindings are configured in the usual Controls menu (FMC category) and are by default unset.

### Crosshair

This disables crosshair changing it's color based on background and instead let's you choose the static color yourself. You can also set the scale of the crosshair. You can still scale the crosshair even if you don't use the static color feature.

If you use a crosshair from a resource pack that is already colored or you wish to have a crosshair that has multiple colors, set the color to white (RGB: 255, 255, 255) which will make it so only colors from the resource pack are used.

NOTE: Crosshair in resource pack is the upper-left 15x15 pixels in *assets/minecraft/textures/gui/icons.png*

### HUD Info

Shows your location, pitch, cardinal direction and block light while ingame.

### Disable 'W' To Sprint

The header says it all. Gets rid of one of the most annoying features.

### Death Coordinates

Sends your last death coordinates in chat after you respawn (only visible to you).

### Tool Breaking

Has two independent modes:

#### 1) Warning

When your tools go below 10% durability and have 12 or less uses a red warning text appears on your screen for two seconds. Can be either on top of the screen or above the hotbar. Text size can be changed in settings.

#### 2) Prevent breaking

Makes your tools stop working at certain durability (will stop at 2 durability for most tools). This includes swords, pickaxes, axes, shovels, hoes, trident, bow and crossbow. Can be overriden (by default holding right ALT).

### Cloud Height

Allows you to set the height at which clouds render (0 - 256).

### No Nether Fog

Disables the thick nether fog (but leaves the default fog intact).

### No Block Break Particles

Disables the particles that spawn when you break a block.

### Refill Hand

Once the stack in your main hand is below 50% automatically finds the same item in your inventory and restocks it.

### Random Block Placement (keybindable)

Randomly selects a block from your hotbar to place every time you try to place a block or when you have an empty hand.

### Fullbright (keybindable)

Self-explanatory. Useful for caving and in Nether.

### Entity Outline (keybindable)

Makes all entities (except players) glow white and be seen through walls. Useful for mob spawn proofing.

### Freecam (keybindable)

Allows you to leave your body and explore your surroundings. Works similar to spectator.

## Preview (v1.4.1)

Static crosshair color, HUD info, death message & tool breaking warning.

![hud](https://user-images.githubusercontent.com/33128006/91038667-70387b80-e60b-11ea-9ee0-2e28d4d7d6f2.png)

Ingame menu (default settings)

![menu](https://user-images.githubusercontent.com/33128006/98689627-669df480-236c-11eb-8659-17048266464a.png)

----

If you have any suggestions you can post in Issues.
