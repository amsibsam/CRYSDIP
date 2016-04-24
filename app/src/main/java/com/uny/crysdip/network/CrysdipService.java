package com.uny.crysdip.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.uny.crysdip.Pojo.IndustriDetail;
import com.uny.crysdip.Pojo.ListIndustri;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.BaseUrl;
import retrofit.GsonConverterFactory;
import retrofit.HttpException;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by root on 09/04/16.
 */
public class CrysdipService {
    private interface CrysdipApi {
        @GET("industri/list")
        Observable<ListIndustriResponse> getListIndustri();

        @GET("industri/detail")
        Observable<IndustriDetailResponse> getIndustriDetail(@Query("id") int id);
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

    public Observable<IndustriDetail> getIndustriDetail(int id){
        return crysdipApi.getIndustriDetail(id)
                .map(new Func1<IndustriDetailResponse, IndustriDetail>() {
                    @Override
                    public IndustriDetail call(IndustriDetailResponse industriDetailResponse) {
                        return industriDetailResponse.industri.toIndustriDetailPojo();
                    }
                });
    }

//    public Observable<Speciality> getSpeciality() {
//        return crysdipApi.listSpeciality()
//                .flatMap(new Func1<GenericResponse<ListSpecialityResponse>, Observable<ListSpecialityResponse.Speciality>>() {
//                    @Override
//                    public Observable<ListSpecialityResponse.Speciality> call(GenericResponse<ListSpecialityResponse> listSpecialityResponse) {
//                        return Observable.from(listSpecialityResponse.parse(gson, ListSpecialityResponse.class).doctor_specialties);
//                    }
//                })
//                .map(new Func1<ListSpecialityResponse.Speciality, Speciality>() {
//                    @Override
//                    public Speciality call(ListSpecialityResponse.Speciality speciality) {
//                        return speciality.toSpecialityPojo();
//                    }
//                });
//    }



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

            ListIndustri toIndustriPojo(){
                return new ListIndustri(id, namaIndustri, alamat);
            }
        }
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

        class Industri{
            String namaIndustri;
            String deskripsi;
            String alamat;
            String lat;
            String lng;
            int jumlahKaryawan;
            String fotoUrl;

            IndustriDetail toIndustriDetailPojo(){
                return new IndustriDetail(namaIndustri, deskripsi, alamat, Double.parseDouble(lat), Double.parseDouble(lng), jumlahKaryawan, fotoUrl);
            }
        }


    }



}
