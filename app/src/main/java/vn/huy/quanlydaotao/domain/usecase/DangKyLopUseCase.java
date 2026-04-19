package vn.huy.quanlydaotao.domain.usecase;

import androidx.lifecycle.LiveData;
import vn.huy.quanlydaotao.data.remote.dto.DangKyLopResponse;
import vn.huy.quanlydaotao.domain.repository.IDangKyLopRepository;

public class DangKyLopUseCase {

    private final IDangKyLopRepository repository;

    public DangKyLopUseCase(IDangKyLopRepository repository) {
        this.repository = repository;
    }

    public LiveData<DangKyLopResponse> thucHienDangKy(int idNguoiDung, int idLopHoc) {
        return repository.dangKyLopRemote(idNguoiDung, idLopHoc);
    }

    public boolean daDangKy(int idNguoiDung, int idLopHoc) {
        return repository.kiemTraDaDangKyLocal(idNguoiDung, idLopHoc);
    }
}