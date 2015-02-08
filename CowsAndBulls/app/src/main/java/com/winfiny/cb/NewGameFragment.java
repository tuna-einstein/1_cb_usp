package com.winfiny.cb;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.winfiny.cb.model.Prediction;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.sephiroth.android.library.widget.HListView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.winfiny.cb.NewGameFragment.OnNewGameFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewGameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    @InjectView(R.id.hListView1) HListView mListView;

    private OnNewGameFragmentInteractionListener mListener;
    private ArrayList<String> mList;
    private LetterAdapter mLetterAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewGameFragment newInstance(String param1, String param2) {
        NewGameFragment fragment = new NewGameFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    public NewGameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_game, container, false);
        ButterKnife.inject(this, v);

        mList = new ArrayList<String>();
        mList.add("W");
        mList.add("O");
        mList.add("R");
        mList.add("D");
        mLetterAdapter = new LetterAdapter(getActivity(), R.layout.item_char_list, mList);
        mListView.setAdapter(mLetterAdapter);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int primaryCode) {
        switch(primaryCode){
            case Keyboard.KEYCODE_DELETE :
//                deleteSurroundingText(1, 0);
                mLetterAdapter.remove(mList.get(mList.size()-1));
                break;
            case Keyboard.KEYCODE_DONE:
//                sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            default:
                char code = (char)primaryCode;
                if(Character.isLetter(code)){
                    code = Character.toUpperCase(code);
                }
                mLetterAdapter.add(String.valueOf(code));
//                commitText(String.valueOf(code),1);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnNewGameFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnNewGameFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    class LetterAdapter extends ArrayAdapter<String> {

        List<String> mObjects;

        public LetterAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;

            // Check if the incoming view is null.
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_char_list,
                        parent, false);
                holder = new ViewHolder();
                holder.letter = (TextView) convertView
                        .findViewById(R.id.tvItemChar);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String obj;
            if (position < mObjects.size()) {
                obj = mObjects.get(position);
            } else {
                return convertView;
            }

            holder.letter.setText(obj);

            return convertView;
        }

    }
    static class ViewHolder {
        TextView letter;
    }

}
