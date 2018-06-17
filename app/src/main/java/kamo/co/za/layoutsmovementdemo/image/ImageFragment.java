package kamo.co.za.layoutsmovementdemo.image;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import java.io.File;
import java.lang.reflect.Method;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import dmax.dialog.SpotsDialog;
import kamo.co.za.layoutsmovementdemo.workers.ImageWorker;

import static kamo.co.za.layoutsmovementdemo.main.MainFragment.REQUEST_IMAGE_CAPTURE;
import static kamo.co.za.layoutsmovementdemo.main.MainFragment.RESULT_LOAD_IMAGE;



public abstract class ImageFragment extends Fragment implements IImage.View{

    private File file;
    String mSelectedImagePath;
    private String imageName;
    public SpotsDialog saveImageProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayout(), container, false);
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
        verifyStoragePermissions(getActivity());
        saveImageProgressDialog=new SpotsDialog(getActivity(),"Saving Image");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void initSaveImageProgressDialog() {
        if(saveImageProgressDialog !=null)
            saveImageProgressDialog.show();
    }


    @Override
    public void dismissImageSaveDialog() {
        if(saveImageProgressDialog !=null)
            saveImageProgressDialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE ) {
            loadMovableImageByName(file.getName());
        }
        else if(requestCode ==RESULT_LOAD_IMAGE)
        {
            mSelectedImagePath = getPath(data.getData());
            imageName =getCurrentTimesStamp()+".jpg";
            file = ImageWorker.createImage(getActivity(), imageName,true);
            copyImageToRightDirectory(mSelectedImagePath,file);
            loadMovableImageByName(file.getName());
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        getActivity().startManagingCursor(cursor);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void takeImageIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageName =getCurrentTimesStamp()+".jpg";
        file = ImageWorker.createImage(getActivity(), imageName,true);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
        }
    }

    public void startBrowserIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), RESULT_LOAD_IMAGE);
    }

    private void init() {
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Checking storage permission
     */
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks storage permission
     */
    public void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    protected abstract int getFragmentLayout();
    protected abstract void copyImageToRightDirectory(String mSelectedImagePath, File file);
    protected abstract void loadMovableImageByName(String imageName);
    protected abstract String getCurrentTimesStamp();
    protected abstract void saveBitMapAsImage(Bitmap bitmap);

    @Override
    public abstract void savedImage();

    public Context getContext(){
        return getActivity().getBaseContext();
    }

    @Override
    public void toastMsg(String msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnViewAsBitMap(Bitmap bitmap) {
        saveBitMapAsImage(bitmap);
    }


}
