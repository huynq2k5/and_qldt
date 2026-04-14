package vn.huy.quanlydaotao.ui.baihoc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.BaiHoc;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachBaiHocUseCase;

public class BaiHocViewModel extends ViewModel {
    private LayDanhSachBaiHocUseCase useCase;

    public BaiHocViewModel(LayDanhSachBaiHocUseCase useCase) {
        this.useCase = useCase;
    }

    public LiveData<List<BaiHoc>> getDanhSachBaiHoc(int idKhoaHoc) {
        return useCase.execute(idKhoaHoc);
    }

    public void taiLaiDuLieu(int idKhoaHoc) {
        useCase.refresh(idKhoaHoc);
    }
}