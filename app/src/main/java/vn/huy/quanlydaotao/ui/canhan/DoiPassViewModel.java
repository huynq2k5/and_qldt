package vn.huy.quanlydaotao.ui.canhan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import vn.huy.quanlydaotao.data.remote.dto.DoiPassResponse;
import vn.huy.quanlydaotao.domain.usecase.DoiPassUseCase;

public class DoiPassViewModel extends ViewModel {
    private final DoiPassUseCase doiPassUseCase;
    private final MutableLiveData<Boolean> isProcessing = new MutableLiveData<>(false);

    public DoiPassViewModel(DoiPassUseCase doiPassUseCase) {
        this.doiPassUseCase = doiPassUseCase;
    }

    public LiveData<DoiPassResponse> thucHienDoiPass(int id, String passCu, String passMoi) {
        isProcessing.setValue(true);
        return doiPassUseCase.thucHien(id, passCu, passMoi);
    }

    public LiveData<Boolean> getIsProcessing() {
        return isProcessing;
    }

    public void setProcessing(boolean processing) {
        isProcessing.setValue(processing);
    }
}