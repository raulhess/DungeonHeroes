package com.dungeonheroes.data;

public class DungeonLizardfolk extends DungeonGenerator {
	private static final String[] LIZARDFOLK_MINION_NAMES = new String[] {
			"Lizardfolk Archer", "Lizardfolk Brute", "Lizardfolk Scout",
			"Lizardfolk Skirmisher", "Lizardfolk Shaman", "Lizardfolk Warrior" };
	private static final String[] LIZARDFOLK_BOSS_NAMES = new String[] {
			"Fire Lizard", "Giant Black Lizard", "Lizardking" };

	@Override
	public GameMonster generateMinion(int level) {
		GameMonster undeadMinion = null;
		String name = LIZARDFOLK_MINION_NAMES[Roller
				.randomInt(LIZARDFOLK_MINION_NAMES.length)];

		switch (name) {
		case "Lizardfolk Archer":
			undeadMinion = generateDefaultMonster(level, false,
					GameMonster.RANGED);
			break;
		case "Lizardfolk Brute":
			undeadMinion = generateDefaultMonster(level, false,
					GameMonster.BERSERKER);
			break;
		case "Lizardfolk Scout":
			undeadMinion = generateDefaultMonster(level, false,
					GameMonster.MELEE);
			break;
		case "Lizardfolk Skirmisher":
			undeadMinion = generateDefaultMonster(level, false,
					GameMonster.RANGED);
			break;
		case "Lizardfolk Shaman":
			undeadMinion = generateDefaultMonster(level, false,
					GameMonster.CASTER);
			break;
		case "Lizardfolk Warrior":
			undeadMinion = generateDefaultMonster(level, false,
					GameMonster.MELEE);
			break;
		}

		undeadMinion.setName(name);
		return undeadMinion;
	}

	@Override
	public GameMonster generateBoss(int level) {
		GameMonster undeadBoss = null;
		String name = LIZARDFOLK_BOSS_NAMES[Roller
				.randomInt(LIZARDFOLK_BOSS_NAMES.length)];
		switch (name) {
		case "Fire Lizard":
			undeadBoss = generateDefaultMonster(level, true,
					GameMonster.CASTER);
			break;
		case "Giant Black Lizard":
			undeadBoss = generateDefaultMonster(level, true, GameMonster.MELEE);
			break;
		case "Lizardking":
			undeadBoss = generateDefaultMonster(level, true, GameMonster.MELEE);
			break;
		}
		undeadBoss.setName(name);
		return undeadBoss;
	}

	@Override
	public String getType() {
		return GameDungeon.LIZARDFOLK;
	}
}
