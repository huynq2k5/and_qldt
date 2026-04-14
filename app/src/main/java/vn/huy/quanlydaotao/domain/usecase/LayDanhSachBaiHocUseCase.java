package vn.huy.quanlydaotao.domain.usecase;

import androidx.lifecycle.LiveData;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.BaiHoc;
import vn.huy.quanlydaotao.domain.repository.IBaiHocRepository;

public class LayDanhSachBaiHocUseCase {
    private IBaiHocRepository repository;

    public LayDanhSachBaiHocUseCase(IBaiHocRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<BaiHoc>> execute(int idKhoaHoc) {
        return repository.getDanhSachBaiHoc(idKhoaHoc);
    }

    public void refresh(int idKhoaHoc) {
        repository.refreshBaiHoc(idKhoaHoc);
    }
}