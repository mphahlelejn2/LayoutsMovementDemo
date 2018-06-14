package bme.co.za.stopeImages.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bme.co.za.stopeImages.Activities.EditableStopeImagesActivity;
import bme.co.za.stopeImages.R;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * This class is adaptor to load individual stopes data into a list
 *
 * Created by Jeffrey.Mphahlele on 6/6/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<bme.co.za.stopeImages.Adapter.MyAdapter.ViewHolder>   {

    private List<String> mDataset;
    private bme.co.za.stopeImages.Interface.IMain.MyView view;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //setup a holder parameters
        @BindView(R.id.tvTimesStamp)
        public TextView timeStamp;
        @BindView(R.id.ivDelete)
        public ImageView delete;
        @BindView(R.id.ivEdit)
        public ImageView edit;
        @BindView(R.id.ivOpen)
        public ImageView view;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public MyAdapter(bme.co.za.stopeImages.Interface.IMain.MyView view, List<String> myDataset) {
        this.mDataset = myDataset;
        this.view= view;
    }


    @Override
    public bme.co.za.stopeImages.Adapter.MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                 int viewType) {
        View card = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stope_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(card);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final String key = mDataset.get(position);
        holder.timeStamp.setText("Stope_"+key);
        //Navigate to view activity
        holder.view.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(), bme.co.za.stopeImages.Activities.NonEditableStopeImagesActivity.class);
            intent.putExtra("keyTimesStamp", key);
            view.getContext().startActivity(intent);
        });

        //delete current stope data
        holder.delete.setOnClickListener(v -> deleteBuilder(key));

        //navigate to edit stope activity
        holder.edit.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(), EditableStopeImagesActivity.class);
            intent.putExtra("keyTimesStamp", key);
            view.getContext().startActivity(intent);
        });
    }


    /**
     * Dialog Massage to delete
     */
    public void deleteBuilder(final String key) {
       view.deleteDialog(key);
    }



    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
