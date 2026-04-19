package vn.huy.quanlydaotao.data.repository;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.huy.quanlydaotao.data.local.dao.DangKyLopDao;
import vn.huy.quanlydaotao.data.local.entity.DangKyLopEntity;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.dto.DangKyLopRequest;
import vn.huy.quanlydaotao.data.remote.dto.DangKyLopResponse;
import vn.huy.quanlydaotao.domain.repository.IDangKyLopRepository;

public class DangKyLopRepositoryImpl implements IDangKyLopRepository {

    private final DangKyLopDao dangKyLopDao;
    private final DichVuApi api;

    public DangKyLopRepositoryImpl(DangKyLopDao dangKyLopDao, DichVuApi api) {
        this.dangKyLopDao = dangKyLopDao;
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
                    if (response.body().getStatus().equals("success")) {
                        luuVaoLocal(idNguoiDung, idLopHoc);
                    }
                    ketQua.setValue(response.body());
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

    @Override
    public boolean kiemTraDaDangKyLocal(int idNguoiDung, int idLopHoc) {
        return dangKyLopDao.isDaDangKy(idNguoiDung, idLopHoc);
    }

    private void luuVaoLocal(int idNguoiDung, int idLopHoc) {
        new Thread(() -> {
            DangKyLopEntity entity = new DangKyLopEntity();
            entity.idNguoiDung = idNguoiDung;
            entity.idLopHoc = idLopHoc;
            dangKyLopDao.insert(entity);
        }).start();
    }
}