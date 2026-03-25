package vn.huy.quanlydaotao.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.huy.quanlydaotao.data.local.dao.KhoaHocDao;
import vn.huy.quanlydaotao.data.local.entity.KhoaHocEntity;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.dto.KhoaHocResponse;
import vn.huy.quanlydaotao.domain.model.KhoaHoc;
import vn.huy.quanlydaotao.domain.repository.IKhoaHocRepository;

public class KhoaHocRepositoryImpl implements IKhoaHocRepository {
    private final KhoaHocDao khoaHocDao;
    private final DichVuApi dichVuApi;

    public KhoaHocRepositoryImpl(KhoaHocDao khoaHocDao, DichVuApi dichVuApi) {
        this.khoaHocDao = khoaHocDao;
        this.dichVuApi = dichVuApi;
    }

    @Override
    public LiveData<List<KhoaHoc>> getDanhSachKhoaHoc() {
        return Transformations.map(khoaHocDao.getDanhSachKhoaHoc(), entities -> {
            List<KhoaHoc> domainList = new ArrayList<>();
            for (KhoaHocEntity entity : entities) {
                domainList.add(new KhoaHoc(entity.getId(), entity.getTenKhoaHoc(), entity.getMoTa(), entity.getNgayTao()));
            }
            return domainList;
        });
    }

    @Override
    public void refreshKhoaHoc() {
        dichVuApi.getDanhSachKhoaHoc().enqueue(new Callback<List<KhoaHocResponse>>() {
            @Override
            public void onResponse(Call<List<KhoaHocResponse>> call, Response<List<KhoaHocResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        List<KhoaHocEntity> entities = new ArrayList<>();
                        for (KhoaHocResponse dto : response.body()) {
                            entities.add(new KhoaHocEntity(dto.getId(), dto.getTenKhoaHoc(), dto.getMoTa(), dto.getNgayTao()));
                        }
                        khoaHocDao.deleteAll();
                        khoaHocDao.insertAll(entities);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<List<KhoaHocResponse>> call, Throwable t) {
                // Xử lý lỗi
            }
        });
    }
}
