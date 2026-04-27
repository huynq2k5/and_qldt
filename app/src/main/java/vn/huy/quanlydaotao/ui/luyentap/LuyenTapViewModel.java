package vn.huy.quanlydaotao.ui.luyentap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vn.huy.quanlydaotao.domain.model.BaiKiemTra;
import vn.huy.quanlydaotao.domain.usecase.LayBaiKiemTraUseCase;

public class LuyenTapViewModel extends ViewModel {
    private final LayBaiKiemTraUseCase layBaiKiemTraUseCase;

    public LuyenTapViewModel(LayBaiKiemTraUseCase useCase) {
        this.layBaiKiemTraUseCase = useCase;
    }

    public LiveData<List<BaiKiemTra>> getDanhSachBaiKiemTra() {
        return layBaiKiemTraUseCase.execute();
    }

    public void lamMoiDuLieu(int idNguoiDung) {
        layBaiKiemTraUseCase.refresh(idNguoiDung);
    }
}