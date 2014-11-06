package com.monsterhunt.data;

public class ClassFighter extends GameClass {
	private static final long serialVersionUID = -8763421060596927281L;

	@Override
	public String getName() {

		return "Fighter";
	}

	@Override
	public GameAction getLevelAbility(int level) {
		switch(level){
		case 1:
			return new GameAction("Basic Attack", false, 1, 10, 1, 0);
		case 2:
			return new GameAction("Strong Attack", false, 2, 6, 1, 1);
		case 5:
			return new GameAction("Cleave", false, 3, 6, 3, 3);
		default:
			return null;
		}
	}

	@Override
	public int getHpDie() {

		return 10;
	}
}
