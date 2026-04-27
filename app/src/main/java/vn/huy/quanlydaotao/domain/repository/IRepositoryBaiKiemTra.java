package vn.huy.quanlydaotao.domain.repository;

import androidx.lifecycle.LiveData;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.BaiKiemTra;

public interface IRepositoryBaiKiemTra {
    LiveData<List<BaiKiemTra>> getDanhSachBaiKiemTra();
    void refreshBaiKiemTra(int idNguoiDung);
}