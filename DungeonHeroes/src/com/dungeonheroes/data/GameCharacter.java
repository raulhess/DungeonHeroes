package com.dungeonheroes.data;

import java.io.Serializable;
import java.util.Date;

import com.dungeonheroes.activities.MainActivity;

import android.util.Log;

public class GameCharacter implements Serializable{
	private static final long serialVersionUID = -3360528699956301542L;
	
	public static final int RANK_EXPLORER = 0;
	public static final int RANK_ADVENTURER = 1;
	public static final int RANK_HERO = 2;
	
	private String name;
	private Date date;
	private int level;
	private int rank;
	private int hp;
	private int currentHp;
	private int initiative = 0;

	private int blind = 0;
	private int damageOverTime = 0;
	private int damageOverTimeCounter = 0;
	private int stun = 0;
	private int slow = 0;
	private int weak = 0;
	
	private int intel;
	private int str;
	private int dex;
	
	private int pIntel;
	private int pStr;
	private int pDex;
	private int pHp;
	private int guardianSouls;
	private int guardianKeys;
	
	private GameClass charClass;
	private GameAction basicAction;
	private GameAction powerAction;
	
	private int unspentAttPoints;

	public GameCharacter(String name, GameClass charClass, int intelligence,
			int strength, int dexterity) {
		this.name = name;
		this.charClass = charClass;
		this.intel = intelligence;
		this.str = strength;
		this.dex = dexterity;
		rank = RANK_ADVENTURER;
		pIntel = 0;
		pDex = 0;
		pStr = 0;
		pHp = 0;
		guardianSouls = 0;
		guardianKeys = 0;
		level = 1;
		this.date = new Date();
		hp = charClass.getHpDie() * 2;
		currentHp = hp;
		GameAction ga = charClass.getLevelAbility(level);
		if(ga != null)
			basicAction = ga;
		unspentAttPoints = 0;
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

	public int takeShortRest() {
		if(powerAction != null)
			powerAction.recover();
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
	
	public String reincarnate(){
		int permanentPoints = 0;
		// generate permanent att points if level >= 5
		if(level >= 5){
			int it = 0;
			int chance = 0;
			int extraChance = guardianSouls > 15 ? 15 : guardianSouls;
			if(rank >= RANK_EXPLORER) extraChance += 5;
			int target = 10 + (extraChance);
			for(it = 0; it < str; it++){
				chance = Roller.roll(100);
				Log.d(MainActivity.TAG, "Chance: " + chance + " / " + target);
				if(chance <= target){
					addPermStr();
					permanentPoints++;
				}
			}
			for(it = 0; it < dex; it++){
				chance = Roller.roll(100);
				Log.d(MainActivity.TAG, "Chance: " + chance + " / " + target);
				if(chance <= target){
					addPermDex();
					permanentPoints++;
				}
			}
			for(it = 0; it < intel; it++){
				chance = Roller.roll(100);
				Log.d(MainActivity.TAG, "Chance: " + chance + " / " + target);
				if(chance <= target){
					addPermIntel();
					permanentPoints++;
				}
			}
		}
		
		// generate permanent hp points if level >= 10
		pHp += (level / 10) * 5;
		
		// resets level and attributes
		level = 1;
		hp = charClass.getHpDie() * 2 + pHp;
		currentHp = hp;
		str = 0;
		dex = 0;
		intel = 0;
		GameAction ga = charClass.getLevelAbility(level);
		basicAction = null;
		if(ga != null)
			basicAction = ga;
		powerAction = null;
		if(permanentPoints > 0)
			return "Congratulations! After reincarnating, " + permanentPoints + " attribute points "
					+ "became permanently bound to your character's soul";
		else
			return null;
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
		return 10 + getDexterity() / 2;
	}
	
	public int getPermanentAttPoints(){
		return pDex + pIntel + pStr;
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

	public GameAction getBasicAction() {
		return basicAction;
	}

	public GameAction getPowerAction() {
		return powerAction;
	}
	
	public void setBasicAction(GameAction basicAction) {
		this.basicAction = basicAction;
	}

	public void setPowerAction(GameAction powerAction) {
		this.powerAction = powerAction;
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
	
	public int getGuardianKeys() {
		return guardianKeys;
	}
	
	public int getGuardianSouls() {
		return guardianSouls;
	}
	
	public String addGuardianSoul() {
		guardianSouls++;
		if(guardianSouls >= 15 && rank == RANK_EXPLORER){
			return "Congratulations! " + name + " has evolved from "
					+ "Explorer to Hero! He now gains an extra 5% chance of "
					+ "turning an attribute point permanent on a reincarnation.";
		}
		return null;
	}

	public void rollInit() {
		initiative = Roller.roll(20) + getDexterity();
	}
	
	public int getInit(){
		return initiative;
	}
	
	public int getRank(){
		return rank;
	}
	
	public String getRankName(){
		switch(rank){
		case RANK_ADVENTURER:
			return "Adventurer";
		case RANK_EXPLORER:
			return "Explorer";
		case RANK_HERO:
			return "Adventurer";
		}
		return "";
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
