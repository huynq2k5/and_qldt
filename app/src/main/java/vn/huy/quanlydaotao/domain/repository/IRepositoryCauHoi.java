package vn.huy.quanlydaotao.domain.repository;

import androidx.lifecycle.LiveData;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.CauHoi;

public interface IRepositoryCauHoi {
    LiveData<List<CauHoi>> getDanhSachCauHoi(int idBaiKiemTra);
    void refreshCauHoi(int idNguoiDung, int idBaiKiemTra);
}