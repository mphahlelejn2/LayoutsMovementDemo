package kamo.co.za.layoutsmovementdemo.main;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.ViewGroup;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import kamo.co.za.layoutsmovementdemo.image.ImageFragment;
import kamo.co.za.layoutsmovementdemo.R;
import kamo.co.za.layoutsmovementdemo.views.LockableScrollView;
import kamo.co.za.layoutsmovementdemo.views.MovableLayout;
import android.widget.TextView;
import javax.inject.Inject;



public class MainFragment extends ImageFragment implements IMain.View {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int RESULT_LOAD_IMAGE=2;
    public static final String TAG= "ImageLayoutFragment";

    @BindView(R.id.fab_delete)
    public FloatingActionButton deleteLastImage;
    @BindView(R.id._fab_capture)
    public FloatingActionButton addImage;
    @BindView(R.id.fab_upload)
    public FloatingActionButton uploadImage;
    @BindView(R.id.fab_save)
    public FloatingActionButton save;
    @BindView(R.id.stopeScrollView)
    public LockableScrollView stopeScrollView;
    @Inject
    public  IMain.Presenter presenter;
    @BindView(R.id.layout)
    public ViewGroup viewGroup;
    @BindView(R.id.textView)
    public TextView textView;
    private List<MovableLayout> ListOfLayouts=new ArrayList<>();

    @Override public void onViewCreated(android.view.View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listenerInit();
        loadInit();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.layout_fragment;
    }

    @Override
    protected void copyImageToRightDirectory(String mSelectedImagePath, File file) {
       presenter.copyImageToTheRightDirectory(mSelectedImagePath,file);
    }

    @Override
    protected void loadMovableImageByName(String imageName) {
        presenter.loadMovableImageByName(imageName);
    }

    @Override
    protected String getCurrentTimesStamp() {
        return presenter.getCurrentTimesStamp();
    }

    @Override
    protected void saveBitMapAsImage(Bitmap bitmap) {
        presenter.saveBitmapAsImage(bitmap);
    }

    private void loadInit() {

        if(presenter.getListOfImages()!=null&&!presenter.getListOfImages().isEmpty()){
            textView.setVisibility(android.view.View.INVISIBLE);
            presenter.loadListOfMovableLayouts(presenter.getListOfImages());
        }else
        {
            textView.setVisibility(android.view.View.VISIBLE);
        }
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Override
    public void addToLayout(MovableLayout movableLayout) {
        ListOfLayouts.add(movableLayout);
        viewGroup.addView(movableLayout);
        textView.setVisibility(android.view.View.INVISIBLE);
    }

    @Override
    public void removeFromLayout(MovableLayout movableLayout) {
        presenter.removeLastImageFromSharedPreference();
        ListOfLayouts.remove(movableLayout);
        viewGroup.removeView(movableLayout);
        restartIntent();
    }

    @Override
    public void restartIntent() {
        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }

    public void listenerInit() {

        uploadImage.setOnClickListener(view -> startBrowserIntent());
        addImage.setOnClickListener(view -> takeImageIntent());
        deleteLastImage.setOnClickListener(view ->
                {
                    if(!presenter.getListOfImages().isEmpty())
                        presenter.removeLastMovableImageLayout(presenter.getListOfImages().get(presenter.getListOfImages().size()-1));
                    else
                        toastMsg("Please add Images");
                }
        );


        save.setOnClickListener(view -> {
            if(!presenter.getListOfImages().isEmpty()){
                clearLayout();
                presenter.getBitmapFromViewGroup(viewGroup);
            }else{
                toastMsg("Please add Images");
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }


    @Override
    public void deletedCurrentImageSeries() {
        presenter.deletedCurrentImageSeriesFromSharedPreference();
        restartIntent();
    }

    @Override
    public List<MovableLayout> getListOfLayouts() {
        return ListOfLayouts;
    }

    @Override
    public void savedImage() {
        presenter.deleteCurrentImageSeries();
    }

    @Override
    public void returnViewAsBitMap(Bitmap bitmap) {
        presenter.saveBitmapAsImage(presenter.getCompressedBitmap(bitmap));
    }

    @Override
    public void loadImageToLayoutError() {

    }

    @Override
    public void noLayoutFound() {

    }


    @Override
    public void layoutScrollViewEnabled(boolean b) {
        stopeScrollView.setScrollingEnabled(b);
    }

    @Override
    public void clearLayout() {
        for (MovableLayout movableLayout:ListOfLayouts) {
            movableLayout.clearLayoutBackground();
        }
    }

}
