package vn.huy.quanlydaotao.domain.usecase;

import androidx.lifecycle.LiveData;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.ThongBao;
import vn.huy.quanlydaotao.domain.repository.IRepositoryThongBao;

public class LayDanhSachThongBaoUseCase {

    private final IRepositoryThongBao repository;

    public LayDanhSachThongBaoUseCase(IRepositoryThongBao repository) {
        this.repository = repository;
    }

    public LiveData<List<ThongBao>> execute(int idNguoiDung) {
        repository.dongBoThongBao(idNguoiDung);
        return repository.getDanhSachThongBaoLocal(idNguoiDung);
    }
}