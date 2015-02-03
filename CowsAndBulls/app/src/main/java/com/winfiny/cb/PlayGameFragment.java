package com.winfiny.cb;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.winfiny.cb.model.Prediction;

import net.jeremybrooks.knicker.dto.Definition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PlayGameFragment extends Fragment {

	private ListView mListView;
	public List<Prediction> mList;
	private ItemsListAdapter mAdapter;
	private ProgressDialog mDialog;
	private EditText input1;
	private EditText input2;
	private EditText input3;
	private EditText input4;
	private Button button;
	private String mWord;
	private int mChances;
	private AlertDialog mAlert;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setRetainInstance(true);
		View v = inflater.inflate(R.layout.play_game, container);
		input1 = (EditText) v.findViewById(R.id.input_1);
		input1.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if((start+count) - (start+before) >= 0){
					input2.requestFocus();
				}else if((start+count) - (start+before) < 0){
					
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
		input2 = (EditText) v.findViewById(R.id.input_2);
		input2.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if((start+count) - (start+before) >= 0){
					input3.requestFocus();
				}else if((start+count) - (start+before) < 0){
					input1.requestFocus();
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
		input3 = (EditText) v.findViewById(R.id.input_3);
		input3.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if((start+count) - (start+before) >= 0){
					input4.requestFocus();
				}else if((start+count) - (start+before) < 0){
					input2.requestFocus();
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
		input4 = (EditText) v.findViewById(R.id.input_4);
		input4.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if((start+count) - (start+before) >= 0){
					input1.clearFocus();
					input2.clearFocus();
					input3.clearFocus();
					input4.clearFocus();
				}else if((start+count) - (start+before) < 0){
					input3.requestFocus();
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
		button = (Button) v.findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String guess = input1.getText().toString() + input2.getText().toString() + input3.getText().toString() + input4.getText().toString();
				List<Definition> result;
				try {
					result = new WordValidationTask().execute(guess).get();
					if(result != null && result.size() > 0){
						input1.setError(null);
						input1.setText("");input2.setText("");input3.setText("");input4.setText("");input1.requestFocus();
						Prediction p = getAttemptResult(mWord, guess);
						mList.add(0, p);
						mAdapter.notifyDataSetChanged();
						if(guess.equalsIgnoreCase(mWord)){
							if(mList.size() < 2){
								showMessage(getString(R.string.congrats), getString(R.string.psychic));
							}else{
								showMessage(getString(R.string.congrats), getString(R.string.game_over_won, mList.size()));
							}
							button.setVisibility(View.GONE);
						}else if(mList.size() >= mChances){
							showMessage(getString(R.string.game_over), getString(R.string.game_over_attempts, mWord));
							button.setVisibility(View.GONE);
						}
					} else {
						input1.setError(guess + " is not a valid word!");
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		mListView = (ListView) v.findViewById(android.R.id.list);

		mList = new ArrayList<Prediction>();
		mAdapter = new ItemsListAdapter(getActivity(), mList);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {				
			}
		});
		setHasOptionsMenu(true);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public void showMessage(String title, String message){
		Context ctx = getActivity();
		mAlert = new AlertDialog.Builder(ctx)
		.setTitle(title)
		.setMessage(message)
		.setPositiveButton(android.R.string.ok, null)
		.show();
	}
	
	public void hideMessage(){
		if(mAlert != null){
			mAlert.dismiss();
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onDestroy() {
		hideProgress();
		hideMessage();
		super.onDestroy();
	}
	
	public void showProgress(){
		if(mDialog == null){
			mDialog = new ProgressDialog(getActivity());
		}
		mDialog.setMessage("Loading...");
		mDialog.show();
	}
	
	public void hideProgress(){
		if(mDialog != null){
			mDialog.dismiss();
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

/*		case R.id.refresh:
			return true;
		case R.id.create_game:
			Intent i = new Intent(getActivity(), CreateGameActivity.class);
			getActivity().startActivity(i);
			return true;
		case R.id.logout:
			return true;*/

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
//		inflater.inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
		
	private class ItemsListAdapter extends ArrayAdapter<Prediction> {

		private List<Prediction> mObjects;

		public ItemsListAdapter(Context context, List<Prediction> objects) {
			super(context, R.layout.group_list_item, android.R.id.text1,
					objects);
			mObjects = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder;

			// Check if the incoming view is null.
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) getContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.play_game_list_item,
						parent, false);
				holder = new ViewHolder();
				holder.name = (TextView) convertView
						.findViewById(R.id.list_item_name);
				holder.desc = (TextView) convertView
						.findViewById(R.id.list_item_desc);
				holder.bulls = (TextView) convertView
						.findViewById(R.id.list_item_bulls);
				holder.cows = (TextView) convertView
						.findViewById(R.id.list_item_cows);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Prediction obj;
			if (position < mObjects.size()) {
				obj = mObjects.get(position);
			} else {
				return convertView;
			}
			final Prediction object = obj;

			holder.name.setSingleLine(true);

			holder.name.setText(object.getWord());
			holder.bulls.setText("B: "+object.getBulls());
			holder.cows.setText("C: "+object.getCows());
//			holder.desc.setText(object.get(Game.WORD).toString());
//			holder.price.setText(object.get(Game.CHANCES).toString());

			return convertView;
		}

	}
	static class ViewHolder {
		TextView bulls;
		TextView name;
		TextView desc;
		TextView cows;
	}
	
	public Prediction getAttemptResult(String word, String guess){

		HashMap<Character, ArrayList<Integer>> dataStructure = new HashMap<Character, ArrayList<Integer>>();

		for (int i = 0; i < word.length(); i++) {
			Character c = word.charAt(i);
			if (dataStructure.get(c) == null) {
				dataStructure.put(c, new ArrayList<Integer>());
			}
			dataStructure.get(c).add(i);
		}

		HashMap<Character, ArrayList<Integer>> data = (HashMap<Character, ArrayList<Integer>>) dataStructure.clone();
		int bulls = 0;
		int cows = 0;
		for (int i = 0; i < guess.length(); i++) {
			Character c = guess.charAt(i);
			if (data.containsKey(c)) {
				ArrayList<Integer> list = (ArrayList<Integer>) dataStructure.get(c).clone();
				if (list.contains(i)) {
					bulls++;
				} else {
					cows++;
				}
				list.remove(new Integer(i));
				if (list.isEmpty()) {
					data.remove(c);
				}
			}
		}
		return new Prediction(bulls, cows, guess);
	}

	public void setWord(String word) {
		mWord = word;
	}

	public void setChances(int chances) {
		mChances = chances;		
	}
}
