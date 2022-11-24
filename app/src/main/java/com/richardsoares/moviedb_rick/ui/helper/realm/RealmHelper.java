package com.richardsoares.moviedb_rick.ui.helper.realm;

import android.content.Context;
import android.util.Log;

import com.richardsoares.moviedb_rick.model.ModelMovie;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {

    private Context mContext;
    private Realm realm;
    private RealmResults<ModelMovie> modelMovie;

    public RealmHelper(Context mContext) {
        this.mContext = mContext;
        Realm.init(mContext);
        realm = Realm.getDefaultInstance();
    }

    public ArrayList<ModelMovie> showFavoriteMovie() {
        ArrayList<ModelMovie> data = new ArrayList<>();
        modelMovie = realm.where(ModelMovie.class).findAll();

        if (modelMovie.size() > 0) {
            for (int i = 0; i < modelMovie.size(); i++) {
                ModelMovie movie = new ModelMovie();
                movie.setId(modelMovie.get(i).getId());
                movie.setTitle(modelMovie.get(i).getTitle());
                movie.setVoteAverage(modelMovie.get(i).getVoteAverage());
                movie.setOverview(modelMovie.get(i).getOverview());
                movie.setReleaseDate(modelMovie.get(i).getReleaseDate());
                movie.setPosterPath(modelMovie.get(i).getPosterPath());
                movie.setBackdropPath(modelMovie.get(i).getBackdropPath());
                movie.setPopularity(modelMovie.get(i).getPopularity());
                data.add(movie);
            }
        }
        return data;
    }

    public void addFavoriteMovie(int Id, String Title, double VoteAverage, String Overview,
                                 String ReleaseDate, String PosterPath, String BackdropPath, String Popularity) {

        ModelMovie movie = new ModelMovie();
        Log.i("FILME:",String.valueOf(Id));
        movie.setId(Id);
        movie.setTitle(Title);
        movie.setVoteAverage(VoteAverage);
        movie.setOverview(Overview);
        movie.setReleaseDate(ReleaseDate);
        movie.setPosterPath(PosterPath);
        movie.setBackdropPath(BackdropPath);
        movie.setPopularity(Popularity);

        realm.beginTransaction();
        realm.copyToRealm(movie);
        realm.commitTransaction();
    }

    public void deleteFavoriteMovie(int id) {
        realm.beginTransaction();
//        RealmResults<ModelMovie> modelMovie = realm.where(ModelMovie.class).findAll();
        ModelMovie modelMovie = realm.where(ModelMovie.class).equalTo("Id",id).findFirst();
        modelMovie.deleteFromRealm();
        realm.commitTransaction();
    }


    public Boolean existsinFavoriteMovie(int id){
        ModelMovie modelMovie = realm.where(ModelMovie.class).equalTo("Id",id).findFirst();
        Boolean ret = false;
        if (modelMovie!=null)
        {
            Log.i("Favorito",String.valueOf(modelMovie.getId())+" "+modelMovie.getTitle());
            ret=true;
        }
        return ret;
    }
}
