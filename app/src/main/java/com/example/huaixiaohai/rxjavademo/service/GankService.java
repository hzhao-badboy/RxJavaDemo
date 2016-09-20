package com.example.huaixiaohai.rxjavademo.service;

import com.example.huaixiaohai.rxjavademo.model.DailyModel;
import com.example.huaixiaohai.rxjavademo.model.SearchModel;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by huaixiaohai on 16/9/18.
 */
public interface GankService {

    /**
     * 搜索 API
     * 地址 http://gank.io/api/search/query/listview/category/Android/count/10/page/1
     * @param category  可接受参数 all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     * @param count     count 最大 50
     * @param page
     * @return
     */
    @GET("api/search/query/listview/category/{type}/count/{count}/page/{page}")
    Observable<SearchModel> search(@Path("type") String category, @Path("count") int count, @Path("page") int page);

    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     * @param year  年
     * @param month  月
     * @param day  日
     * @return
     */
    @GET("api/day/{year}/{month}/{day}")
    Observable<DailyModel> getDailyData(@Path("year") int year, @Path("month") int month, @Path("day") int day);


}
