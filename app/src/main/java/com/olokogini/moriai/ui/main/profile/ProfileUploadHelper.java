package com.olokogini.moriai.ui.main.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.olokogini.moriai.api.RetroFitClient;
import com.olokogini.moriai.api.UploadResponse;

public class ProfileUploadHelper {

    public interface UploadCallback {
        void onSuccess(String imageUrl);
        void onError(String error);
    }

    public static void uploadImage(Context context, Uri uri, UploadCallback callback) {

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                    context.getContentResolver(),
                    uri
            );

            bitmap = Bitmap.createScaledBitmap(bitmap, 512, 512, true);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);

            byte[] imageBytes = baos.toByteArray();

            RequestBody requestFile = RequestBody.create(
                    imageBytes,
                    MediaType.parse("image/jpeg")
            );

            MultipartBody.Part body = MultipartBody.Part.createFormData(
                    "image",
                    "profile.jpg",
                    requestFile
            );

            RetroFitClient.INSTANCE.getApi().uploadImage(body)
                    .enqueue(new Callback<UploadResponse>() {
                        @Override
                        public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                String url = response.body().getImageUrl();

                                Log.d("UPLOAD SUCCESS", "URL : " + url);

                                callback.onSuccess(url);
                            } else {
                                Log.e("UPLOAD_ERROR", "Response Failed: " + response.message());
                                callback.onError("Upload Failed");
                            }
                        }

                        @Override
                        public void onFailure(Call<UploadResponse> call, Throwable t) {
                            Log.e("UPLOAD_FAIL", t.getMessage());
                            callback.onError(t.getMessage());
                        }
                    });

        } catch (IOException e) {
            callback.onError(e.getMessage());
        }
    }
}