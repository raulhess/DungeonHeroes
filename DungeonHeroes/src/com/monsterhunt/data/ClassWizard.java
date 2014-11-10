package com.monsterhunt.data;

public class ClassWizard extends GameClass {
	private static final long serialVersionUID = 611248567062831595L;

	@Override
	public String getName() {

		return "Wizard";
	}

	@Override
	public GameAction getLevelAbility(int level) {
		switch(level){
		case 1:
			return new GameAction("Magic Missiles", true, 2, 4, 2, 0, null);
		case 2:
			return new GameAction("Fire Ball", true, 3, 6, 1, 1, null);
		case 3:
			return new GameAction("Melf's Acid Arrow", true, 3, 8, 4, 1, null);
		case 5:
			return new GameAction("Burning Ray", true, 5, 4, 4, 3, null);
		default:
			return null;
		}
		
	}

	@Override
	public int getHpDie() {

		return 6;
	}

}
