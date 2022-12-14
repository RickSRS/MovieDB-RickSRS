// Generated by view binder compiler. Do not edit!
package com.richardsoares.moviedb_rick.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.richardsoares.moviedb_rick.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ListaItemFilmeBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final CardView cvFilm;

  @NonNull
  public final TextView filmeLabelDesc;

  @NonNull
  public final ImageView imgFotoFilme;

  @NonNull
  public final RatingBar ratingStars;

  @NonNull
  public final TextView tvRealeseDate;

  @NonNull
  public final TextView tvTitle;

  private ListaItemFilmeBinding(@NonNull RelativeLayout rootView, @NonNull CardView cvFilm,
      @NonNull TextView filmeLabelDesc, @NonNull ImageView imgFotoFilme,
      @NonNull RatingBar ratingStars, @NonNull TextView tvRealeseDate, @NonNull TextView tvTitle) {
    this.rootView = rootView;
    this.cvFilm = cvFilm;
    this.filmeLabelDesc = filmeLabelDesc;
    this.imgFotoFilme = imgFotoFilme;
    this.ratingStars = ratingStars;
    this.tvRealeseDate = tvRealeseDate;
    this.tvTitle = tvTitle;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ListaItemFilmeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ListaItemFilmeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.lista_item_filme, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ListaItemFilmeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cvFilm;
      CardView cvFilm = ViewBindings.findChildViewById(rootView, id);
      if (cvFilm == null) {
        break missingId;
      }

      id = R.id.filmeLabelDesc;
      TextView filmeLabelDesc = ViewBindings.findChildViewById(rootView, id);
      if (filmeLabelDesc == null) {
        break missingId;
      }

      id = R.id.imgFotoFilme;
      ImageView imgFotoFilme = ViewBindings.findChildViewById(rootView, id);
      if (imgFotoFilme == null) {
        break missingId;
      }

      id = R.id.ratingStars;
      RatingBar ratingStars = ViewBindings.findChildViewById(rootView, id);
      if (ratingStars == null) {
        break missingId;
      }

      id = R.id.tvRealeseDate;
      TextView tvRealeseDate = ViewBindings.findChildViewById(rootView, id);
      if (tvRealeseDate == null) {
        break missingId;
      }

      id = R.id.tvTitle;
      TextView tvTitle = ViewBindings.findChildViewById(rootView, id);
      if (tvTitle == null) {
        break missingId;
      }

      return new ListaItemFilmeBinding((RelativeLayout) rootView, cvFilm, filmeLabelDesc,
          imgFotoFilme, ratingStars, tvRealeseDate, tvTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
