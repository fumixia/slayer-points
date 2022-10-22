package com.tureallocations;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import javax.annotation.Nullable;
import lombok.Getter;
import net.runelite.api.ItemID;

@Getter
enum Task
{
	//<editor-fold desc="Enums">
	BANSHEES("Banshees", ItemID.BANSHEE, "Slayer tower", "Earmuffs"),
	BATS("Bats", ItemID.GIRAL_BAT_2, "Lumberyard teleport", "Cannonable"),
	BIRDS("Birds", ItemID.FEATHER, "Xeric's glade teleport", "Cannonable"),
	BEARS("Bears", ItemID.ENSOULED_BEAR_HEAD, "Ardougne teleport N/E", "Cannonable"),
	CAVE_BUGS("Cave bugs", ItemID.SWAMP_CAVE_BUG, "Lumbridge teleport", "Need light"),
	CAVE_CRAWLERS("Cave crawlers", ItemID.CAVE_CRAWLER, "Slayer Cave", "Poisonous"),
	CAVE_SLIMES("Cave slimes", ItemID.SWAMP_CAVE_SLIME, "Lumbridge Teleport", "Need light & Poisonous"),
	COWS("Cows", ItemID.COW_MASK, "Xeric's glade teleport", "Cannonable"),
	CRAWLING_HANDS("Crawling hands", ItemID.CRAWLING_HAND, "Slayer tower", "Non-Cannonable"),
	LIZARDS("Lizards", ItemID.DESERT_LIZARD, "Fairy ring DLQ", "Cannonable"),
	DOGS("Dogs", ItemID.GUARD_DOG, "Fairy Ring DLQ", "Cannonable"),
	DWARVES("Dwarves", ItemID.DWARVEN_HELMET, "Lassar Teleport", "Non-Cannonable"),
	GHOSTS("Ghosts", ItemID.GHOSTSPEAK_AMULET, "Kourend Catacombs South", "Non-Cannonable"),
	GOBLINS("Goblins", ItemID.ENSOULED_GOBLIN_HEAD, "Lumbridge Teleport", "Cannonable"),
	ICEFIENDS("Icefiends", ItemID.ICE_DIAMOND, "Lassar Teleport", "Cannonable"),
	KALPHITE("Kalphite", ItemID.KALPHITE_SOLDIER, "Slayer Ring - Stronghold", "Cannonable"),
	MINOTAURS("Minotaurs", ItemID.ENSOULED_MINOTAUR_HEAD, "Edgeville tele - South", "Cannonable"),
	MONKEYS("Monkeys", ItemID.ENSOULED_MONKEY_HEAD, "Fairy Ring - CKR", "Cannonable"),
	RATS("Rats", ItemID.RATS_TAIL, "Varrock Teleport - Sewer", "Cannonable"),
	SCORPIONS("Scorpions", ItemID.ENSOULED_SCORPION_HEAD, "Dueling PvP Arena", "Cannonable"),
	SKELETONS("Skeletons", ItemID.SKELETON_GUARD, "Edgeville Teleport - Sewers", "Cannonable"),
	SPIDERS("Spiders", ItemID.HUGE_SPIDER, "Lumbridge Teleport - Jail", "Cannonable"),
	WOLVES("Wolves", ItemID.GREY_WOLF_FUR, "Duel Arean - Glider WWM", "Cannonable"),
	ZOMBIES("Zombies", ItemID.ZOMBIE_HEAD, "Yanille Teleport - Jails", "Cannonable");
	//</editor-fold>

	private static final Map<String, Task> tasks;

	private final String name;
	private final int itemSpriteId;
	private final String gettingThere;
	private final String notes;

	static
	{
		ImmutableMap.Builder<String, Task> builder = new ImmutableMap.Builder<>();

		for (Task task : values())
		{
			builder.put(task.getName().toLowerCase(), task);
		}

		tasks = builder.build();
	}

	Task(String name, int itemSpriteId, String gettingThere, String notes)
	{
		this.name = name;
		this.itemSpriteId = itemSpriteId;
		this.gettingThere = gettingThere;
		this.notes = notes;
	}

	@Nullable
	static Task getTask(String taskName)
	{
		return tasks.get(taskName.toLowerCase());
	}
}
