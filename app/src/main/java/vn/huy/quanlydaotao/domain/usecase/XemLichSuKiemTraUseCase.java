package vn.huy.quanlydaotao.domain.usecase;

import androidx.lifecycle.LiveData;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.LichSuKT;
import vn.huy.quanlydaotao.domain.repository.IRepositoryLichSu;

public class XemLichSuKiemTraUseCase {
    private final IRepositoryLichSu repository;

    public XemLichSuKiemTraUseCase(IRepositoryLichSu repository) {
        this.repository = repository;
    }

    public LiveData<List<LichSuKT>> execute(int idNguoiDung) {
        repository.dongBoLichSuKiMTra(idNguoiDung);
        return repository.getLichSuKiMTraLocal(idNguoiDung);
    }
}