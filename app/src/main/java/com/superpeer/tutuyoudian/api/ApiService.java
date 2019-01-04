package com.superpeer.tutuyoudian.api;



import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseCountBean;
import com.superpeer.tutuyoudian.bean.BaseCountList;
import com.superpeer.tutuyoudian.bean.BaseLocationBean;
import com.superpeer.tutuyoudian.bean.BaseRunBean;
import com.superpeer.tutuyoudian.bean.BaseSearchResult;
import com.superpeer.tutuyoudian.bean.WxBean;
import com.superpeer.tutuyoudian.listener.OnLocationListener;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by wangzhankai on 2018/2/7.
 */

public interface ApiService {

    //下载文件
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET
    Observable<ResponseBody> downloadPicFromNet(@Url String fileUrl);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("service")
    Observable<BaseBeanResult> getPostRemote(@Query("json") String bean);

    //校验验证码
    @FormUrlEncoded
    @POST("common/message/check")
    Observable<BaseBeanResult> check(@Field("phone") String phone, @Field("content") String content, @Field("messageType") String type);

    //获取短信
    @FormUrlEncoded
    @POST("common/message/smsValidation")
    Observable<BaseBeanResult> getCode(@Field("phone") String phone, @Field("messageType") String type, @Field("deviceId") String deviceId);

    //获取协议
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/shop/getAgreement")
    Observable<BaseBeanResult> getAgreement(@Query("type") String type);

    //入驻提交
    @FormUrlEncoded
    @POST("app/shop/register")
    Observable<BaseBeanResult> register(@Field("phone") String phone, @Field("content") String content,
                                        @Field("messageType") String messageType, @Field("password") String password, @Field("invitationCode") String invitationCode, @Field("token") String token);

    //入驻提交
    @FormUrlEncoded
    @POST("app/runner/register")
    Observable<BaseBeanResult> runnerRegister(@Field("loginName") String loginName, @Field("password") String password, @Field("content") String content, @Field("messageType") String messageType);

    //重置密码
    @FormUrlEncoded
    @POST("app/shop/resetPwd")
    Observable<BaseBeanResult> reset(@Field("phone") String phone, @Field("password") String password, @Field("content") String content, @Field("deviceId") String deviceId);

    //登录
    @FormUrlEncoded
    @POST("app/shop/login")
    Observable<BaseBeanResult> login(@Field("username") String username, @Field("password") String password, @Field("token") String token);

    //信息补全
    @Multipart
    @POST("app/shop/uploadImgs")
    Observable<BaseBeanResult> uploadImgs(@Part("shopId") RequestBody shopId, @PartMap Map<String, RequestBody> imgs);

    //获取银行列表
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("common/bank/getBanks")
    Observable<BaseBeanResult> getBanks();

    //店铺分类 code = SHOP_TYPE     账户类型 code = ACCOUNT_TYPE
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/shop/getDict")
    Observable<BaseBeanResult> getDict(@Query("code") String code);

    //全国区域
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("common/base/getAreas")
    Observable<BaseBeanResult> getAreas();

    //支行地址
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("common/bank/getSubBranchName")
    Observable<BaseBeanResult> getSubBranchName(@Query("bankId") String bankId, @Query("provinceCode") String provinceCode, @Query("cityCode") String cityCode);

    //保存结算方式
    @FormUrlEncoded
    @POST("app/shop/saveShopAccount")
    Observable<BaseBeanResult> saveShopAccount(@Field("roleType") String roleType, @Field("shopId") String shopId, @Field("bankCard") String bankCard, @Field("accountName") String accountName,
                                               @Field("bankId") String bankId, @Field("bankName") String bankName, @Field("accountType") String accountType,
                                               @Field("accountProvincesCode") String accountProvincesCode, @Field("accountProvinces") String accountProvinces, @Field("accountCityCode") String accountCityCode,
                                               @Field("accountCity") String accountCity, @Field("subbranchId") String subbranchId, @Field("subbranchName") String subbranchName);

    //保存结算方式
    @FormUrlEncoded
    @POST("app/shop/saveShopAccount")
    Observable<BaseBeanResult> saveRunnerAccount(@Field("id") String id, @Field("roleType") String roleType, @Field("bankCard") String bankCard, @Field("accountName") String accountName,
                                               @Field("bankId") String bankId, @Field("bankName") String bankName, @Field("accountType") String accountType,
                                               @Field("accountProvincesCode") String accountProvincesCode, @Field("accountProvinces") String accountProvinces, @Field("accountCityCode") String accountCityCode,
                                               @Field("accountCity") String accountCity, @Field("subbranchId") String subbranchId, @Field("subbranchName") String subbranchName);

    //保存结算方式
    @FormUrlEncoded
    @POST("app/shop/saveShopAccount")
    Observable<BaseBeanResult> saveWxShopAccount(@Field("roleType") String roleType, @Field("shopId") String shopId, @Field("openId") String openId,
                                               @Field("unionId") String unionId, @Field("nickName") String nickName);

    //保存结算方式
    @FormUrlEncoded
    @POST("app/shop/saveShopAccount")
    Observable<BaseBeanResult> saveWxRunnerAccount(@Field("id") String id, @Field("roleType") String roleType, @Field("openId") String openId,
                                                 @Field("unionId") String unionId, @Field("nickName") String nickName);

    //公告列表
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/shop/getNotices")
    Observable<BaseBeanResult> getNotice(@Query("shopId") String shopId, @Query("defaultCurrent") String page, @Query("pageSize") String pageSize);

    //更改营业状态
    @FormUrlEncoded
    @POST("app/shop/changeOperating")
    Observable<BaseBeanResult> changeStatus(@Field("shopId") String shopId, @Field("operatingStatus") String status);

    //登录公告
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/shop/loginNotice")
    Observable<BaseBeanResult> getLoginNotice(@Query("shopId") String shopId);

    //商品分类
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/shop/getGoodsType")
    Observable<BaseBeanResult> getShopCategory(@Query("shopId") String shopId);

    //商品分类
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/shop/getShopType")
    Observable<BaseBeanResult> getShopType();

    //获取商品库商品
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/shop/getGoodsByType")
    Observable<BaseBeanResult> getAllGoods(@Query("goodsTypeId") String goodsTypeId, @Query("shopId") String shopId, @Query("defaultCurrent") String defaultCurrent, @Query("pageSize") String pageSize, @Query("name") String name);

    //获取普通订单列表
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/order/singleOrderList")
    Observable<BaseBeanResult> getSingleOrderList(@Query("shopId") String shopId, @Query("defaultCurrent") String defaultCurrent, @Query("pageSize") String pageSize, @Query("orderStatus") String orderStatus, @Query("shippingType") String shippingType);

    //获取订单详情
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/order/singleOrderInfo")
    Observable<BaseBeanResult> getOrderDetail(@Query("orderId") String orderId);

    //获取薪金数据
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/shop/getAccountInfo")
    Observable<BaseBeanResult> getCashData(@Query("shopId") String shopId);

    //获取拼团列表
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/shop/getGroupList")
    Observable<BaseBeanResult> getGroupList(@Query("shopId") String shopId);

    //更改营业状态
    @FormUrlEncoded
    @POST("app/shop/delGroup")
    Observable<BaseBeanResult> delGroup(@Field("groupId") String groupId);

    //访客数据
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/count/getVisitorNum")
    Observable<BaseCountBean> getVisitorNum(@Query("shopId") String shopId, @Query("num") String num);

    //订单数据统计
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/count/getOrderNum")
    Observable<BaseCountBean> getOrderNum(@Query("shopId") String shopId, @Query("num") String num, @Query("pageSize") String pageSize);

    //营销数据统计
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/count/getRunNum")
    Observable<BaseCountBean> getRunNum(@Query("shopId") String shopId, @Query("num") String num);

    //获取营收明细
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/count/getCapitalRecord")
    Observable<BaseBeanResult> getCapitalRecord(@Query("shopId") String shopId, @Query("defaultCurrent") String defaultCurrent, @Query("pageSize") String pageSize);

    //获取商品销售量
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/count/getSaleGoods")
    Observable<BaseBeanResult> getSaleGoods(@Query("shopId") String shopId, @Query("num") String num, @Query("pageSize") String pageSize);

    //营销数据统计
//    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
//    @GET("app/shop/getAgreement")
//    Observable<BaseBeanResult> getAgreement(@Query("shopId") String shopId);

    //提现方式
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/shop/getAccountType")
    Observable<BaseBeanResult> getAccountType(@Query("shopId") String shopId);

    //发布店铺公告
    @FormUrlEncoded
    @POST("app/shop/saveShopNotice")
    Observable<BaseBeanResult> publish(@Field("shopId") String shopId, @Field("content") String content);

    //获取拼团订单列表
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/order/groupOrderList")
    Observable<BaseBeanResult> getGroupOrderList(@Query("shopId") String shopId, @Query("defaultCurrent") String defaultCurrent, @Query("pageSize") String pageSize, @Query("orderStatus") String orderStatus, @Query("shippingType") String shippingType);

    //获取首页数据
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/shop/getHomepageInfo")
    Observable<BaseBeanResult> getMainData(@Query("shopId") String shopId);

    //获取店铺的分类
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/shop/getGoodsTypeByShopType")
    Observable<BaseBeanResult> getType(@Query("shopId") String shopId);

    //获取店铺分类的商品
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/shop/getMyGoodsByType")
    Observable<BaseBeanResult> getGoods(@Query("shopId") String shopId, @Query("goodsTypeId") String goodsTypeId, @Query("saleState") String saleState, @Query("stock") String stock, @Query("defaultCurrent") String defaultCurrent, @Query("pageSize") String pageSize);

    //获取店铺分类的商品
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/shop/getMyGoodsByType")
    Observable<BaseBeanResult> getGoodsSearch(@Query("shopId") String shopId, @Query("goodsTypeId") String goodsTypeId, @Query("saleState") String saleState, @Query("stock") String stock, @Query("defaultCurrent") String defaultCurrent, @Query("pageSize") String pageSize, @Query("name") String name);

    //库存商品
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/shop/getMyGoodsByStock")
    Observable<BaseBeanResult> getStock(@Query("shopId") String shopId, @Query("goodsTypeId") String goodsTypeId,@Query("defaultCurrent") String defaultCurrent, @Query("pageSize") String pageSize);

    //库存商品
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/shop/getMyGoodsByStock")
    Observable<BaseBeanResult> getStockSearch(@Query("shopId") String shopId, @Query("name") String name,@Query("defaultCurrent") String defaultCurrent, @Query("pageSize") String pageSize);

    //扫码上传
    @FormUrlEncoded
    @POST("app/shop/findByBarCode")
    Observable<BaseBeanResult> codeUpload(@Field("barCode") String barCode, @Field("shopId") String shopId);

    //本地上传
    @Multipart
    @POST("app/shop/saveGoodsFromLocal")
    /*Observable<BaseBeanResult> localUpload(@Part("shopId") RequestBody shopId, @Part("goodsId") RequestBody goodsId, @Part("name") RequestBody name, @Part("bankId") RequestBody bankId, @Part("manufacturer") RequestBody manufacturer,
                                           @Part("barCode") RequestBody barCode, @Part("images") MultipartBody.Part imgs,
                                           @Part("type") RequestBody type, @Part("brand") RequestBody brand, @Part("specifications") RequestBody specifications,
                                           @Part("unit") RequestBody unit, @Part("price") RequestBody price, @Part("stock") RequestBody stock,
                                           @Part("vipPrice") RequestBody vipPrice, @Part("saleState") RequestBody saleState);*/
    Observable<BaseBeanResult> localUpload(@Part("shopId") RequestBody shopId, @Part("goodsId") RequestBody goodsId, @Part("name") RequestBody name, @Part("bankId") RequestBody bankId, @Part("manufacturer") RequestBody manufacturer,
                                           @Part("barCode") RequestBody barCode, @PartMap Map<String, RequestBody> imgs,
                                           @Part("type") RequestBody type, @Part("brand") RequestBody brand, @Part("specifications") RequestBody specifications,
                                           @Part("price") RequestBody price, @Part("stock") RequestBody stock,
                                           @Part("vipPrice") RequestBody vipPrice, @Part("saleState") RequestBody saleState);

    //设置自动接单
    @FormUrlEncoded
    @POST("app/shop/changeAutomaticStatus")
    Observable<BaseBeanResult> autoChange(@Field("shopId") String shopId, @Field("automaticStatus") String operatingStatus);

    //保存或修改拼团设置
    @FormUrlEncoded
    @POST("app/shop/settingGroupGoods")
    Observable<BaseBeanResult> setCollageInfo(@Field("id") String id, @Field("shopId") String shopId, @Field("goodsId") String goodsId, @Field("title") String title,
                                              @Field("goodsNum") String goodsNum, @Field("groupDesc") String groupDesc,  @Field("price") String price, @Field("groupNum") String groupNum,
                                              @Field("needNum") String needNum,  @Field("shippingType") String shippingType,  @Field("keepHour") String keepHour,
                                              @Field("cancelHour") String cancelHour,  @Field("noGetHour") String noGetHour,  @Field("noSendHour") String noSendHour);

    @FormUrlEncoded

    @POST("/app/shop/copyGroupGoods")
    Observable<BaseBeanResult> addCollage(@Field("groupId") String groupId);

    //商品库上传商品
    @FormUrlEncoded
    @POST("app/shop/saveGoodsFromBank")
    Observable<BaseBeanResult> saveGoods(@Field("shopId") String shopId, @Field("goodsBankId") String goodsBankId);

    //扫码验证
    @FormUrlEncoded
    @POST("app/shop/checkPickGoods")
    Observable<BaseBeanResult> checkPickGoods(@Field("pickGoods") String pickGoods, @Field("shopId") String shopId);

    //获取提现记录
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/shop/getSettleDetail")
    Observable<BaseBeanResult> getRecord(@Query("shopId") String shopId, @Query("pageSize") String pageSize, @Query("defaultCurrent") String defaultCurrent);

    //保存提现记录
    @FormUrlEncoded
    @POST("app/shop/saveSettleDetail")
    Observable<BaseBeanResult> saveWithDraw(@Field("shopId") String shopId, @Field("withdrawMoney") String withdrawMoney, @Field("accountType") String accountType, @Field("payPwd") String payPwd);

    //保存申请入驻
    @Multipart
    @POST("app/shop/saveShopInfo")
    Observable<BaseBeanResult> saveShopInfo(@Part("shopId") RequestBody shopId, @Part("accountId") RequestBody accountId, @Part("name") RequestBody name,
                                            @PartMap Map<String, RequestBody> imgs, @Part("type") RequestBody type, @Part("typeName") RequestBody typeName,
                                            @Part("businessScope") RequestBody businessScope,
                                            @Part("areaCode") RequestBody areaCode, @Part("longitude") RequestBody longitude, @Part("latitude") RequestBody latitude,
                                            @Part("address") RequestBody address, @Part("bossName") RequestBody bossName, @Part("phone") RequestBody phone);


    //取消订单
    @FormUrlEncoded
    @POST("app/order/cancelOrder")
    Observable<BaseBeanResult> cancelOrder(@Field("orderId") String orderId);

    //接单
    @FormUrlEncoded
    @POST("app/order/receiptOrder")
    Observable<BaseBeanResult> receiptOrder(@Field("orderId") String orderId);

    //获取拼团订单详情
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/shop/findGroupOrderInfo")
    Observable<BaseBeanResult> getGroupDetail(@Query("orderId") String orderId);

    //获取详细的拼团信息
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/shop/findByGroupId")
    Observable<BaseBeanResult> findGroupDetail(@Query("groupId") String groupId);

    //获取通知详情
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/shop/findByNoticeId")
    Observable<BaseBeanResult> getNoticeDetail(@Query("noticeId") String noticeId, @Query("shopId") String shopId);

    //逆地理编码
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("geocoder/v1")
    Observable<BaseLocationBean> getLocation(@Query("address") String address, @Query("region") String region, @Query("key") String key, @Query("callback") OnLocationListener callback);

    //关键词提示
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("place/v1/suggestion")
    Observable<BaseSearchResult> getLocationTips(@Query("keyword") String keyword, @Query("region") String region, @Query("region_fix") String region_fix, @Query("page_index") String page_index, @Query("page_size") String page_size, @Query("key") String key);

    //搜索
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("place/v1/search")
    Observable<BaseSearchResult> search(@Query("keyword") String keyword, @Query("boundary") String boundary, @Query("key") String key, @Query("page_size") String page_size, @Query("page_index") String index);

    //上下架
    @FormUrlEncoded
    @POST("app/shop/modifySaleState")
    Observable<BaseBeanResult> modifySaleState(@Field("goodsId") String goodsId, @Field("saleState") String saleState);

    //删除我的商品
    @FormUrlEncoded
    @POST("app/shop/delMyGoods")
    Observable<BaseBeanResult> delMyGoods(@Field("goodsId") String goodsId);

    //更改数据排序
    @FormUrlEncoded
    @POST("app/shop/changeSortNo")
    Observable<BaseBeanResult> changeSortNo(@Field("goodsId") String goodsId, @Field("flag") String flag);

    //获取排序列表
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/shop/getSortGoodsInfo")
    Observable<BaseBeanResult> getSortGoodsInfo(@Query("shopId") String shopId, @Query("goodsTypeId") String goodsTypeId, @Query("defaultCurrent") String defaultCurrent, @Query("pageSize") String pageSize);

    //更改数据排序
    @FormUrlEncoded
    @POST("app/shop/saveDistributionInfo")
    Observable<BaseBeanResult> saveDistributionInfo(@Field("shopId") String shopId, @Field("deliveryRange") String deliveryRange, @Field("minMonery") String minMonery,
                                                    @Field("packingFee") String packingFee, @Field("deliveryTime") String deliveryTime, @Field("deliverFee") String deliveryFee, @Field("openingTime") String openingTime,
                                                    @Field("closingTime") String closingTime, @Field("shopDayOff") String shopDayOff);

    //更改数据排序
    @FormUrlEncoded
    @POST("app/shop/changeStock")
    Observable<BaseBeanResult> changeStock(@Field("goodsId") String goodsId, @Field("stock") String stock);

    //删除订单
    @FormUrlEncoded
    @POST("app/order/delOrder")
    Observable<BaseBeanResult> delOrder(@Field("orderId") String orderId);

    //订单送达
    @FormUrlEncoded
    @POST("app/order/confirmOrder")
    Observable<BaseBeanResult> confirmOrder(@Field("orderId") String orderId);

    //获取订单详情
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/order/groupOrderInfo")
    Observable<BaseBeanResult> groupOrderInfo(@Query("orderId") String orderId);

    //骑手端接口
    //更改状态
    @FormUrlEncoded
    @POST("app/runner/changeReceiptStatus")
    Observable<BaseBeanResult> changeReceiptStatus(@Field("id") String id, @Field("receiptStatus") String receiptStatus);

    //完善信息
    @Multipart
    @POST("app/runner/addInfos")
    Observable<BaseBeanResult> addInfos(@Part("id") RequestBody id, @PartMap Map<String, RequestBody> imgs, @Part("userName") RequestBody userName, @Part("identityCard") RequestBody identifyCard);

    //设置联系手机号
    @FormUrlEncoded
    @POST("app/runner/changePhone")
    Observable<BaseBeanResult> modifyPhone(@Field("id") String id, @Field("phone") String phone);

    //历史配送
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/runner/queryHistoryOrder")
    Observable<BaseBeanResult> queryHistoryOrder(@Query("runnerId") String runnerId, @Query("defaultCurrent") String defaultCurrent, @Query("pageSize") String pageSize);

    //首页列表
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/runner/getOrderList")
    Observable<BaseBeanResult> getOrderList(@Query("id") String id, @Query("orderStatus") String orderStatus, @Query("defaultCurrent") String defaultCurrent, @Query("pageSize") String pageSize);

    //抢单操作
    @FormUrlEncoded
    @POST("app/order/grabOrder")
    Observable<BaseBeanResult> grabOrder(@Field("orderId") String orderId, @Field("runnerId") String runnerId);

    //放弃配送
    @FormUrlEncoded
    @POST("app/runner/giveUpOrder")
    Observable<BaseBeanResult> giveUpOrder(@Field("orderId") String orderId);

    //设置支付密码
    @FormUrlEncoded
    @POST("app/runner/setPayPassword")
    Observable<BaseBeanResult> setPayPassword(@Field("id") String id, @Field("payPwd") String payPwd, @Field("shopId") String shopId, @Field("roleType") String roleType);

    //校验支付密码
    @FormUrlEncoded
    @POST("app/runner/checkPayPwd")
    Observable<BaseBeanResult> checkPayPwd(@Field("id") String id, @Field("payPwd") String payPwd, @Field("shopId") String shopId, @Field("roleType") String roleType);

    //登录密码修改
    @FormUrlEncoded
    @POST("app/runner/updatePassword")
    Observable<BaseBeanResult> updatePassword(@Field("id") String id, @Field("oldPwd") String oldPwd, @Field("pwd") String pwd, @Field("shopId") String shopId, @Field("roleType") String roleType);

    //历史配送
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/runner/queryWithdrawRecord")
    Observable<BaseBeanResult> queryWithdrawRecord(@Query("runnerId") String runnerId, @Query("defaultCurrent") String defaultCurrent, @Query("pageSize") String pageSize);

    //设置支付密码
    @FormUrlEncoded
    @POST("app/runner/saveWithdraw")
    Observable<BaseBeanResult> saveWithdraw(@Field("id") String id, @Field("money") String money, @Field("withdrawType") String withdrawType, @Field("payPwd") String payPwd);

    //获取账户信息
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/runner/getAccountInfo")
    Observable<BaseBeanResult> getAccountInfo(@Query("id") String id);

    //获取收款方式
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/runner/getAccountType")
    Observable<BaseBeanResult> getRunnerType(@Query("id") String id);

    //店铺激活
    @FormUrlEncoded
    @POST("app/shop/activation")
    Observable<BaseBeanResult> activation(@Field("shopId") String shopId, @Field("feeSettingId") String feeSettingId);

    //获取套餐
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("app/shop/queryFeeSetting")
    Observable<BaseBeanResult> queryFeeSetting();

    //召唤骑士
    @FormUrlEncoded
    @POST("app/shop/callRunner")
    Observable<BaseBeanResult> callRunner(@Field("shopId") String shopId, @Field("sendStatus") String sendStatus);

    //扫描骑手二维码
    @FormUrlEncoded
    @POST("/app/shop/scanQr")
    Observable<BaseBeanResult> scanQr(@Field("qr") String qr, @Field("shopId") String shopId);

    //获取骑手列表
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("/app/shop/queryRunners")
    Observable<BaseBeanResult> queryRunners(@Query("shopId") String shopId);

    //绑定骑手
    @FormUrlEncoded
    @POST("app/shop/bindingRunner")
    Observable<BaseBeanResult> bindingRunner(@Field("shopId") String shopId, @Field("id") String id, @Field("runnerType") String runnerType);

    //刷新信息
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("/app/shop/refreshShopInfo")
    Observable<BaseBeanResult> refreshShopInfo(@Query("shopId") String shopId);

    //微信授权
    @FormUrlEncoded
    @POST("/app/shop/appOauth2")
    Observable<BaseBeanResult> appOauth2(@Field("shopId") String shopId, @Field("runnerId") String runnerId, @Field("code") String code);

    //微信获取token
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("sns/oauth2/access_token")
    Observable<WxBean> access_token(@Query("appid") String appid, @Query("secret") String secret, @Query("code") String code, @Query("WxBean") String grant_type);

    //微信获取用户信息
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("sns/userinfo")
    Observable<WxBean> getUserInfo(@Query("access_token") String token, @Query("openid") String openId);

    //版本更新
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("/app/version/queryNewVersion")
    Observable<BaseBeanResult> update(@Query("terminalType") String terminalType);

    /*@FormUrlEncoded
    @POST("/app/version/queryNewVersion")
    Observable<BaseBeanResult> appOauth2(@Field("terminalType") String terminalType);*/

}
