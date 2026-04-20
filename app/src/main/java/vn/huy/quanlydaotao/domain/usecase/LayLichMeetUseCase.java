package vn.huy.quanlydaotao.domain.usecase;

import androidx.lifecycle.LiveData;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.LichMeet;
import vn.huy.quanlydaotao.domain.repository.ILichMeetRepository;

public class LayLichMeetUseCase {

    private final ILichMeetRepository repository;

    public LayLichMeetUseCase(ILichMeetRepository repository) {
        this.repository = repository;
    }

    /**
     * Lấy lịch học và tự động ra lệnh đồng bộ từ Server
     */
    public LiveData<List<LichMeet>> execute(int idNguoiDung) {
        // Mỗi lần màn hình yêu cầu dữ liệu, ta kích hoạt đồng bộ ngầm
        repository.dongBoLichHoc(idNguoiDung);

        // Trả về LiveData quan sát từ Room (Single Source of Truth)
        return repository.getLichHocCuaToiLocal();
    }

    /**
     * Hàm dùng khi người dùng thực hiện thao tác Vuốt để làm mới (SwipeRefresh)
     */
    public void refresh(int idNguoiDung) {
        repository.dongBoLichHoc(idNguoiDung);
    }
}