package vn.huy.quanlydaotao.ui.thongbao;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import vn.huy.quanlydaotao.domain.usecase.DanhDauDaDocUseCase;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachThongBaoUseCase;

public class ThongBaoViewModelFactory implements ViewModelProvider.Factory {
    private final LayDanhSachThongBaoUseCase useCase;
    private final DanhDauDaDocUseCase danhDauDaDocUseCase;


    public ThongBaoViewModelFactory(LayDanhSachThongBaoUseCase useCase, DanhDauDaDocUseCase danhDauDaDocUseCase) {
        this.useCase = useCase;
        this.danhDauDaDocUseCase = danhDauDaDocUseCase;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ThongBaoViewModel.class)) {
            return (T) new ThongBaoViewModel(useCase, danhDauDaDocUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}