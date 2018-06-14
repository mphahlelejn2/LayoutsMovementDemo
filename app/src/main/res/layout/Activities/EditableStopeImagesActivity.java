package bme.co.za.stopeImages.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bme.co.za.stopeImages.PresentersImp.EditableStopeImagesPresenterImp;
import bme.co.za.stopeImages.R;
import bme.co.za.stopeImages.Views.LockableScrollView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This activity allows zoom,crop and move of stope images across the frame
 */
public class EditableStopeImagesActivity extends bme.co.za.stopeImages.Activities.BaseActivity implements bme.co.za.stopeImages.Interface.IEditableStopeImages.MyView
{
    // wrapper containing information about stope images
    private bme.co.za.stopeImages.Interface.IEditableStopeImages.Presenter editableStopeImagesPresenter;
    private ProgressDialog saveImageProgressDialog;
    private ProgressDialog deleteImageProgressDialog;
    private List<bme.co.za.stopeImages.Views.ZoomLayout> layoutsList=new ArrayList<>();

    //controls enabling and disabling of scrollview
    @BindView(R.id.hsv)
    public LockableScrollView lockableScrollView;
    @BindView(R.id.layout)
    public  ViewGroup viewGroup;
    @BindView(R.id.fab)
    public FloatingActionButton saveButton;
    @BindView(R.id.fab2)
    public FloatingActionButton backButton;

    public  void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_stope_activity);
        ButterKnife.bind(this);
        init();
        editableStopeImagesPresenter=new EditableStopeImagesPresenterImp(this);
        editableStopeImagesPresenter.stopeInit();
        listenerInit();
    }

    private void listenerInit() {
        saveButton.setOnClickListener(view -> editableStopeImagesPresenter.displayFullImage());
        backButton.setOnClickListener(view -> backButtonBuilder());
    }

    /**
     * initialise all parameters
     */
    @Override
    public void init() {
        //set a new stope index
        setConstantStopeKeyValue(getIntent().getStringExtra("keyTimesStamp"));
        lockableScrollView.setScrollingEnabled(true);
        backButton.setImageResource(R.drawable.ic_keyboard_backspace_white_24dp);
        scrollViewEnabled(true);
    }


    @Override
    public Context getContext() {
        return getApplicationContext();
    }



    /**
     * Loads all images from external storage into multiple layouts
     */

    @Override
    public void scrollViewEnabled(boolean b) {
        lockableScrollView.setScrollingEnabled(b);
    }

    @Override
    public void saveImageDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.Are_you_sure_you_want_to_save_cropped_stope_image);
        alertDialogBuilder.setPositiveButton(R.string.save, (arg0, arg1) -> editableStopeImagesPresenter.saveProcess());

        alertDialogBuilder.setNegativeButton(R.string.cancel, (dialog, which) -> {

        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void backButtonBuilder() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.Are_you_sure_you_want_to_go_back_to_home_page);
        alertDialogBuilder.setPositiveButton(R.string.back, (arg0, arg1) -> {
            //finish current
            finish();
        });

        alertDialogBuilder.setNegativeButton(R.string.cancel, (dialog, which) -> {

        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    /**
     * Dialog Massage to clear the whole stope images
     */
    @Override
    public void clearSavedImageDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.Are_you_sure_you_want_to_clear_saved_stope);
        alertDialogBuilder.setPositiveButton(R.string.clear, (arg0, arg1) -> {
            //initiate clearing of the saved stope image
            editableStopeImagesPresenter.deleteImage();
        });

        alertDialogBuilder.setNegativeButton(R.string.cancel, (dialog, which) -> {

        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void setupUneditableStopeViews() {
        //notify users by toast
        Toast.makeText(bme.co.za.stopeImages.Activities.EditableStopeImagesActivity.this,R.string.you_are_viewing_uneditable_stope_image,Toast.LENGTH_SHORT).show();
        //enable scroll view
        scrollViewEnabled(true);
        //change button image
        saveButton.setImageResource(R.drawable.ic_replay_white_24dp);
    }

    @Override
    public void setUpEditableStopeDoneButton() {
        saveButton.setImageResource(R.drawable.ic_done_white_24dp);
    }

    @Override
    public void addToLayoutList(bme.co.za.stopeImages.Views.ZoomLayout zoomLayout) {
        layoutsList.add(zoomLayout);
    }
    /**
     * clear all setUpLayout on layouts
     */
    @Override
    public void clearLayout() {
        for(bme.co.za.stopeImages.Views.ZoomLayout l:layoutsList)
            l.clearLayoutBackground();
    }


    @Override
    public void initSaveImageProgressDialog() {
        //setup progress abr to display while in action
        saveImageProgressDialog = new ProgressDialog(this);
        saveImageProgressDialog.setMessage("Saving cropped stope Image to SD Card");
        saveImageProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        saveImageProgressDialog.setIndeterminate(true);
        saveImageProgressDialog.setCancelable(false);
        saveImageProgressDialog.show();
    }
    @Override
    public void dismissSaveImageProgressDialog() {
        saveImageProgressDialog.dismiss();
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

    @Override
    public void addView(ImageView imageView) {
        viewGroup.addView(imageView);
    }

    @Override
    public void addImageLayout(bme.co.za.stopeImages.Views.ZoomLayout zoomLayout) {
        viewGroup.addView(zoomLayout);
    }

    @Override
    public ViewGroup getViewGroup() {
        return viewGroup;
    }

    public void setViewGroup(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
    }
    @Override
    public void clearLayoutsSettings() {
        clearLayout();
    }
}
