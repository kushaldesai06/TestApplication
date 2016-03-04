package com.example.kushaldesai.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.kushaldesai.testapplication.R;

/**
 * Created by kushal.desai on 3/3/2016.
 */
public class ImageFragment extends Fragment {

    private LinearLayout linearLayout;
    private int position;
    private ImageView pagerFragmentItem;

    public ImageFragment(int position) {
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        linearLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.fragment_image, container, false);
        defineView();
        return linearLayout;
    }

    private void defineView(){
        pagerFragmentItem = (ImageView)linearLayout.findViewById(R.id.pagerFragmentItem);
        switch (position) {
            case 0:  pagerFragmentItem.setBackgroundResource(R.drawable.autombile);
                break;
            case 1:  pagerFragmentItem.setBackgroundResource(R.drawable.hitech_02);
                break;
            case 2:  pagerFragmentItem.setBackgroundResource(R.drawable.telecom);
                break;
            case 3:  pagerFragmentItem.setBackgroundResource(R.drawable.telecom_img1);
                break;
            default: pagerFragmentItem.setBackgroundResource(R.drawable.autombile);
                break;
        }
    }
}
