package vn.huy.quanlydaotao.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.huy.quanlydaotao.data.local.dao.LichMeetDao;
import vn.huy.quanlydaotao.data.local.entity.LichMeetEntity;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.dto.LichMeetResponse;
import vn.huy.quanlydaotao.domain.model.LichMeet;
import vn.huy.quanlydaotao.domain.repository.ILichMeetRepository;

public class LichMeetRepositoryImpl implements ILichMeetRepository {
    private LichMeetDao lichMeetDao;
    private DichVuApi api;

    public LichMeetRepositoryImpl(LichMeetDao dao, DichVuApi api) {
        this.lichMeetDao = dao;
        this.api = api;
    }

    @Override
    public LiveData<List<LichMeet>> getDanhSachLichMeet(int idLopHoc) {
        return Transformations.map(lichMeetDao.getAllLichMeet(), entities -> {
            List<LichMeet> models = new ArrayList<>();
            for (LichMeetEntity entity : entities) {
                models.add(new LichMeet(entity.id, entity.idLopHoc, entity.tieuDe, entity.linkMeet, entity.thoiGian));
            }
            return models;
        });
    }

    @Override
    public void lamMoiLichMeet(int idLopHoc) {
        android.util.Log.d("HUY_DEBUG", "Đã vào tới Repository, ID lớp: " + idLopHoc);
        api.getDanhSachLichMeet(idLopHoc).enqueue(new Callback<List<LichMeetResponse>>() {
            @Override
            public void onResponse(Call<List<LichMeetResponse>> call, Response<List<LichMeetResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    android.util.Log.d("HUY_DEBUG", "Lấy được " + response.body().size() + " lịch meet");
                    new Thread(() -> {
                        List<LichMeetEntity> entities = new ArrayList<>();
                        for (LichMeetResponse dto : response.body()) {
                            LichMeetEntity entity = new LichMeetEntity();
                            entity.id = dto.getId();
                            entity.idLopHoc = dto.getIdLopHoc();
                            entity.tieuDe = dto.getTieuDe();
                            entity.linkMeet = dto.getLinkMeet();
                            entity.thoiGian = dto.getThoiGian();
                            entities.add(entity);
                        }
                        lichMeetDao.insertAll(entities);
                    }).start();
                }else{
                    android.util.Log.d("HUY_DEBUG", "Server trả lỗi: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<LichMeetResponse>> call, Throwable t) {
                android.util.Log.d("HUY_DEBUG", "Lỗi Retrofit: " + t.getMessage());
            }
        });
    }
}
