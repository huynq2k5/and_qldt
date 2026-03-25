package vn.huy.quanlydaotao.domain.repository;

import androidx.lifecycle.LiveData;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.KhoaHoc;

public interface IKhoaHocRepository {
    LiveData<List<KhoaHoc>> getDanhSachKhoaHoc();
    void refreshKhoaHoc();
}
