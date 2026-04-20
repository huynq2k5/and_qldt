package vn.huy.quanlydaotao.domain.repository;

import androidx.lifecycle.LiveData;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.LopHoc;

public interface ILopHocRepository {
    void dongBoLopHoc(int idKhoaHoc, int idNguoiDung);
    LiveData<List<LopHoc>> getDanhSachLopHocLocal(int idKhoaHoc);
}