package kamo.co.za.layoutsmovementdemo.di;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kamo.co.za.layoutsmovementdemo.rx.BaseSchedulerProvider;
import kamo.co.za.layoutsmovementdemo.rx.SchedulerProvider;


@Module
public class AppModule {

    @Provides
    @Singleton
    BaseSchedulerProvider getBaseSchedulerProvider(){
        return SchedulerProvider.getInstance();
    }
}
