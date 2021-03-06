package com.winfiny.cb;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import com.winfiny.cb.model.Game;

public class PlayGameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_game);
		FragmentManager fm = getFragmentManager();
		if (fm.findFragmentById(R.id.games_container) == null) {
			PlayGameFragment game = new PlayGameFragment();
			Intent intent = getIntent();
			String word = intent.getStringExtra(Game.WORD);
			game.setWord(word);
			int chances = intent.getIntExtra(Game.CHANCES, 5);
			game.setChances(chances);
			fm.beginTransaction().add(R.id.games_container, game).commit();
		}
	}
}
