package com.dungeonheroes.data;

import java.io.Serializable;

public abstract class GameClass implements Serializable{
	private static final long serialVersionUID = 4624012191093267462L;
	public abstract String getName();
	public abstract GameAction getLevelAbility(int level);
	public abstract int getHpDie();
	
}
