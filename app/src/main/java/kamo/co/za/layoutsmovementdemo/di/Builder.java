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

    /*@PerActivity
    @ContributesAndroidInjector(modules = ColorModule.class)
    abstract ListOfColorsFragment bindListOfColorsFragment();

    @PerActivity
    @ContributesAndroidInjector(modules = UserModule.class)
    abstract ListOfUsersFragment bindListOfUsersFragment();*/
}
