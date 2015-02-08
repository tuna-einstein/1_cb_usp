package com.winfiny.cb;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.winfiny.cb.model.Game;

import java.util.ArrayList;
import java.util.List;

public class GamesListFragment extends Fragment {

    private ListView mListView;
    public List<ParseObject> mList;
    private ItemsListAdapter mAdapter;
    private ProgressDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.games_list, container);
        mListView = (ListView) v.findViewById(android.R.id.list);

        mList = new ArrayList<ParseObject>();
        mAdapter = new ItemsListAdapter(getActivity(), mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                ParseObject p = (ParseObject) mListView.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), PlayGameActivity.class);
                Game g = Game.fromParseObject(p);
                intent.putExtra(Game.WORD, g.getWord());
                intent.putExtra(Game.CHANCES, g.getChances());
                startActivity(intent);
            }
        });
        setHasOptionsMenu(true);
        refreshList();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void refreshList(){
        new RemoteDataTask().execute();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, List<ParseObject>> {
        // Override this method to do custom remote calls
        protected List<ParseObject> doInBackground(Void... params) {
            // Gets the current list of todos in sorted order
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("GamesList");
            query.orderByDescending("createdAt");

            try {
                return query.find();
            } catch (ParseException e) {

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            showProgress();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<ParseObject> result) {
            mList.clear();
            if (result != null) {
                mList.addAll(result);
            }
            mAdapter.notifyDataSetChanged();
            hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        hideProgress();
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

            case R.id.refresh:
                refreshList();
                return true;
            case R.id.create_game:
                Intent i = new Intent(getActivity(), NewGameActivity.class);
                getActivity().startActivity(i);
                return true;
            case R.id.logout:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private class ItemsListAdapter extends ArrayAdapter<ParseObject> {

        private List<ParseObject> mObjects;

        public ItemsListAdapter(Context context, List<ParseObject> objects) {
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
                convertView = inflater.inflate(R.layout.group_list_item,
                        parent, false);
                holder = new ViewHolder();
                holder.name = (TextView) convertView
                        .findViewById(R.id.list_item_name);
                holder.desc = (TextView) convertView
                        .findViewById(R.id.list_item_desc);
                holder.price = (TextView) convertView
                        .findViewById(R.id.list_item_chances);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ParseObject obj;
            if (position < mObjects.size()) {
                obj = mObjects.get(position);
            } else {
                return convertView;
            }
            final ParseObject object = obj;

            holder.name.setSingleLine(true);

            holder.name.setText(object.get(Game.OWNER_USERNAME).toString());
//            holder.desc.setText(object.get(Game.WORD).toString());
            holder.price.setText(object.get(Game.CHANCES).toString());

            return convertView;
        }

    }
    static class ViewHolder {
        TextView price;
        TextView name;
        TextView desc;
    }
}
