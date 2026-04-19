package vn.huy.quanlydaotao.ui.hoctructuyen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vn.huy.quanlydaotao.domain.model.LichMeet;
import vn.huy.quanlydaotao.domain.model.LopHoc;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachLichMeetUseCase;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachLopHocUseCase;

public class HocTrucTuyenViewModel extends ViewModel {

    private final LayDanhSachLichMeetUseCase lichMeetUseCase;
    private final LayDanhSachLopHocUseCase lopHocUseCase;

    public HocTrucTuyenViewModel(LayDanhSachLichMeetUseCase lichMeetUseCase,
                                 LayDanhSachLopHocUseCase lopHocUseCase) {
        this.lichMeetUseCase = lichMeetUseCase;
        this.lopHocUseCase = lopHocUseCase;
    }

    public LiveData<List<LichMeet>> getDanhSachLichMeet(int idLopHoc) {
        return lichMeetUseCase.execute(idLopHoc);
    }

    public LiveData<List<LopHoc>> getDanhSachLopHoc(int idKhoaHoc) {
        return lopHocUseCase.execute(idKhoaHoc);
    }

    public void taiLaiDuLieu(int idLopHoc) {
        lichMeetUseCase.refresh(idLopHoc);
    }

    public void lamMoiLopHoc(int idKhoaHoc) {
        lopHocUseCase.refresh(idKhoaHoc);
    }
}