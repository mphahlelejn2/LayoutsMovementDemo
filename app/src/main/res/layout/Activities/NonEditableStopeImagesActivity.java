package bme.co.za.stopeImages.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import bme.co.za.stopeImages.Interface.INonEditableStopeImages;
import bme.co.za.stopeImages.PresentersImp.NonEditableStopeImagesPresenterImp;
import bme.co.za.stopeImages.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This activity allow user to view uneditable stope images
 *
 * Delete,add or go back to the main page
 *
 * Created by Jeffrey.Mphahlele on 5/31/2017.
 */


public class NonEditableStopeImagesActivity extends bme.co.za.stopeImages.Activities.BaseActivity implements INonEditableStopeImages.MyView {

    @BindView(R.id.bReturn)
    public  Button home;
    @BindView(R.id.bTake)
    public  Button takePicture;
    @BindView(R.id.bClearLast)
    public  Button clearLast;
    @BindView(R.id.layout)
    public  LinearLayout linearLayout;

    private INonEditableStopeImages.Presenter presenter;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.view_stope_activity);
        ButterKnife.bind(this);
        setConstantStopeKeyValue(getIntent().getStringExtra("keyTimesStamp"));
        presenter =new NonEditableStopeImagesPresenterImp(this);
        presenter.setStopeData();
        presenter.loadImages();
        listenerInit();
    }



    /**
     * Handles all button listeners
     */
    @Override
    public void listenerInit() {
        home.setOnClickListener(v -> closeActivity());
        takePicture.setOnClickListener(v -> takeImageIntent());
        clearLast.setOnClickListener(v -> clearLastBuilder());
    }


    /**
     * Dialog Massage to clear last image
     */
    @Override
    public void clearLastBuilder() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.Are_you_sure_you_want_to_clear_the_last_image);
        alertDialogBuilder.setPositiveButton(R.string.clear, (arg0, arg1) -> {
          presenter.deleteLastImage();
        });

        alertDialogBuilder.setNegativeButton(R.string.cancel, (dialog, which) -> {

        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void enableClearLastButton(Boolean b) {
        clearLast.setEnabled(b);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void closeActivity() {
        //check is activity is not a root activity
        if (isTaskRoot()) {
            Intent intent = new Intent(bme.co.za.stopeImages.Activities.NonEditableStopeImagesActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            finish();
        }

    }

    @Override
    public void disableClearLast() {
        clearLast.setEnabled(false);
    }

    @Override
    public void setlinearLayout(ImageView image) {
        linearLayout.addView(image);
    }

}
