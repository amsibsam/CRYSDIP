package com.uny.crysdip.network;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.uny.crysdip.pojo.IndustriDetail;
import com.uny.crysdip.pojo.IndustriKategoriDetail;
import com.uny.crysdip.pojo.ListIndustri;
import com.uny.crysdip.pojo.Mahasiswa;
import com.uny.crysdip.pojo.Testimoni;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.BaseUrl;
import retrofit.GsonConverterFactory;
import retrofit.HttpException;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by rahardyan on 09/04/16.
 */
public class CrysdipService {
    private interface CrysdipApi {
        @GET("industri/list")
        Observable<ListIndustriResponse> getListIndustri();

        @GET("industri/detail")
        Observable<IndustriDetailResponse> getIndustriDetail(@Query("industri_id") int industriId,
                                                             @Query("mahasiswa_id") int mahasiswaId);

        @GET("industri/kategori-detail")
        Observable<IndustriKategoriDetailResponse> getIndustriKategoriDetail(@Query("industri_id") int industriId);

        @GET("industri/favorite")
        Observable<ListIndustriResponse> getFavoritedIndustri(@Query("mahasiswa_id") int mahasiswaId);

        @GET("industri/testimoni-short")
        Observable<TestimoniListResponse> getTestimoni(@Query("industri_id") int industriId);

        @GET("industri/testimoni")
        Observable<TestimoniListResponse> getTestimoniLong(@Query("industri_id") int industriId);


        @GET("industri/cari")
        Observable<ListRecomendedIndustriResponse> getRecomendation(@Query("nama_kategori[]") String namaKategori1,
                                                          @Query("nama_kategori[]") String namaKategori2,
                                                          @Query("nama_kategori[]") String namaKategori3,
                                                          @Query("nama_kategori[]") String namaKategori4,
                                                          @Query("nama_kategori[]") String namaKategori5,
                                                          @Query("spesifikasi[]") String spesifikasi1,
                                                          @Query("spesifikasi[]") String spesifikasi2,
                                                          @Query("spesifikasi[]") String spesifikasi3,
                                                          @Query("spesifikasi[]") String spesifikasi4,
                                                          @Query("spesifikasi[]") String spesifikasi5,
                                                          @Query("spesifikasi[]") String spesifikasi6,
                                                          @Query("spesifikasi[]") String spesifikasi7,
                                                          @Query("spesifikasi[]") String spesifikasi8,
                                                          @Query("spesifikasi[]") String spesifikasi9);

        @FormUrlEncoded
        @POST("mahasiswa/login")
        Observable<MahasiswaResponse> login(@Field("nim") String nim,
                                            @Field("password") String password);


        @POST("industri/like")
        Observable<FavoriteResponse> setFavorite(@Query("industri_id") int industriId,
                                                 @Query("mahasiswa_id") int mahasiswaId);

        @FormUrlEncoded
        @POST("industri/unlike")
        Observable<FavoriteResponse> setUnfavorite(@Field("industri_id") int industriId,
                                                 @Field("mahasiswa_id") int mahasiswaId);

        @FormUrlEncoded
        @POST("industri/testimoni-post")
        Observable<TestimoniResponse> postTestimoni(@Field("testimoni") String testimoni,
                                                    @Field("mahasiswa_id") int mahasiswaId,
                                                    @Field("industri_id") int industriId);
    }


    private CrysdipApi crysdipApi;

    private Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public CrysdipService(Context context){
        // 1. Prepare the baseUrl. We will dynamically resolve this from the SharedPreferences.
        BaseUrl baseUrl = new BaseUrl() {
            @Override
            public HttpUrl url() {
                final String baseUrl = "http://crysdip.herokuapp.com/api/";
//                final String baseUrl = "http://192.168.1.111:8001/api/";
                return HttpUrl.parse(baseUrl);
            }
        };

        // 2. Create and prepare the Client that will be the "backend".
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(120, TimeUnit.SECONDS);
        client.setReadTimeout(120, TimeUnit.SECONDS);

        // 3. Create and prepare the logging interceptor. This is mainly for debugging purpose.
        // TBD: Is it better to use Stetho instead?
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("retrofit", message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(loggingInterceptor);

        // 4. Almost done. Now, we can create the Retrofit instance.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        // 5. Finally, we can create the model of the API.
        crysdipApi = retrofit.create(CrysdipApi.class);
    }

    public Observable<Mahasiswa> login(String nim, String password){
        return crysdipApi.login(nim, password)
                .map(new Func1<MahasiswaResponse, Mahasiswa>() {
                    @Override
                    public Mahasiswa call(MahasiswaResponse mahasiswaResponse) {
                        return mahasiswaResponse.mahasiswa.toMahasiswaPojo();
                    }
                });
    }

    public Observable<String> setFavorite(int industriId, int mahasiswaId){
        return crysdipApi.setFavorite(industriId, mahasiswaId)
                .map(new Func1<FavoriteResponse, String>() {
                    @Override
                    public String call(FavoriteResponse favoriteResponse) {
                        return favoriteResponse.status;
                    }
                });
    }

    public Observable<String> setUnfavorite(int industriId, int mahasiswaId){
        return crysdipApi.setUnfavorite(industriId, mahasiswaId)
                .map(new Func1<FavoriteResponse, String>() {
                    @Override
                    public String call(FavoriteResponse favoriteResponse) {
                        return favoriteResponse.status;
                    }
                });
    }

    public Observable<String> postTestimoni(final String testimoni, int mahasiswaId, int industriId){
        return crysdipApi.postTestimoni(testimoni, mahasiswaId, industriId)
                .map(new Func1<TestimoniResponse, String>() {
                    @Override
                    public String call(TestimoniResponse testimoniResponse) {
                        return testimoniResponse.message;
                    }
                });
    }



    public Observable<ListIndustri> getListIndustri(){
        return crysdipApi.getListIndustri()
                .flatMap(new Func1<ListIndustriResponse, Observable<ListIndustriResponse.Industri>>() {
                    @Override
                    public Observable<ListIndustriResponse.Industri> call(ListIndustriResponse listIndustriResponse) {
                        return Observable.from(listIndustriResponse.industris);
                    }
                })
                .map(new Func1<ListIndustriResponse.Industri, ListIndustri>() {
                    @Override
                    public ListIndustri call(ListIndustriResponse.Industri industri) {
                        return industri.toIndustriPojo();
                    }
                });
    }



    public Observable<IndustriDetail> getIndustriDetail(int industriId, int mahasiswaId){
        return crysdipApi.getIndustriDetail(industriId, mahasiswaId)
                .map(new Func1<IndustriDetailResponse, IndustriDetail>() {
                    @Override
                    public IndustriDetail call(IndustriDetailResponse industriDetailResponse) {
                        return industriDetailResponse.industri.toIndustriDetailPojo(industriDetailResponse.liked);
                    }
                });
    }

    public Observable<IndustriKategoriDetail> getKategori(final int industriId){
        return crysdipApi.getIndustriKategoriDetail(industriId)
                .map(new Func1<IndustriKategoriDetailResponse, IndustriKategoriDetail>() {
                    @Override
                    public IndustriKategoriDetail call(IndustriKategoriDetailResponse industriKategoriDetailResponse) {
                        return industriKategoriDetailResponse.toIndustriKategoriDetailPojo();
                    }
                });
    }

    public Observable<Testimoni> getTestimoni(int industriId){
        return crysdipApi.getTestimoni(industriId)
                .flatMap(new Func1<TestimoniListResponse, Observable<TestimoniListResponse.Testimoni>>() {
                    @Override
                    public Observable<TestimoniListResponse.Testimoni> call(TestimoniListResponse testimoniListResponse) {
                        return Observable.from(testimoniListResponse.testimoni);
                    }
                })
                .map(new Func1<TestimoniListResponse.Testimoni, Testimoni>() {
                    @Override
                    public Testimoni call(TestimoniListResponse.Testimoni testimoni) {
                        return testimoni.toTestimoniPojo();
                    }
                });
    }

    public Observable<Testimoni> getTestimoniLong(int industriId){
        return crysdipApi.getTestimoniLong(industriId)
                .flatMap(new Func1<TestimoniListResponse, Observable<TestimoniListResponse.Testimoni>>() {
                    @Override
                    public Observable<TestimoniListResponse.Testimoni> call(TestimoniListResponse testimoniListResponse) {
                        return Observable.from(testimoniListResponse.testimoni);
                    }
                })
                .map(new Func1<TestimoniListResponse.Testimoni, Testimoni>() {
                    @Override
                    public Testimoni call(TestimoniListResponse.Testimoni testimoni) {
                        return testimoni.toTestimoniPojo();
                    }
                });
    }



    public Observable<ListIndustri> getFavoritedIndustri(int mahasiswaId){
        return crysdipApi.getFavoritedIndustri(mahasiswaId)
                .flatMap(new Func1<ListIndustriResponse, Observable<ListIndustriResponse.Industri>>() {
                    @Override
                    public Observable<ListIndustriResponse.Industri> call(ListIndustriResponse listIndustriResponse) {
                        return Observable.from(listIndustriResponse.industris);
                    }
                })
                .map(new Func1<ListIndustriResponse.Industri, ListIndustri>() {
                    @Override
                    public ListIndustri call(ListIndustriResponse.Industri industri) {
                        return industri.toIndustriPojo();
                    }
                });
    }

    public Observable<ListIndustri> getRecomendation(String kategori1,
                                                     String kategori2,
                                                     String kategori3,
                                                     String kategori4,
                                                     String kategori5,
                                                     String spesifikasi1,
                                                     String spesifikasi2,
                                                     String spesifikasi3,
                                                     String spesifikasi4,
                                                     String spesifikasi5,
                                                     String spesifikasi6,
                                                     String spesifikasi7,
                                                     String spesifikasi8,
                                                     String spesifikasi9){
        return crysdipApi.getRecomendation(kategori1, kategori2, kategori3, kategori4, kategori5,
                spesifikasi1, spesifikasi2, spesifikasi3, spesifikasi4, spesifikasi5, spesifikasi6, spesifikasi7, spesifikasi8, spesifikasi9)
                .flatMap(new Func1<ListRecomendedIndustriResponse, Observable<ListRecomendedIndustriResponse.Industri>>() {
                    @Override
                    public Observable<ListRecomendedIndustriResponse.Industri> call(ListRecomendedIndustriResponse listIndustriResponse) {
                        return Observable.from(listIndustriResponse.industris);
                    }
                })
                .map(new Func1<ListRecomendedIndustriResponse.Industri, ListIndustri>() {
                    @Override
                    public ListIndustri call(ListRecomendedIndustriResponse.Industri industri) {
                        return industri.toIndustriPojo();
                    }
                });

    }




    @NonNull
    private <T> Func1<Throwable, Observable<T>> handleError() {
        return new Func1<Throwable, Observable<T>>() {
            @Override
            public Observable<T> call(Throwable throwable) {
                if (throwable instanceof HttpException) {
                    HttpException httpException = (HttpException) throwable;
                    try {
                        String errorBody = httpException.response().errorBody().string();
                        GenericResponse<T> genericResponse = gson.fromJson(errorBody, GenericResponse.class);
                        String errorMessage = genericResponse.message;
                        if (errorMessage != null && !errorMessage.equals("")) {
                            return Observable.error(new Exception(errorMessage));
                        } else {
                            return Observable.error(new Exception("There's no error message from server. Please contact developer."));
                        }
                    } catch (IOException e) {
                        return Observable.error(e);
                    }
                }
                return Observable.error(throwable);
            }
        };
    }

    private static class GenericResponse<T> {
        String status;
        JsonElement data;
        String message;

        T parse(Gson gson, Class<T> clz) {
            return gson.fromJson(data, clz);
        }
    }


    /*
    Sample Response
    {
      "status": "success",
      "industris": [
        {
          "id": 3,
          "nama_industri": "Qiscus",
          "alamat": "Jalan Petung"
        }
      ]
    }
    */
    private class ListIndustriResponse{
        String status;
        List<Industri> industris;

        class Industri{
            int id;
            String namaIndustri;
            String alamat;
            String fotoUrl;
            String count;

            ListIndustri toIndustriPojo(){
                return new ListIndustri(id, namaIndustri, alamat, fotoUrl, count == null ? 0: Integer.parseInt(count));
            }
        }
    }

    private class ListRecomendedIndustriResponse{
        String status;
        List<Industri> industris;

        class Industri{
            int id;
            String namaIndustri;
            String alamat;
            String fotoUrl;
            String count;
            ListIndustri toIndustriPojo(){
                return new ListIndustri(id, namaIndustri, alamat, fotoUrl, count == null ? 0: Integer.parseInt(count));
            }
        }
    }

    private class ListIndustriElementResponse{
        String status;
        JsonElement industris;


    }

    /*
    Sample Response
    {
      "status": "success",
      "industri": {
        "nama_industri": "Qiscus",
        "deskripsi": "Qiscus merupakan industri yang bergerak dalam aplikasi realtime chatting",
        "alamat": "Jalan Petung",
        "lat": "123",
        "lng": "321",
        "jumlah_karyawan": 123,
        "foto": "zeus.jpg",
        "foto_url": "https://crysdip.s3-ap-southeast-1.amazonaws.com/1461454525.jpg"
      }
    }
    */
    private class IndustriDetailResponse{
        Industri industri;
        boolean liked;

        class Industri{
            String namaIndustri;
            String deskripsi;
            String alamat;
            String lat;
            String lng;
            int jumlahKaryawan;
            String fotoUrl;

            IndustriDetail toIndustriDetailPojo(boolean favorite){
                return new IndustriDetail(namaIndustri, deskripsi, alamat, Double.parseDouble(lat), Double.parseDouble(lng), jumlahKaryawan, fotoUrl, favorite);
            }
        }
    }

    private class IndustriKategoriDetailResponse{
        String namaKategori;

        IndustriKategoriDetail toIndustriKategoriDetailPojo(){
            return new IndustriKategoriDetail(namaKategori);
        }
    }

    /*
    {
  "status": "success",
  "data": {
        "mahasiswa": {
          "id": 6,
          "nama_mahasiswa": "Rahardyan Bisma",
          "alamat": "Pandes, Panggungharjo",
          "nama_prodi": "Pendidikan Teknik Informatika",
          "nim": "13520244022"
        }
      }
    }
    */
    private static class MahasiswaResponse{
        Mahasiswa mahasiswa;

        class Mahasiswa{
            int id;
            String namaMahasiswa;
            String alamat;
            String namaProdi;
            String nim;

            com.uny.crysdip.pojo.Mahasiswa toMahasiswaPojo(){
                return new com.uny.crysdip.pojo.Mahasiswa(id, namaMahasiswa, alamat, namaProdi, nim);
            }
        }
    }

    private static class FavoriteResponse{
        String status;
    }

    private static class TestimoniResponse{
        String status;
        String message;
    }

    private static class TestimoniListResponse{
        String status;
        List<Testimoni> testimoni;

        class Testimoni{
            String createdAt;
            String namaMahasiswa;
            String nim;
            String testimoni;

            com.uny.crysdip.pojo.Testimoni toTestimoniPojo(){
                return new com.uny.crysdip.pojo.Testimoni(createdAt, namaMahasiswa, nim, testimoni);
            }
        }



    }



}
