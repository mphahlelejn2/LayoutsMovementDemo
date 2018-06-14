
package kamo.co.za.layoutsmovementdemo.image;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ViewGroup;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableMaybeObserver;

import kamo.co.za.layoutsmovementdemo.rx.BaseSchedulerProvider;
import kamo.co.za.layoutsmovementdemo.usecase.IImageUseCase;



abstract public class ImagePresenterImp implements IImage.Presenter {

    protected IImage.View imageView;
    protected IImageUseCase iImageUseCase;
    protected BaseSchedulerProvider scheduler;
    protected CompositeDisposable compositeDisposable=new CompositeDisposable();



    @Override
    public void copyImageToTheRightDirectory(String mSelectedImagePath, File file) {
        Disposable disposable = iImageUseCase.copyImage(mSelectedImagePath,file)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableMaybeObserver<String>() {
                    @Override
                    public void onSuccess(String imageName) {
                        //imageView.loadImage(imageName);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        compositeDisposable.add(disposable);


    }


    @Override
    public void getBitmapFromViewGroup(ViewGroup viewGroup){
        imageView.initSaveImageProgressDialog();
        Disposable disposable = iImageUseCase.loadBitmapFromView(viewGroup)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableMaybeObserver<Bitmap>() {

                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        imageView.returnViewAsBitMap(bitmap);
                        imageView.toastMsg("Image Save in local storage");

                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {

                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void saveBitmapAsImage(Bitmap bitmap) {
        Disposable disposable = iImageUseCase.saveFullyProcessedImage( bitmap, getCurrentTimesStamp()+"_FullyProcessedImage.jpg")
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .doOnComplete(() -> {
                    imageView.savedImage();
                })
                .doOnError(throwable -> {

                })

                .subscribe();

        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }

    @Override
    public String getCurrentTimesStamp()
    {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }

    @Override
    public Bitmap getCompressedBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,70,stream);
        byte[] byteArray = stream.toByteArray();
        return  BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
    }
}
