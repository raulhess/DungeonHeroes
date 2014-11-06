package com.monsterhunt.data;

public class DungeonGoblinoid extends DungeonGenerator {
	private static final String[] GOBLIN_MINION_NAMES = new String[] {
			"Goblin Skirmisher", "Goblin Shaman", "Goblin Warrior",
			"Hobgoblin Archer", "Hobgoblin Warrior", "Hobgoblin Wizard" };
	private static final String[] GOBLIN_BOSS_NAMES = new String[] {
			"Hobgoblin Commander", "Troll", "Ogre" };

	@Override
	public GameMonster generateMinion(int level) {
		GameMonster goblinMinion = null;
		String name = GOBLIN_MINION_NAMES[Roller
				.randomInt(GOBLIN_MINION_NAMES.length)];

		switch (name) {
		case "Goblin Skirmisher":
			goblinMinion = generateDefaultMonster(level, false,
					GameMonster.RANGED);
			break;
		case "Goblin Shaman":
			goblinMinion = generateDefaultMonster(level, false,
					GameMonster.CASTER);
			break;
		case "Goblin Warrior":
			goblinMinion = generateDefaultMonster(level, false,
					GameMonster.MELEE);
			break;
		case "Hobgoblin Archer":
			goblinMinion = generateDefaultMonster(level, false,
					GameMonster.RANGED);
			break;
		case "Hobgoblin Warrior":
			goblinMinion = generateDefaultMonster(level, false,
					GameMonster.MELEE);
			break;
		case "Hobgoblin Wizard":
			goblinMinion = generateDefaultMonster(level, false,
					GameMonster.CASTER);
			break;
		}

		goblinMinion.setName(name);
		return goblinMinion;
	}

	@Override
	public GameMonster generateBoss(int level) {
		GameMonster goblinBoss = null;
		String name = GOBLIN_BOSS_NAMES[Roller
				.randomInt(GOBLIN_BOSS_NAMES.length)];
		switch (name) {
		case "Hobgoblin Commander":
			goblinBoss = generateDefaultMonster(level, true, GameMonster.MELEE);
			break;
		case "Troll":
			goblinBoss = generateDefaultMonster(level, true, GameMonster.MELEE);
			break;
		case "Ogre":
			goblinBoss = generateDefaultMonster(level, true,
					GameMonster.BERSERKER);
			break;
		}
		goblinBoss.setName(name);
		return goblinBoss;
	}

	@Override
	public String getType() {
		return GameDungeon.FOREST;
	}
}
