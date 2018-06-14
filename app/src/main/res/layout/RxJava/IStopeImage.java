package bme.co.za.stopeImages.RxJava;

import java.util.ArrayList;

import io.reactivex.Completable;

/**
 * Created by Jeffrey.Mphahlele on 11/17/2017.
 */

public interface IStopeImage {
    Completable saveImageBitmap(bme.co.za.stopeImages.Wrapper.StopeImageBuilder myBuilder);
    Completable deleteStopeImage(String string);
    Completable deleteStopeImages(ArrayList<String> urls);
}
