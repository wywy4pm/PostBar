package com.arun.bar.retrofit;

import com.arun.bar.bean.BarBean;
import com.arun.bar.bean.CommonApiResponse;
import com.arun.bar.bean.PostItem;
import com.arun.bar.bean.PostListData;
import com.arun.bar.bean.RegisterBodyBean;
import com.arun.bar.bean.ReplyListData;
import com.arun.bar.bean.post.ReplyBean;
import com.arun.bar.bean.request.BarAddRequest;
import com.arun.bar.bean.request.PostAddRequest;
import com.arun.bar.bean.request.PostListRequest;
import com.arun.bar.bean.request.RegisterRequest;
import com.arun.bar.bean.request.ReplyListRequest;
import com.arun.bar.bean.request.ReplyRequest;
import com.arun.bar.bean.request.UserUpdateRequest;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST(RetrofitUrl.POST_ADD)
    Observable<CommonApiResponse> postAdd(@Body PostAddRequest postAddRequest, @Query("imgs") List<String> imgs);

    @GET(RetrofitUrl.INVITE_QR_CODE)
    Call<ResponseBody> getInviteQRCode(@Query("total_fee") String total_fee, @Query("product_id") String product_id);

    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST(RetrofitUrl.REPLY_LIST)
    Observable<CommonApiResponse<ReplyListData>> getReplyList(@Body ReplyListRequest replyListRequest);

    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST(RetrofitUrl.REPLY_ADD)
    Observable<CommonApiResponse> replyAdd(@Body ReplyRequest replyRequest);

    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST(RetrofitUrl.USER_JOIN_BAR)
    Observable<CommonApiResponse<BarBean>> joinBar(@Query("user_uuid") String user_uuid);
}
