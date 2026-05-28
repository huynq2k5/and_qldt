package vn.huy.quanlydaotao.data.repository;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.dto.DangKyLopRequest;
import vn.huy.quanlydaotao.data.remote.dto.DangKyLopResponse;
import vn.huy.quanlydaotao.domain.repository.IDangKyLopRepository;

public class DangKyLopRepositoryImpl implements IDangKyLopRepository {

    private final DichVuApi api;

    public DangKyLopRepositoryImpl(DichVuApi api) {
        this.api = api;
    }

    @Override
    public LiveData<DangKyLopResponse> dangKyLopRemote(int idNguoiDung, int idLopHoc) {
        MutableLiveData<DangKyLopResponse> ketQua = new MutableLiveData<>();
        DangKyLopRequest request = new DangKyLopRequest(idNguoiDung, idLopHoc);

        api.dangKyLopHoc(request).enqueue(new Callback<DangKyLopResponse>() {
            @Override
            public void onResponse(Call<DangKyLopResponse> call, Response<DangKyLopResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ketQua.setValue(response.body());
                } else {
                    ketQua.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<DangKyLopResponse> call, Throwable t) {
                Log.e("HUY_DEBUG", "Lỗi đăng ký: " + t.getMessage());
                ketQua.setValue(null);
            }
        });

        return ketQua;
    }
}