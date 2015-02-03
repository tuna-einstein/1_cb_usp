package com.winfiny.cb.model;

import android.os.AsyncTask;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class Game {

    String word;
    int chances;
    String ownerId;
    String ownerUsername;

    public static final String OWNER_USERNAME = "OwnerUsername";
    public static final String WORD = "word";
    public static final String OWNER_ID = "OwnerId";
    public static final String CHANCES = "Chances";

    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public int getChances() {
        return chances;
    }
    public void setChances(int chances) {
        this.chances = chances;
    }
    public String getOwnerId() {
        return ParseUser.getCurrentUser().getObjectId();
    }
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
    public String getOwnerUsername() {
        return ParseUser.getCurrentUser().getUsername();
    }
    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public Game(String word, int chances) {
        super();
        this.word = word;
        this.chances = chances;
    }

    public Game() {
    }

    public void persistGame(){
        new RemoteDataTask().execute();
    }

    public static Game fromParseObject(ParseObject p){
        Game g = new Game();
        g.setChances(p.getInt(CHANCES));
        g.setWord(p.get(WORD).toString());
        g.setOwnerId(p.get(OWNER_ID).toString());
        g.setOwnerUsername(p.get(OWNER_USERNAME).toString());
        return g;
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... params) {
            ParseObject game = new ParseObject("GamesList");
            game.put(CHANCES, getChances());
            game.put(OWNER_ID, getOwnerId());
            game.put(WORD, getWord());
            game.put(OWNER_USERNAME, getOwnerUsername());
            try {
                game.save();
            } catch (ParseException e) {
            }
            return null;
        }
    }
}
