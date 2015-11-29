package com.example.guillaume2.gw2applicaton;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by guillaume2 on 16/11/15.
 */
public class DownloadImage extends AsyncTask<DataImageToDl , Void, Void> {

    private CallerBack callerBack;
    private List<DataImageToDl> urls;
    private String id;
    private int index = 0;
    private int width;
    private int height;
    public DownloadImage(CallerBack callerBack, List<DataImageToDl> urls) {
        this.urls = urls;
        this.callerBack = callerBack;
    }


    protected void onPreExecute() {
        //currentDownloadImage = imagesToDownload.get(0);
    }

    protected Void doInBackground(DataImageToDl ... data) {
        for(int i = 0; i < urls.size(); i++) {
            id = urls.get(i).id;
            index = urls.get(i).index;
            width = urls.get(i).imageResource.width;
            height = urls.get(i).imageResource.height;
            retrieveImage(urls.get(i).imageResource.iconUrl);
        }
        return null;
    }

    /***
     * Update the download queue and start a new download if needed
     */
    protected void onPostExecute(Void o) {

    }

    /***
     * Retrieve a rescaled image from the corresponding url
     */
    private void retrieveImage(String urlImage) {
        Bitmap result;


        try {
            System.out.println("Image " + urlImage);
            if(urlImage == null || new File(id).exists()) {
                callerBack.notifyUpdate(this, null, index, id);
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

            callerBack.notifyUpdate(this, result, index, id);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}