package kamo.co.za.layoutsmovementdemo.usecase;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Maybe;
import kamo.co.za.layoutsmovementdemo.main.IMain;
import kamo.co.za.layoutsmovementdemo.views.MovableLayout;


public interface ILayoutUseCase {
    Maybe<MovableLayout> getMovableLayout(String imageName);
    Maybe<List<MovableLayout>> getListOfMovableLayouts(ArrayList<String> listOfImages);
    List<MovableLayout> createListOfMovableLayouts(ArrayList<String> imageList);
    MovableLayout createMovableLayout(IImageUseCase image, IMain.View view, String imageName);
}
