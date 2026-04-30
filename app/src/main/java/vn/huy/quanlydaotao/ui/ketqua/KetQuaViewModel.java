package vn.huy.quanlydaotao.ui.ketqua;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import vn.huy.quanlydaotao.domain.model.ChiTietKetQua;
import vn.huy.quanlydaotao.domain.usecase.LayKetQuaUseCase;

public class KetQuaViewModel extends ViewModel {
    private final LayKetQuaUseCase layKetQuaUseCase;

    public KetQuaViewModel(LayKetQuaUseCase layKetQuaUseCase) {
        this.layKetQuaUseCase = layKetQuaUseCase;
    }

    public LiveData<ChiTietKetQua> xemKetQua(int idKetQua) {
        return layKetQuaUseCase.execute(idKetQua);
    }
}