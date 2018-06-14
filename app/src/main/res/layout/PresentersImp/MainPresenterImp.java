package bme.co.za.stopeImages.PresentersImp;

import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import bme.co.za.stopeImages.Adapter.MyAdapter;
import bme.co.za.stopeImages.Commons.SharedPreferenceWorker;
import bme.co.za.stopeImages.RxJava.IStopeImage;
import bme.co.za.stopeImages.RxJava.StopeImageImp;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Jeffrey.Mphahlele on 11/15/2017.
 */

public class MainPresenterImp implements bme.co.za.stopeImages.Interface.IMain.Presenter{
    private RecyclerView.Adapter mAdapter;
    private bme.co.za.stopeImages.Interface.IMain.MyView view;
    private IStopeImage stopeImageImp;

    public MainPresenterImp(bme.co.za.stopeImages.Interface.IMain.MyView view) {
        this.view = view;
        stopeImageImp=new StopeImageImp();
    }


    /**
     * Load adaptor
     */
    @Override
    public void loadAdaptor() {
        mAdapter = new MyAdapter(view.getMainActivity(), SharedPreferenceWorker.getAllStopesKeysReference(view.getContext()));
        view.setUpAdapter(mAdapter);
    }

    @Override
    public void deleteStope(String key) {
        view.initDeleteImageProgressDialog();
        //get a list of urls to delete
        ArrayList<String> urls = SharedPreferenceWorker.getStopeInfoReference(view.getContext(), key).getImagesList();
        //delete urls reference from shared preference
        SharedPreferenceWorker.deleteStopeWrapperReference(view.getContext(), key);

        Disposable disposable = stopeImageImp.deleteStopeImages(urls)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Disposable disposable = stopeImageImp.deleteStopeImage(key)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableCompletableObserver() {
                                    @Override
                                    public void onComplete() {
                                        view.dismissDeleteImageProgressDialog();
                                        //refresh the activity
                                        view.refreshActivity();
                                        //notify a change
                                        Toast.makeText(view.getContext(), "ImageLayoutInfo deleted ", Toast.LENGTH_SHORT).show();
                                        mAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        view.dismissDeleteImageProgressDialog();
                                        Toast.makeText(view.getContext(), "ImageLayoutInfo not deleted ", Toast.LENGTH_SHORT).show();

                                    }
                                });
                        //disposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDeleteImageProgressDialog();
                        Toast.makeText(view.getContext(), "ImageLayoutInfo not deleted ", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
