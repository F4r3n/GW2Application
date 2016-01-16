package com.faren.gw2.gw2applicaton.Tool;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;

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

    public static void scaleImage(Activity activity, ImageView view, Bitmap b, int w, int h) throws NoSuchElementException {
        Bitmap bitmap = b;

        int width = 0;

        try {
            width = bitmap.getWidth();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("Can't find bitmap on given view/drawable");
        }

        int height = bitmap.getHeight();
        int boundingX = FileManagerTool.dpToPx(activity, w);
        int boundingY = dpToPx(activity, h);

        float xScale = ((float) boundingX) / width;
        float yScale = ((float) boundingY) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        width = scaledBitmap.getWidth(); // re-use
        height = scaledBitmap.getHeight(); // re-use
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);

        view.setImageDrawable(result);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);

    }

    public static int dpToPx(Activity activity, int dp) {
        float density = activity.getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public static float convertDpToPixels(Activity activity, float dp) {
        Resources resources = activity.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }

    public static Bitmap makeTransparent(Bitmap src, int value) {
        if (value == 255) return src;
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap transBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(transBitmap);
        canvas.drawARGB(0, 0, 0, 0);
        // config paint
        final Paint paint = new Paint();
        paint.setAlpha(value);
        canvas.drawBitmap(src, 0, 0, paint);
        return transBitmap;
    }

    public static String readFile(String dir, String name) throws IOException {
        File file = new File(dir + name);
        if (!file.exists()) {
            System.out.println("File does not exist");
            return null;
        } else {
            System.out.println("File does exist");
        }

        BufferedReader br;
        String line;
        String out = "";
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                out += line + " ";
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    public static void saveFile(String path, String name, String content) {
        File dir = new File(path);
        dir.mkdirs();
        File file = new File(dir, name);

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(String path) {
        new File(path).delete();
    }
}
