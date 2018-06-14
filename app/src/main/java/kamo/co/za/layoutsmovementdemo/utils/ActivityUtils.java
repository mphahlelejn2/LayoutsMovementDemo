package kamo.co.za.layoutsmovementdemo.utils;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class ActivityUtils extends AppCompatActivity {
    public static void addFragment(FragmentManager fragmentManager, Fragment fragment, int fragmentId){
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(fragmentId, fragment);
        transaction.commit();
    }
}
