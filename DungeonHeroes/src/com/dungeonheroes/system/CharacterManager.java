package com.dungeonheroes.system;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.dungeonheroes.data.GameCharacter;

public class CharacterManager {
	private static final String FILENAME = "characters";
	
	public static void save(Context context, List<GameCharacter> list) {
		try {
			FileOutputStream foo = context.openFileOutput(FILENAME,
					Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(foo);
			oos.writeObject(list);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<GameCharacter> load(Context context) {
		try {
			InputStream is = context.openFileInput(FILENAME);
			InputStream buffer = new BufferedInputStream(is);
			ObjectInputStream ois = new ObjectInputStream(buffer);
			List<GameCharacter> newList = (List<GameCharacter>) ois.readObject();
			ois.close();
			return newList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<GameCharacter>();
	}
}
