package com.brandsin.driver.database

import com.brandsin.driver.model.IntroResponse
import com.brandsin.driver.model.auth.login.LoginResponse
import com.brandsin.driver.model.auth.verifycode.VerifyCodeRequest
import com.brandsin.driver.model.auth.devicetoken.DeviceTokenRequest
import com.brandsin.driver.model.auth.devicetoken.DeviceTokenResponse
import com.brandsin.driver.model.auth.forgot.ForgotPassRequest
import com.brandsin.driver.model.auth.forgot.ForgotPassResponse
import com.brandsin.driver.model.auth.login.LoginRequest
import com.brandsin.driver.model.auth.register.RegisterResponse
import com.brandsin.driver.model.auth.resendcode.ResendCodeRequest
import com.brandsin.driver.model.auth.resendcode.ResendCodeResponse
import com.brandsin.driver.model.auth.resetpass.ResetPassRequest
import com.brandsin.driver.model.auth.resetpass.ResetPassResponse
import com.brandsin.driver.model.auth.setting.countryId.CountryIdResponse
import com.brandsin.driver.model.auth.verifycode.VerifyCodeResponse
import com.brandsin.driver.model.main.homepage.OrdersResponse
import com.brandsin.driver.model.menu.commonquest.CommonQuesResponse
import com.brandsin.driver.model.menu.notifications.ReadNotificationRequest
import com.brandsin.driver.model.main.order.CancelOrderRequest
import com.brandsin.driver.model.profile.changePass.ChangePassRequest
import com.brandsin.driver.model.profile.changePass.ChangePassResponse
import com.brandsin.driver.model.profile.updateprofile.UpdateProfileRequest
import com.brandsin.driver.model.profile.updateprofile.UpdateProfileResponse
import com.brandsin.driver.model.menu.help.HelpQuesResponse
import com.brandsin.driver.model.menu.notifications.NotificationResponse
import com.brandsin.driver.model.menu.notifications.ReadNotificationResponse
import com.brandsin.driver.model.menu.settings.PhoneNumberResponse
import com.brandsin.driver.model.menu.settings.SocialLinksResponse
import com.brandsin.driver.model.main.orderdetails.OrderDetailsResponse
import com.brandsin.driver.model.main.orderdetails.UpdateStatusRequest
import com.brandsin.driver.model.main.orderdetails.UpdateStatusResponse
import com.brandsin.driver.model.main.reports.OrderReportsResponse
import com.brandsin.driver.model.menu.active.ActiveRequest
import com.brandsin.driver.model.menu.active.ActiveResponse
import com.brandsin.driver.model.profile.UpdateLocatoin.UpdateLocatoinRequest
import com.brandsin.driver.model.profile.UpdateLocatoin.UpdateLocatoinResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface
{
    /* ---------- Auth APIs -------- */
    // register
    @Multipart
    @POST("/api/hajaty/driver/register")
    suspend fun register(
        @Part("user[name]") name: RequestBody,
        @Part("user[last_name]") last_name: RequestBody,
        @Part("user[phone_number]") phone_number: RequestBody,
        @Part("user[email]") email: RequestBody,
        @Part("user[password]") password: RequestBody,
        @Part("driver[lat]") lat: RequestBody,
        @Part("driver[lng]") lng: RequestBody,
        @Part("driver[vehicle_type]") vehicle_type: RequestBody,
        @Part("driver[vehicle_number]") vehicle_number: RequestBody,
        @Part("driver[driving_licence]") driving_licence: RequestBody,
        @Part national_id_image: MultipartBody.Part,
        @Part driving_licence_image: MultipartBody.Part,
        @Part("driver[store_id]") store_id: RequestBody,
        @Part user_picture: MultipartBody.Part,
        @Part vehicle_image: MultipartBody.Part,
        @Part("lang") lang: RequestBody
    ): RegisterResponse
    // login
    @POST("/api/hajaty/driver/login")
    fun login(@Body loginRequest: LoginRequest?): Call<LoginResponse?>

    // country_id+Condition
    @GET("/api/common/settings")
    fun getCountryId(@Query("code") code: String?, @Query("lang") lang: String?): Call<CountryIdResponse?>?

    // verify
    @POST("/api/users/check_code")
    fun verify(@Body verifyCodeRequest: VerifyCodeRequest?): Call<VerifyCodeResponse?>
    // resendCode
    @POST("/api/users/resend_code")
    fun resendCode(@Body resendCodeRequest: ResendCodeRequest?): Call<ResendCodeResponse?>
    // forgotPass
    @POST("/api/users/forget_password")
    fun forgotPass(@Body forgotPassRequest: ForgotPassRequest?): Call<ForgotPassResponse?>
    // resetPass
    @POST("/api/users/update_password")
    fun resetPass(@Body resetPassRequest: ResetPassRequest?): Call<ResetPassResponse?>
    // changePass
    @POST("/api/users/update_user")
    fun changePass(@Body changePassRequest: ChangePassRequest?): Call<ChangePassResponse?>
    // updateProfile
    @POST("/api/users/update_user")
    fun updateProfile(@Body updateProfileRequest: UpdateProfileRequest?): Call<UpdateProfileResponse?>
    // deviceToken
    @POST("/api/users/update_token")
    fun deviceToken(@Body deviceTokenRequest: DeviceTokenRequest?): Call<DeviceTokenResponse?>


    @GET("/api/hajaty/notifications")
    suspend fun getNotifications(@Query("limit") limit: Int, @Query("page") page: Int,
                                 @Query("user_id") user_id: Int): NotificationResponse

    @POST("/api/hajaty/notifications")
    suspend fun readNotification(@Body request : ReadNotificationRequest): ReadNotificationResponse

    @GET("/api/hajaty/pages")
    suspend fun getCommonQues(@Query("type") type: String, @Query("lang") lang: String): CommonQuesResponse

    @GET("/api/hajaty/pages")
    suspend fun getHelpQues(@Query("type") type: String, @Query("lang") lang: String): HelpQuesResponse

    @GET("/api/hajaty/driver/orders")
    suspend fun getStoreOrders(@Query("lang") lang: String,
                               @Query("driver_id") driver_id: Int,
                               @Query("status") status: String): OrdersResponse


    @GET("/api/common/settings")
    suspend fun getPhoneNumber(@Query("code") code: String, @Query("lang") lang: String): PhoneNumberResponse

    @GET("/api/common/settings")
    suspend fun getSocialLinks(@Query("code") code: String, @Query("lang") lang: String): SocialLinksResponse


    @GET("/api/orders/order_details")
    suspend fun getOrderDetails(@Query("order_id") id : Int, @Query("lang") lang : String): OrderDetailsResponse

    @POST("/api/orders/cancel_order")
    suspend fun cancelOrder(@Body cancelRequest : CancelOrderRequest): OrderDetailsResponse

    // update_status
    @POST("/api/hajaty/order/update_status")
    fun setUpdateStatus(@Body updateStatusRequest: UpdateStatusRequest?): Call<UpdateStatusResponse?>

    //report
    @GET("/api/hajaty/driver/report")
    suspend fun getReports(
            @Query("driver_id") driver_id: Int,
            @Query("limit") limit: Int,
            @Query("page") page: Int,
            @Query("type") type: String,
            @Query("from") from: String,
            @Query("to") to: String
    ): OrderReportsResponse

    // active
    @POST("/api/hajaty/driver/is_working")
    fun setActive(@Body activeRequest: ActiveRequest?): Call<ActiveResponse?>

    //Intro
    @GET("/api/common/introduction_app")
    fun getIntro(@Query("app") app: String ): Call<IntroResponse?>

    @POST("/api/hajaty/driver/update_location")
    fun updateLocation(@Body updateLocation: UpdateLocatoinRequest):Call<UpdateLocatoinResponse?>

}