package com.monsterhunt.data;

public class DungeonUndead extends DungeonGenerator{
	private static final String[] UNDEAD_MINION_NAMES = new String[] {
			"Cursed Skeleton", "Cursed Wight", "Ghoul", "Skeleton Warrior",
			"Wight", "Zombie" };
	private static final String[] UNDEAD_BOSS_NAMES = new String[] {
			"Giant Skeleton", "Grave Digger", "Flaming Skeleton" };
	
	@Override
	public GameMonster generateMinion(int level) {
		GameMonster undeadMinion = null;
		String name = UNDEAD_MINION_NAMES[Roller
				.randomInt(UNDEAD_MINION_NAMES.length)];

		switch (name) {
		case "Cursed Skeleton":
			undeadMinion = generateDefaultMonster(level, false,
					GameMonster.CASTER);
			break;
		case "Cursed Wight":
			undeadMinion = generateDefaultMonster(level, false,
					GameMonster.CASTER);
			break;
		case "Ghoul":
			undeadMinion = generateDefaultMonster(level, false,
					GameMonster.MELEE);
			break;
		case "Skeleton Warrior":
			undeadMinion = generateDefaultMonster(level, false,
					GameMonster.MELEE);
			break;
		case "Wight":
			undeadMinion = generateDefaultMonster(level, false,
					GameMonster.MELEE);
			break;
		case "Zombie":
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
		String name = UNDEAD_BOSS_NAMES[Roller
				.randomInt(UNDEAD_BOSS_NAMES.length)];
		switch (name) {
		case "Giant Skeleton":
			undeadBoss = generateDefaultMonster(level, true,
					GameMonster.BERSERKER);
			break;
		case "Grave Digger":
			undeadBoss = generateDefaultMonster(level, true, GameMonster.MELEE);
			break;
		case "Flaming Skeleton":
			undeadBoss = generateDefaultMonster(level, true, GameMonster.CASTER);
			break;
		}
		undeadBoss.setName(name);
		return undeadBoss;
	}

	@Override
	public String getType() {
		return GameDungeon.UNDEAD;
	}
}
