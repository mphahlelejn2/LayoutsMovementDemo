package bme.co.za.stopeImages.Interface;


import android.content.Context;
import android.support.v7.widget.RecyclerView;

import bme.co.za.stopeImages.Activities.MainActivity;

/**
 * Created by Jeffrey.Mphahlele on 11/15/2017.
 */

public interface IMain {
    interface MyView{
        Context getContext();
        MainActivity getMainActivity();
        void setUpAdapter(RecyclerView.Adapter mAdapter);
        void refreshActivity();
        void deleteDialog(String key);
        void initDeleteImageProgressDialog();
        void dismissDeleteImageProgressDialog();
    }

    interface Presenter{
        void loadAdaptor();
        void deleteStope(String key);
    }
}
