package vn.huy.quanlydaotao.domain.repository;

import androidx.lifecycle.LiveData;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.BaiHoc;

public interface IBaiHocRepository {
    LiveData<List<BaiHoc>> getDanhSachBaiHoc(int idKhoaHoc);
    void refreshBaiHoc(int idKhoaHoc);
}