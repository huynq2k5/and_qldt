package vn.huy.quanlydaotao.domain.usecase;

import androidx.lifecycle.LiveData;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.CauHoi;
import vn.huy.quanlydaotao.domain.repository.IRepositoryCauHoi;

public class LayCauHoiUseCase {
    private final IRepositoryCauHoi repository;

    public LayCauHoiUseCase(IRepositoryCauHoi repository) {
        this.repository = repository;
    }

    public LiveData<List<CauHoi>> execute(int idBaiKiemTra) {
        return repository.getDanhSachCauHoi(idBaiKiemTra);
    }

    public void refresh(int idNguoiDung, int idBaiKiemTra) {
        repository.refreshCauHoi(idNguoiDung, idBaiKiemTra);
    }
}