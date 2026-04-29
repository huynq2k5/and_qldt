package vn.huy.quanlydaotao.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.huy.quanlydaotao.data.local.dao.CauHoiDao;
import vn.huy.quanlydaotao.data.local.entity.CauHoiEntity;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.dto.CauHoiResponse;
import vn.huy.quanlydaotao.domain.model.CauHoi;
import vn.huy.quanlydaotao.domain.repository.IRepositoryCauHoi;

public class CauHoiRepositoryImpl implements IRepositoryCauHoi {
    private final CauHoiDao cauHoiDao;
    private final DichVuApi dichVuApi;

    public CauHoiRepositoryImpl(CauHoiDao cauHoiDao, DichVuApi dichVuApi) {
        this.cauHoiDao = cauHoiDao;
        this.dichVuApi = dichVuApi;
    }

    @Override
    public LiveData<List<CauHoi>> getDanhSachCauHoi(int idBaiKiemTra) {
        return Transformations.map(cauHoiDao.getCauHoiByBaiKiemTra(idBaiKiemTra), entities -> {
            List<CauHoi> domainList = new ArrayList<>();
            for (CauHoiEntity entity : entities) {
                domainList.add(new CauHoi(
                        entity.getId(),
                        entity.getIdBaiKiemTra(),
                        entity.getNoiDung(),
                        entity.getCauA(),
                        entity.getCauB(),
                        entity.getCauC(),
                        entity.getCauD()
                ));
            }
            return domainList;
        });
    }

    @Override
    public void refreshCauHoi(int idNguoiDung, int idBaiKiemTra) {
        dichVuApi.getDanhSachCauHoi(idNguoiDung, idBaiKiemTra).enqueue(new Callback<List<CauHoiResponse>>() {
            @Override
            public void onResponse(Call<List<CauHoiResponse>> call, Response<List<CauHoiResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        List<CauHoiEntity> entities = new ArrayList<>();
                        for (CauHoiResponse dto : response.body()) {
                            entities.add(new CauHoiEntity(
                                    dto.getId(),
                                    dto.getIdBaiKiemTra(),
                                    dto.getNoiDung(),
                                    dto.getCauA(),
                                    dto.getCauB(),
                                    dto.getCauC(),
                                    dto.getCauD()
                            ));
                        }
                        cauHoiDao.deleteAllByBaiKiemTra(idBaiKiemTra);
                        cauHoiDao.insertAll(entities);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<List<CauHoiResponse>> call, Throwable t) {
                // Xử lý lỗi kết nối
            }
        });
    }
}