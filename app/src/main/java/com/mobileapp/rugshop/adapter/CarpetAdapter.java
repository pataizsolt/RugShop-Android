package com.mobileapp.rugshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobileapp.rugshop.ItemListActivity;
import com.mobileapp.rugshop.R;
import com.mobileapp.rugshop.model.Carpet;

import java.util.ArrayList;
import java.util.EventListener;

public class CarpetAdapter
        extends RecyclerView.Adapter<CarpetAdapter.ViewHolder>
        implements Filterable {
    // Member variables.
    private ArrayList<Carpet> mShoppingData;
    private ArrayList<Carpet> mSoppingDataAll;
    private Context mContext;
    private int lastPosition = -1;

    CarpetAdapter(Context context, ArrayList<Carpet> itemsData) {
        this.mShoppingData = itemsData;
        this.mSoppingDataAll = itemsData;
        this.mContext = context;
    }

    @Override
    public CarpetAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.element, parent, false));
    }

    @Override
    public void onBindViewHolder(CarpetAdapter.ViewHolder holder, int position) {
        // Get current sport.
        Carpet currentItem = mShoppingData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentItem);


        if(holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mShoppingData.size();
    }


    /**
     * RecycleView filter
     * **/
    @Override
    public Filter getFilter() {
        return shoppingFilter;
    }

    private Filter shoppingFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Carpet> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0) {
                results.count = mSoppingDataAll.size();
                results.values = mSoppingDataAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(Carpet item : mSoppingDataAll) {
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mShoppingData = (ArrayList)filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView carpetName;
        private TextView carpetDescription;
        private TextView price;

        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            carpetName = itemView.findViewById(R.id.carpetName);
            carpetDescription = itemView.findViewById(R.id.carpetDescription);
            price = itemView.findViewById(R.id.price);

            //itemView.findViewById(R.id.add_to_cart).setOnClickListener(view -> ((ItemListActivity)mContext).updateAlertIcon());
        }

        void bindTo(Carpet currentItem){
            carpetName.setText(currentItem.getName());
            carpetDescription.setText("Type: "+currentItem.getType()+"\nColor: "+currentItem.getColor()+"\nDimensions: "+currentItem.getWidth()+"cm x "+currentItem.getLength()+"cm");
            price.setText(currentItem.getPrice()+" EUR");

            // Load the images into the ImageView using the Glide library.
            //Glide.with(mContext).load(currentItem.getImageResource()).into(mItemImage);
        }
    }
    /*private class CarpetViewHolder extends RecyclerView.ViewHolder{
        private TextView carpetName;
        private TextView carpetDescription;
        private TextView price;

        public CarpetViewHolder(@NonNull View itemView) {
            super(itemView);

            carpetName = itemView.findViewById(R.id.carpetName);
            carpetDescription = itemView.findViewById(R.id.carpetDescription);
            price = itemView.findViewById(R.id.price);
        }
    }*/
}

