package bme.co.za.stopeImages.Commons;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This method is a Worker to saveViewGroupBitmap,delete or view images from external storage
 *
 */

public class ImageWorker {

    private static String directoryName = "ImageLayoutInfo Images App";

    public static Uri getUrl(Context context, String imageName, boolean b) {
        return Uri.fromFile(createFile(context,imageName,b ));
    }


    /**
     * This method create a file representation of image into external storage
     *
     * @param context
     * @param fileName
     * @param external
     * @return
     */
    public static File createFile(Context context,String fileName,boolean external ) {
        File directory;
        //check external storage
        if (external) {
            directory = getAlbumStorageDir(directoryName);
        } else {
            directory = context.getDir(directoryName, Context.MODE_PRIVATE);
        }
        return new File(directory, fileName);
    }


    /**
     * Get images directory
     *
     * @param albumName
     * @return
     */

    private static File getAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
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
    public static void deleteStopeImage(String url) {
        File dir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), directoryName);

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
    public static void deleteStopeImages(ArrayList<String> urls) {
        File dir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), directoryName);
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

    public static void saveBitmap(bme.co.za.stopeImages.Wrapper.StopeImageBuilder myBuilder) {

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(bme.co.za.stopeImages.Commons.ImageWorker.createFile(myBuilder.getContext(),myBuilder.getFileName(),true));

            //Scale the image
            myBuilder.getBitmapImage().compress(Bitmap.CompressFormat.PNG, 50, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
