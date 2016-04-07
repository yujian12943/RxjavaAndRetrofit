package com.java.yu.rxjavaandretrofit;

import java.io.File;

import retrofit.http.GET;
import retrofit.http.Query;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Administrator on 2016/3/22.
 */
public interface MovieService {


    @GET("get请求")
    Call<MovieEntity> getNewData(@Query("start")int start,@Query("count")int count);
    @POST("post")
    Call<MovieEntity> getpostData(@Query("start")int start,@Query("count")int count);
    @POST("上传文件")
    void upload(@Part("file") File file, Callback<File> callback);
//    @Path()---更换url参数
//    @Body-----提交实体
//    @Query()——————url自动拼参数
//    @Header--------url添加header信息
//    @FieldMap-----提交map表单
//    @Part----文件
}
