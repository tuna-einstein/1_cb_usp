package com.winfiny.cb;

import android.os.AsyncTask;

import net.jeremybrooks.knicker.AccountApi;
import net.jeremybrooks.knicker.KnickerException;
import net.jeremybrooks.knicker.WordApi;
import net.jeremybrooks.knicker.dto.Definition;
import net.jeremybrooks.knicker.dto.TokenStatus;

import java.util.List;

public class WordValidationTask extends AsyncTask<String, Void, List<Definition>> {

	@Override
	protected void onPostExecute(List<Definition> result) {
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected List<Definition> doInBackground(String... params) {
		String word = params[0];
		
		System.setProperty("WORDNIK_API_KEY", "443dcfb17ca53d14d5009003b4109f8648d5338115d253d01");

		try {
			
		TokenStatus status = AccountApi.apiTokenStatus();
		if (status.isValid()) {
		} else {
			return null;
		}

		return WordApi.definitions(word);
		
		} catch (KnickerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}