package kamo.co.za.layoutsmovementdemo.workers;
import android.widget.ImageView;
import android.widget.LinearLayout;
import kamo.co.za.layoutsmovementdemo.main.IMain;
import kamo.co.za.layoutsmovementdemo.usecase.IImageUseCase;
import kamo.co.za.layoutsmovementdemo.views.MovableLayout;

/**
 * Created by Jeffrey.Mphahlele on 2/25/2018.
 */

public class ImageLayoutWorker {
    public static MovableLayout createMovableLayout(IImageUseCase imageUseCase, IMain.View view, String imageName){
        MovableLayout child=null;

        if(imageUseCase !=null&&view!=null&&imageName!=null&&!imageName.isEmpty()) {
           child = new MovableLayout(view);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(900, 715);
            child.setLayoutParams(params);
            child.setOrientation(LinearLayout.VERTICAL);


            ImageView image = new ImageView(view.getContext());
            LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            image.setLayoutParams(paramsImage);

            imageUseCase.loadImageWithGlide(imageName, image);

            image.setScaleType(ImageView.ScaleType.FIT_XY);
            child.addView(image);
            child.init();
            return child;
        }else
        {return child;}
    }
}
