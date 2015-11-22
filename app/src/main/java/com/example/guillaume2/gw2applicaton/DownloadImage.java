package com.example.guillaume2.gw2applicaton;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;

import com.example.guillaume2.gw2applicaton.item.GWItem;

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
            retrieveImage(urls.get(i).url);
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
            if (imageWidth > GWItem.MAX_WIDTH || imageHeight > GWItem.MAX_HEIGHT) {
                // Compute the scale factor so that the image fits into the max dimensions
                float widthScale = ((float) imageWidth) / GWItem.MAX_WIDTH;
                float heightScale = ((float) imageHeight) / GWItem.MAX_HEIGHT;
                float scale = (widthScale > heightScale) ? widthScale : heightScale;
                options.inSampleSize = (int) scale;
                options.inJustDecodeBounds = false;
                result = BitmapFactory.decodeStream(connection.getInputStream(), new Rect(), options);
            } else {
                result = BitmapFactory.decodeStream(connection.getInputStream());
            }
            connection.disconnect();

            callerBack.notifyUpdate(this, result, 0, id);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}