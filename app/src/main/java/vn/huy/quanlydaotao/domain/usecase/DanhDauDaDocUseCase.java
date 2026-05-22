package vn.huy.quanlydaotao.domain.usecase;

import androidx.lifecycle.LiveData;
import vn.huy.quanlydaotao.data.remote.dto.DaDocResponse;
import vn.huy.quanlydaotao.domain.repository.IRepositoryDaDoc;

public class DanhDauDaDocUseCase {
    private final IRepositoryDaDoc repository;

    public DanhDauDaDocUseCase(IRepositoryDaDoc repository) {
        this.repository = repository;
    }

    public LiveData<DaDocResponse> execute(int id) {
        return repository.danhDauDaDoc(id);
    }
}