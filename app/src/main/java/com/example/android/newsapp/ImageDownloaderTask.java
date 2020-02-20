package com.example.android.newsapp;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;

    public ImageDownloaderTask(ImageView imageView) {
        imageViewReference = new WeakReference<>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        StringBuilder builder = new StringBuilder(params[0]);

        // Avoid java.io.IOException: Cleartext HTTP traffic to media.guim.co.uk not permitted
        if (params[0].startsWith("http:")) {
            builder.replace(0, 5, "https:");
        }

        return QueryUtils.fetchThumbnail(builder.toString());
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        ImageView imageView = imageViewReference.get();
        if (imageView != null) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                Drawable placeholder = imageView.getContext().getResources().getDrawable(R.drawable.thumbnail_placeholder);
                imageView.setImageDrawable(placeholder);
            }
        }
    }
}
