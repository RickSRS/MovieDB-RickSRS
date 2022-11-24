package com.richardsoares.moviedb_rick.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.richardsoares.moviedb_rick.R;
import com.richardsoares.moviedb_rick.ui.fragment.FragmentFavorite;
import com.richardsoares.moviedb_rick.ui.fragment.FragmentMovie;
import com.richardsoares.moviedb_rick.ui.utils.BottomBarBehavior;

public class MainActivity extends AppCompatActivity {

    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BubbleNavigationLinearView navigationBar = findViewById(R.id.navBar);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigationBar.getLayoutParams();
        layoutParams.setBehavior(new BottomBarBehavior());

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentMovie()).commit();

        navigationBar.setNavigationChangeListener((view, position) -> {
            switch (position) {
                case 0:
                    fragment = new FragmentMovie();
                    break;
                case 1:
                    fragment = new FragmentFavorite();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
        });
    }
}
