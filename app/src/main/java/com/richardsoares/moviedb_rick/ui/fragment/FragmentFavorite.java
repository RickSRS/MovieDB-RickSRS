package com.richardsoares.moviedb_rick.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.richardsoares.moviedb_rick.R;
import com.richardsoares.moviedb_rick.ui.adapter.ViewPageAdapter;

public class FragmentFavorite extends Fragment {

    public FragmentFavorite() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorito, container, false);

        ViewPager viewPager = rootView.findViewById(R.id.vpFavorito);
        viewPager.setAdapter(new ViewPageAdapter((getChildFragmentManager())));

        return rootView;
    }

}
