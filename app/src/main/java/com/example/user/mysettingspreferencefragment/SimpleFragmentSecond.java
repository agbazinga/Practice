package com.example.user.mysettingspreferencefragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IntroOne.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IntroOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SimpleFragmentSecond extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_POSITION = "position";
    private static final String ARG_PARAM2 = "param2";

    private TextView mTextView;
    private RelativeLayout mParentView;
    private ViewPager mViewPager;
    private NestedViewPagerAdapter mPagerAdapter;
    private int mParamPosition;
    private ImageView dotImageViews[];
    private LinearLayout dotContainer;
    private static int NUM_PAGES = 2;
    private int currentPage;

    private OnFragmentInteractionListenerSecond mListener;

    public SimpleFragmentSecond() {
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
    public static SimpleFragmentSecond newInstance(int position) {
        SimpleFragmentSecond fragment = new SimpleFragmentSecond();
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_simple_second, container, false);
        mTextView = (TextView) rootView.findViewById(R.id.fragmentTextView);
        mParentView = (RelativeLayout) rootView.findViewById(R.id.simple_fragment_second_root);
        mViewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        mViewPager.addOnPageChangeListener(mViewPagerChangeListener);
        mPagerAdapter = new NestedViewPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        setupScreens(mParamPosition);

        dotImageViews = new ImageView[NUM_PAGES];
        dotContainer = (LinearLayout) rootView.findViewById(R.id.dotsContainer);

        initialisePageIndicatorDynamic();
        return rootView;
    }

    private void setupScreens(int position) {
        if (position == 0) {
            mParentView.setBackgroundColor(getResources().getColor(R.color.fragmentOneColor));
            mTextView.setText("Simple Fragment Second Slide One");
        } else if (position == 1) {
            mParentView.setBackgroundColor(getResources().getColor(R.color.fragmentTwoColor));
            mTextView.setText("Simple Fragment Second Slide Two");
        } else if (position == 2) {
            mParentView.setBackgroundColor(getResources().getColor(R.color.fragmentThreeColor));
            mTextView.setText("Simple Fragment Second Slide Three");
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteractionSecond(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListenerSecond) {
            mListener = (OnFragmentInteractionListenerSecond) context;
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
    public interface OnFragmentInteractionListenerSecond {
        // TODO: Update argument type and name
        void onFragmentInteractionSecond(Uri uri);
    }

    private void initialisePageIndicatorDynamic() {

        int h = getResources().getDimensionPixelSize(R.dimen.indicatorHeight);
        int endMargin = getResources().getDimensionPixelSize(R.dimen.indicator_end_margin);

        LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(h, h);
        imageViewLayoutParams.setMarginStart(endMargin / 2);
        imageViewLayoutParams.setMarginEnd(endMargin / 2);

        for (int i = 0; i < NUM_PAGES; i++) {
            dotImageViews[i] = new ImageView(getActivity());
            dotImageViews[i].setLayoutParams(imageViewLayoutParams);
            LinearLayout imageViewContainer = new LinearLayout(getActivity());

            if (i == 0)
                dotImageViews[i].setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
            else
                dotImageViews[i].setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_dim));

            dotContainer.addView(dotImageViews[i]);

        }
    }

    private void setUpPageIndicator(int position) {

        for (int i = 0; i < NUM_PAGES; i++) {
            if (i != position) {
                dotImageViews[i].setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_dim));
            } else {
                dotImageViews[i].setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
            }

        }
    }

    private class NestedViewPagerAdapter extends FragmentPagerAdapter {

        public NestedViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return NestedFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    ViewPager.OnPageChangeListener mViewPagerChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // Log.d("ABHI", "onPageScrolled");

            if (position == currentPage) {
                // Position is same as currentPage , so scrolling is to right.
                if (positionOffset > 0.5) {
                    setUpPageIndicator(position + 1);
                } else {
                    setUpPageIndicator(position);
                }

            } else {
                //Position is not same as currentPage , so scrolling is to left.
                if (positionOffset < 0.5) {
                    setUpPageIndicator(position);
                } else {
                    setUpPageIndicator(position + 1);
                }
            }
        }

        @Override
        public void onPageSelected(int position) {
            currentPage = position;
            setUpPageIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
