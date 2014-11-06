package com.monsterhunt.data;

import java.io.Serializable;

public class GameAction implements Serializable {
	private static final long serialVersionUID = -7044772487536884937L;
	private String name;
	private boolean isMagical;
	private int diceNumber;
	private int diceFaces;
	private int dmgModifier;
	private int cooldown;
	private int cooldownCount;

	

	public GameAction(String name, boolean isMagical, int diceNumber,
			int diceFaces, int dmgModifier, int cooldown) {
		super();
		this.name = name;
		this.isMagical = isMagical;
		this.diceNumber = diceNumber;
		this.diceFaces = diceFaces;
		this.dmgModifier = dmgModifier;
		this.cooldown = cooldown;
		cooldownCount = 0;
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

	public int getCooldown() {
		return cooldown;
	}

	public int getCooldownCount() {
		return cooldownCount;
	}
	
	public void setCooldown(){
		cooldownCount = cooldown;
	}
	
	public void reduceCooldown() {
		cooldownCount--;
	}

}
