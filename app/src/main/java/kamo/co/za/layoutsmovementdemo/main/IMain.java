package kamo.co.za.layoutsmovementdemo.main;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import kamo.co.za.layoutsmovementdemo.image.IImage;
import kamo.co.za.layoutsmovementdemo.views.MovableLayout;

public interface IMain {

    interface View extends IImage.View{
        Context getContext();
        void restartIntent();
        Activity getActivity();
        void layoutScrollViewEnabled(boolean b);
        void clearLayout();
        void removeFromLayout(MovableLayout movableLayout);
        void deletedCurrentImageSeries();
        List<MovableLayout> getListOfLayouts();
        void addToLayout(MovableLayout movableLayout);
        void loadImageToLayoutError();
        void noLayoutFound();
    }

    interface Presenter extends IImage.Presenter{
        ArrayList<String> getListOfImages();
        void loadListOfMovableLayouts(ArrayList<String> listOfImages);
        void getBitmapFromViewGroup(ViewGroup viewGroup);
        void deleteCurrentImageSeries();
        void removeLastImageFromSharedPreference();
        void deletedCurrentImageSeriesFromSharedPreference();
        void loadMovableImageByName(String imageName);
        MovableLayout getLastMovableLayout();
        void removeLastMovableImageLayout(String imageName);
    }
}
