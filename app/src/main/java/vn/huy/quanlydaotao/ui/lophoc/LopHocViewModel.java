package vn.huy.quanlydaotao.ui.lophoc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.LopHoc;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachLopHocUseCase;

public class LopHocViewModel extends ViewModel {
    private final LayDanhSachLopHocUseCase useCase;
    // Biến quản lý trạng thái
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);

    public LopHocViewModel(LayDanhSachLopHocUseCase useCase) {
        this.useCase = useCase;
    }

    public LiveData<Boolean> getIsLoading() { return isLoading; }

    public LiveData<List<LopHoc>> layDanhSachLopHoc(int idKhoaHoc) {
        return useCase.execute(idKhoaHoc);
    }

    public void taiLaiDuLieu(int idKhoaHoc) {
        isLoading.setValue(true); // Bắt đầu tải thì bật loading
        useCase.refresh(idKhoaHoc);
    }

    public void setFinishedLoading() {
        isLoading.setValue(false); // Gọi hàm này khi đã có dữ liệu thật
    }
}
