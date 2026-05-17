package vn.huy.quanlydaotao.domain.usecase;

import androidx.lifecycle.LiveData;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.KhoaHoc;
import vn.huy.quanlydaotao.domain.repository.IKhoaHocRepository;

public class LayDanhSachKhoaHocUseCase {
    private final IKhoaHocRepository repository;

    public LayDanhSachKhoaHocUseCase(IKhoaHocRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<KhoaHoc>> execute() {
        return repository.getDanhSachKhoaHoc();
    }

    public void refresh() {
        repository.refreshKhoaHoc();
    }
}