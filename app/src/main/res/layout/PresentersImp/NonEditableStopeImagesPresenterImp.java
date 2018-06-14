package bme.co.za.stopeImages.PresentersImp;

import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import bme.co.za.stopeImages.Commons.ImageWorker;
import bme.co.za.stopeImages.Interface.INonEditableStopeImages;
import bme.co.za.stopeImages.R;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Jeffrey.Mphahlele on 9/6/2017.
 */

public class NonEditableStopeImagesPresenterImp implements INonEditableStopeImages.Presenter {

    private INonEditableStopeImages.MyView view;
    private bme.co.za.stopeImages.RxJava.IStopeImage stopeImageImp;

    public NonEditableStopeImagesPresenterImp(INonEditableStopeImages.MyView  nonEditableStopeImagesActivity) {
        this.view = nonEditableStopeImagesActivity;
        this.stopeImageImp=new bme.co.za.stopeImages.RxJava.StopeImageImp();
    }

    /**
     * load all images of stope identified by TimesStamp
     */
    @Override
    public void loadImages() {

        //check is external storage is accessible
        if (ImageWorker.isExternalStorageReadable()&&view.getStopeInfo()!=null)
            //loop the list of image urls

            for(String url :view.getStopeInfo().getImagesList())
            {
                //create ImageView for every bitmap
                ImageView image = new ImageView(view.getContext());
                //Loading image from below url into imageView
                Glide.with(view.getContext())
                        .load(ImageWorker.getUrl(view.getContext(),url,true))
                        .fitCenter()
                        .override(700, 580)
                        .into(image);
                image.setScaleType(ImageView.ScaleType.FIT_XY);
                //load image view into the viewGroup
                view.setlinearLayout(image);
                if(view.getStopeInfo().getImagesList().size()<2) {
                   // Toast.makeText(view.getContext(),"Cant delete , please delete the whole stope",Toast.LENGTH_SHORT).show();
                    view.disableClearLast();
                }
            }

    }

    @Override
    public void deleteLastImage() {
        //get last image url
        String url= bme.co.za.stopeImages.Commons.SharedPreferenceWorker.removeLastStopeImageNameReference(view.getContext(), view.getConstantStopeKeyValue());
        //delete last image form external storage
        Disposable disposable = stopeImageImp.deleteStopeImage(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        view.refreshActivity();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    /**
     * Initialize current stope information
     */
    @Override
    public void setStopeData() {
        bme.co.za.stopeImages.Wrapper.StopeInfo stopeInfo = view.getStopeInfo();
        // check stope images list
        if(stopeInfo.getImagesList()==null|| stopeInfo.getImagesList().isEmpty()){
            //disable a clear button if the list is empty
            view.enableClearLastButton(false);
            //toast
            Toast.makeText(view.getContext(), R.string.sorry_this_stope_is_empty_please_delete_or_add_images,Toast.LENGTH_SHORT).show();
        }
    }
}
