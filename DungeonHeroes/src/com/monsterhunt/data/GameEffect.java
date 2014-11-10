package com.monsterhunt.data;

public class GameEffect {
	public static final String DOT = "damage_over_time_effect";
	public static final String SLOW = "slow_effect";
	public static final String STUN = "stun_effect";

	private String skillName;
	private String effectName;
	private int duration;
	private int power;

	public GameEffect(String skillName, String effectName, int duration,
			int power) {
		super();
		this.skillName = skillName;
		this.effectName = effectName;
		this.duration = duration;
		this.power = power;
	}

	public String getSkillName() {
		return this.skillName;
	}

	public void use() {
		duration--;
	}

	public int getDuration() {
		return duration;
	}

	public boolean is(String effect) {
		if (effectName.equals(effect))
			return true;
		return false;
	}

	public int getDmg() {
		return power;
	}
}
