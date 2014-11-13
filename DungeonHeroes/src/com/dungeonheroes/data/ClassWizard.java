package com.dungeonheroes.data;

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
			return new GameAction("Mystic Darts", true, 2, 4, 4, 0, null);
		case 2:
			return new GameAction("Melf's Acid Arrow", true, 3, 10, 2, 5, null);
		case 4:
			return new GameAction("Energy Ray", true, 3, 6, 2, 0, null);
		case 6:
			return new GameAction("Fire Ball", true, 6, 6, 2, 5, null);
		case 9:
			return new GameAction("Magic Missile", true, 5, 4, 2, 0, null);
		case 12:
			return new GameAction("Frostbite", true, 5, 8, 3, 5, null);
		case 15:
			return new GameAction("Burning Hands", true, 4, 10, 2, 0, null);
		case 20:
			return new GameAction("Necrotic Ray", true, 8, 10, 5, 3, null);
		default:
			return null;
		}
		
	}

	@Override
	public int getHpDie() {

		return 6;
	}

}
