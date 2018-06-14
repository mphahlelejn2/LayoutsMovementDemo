package bme.co.za.stopeImages.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import bme.co.za.stopeImages.Interface.IStopeImage;
import bme.co.za.stopeImages.PresentersImp.StopeImagePresenterImp;
import bme.co.za.stopeImages.R;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * This activity displays single image on the activity
 *
 * with functionality to saveViewGroupBitmap,clear and return to main page
 *
 * Created by Jeffrey.Mphahlele on 5/31/2017.
 */

public class StopeImageActivity extends bme.co.za.stopeImages.Activities.BaseActivity implements IStopeImage.MyView {

    @BindView(R.id.IvPicture)
    public  ImageView picture;
    @BindView(R.id.bRetake)
    public  Button retake;
    @BindView(R.id.bSave)
    public  Button save;

    private IStopeImage.Presenter stopeImagePresenter;
    private ProgressDialog saveImageProgressDialog;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_activity);
        ButterKnife.bind(this);
        init();
        stopeImagePresenter=new StopeImagePresenterImp(this,getInitBitmap());
        listenerInit();
    }


    /**
     * initialization
     */
    @Override
    public void init() {
        picture.setImageBitmap(getInitBitmap());
    }

    @Override
    public void initProgressDialog()
    {
        //setup progress abr to display while in action
        saveImageProgressDialog = new ProgressDialog(this);
        saveImageProgressDialog.setMessage("Saving stope Image to SD Card");
        saveImageProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        saveImageProgressDialog.setIndeterminate(true);
        saveImageProgressDialog.setCancelable(false);
        saveImageProgressDialog.show();
    }
    @Override
    public void progressDialogshow() {
        saveImageProgressDialog.show();
    }
    @Override
    public void progressDismiss() {
        saveImageProgressDialog.dismiss();
    }

    /**
     * Save image information
     */

    @Override
    public void closeActivity(){
        finish();
    }

    @Override
    public void listenerInit() {
        save.setOnClickListener(v -> {
            //saveViewGroupBitmap bitmap
            stopeImagePresenter.saveImage();
        });
        retake.setOnClickListener(v -> takeImageIntent());
    }
    public Bitmap getInitBitmap() {
        return getIntent().getParcelableExtra("bitmap");
    }
    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
