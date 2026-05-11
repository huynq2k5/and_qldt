package vn.huy.quanlydaotao.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.dto.NguoiDungRequest;
import vn.huy.quanlydaotao.data.remote.dto.NguoiDungResponse;
import vn.huy.quanlydaotao.domain.repository.INguoiDungRepository;

public class NguoiDungRepositoryImpl implements INguoiDungRepository {
    private final DichVuApi dichVuApi;

    public NguoiDungRepositoryImpl(DichVuApi dichVuApi) {
        this.dichVuApi = dichVuApi;
    }

    @Override
    public LiveData<NguoiDungResponse> suaThongTin(int id, String hoTen, String email) {
        MutableLiveData<NguoiDungResponse> ketQua = new MutableLiveData<>();
        NguoiDungRequest request = new NguoiDungRequest(id, hoTen, email);

        dichVuApi.suaThongTin(request).enqueue(new Callback<NguoiDungResponse>() {
            @Override
            public void onResponse(Call<NguoiDungResponse> call, Response<NguoiDungResponse> response) {
                if (response.isSuccessful()) {
                    ketQua.setValue(response.body());
                } else {
                    ketQua.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<NguoiDungResponse> call, Throwable t) {
                ketQua.setValue(null);
            }
        });
        return ketQua;
    }
}