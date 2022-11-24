package com.richardsoares.moviedb_rick.ui.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;
import com.richardsoares.moviedb_rick.R;
import com.richardsoares.moviedb_rick.model.ModelMovie;
import com.richardsoares.moviedb_rick.moviedb.networking.ApiMovieDB;
import com.richardsoares.moviedb_rick.ui.activities.DetailMovieActivity;
import com.richardsoares.moviedb_rick.ui.adapter.MovieAdapter;
import com.richardsoares.moviedb_rick.ui.adapter.MovieHorizontalAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FragmentMovie extends Fragment implements MovieHorizontalAdapter.onSelectData, MovieAdapter.onSelectData {

    private RecyclerView rvNowPlaying, rvFilmRecommend;
    private MovieHorizontalAdapter movieHorizontalAdapter;
    private MovieAdapter movieAdapter;
    private SearchView searchFilm;
    private List<ModelMovie> moviePlayNow = new ArrayList<>();
    private List<ModelMovie> moviePopular = new ArrayList<>();
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private FloatingActionButton next;
    private NestedScrollView ns;
    int page=1;
    boolean down_auto,up_auto;
    Parcelable recyclerViewState;
    private ProgressBar mProgressBar;



    public FragmentMovie() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_filme, container, false);

        moviePopular = new ArrayList<>();

        searchFilm = rootView.findViewById(R.id.pesquisarFilme);
        searchFilm.setQueryHint(getString(R.string.pesquisar_filme));
        searchFilm.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setSearchMovie(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals(""))
                    getMovie(page);
                return false;
            }
        });

        int searchPlateId = searchFilm.getContext().getResources()
                .getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchFilm.findViewById(searchPlateId);
        if (searchPlate != null) {
            searchPlate.setBackgroundColor(Color.TRANSPARENT);
        }

        rvNowPlaying = rootView.findViewById(R.id.rvNowPlaying);
        rvNowPlaying.setHasFixedSize(true);
        rvNowPlaying.setLayoutManager(new CardSliderLayoutManager(getActivity()));
        new CardSnapHelper().attachToRecyclerView(rvNowPlaying);

        rvFilmRecommend = rootView.findViewById(R.id.rvFilmesRecomendados);
        rvFilmRecommend.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFilmRecommend.setHasFixedSize(true);
        rvFilmRecommend.setNestedScrollingEnabled(false);

        ns = rootView.findViewById(R.id.nested_scrollView);
        mProgressBar = rootView.findViewById(R.id.progress_bar);

        getMovieHorizontal();
        getMovie(page);
        NestedScrollView nestedSV = (NestedScrollView) rootView.findViewById(R.id.nested_scrollView);

        if (nestedSV != null) {

            nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    String TAG = "nested_sync";
                    if (scrollY >= (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                        //Log.i(TAG, "BOTTOM SCROLL"+down_auto);
                        page++;
                        //Pega uma nova página de filmes
                        //rvFilmRecommend.setVisibility(View.GONE);
                        mProgressBar.setVisibility(View.VISIBLE);
                        getMovie(page);
                    }
                }
            });
        }

        return rootView;

    }

    private void setSearchMovie(String query) {
        AndroidNetworking.get(ApiMovieDB.BASEURL + ApiMovieDB.SEARCH_MOVIE
                        + ApiMovieDB.APIKEY + ApiMovieDB.LANGUAGE + ApiMovieDB.QUERY + query)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            moviePopular = new ArrayList<>();
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                ModelMovie dataApi = new ModelMovie();
                                SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMMM yyyy");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                                String datePost = jsonObject.getString("release_date");

                                dataApi.setId(jsonObject.getInt("id"));
                                dataApi.setTitle(jsonObject.getString("title"));
                                dataApi.setVoteAverage(jsonObject.getDouble("vote_average"));
                                dataApi.setOverview(jsonObject.getString("overview"));
                                dataApi.setReleaseDate(formatter.format(dateFormat.parse(datePost)));
                                dataApi.setPosterPath(jsonObject.getString("poster_path"));
                                dataApi.setBackdropPath(jsonObject.getString("backdrop_path"));
                                dataApi.setPopularity(jsonObject.getString("popularity"));
                                moviePopular.add(dataApi);
                                showMovie();
                            }
                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), getString(R.string.erro_exibir_filme), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getActivity(), getString(R.string.erro_conexao), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getMovieHorizontal() {
        AndroidNetworking.get(ApiMovieDB.BASEURL + ApiMovieDB.MOVIE_PLAYNOW + ApiMovieDB.APIKEY + ApiMovieDB.LANGUAGE)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                ModelMovie dataApi = new ModelMovie();
                                SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMMM yyyy");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                                String datePost = jsonObject.getString("release_date");

                                dataApi.setId(jsonObject.getInt("id"));
                                dataApi.setTitle(jsonObject.getString("title"));
                                dataApi.setVoteAverage(jsonObject.getDouble("vote_average"));
                                dataApi.setOverview(jsonObject.getString("overview"));
                                dataApi.setReleaseDate(formatter.format(dateFormat.parse(datePost)));
                                dataApi.setPosterPath(jsonObject.getString("poster_path"));
                                dataApi.setBackdropPath(jsonObject.getString("backdrop_path"));
                                dataApi.setPopularity(jsonObject.getString("popularity"));
                                moviePlayNow.add(dataApi);
                                showMovieHorizontal();
                            }
                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), getString(R.string.erro_exibir_filme), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getActivity(), getString(R.string.erro_conexao), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getMovie(int page) {
        //Log.i("API: ",ApiMovieDB.BASEURL + ApiMovieDB.MOVIE_POPULAR + ApiMovieDB.APIKEY + ApiMovieDB.LANGUAGE);
        AndroidNetworking.get(ApiMovieDB.BASEURL + ApiMovieDB.MOVIE_POPULAR + ApiMovieDB.APIKEY + ApiMovieDB.LANGUAGE+"&page="+String.valueOf(page))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                ModelMovie dataApi = new ModelMovie();
                                SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMMM yyyy");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                                String datePost = jsonObject.getString("release_date");

                                dataApi.setId(jsonObject.getInt("id"));
                                dataApi.setTitle(jsonObject.getString("title"));
                                dataApi.setVoteAverage(jsonObject.getDouble("vote_average"));
                                dataApi.setOverview(jsonObject.getString("overview"));
                                dataApi.setReleaseDate(formatter.format(dateFormat.parse(datePost)));
                                dataApi.setPosterPath(jsonObject.getString("poster_path"));
                                dataApi.setBackdropPath(jsonObject.getString("backdrop_path"));
                                dataApi.setPopularity(jsonObject.getString("popularity"));
                                moviePopular.add(dataApi);
                                showMovie();
                            }
                            //showButtonMore();
                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), getString(R.string.erro_exibir_filme), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getActivity(), getString(R.string.erro_conexao), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showMovieHorizontal() {
        movieHorizontalAdapter = new MovieHorizontalAdapter(getActivity(), moviePlayNow, this);
        rvNowPlaying.setAdapter(movieHorizontalAdapter);
        movieHorizontalAdapter.notifyDataSetChanged();
    }

    private void showMovie() {
        movieAdapter = new MovieAdapter(getActivity(), (ArrayList<ModelMovie>) moviePopular, this);
        rvFilmRecommend.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();
        rvFilmRecommend.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSelected(ModelMovie modelMovie) {
        Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
        intent.putExtra("detailMovie", modelMovie);
        startActivity(intent);
    }

}