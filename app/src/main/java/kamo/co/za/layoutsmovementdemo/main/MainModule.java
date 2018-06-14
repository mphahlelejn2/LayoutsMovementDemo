package kamo.co.za.layoutsmovementdemo.main;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import kamo.co.za.layoutsmovementdemo.usecase.IImageUseCase;
import kamo.co.za.layoutsmovementdemo.usecase.LayoutUseCaseImp;
import kamo.co.za.layoutsmovementdemo.rx.BaseSchedulerProvider;
import kamo.co.za.layoutsmovementdemo.usecase.ILayoutUseCase;
import kamo.co.za.layoutsmovementdemo.usecase.ImageUseCaseImp;


@Module
abstract public class MainModule {

    @Binds
        abstract IMain.View getView(MainFragment imageLayoutFragment);

    @Provides
    static IMain.Presenter getPresenter(IMain.View view, IImageUseCase image, ILayoutUseCase Layout, BaseSchedulerProvider scheduler){
        return new MainPresenterImp(view,image,Layout,scheduler);
    }

    @Provides
    static IImageUseCase getImage(IMain.View view){
        return new ImageUseCaseImp(view);
    }

    @Provides
    static ILayoutUseCase getLayoutImp(IMain.View view, IImageUseCase image){
        return new LayoutUseCaseImp(view,image);
    }

/*

    @Binds
    abstract IMain.View getView(MainFragment mainFragment);

    @Provides
    static IMain.Presenter getPresenter(IMain.View view, IImageUseCase image, ILayoutUseCase Layout, BaseSchedulerProvider scheduler){
        return new MainPresenterImp(view,image,Layout,scheduler);
    }

    @Provides
    static IImageUseCase getImage(IImage.View view){
        return new ImageUseCaseImp(view);
    }

    @Provides
    static ILayoutUseCase getLayoutImp(IMain.View view, IImageUseCase image){
        return new LayoutUseCaseImp(view,image);
    }

 */
}
