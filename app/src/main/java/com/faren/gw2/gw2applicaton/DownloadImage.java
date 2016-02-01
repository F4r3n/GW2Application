package com.faren.gw2.gw2applicaton;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class DownloadImage extends AsyncTask<DataImageToDl, Void, Void> {

    private CallerBack callerBack;
    private List<DataImageToDl> urls;
    private String id;
    private int index = 0;
    private int width;
    private int height;
    private int status_code = 0;

    public DownloadImage(CallerBack callerBack, List<DataImageToDl> urls, int status_code) {
        this.urls = urls;
        this.callerBack = callerBack;
        this.status_code = status_code;
    }


    protected void onPreExecute() {
    }

    protected Void doInBackground(DataImageToDl... data) {
        for (int i = 0; i < urls.size(); i++) {
            id = urls.get(i).id;
            index = urls.get(i).index;
            width = urls.get(i).imageResource.width;
            height = urls.get(i).imageResource.height;
            index = i;
            retrieveImage(urls.get(i).imageResource.iconUrl);

        }
        return null;
    }

    protected void onPostExecute(Void o) {
        callerBack.notifyUpdate(this, null, -1, -1, status_code);
    }

    private void retrieveImage(String urlImage) {
        Bitmap result;


        try {
            System.out.println("Image " + urlImage + " " + index);
            if (urlImage == null || new File(id).exists()) {
                if (urlImage != null)
                    System.out.println("DLImage " + id + " already exists");
                callerBack.notifyUpdate(this, null, index, id, 0);
                return;
            }
            URL url = new URL(urlImage);
            // Get the image dimensions
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(connection.getInputStream(), new Rect(), options);
            connection.disconnect();
            int imageHeight = options.outHeight;
            int imageWidth = options.outWidth;

            // Get the image
            connection = (HttpURLConnection) url.openConnection();
            if (imageWidth > width || imageHeight > height) {
                // Compute the scale factor so that the image fits into the max dimensions
                float widthScale = ((float) imageWidth) / width;
                float heightScale = ((float) imageHeight) / height;
                float scale = (widthScale > heightScale) ? widthScale : heightScale;
                options.inSampleSize = (int) scale;
                options.inJustDecodeBounds = false;
                result = BitmapFactory.decodeStream(connection.getInputStream(), new Rect(), options);
            } else {
                result = BitmapFactory.decodeStream(connection.getInputStream());
            }
            connection.disconnect();

            callerBack.notifyUpdate(this, result, index, id, status_code);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}