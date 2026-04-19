package vn.huy.quanlydaotao.ui.lophoc.dangky;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import vn.huy.quanlydaotao.data.remote.dto.DangKyLopResponse;
import vn.huy.quanlydaotao.domain.usecase.DangKyLopUseCase;

public class DangKyLopViewModel extends ViewModel {

    private final DangKyLopUseCase dangKyLopUseCase;
    private final MutableLiveData<Boolean> isProcessing = new MutableLiveData<>(false);

    public DangKyLopViewModel(DangKyLopUseCase dangKyLopUseCase) {
        this.dangKyLopUseCase = dangKyLopUseCase;
    }

    public LiveData<DangKyLopResponse> thucHienDangKy(int idNguoiDung, int idLopHoc) {
        isProcessing.setValue(true);
        return dangKyLopUseCase.thucHienDangKy(idNguoiDung, idLopHoc);
    }

    public boolean kiemTraTrangThai(int idNguoiDung, int idLopHoc) {
        return dangKyLopUseCase.daDangKy(idNguoiDung, idLopHoc);
    }

    public LiveData<Boolean> getIsProcessing() {
        return isProcessing;
    }

    public void setProcessing(boolean processing) {
        isProcessing.setValue(processing);
    }
}