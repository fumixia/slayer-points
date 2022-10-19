package com.tureallocations;

import com.google.inject.Provides;
import javax.inject.Inject;

import joptsimple.internal.Strings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.ItemID;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import net.runelite.client.util.ColorUtil;
import net.runelite.client.util.Text;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@PluginDescriptor(
	name = "Tureal NPC Locations"
)
public class TurealLocationsPlugin extends Plugin
{
	//Chat messages
	private static final Pattern CHAT_GEM_PROGRESS_MESSAGE = Pattern.compile("^(?:You're assigned to kill|You have received a new Slayer assignment from .*:) (?:[Tt]he )?(?<name>.+?)(?: (?:in|on|south of) (?:the )?(?<location>[^;]+))?(?:; only | \\()(?<amount>\\d+)(?: more to go\\.|\\))$");
	private static final String CHAT_GEM_COMPLETE_MESSAGE = "You need something new to hunt.";
	private static final String CHAT_CANCEL_MESSAGE = "Your task has been cancelled.";

	@Inject
	private Client client;
	@Inject
	private TurealLocationsConfig config;
	@Inject
	private InfoBoxManager infoBoxManager;
	@Inject
	private ItemManager itemManager;
	@Getter(AccessLevel.PACKAGE)
	@Setter(AccessLevel.PACKAGE)
	private String taskName;

	private TaskCounter counter;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Example started!");
	}



	@Override
	protected void shutDown() throws Exception
	{
		removeCounter();
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		if (event.getType() != ChatMessageType.GAMEMESSAGE && event.getType() != ChatMessageType.SPAM)
		{
			return;
		}

		String chatMsg = Text.removeTags(event.getMessage()); //remove color and linebreaks

		if (chatMsg.startsWith("You've completed") && (chatMsg.contains("Slayer master") || chatMsg.contains("Slayer Master")))
		{
			setTask("");
			return;
		}

		if (chatMsg.equals(CHAT_GEM_COMPLETE_MESSAGE) || chatMsg.equals(CHAT_CANCEL_MESSAGE))
		{
			setTask("");
			return;
		}


		Matcher mProgress = CHAT_GEM_PROGRESS_MESSAGE.matcher(chatMsg);

		if (mProgress.find())
		{
			String name = mProgress.group("name");
			setTask(name);
		}
	}

	private void setTask(String name)
	{
		log.info(name);
		taskName = name;
		removeCounter();
		addCounter();
	}

	private void addCounter()
	{
		if (Strings.isNullOrEmpty(taskName))
		{
			return;
		}

		Task task = Task.getTask(taskName);
		int itemSpriteId = ItemID.ENCHANTED_GEM;
		if (task != null)
		{
			itemSpriteId = task.getItemSpriteId();
		}

		BufferedImage taskImg = itemManager.getImage(itemSpriteId);
		String taskTooltip = ColorUtil.wrapWithColorTag("%s", new Color(255, 119, 0)) + "</br>";

		taskTooltip += ColorUtil.wrapWithColorTag("Getting there:", Color.YELLOW)
				+ " %s</br>"
				+ ColorUtil.wrapWithColorTag("Cannonable:", Color.YELLOW)
				+ " %s";

		counter = new TaskCounter(taskImg, this);
		counter.setTooltip(String.format(taskTooltip, capsString(taskName), task.getGettingThere(), task.getNotes()));

		infoBoxManager.addInfoBox(counter);
	}

	private void removeCounter()
	{
		if (counter == null)
		{
			return;
		}

		infoBoxManager.removeInfoBox(counter);
		counter = null;
	}

	//Utils
	private String capsString(String str)
	{
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	@Provides
	TurealLocationsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(TurealLocationsConfig.class);
	}
}
