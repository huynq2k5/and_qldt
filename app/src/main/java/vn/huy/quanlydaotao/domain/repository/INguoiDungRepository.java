package vn.huy.quanlydaotao.domain.repository;

import androidx.lifecycle.LiveData;
import vn.huy.quanlydaotao.data.remote.dto.NguoiDungResponse;

public interface INguoiDungRepository {
    LiveData<NguoiDungResponse> suaThongTin(int id, String hoTen, String email);
}