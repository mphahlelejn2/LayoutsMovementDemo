package kamo.co.za.layoutsmovementdemo.PresentersImp;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import kamo.co.za.layoutsmovementdemo.image.IImage;
import kamo.co.za.layoutsmovementdemo.main.IMain;
import kamo.co.za.layoutsmovementdemo.main.MainPresenterImp;
import kamo.co.za.layoutsmovementdemo.rx.BaseSchedulerProvider;
import kamo.co.za.layoutsmovementdemo.usecase.IImageUseCase;
import kamo.co.za.layoutsmovementdemo.usecase.ILayoutUseCase;
import kamo.co.za.layoutsmovementdemo.usecase.ImageUseCaseImp;
import kamo.co.za.layoutsmovementdemo.usecase.LayoutUseCaseImp;
import kamo.co.za.layoutsmovementdemo.views.MovableLayout;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by Jeffrey.Mphahlele on 3/13/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainPresenterImpTest {


    @Mock
    private IMain.View mainView;
    @Mock
    private IImage.View imageView;
    private BaseSchedulerProvider scheduler;

    private ILayoutUseCase layoutUseCase;
    private IMain.Presenter presenter;
    private IImageUseCase iImageUseCase;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        scheduler=SchedulerProviderTest.getInstance();
        iImageUseCase =new ImageUseCaseImp(imageView);
        layoutUseCase=new LayoutUseCaseImp(mainView,iImageUseCase);
        presenter =new MainPresenterImp(mainView, iImageUseCase,layoutUseCase,scheduler);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getCurrentTimesStamp() throws Exception {
        String currentTest=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        Assert.assertEquals(presenter.getCurrentTimesStamp(), currentTest);
    }

    @Test
    public void loadListOfMovableLayouts_Success() throws Exception {

        List<MovableLayout> movableLayoutList=getMovableLayoutList();
        ArrayList<String> arrayList=new ArrayList<>();
        //given
        when(layoutUseCase.getListOfMovableLayouts(arrayList)).thenReturn(Maybe.just((movableLayoutList)));
        //when
        presenter.loadListOfMovableLayouts(arrayList);
        //then
        verify(mainView, Mockito.never()).noLayoutFound();
        verify(mainView, Mockito.never()).loadImageToLayoutError();
        for(MovableLayout layout:movableLayoutList)
        verify(mainView).addToLayout(layout);

    }

    private List<MovableLayout> getMovableLayoutList() {
        List<MovableLayout> list=new ArrayList<>();
        list.add(new MovableLayout(mainView));
        list.add(new MovableLayout(mainView));
        return list;
    }

    @Test
    public void loadListOfMovableLayouts_Erorr() throws Exception {

        List<MovableLayout> movableLayoutList=getMovableLayoutList();
        ArrayList<String> arrayList=new ArrayList<>();
        //given
        when(layoutUseCase.getListOfMovableLayouts(arrayList)).thenReturn(Maybe.error(new Throwable()));
        //when
        presenter.loadListOfMovableLayouts(arrayList);
        //then
        verify(mainView, Mockito.never()).noLayoutFound();
        verify(mainView).loadImageToLayoutError();
        for(MovableLayout layout:movableLayoutList)
            verify(mainView, Mockito.never()).addToLayout(layout);

    }

    @Test
    public void loadListOfMovableLayouts_Empty() throws Exception {

        List<MovableLayout> movableLayoutList=getMovableLayoutList();
        ArrayList<String> arrayList=new ArrayList<>();
        //given
        when(layoutUseCase.getListOfMovableLayouts(arrayList)).thenReturn(Maybe.empty());
        //when
        presenter.loadListOfMovableLayouts(arrayList);
        //then
        verify(mainView).noLayoutFound();
        verify(mainView,Mockito.never()).loadImageToLayoutError();
        for(MovableLayout layout:movableLayoutList)
            verify(mainView, Mockito.never()).addToLayout(layout);

    }


    @Test
    public void getMovableLoadImageByName_Success() throws Exception {

        MovableLayout movableLayout =new MovableLayout(mainView);
        //given
        when(layoutUseCase.getMovableLayout(anyString())).thenReturn(Maybe.just((movableLayout)));
        //when
        presenter.loadMovableImageByName(anyString());
        //then
        verify(mainView, Mockito.never()).noLayoutFound();
        verify(mainView, Mockito.never()).loadImageToLayoutError();
        verify(mainView).addToLayout(movableLayout);

    }

    @Test
    public void getMovableLoadImageByName_Erorr() throws Exception {

        MovableLayout movableLayout =new MovableLayout(mainView);
        //given
        when(layoutUseCase.getMovableLayout(anyString())).thenReturn(Maybe.error(new Throwable()));
        //when
        presenter.loadMovableImageByName(anyString());
        //then
        verify(mainView, Mockito.never()).noLayoutFound();
        verify(mainView, Mockito.never()).addToLayout(movableLayout);
        verify(mainView).loadImageToLayoutError();

    }

    @Test
    public void getMovableLoadImageByName_Empty() throws Exception {

        MovableLayout movableLayout =new MovableLayout(mainView);
        //given
        when(layoutUseCase.getMovableLayout(anyString())).thenReturn(Maybe.empty());
        //when
        presenter.loadMovableImageByName(anyString());
        //then
        verify(mainView, Mockito.never()).addToLayout(movableLayout);
        verify(mainView, Mockito.never()).loadImageToLayoutError();
        verify(mainView).noLayoutFound();

    }

    @Test
    public void deleteCurrentImageSeries() {

    }

    @Test
    public void removeLastMovableImageLayout() {

    }


}