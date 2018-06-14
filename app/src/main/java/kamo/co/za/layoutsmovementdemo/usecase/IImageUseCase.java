package kamo.co.za.layoutsmovementdemo.usecase;


import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import java.io.File;
import java.util.ArrayList;
import io.reactivex.Completable;
import io.reactivex.Maybe;


public interface IImageUseCase {
    Completable deleteImage(String ImageName);
    Maybe<String> copyImage(String mSelectedImagePath, File file);
    Maybe<Bitmap> loadBitmapFromView(View v);
    Completable saveFullyProcessedImage( Bitmap bitmap, String s);
    Completable deleteCurrentImageSeries( ArrayList<String> listOfImages);
    void loadImageWithGlide(String imageName, ImageView image);
}
