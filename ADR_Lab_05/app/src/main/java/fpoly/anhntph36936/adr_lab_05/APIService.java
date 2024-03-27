package fpoly.anhntph36936.adr_lab_05;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    String DOMAIN = "http://192.168.78.20:3000/api/";

    @GET("lab5")
    Call<List<Title>> getData();

    @POST("add")
    Call<List<Title>> addData(@Body Title title);

    @DELETE("xoa/{id}")
    Call<List<Title>> delData(@Path("id") String id);

    @PUT("update/{id}")
    Call<List<Title>> upData(@Path("id") String id,@Body Title model);

    @GET("/timData/{name}")
    Call<List<Title>> timData(@Query("name") String name);

}
