package vn.huy.quanlydaotao.domain.usecase;

import androidx.lifecycle.LiveData;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.ChungChi;
import vn.huy.quanlydaotao.domain.repository.IChungChiRepository;

public class LayDanhSachChungChiUseCase {
    private final IChungChiRepository repository;

    public LayDanhSachChungChiUseCase(IChungChiRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<ChungChi>> execute() {
        return repository.getDanhSachChungChi();
    }

    public void refresh(int idNguoiDung) {
        repository.refreshChungChi(idNguoiDung);
    }
}