package bme.co.za.stopeImages.RxJava;

import java.util.ArrayList;

import bme.co.za.stopeImages.Commons.ImageWorker;
import io.reactivex.Completable;

/**
 * Created by Jeffrey.Mphahlele on 11/17/2017.
 */

/**
 * This is a Sync class to save image on a separate Thread
 */
public class StopeImageImp implements bme.co.za.stopeImages.RxJava.IStopeImage {

    @Override
    public Completable saveImageBitmap(bme.co.za.stopeImages.Wrapper.StopeImageBuilder myBuilder) {
        return Completable.create(emitter -> {
                  ImageWorker.saveBitmap(myBuilder);
                  emitter.onComplete();
                });
    }

    @Override
    public Completable deleteStopeImage(String string) {
        return Completable.create(emitter -> {
            ImageWorker.deleteStopeImage(string);
            emitter.onComplete();
        });
    }
    @Override
    public Completable deleteStopeImages(ArrayList<String> urls) {
        return Completable.create(emitter -> {
            ImageWorker.deleteStopeImages(urls);
            emitter.onComplete();
        });
    }
}
