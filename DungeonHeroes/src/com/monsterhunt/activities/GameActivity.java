package com.monsterhunt.activities;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.monsterhunt.data.DungeonGenerator;
import com.monsterhunt.data.DungeonGoblinoid;
import com.monsterhunt.data.DungeonLizardfolk;
import com.monsterhunt.data.DungeonSnow;
import com.monsterhunt.data.DungeonUndead;
import com.monsterhunt.data.GameAction;
import com.monsterhunt.data.GameCharacter;
import com.monsterhunt.data.GameDungeon;
import com.monsterhunt.data.GameMonster;
import com.monsterhunt.data.Roller;
import com.monsterhunt.system.CharacterManager;

@SuppressLint({ "DefaultLocale", "InflateParams" })
public class GameActivity extends Activity {
	private static DungeonGenerator generator;
	private static GameCharacter activeChar;
	private static GameDungeon dungeon;
	private static GameMonster activeMonster;

	// Dialogs
	private CharacterDetailDialog characterDetailDialog;
	private NextLevelDialog nextLevelDialog;
	private DeathDialog deathDialog;

	// Cover
	private TextView cover;

	// Monster Layout
	private LinearLayout mLayout;
	private ImageView mPortrait;
	private TextView mName;
	private ProgressBar mHealth;
	private TextView mHealthDisplay;

	// Log
	private ScrollView scroll;
	private TextView log;

	// Character Layout
	private LinearLayout cLayout;
	private TextView cName;
	private ProgressBar cHealth;
	private TextView cHealthDisplay;
	private LinearLayout cActions;
	private LinearLayout actionA;
	private LinearLayout actionB;
	private LinearLayout actionC;
	private LinearLayout actionD;

	private Handler charHandler;
	private Runnable saveChar = new Runnable() {

		@Override
		public void run() {
			saveActiveChar();
			charHandler.postDelayed(this, 1000);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		Intent it = getIntent();

		cover = (TextView) findViewById(R.id.cover);
		cover.setTypeface(Typeface.createFromAsset(getAssets(),
				"fonts/narkisin.ttf"));
		cover.setText("Dungeon");

		mLayout = (LinearLayout) findViewById(R.id.game_monster_layout);
		mPortrait = (ImageView) findViewById(R.id.game_monster_img);
		mName = (TextView) findViewById(R.id.monster_name);
		mHealth = (ProgressBar) findViewById(R.id.monster_health);
		mHealthDisplay = (TextView) findViewById(R.id.monster_health_display);

		scroll = (ScrollView) findViewById(R.id.log_scroll);
		log = (TextView) findViewById(R.id.game_log);
		log.setText("");

		cLayout = (LinearLayout) findViewById(R.id.game_character_layout);
		cName = (TextView) findViewById(R.id.char_name);
		cHealth = (ProgressBar) findViewById(R.id.char_health);
		cHealthDisplay = (TextView) findViewById(R.id.char_health_display);
		cActions = (LinearLayout) findViewById(R.id.char_actions);

		try {
			activeChar = (GameCharacter) it.getSerializableExtra("char");

			if (activeChar != null) {
				createNextDungeon();
				updateCharData();
				updateCharActions();
				nextRound();
				if (activeChar.getCurrentHp() <= 0) {
					openDeathScreen();
				}
				if (activeChar.getUnspentAttPoints() > 0) {
					openNewLevelScreen();
				}
				charHandler = new Handler();
				charHandler.postDelayed(saveChar, 1000);
				cLayout.setOnLongClickListener(new OnLongClickListener() {

					@Override
					public boolean onLongClick(View arg0) {
						openCharacterDetailScreen();
						return false;
					}
				});
				mLayout.setOnLongClickListener(new OnLongClickListener() {

					@Override
					public boolean onLongClick(View v) {
						return false;
					}
				});
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void createNextDungeon() {
		List<String> possibleDungeons = new ArrayList<String>();
		possibleDungeons.add(GameDungeon.FOREST);
		possibleDungeons.add(GameDungeon.LIZARDFOLK);
		if (activeChar.getLevel() > 4) {
			possibleDungeons.add(GameDungeon.UNDEAD);
		}

		String dungeonType = possibleDungeons.get(Roller
				.randomInt(possibleDungeons.size()));
		generator = null;

		switch (dungeonType) {
		case GameDungeon.FOREST:
			generator = new DungeonGoblinoid();
			cover.setBackground(getResources().getDrawable(
					R.drawable.cover_forest_b));
			break;
		case GameDungeon.LIZARDFOLK:
			generator = new DungeonLizardfolk();
			cover.setBackground(getResources().getDrawable(
					R.drawable.cover_swamp_a));
			break;
		case GameDungeon.UNDEAD:
			generator = new DungeonUndead();
			cover.setBackground(getResources().getDrawable(
					R.drawable.cover_undead_a));
			break;
		case GameDungeon.SNOW:
			generator = new DungeonSnow();
			cover.setBackground(getResources().getDrawable(
					R.drawable.cover_snow_a));
			break;
		}
		if (generator != null) {
			dungeon = generator.generateDungeon(activeChar.getLevel());
			cover.setText(dungeon.getName() + " (" + dungeon.getDifficulty()
					+ ")");
		}
	}

	private void nextRound() {
		activeMonster = dungeon.getNextMonster();
		if (activeMonster == null) {
			GameAction newAction = activeChar.levelUp();
			if (newAction != null) {
				if (activeChar.getActionB() == null)
					activeChar.setActionB(newAction);
				else if (activeChar.getActionC() == null)
					activeChar.setActionC(newAction);
				else if (activeChar.getActionD() == null)
					activeChar.setActionD(newAction);
				else
					activeChar.setUnassignedAction(newAction);
			}
			updateCharActions();
			openNewLevelScreen();
			int healAmount = activeChar.takeShortRest();
			addLogText(activeChar.getName() + " rested, recovering "
					+ healAmount + " health points");
			createNextDungeon();
			activeMonster = dungeon.getNextMonster();
		}
		String assetFile = "monsters/" + activeMonster.getName().toLowerCase();
		assetFile = assetFile.replace(' ', '_');
		assetFile += ".jpg";
		mPortrait.setImageBitmap(getBitmapFromAsset(assetFile));
		mName.setText(activeMonster.getName() + " (" + dungeon.getDifficulty()
				+ ")");
		mHealth.setMax(activeMonster.getHp());
		updateMonsterData();
		addLogText("---------- # ----------");
		addLogText(activeMonster.getName() + " appeared in the dungeon!");
	}

	private void openCharacterDetailScreen() {
		if (characterDetailDialog == null)
			characterDetailDialog = new CharacterDetailDialog();
		if(activeChar != null)
			characterDetailDialog.show(getFragmentManager(), MainActivity.TAG);
	}

	private void openNewLevelScreen() {
		if (nextLevelDialog == null)
			nextLevelDialog = new NextLevelDialog();
		nextLevelDialog.setCancelable(false);
		nextLevelDialog.show(getFragmentManager(), MainActivity.TAG);
	}

	private void openDeathScreen() {
		if (deathDialog == null)
			deathDialog = new DeathDialog();
		deathDialog.setCancelable(false);
		deathDialog.show(getFragmentManager(), MainActivity.TAG);
	}

	private class NextLevelDialog extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			// Get the layout inflater
			LayoutInflater inflater = getActivity().getLayoutInflater();

			LinearLayout dialogLayout = (LinearLayout) inflater.inflate(
					R.layout.new_level_layout, null);
			TextView title = (TextView) dialogLayout
					.findViewById(R.id.new_level_title);
			TextView info = (TextView) dialogLayout
					.findViewById(R.id.new_level_info);
			Button addStr = (Button) dialogLayout
					.findViewById(R.id.new_level_str);
			Button addDex = (Button) dialogLayout
					.findViewById(R.id.new_level_dex);
			Button addInt = (Button) dialogLayout
					.findViewById(R.id.new_level_int);
			title.setText("Level Up!");
			title.setTypeface(Typeface.createFromAsset(getAssets(),
					"fonts/narkisin.ttf"));
			info.setText("Choose an attribute to increase:");
			addStr.setText("Strength (" + activeChar.getStrength() + ")");
			addStr.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					activeChar.addStr();
					saveActiveChar();
					updateCharActions();
					nextLevelDialog.dismiss();
				}
			});
			addDex.setText("Dexterity (" + activeChar.getDexterity() + ")");
			addDex.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					activeChar.addDex();
					saveActiveChar();
					updateCharActions();
					nextLevelDialog.dismiss();
				}
			});
			addInt.setText("Intelligence (" + activeChar.getIntelligence()
					+ ")");
			addInt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					activeChar.addIntel();
					saveActiveChar();
					updateCharActions();
					nextLevelDialog.dismiss();
				}
			});
			builder.setView(dialogLayout);
			return builder.create();
		}
	}

	private class DeathDialog extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			// Get the layout inflater
			LayoutInflater inflater = getActivity().getLayoutInflater();

			LinearLayout dialogLayout = (LinearLayout) inflater.inflate(
					R.layout.death_layout, null);
			TextView title = (TextView) dialogLayout
					.findViewById(R.id.death_title);
			TextView info = (TextView) dialogLayout
					.findViewById(R.id.death_info);
			Button restart = (Button) dialogLayout
					.findViewById(R.id.death_restart);
			Button remove = (Button) dialogLayout
					.findViewById(R.id.death_remove);
			title.setText(activeChar.getName() + " Died");
			title.setTypeface(Typeface.createFromAsset(getAssets(),
					"fonts/narkisin.ttf"));
			info.setText("Your character died. Would you like to reincarnate or rest forever?");
			restart.setText("Reincarnate");
			restart.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					activeChar.reincarnate();
					saveActiveChar();
					createNextDungeon();
					updateCharData();
					updateCharActions();
					nextRound();
					deathDialog.dismiss();
				}
			});
			remove.setText("Die");
			remove.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					deleteActiveChar();
					deathDialog.dismiss();
				}

			});
			builder.setView(dialogLayout);
			return builder.create();
		}
	}

	private class CharacterDetailDialog extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			// Get the layout inflater
			LayoutInflater inflater = getActivity().getLayoutInflater();

			LinearLayout dialogLayout = (LinearLayout) inflater.inflate(
					R.layout.character_detail_layout, null);
			TextView title = (TextView) dialogLayout
					.findViewById(R.id.char_detail_title);
			TextView info = (TextView) dialogLayout
					.findViewById(R.id.char_detail_class);
			TextView date = (TextView) dialogLayout
					.findViewById(R.id.char_detail_date);
			((TextView) dialogLayout.findViewById(R.id.detail_hp)).setText(activeChar
					.getHp() + "");
			((TextView) dialogLayout.findViewById(R.id.detail_hp_perm)).setText(activeChar
					.getpHp() + "");
			((TextView) dialogLayout.findViewById(R.id.detail_str)).setText(activeChar
					.getStr() + "");
			((TextView) dialogLayout.findViewById(R.id.detail_str_perm)).setText(activeChar
					.getpStr() + "");
			((TextView) dialogLayout.findViewById(R.id.detail_dex)).setText(activeChar
					.getDex() + "");
			((TextView) dialogLayout.findViewById(R.id.detail_dex_perm)).setText(activeChar
					.getpDex() + "");
			((TextView) dialogLayout.findViewById(R.id.detail_int)).setText(activeChar
					.getIntel() + "");
			((TextView) dialogLayout.findViewById(R.id.detail_int_perm)).setText(activeChar
					.getpIntel() + "");

			title.setText(activeChar.getName());
			title.setTypeface(Typeface.createFromAsset(getAssets(),
					"fonts/narkisin.ttf"));
			info.setText(activeChar.getCharClass().getName() + " ("
					+ activeChar.getLevel() + ")");
			String dateString = new SimpleDateFormat("dd/MM/yy",Locale.ROOT).format(activeChar.getDate());
			date.setText("Created on: " + dateString);
			builder.setView(dialogLayout);
			return builder.create();
		}
	}

	private void addLogText(String text) {
		log.setText(log.getText() + "\r\n " + text);
		scroll.post(new Runnable() {
			@Override
			public void run() {
				scroll.fullScroll(View.FOCUS_DOWN);
			}
		});
	}

	private void updateMonsterData() {
		if (dungeon.hasMonsters())
			mName.setTextColor(getResources().getColor(android.R.color.black));
		else
			mName.setTextColor(getResources().getColor(R.color.red));
		mHealth.setProgress(activeMonster.getCurrentHp());
		mHealthDisplay.setText(activeMonster.getCurrentHp() + " / "
				+ activeMonster.getHp());
	}

	private void updateCharData() {
		cName.setText(activeChar.getName() + " (" + activeChar.getLevel() + ")");
		cHealth.setMax(activeChar.getHp());
		cHealth.setProgress(activeChar.getCurrentHp());
		cHealthDisplay.setText(activeChar.getCurrentHp() + " / "
				+ activeChar.getHp());
	}

	private void updateCharActions() {
		cActions.removeAllViews();
		actionA = (LinearLayout) getLayoutInflater().inflate(
				R.layout.char_action_item, cActions, false);
		setAction(actionA, activeChar.getActionA());
		cActions.addView(actionA);
		actionB = (LinearLayout) getLayoutInflater().inflate(
				R.layout.char_action_item, cActions, false);
		setAction(actionB, activeChar.getActionB());
		cActions.addView(actionB);
		actionC = (LinearLayout) getLayoutInflater().inflate(
				R.layout.char_action_item, cActions, false);
		setAction(actionC, activeChar.getActionC());
		cActions.addView(actionC);
		actionD = (LinearLayout) getLayoutInflater().inflate(
				R.layout.char_action_item, cActions, false);
		setAction(actionD, activeChar.getActionD());
		cActions.addView(actionD);
	}

	private void setAction(LinearLayout layout, final GameAction ga) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.bottomMargin = 10;
		params.leftMargin = 10;
		params.topMargin = 10;
		params.rightMargin = 10;
		layout.setLayoutParams(params);

		if (ga != null) {
			TextView actionName = (TextView) layout
					.findViewById(R.id.action_name);
			actionName.setText(ga.getName());

			TextView actionDmg = (TextView) layout
					.findViewById(R.id.action_dmg);
			if (ga.isMagical())
				actionDmg.setText(ga.getDmgString()
						+ (ga.getModifier() + activeChar.getIntelligence()));
			else
				actionDmg.setText(ga.getDmgString()
						+ (ga.getModifier() + activeChar.getStrength()));

			TextView actionCd = (TextView) layout
					.findViewById(R.id.action_cooldown);
			actionCd.setText(ga.getCooldown() + "");

			layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					resolveAttack(ga);
				}

			});
		}
	}

	private void resolveAttack(GameAction ga) {
		disableActions();

		int cInit = Roller.roll(20);
		int mInit = Roller.roll(20);
		while (cInit == mInit) {
			cInit = Roller.roll(20);
			mInit = Roller.roll(20);
		}
		if (cInit > mInit) {
			addLogText(activeChar.getName() + " has the initiative!");
			playerTurn(ga);
			if (activeMonster.getCurrentHp() > 0) {
				monsterTurn();
				if (activeChar.getCurrentHp() <= 0) {
					openDeathScreen();
				}
			} else {
				addLogText(activeMonster.getName() + " died");
				nextRound();
			}
		} else if (cInit < mInit) {
			addLogText(activeMonster.getName() + " has the initiative!");
			monsterTurn();
			if (activeChar.getCurrentHp() > 0) {
				playerTurn(ga);
				if (activeMonster.getCurrentHp() <= 0) {
					addLogText(activeMonster.getName() + " died");
					nextRound();
				}
			} else {
				openDeathScreen();
			}
		}

		updateCharData();
		updateMonsterData();
		cover.postDelayed(new Runnable() {

			@Override
			public void run() {
				enableActions();
			}
		}, 500);
	}

	private void playerTurn(GameAction ga) {
		int extraDmg = 0;
		if (ga.isMagical())
			extraDmg += activeChar.getIntelligence();
		else
			extraDmg += activeChar.getStrength();
		int mDamage = activeMonster.takeAtt(ga.isMagical(),
				activeChar.getLevel(), ga.getDmg() + extraDmg);
		if (mDamage == 0) {
			addLogText("You missed!");
		} else {
			addLogText("You dealt " + mDamage + " damage to "
					+ activeMonster.getName());
		}
		Log.d(MainActivity.TAG, "Monster took " + mDamage + " damage");
	}

	private void monsterTurn() {

		int cDamage = activeChar.takeAtt(activeMonster.isMagical(),
				activeMonster.getAttBonus(), activeMonster.getDamage());
		if (cDamage == 0) {
			addLogText(activeMonster.getName() + " missed!");
		} else {
			addLogText(activeMonster.getName() + " dealt " + cDamage
					+ " damage to you");
		}
		Log.d(MainActivity.TAG, "Player took " + cDamage + " damage");
	}

	private void disableActions() {
		actionA.setEnabled(false);
		actionB.setEnabled(false);
		actionC.setEnabled(false);
		actionD.setEnabled(false);
	}

	private void enableActions() {
		actionA.setEnabled(true);
		actionB.setEnabled(true);
		actionC.setEnabled(true);
		actionD.setEnabled(true);
	}

	private void saveActiveChar() {
		if (activeChar != null) {
			List<GameCharacter> list = MainActivity.getCharacters();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equals(activeChar))
					list.set(i, activeChar);
			}
			CharacterManager.save(getApplicationContext(), list);
		}
	}

	private void deleteActiveChar() {
		if (activeChar != null) {
			List<GameCharacter> list = MainActivity.getCharacters();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equals(activeChar)) {
					list.remove(i);
					break;
				}
			}
			CharacterManager.save(getApplicationContext(), list);
			Intent it = new Intent(this, MainActivity.class);
			startActivity(it);
			this.finish();
		}
	}

	private Bitmap getBitmapFromAsset(String strName) {
		AssetManager assetManager = getAssets();
		InputStream istr = null;
		try {
			istr = assetManager.open(strName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bitmap bitmap = BitmapFactory.decodeStream(istr);
		return bitmap;
	}

	@Override
	protected void onPause() {
		super.onPause();
		charHandler.removeCallbacks(saveChar);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		// int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}
}