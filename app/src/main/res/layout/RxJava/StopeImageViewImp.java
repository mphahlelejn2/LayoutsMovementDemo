package bme.co.za.stopeImages.RxJava;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.mainView.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;

import bme.co.za.stopeImages.Commons.ImageWorker;
import io.reactivex.Single;

/**
 * Created by Jeffrey.Mphahlele on 11/16/2017.
 */

public class StopeImageViewImp implements bme.co.za.stopeImages.RxJava.IStopeImageView {

    private String stopeImageName;
    private Context context;

    public StopeImageViewImp(Context context,String stopeImageName) {
        this.context=context;
        this.stopeImageName= stopeImageName;
    }


    @Override
    public Single<ImageView> getImageView() {
            return Single.just(createImageView());
    }


    @Override
    public Single<Bitmap> loadBitmapFromView(View v) {
        return Single.just(getBitmapFromView(v));
    }

    private ImageView createImageView() {
        //create imageView to display a bitmap
        ImageView image = new ImageView(context);
        //set Image size
        LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        image.setLayoutParams(paramsImage);
        //load image
        Glide.with(context)
                .load(ImageWorker.getUrl(context,stopeImageName,true))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .fitCenter()
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .into(image);
        return image;
    }

/*
    public Bitmap getBitmapFromView(View v) {
        //extract a bitmap from given mainView
        Bitmap b = Bitmap.createBitmap( v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        //save bitmap to the canvas
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }
*/

    public  Bitmap getBitmapFromView(View mainView) {
        Bitmap returnedBitmap = Bitmap.createBitmap(mainView.getWidth(), mainView.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =mainView.getBackground();
        if (bgDrawable!=null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        mainView.draw(canvas);
        return returnedBitmap;
    }

}
