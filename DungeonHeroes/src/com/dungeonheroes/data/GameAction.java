package com.dungeonheroes.data;

import java.io.Serializable;

public class GameAction implements Serializable {
	private static final long serialVersionUID = -7044772487536884937L;
	
	private String name;
	private boolean isMagical;
	private int diceNumber;
	private int diceFaces;
	private int dmgModifier;
	private int charges;
	private int currentCharges;
	private GameEffect effect;
	
	public GameAction(String name, boolean isMagical, int diceNumber,
			int diceFaces, int dmgModifier, int charges, GameEffect effect) {
		super();
		this.name = name;
		this.isMagical = isMagical;
		this.diceNumber = diceNumber;
		this.diceFaces = diceFaces;
		this.dmgModifier = dmgModifier;
		this.charges = charges;
		currentCharges = charges;
		this.effect = effect;
	}

	public String getName() {
		return name;
	}

	public boolean isMagical() {
		return isMagical;
	}

	public int getDmg() {
		return Roller.roll(diceNumber, diceFaces, dmgModifier);
	}
	
	public int getModifier() {
		return dmgModifier;
	}
	
	public String getDmgString() {
		return diceNumber + "d" + diceFaces + " +";
	}
	
	public int getMinDamage() {
		return diceNumber + dmgModifier;
	}
	
	public int getMaxDamage() {
		return diceNumber * diceFaces + dmgModifier;
	}
	
	public boolean use(){
		if(charges == 0)
			return true;
		if(currentCharges > 0){
			currentCharges--;
			return true;
		}
		return false;
	}
	
	public void recover(){
		this.currentCharges = charges;
	}

	public int getCharges() {
		return charges;
	}

	public int getCurrentCharges() {
		return currentCharges;
	}

	public GameEffect getEffect() {
		return effect;
	}
	
	public boolean isBasic(){
		if(charges == 0)
			return true;
		return false;
	}
	
	
}
