package vn.huy.quanlydaotao.data.repository;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.huy.quanlydaotao.data.local.dao.TienDoDao;
import vn.huy.quanlydaotao.data.local.entity.TienDoEntity;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.dto.TienDoRequest;
import vn.huy.quanlydaotao.data.remote.dto.TienDoResponse;
import vn.huy.quanlydaotao.domain.repository.ITienDoRepository;

public class TienDoRepositoryImpl implements ITienDoRepository {

    private final TienDoDao tienDoDao;
    private final DichVuApi api;

    public TienDoRepositoryImpl(TienDoDao tienDoDao, DichVuApi api) {
        this.tienDoDao = tienDoDao;
        this.api = api;
    }

    @Override
    public LiveData<TienDoResponse> luuTienDoRemote(int idNguoiDung, int idBaiHoc, int phanTram) {
        MutableLiveData<TienDoResponse> ketQua = new MutableLiveData<>();
        TienDoRequest request = new TienDoRequest(idNguoiDung, idBaiHoc, phanTram);

        api.luuTienDo(request).enqueue(new Callback<TienDoResponse>() {
            @Override
            public void onResponse(Call<TienDoResponse> call, Response<TienDoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        luuVaoLocal(idNguoiDung, idBaiHoc, phanTram);
                    }
                    ketQua.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TienDoResponse> call, Throwable t) {
                Log.e("HUY_DEBUG", "Lỗi cập nhật tiến độ: " + t.getMessage());
                ketQua.setValue(null);
            }
        });

        return ketQua;
    }

    private void luuVaoLocal(int idNguoiDung, int idBaiHoc, int phanTram) {
        new Thread(() -> {
            String ngayHienTai = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            if (tienDoDao.hasTienDo(idNguoiDung, idBaiHoc)) {
                tienDoDao.updateTienDoLocal(idNguoiDung, idBaiHoc, phanTram, ngayHienTai);
            } else {
                TienDoEntity entity = new TienDoEntity();
                entity.idNguoiDung = idNguoiDung;
                entity.idBaiHoc = idBaiHoc;
                entity.phanTram = phanTram;
                entity.ngayCapNhat = ngayHienTai;
                tienDoDao.insert(entity);
            }
        }).start();
    }
}