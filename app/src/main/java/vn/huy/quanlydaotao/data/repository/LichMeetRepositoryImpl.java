package vn.huy.quanlydaotao.data.repository;

import android.util.Log;
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

    private final LichMeetDao lichMeetDao;
    private final DichVuApi api;

    public LichMeetRepositoryImpl(LichMeetDao lichMeetDao, DichVuApi api) {
        this.lichMeetDao = lichMeetDao;
        this.api = api;
    }

    @Override
    public void dongBoLichHoc(int idNguoiDung) {
        api.getLichMeetDaDangKy(idNguoiDung).enqueue(new Callback<LichMeetResponse>() {
            @Override
            public void onResponse(Call<LichMeetResponse> call, Response<LichMeetResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    new Thread(() -> {
                        List<LichMeetEntity> entities = new ArrayList<>();
                        for (LichMeetResponse.LichMeetDTO dto : response.body().getData()) {
                            entities.add(new LichMeetEntity(
                                    dto.getId(),
                                    dto.getIdLopHoc(),
                                    dto.getTenLop(),
                                    dto.getTieuDe(),
                                    dto.getLinkMeet(),
                                    dto.getThoiGian()
                            ));
                        }
                        lichMeetDao.deleteAll();
                        lichMeetDao.insertAll(entities);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<LichMeetResponse> call, Throwable t) {
                Log.e("HUY_DEBUG", "Lỗi đồng bộ lịch: " + t.getMessage());
            }
        });
    }

    @Override
    public LiveData<List<LichMeet>> getLichHocCuaToiLocal() {
        return Transformations.map(lichMeetDao.getAllLichHoc(), entities -> {
            List<LichMeet> models = new ArrayList<>();
            for (LichMeetEntity entity : entities) {
                // SỬA LẠI THỨ TỰ Ở ĐÂY:
                models.add(new LichMeet(
                        entity.id,           // 1. id
                        entity.tenLop,       // 2. tenLop
                        entity.tieuDe,       // 3. tieuDe (Cái này hiện đang bị nhầm thành thoiGian)
                        entity.linkMeet,     // 4. linkMeet
                        entity.thoiGian      // 5. thoiGian (Cái này hiện đang bị nhầm thành linkMeet)
                ));
            }
            return models;
        });
    }
}