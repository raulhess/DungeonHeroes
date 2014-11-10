package com.monsterhunt.data;

public class GameMonster extends GameEntity{
	public static final String BERSERKER = "berserker";
	public static final String CASTER = "caster";
	public static final String MELEE = "melee";
	public static final String RANGED = "ranged";
	
	private String type;
	private String name;
	private int attBonus;
	private int ac;
	private int dmgDiceNumber;
	private int dmgDiceFaces;
	private int dmgDiceModifier;
	private boolean isMagical;
	private boolean isGuardian;

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

	public int getDamage(){
		return Roller.roll(dmgDiceNumber, dmgDiceFaces, dmgDiceModifier);
	}
	
	@Override
	public int getAc(){
		return this.ac;
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

	public boolean isGuardian() {
		return isGuardian;
	}

	public void setGuardian(boolean isGuardian) {
		this.isGuardian = isGuardian;
	}

}
