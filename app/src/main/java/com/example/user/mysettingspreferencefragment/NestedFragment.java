package com.example.user.mysettingspreferencefragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NestedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NestedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NestedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_POSITION = "position";
    private TextView mTextView;
    private FrameLayout mParentView;
    // TODO: Rename and change types of parameters
    private int mParamPosition;

    private OnFragmentInteractionListener mListener;

    public NestedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NestedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NestedFragment newInstance(int position) {
        NestedFragment fragment = new NestedFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_nested, container, false);
        mTextView = (TextView) rootView.findViewById(R.id.nested_fragment_text);
        mParentView = (FrameLayout) rootView.findViewById(R.id.nested_fragment_parent_view);
        setupScreens(mParamPosition);
        return rootView;
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
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void setupScreens(int position) {
        if (position == 0) {
            mTextView.setText("Nested Fragment Slide One");
        } else if (position == 1) {
            mTextView.setText("Nested Fragment Slide Two");
        } else if (position == 2) {
            mTextView.setText("Nested Fragment Slide Three");
        }
    }
}
