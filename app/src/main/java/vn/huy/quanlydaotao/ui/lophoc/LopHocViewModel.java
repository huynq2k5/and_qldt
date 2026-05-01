package vn.huy.quanlydaotao.ui.lophoc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.LopHoc;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachLopHocUseCase;

public class LopHocViewModel extends ViewModel {
    private final LayDanhSachLopHocUseCase useCase;

    public LopHocViewModel(LayDanhSachLopHocUseCase useCase) {
        this.useCase = useCase;
    }

    public LiveData<List<LopHoc>> layDanhSachLopHoc(int idKhoaHoc, int idNguoiDung) {
        return useCase.execute(idKhoaHoc, idNguoiDung);
    }

    public void taiLaiDuLieu(int idKhoaHoc, int idNguoiDung) {
        useCase.execute(idKhoaHoc, idNguoiDung);
    }
}