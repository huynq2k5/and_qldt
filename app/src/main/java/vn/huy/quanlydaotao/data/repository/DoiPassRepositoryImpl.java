package vn.huy.quanlydaotao.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.dto.DoiPassRequest;
import vn.huy.quanlydaotao.data.remote.dto.DoiPassResponse;
import vn.huy.quanlydaotao.domain.repository.IDoiPassRepository;

public class DoiPassRepositoryImpl implements IDoiPassRepository {
    private final DichVuApi dichVuApi;

    public DoiPassRepositoryImpl(DichVuApi dichVuApi) {
        this.dichVuApi = dichVuApi;
    }

    @Override
    public LiveData<DoiPassResponse> doiMatKhau(DoiPassRequest request) {
        MutableLiveData<DoiPassResponse> ketQua = new MutableLiveData<>();

        dichVuApi.doiMatKhau(request).enqueue(new Callback<DoiPassResponse>() {
            @Override
            public void onResponse(Call<DoiPassResponse> call, Response<DoiPassResponse> response) {
                if (response.isSuccessful()) {
                    ketQua.setValue(response.body());
                } else {
                    ketQua.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<DoiPassResponse> call, Throwable t) {
                ketQua.setValue(null);
            }
        });

        return ketQua;
    }
}