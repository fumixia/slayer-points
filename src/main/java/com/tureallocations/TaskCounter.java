package com.tureallocations;

import net.runelite.client.plugins.Plugin;
import net.runelite.client.ui.overlay.infobox.InfoBox;

import java.awt.*;
import java.awt.image.BufferedImage;

class TaskCounter extends InfoBox
{

	TaskCounter(BufferedImage image, Plugin plugin)
	{
		super(image, plugin);
	}

	@Override
	public String getText()
	{
		return "";
	}

	@Override
	public Color getTextColor()
	{
		return Color.WHITE;
	}
}
