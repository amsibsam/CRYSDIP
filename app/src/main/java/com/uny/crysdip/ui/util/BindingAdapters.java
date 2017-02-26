package com.uny.crysdip.ui.util;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class BindingAdapters {

    @BindingAdapter({"binding"})
    public static void bindImageResource(ImageView imageView, Uri imageUri) {
        Picasso.with(imageView.getContext())
                .load(imageUri)
                .fit()
                .into(imageView);
    }

    @BindingAdapter({"binding"})
    public static void bindImageResource(ImageView imageView, int resourceId) {
        imageView.setImageResource(resourceId);
    }
}
