package bme.co.za.stopeImages.PresentersImp;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;

import bme.co.za.stopeImages.Commons.SharedPreferenceWorker;
import bme.co.za.stopeImages.R;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Jeffrey.Mphahlele on 9/5/2017.
 */

public class EditableStopeImagesPresenterImp implements bme.co.za.stopeImages.Interface.IEditableStopeImages.Presenter{

    // private ViewGroup viewGroup;
    private bme.co.za.stopeImages.Wrapper.StopeImageBuilder myBuilder;

    // wrapper containing information about stope images
    private bme.co.za.stopeImages.Interface.IEditableStopeImages.MyView view;
    private bme.co.za.stopeImages.RxJava.IZoomLayout zoomLayoutImp;
    private bme.co.za.stopeImages.RxJava.IStopeImageView imageViewImp;
    private bme.co.za.stopeImages.RxJava.IStopeImage stopeImageImp;

    private bme.co.za.stopeImages.Wrapper.StopeInfo getStopeInfo() {
        return view.getStopeInfo();
    }


    public EditableStopeImagesPresenterImp(bme.co.za.stopeImages.Interface.IEditableStopeImages.MyView view){
        this.view=view;
        this.zoomLayoutImp =new bme.co.za.stopeImages.RxJava.ZoomLayoutImp(view);
        this.imageViewImp =new bme.co.za.stopeImages.RxJava.StopeImageViewImp(view.getContext(),getSavedStopeImageName());
        this.stopeImageImp =new bme.co.za.stopeImages.RxJava.StopeImageImp();
    }

    /**
     * Loads all images from external storage into multiple layouts
     */

    public void loadImages() {

        for(String url :getStopeInfo().getImagesList()) {
            zoomLayoutImp.setUrl(url);
            Disposable disposable = zoomLayoutImp.getZoomLayout().
                    subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<bme.co.za.stopeImages.Views.ZoomLayout>() {

                        @Override
                        public void onSuccess(bme.co.za.stopeImages.Views.ZoomLayout zoomLayout) {
                            //add layouts into a list
                            view.addToLayoutList(zoomLayout);
                            //add viewGroup to the view
                            view.addImageLayout(zoomLayout);
                            //viewGroup.addView(child);
                            //view.refreshActivity();
                            //Toast.makeText(myBuilder.getContext(), R.string.saved_stope,Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            // handle the error case
                        }
                    });
            //disposable.dispose();
        }
    }

    /**
     * Load saved image from external storage into stope view
     */
    public void loadSavedImage(){

        Disposable disposable =imageViewImp.getImageView()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ImageView>() {
            @Override
            public void onSuccess(ImageView imageView) {

                view.addView(imageView);
            }
            @Override
            public void onError(Throwable e) {

            }
        });
        //disposable.dispose();
    }

    /**
     * this method saves current view as image into external storage
     */
    @Override
    public void saveProcess() {
        view.initSaveImageProgressDialog();
        //saveViewGroupBitmap image details into sharedPreference
        SharedPreferenceWorker.saveCroppedStopeImageNameReference(view.getContext(), view.getConstantStopeKeyValue(), getSavedStopeImageName());
        setMyBuilder();
    }



    /**
     * This method delete saved image view and reload initial individual images
     */
    @Override
    public void deleteImage() {
        view.initDeleteImageProgressDialog();
        //clear shared preference data
        SharedPreferenceWorker.deleteCroppedStopeImageNameReference(view.getContext(),view.getConstantStopeKeyValue());
        //delete the image
        Disposable disposable = stopeImageImp.deleteStopeImage(getSavedStopeImageName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        view.dismissDeleteImageProgressDialog();
                        //report the msg
                        Toast.makeText(view.getContext(),R.string.reloading_stope_images,Toast.LENGTH_SHORT).show();
                        //refresh activity after clearing
                        view.refreshActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDeleteImageProgressDialog();

                    }
                });
        //disposable.dispose();
    }

    /**
     * Load saved stope image or all individual images base on initial setUpLayout
     */

    @Override
    public void setMyBuilder(){
        Disposable disposable = imageViewImp.loadBitmapFromView(view.getViewGroup())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Bitmap>() {

                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        //load bitmap
                        myBuilder=new bme.co.za.stopeImages.Wrapper.StopeImageBuilder();
                        myBuilder.setContext(view.getContext());
                        // myBuilder.setBitmapImage(loadBitmapFromView(view.getViewGroup()));
                        myBuilder.setBitmapImage(bitmap);
                        myBuilder.setFileName(getSavedStopeImageName());

                        save();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void save(){
        //Save the image
        Disposable disposable = stopeImageImp.saveImageBitmap(myBuilder).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {

                    view.dismissSaveImageProgressDialog();
                    view.refreshActivity();

                })
                .doOnError(throwable -> {

                    view.dismissSaveImageProgressDialog();
                })
                .subscribe();
    }

    private String getSavedStopeImageName() {
        //create a special name for saved viewGroup
        return  "savedStopeImageName_" + view.getConstantStopeKeyValue();
    }

    private Boolean hasSavedImage() {
        if(getStopeInfo().getSavedStopeImageName().length()>0||!getStopeInfo().getSavedStopeImageName().isEmpty())
        {
            return true;
        }
        return false;
    }

    @Override
    public void displayFullImage() {
        if(!getStopeInfo().getSavedStopeImageName().equals("")||!getStopeInfo().getSavedStopeImageName().isEmpty())
        {
            view.clearSavedImageDialog();
        }
        else
        {
            //clear all previous layouts setUpLayout first
            view.clearLayout();
            view.saveImageDialog();
        }
    }

    @Override
    public void stopeInit(){

        if(hasSavedImage())
        {
            view.setupUneditableStopeViews();
            loadSavedImage();
        }
        else
        {
            //view.setupUneditableStopeViews();
            loadImages();
        }
    }
}
