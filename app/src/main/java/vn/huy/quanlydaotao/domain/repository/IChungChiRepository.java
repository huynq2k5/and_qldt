package vn.huy.quanlydaotao.domain.repository;

import androidx.lifecycle.LiveData;
import java.util.List;
import vn.huy.quanlydaotao.domain.model.ChungChi;

public interface IChungChiRepository {
    LiveData<List<ChungChi>> getDanhSachChungChi();
    void refreshChungChi(int idNguoiDung);
}