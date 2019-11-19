package com.example.h.fuxi;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment implements View.OnClickListener {
Handler handler=new Handler (){
    @Override
    public void handleMessage(Message msg) {
        if (msg.what==1){
            adapter.notifyDataSetChanged ();
        }
        if (msg.what==2){
            adapter.notifyDataSetChanged ();
        }


    }
};

    private View view;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;

    private ArrayList<Info.DataBean> itemList;
    private ArrayList<BannerBean.DataBean> bannerItem;
    private MyOneAdapter adapter;
    private int _type;
    private int _p;
    private PopupWindow popupWindow;

    public OneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate (R.layout.fragment_one, container, false);
        initView (view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById (R.id.RecyclerView);
        mSmartRefreshLayout = (SmartRefreshLayout) view.findViewById (R.id.SmartRefreshLayout);
        mRecyclerView.setLayoutManager (new LinearLayoutManager (getContext ()));
        registerForContextMenu (mRecyclerView);
       bannerItem = new ArrayList<> ( );
        itemList = new ArrayList<> ( );
        adapter = new MyOneAdapter (itemList, bannerItem, getContext ( ));
        adapter.setClickl (new MyOneAdapter.MyClick ( ) {
            @Override
            public void myCli(int p) {
                _p=p;
                initPop();

            }

            @Override
            public void myLongCli(int type) {
                _type=type;




            }
        });
        mRecyclerView.setAdapter (adapter);
        initBanner();
        initItem();
    }

    private void initPop() {
        View view1 = View.inflate (getContext ( ), R.layout.layout_pop, null);
        Button id = view1.findViewById (R.id.delete);
        LinearLayout id1 = view1.findViewById (R.id.LinearLayout);
        popupWindow = new PopupWindow (view1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable (false);
        popupWindow.setOutsideTouchable (true);
        popupWindow.setAnimationStyle (R.style.pop_item);
        id.setOnClickListener (this);
        id1.setOnClickListener (this);
        popupWindow.showAtLocation (view1, Gravity.CENTER,0,0);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add (1,1,1,"删除");
        menu.add (1,2,1,"修改");
        super.onCreateContextMenu (menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId ()){
            case 1:
                itemList.remove (_type);
                adapter.notifyDataSetChanged ();
                break;

            case 2:

                break;
        }
        return super.onContextItemSelected (item);
    }

    private void initItem() {
        Retrofit build = new Retrofit.Builder ( ).baseUrl (ApiService.getItem).addCallAdapterFactory (RxJava2CallAdapterFactory.create ()).addConverterFactory (GsonConverterFactory.create ( )).build ( );
        ApiService apiService = build.create (ApiService.class);
        apiService.item1 ().subscribeOn (Schedulers.newThread ()).observeOn (AndroidSchedulers.mainThread ()).subscribe (new Observer<Info> ( ) {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Info info) {
                List<Info.DataBean> list = info.getData ( );
                itemList.addAll (list);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        /*apiService.item ().enqueue (new retrofit2.Callback<Info> ( ) {
            @Override
            public void onResponse(retrofit2.Call<Info> call, retrofit2.Response<Info> response) {
                List<Info.DataBean> list = response.body ( ).getData ( );
                itemList.addAll (list);
                handler.sendEmptyMessage (2);
            }

            @Override
            public void onFailure(retrofit2.Call<Info> call, Throwable t) {

            }
        });*/
    }

    private void initBanner() {
        OkHttpClient build = new OkHttpClient.Builder ( ).build ( );
        final Request request=new Request.Builder ().get ().url ("https://www.wanandroid.com/banner/json").build ();
        build.newCall (request).enqueue (new Callback ( ) {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body ( ).string();
                BannerBean json = new Gson ( ).fromJson (s, BannerBean.class);
                List<BannerBean.DataBean> list = json.getData ( );
                bannerItem.addAll (list);
                handler.sendEmptyMessage (1);


            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()){
            case R.id.delete:
                itemList.remove (_p);
                adapter.notifyDataSetChanged ();
                popupWindow.dismiss ();
                break;

            case R.id.LinearLayout:
                popupWindow.dismiss ();
                break;
        }

    }
}
