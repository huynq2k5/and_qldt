package vn.huy.quanlydaotao.domain.usecase;

import androidx.lifecycle.LiveData;
import vn.huy.quanlydaotao.data.remote.dto.KetQuaRequest;
import vn.huy.quanlydaotao.domain.model.KetQua;
import vn.huy.quanlydaotao.domain.repository.IRepositoryKetQua;

public class NopBaiUseCase {
    private final IRepositoryKetQua repository;

    public NopBaiUseCase(IRepositoryKetQua repository) {
        this.repository = repository;
    }

    public LiveData<KetQua> execute(KetQuaRequest request) {
        return repository.nopBai(request);
    }
}