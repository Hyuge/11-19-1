package com.example.h.fuxi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

/**
 * Created by H on 2019/10/31.
 */

class MyOneAdapter extends RecyclerView.Adapter{
    private ArrayList<Info.DataBean> itemList;
    private ArrayList<BannerBean.DataBean> bannerItem;
    private Context context;
    private int _position;

    public MyOneAdapter(ArrayList<Info.DataBean> itemList, ArrayList<BannerBean.DataBean> bannerItem, Context context) {
        this.itemList = itemList;
        this.bannerItem = bannerItem;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==0){
            View view = LayoutInflater.from (context).inflate (R.layout.layout_banner, parent, false);
            return new BannerHolder (view);
        }
        View view = LayoutInflater.from (context).inflate (R.layout.layout_item_rec, parent, false);

        return new ItemHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        int i = getItemViewType (position);
        if (i==0){
            final BannerHolder holder1= (BannerHolder) holder;
            holder1.banner1.setImages (bannerItem).setImageLoader (new ImageLoader ( ) {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    BannerBean.DataBean path1= (BannerBean.DataBean) path;
                    Glide.with (context).load (path1.getImagePath ()).into (imageView);
                }
            }).start ();
        }
        _position =position;
        if (i==1){
           if (itemList.size ()>0){
               _position=position-1;
           }
            ItemHolder holder2= (ItemHolder) holder;
            holder2.desc.setText (itemList.get (_position).getFood_str ());
            holder2.title.setText (itemList.get (_position).getTitle ());
            Glide.with (context).load (itemList.get (_position).getPic ()).apply (RequestOptions.bitmapTransform (new CircleCrop ())).into (holder2.pic);
        }

        holder.itemView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                if (clickl!=null){
                    clickl.myCli (position);
                }
            }
        });
        holder.itemView.setOnLongClickListener (new View.OnLongClickListener ( ) {
            @Override
            public boolean onLongClick(View v) {
                if (clickl!=null){
                    clickl.myLongCli (position);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (bannerItem.size ()>0){
            return itemList.size ()+1;
        }
        return itemList.size ();
    }

    @Override
    public int getItemViewType(int position) {

        if (position==0&&bannerItem.size ()>0){
            return 0;
        }else {

            return 1;
        }
    }

    class BannerHolder extends RecyclerView.ViewHolder{
        private Banner banner1;
        public BannerHolder(View itemView) {
            super (itemView);
            banner1=itemView.findViewById (R.id.bann);
        }
    }
    class ItemHolder extends RecyclerView.ViewHolder{
        private TextView desc;
        private TextView title;
        private ImageView pic;
        public ItemHolder(View itemView) {
            super (itemView);
            desc=itemView.findViewById (R.id.desc1);
            title=itemView.findViewById (R.id.title1);
            pic=itemView.findViewById (R.id.pic1);
        }
    }
        interface MyClick{
        void myCli(int p);
            void myLongCli(int type);
        }

    private MyClick clickl;

    public void setClickl(MyClick clickl) {
        this.clickl = clickl;
    }
}