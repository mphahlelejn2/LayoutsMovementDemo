package kamo.co.za.layoutsmovementdemo.main;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import kamo.co.za.layoutsmovementdemo.commons.Constants;
import kamo.co.za.layoutsmovementdemo.image.ImagePresenterImp;

import kamo.co.za.layoutsmovementdemo.rx.BaseSchedulerProvider;
import kamo.co.za.layoutsmovementdemo.usecase.IImageUseCase;
import kamo.co.za.layoutsmovementdemo.usecase.ILayoutUseCase;
import kamo.co.za.layoutsmovementdemo.views.MovableLayout;
import kamo.co.za.layoutsmovementdemo.workers.SharedPreferenceWorker;

public class MainPresenterImp extends ImagePresenterImp implements IMain.Presenter {

    private IMain.View mainView;
    private ILayoutUseCase layoutUseCase;

    public MainPresenterImp(IMain.View mainView,
                            IImageUseCase iImageUseCase,
                            ILayoutUseCase layout,
                            BaseSchedulerProvider scheduler) {
        this.mainView = mainView;
        this.layoutUseCase = layout;
        this.iImageUseCase=iImageUseCase;
        this.scheduler=scheduler;
        this.imageView = mainView;
    }


    @Override
    public MovableLayout getLastMovableLayout() {
        return mainView.getListOfLayouts().get(mainView.getListOfLayouts().size()-1);
    }


    @Override
    public void loadListOfMovableLayouts(ArrayList<String> listOfImages) {
        Disposable disposable = layoutUseCase.getListOfMovableLayouts(listOfImages)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableMaybeObserver<List<MovableLayout>>() {

                    @Override
                    public void onSuccess(List<MovableLayout> movableLayouts) {
                        for (MovableLayout movableLayout:movableLayouts) {
                            mainView.addToLayout(movableLayout);
                        }
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
    public void loadMovableImageByName(String imageName) {
        //layoutUseCase.
        Disposable disposable = layoutUseCase.getMovableLayout(imageName)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableMaybeObserver<MovableLayout>() {
                    @Override
                    public void onSuccess(MovableLayout movableLayout) {
                        SharedPreferenceWorker.addImageName(imageView.getContext(),imageName, Constants.key);
                        mainView.addToLayout(movableLayout);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mainView.loadImageToLayoutError();
                    }

                    @Override
                    public void onComplete() {
                        mainView.noLayoutFound();
                    }
                });
        compositeDisposable.add(disposable);
    }


    @Override
    public void deleteCurrentImageSeries() {
        Disposable disposable = iImageUseCase.deleteCurrentImageSeries(getListOfImages())
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .doOnComplete(() -> {
                    mainView.deletedCurrentImageSeries();
                    mainView.dismissImageSaveDialog();
                })
                .doOnError(throwable -> {

                })
                .subscribe();

        compositeDisposable.add(disposable);
    }


    @Override
    public void removeLastMovableImageLayout(String imageName) {
        //layoutUseCase.setImageName( getLastImage());
        final MovableLayout movableLayout=getLastMovableLayout();
        Disposable disposable = iImageUseCase.deleteImage(imageName)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        mainView.removeFromLayout(movableLayout);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        compositeDisposable.add(disposable);


    }
    @Override
    public void removeLastImageFromSharedPreference() {
        SharedPreferenceWorker.removeLastImage(mainView.getContext(), Constants.key);
    }

    @Override
    public void deletedCurrentImageSeriesFromSharedPreference() {
        SharedPreferenceWorker.deleteLayoutInfo(mainView.getContext(), Constants.key);
    }

    @Override
    public ArrayList<String> getListOfImages() {
        return SharedPreferenceWorker.getImageList(mainView.getContext(), Constants.key);
    }


}
