package bme.co.za.stopeImages.Interface;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Jeffrey.Mphahlele on 9/6/2017.
 */

public interface INonEditableStopeImages {
    interface MyView {
        void listenerInit();
        void clearLastBuilder();
        void enableClearLastButton(Boolean b);
        Context getContext();
        void setlinearLayout(ImageView image);
        bme.co.za.stopeImages.Wrapper.StopeInfo getStopeInfo();
        void closeActivity();
        void refreshActivity();
        String getConstantStopeKeyValue();
        void disableClearLast();
    }

    interface Presenter{
        void setStopeData();
        void loadImages();
        void deleteLastImage();
    }
}
