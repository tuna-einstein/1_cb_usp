package com.winfiny.cb;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.winfiny.cb.model.Game;

import net.jeremybrooks.knicker.dto.Definition;

import java.util.List;

public class CreateGameActivity extends ActionBarActivity implements
		OnSeekBarChangeListener {
	private EditText text1;
	private EditText text3;
	private EditText text2;
	private EditText text4;
	private Button button1;
	private ProgressBar spinner1;
	private TextView text_max_attempts_label;

@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_game_options);

		SeekBar seekbar = (SeekBar) findViewById(R.id.seekbar_max_attempts);
		seekbar.setProgress(5);

		text_max_attempts_label = (TextView) findViewById(R.id.text_max_attempts);
		text_max_attempts_label.setText("5");
		seekbar.setOnSeekBarChangeListener(this);
		button1 = (Button) findViewById(R.id.button1);
		spinner1 = (ProgressBar) findViewById(R.id.progressBar1);
		spinner1.setVisibility(View.GONE);
		text1 = (EditText) findViewById(R.id.input_1);		
		text1.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String str = text1.getText().toString();
				if(str.length() > 0){
					text2.requestFocus();
					text4.clearFocus();
					text3.clearFocus();
					text1.clearFocus();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {				
			}
		});
		text2 = (EditText) findViewById(R.id.input_2);
		text2.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				String str = text2.getText().toString();
				if(str.equals("")){
					if(keyCode == KeyEvent.KEYCODE_DEL){
						text1.requestFocus();
					}
				}
				return false;
			}
		});
		text2.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String str = text2.getText().toString();
				if(str.length() > 0){
					text3.requestFocus();
					text4.clearFocus();
					text2.clearFocus();
					text1.clearFocus();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {				
			}
		});
		text3 = (EditText) findViewById(R.id.input_3);
		text3.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				String str = text3.getText().toString();
				if(str.equals("")){
					if(keyCode == KeyEvent.KEYCODE_DEL){
						text2.requestFocus();
					}
				}
				return false;
			}
		});
		text3.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String str = text3.getText().toString();
				if(str.length() > 0){
					text4.requestFocus();
					text3.clearFocus();
					text2.clearFocus();
					text1.clearFocus();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {				
			}
		});
		text4 = (EditText) findViewById(R.id.input_4);
		text4.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				String str = text4.getText().toString();
				if(str.equals("")){
					if(keyCode == KeyEvent.KEYCODE_DEL){
						text3.requestFocus();
					}
				}
				return false;
			}
		});
		text4.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String str = text4.getText().toString();
				if(str.length() > 0){
					text4.clearFocus();
					text3.clearFocus();
					text2.clearFocus();
					text1.clearFocus();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {				
			}
		});
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendInput();
			}
		});
	}

	@Override
	public void onProgressChanged(SeekBar v, int progress, boolean isUser) {
		text_max_attempts_label.setText(Integer.toString(progress));
	}


	public void sendInput() {
		String input = text1.getText().toString() + text2.getText().toString()
				+ text3.getText().toString() + text4.getText().toString();

		int maxAttempts = Integer.parseInt(text_max_attempts_label.getText().toString());
		List<Definition> result;
		try {
			result = new WordValidationTask().execute(input).get();
			if(result != null && result.size() > 0){
				new Game(input, maxAttempts).persistGame();
				finish();
			} else {
				text1.setError(input + " is not a valid word!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			text1.setError(input + " - Word couldn't be verified. Try again later!");
		}
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
}
