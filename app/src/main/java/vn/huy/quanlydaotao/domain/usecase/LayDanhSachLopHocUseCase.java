package vn.huy.quanlydaotao.domain.usecase;

import androidx.lifecycle.LiveData;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.LopHoc;
import vn.huy.quanlydaotao.domain.repository.ILopHocRepository;

public class LayDanhSachLopHocUseCase {

    private final ILopHocRepository repository;

    public LayDanhSachLopHocUseCase(ILopHocRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<LopHoc>> execute(int idKhoaHoc, int idNguoiDung) {
        repository.dongBoLopHoc(idKhoaHoc, idNguoiDung);
        return repository.getDanhSachLopHocLocal(idKhoaHoc);
    }
}