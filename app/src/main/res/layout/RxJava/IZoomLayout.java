package bme.co.za.stopeImages.RxJava;

import io.reactivex.Single;

/**
 * Created by Jeffrey.Mphahlele on 11/15/2017.
 */

public interface IZoomLayout {
    void setUrl(String url);
    Single<bme.co.za.stopeImages.Views.ZoomLayout> getZoomLayout();
}
