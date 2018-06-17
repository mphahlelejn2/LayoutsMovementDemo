package kamo.co.za.layoutsmovementdemo.workers;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;




public class ImageWorker {

    public static final String directoryName =  "/ME" + "/Layout/"+"Images";

    public static Uri getImageUrl(Context context, String imageName, boolean b) {
        return Uri.fromFile(createImage(context,imageName,b ));
    }

    /**
     * This method create a file representation of image into external storage
     *
     * @param context
     * @param external
     * @return
     */

    public static File getImageDirectory(Context context, boolean external) {

        File directory;
        //check external storage
        if (external) {
            directory = getAlbumStorageDir();
        } else {
            directory = context.getDir(directoryName, Context.MODE_PRIVATE);
        }
        return directory;
    }


    public static File createImage(Context context, String fileName, boolean external ) {
        return new File(getImageDirectory(context,external), fileName);
    }


    private static File getAlbumStorageDir() {
        File file = new File(Environment.getExternalStorageDirectory(), directoryName);
        if (!file.mkdirs()) {
            Log.e("ImageSaver", "Directory not created");
        }
        return file;
    }


        /**
         * Given url this method deletes the image stored into externally storage
         *
         * @param url
         */
    public static void deleteImage(Context context, String url) {
        File dir = getImageDirectory(context,true);
        if (dir.isDirectory()) {
            //check if url is not null
            if (url != null && !(url.isEmpty())) {
                File list[] = dir.listFiles();
                for (File f : list) {
                    if (f.getName().contains(url))
                        f.delete();
                }
            }
        }
    }

    /**
     * This method delete the entire images associated with the particular stope
     *
     * @param urls
     */
    public static void deleteImages(Context context, ArrayList<String> urls) {
        File dir = getImageDirectory(context,true);
        if (dir.isDirectory()) {
            //check if url list is not empty
            if (urls != null && !(urls.isEmpty()))
                for (String filename : urls) {
                    File list[] = dir.listFiles();
                    //Loop to directory to delete
                    for (File f : list) {
                        if (f.getName().contains(filename))
                            f.delete();
                    }
                }
        }
    }

    /**
     * This method check if external storage is readable
     *
     * @return
     */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public static void saveBitmap(Context context, Bitmap bitmap, String name) {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100 , outStream);
        File f= createImage(context,name,true);
        try {
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(outStream.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Bitmap getBitmap(Context context, String url) {

        File dir = getImageDirectory(context,true);
        Bitmap bitmap = null;
        if (dir.isDirectory()) {
            //check if url is not null
            if (url != null && !(url.isEmpty())) {
                File list[] = dir.listFiles();
                for (File f : list) {
                    if (f.getName().matches(url)){
                        bitmap= BitmapFactory.decodeFile(f.getAbsolutePath());
                    }
                }
            }
        }
        return bitmap;
    }


    public static String copyImage(String mSelectedImagePath, File file) {
        File source = new File(mSelectedImagePath );
        FileChannel src;
        FileChannel dst;
        try {
            src = new FileInputStream(source).getChannel();
            dst = new FileOutputStream(file).getChannel();
            dst.transferFrom(src, 0, src.size());       // copy the first file to second.....
            src.close();
            dst.close();
            return file.getName();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }

}
