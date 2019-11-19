package com.example.h.fuxi;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by H on 2019/10/31.
 */

 interface  ApiService {
     String getItem="http://www.qubaobei.com/";
     @GET("ios/cf/dish_list.php?stage_id=1&limit=20&page=1")
     Call<Info> item();
     @GET("ios/cf/dish_list.php?stage_id=1&limit=20&page=1")
    Observable<Info> item1();


}
