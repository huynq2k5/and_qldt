package vn.huy.quanlydaotao.ui.lophoc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.LopHoc;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachLopHocUseCase;

public class LopHocViewModel extends ViewModel {
    private final LayDanhSachLopHocUseCase useCase;
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);

    public LopHocViewModel(LayDanhSachLopHocUseCase useCase) {
        this.useCase = useCase;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<List<LopHoc>> layDanhSachLopHoc(int idKhoaHoc, int idNguoiDung) {
        return useCase.execute(idKhoaHoc, idNguoiDung);
    }

    public void taiLaiDuLieu(int idKhoaHoc, int idNguoiDung) {
        isLoading.setValue(true);
        useCase.execute(idKhoaHoc, idNguoiDung);
    }

    public void setFinishedLoading() {
        isLoading.setValue(false);
    }
}