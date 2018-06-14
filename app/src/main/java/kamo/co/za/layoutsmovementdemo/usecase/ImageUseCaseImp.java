package kamo.co.za.layoutsmovementdemo.usecase;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import java.io.File;
import java.util.ArrayList;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import kamo.co.za.layoutsmovementdemo.image.IImage;
import kamo.co.za.layoutsmovementdemo.workers.ImageWorker;


public class ImageUseCaseImp implements IImageUseCase {

    private IImage.View view;

    public ImageUseCaseImp(IImage.View view) {
        this.view = view;
    }

    @Override
    public Completable deleteImage(String ImageName) {
        return Completable.create(emitter -> {
            ImageWorker.deleteImage(view.getContext(),ImageName);
            emitter.onComplete();
        });
    }

    @Override
    public Maybe<String> copyImage(String mSelectedImagePath, File file) {
        return Maybe.just(ImageWorker.copyImage(mSelectedImagePath,file));
    }

    @Override
    public Maybe<Bitmap> loadBitmapFromView(View v) {
        return Maybe.just(ImageWorker.getBitmapFromView(v));
    }

    @Override
    public Completable saveFullyProcessedImage(Bitmap bitmap, String s) {
        return Completable.create(emitter -> {
            ImageWorker.saveBitmap(view.getContext(),bitmap,s);
            emitter.onComplete();
        });
    }

    @Override
    public Completable deleteCurrentImageSeries(ArrayList<String> listOfImages) {
        return Completable.create(emitter -> {
            ImageWorker.deleteImages(view.getContext(),listOfImages);
            emitter.onComplete();
        });
    }

    @Override
    public void loadImageWithGlide(String imageName, ImageView image) {
        Glide.with(view.getContext()).load(ImageWorker.getImageUrl(view.getContext(),imageName, true)).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(image);
    }

}

