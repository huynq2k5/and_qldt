package vn.huy.quanlydaotao.ui.canhan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.ChungChi;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachChungChiUseCase;

public class ChungChiViewModel extends ViewModel {
    private final LayDanhSachChungChiUseCase useCase;

    public ChungChiViewModel(LayDanhSachChungChiUseCase useCase) {
        this.useCase = useCase;
    }

    public LiveData<List<ChungChi>> getDanhSachChungChi() {
        return useCase.execute();
    }

    public void taiLaiDuLieu(int idNguoiDung) {
        useCase.refresh(idNguoiDung);
    }
}