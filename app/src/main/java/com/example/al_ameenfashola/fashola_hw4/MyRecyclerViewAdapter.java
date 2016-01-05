package com.example.al_ameenfashola.fashola_hw4;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by al-ameenfashola on 2/11/15.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<Map<String, ?>> mDataset ;
    private Context mContext ;
    OnItemClickListener mItemClickListener ;

    public MyRecyclerViewAdapter(Context mycontext,List<Map<String, ?>> myDataset ) {
      mContext = mycontext ;
      mDataset = myDataset;

    }

    public abstract class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView vIcon ;
        public TextView vTitle;
        public TextView vDescription;
        public CheckBox vCheckbox;
        public MyViewHolder(View v) {

            super(v);
            init(v);

            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getPosition());
                    }

                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {


                @Override
                public boolean onLongClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemLongClick(v, getPosition());

                    }
                    return true;
                }

            });

            vCheckbox.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    HashMap movie = (HashMap) mDataset.get(getPosition()) ;
                    movie.put("selection", vCheckbox.isChecked()) ;
                }
            });
        }

        public void bindMovieData(Map<String, ?> movie) {

            vTitle.setText((String) movie.get(("name")));
            vDescription.setText((String) movie.get(("description")));
            vIcon.setImageResource((Integer) movie.get(("image")));
            vCheckbox.setChecked((Boolean) movie.get(("selection")));
        }

        public abstract void init(View v);
    }


    public class ViewHolder1 extends MyViewHolder {

         public ViewHolder1(View v){
             super(v);
         }

        public void init(View v){
            vIcon = (ImageView) v.findViewById(R.id.icon);
            vTitle = (TextView) v.findViewById(R.id.name);
            vDescription = (TextView) v.findViewById(R.id.description);
            vCheckbox = (CheckBox) v.findViewById(R.id.selection);

        }


    }

    public class ViewHolder2 extends MyViewHolder {
        public ViewHolder2(View v) {
            super(v);
        }
        public void init(View v){
            vIcon = (ImageView) v.findViewById(R.id.icon1);
            vTitle = (TextView) v.findViewById(R.id.name1);
            vDescription = (TextView) v.findViewById(R.id.description1);
            vCheckbox = (CheckBox) v.findViewById(R.id.selection1);
        }

    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        HashMap movie = (HashMap) mDataset.get(position) ;
        double rating = (Double)movie.get("rating");

        if(rating >= 8){
            return 0;
        }else{
            return 1;
        }
//        return position % 2 * 2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent,false) ;
        View v2;
        v2= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview2, parent,false) ;
        ViewHolder1 vh = new ViewHolder1(v);
        ViewHolder2 vh2 = new ViewHolder2(v2);
        if(viewType == 0){
            return vh;
        }else{
            return vh2;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Map<String, ?> movie = mDataset.get(i);
        ((MyViewHolder) viewHolder).bindMovieData(movie);
    }

/*
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Map<String, ?> movie = mDataset.get(position);
        holder.bindMovieData(movie);



    }

*/



    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface OnItemClickListener{
        public void onItemClick (View view , int position) ;
        public void onItemLongClick (View view , int position) ;

    }

    public void SetOnItemClickListener (final OnItemClickListener mItemClickListener) {
        this.mItemClickListener= mItemClickListener ;
    }

    public void removeItem(int position) {mDataset.remove(position) ;}

}
