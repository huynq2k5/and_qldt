package vn.huy.quanlydaotao.ui.lophoc.dangky;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import vn.huy.quanlydaotao.domain.usecase.DangKyLopUseCase;

public class DangKyLopViewModelFactory implements ViewModelProvider.Factory {
    private final DangKyLopUseCase useCase;

    public DangKyLopViewModelFactory(DangKyLopUseCase useCase) {
        this.useCase = useCase;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DangKyLopViewModel.class)) {
            return (T) new DangKyLopViewModel(useCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}