package com.arun.bar.retrofit;

import com.arun.bar.bean.CommonApiResponse;
import com.arun.bar.bean.PostItem;
import com.arun.bar.bean.PostListData;
import com.arun.bar.bean.RegisterBodyBean;
import com.arun.bar.bean.request.BarAddRequest;
import com.arun.bar.bean.request.PostListRequest;
import com.arun.bar.bean.request.RegisterRequest;
import com.arun.bar.bean.request.UserUpdateRequest;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitApi {

    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST(RetrofitUrl.USER_REGISTER)
    Observable<CommonApiResponse<RegisterBodyBean>> register(@Body RegisterRequest registerRequest);

    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST(RetrofitUrl.BAR_ADD)
    Observable<CommonApiResponse> barAdd(@Body BarAddRequest barAddRequest);

    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST(RetrofitUrl.USER_UPDATE)
    Observable<CommonApiResponse> updateUser(@Body UserUpdateRequest userUpdateRequest);

    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST(RetrofitUrl.POST_LIST)
    Observable<CommonApiResponse<PostListData>> getPostList(@Body PostListRequest postListRequest);
}
