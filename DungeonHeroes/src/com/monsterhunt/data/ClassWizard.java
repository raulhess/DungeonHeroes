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
			return new GameAction("Magic Missiles", true, 1, 8, 0, 0);
		case 2:
			return new GameAction("Fire Ball", true, 2, 6, 0, 1);
		case 3:
			return new GameAction("Melf's Acid Arrow", true, 2, 4, 2, 1);
		case 5:
			return new GameAction("Burning Ray", true, 3, 6, 0, 3);
		default:
			return null;
		}
		
	}

	@Override
	public int getHpDie() {

		return 6;
	}

}
