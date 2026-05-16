package vn.huy.quanlydaotao.ui.thongbao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.ThongBao;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachThongBaoUseCase;

public class ThongBaoViewModel extends ViewModel {
    private final LayDanhSachThongBaoUseCase useCase;

    public ThongBaoViewModel(LayDanhSachThongBaoUseCase useCase) {
        this.useCase = useCase;
    }

    public LiveData<List<ThongBao>> layDanhSachThongBao(int idNguoiDung) {
        return useCase.execute(idNguoiDung);
    }

    public void taiLaiDuLieu(int idNguoiDung) {
        useCase.execute(idNguoiDung);
    }
}