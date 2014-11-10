package com.monsterhunt.data;

import java.util.ArrayList;
import java.util.List;

public abstract class GameEntity {
	public int hp;
	public int currentHp;
	
	public List<GameEffect> effects = new ArrayList<GameEffect>();
	
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
	
	public abstract int getAc();
	
	public void addEffect(GameEffect ge){
		effects.add(ge);
	}
	
	public void runEffects(){
		for(GameEffect ge : effects){
			ge.use();
			if(ge.getDuration() <= 0)
				effects.remove(ge);
		}
	}
	
	public boolean isStunned(){
		for(GameEffect ge : effects){
			if(ge.is(GameEffect.STUN))
				return true;
		}
		return false;
	}
}
