package vn.huy.quanlydaotao.ui.hoctructuyen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.LichMeet;
import vn.huy.quanlydaotao.domain.usecase.LayLichMeetUseCase;

public class HocTrucTuyenViewModel extends ViewModel {

    private final LayLichMeetUseCase layLichMeetUseCase;

    public HocTrucTuyenViewModel(LayLichMeetUseCase layLichMeetUseCase) {
        this.layLichMeetUseCase = layLichMeetUseCase;
    }

    /**
     * Trả về danh sách lịch Meet của tất cả các lớp đã đăng ký
     */
    public LiveData<List<LichMeet>> getDanhSachLichMeet(int idNguoiDung) {
        return layLichMeetUseCase.execute(idNguoiDung);
    }

    /**
     * Ép buộc tải lại dữ liệu từ Server
     */
    public void lamMoiLichHoc(int idNguoiDung) {
        layLichMeetUseCase.refresh(idNguoiDung);
    }
}