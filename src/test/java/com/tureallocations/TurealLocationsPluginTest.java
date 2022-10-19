package com.tureallocations;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class TurealLocationsPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(TurealLocationsPlugin.class);
		RuneLite.main(args);
	}
}