package vn.huy.quanlydaotao.ui.thongbao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

import vn.huy.quanlydaotao.data.remote.dto.DaDocResponse;
import vn.huy.quanlydaotao.domain.model.ThongBao;
import vn.huy.quanlydaotao.domain.usecase.DanhDauDaDocUseCase;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachThongBaoUseCase;

public class ThongBaoViewModel extends ViewModel {
    private final LayDanhSachThongBaoUseCase useCase;
    private final DanhDauDaDocUseCase danhDauDaDocUseCase;

    public ThongBaoViewModel(LayDanhSachThongBaoUseCase useCase, DanhDauDaDocUseCase danhDauDaDocUseCase) {
        this.useCase = useCase;
        this.danhDauDaDocUseCase = danhDauDaDocUseCase;
    }

    public LiveData<List<ThongBao>> layDanhSachThongBao(int idNguoiDung) {
        return useCase.execute(idNguoiDung);
    }

    public LiveData<DaDocResponse> danhDauDaDoc(int id) {
        return danhDauDaDocUseCase.execute(id);
    }

    public void taiLaiDuLieu(int idNguoiDung) {
        useCase.execute(idNguoiDung);
    }
}