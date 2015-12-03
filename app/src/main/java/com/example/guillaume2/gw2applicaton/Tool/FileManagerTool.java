package com.example.guillaume2.gw2applicaton.Tool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by guillaume2 on 26/11/15.
 */
public class FileManagerTool {

    public static void saveImage(Bitmap bmp, String path) {
        File dir = new File(path.substring(0, path.lastIndexOf("/") + 1));
        dir.mkdirs();

        File file = new File(dir, path.substring(path.lastIndexOf("/") + 1));
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap getImage(String path) {
        return BitmapFactory.decodeFile(path);
    }
}
