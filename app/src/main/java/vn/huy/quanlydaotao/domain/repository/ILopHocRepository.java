package vn.huy.quanlydaotao.domain.repository;

import androidx.lifecycle.LiveData;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.LopHoc;

public interface ILopHocRepository {
    LiveData<List<LopHoc>> layDanhSachLopHoc(int idKhoaHoc);
    void lamMoiLopHoc(int idKhoaHoc);
}
