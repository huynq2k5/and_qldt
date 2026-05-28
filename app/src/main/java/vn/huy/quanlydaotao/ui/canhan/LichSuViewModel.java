package vn.huy.quanlydaotao.ui.canhan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.LichSuKT;
import vn.huy.quanlydaotao.domain.usecase.XemLichSuKiemTraUseCase;

public class LichSuViewModel extends ViewModel {
    private final XemLichSuKiemTraUseCase useCase;

    public LichSuViewModel(XemLichSuKiemTraUseCase useCase) {
        this.useCase = useCase;
    }

    public LiveData<List<LichSuKT>> xemLichSu(int idNguoiDung) {
        return useCase.execute(idNguoiDung);
    }

    public void taiLaiDuLieu(int idNguoiDung) {
        useCase.execute(idNguoiDung);
    }
}