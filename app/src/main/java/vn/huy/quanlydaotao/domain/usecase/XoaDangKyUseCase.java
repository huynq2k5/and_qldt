package vn.huy.quanlydaotao.domain.usecase;

import androidx.lifecycle.LiveData;
import vn.huy.quanlydaotao.data.remote.dto.XoaDangKyResponse;
import vn.huy.quanlydaotao.domain.repository.IRepositoryXoaDangKy;

public class XoaDangKyUseCase {
    private final IRepositoryXoaDangKy repository;

    public XoaDangKyUseCase(IRepositoryXoaDangKy repository) {
        this.repository = repository;
    }

    public LiveData<XoaDangKyResponse> execute(int idNguoiDung, int idLopHoc) {
        return repository.xoaDangKyRemote(idNguoiDung, idLopHoc);
    }
}