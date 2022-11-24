package com.richardsoares.moviedb_rick.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.richardsoares.moviedb_rick.R;
import com.richardsoares.moviedb_rick.model.ModelMovie;
import com.richardsoares.moviedb_rick.ui.helper.realm.RealmHelper;
import com.richardsoares.moviedb_rick.ui.activities.DetailMovieActivity;
import com.richardsoares.moviedb_rick.ui.adapter.MovieAdapter;

import java.util.ArrayList;

public class FragmentFavoriteMovie extends Fragment implements MovieAdapter.onSelectData {

    private RecyclerView rvMovieFav;
    private ArrayList<ModelMovie> modelMovie = new ArrayList<>();
    private RealmHelper helper;
    private TextView txtSemFilmeFavorito;

    public FragmentFavoriteMovie() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_filme_favorito, container, false);

        helper = new RealmHelper(getActivity());

        txtSemFilmeFavorito = rootView.findViewById(R.id.filmeSemFavorito);
        rvMovieFav = rootView.findViewById(R.id.rvFilmeFavorito);
        rvMovieFav.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovieFav.setAdapter(new MovieAdapter(getActivity(), modelMovie, this));
        rvMovieFav.setHasFixedSize(true);

        setData();

        return rootView;
    }

    private void setData() {
        modelMovie = helper.showFavoriteMovie();
        if (modelMovie.size() == 0) {
            txtSemFilmeFavorito.setVisibility(View.VISIBLE);
            rvMovieFav.setVisibility(View.GONE);
        } else {
            txtSemFilmeFavorito.setVisibility(View.GONE);
            rvMovieFav.setVisibility(View.VISIBLE);
            rvMovieFav.setAdapter(new MovieAdapter(getActivity(), modelMovie, this));
        }
    }

    @Override
    public void onSelected(ModelMovie modelMovie) {
        Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
        intent.putExtra("detailMovie", modelMovie);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }
}
