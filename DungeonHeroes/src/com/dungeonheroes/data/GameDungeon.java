package com.dungeonheroes.data;

import java.util.List;

public class GameDungeon {
	public static final String LIZARDFOLK = "type_lizardfolk";
	public static final String[] LIZARDFOLK_NAMES = new String[] {
			"Bleak Mire", "Shadow Marshes", "Temple of Scales", "Death Swamp" };
	public static final String FOREST = "type_forest";
	public static final String[] FOREST_NAMES = new String[] { "Blackwood",
			"Greenhand Camp", "Wild Evergreen", "Lost Vale" };
	public static final String UNDEAD = "type_undead";
	public static final String[] UNDEAD_NAMES = new String[] {
			"Abandoned Crypt", "Cursed Cemetery", "Foul Lair", "Haunted House" };
	public static final String SNOW = "type_snow";
	public static final String[] SNOW_NAMES = new String[] { "Broken Valley",
			"Ice Point", "Cold Plains", "White Mountains" };

	private List<GameMonster> monsters;
	private int difficulty;
	private String type;
	private String name;
	private int size;

	public GameDungeon(List<GameMonster> monsters, int difficulty, String type) {
		super();
		this.monsters = monsters;
		this.difficulty = difficulty;
		this.type = type;
		switch (type) {
		case LIZARDFOLK:
			this.name = LIZARDFOLK_NAMES[Roller
					.randomInt(LIZARDFOLK_NAMES.length)];
			break;
		case FOREST:
			this.name = FOREST_NAMES[Roller.randomInt(FOREST_NAMES.length)];
			break;
		case UNDEAD:
			this.name = UNDEAD_NAMES[Roller.randomInt(UNDEAD_NAMES.length)];
			break;
		case SNOW:
			this.name = SNOW_NAMES[Roller.randomInt(SNOW_NAMES.length)];
			break;
		default:
			this.name = "Unknown";
			break;
		}
		this.size = monsters.size();
	}

	public GameMonster getNextMonster() {
		if (monsters.size() > 0)
			return monsters.remove(0);
		return null;
	}

	public boolean hasMonsters() {
		if (monsters.size() > 0)
			return true;
		return false;
	}
	
	public int getCurrentMonsters() {
		return size - monsters.size();
	}
	
	public int getTotalMonsters() {
		return size;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

}
