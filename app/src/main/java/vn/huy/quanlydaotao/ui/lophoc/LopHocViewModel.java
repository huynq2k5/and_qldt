package vn.huy.quanlydaotao.ui.lophoc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.LopHoc;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachLopHocUseCase;

public class LopHocViewModel extends ViewModel {
    private final LayDanhSachLopHocUseCase layDanhSachLopHocUseCase;

    public LopHocViewModel(LayDanhSachLopHocUseCase layDanhSachLopHocUseCase) {
        this.layDanhSachLopHocUseCase = layDanhSachLopHocUseCase;
    }

    public LiveData<List<LopHoc>> layDanhSachLopHoc(int idKhoaHoc) {
        return layDanhSachLopHocUseCase.execute(idKhoaHoc);
    }

    public void taiLaiDuLieu(int idKhoaHoc) {
        layDanhSachLopHocUseCase.refresh(idKhoaHoc);
    }
}
