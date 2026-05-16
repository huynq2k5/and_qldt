package vn.huy.quanlydaotao.ui.thongbao;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachThongBaoUseCase;

public class ThongBaoViewModelFactory implements ViewModelProvider.Factory {
    private final LayDanhSachThongBaoUseCase useCase;

    public ThongBaoViewModelFactory(LayDanhSachThongBaoUseCase useCase) {
        this.useCase = useCase;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ThongBaoViewModel.class)) {
            return (T) new ThongBaoViewModel(useCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}