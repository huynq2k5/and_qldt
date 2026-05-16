package vn.huy.quanlydaotao.domain.repository;

import androidx.lifecycle.LiveData;
import vn.huy.quanlydaotao.data.remote.dto.TienDoResponse;

public interface ITienDoRepository {
    LiveData<TienDoResponse> luuTienDoRemote(int idNguoiDung, int idBaiHoc, int phanTram);
}