package bme.co.za.stopeImages.Interface;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Jeffrey.Mphahlele on 9/5/2017.
 */

public interface IEditableStopeImages {


 interface MyView{
  void scrollViewEnabled(boolean b);
  void saveImageDialog();
  void backButtonBuilder();
  void clearSavedImageDialog();
  void init();
  Context getContext();
  void clearLayoutsSettings();
  void refreshActivity();
  void setupUneditableStopeViews();
  void setUpEditableStopeDoneButton();
  String getConstantStopeKeyValue();
  bme.co.za.stopeImages.Wrapper.StopeInfo getStopeInfo();
  void clearLayout();
  void addToLayoutList(bme.co.za.stopeImages.Views.ZoomLayout zoomLayout);
  void initSaveImageProgressDialog();
  void dismissSaveImageProgressDialog();
  void initDeleteImageProgressDialog();
  void dismissDeleteImageProgressDialog();
  void addView(ImageView imageView);
  void addImageLayout(bme.co.za.stopeImages.Views.ZoomLayout zoomLayout);
  ViewGroup getViewGroup();
 }

 interface Presenter{
  void saveProcess();
  void setMyBuilder();
  void displayFullImage();
  void deleteImage();
  void stopeInit();
 }

}

