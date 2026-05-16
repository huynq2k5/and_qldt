package vn.huy.quanlydaotao.domain.repository;

import androidx.lifecycle.LiveData;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.ThongBao;

public interface IRepositoryThongBao {
    void dongBoThongBao(int idNguoiDung);
    LiveData<List<ThongBao>> getDanhSachThongBaoLocal(int idNguoiDung);
}