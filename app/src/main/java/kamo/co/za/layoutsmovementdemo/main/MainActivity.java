package kamo.co.za.layoutsmovementdemo.main;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import kamo.co.za.layoutsmovementdemo.R;
import kamo.co.za.layoutsmovementdemo.utils.ActivityUtils;


public class MainActivity extends AppCompatActivity {

    private MainFragment imageLayoutFragment;
    private FragmentManager fragmentManager;
    private static final String LAYOUT ="LAYOUT";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity);
        fragmentManager=getSupportFragmentManager();

        //imageLayoutFragment= (MainFragment) fragmentManager.findFragmentById(Integer.parseInt(LAYOUT));

        if(imageLayoutFragment ==null)
            imageLayoutFragment = MainFragment.newInstance();

        ActivityUtils.addFragment(fragmentManager, imageLayoutFragment,R.id.fragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(imageLayoutFragment != null)
            imageLayoutFragment.onActivityResult(requestCode, resultCode, data);
    }

}
