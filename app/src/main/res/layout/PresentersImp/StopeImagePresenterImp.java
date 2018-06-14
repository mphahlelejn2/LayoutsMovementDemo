package bme.co.za.stopeImages.PresentersImp;
import android.content.Intent;
import android.graphics.Bitmap;

import bme.co.za.stopeImages.Commons.SharedPreferenceWorker;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Jeffrey.Mphahlele on 9/6/2017.
 */

public class StopeImagePresenterImp implements  bme.co.za.stopeImages.Interface.IStopeImage.Presenter{

    private bme.co.za.stopeImages.Wrapper.StopeImageBuilder myBuilder;
    private bme.co.za.stopeImages.Interface.IStopeImage.MyView view;
    private Bitmap bitmap;
    private bme.co.za.stopeImages.RxJava.IStopeImage stopeImageImp;

    public StopeImagePresenterImp(bme.co.za.stopeImages.Interface.IStopeImage.MyView view, Bitmap bitmap) {
        this.view = view;
        this.bitmap=bitmap;
        this.stopeImageImp =new bme.co.za.stopeImages.RxJava.StopeImageImp();
    }

    /**
     * saveViewGroupBitmap image url inside SharedPreference
     */
    @Override
    public void saveImageUrl() {
        //Save filename into sharedPreference
        SharedPreferenceWorker.saveImageNameReferenceToStopeWrapper(view.getContext(),view.getConstantStopeKeyValue(),getFileName());
    }

    /**
     * This method saveViewGroupBitmap image into device external storage
     */
    @Override
    public void saveImage() {
            view.initProgressDialog();
            setMyBuilder();
            saveImageUrl();
            Disposable disposable = stopeImageImp.saveImageBitmap(myBuilder).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete(() -> {
                        view.progressDismiss();
                        openStopeImagesActivity();
                        view.closeActivity();

                    })
                    .doOnError(throwable -> {

                       view.progressDismiss();
                    })
                    .subscribe();
           // disposable.dispose();


    }



    /**
     * This method Initialize builder setUpLayout
     */
    @Override
    public void setMyBuilder(){
        myBuilder=new bme.co.za.stopeImages.Wrapper.StopeImageBuilder();
        myBuilder.setContext(view.getContext());
        myBuilder.setBitmapImage(bitmap);
        myBuilder.setFileName(getFileName());
    }

    public String getFileName() {
        return "image_"+ view.getCurrentTimesStamp();
    }


    /**
     * This method opens image activity and kill the current one
     */
    @Override
    public void openStopeImagesActivity() {
        Intent intent = new Intent(view.getContext(), bme.co.za.stopeImages.Activities.NonEditableStopeImagesActivity.class);
        intent.putExtra("keyTimesStamp", view.getConstantStopeKeyValue());
        view.getContext().startActivity(intent);
    }

}
