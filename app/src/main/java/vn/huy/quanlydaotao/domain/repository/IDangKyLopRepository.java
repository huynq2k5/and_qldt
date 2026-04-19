package vn.huy.quanlydaotao.domain.repository;

import androidx.lifecycle.LiveData;
import vn.huy.quanlydaotao.data.remote.dto.DangKyLopResponse;

public interface IDangKyLopRepository {
    LiveData<DangKyLopResponse> dangKyLopRemote(int idNguoiDung, int idLopHoc);
    boolean kiemTraDaDangKyLocal(int idNguoiDung, int idLopHoc);
}