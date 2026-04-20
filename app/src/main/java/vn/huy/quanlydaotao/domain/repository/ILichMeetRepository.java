package vn.huy.quanlydaotao.domain.repository;

import androidx.lifecycle.LiveData;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.LichMeet;

public interface ILichMeetRepository {
    // Lệnh yêu cầu đồng bộ lịch từ Server về Room
    void dongBoLichHoc(int idNguoiDung);

    // Lấy dữ liệu từ Room để hiển thị lên UI (LiveData)
    LiveData<List<LichMeet>> getLichHocCuaToiLocal();
}