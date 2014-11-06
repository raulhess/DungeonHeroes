package com.monsterhunt.data;

import java.io.Serializable;
import java.util.Date;

public class GameCharacter implements Serializable{
	private static final long serialVersionUID = -3360528699956301542L;
	private String name;
	private int level;
	private Date date;
	private int hp;
	private int currentHp;
	
	private int intel;
	private int str;
	private int dex;
	
	private int pIntel;
	private int pStr;
	private int pDex;
	private int pHp;
	
	private GameClass charClass;
	private GameAction actionA;
	private GameAction actionB;
	private GameAction actionC;
	private GameAction actionD;
	
	private int unspentAttPoints;
	private GameAction unassignedAction;

	public GameCharacter(String name, GameClass charClass, int intelligence,
			int strength, int dexterity) {
		this.name = name;
		this.charClass = charClass;
		this.intel = intelligence;
		this.str = strength;
		this.dex = dexterity;
		pIntel = 0;
		pDex = 0;
		pStr = 0;
		pHp = 0;
		this.level = 1;
		this.date = new Date();
		this.hp = charClass.getHpDie() * 2;
		this.currentHp = hp;
		GameAction ga = charClass.getLevelAbility(level);
		if(ga != null)
			actionA = ga;
		unspentAttPoints = 0;
		unassignedAction = null;
	}

	public GameAction levelUp() {
		level++;
		int nextHp = 0;
		while (nextHp < (charClass.getHpDie() / 2))
			nextHp = Roller.roll(charClass.getHpDie());
		hp += nextHp;
		currentHp += nextHp;
		unspentAttPoints ++;
		return charClass.getLevelAbility(level);
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

	public int takeShortRest() {
		int diceNumber = (level / 2) + (level % 2);
		int healAmount = Roller.roll(diceNumber, 8);
		return heal(healAmount);
	}
	
	public int heal(int amount){
		currentHp += amount;
		if (currentHp > hp)
			currentHp = hp;
		return amount;
	}
	
	public void reincarnate(){
		// generate permanent att points if level >= 5
		if(level >= 5){
			int it = 0;
			int chance = 0;
			for(it = 0; it < str; it++){
				chance = Roller.roll(100);
				if(chance <= 5)
					addPermStr();
			}
			for(it = 0; it < dex; it++){
				chance = Roller.roll(100);
				if(chance <= 5)
					addPermDex();
			}
			for(it = 0; it < intel; it++){
				chance = Roller.roll(100);
				if(chance <= 5)
					addPermIntel();
			}
		}
		
		// generate permanent hp points if level >= 10
		if(level >= 10)
			pHp += ((level / 5) - 1) * 5;
		
		// resets level and attributes
		level = 1;
		hp = charClass.getHpDie() * 2 + pHp;
		currentHp = hp;
		str = 0;
		dex = 0;
		intel = 0;
		GameAction ga = charClass.getLevelAbility(level);
		actionA = null;
		if(ga != null)
			actionA = ga;
		actionB = null;
		actionC = null;
		actionD = null;
	}
	
	private void addPermStr(){
		pStr++;
	}
	
	private void addPermIntel(){
		pIntel++;
	}
	
	private void addPermDex(){
		pDex++;
	}
	
	public void addStr(){
		if(unspentAttPoints > 0){
			str++;
			unspentAttPoints--;
		}
	}
	
	public void addIntel(){
		if(unspentAttPoints > 0){
			intel++;
			unspentAttPoints--;
		}
	}
	
	public void addDex(){
		if(unspentAttPoints > 0){
			dex++;
			unspentAttPoints--;
		}
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}

	public Date getDate() {
		return date;
	}

	public int getHp() {
		return hp;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public int getAc() {
		return 10 + dex + pDex;
	}

	public int getIntelligence() {
		return intel + pIntel;
	}

	public int getStrength() {
		return str + pStr;
	}

	public int getDexterity() {
		return dex + pDex;
	}

	public GameClass getCharClass() {
		return charClass;
	}

	public GameAction getActionA() {
		return actionA;
	}

	public GameAction getActionB() {
		return actionB;
	}

	public GameAction getActionC() {
		return actionC;
	}

	public GameAction getActionD() {
		return actionD;
	}
	
	public void setActionA(GameAction actionA) {
		this.actionA = actionA;
	}

	public void setActionB(GameAction actionB) {
		this.actionB = actionB;
	}

	public void setActionC(GameAction actionC) {
		this.actionC = actionC;
	}

	public void setActionD(GameAction actionD) {
		this.actionD = actionD;
	}

	public int getExtraPhysicalDmg(){
		return str + pStr;
	}
	
	public int getExtraMagicalDmg(){
		return intel + pIntel;
	}

	public int getUnspentAttPoints() {
		return unspentAttPoints;
	}

	public void setUnspentAttPoints(int unspentAttPoints) {
		this.unspentAttPoints = unspentAttPoints;
	}

	public GameAction getUnassignedAction() {
		return unassignedAction;
	}

	public void setUnassignedAction(GameAction unassignedAction) {
		this.unassignedAction = unassignedAction;
	}
	
	@Override
	public boolean equals(Object o) {
		try{
			GameCharacter other = (GameCharacter) o;
			if(other.getName().equals(getName())){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	public int getIntel() {
		return intel;
	}

	public int getStr() {
		return str;
	}

	public int getDex() {
		return dex;
	}

	public int getpIntel() {
		return pIntel;
	}

	public int getpStr() {
		return pStr;
	}

	public int getpDex() {
		return pDex;
	}

	public int getpHp() {
		return pHp;
	}
	
	

}
