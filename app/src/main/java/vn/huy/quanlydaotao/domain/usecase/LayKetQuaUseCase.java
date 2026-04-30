package vn.huy.quanlydaotao.domain.usecase;

import androidx.lifecycle.LiveData;
import vn.huy.quanlydaotao.domain.model.ChiTietKetQua;
import vn.huy.quanlydaotao.domain.repository.IRepositoryChiTietKetQua;
import vn.huy.quanlydaotao.data.repository.ChiTietKetQuaRepositoryImpl;

public class LayKetQuaUseCase {
    private final IRepositoryChiTietKetQua repository;

    public LayKetQuaUseCase(IRepositoryChiTietKetQua repository) {
        this.repository = repository;
    }

    public LiveData<ChiTietKetQua> execute(int idKetQua) {
        if (repository instanceof ChiTietKetQuaRepositoryImpl) {
            ((ChiTietKetQuaRepositoryImpl) repository).loadChiTietFromServer(idKetQua);
        }
        return repository.getChiTietKetQua(idKetQua);
    }
}