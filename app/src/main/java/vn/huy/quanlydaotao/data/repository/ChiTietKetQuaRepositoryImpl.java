package vn.huy.quanlydaotao.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.huy.quanlydaotao.data.local.dao.ChiTietKetQuaDao;
import vn.huy.quanlydaotao.data.local.entity.ChiTietKetQuaEntity;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.dto.ChiTietKetQuaResponse;
import vn.huy.quanlydaotao.domain.model.ChiTietKetQua;
import vn.huy.quanlydaotao.domain.repository.IRepositoryChiTietKetQua;

public class ChiTietKetQuaRepositoryImpl implements IRepositoryChiTietKetQua {
    private final ChiTietKetQuaDao chiTietKetQuaDao;
    private final DichVuApi dichVuApi;
    private final Gson gson;

    public ChiTietKetQuaRepositoryImpl(ChiTietKetQuaDao chiTietKetQuaDao, DichVuApi dichVuApi) {
        this.chiTietKetQuaDao = chiTietKetQuaDao;
        this.dichVuApi = dichVuApi;
        this.gson = new Gson();
    }

    @Override
    public LiveData<ChiTietKetQua> getChiTietKetQua(int idKetQua) {
        return Transformations.map(chiTietKetQuaDao.getChiTietById(idKetQua), entity -> {
            if (entity == null) return null;

            Type listType = new TypeToken<List<ChiTietKetQua.CauHoiChiTiet>>() {}.getType();
            List<ChiTietKetQua.CauHoiChiTiet> details = gson.fromJson(entity.getJsonChiTiet(), listType);

            return new ChiTietKetQua(entity.getDiemSo(), entity.getTrangThai(), entity.getThoiGianNop(), details);
        });
    }

    public void loadChiTietFromServer(int idKetQua) {
        dichVuApi.layChiTietKetQua(idKetQua).enqueue(new Callback<ChiTietKetQuaResponse>() {
            @Override
            public void onResponse(Call<ChiTietKetQuaResponse> call, Response<ChiTietKetQuaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ChiTietKetQuaResponse res = response.body();
                    String jsonDetails = gson.toJson(res.getChi_iet());

                    new Thread(() -> {
                        chiTietKetQuaDao.insert(new ChiTietKetQuaEntity(
                                idKetQua, res.getDiem_so(), res.getTrang_thai(), res.getThoi_gian_nop(), jsonDetails
                        ));
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<ChiTietKetQuaResponse> call, Throwable t) {
            }
        });
    }
}