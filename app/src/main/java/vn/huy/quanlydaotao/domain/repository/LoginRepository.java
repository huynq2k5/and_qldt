package vn.huy.quanlydaotao.domain.repository;

import androidx.lifecycle.LiveData;
import vn.huy.quanlydaotao.data.remote.dto.LoginResponse;

public interface LoginRepository {
    LiveData<LoginResponse> login(String username, String password);
}