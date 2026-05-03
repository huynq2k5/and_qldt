package vn.huy.quanlydaotao.data.repository;

import android.util.Log;
import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.dto.BaseResponse;
import vn.huy.quanlydaotao.domain.repository.IFCMRepository;

public class FCMRepositoryImpl implements IFCMRepository {
    private final DichVuApi api;

    public FCMRepositoryImpl(DichVuApi api) {
        this.api = api;
    }

    @Override
    public void updateFcmToken(int idUser, String token) {
        api.luuFcmToken(idUser, token).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("FCM_REPO", response.body().getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Log.e("FCM_REPO", t.getMessage());
            }
        });
    }
}