package vn.huy.quanlydaotao.ui.canhan;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import vn.huy.quanlydaotao.domain.usecase.DoiPassUseCase;

public class DoiPassViewModelFactory implements ViewModelProvider.Factory {
    private final DoiPassUseCase useCase;

    public DoiPassViewModelFactory(DoiPassUseCase useCase) {
        this.useCase = useCase;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DoiPassViewModel.class)) {
            return (T) new DoiPassViewModel(useCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}