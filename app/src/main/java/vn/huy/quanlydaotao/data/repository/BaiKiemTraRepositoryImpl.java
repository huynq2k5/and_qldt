package vn.huy.quanlydaotao.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.huy.quanlydaotao.data.local.dao.BaiKiemTraDao;
import vn.huy.quanlydaotao.data.local.entity.BaiKiemTraEntity;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.dto.BaiKiemTraResponse;
import vn.huy.quanlydaotao.domain.model.BaiKiemTra;
import vn.huy.quanlydaotao.domain.repository.IRepositoryBaiKiemTra;

public class BaiKiemTraRepositoryImpl implements IRepositoryBaiKiemTra {
    private final BaiKiemTraDao baiKiemTraDao;
    private final DichVuApi dichVuApi;

    public BaiKiemTraRepositoryImpl(BaiKiemTraDao baiKiemTraDao, DichVuApi dichVuApi) {
        this.baiKiemTraDao = baiKiemTraDao;
        this.dichVuApi = dichVuApi;
    }

    @Override
    public LiveData<List<BaiKiemTra>> getDanhSachBaiKiemTra() {
        return Transformations.map(baiKiemTraDao.getAll(), entities -> {
            List<BaiKiemTra> domainList = new ArrayList<>();
            for (BaiKiemTraEntity entity : entities) {
                domainList.add(new BaiKiemTra(
                        entity.getId(),
                        entity.getIdKhoaHoc(),
                        entity.getTieuDe(),
                        entity.getDiemDat(),
                        entity.getThoiGianLam(),
                        entity.getTenKhoaHoc()
                ));
            }
            return domainList;
        });
    }

    @Override
    public void refreshBaiKiemTra(int idNguoiDung) {
        dichVuApi.getDanhSachBaiKiemTra(idNguoiDung).enqueue(new Callback<List<BaiKiemTraResponse>>() {
            @Override
            public void onResponse(Call<List<BaiKiemTraResponse>> call, Response<List<BaiKiemTraResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        List<BaiKiemTraEntity> entities = new ArrayList<>();
                        for (BaiKiemTraResponse dto : response.body()) {
                            entities.add(new BaiKiemTraEntity(
                                    dto.getId(),
                                    dto.getIdKhoaHoc(),
                                    dto.getTieuDe(),
                                    dto.getDiemDat(),
                                    dto.getThoiGianLam(),
                                    dto.getTenKhoaHoc()
                            ));
                        }
                        baiKiemTraDao.deleteAll();
                        baiKiemTraDao.insertAll(entities);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<List<BaiKiemTraResponse>> call, Throwable t) {
                // Có thể Log lỗi ở đây để debug
            }
        });
    }
}