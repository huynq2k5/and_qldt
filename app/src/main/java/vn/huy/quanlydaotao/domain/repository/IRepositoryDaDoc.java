package vn.huy.quanlydaotao.domain.repository;

import androidx.lifecycle.LiveData;
import vn.huy.quanlydaotao.data.remote.dto.DaDocResponse;

public interface IRepositoryDaDoc {
    LiveData<DaDocResponse> danhDauDaDoc(int id);
}