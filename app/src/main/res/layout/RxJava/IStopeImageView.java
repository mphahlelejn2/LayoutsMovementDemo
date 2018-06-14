package bme.co.za.stopeImages.RxJava;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import io.reactivex.Single;

/**
 * Created by Jeffrey.Mphahlele on 11/16/2017.
 */

public interface IStopeImageView {
    Single<ImageView> getImageView();
    Single<Bitmap> loadBitmapFromView(View v);
}
