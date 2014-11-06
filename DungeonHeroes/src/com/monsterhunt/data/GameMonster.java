package com.monsterhunt.data;

public class GameMonster {
	public static final String BERSERKER = "berserker";
	public static final String CASTER = "caster";
	public static final String MELEE = "melee";
	public static final String RANGED = "ranged";
	
	private String type;
	private String name;
	private int hp;
	private int currentHp;
	private int attBonus;
	private int ac;
	private int dmgDiceNumber;
	private int dmgDiceFaces;
	private int dmgDiceModifier;
	private boolean isMagical;

	public GameMonster(String type, String name, int hp, int attBonus, int ac,
			int dmgDiceNumber, int dmgDiceFaces, int dmgDiceModifier,
			boolean isMagical) {
		super();
		this.type = type;
		this.name = name;
		this.hp = hp;
		this.currentHp = hp;
		this.attBonus = attBonus;
		this.ac = ac;
		this.dmgDiceNumber = dmgDiceNumber;
		this.dmgDiceFaces = dmgDiceFaces;
		this.dmgDiceModifier = dmgDiceModifier;
		this.isMagical = isMagical;
	}

	public int takeAtt(boolean isMagical, int attBonus, int dmg) {
		if (isMagical) {
			currentHp -= dmg;
			return dmg;
		} else {
			int roll = Roller.roll(20);
			if (roll == 20) {
				currentHp -= dmg * 2;
				return dmg * 2;
			} else if (roll != 1 && roll + attBonus >= ac) {
				currentHp -= dmg;
				return dmg;
			} else {
				return 0;
			}
		}
	}

	public int getDamage(){
		return Roller.roll(dmgDiceNumber, dmgDiceFaces, dmgDiceModifier);
	}
	
	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public int getHp() {
		return hp;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public int getAttBonus() {
		return attBonus;
	}

	public int getAc() {
		return ac;
	}

	public int getDmgDiceNumber() {
		return dmgDiceNumber;
	}

	public int getDmgDiceFaces() {
		return dmgDiceFaces;
	}

	public int getDmgDiceModifier() {
		return dmgDiceModifier;
	}

	public boolean isMagical() {
		return isMagical;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMagical(boolean isMagical) {
		this.isMagical = isMagical;
	}
	
	

}
