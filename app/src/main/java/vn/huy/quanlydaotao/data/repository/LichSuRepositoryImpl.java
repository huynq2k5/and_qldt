package vn.huy.quanlydaotao.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.huy.quanlydaotao.data.local.dao.LichSuDao;
import vn.huy.quanlydaotao.data.local.entity.LichSuEntity;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.dto.LichSuResponse;
import vn.huy.quanlydaotao.domain.model.LichSuKT;
import vn.huy.quanlydaotao.domain.repository.IRepositoryLichSu;

public class LichSuRepositoryImpl implements IRepositoryLichSu {

    private final LichSuDao lichSuDao;
    private final DichVuApi api;

    public LichSuRepositoryImpl(LichSuDao lichSuDao, DichVuApi api) {
        this.lichSuDao = lichSuDao;
        this.api = api;
    }

    @Override
    public void dongBoLichSuKiMTra(int idNguoiDung) {
        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("id_nguoi_dung", idNguoiDung);

        api.xemLichSuKiemTra(requestBody).enqueue(new Callback<LichSuResponse>() {
            @Override
            public void onResponse(Call<LichSuResponse> call, Response<LichSuResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    new Thread(() -> {
                        List<LichSuEntity> entities = new ArrayList<>();
                        for (LichSuResponse.LichSuDTO dto : response.body().getData()) {
                            entities.add(new LichSuEntity(
                                    dto.getId(),
                                    dto.getIdNguoiDung(),
                                    dto.getIdBaiKiemTra(),
                                    dto.getDiemSo(),
                                    dto.getTrangThai(),
                                    dto.getThoiGianNop()
                            ));
                        }
                        lichSuDao.deleteByNguoiDung(idNguoiDung);
                        lichSuDao.insertAll(entities);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<LichSuResponse> call, Throwable t) {}
        });
    }

    @Override
    public LiveData<List<LichSuKT>> getLichSuKiMTraLocal(int idNguoiDung) {
        return Transformations.map(lichSuDao.getLichSuByNguoiDung(idNguoiDung), entities -> {
            List<LichSuKT> models = new ArrayList<>();
            for (LichSuEntity entity : entities) {
                models.add(new LichSuKT(
                        entity.id,
                        entity.idNguoiDung,
                        entity.idBaiKiemTra,
                        entity.diemSo,
                        entity.trangThai,
                        entity.thoiGianNop
                ));
            }
            return models;
        });
    }
}