package com.monsterhunt.data;

import java.util.ArrayList;
import java.util.List;

public abstract class DungeonGenerator {

	public GameDungeon generateDungeon(int level) {
		List<GameMonster> monsters = new ArrayList<GameMonster>();
		int dungeonSize = 4 + (level / 2);
		for (int i = 0; i < dungeonSize; i++) {
			monsters.add(generateMinion(level));
		}
		monsters.add(generateBoss(level));
		return new GameDungeon(monsters, level, getType());
	}

	public abstract GameMonster generateMinion(int level);
	public abstract GameMonster generateBoss(int level);
	public abstract String getType();

	protected GameMonster generateDefaultMonster(int level, boolean isBoss,
			String type) {
		if (isBoss)
			level += 2;

		int hpNum = (level / 2) + (level % 2);
		// TODO generate random mod
		// double hpMod = Math.pow(5, (level/10));
		int hp = Roller.roll(hpNum, 6, 1);
		int attBonus = level / 2;
		int ac = level / 3;
		int dmgDiceNumber = getDiceNumberCategory(level);
		int dmgDiceFaces = getDiceFacesCategory(level);
		int dmgDiceModifier = getDiceModifierCategory(level);
		boolean isMagical = false;

		switch (type) {
		case GameMonster.BERSERKER:
			attBonus = level / 4;
			dmgDiceNumber = getDiceNumberCategory(level + 1);
			dmgDiceFaces = getDiceFacesCategory(level + 1);
			dmgDiceModifier = getDiceModifierCategory(level + 1);
			break;
		case GameMonster.CASTER:
			isMagical = true;
			if (level > 2) {
				dmgDiceNumber = getDiceNumberCategory(level - 2);
				dmgDiceFaces = getDiceFacesCategory(level - 2);
				dmgDiceModifier = getDiceModifierCategory(level - 2);
			}
			break;
		case GameMonster.MELEE:
			hp += level;
			break;
		case GameMonster.RANGED:
			attBonus = level;
			if (level > 1) {
				dmgDiceNumber = getDiceNumberCategory(level - 1);
				dmgDiceFaces = getDiceFacesCategory(level - 1);
				dmgDiceModifier = getDiceModifierCategory(level - 1);
			}
			break;
		}

		return new GameMonster(level, type, null, hp, attBonus, ac, dmgDiceNumber,
				dmgDiceFaces, dmgDiceModifier, isMagical);
	}

	protected static int getDiceFacesCategory(int level) {
		if (level < 5) {
			return 4;
		} else if (level < 9) {
			return 6;
		} else if (level < 13) {
			return 8;
		} else if (level < 17) {
			return 10;
		} else {
			return 12;
		}
	}

	protected static int getDiceNumberCategory(int level) {
		if (level < 21) {
			return 1;
		} else if (level < 25) {
			return 2;
		} else if (level < 29) {
			return 3;
		} else if (level < 33) {
			return 4;
		} else if (level < 37) {
			return 5;
		} else {
			return 6;
		}
	}

	protected static int getDiceModifierCategory(int level) {
		if (level < 41) {
			return level / 3;
		} else {
			return level / 2;
		}
	}
}
