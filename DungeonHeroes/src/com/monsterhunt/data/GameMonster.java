package com.monsterhunt.data;

public class GameMonster {
	public static final String BERSERKER = "berserker";
	public static final String CASTER = "caster";
	public static final String MELEE = "melee";
	public static final String RANGED = "ranged";
	
	private String type;
	private String name;
	private int level;
	private int hp;
	private int currentHp;
	private int initiative = 0;
	
	private int attBonus;
	private int ac;
	private int dmgDiceNumber;
	private int dmgDiceFaces;
	private int dmgDiceModifier;
	private boolean isMagical;
	private boolean isGuardian;
	private boolean isAstral;
	
	private int blind = 0;
	private int damageOverTime = 0;
	private int damageOverTimeCounter = 0;
	private int stun = 0;
	private int slow = 0;
	private int weak = 0;

	public GameMonster(int level, String type, String name, int hp, int attBonus, int ac,
			int dmgDiceNumber, int dmgDiceFaces, int dmgDiceModifier,
			boolean isMagical) {
		super();
		this.level = level;
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
	
	public int takeAtt(boolean isMagical, int attBonus, int dmg) {
		if (isMagical) {
			currentHp -= dmg;
			return dmg;
		} else {
			int roll = Roller.roll(20);
			if (roll == 20) {
				currentHp -= dmg * 2;
				return dmg * 2;
			} else if (roll != 1 && roll + attBonus >= getAc()) {
				currentHp -= dmg;
				return dmg;
			} else {
				return 0;
			}
		}
	}
	
	public int getAc(){
		return ac;
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

	public boolean isAstral() {
		return isAstral;
	}

	public void setAstral(boolean isAstral) {
		this.isAstral = isAstral;
	}

	public void rollInit() {
		initiative = Roller.roll(20) + level;
	}
	
	public int getInit(){
		return initiative;
	}
	
	public boolean isBlind() {
		if (blind <= 0) {
			return false;
		}
		blind--;
		return true;
	}
	
	public int hasDamageOverTime() {
		if (damageOverTime <= 0) {
			return 0;
		} else {
			int damage = damageOverTime;
			this.hp -= damage;
			damageOverTimeCounter--;
			if (damageOverTimeCounter <= 0) {
				damageOverTime = 0;
			}
			return damage;
		}
	}

	public boolean isSlowed() {
		if (slow <= 0) {
			return false;
		}
		slow--;
		return true;
	}

	public boolean isStunned() {
		if (stun <= 0) {
			return false;
		}
		stun--;
		return true;
	}

	public boolean isWeakened() {
		if (weak <= 0) {
			return false;
		}
		weak--;
		return true;
	}

}
