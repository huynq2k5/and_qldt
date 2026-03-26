package vn.huy.quanlydaotao.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public LopHocRepositoryImpl(LopHocDao lopHocDao, DichVuApi api) {
        this.lopHocDao = lopHocDao;
        this.api = api;
    }

    @Override
    public LiveData<List<LopHoc>> layDanhSachLopHoc(int idKhoaHoc) {
        return Transformations.map(lopHocDao.layDanhSachLopHocTheoKhoaHoc(idKhoaHoc), entities -> 
            entities.stream().map(e -> new LopHoc(
                e.getId(), e.getTenLop(), e.getNgayBatDau(), e.getNgayKetThuc(), e.getIdKhoaHoc()
            )).collect(Collectors.toList())
        );
    }

    @Override
    public void lamMoiLopHoc(int idKhoaHoc) {
        api.getDanhSachLopHoc(idKhoaHoc).enqueue(new Callback<List<LopHocResponse>>() {
            @Override
            public void onResponse(Call<List<LopHocResponse>> call, Response<List<LopHocResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    executor.execute(() -> {
                        List<LopHocEntity> entities = response.body().stream().map(r -> 
                            new LopHocEntity(r.getId(), r.getTenLop(), r.getNgayBatDau(), r.getNgayKetThuc(), r.getIdKhoaHoc())
                        ).collect(Collectors.toList());
                        lopHocDao.luuDanhSachLopHoc(entities);
                    });
                }
            }

            @Override
            public void onFailure(Call<List<LopHocResponse>> call, Throwable t) {
                // Xử lý lỗi
            }
        });
    }
}
