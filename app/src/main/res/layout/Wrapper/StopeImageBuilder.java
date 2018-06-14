package bme.co.za.stopeImages.Wrapper;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * This is a builder that wrap parameters
 *
 * Created by Jeffrey.Mphahlele on 8/7/2017.
 */

public class StopeImageBuilder {
    private Context context;
    private Bitmap bitmapImage;
    private String fileName;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Bitmap getBitmapImage() {
        return bitmapImage;
    }

    public void setBitmapImage(Bitmap bitmapImage) {
        this.bitmapImage = bitmapImage;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
