package com.example.huaixiaohai.rxjavademo;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by huaixiaohai on 16/9/18.
 */
public interface GankService {
    // http://gank.io/api/search/query/listview/category/Android/count/10/page/1
    @GET("api/search/query/listview/category/{type}/count/{count}/page/{page}")
    Observable<SearchModel> search(@Path("type") String category, @Path("count") int count, @Path("page") int page);
}
