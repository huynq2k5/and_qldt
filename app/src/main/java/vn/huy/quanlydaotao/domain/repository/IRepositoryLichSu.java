package vn.huy.quanlydaotao.domain.repository;

import androidx.lifecycle.LiveData;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.LichSuKT;

public interface IRepositoryLichSu {
    void dongBoLichSuKiMTra(int idNguoiDung);
    LiveData<List<LichSuKT>> getLichSuKiMTraLocal(int idNguoiDung);
}