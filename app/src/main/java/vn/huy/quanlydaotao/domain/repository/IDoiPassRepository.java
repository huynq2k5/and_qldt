package vn.huy.quanlydaotao.domain.repository;

import androidx.lifecycle.LiveData;
import vn.huy.quanlydaotao.data.remote.dto.DoiPassRequest;
import vn.huy.quanlydaotao.data.remote.dto.DoiPassResponse;

public interface IDoiPassRepository {
    LiveData<DoiPassResponse> doiMatKhau(DoiPassRequest request);
}