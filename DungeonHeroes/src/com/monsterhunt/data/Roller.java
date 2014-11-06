package com.monsterhunt.data;

import java.util.Random;

public class Roller {
	private static Random random = new Random();

	public static int randomInt(int number){
		return random.nextInt(number);
	}
	
	public static int roll(int faces) {
		return random.nextInt(faces) + 1;
	}

	public static int roll(int number, int faces) {
		int sum = 0;
		while (number > 0) {
			sum += roll(faces);
			number--;
		}
		return sum;
	}
	public static int roll(int number,int faces,int modifier){
		
		return roll(number,faces) + modifier;
	}
}
