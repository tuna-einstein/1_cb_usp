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
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.winfiny.cb.model.Game;
import com.winfiny.cb.model.Prediction;

import net.jeremybrooks.knicker.dto.Definition;

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
public class NewGameFragment extends Fragment implements View.OnFocusChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    //    @InjectView(R.id.hListView1) HListView mListView;
    @InjectView(R.id.sbAttempts)
    SeekBar mSeekBar;
    @InjectView(R.id.tvAttempts)
    TextView mTvAttempts;
    @InjectView(R.id.swTimer)
    Switch mSwTimer;
    @InjectView(R.id.tvLetter1)
    Button tvLetter1;
    @InjectView(R.id.tvLetter2)
    Button tvLetter2;
    @InjectView(R.id.tvLetter3)
    Button tvLetter3;
    @InjectView(R.id.tvLetter4)
    Button tvLetter4;

    private OnNewGameFragmentInteractionListener mListener;
//    private ArrayList<String> mList;
//    private LetterAdapter mLetterAdapter;

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

//        mList = new ArrayList<String>();
//        mList.add("W");
//        mList.add("O");
//        mList.add("R");
//        mList.add("D");
//        mLetterAdapter = new LetterAdapter(getActivity(), R.layout.item_char_list, mList);
//        mListView.setAdapter(mLetterAdapter);

        tvLetter1.setOnFocusChangeListener(this);
        tvLetter2.setOnFocusChangeListener(this);
        tvLetter3.setOnFocusChangeListener(this);
        tvLetter4.setOnFocusChangeListener(this);

        tvLetter1.requestFocus();
        mSeekBar.setProgress(0);
        mCurrentLetter = 0;
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress > 0) {
                    mTvAttempts.setText(String.valueOf(progress));
                } else {
                    mTvAttempts.setText(getString(R.string.label_attempts_unlimited));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return v;
    }

    int mCurrentLetter;

    public void setLetterText(String letter) {
//        if(tvLetter1.isFocused()){
//            tvLetter2.requestFocus();
//        }else if(tvLetter2.isFocused()){
//            tvLetter3.requestFocus();
//        }else if(tvLetter3.isFocused()){
//            tvLetter4.requestFocus();
//        }
        switch (mCurrentLetter) {
            case 0:
                tvLetter1.setText(letter);
                break;
            case 1:
                tvLetter2.setText(letter);
                break;
            case 2:
                tvLetter3.setText(letter);
                break;
            case 3:
                tvLetter4.setText(letter);
                break;
            default:
                break;

        }
    }

    public void createNewGame() {
        String input = tvLetter1.getText().toString() + tvLetter2.getText().toString()
                + tvLetter3.getText().toString() + tvLetter4.getText().toString();

        int maxAttempts = mSeekBar.getProgress();
        boolean timedGame = mSwTimer.isChecked();
        List<Definition> result;
        try {
            result = new WordValidationTask().execute(input).get();
            if (result != null && result.size() > 0) {
                new Game(input, maxAttempts, timedGame).persistGame();
                getActivity().finish();
            } else {
                tvLetter1.setError(input + " is not a valid word!");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            tvLetter1.setError(input + " - Word couldn't be verified. Try again later!");
        }

    }

    public void setFocus() {
//        if(tvLetter1.isFocused()){
//            tvLetter2.requestFocus();
//        }else if(tvLetter2.isFocused()){
//            tvLetter3.requestFocus();
//        }else if(tvLetter3.isFocused()){
//            tvLetter4.requestFocus();
//        }
        switch (mCurrentLetter) {
            case 0:
                tvLetter1.requestFocus();
                break;
            case 1:
                tvLetter2.requestFocus();
                break;
            case 2:
                tvLetter3.requestFocus();
                break;
            case 3:
                tvLetter4.requestFocus();
                break;
            default:
                break;

        }
    }

    public void decrementCurrentLetter() {
        if (mCurrentLetter > 0)
            mCurrentLetter--;
        setFocus();
    }

    public void incrementCurrentLetter() {
        if (mCurrentLetter < 3)
            mCurrentLetter++;
        setFocus();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int primaryCode) {
        tvLetter1.setError(null);
        switch (primaryCode) {
            case Keyboard.KEYCODE_DELETE:
//                deleteSurroundingText(1, 0);
                setLetterText("");
                decrementCurrentLetter();
//                mLetterAdapter.remove(mList.get(mList.size()-1));
                break;
            case Keyboard.KEYCODE_DONE:
                createNewGame();
//                sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            default:
                char code = (char) primaryCode;
                if (Character.isLetter(code)) {
                    code = Character.toUpperCase(code);
                }
                setLetterText(String.valueOf(code));
                incrementCurrentLetter();
//                mLetterAdapter.add(String.valueOf(code));
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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus) return;

        if (v.getId() == R.id.tvLetter1) {
            mCurrentLetter = 0;
        } else if (v.getId() == R.id.tvLetter2) {
            mCurrentLetter = 1;
        } else if (v.getId() == R.id.tvLetter3) {
            mCurrentLetter = 2;
        } else if (v.getId() == R.id.tvLetter4) {
            mCurrentLetter = 3;
        }
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
