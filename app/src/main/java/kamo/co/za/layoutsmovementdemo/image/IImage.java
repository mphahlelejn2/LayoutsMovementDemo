package kamo.co.za.layoutsmovementdemo.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import java.io.File;


public interface IImage {

    interface View{
        void returnViewAsBitMap(Bitmap bitmap);
        void initSaveImageProgressDialog();
        void dismissImageSaveDialog();
        Context getContext();
        void toastMsg(String msg);
        void savedImage();
    }

    interface Presenter{
        void getBitmapFromViewGroup(ViewGroup viewGroup);
        void onDestroy();
        String getCurrentTimesStamp();
        void copyImageToTheRightDirectory(String mSelectedImagePath, File file);
        Bitmap getCompressedBitmap(Bitmap bitmap);
        void saveBitmapAsImage(Bitmap bitmap);
    }

}
