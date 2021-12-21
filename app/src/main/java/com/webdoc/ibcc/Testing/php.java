package com.webdoc.ibcc.Testing;

import android.content.Context;
import android.widget.Toast;

import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.FileUitls;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.ResponseModels.phpfilesResponse.PhpfilesResponse;
import com.webdoc.ibcc.Retrofit.jsonPlaceHolderApi;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class php {
    public static File file;
    static VolleyRequestController volleyRequestController;

    public static void callImageDocumentApi(Context context) {

        volleyRequestController = new VolleyRequestController(Global.applicationContext);

        if (Global.utils.isInternerConnected(context)) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient();
            client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.MINUTES)
                    .addInterceptor(interceptor)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://equivalence.ibcc.edu.pk/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client) // Set HttpClient to be used by Retrofit
                    .build();

            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);

            for (int i = 0; i < Global.phpfiles.size(); i++) {
                File file = new File(FileUitls.getPath(context, Global.phpfiles.get(i)));
                builder.addFormDataPart(
                        "image[]",
                        file.getName(),
                        RequestBody.create(MediaType.parse(context.getContentResolver().getType(Global.phpfiles.get(i))), file)
                );
            }
            RequestBody requestBody = builder.build();
            jsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);
            Call<PhpfilesResponse> call1 = jsonPlaceHolderApi.callImagesFormDataApi(requestBody);

            call1.enqueue(new Callback<PhpfilesResponse>() {
                @Override
                public void onResponse(Call<PhpfilesResponse> call, retrofit2.Response<PhpfilesResponse> response) {
                    Global.utils.hideCustomLoadingDialog();
                    PhpfilesResponse resp = null;
                    if (response.isSuccessful()) {
                        resp = response.body();
                        for (int i = 0; i < resp.getImageUploadResult().getData().size(); i++) {
                            Global.imagesEducationlList.add(resp.getImageUploadResult().getData().get(i).getImagename());
                            Global.timestamp = resp.getImageUploadResult().getData().get(i).getImagename();
                        }
                        Global.equivalenceAddQualification.setImagesEductaionList(Global.imagesEducationlList);
                        if (Global.phptravellingFiles.size() > 0) {

                            callImageTravellingApi(context);
                        } else {
                            if (Global.isFromEditQualitifcation == true) {
                                Global.utils.showCustomLoadingDialog(Global.applicationContext);
                                volleyRequestController.equivalenceEditQualification();
                            } else {
                                Global.utils.showCustomLoadingDialog(Global.applicationContext);
                                volleyRequestController.equivalenceAddQualification();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<PhpfilesResponse> call, Throwable t) {
                    Toast.makeText(Global.applicationContext, t.getMessage() + "", Toast.LENGTH_SHORT).show();
                    Global.utils.hideCustomLoadingDialog();
                    call.cancel();
                }
            });
        } else {
            Toast.makeText(context, "Please connect internet connection !", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public static void callImageTravellingApi(Context context) {

        if (Global.utils.isInternerConnected(context)) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient();
            client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.MINUTES)
                    .addInterceptor(interceptor)
                    .build();
            String url = Constants.TRAVELLING_URL;
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client) // Set HttpClient to be used by Retrofit
                    .build();

            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);

            for (int i = 0; i < Global.phptravellingFiles.size(); i++) {
                File file = new File(FileUitls.getPath(context, Global.phptravellingFiles.get(i)));
                builder.addFormDataPart(
                        "image[]",
                        file.getName(),
                        RequestBody.create(MediaType.parse(context.getContentResolver().getType(Global.phptravellingFiles.get(i))), file)
                );
            }


            RequestBody requestBody = builder.build();
            jsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);
            Call<PhpfilesResponse> call1 = jsonPlaceHolderApi.callImagesTravellingFormDataApi(requestBody);

            call1.enqueue(new Callback<PhpfilesResponse>() {
                @Override
                public void onResponse(Call<PhpfilesResponse> call, retrofit2.Response<PhpfilesResponse> response) {
                    Global.utils.hideCustomLoadingDialog();

                    PhpfilesResponse resp = null;
                    if (response.isSuccessful()) {
                        resp = response.body();

                        for (int i = 0; i < resp.getImageUploadResult().getData().size(); i++) {

                            Global.imagesTravellinglList.add(resp.getImageUploadResult().getData().get(i).getImagename());
                            Global.timestamp = resp.getImageUploadResult().getData().get(i).getImagename();
                        }
                        Global.equivalenceAddQualification.setImagesTravellingList(Global.imagesTravellinglList);
                        if (Global.isFromEditQualitifcation == true) {
                            Global.utils.showCustomLoadingDialog(Global.applicationContext);
                            volleyRequestController.equivalenceEditQualification();

                        } else {
                            Global.utils.showCustomLoadingDialog(Global.applicationContext);
                            volleyRequestController.equivalenceAddQualification();
                        }
                    } else {
                        Toast.makeText(context, "something went wrong / Server error !", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PhpfilesResponse> call, Throwable t) {
                    Toast.makeText(Global.applicationContext, "Failure, something went wrong !", Toast.LENGTH_SHORT).show();
                    Global.utils.hideCustomLoadingDialog();
                    call.cancel();
                }
            });
        } else {
            Toast.makeText(context, "Please connect internet connection !", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
