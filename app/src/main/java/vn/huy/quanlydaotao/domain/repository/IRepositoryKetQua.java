package vn.huy.quanlydaotao.domain.repository;

import androidx.lifecycle.LiveData;

import vn.huy.quanlydaotao.data.remote.dto.KetQuaRequest;
import vn.huy.quanlydaotao.domain.model.KetQua;

public interface IRepositoryKetQua {
    LiveData<KetQua> nopBai(KetQuaRequest request);
}
