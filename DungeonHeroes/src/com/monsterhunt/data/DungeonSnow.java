package com.monsterhunt.data;

public class DungeonSnow extends DungeonGenerator {
	private static final String[] SNOW_MINION_NAMES = new String[] {
			"Barbarian Berserker", "Barbarian Warrior", "Barbarian Rider",
			"Armored Bear", "Winter Wolf", "White Tiger" };
	private static final String[] SNOW_BOSS_NAMES = new String[] {
			"Yeti", "Remorhaz", "Tribal Witch" };

	@Override
	public GameMonster generateMinion(int level) {
		GameMonster goblinMinion = null;
		String name = SNOW_MINION_NAMES[Roller
				.randomInt(SNOW_MINION_NAMES.length)];

		switch (name) {
		case "Barbarian Berserker":
			goblinMinion = generateDefaultMonster(level, false,
					GameMonster.BERSERKER);
			break;
		case "Barbarian Warrior":
			goblinMinion = generateDefaultMonster(level, false,
					GameMonster.MELEE);
			break;
		case "Barbarian Rider":
			goblinMinion = generateDefaultMonster(level, false,
					GameMonster.MELEE);
			break;
		case "Armored Bear":
			goblinMinion = generateDefaultMonster(level, false,
					GameMonster.MELEE);
			break;
		case "Winter Wolf":
			goblinMinion = generateDefaultMonster(level, false,
					GameMonster.MELEE);
			break;
		case "White Tiger":
			goblinMinion = generateDefaultMonster(level, false,
					GameMonster.MELEE);
			break;
		}

		goblinMinion.setName(name);
		return goblinMinion;
	}

	@Override
	public GameMonster generateBoss(int level) {
		GameMonster goblinBoss = null;
		String name = SNOW_BOSS_NAMES[Roller
				.randomInt(SNOW_BOSS_NAMES.length)];
		switch (name) {
		case "Yeti":
			goblinBoss = generateDefaultMonster(level, true, GameMonster.BERSERKER);
			break;
		case "Remorhaz":
			goblinBoss = generateDefaultMonster(level, true, GameMonster.MELEE);
			break;
		case "Tribal Witch":
			goblinBoss = generateDefaultMonster(level, true,
					GameMonster.CASTER);
			break;
		}
		goblinBoss.setName(name);
		return goblinBoss;
	}

	@Override
	public String getType() {
		return GameDungeon.SNOW;
	}
}
