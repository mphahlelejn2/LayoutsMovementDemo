package bme.co.za.stopeImages.RxJava;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import bme.co.za.stopeImages.Commons.ImageWorker;
import bme.co.za.stopeImages.Interface.IEditableStopeImages;
import bme.co.za.stopeImages.Views.ZoomLayout;
import io.reactivex.Single;


/**
 * Created by Jeffrey.Mphahlele on 11/15/2017.
 */

public class ZoomLayoutImp implements IZoomLayout {

    private IEditableStopeImages.MyView view;
    private String url;

    public String getUrl() {
        return url;
    }
    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    public ZoomLayoutImp(IEditableStopeImages.MyView view) {
        this.view = view;
    }

    @Override
    public Single<ZoomLayout> getZoomLayout() {
        return Single.just(createZoomLayout());
    }


    public ZoomLayout createZoomLayout(){

        //Create new Zoomable viewGroup which will hold imageView
        ZoomLayout child = new ZoomLayout(view);
        //set viewGroup size according to current screen size
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(900, 715);
        child.setLayoutParams(params);
        child.setOrientation(LinearLayout.VERTICAL);

        //create and setup new ImageView
        ImageView image = new ImageView(view.getContext());
        LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        image.setLayoutParams(paramsImage);
        //Load bitmap image form external storage
        Glide.with(view.getContext()).load(ImageWorker.getUrl(view.getContext(),getUrl(), true)).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(image);

        image.setScaleType(ImageView.ScaleType.FIT_XY);
        child.addView(image);
        child.init();
        return child;
    }
}
