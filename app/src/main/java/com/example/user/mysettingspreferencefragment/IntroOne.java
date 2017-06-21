package com.example.user.mysettingspreferencefragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IntroOne.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IntroOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntroOne extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_POSITION = "position";
    private static final String ARG_PARAM2 = "param2";

    private TextView mTextView;

    private int mParamPosition;

    private final int SLIDE_ONE_MSG_ONE = 1;
    private final int SLIDE_ONE_MSG_TWO = 2;
    private final int SLIDE_TWO_MSG_ONE = 3;
    private final int SLIDE_TWO_MSG_TWO = 4;
    private final int SLIDE_TWO_MSG_THREE = 5;
    private final int SLIDE_THREE_MSG_ONE = 6;
    private final int SLIDE_THREE_MSG_TWO = 7;
    private final int SLIDE_THREE_MSG_THREE = 8;

    private OnFragmentInteractionListener mListener;

    public IntroOne() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position Parameter 1.
     * @return A new instance of fragment IntroOne.
     */
    // TODO: Rename and change types and number of parameters
    public static IntroOne newInstance(int position) {
        IntroOne fragment = new IntroOne();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamPosition = getArguments().getInt(ARG_POSITION, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_intro_one, container, false);
        mTextView = (TextView) rootView.findViewById(R.id.fragmentTextView);

        // Log.d("ABHI", "OnCreateView : mParamPosition :" + mParamPosition);
      /*  if (mParamPosition == 0)
            mHandler.sendEmptyMessageDelayed(SLIDE_ONE_MSG_ONE, 0);
        else if (mParamPosition == 1)
            mHandler.sendEmptyMessageDelayed(SLIDE_TWO_MSG_ONE, 0);
        else if (mParamPosition == 2)
            mHandler.sendEmptyMessageDelayed(SLIDE_THREE_MSG_ONE, 0);
*/
        setupScreens(mParamPosition);
        return rootView;
    }

    private void setupScreens(int position) {
        if (position == 0) {
            mTextView.setBackgroundColor(getResources().getColor(R.color.fragmentOneColor));
            mTextView.setText("Slide One");
        } else if (position == 1) {
            mTextView.setBackgroundColor(getResources().getColor(R.color.fragmentTwoColor));
            mTextView.setText("Slide Two");
        } else if (position == 2) {
            mTextView.setBackgroundColor(getResources().getColor(R.color.fragmentThreeColor));
            mTextView.setText("Slide Three");
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeCallbacksAndMessages(null);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SLIDE_ONE_MSG_ONE:
                    mTextView.setText("SLIDE ONE : MSG ONE");
                    //  Log.d("ABHI", "Handler : SLIDE ONE : MSG ONE");
                    mHandler.sendEmptyMessageDelayed(SLIDE_ONE_MSG_TWO, 2000);
                    break;
                case SLIDE_ONE_MSG_TWO:
                    mTextView.setText("SLIDE ONE : MSG TWO");
                    //Log.d("ABHI", "Handler : SLIDE ONE : MSG TWO");
                    mHandler.sendEmptyMessageDelayed(SLIDE_ONE_MSG_ONE, 2000);
                    break;
                case SLIDE_TWO_MSG_ONE:
                    mTextView.setText("SLIDE TWO : MSG ONE");
                    //Log.d("ABHI", "Handler : SLIDE TWO : MSG ONE");
                    mHandler.sendEmptyMessageDelayed(SLIDE_TWO_MSG_TWO, 2000);
                    break;
                case SLIDE_TWO_MSG_TWO:
                    mTextView.setText("SLIDE TWO : MSG TWO");
                    //Log.d("ABHI", "Handler : SLIDE TWO : MSG TWO");
                    mHandler.sendEmptyMessageDelayed(SLIDE_TWO_MSG_THREE, 2000);
                    break;
                case SLIDE_TWO_MSG_THREE:
                    mTextView.setText("SLIDE TWO : MSG THREE");
                    //Log.d("ABHI", "Handler : SLIDE TWO : MSG THREE");
                    mHandler.sendEmptyMessageDelayed(SLIDE_TWO_MSG_ONE, 2000);
                    break;
                case SLIDE_THREE_MSG_ONE:
                    mTextView.setText("SLIDE THREE : MSG ONE");
                    //Log.d("ABHI", "Handler : SLIDE THREE : MSG ONE");
                    mHandler.sendEmptyMessageDelayed(SLIDE_THREE_MSG_TWO, 2000);
                    break;
                case SLIDE_THREE_MSG_TWO:
                    mTextView.setText("SLIDE THREE : MSG TWO");
                    //Log.d("ABHI", "Handler : SLIDE THREE : MSG TWO");
                    mHandler.sendEmptyMessageDelayed(SLIDE_THREE_MSG_THREE, 2000);
                    break;
                case SLIDE_THREE_MSG_THREE:
                    mTextView.setText("SLIDE THREE : MSG THREE");
                    //Log.d("ABHI", "Handler : SLIDE THREE : MSG THREE");
                    mHandler.sendEmptyMessageDelayed(SLIDE_THREE_MSG_ONE, 2000);
                    break;
            }
        }
    };
}
