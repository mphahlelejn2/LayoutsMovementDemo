package kamo.co.za.layoutsmovementdemo.usecase;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Maybe;
import kamo.co.za.layoutsmovementdemo.main.IMain;
import kamo.co.za.layoutsmovementdemo.views.MovableLayout;
import kamo.co.za.layoutsmovementdemo.workers.ImageLayoutWorker;


public class LayoutUseCaseImp implements ILayoutUseCase {

    private IMain.View view;
    private IImageUseCase image;

    public LayoutUseCaseImp(IMain.View view, IImageUseCase image) {
        this.view = view;
        this.image=image;
    }

    @Override
    public Maybe<MovableLayout> getMovableLayout(String imageName) {
        return Maybe.just(createMovableLayout(image,view,imageName));
    }

    @Override
    public Maybe<List<MovableLayout>> getListOfMovableLayouts(ArrayList<String> listOfImages) {
        return Maybe.just(createListOfMovableLayouts(listOfImages));
    }

    @Override
    public List<MovableLayout> createListOfMovableLayouts(ArrayList<String> imageList) {
        List<MovableLayout> list=new ArrayList<>();
        for (String imageName:imageList) {
            list.add(createMovableLayout(image,view,imageName));
        }
       return list;
    }

    @Override
    public  MovableLayout createMovableLayout(IImageUseCase image, IMain.View view, String imageName){
        return ImageLayoutWorker.createMovableLayout(image, view, imageName);
    }
}
