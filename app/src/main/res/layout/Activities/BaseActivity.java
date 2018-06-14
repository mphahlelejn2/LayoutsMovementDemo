package bme.co.za.stopeImages.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Date;

import bme.co.za.stopeImages.Commons.SharedPreferenceWorker;
import bme.co.za.stopeImages.Global.Constants;
import bme.co.za.stopeImages.R;


/**
 *This IMain holds common methods for sub activities
 */

public abstract class BaseActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        //checkPerformance();
        verifyStoragePermissions(this);
        //Set screen orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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

    /**
     * Handle return results from camera and open image containing activity
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
           // getApplication().getContentResolver().delete(data.getData(), null, null);
            openImageActivity(imageBitmap);

        }
    }

    /**
     * Opens image activity and close the current activity
     *
     * @param bitmap
     */
    protected void openImageActivity(Bitmap bitmap) {
        Intent intent = new Intent(this, StopeImageActivity.class);
        intent.putExtra("bitmap", bitmap);
        startActivity(intent);
        finish();
    }

    /**
     * Initiate taking picture intent
     */
    protected void takeImageIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Refreshes current activity after changes
     */
    public void refreshActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    /**
     * PreBlastFragments stope index to current timestamp
     */

    public void setDefaultConstantStopeKeyValue()
    {
        Constants.constantStopeKeyValue =getCurrentTimesStamp();
    }



    /**
     * return current constantStopeKeyValue in format
     * @return timestamp string
     */

    public String getCurrentTimesStamp()
    {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }


    /**
     * PreBlastFragments stope index given timestamp
     *
     * @param timestamp
     */
    public void setConstantStopeKeyValue(String timestamp)
    {
        Constants.constantStopeKeyValue =timestamp;
    }


    public bme.co.za.stopeImages.Wrapper.StopeInfo getStopeInfo() {
        //get stope Wrapper
        bme.co.za.stopeImages.Wrapper.StopeInfo stopeInfo = SharedPreferenceWorker.getStopeInfoReference(getApplicationContext(),getConstantStopeKeyValue());

        return stopeInfo;
    }

    /**
     * Getting current stope's timestamp
     *
     * @return timestamp
     */

    public String getConstantStopeKeyValue()
    {
        return Constants.constantStopeKeyValue;
    }
}
