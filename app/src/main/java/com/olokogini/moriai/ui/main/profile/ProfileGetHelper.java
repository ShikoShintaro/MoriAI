package com.olokogini.moriai.ui.main.profile;

import com.olokogini.moriai.api.GetProfileRequest;
import com.olokogini.moriai.api.ProfileResponse;
import com.olokogini.moriai.api.RetroFitClient;
import androidx.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileGetHelper {

    public interface CallbackListener {
        void onSuccess(@Nullable ProfileResponse profile);
        void onError(String error);
    }

    public static void getProfile(String email, CallbackListener listener) {

        Call<ProfileResponse> call = RetroFitClient.INSTANCE
                .getApi()
                .getProfile(new GetProfileRequest(email));

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onError("Failed to load profile");
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }
}