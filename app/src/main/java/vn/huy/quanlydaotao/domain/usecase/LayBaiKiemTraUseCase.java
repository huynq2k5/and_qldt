package vn.huy.quanlydaotao.domain.usecase;

import androidx.lifecycle.LiveData;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.BaiKiemTra;
import vn.huy.quanlydaotao.domain.repository.IRepositoryBaiKiemTra;

public class LayBaiKiemTraUseCase {
    private final IRepositoryBaiKiemTra repository;

    public LayBaiKiemTraUseCase(IRepositoryBaiKiemTra repository) {
        this.repository = repository;
    }

    public LiveData<List<BaiKiemTra>> execute() {
        return repository.getDanhSachBaiKiemTra();
    }

    public void refresh(int idNguoiDung) {
        repository.refreshBaiKiemTra(idNguoiDung);
    }
}