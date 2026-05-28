package vn.huy.quanlydaotao.domain.repository;

import androidx.lifecycle.LiveData;
import vn.huy.quanlydaotao.data.remote.dto.XoaDangKyResponse;

public interface IRepositoryXoaDangKy {
    LiveData<XoaDangKyResponse> xoaDangKyRemote(int idNguoiDung, int idLopHoc);
}