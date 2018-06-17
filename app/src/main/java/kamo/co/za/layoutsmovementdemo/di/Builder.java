package kamo.co.za.layoutsmovementdemo.di;

import dagger.Module;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;
import kamo.co.za.layoutsmovementdemo.main.MainFragment;
import kamo.co.za.layoutsmovementdemo.main.MainModule;


@Module(includes = AndroidInjectionModule.class)
public abstract class Builder {

    @PerActivity
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainFragment bindColorDetailsFragment();
}