package com.monsterhunt.activities;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;

import com.monsterhunt.data.ClassFighter;
import com.monsterhunt.data.ClassWizard;
import com.monsterhunt.data.GameCharacter;
import com.monsterhunt.data.GameClass;
import com.monsterhunt.system.CharacterManager;

@SuppressLint("InflateParams")
public class MainActivity extends ActionBarActivity {
	public static final String TAG = "DungeonHeroes";
	private LinearLayout mainLayout;
	private static List<GameCharacter> characters;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mainLayout = (LinearLayout) findViewById(R.id.main_list);

		updateUi();
	}

	private void createNewCharDialogue() {
		CreateCharDialog cd = new CreateCharDialog();
		cd.show(getFragmentManager(), "AIAI");
	}

	private class CreateCharDialog extends DialogFragment {
		private GameClass gClass;

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			// Get the layout inflater
			LayoutInflater inflater = getActivity().getLayoutInflater();

			LinearLayout dialogLayout = (LinearLayout) inflater.inflate(
					R.layout.character_creation, null);
			final EditText charName = (EditText) dialogLayout
					.findViewById(R.id.character_name_input);
			final Spinner charClass = (Spinner) dialogLayout
					.findViewById(R.id.character_class_input);
			// Create an ArrayAdapter using the string array and a default
			// spinner layout
			ArrayAdapter<CharSequence> adapter = ArrayAdapter
					.createFromResource(getActivity(),
							R.array.game_classes,
							android.R.layout.simple_spinner_item);
			// Specify the layout to use when the list of choices appears
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			// Apply the adapter to the spinner
			charClass.setAdapter(adapter);
			charClass.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					switch (arg2) {
					case 0:
						gClass = new ClassFighter();
						break;
					case 1:
						gClass = new ClassWizard();
						break;

					default:
						break;
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {

				}

			});
			builder.setView(dialogLayout)
					// Add action buttons
					.setPositiveButton("Create",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int id) {
									GameCharacter gc = new GameCharacter(
											charName.getText().toString(),
											gClass, 0, 0, 0);
									characters.add(gc);
									CharacterManager
											.save(getApplicationContext(),characters);
									updateUi();
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									CreateCharDialog.this.getDialog().cancel();
								}
							});
			return builder.create();
		}
	}

	public void updateUi() {
		characters = CharacterManager.load(this);
		mainLayout.removeAllViews();

		for (final GameCharacter gc : characters) {
			
			LinearLayout charItem = (LinearLayout) getLayoutInflater().inflate(
					R.layout.character_item, mainLayout, false);
			TextView charName = (TextView) charItem
					.findViewById(R.id.character_name);
			charName.setText(gc.getName());
			TextView charInfo = (TextView) charItem
					.findViewById(R.id.character_info);
			charInfo.setText(gc.getCharClass().getName() + " (" + gc.getLevel()
					+ ")");
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.bottomMargin = 10;
			params.leftMargin = 10;
			params.topMargin = 10;
			params.rightMargin = 10;
			charItem.setLayoutParams(params);
			charItem.setPadding(20, 20, 20, 20);
			charItem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent it = new Intent(getApplicationContext(),
							GameActivity.class);
					Bundle extra = new Bundle();
					extra.putSerializable("char", gc);
					it.putExtras(extra);
					startActivity(it);
				}
			});
			mainLayout.addView(charItem);
		}

		Button newChar = new Button(this);
		newChar.setText("Create Character");
		newChar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				createNewCharDialogue();
			}

		});

		mainLayout.addView(newChar);
		
	}
	
	public static List<GameCharacter> getCharacters(){
		return characters;
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateUi();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_clear_char) {
			characters = new ArrayList<GameCharacter>();
			CharacterManager.save(getApplicationContext(),characters);
			updateUi();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
