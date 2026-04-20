package vn.huy.quanlydaotao.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.huy.quanlydaotao.data.local.dao.LopHocDao;
import vn.huy.quanlydaotao.data.local.entity.LopHocEntity;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.dto.LopHocResponse;
import vn.huy.quanlydaotao.domain.model.LopHoc;
import vn.huy.quanlydaotao.domain.repository.ILopHocRepository;

public class LopHocRepositoryImpl implements ILopHocRepository {

    private final LopHocDao lopHocDao;
    private final DichVuApi api;

    public LopHocRepositoryImpl(LopHocDao lopHocDao, DichVuApi api) {
        this.lopHocDao = lopHocDao;
        this.api = api;
    }

    @Override
    public void dongBoLopHoc(int idKhoaHoc, int idNguoiDung) {
        api.getDanhSachLopHoc(idKhoaHoc, idNguoiDung).enqueue(new Callback<LopHocResponse>() {
            @Override
            public void onResponse(Call<LopHocResponse> call, Response<LopHocResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        List<LopHocEntity> entities = new ArrayList<>();
                        for (LopHocResponse.LopHocDTO dto : response.body().getData()) {
                            entities.add(new LopHocEntity(
                                    dto.getId(),
                                    dto.getIdKhoaHoc(),
                                    dto.getTenLop(),
                                    dto.getNgayBatDau(),
                                    dto.getNgayKetThuc(),
                                    dto.getDaDangKy()
                            ));
                        }
                        lopHocDao.deleteByKhoaHoc(idKhoaHoc);
                        lopHocDao.insertAll(entities);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<LopHocResponse> call, Throwable t) {}
        });
    }

    @Override
    public LiveData<List<LopHoc>> getDanhSachLopHocLocal(int idKhoaHoc) {
        return Transformations.map(lopHocDao.getLopHocByKhoaHoc(idKhoaHoc), entities -> {
            List<LopHoc> models = new ArrayList<>();
            for (LopHocEntity entity : entities) {
                models.add(new LopHoc(
                        entity.id,
                        entity.tenLop,
                        entity.ngayBatDau,
                        entity.ngayKetThuc,
                        entity.idKhoaHoc,
                        entity.daDangKy
                ));
            }
            return models;
        });
    }
}