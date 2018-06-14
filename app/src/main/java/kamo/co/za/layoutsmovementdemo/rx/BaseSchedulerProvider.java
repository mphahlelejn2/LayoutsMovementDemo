package kamo.co.za.layoutsmovementdemo.rx;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}