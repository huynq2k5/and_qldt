package vn.huy.quanlydaotao.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.huy.quanlydaotao.data.local.dao.BaiHocDao;
import vn.huy.quanlydaotao.data.local.entity.BaiHocEntity;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.dto.BaiHocResponse;
import vn.huy.quanlydaotao.domain.model.BaiHoc;
import vn.huy.quanlydaotao.domain.repository.IBaiHocRepository;

public class BaiHocRepositoryImpl implements IBaiHocRepository {
    private BaiHocDao baiHocDao;
    private DichVuApi api;

    public BaiHocRepositoryImpl(BaiHocDao dao, DichVuApi api) {
        this.baiHocDao = dao;
        this.api = api;
    }

    @Override
    public LiveData<List<BaiHoc>> getDanhSachBaiHoc(int idKhoaHoc) {
        return Transformations.map(baiHocDao.getBaiHocByKhoaHoc(idKhoaHoc), entities -> {
            List<BaiHoc> models = new ArrayList<>();
            for (BaiHocEntity entity : entities) {
                models.add(new BaiHoc(entity.id, entity.idKhoaHoc, entity.tieuDe, entity.loaiNoiDung, entity.duongDanTep));
            }
            return models;
        });
    }

    @Override
    public void refreshBaiHoc(int idKhoaHoc) {
        api.getDanhSachBaiHoc(idKhoaHoc).enqueue(new Callback<List<BaiHocResponse>>() {
            @Override
            public void onResponse(Call<List<BaiHocResponse>> call, Response<List<BaiHocResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        List<BaiHocEntity> entities = new ArrayList<>();
                        for (BaiHocResponse dto : response.body()) {
                            BaiHocEntity entity = new BaiHocEntity();
                            entity.id = dto.getId();
                            entity.idKhoaHoc = dto.getIdKhoaHoc();
                            entity.tieuDe = dto.getTieuDe();
                            entity.loaiNoiDung = dto.getLoaiNoiDung();
                            entity.duongDanTep = dto.getDuongDanTep();
                            entities.add(entity);
                        }
                        baiHocDao.insertAll(entities);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<List<BaiHocResponse>> call, Throwable t) {}
        });
    }
}