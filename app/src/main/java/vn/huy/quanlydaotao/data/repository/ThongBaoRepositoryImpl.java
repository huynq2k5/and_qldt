package vn.huy.quanlydaotao.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.huy.quanlydaotao.data.local.dao.ThongBaoDao;
import vn.huy.quanlydaotao.data.local.entity.ThongBaoEntity;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.dto.ThongBaoRequest;
import vn.huy.quanlydaotao.data.remote.dto.ThongBaoResponse;
import vn.huy.quanlydaotao.domain.model.ThongBao;
import vn.huy.quanlydaotao.domain.repository.IRepositoryThongBao;

public class ThongBaoRepositoryImpl implements IRepositoryThongBao {

    private final ThongBaoDao thongBaoDao;
    private final DichVuApi api;

    public ThongBaoRepositoryImpl(ThongBaoDao thongBaoDao, DichVuApi api) {
        this.thongBaoDao = thongBaoDao;
        this.api = api;
    }

    @Override
    public void dongBoThongBao(int idNguoiDung) {
        ThongBaoRequest request = new ThongBaoRequest(idNguoiDung);
        api.layThongBao(request).enqueue(new Callback<ThongBaoResponse>() {
            @Override
            public void onResponse(Call<ThongBaoResponse> call, Response<ThongBaoResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    new Thread(() -> {
                        List<ThongBaoEntity> entities = new ArrayList<>();
                        for (ThongBaoResponse.ThongBaoDTO dto : response.body().getData()) {
                            entities.add(new ThongBaoEntity(
                                    dto.getId(),
                                    dto.getIdNguoiDung(),
                                    dto.getTieuDe(),
                                    dto.getNoiDung(),
                                    dto.getThoiGian(),
                                    dto.getDaXem()
                            ));
                        }
                        thongBaoDao.deleteByNguoiDung(idNguoiDung);
                        thongBaoDao.insertAll(entities);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<ThongBaoResponse> call, Throwable t) {}
        });
    }

    @Override
    public LiveData<List<ThongBao>> getDanhSachThongBaoLocal(int idNguoiDung) {
        return Transformations.map(thongBaoDao.getThongBaoByNguoiDung(idNguoiDung), entities -> {
            List<ThongBao> models = new ArrayList<>();
            for (ThongBaoEntity entity : entities) {
                models.add(new ThongBao(
                        entity.id,
                        entity.idNguoiDung,
                        entity.tieuDe,
                        entity.noiDung,
                        entity.thoiGian,
                        entity.daXem
                ));
            }
            return models;
        });
    }
}