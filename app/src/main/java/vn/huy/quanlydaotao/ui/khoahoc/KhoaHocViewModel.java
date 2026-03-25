package vn.huy.quanlydaotao.ui.khoahoc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.KhoaHoc;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachKhoaHocUseCase;

public class KhoaHocViewModel extends ViewModel {
    private final LayDanhSachKhoaHocUseCase layDanhSachKhoaHocUseCase;
    private final LiveData<List<KhoaHoc>> danhSachKhoaHoc;

    public KhoaHocViewModel(LayDanhSachKhoaHocUseCase layDanhSachKhoaHocUseCase) {
        this.layDanhSachKhoaHocUseCase = layDanhSachKhoaHocUseCase;
        this.danhSachKhoaHoc = layDanhSachKhoaHocUseCase.execute();
    }

    public LiveData<List<KhoaHoc>> getDanhSachKhoaHoc() {
        return danhSachKhoaHoc;
    }

    public void taiLaiDuLieu() {
        layDanhSachKhoaHocUseCase.refresh();
    }
}
