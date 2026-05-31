package vn.huy.quanlydaotao.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.huy.quanlydaotao.data.local.dao.ChungChiDao;
import vn.huy.quanlydaotao.data.local.entity.ChungChiEntity;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.dto.ChungChiResponse;
import vn.huy.quanlydaotao.domain.model.ChungChi;
import vn.huy.quanlydaotao.domain.repository.IChungChiRepository;

public class ChungChiRepositoryImpl implements IChungChiRepository {
    private final ChungChiDao chungChiDao;
    private final DichVuApi dichVuApi;

    public ChungChiRepositoryImpl(ChungChiDao chungChiDao, DichVuApi dichVuApi) {
        this.chungChiDao = chungChiDao;
        this.dichVuApi = dichVuApi;
    }

    @Override
    public LiveData<List<ChungChi>> getDanhSachChungChi() {
        return Transformations.map(chungChiDao.getDanhSachChungChiLocal(), entities -> {
            List<ChungChi> domainList = new ArrayList<>();
            for (ChungChiEntity entity : entities) {
                domainList.add(new ChungChi(
                        entity.getId(),
                        entity.getIdKhoaHoc(),
                        entity.getMaQr(),
                        entity.getUrlHinhAnh(),
                        entity.getNgayCap(),
                        entity.getTenKhoaHoc()
                    ));
            }
            return domainList;
        });
    }

    @Override
    public void refreshChungChi(int idNguoiDung) {
        dichVuApi.getDanhSachChungChi(idNguoiDung).enqueue(new Callback<List<ChungChiResponse>>() {
            @Override
            public void onResponse(Call<List<ChungChiResponse>> call, Response<List<ChungChiResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        List<ChungChiEntity> entities = new ArrayList<>();
                        for (ChungChiResponse dto : response.body()) {
                            entities.add(new ChungChiEntity(
                                    dto.getId(),
                                    dto.getIdKhoaHoc(),
                                    dto.getMaQr(),
                                    dto.getUrlHinhAnh(),
                                    dto.getNgayCap(),
                                    dto.getTenKhoaHoc()
                            ));
                        }
                        chungChiDao.deleteAll();
                        chungChiDao.insertAll(entities);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<List<ChungChiResponse>> call, Throwable t) {
            }
        });
    }
}