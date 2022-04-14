package com.mobileapp.rugshop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobileapp.rugshop.ItemListActivity;
import com.mobileapp.rugshop.R;
import com.mobileapp.rugshop.model.Carpet;

import java.util.ArrayList;
import java.util.Locale;

public class CarpetAdapter extends RecyclerView.Adapter<CarpetAdapter.ViewHolder> implements Filterable {
    private ArrayList<Carpet> mCarpetData;
    private ArrayList<Carpet> mCarpetDataAll;
    private Context mContext;
    private int lastPosition = -1;

    public CarpetAdapter(Context context, ArrayList<Carpet> itemData){
        this.mCarpetData=itemData;
        this.mCarpetDataAll=itemData;
        this.mContext=context;

    }

    @NonNull
    @Override
    public CarpetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.element,parent,false));
    }

    @Override
    public void onBindViewHolder(CarpetAdapter.ViewHolder holder, int position) {
        Carpet currentCarpet =  mCarpetData.get(position);

        holder.bindTo(currentCarpet);
    }

    @Override
    public int getItemCount() {
        return mCarpetData.size();
    }

    @Override
    public Filter getFilter() {
        return carpetFilter;
    }

    private Filter carpetFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Carpet> filteredCarpetList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if (charSequence == null || charSequence.length() == 0) {
                results.count = mCarpetDataAll.size();
                results.values = mCarpetDataAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Carpet c : mCarpetDataAll) {
                    if (c.getName().toLowerCase().contains(filterPattern)) {
                        filteredCarpetList.add(c);
                    }
                }
                results.count = filteredCarpetList.size();
                results.values = filteredCarpetList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mCarpetData = (ArrayList) filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        private TextView mNameView;
        private TextView mDescriptionView;
        private TextView mPriceView;


        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.carpetImage);
            mNameView = itemView.findViewById(R.id.carpetName);
            mDescriptionView =itemView.findViewById(R.id.carpetDescription);
            mPriceView =itemView.findViewById(R.id.price);

            itemView.findViewById(R.id.add_to_cart).setOnClickListener(view -> ((ItemListActivity)mContext).updateAlertIcon());
        }

        public void bindTo(Carpet currentCarpet) {
            mNameView.setText(currentCarpet.getName());
            //mDescriptionView.setText("Color: "+currentCarpet.getColor()+" Type: "+currentCarpet.getType()+" Dimensions: "+currentCarpet.getWidth()+"x"+currentCarpet.getLength());
            mPriceView.setText(currentCarpet.getPrice());

            //Glide.with(mContext).load(currentCarpet.getImageResource()).into(mImageView);
        }
    }
}
