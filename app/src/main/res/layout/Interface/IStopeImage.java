package bme.co.za.stopeImages.Interface;

import android.content.Context;

/**
 * Created by Jeffrey.Mphahlele on 9/6/2017.
 */

public interface IStopeImage {
    interface MyView {
        void init();
        void listenerInit();
        String getConstantStopeKeyValue();
        String getCurrentTimesStamp();
        Context getContext();
        void initProgressDialog();
        void progressDismiss();
        void progressDialogshow();
        void closeActivity();
    }
    interface Presenter {
        void saveImageUrl();
        void saveImage();
        void setMyBuilder();
        void openStopeImagesActivity();

    }
}
