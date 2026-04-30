package vn.huy.quanlydaotao.domain.repository;

import androidx.lifecycle.LiveData;
import vn.huy.quanlydaotao.domain.model.ChiTietKetQua;

public interface IRepositoryChiTietKetQua {
    LiveData<ChiTietKetQua> getChiTietKetQua(int idKetQua);
}