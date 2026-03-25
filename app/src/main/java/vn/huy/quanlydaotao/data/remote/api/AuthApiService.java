package vn.huy.quanlydaotao.data.remote.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import vn.huy.quanlydaotao.data.remote.dto.LoginRequest;
import vn.huy.quanlydaotao.data.remote.dto.LoginResponse;

public interface AuthApiService {
    @POST("login.php")
    Call<LoginResponse> login(@Body LoginRequest request);
}