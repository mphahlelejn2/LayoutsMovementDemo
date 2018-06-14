package bme.co.za.stopeImages.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import bme.co.za.stopeImages.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This Activity displays slopes images list
 *
 * Delete,view or edit individual slope images
 */
public class MainActivity extends bme.co.za.stopeImages.Activities.BaseActivity implements bme.co.za.stopeImages.Interface.IMain.MyView{

    @BindView(R.id.my_recycler_view)
    public RecyclerView mRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.bTakePicture)
    public  Button takePicture;

    private ProgressDialog deleteImageProgressDialog;
    private bme.co.za.stopeImages.Interface.IMain.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        init();

        presenter=new bme.co.za.stopeImages.PresentersImp.MainPresenterImp(this);
        presenter.loadAdaptor();
        listenersInit();
    }
    private void listenersInit(){
        takePicture.setOnClickListener(v -> {
            setDefaultConstantStopeKeyValue();
            takeImageIntent();
        });
    }
    /**
     * initialization of variables
     */
    private void init() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }


    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public bme.co.za.stopeImages.Activities.MainActivity getMainActivity() {
        return this;
    }

    /**
     * Load stope data into adaptor
     */
    @Override
    public void setUpAdapter(RecyclerView.Adapter mAdapter) {
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void deleteDialog(String key) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.Are_you_sure_you_want_to_delete_all_stope_images);
        alertDialogBuilder.setPositiveButton(R.string.delete, (arg0, arg1) -> {
          presenter.deleteStope(key);
        });

        alertDialogBuilder.setNegativeButton(R.string.cancel, (dialog, which) -> {
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
    @Override
    public void initDeleteImageProgressDialog() {
        //setup progress abr to display while in action
        deleteImageProgressDialog = new ProgressDialog(this);
        deleteImageProgressDialog.setMessage("delete stope Image");
        deleteImageProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        deleteImageProgressDialog.setIndeterminate(true);
        deleteImageProgressDialog.setCancelable(false);
        deleteImageProgressDialog.show();
    }

    @Override
    public void dismissDeleteImageProgressDialog() {
        deleteImageProgressDialog.dismiss();
    }
}
