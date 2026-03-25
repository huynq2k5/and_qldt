package vn.huy.quanlydaotao.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.huy.quanlydaotao.data.remote.api.AuthApiService;
import vn.huy.quanlydaotao.data.remote.api.RetrofitClient;
import vn.huy.quanlydaotao.data.remote.dto.LoginRequest;
import vn.huy.quanlydaotao.data.remote.dto.LoginResponse;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginResponse> ketQuaDangNhap = new MutableLiveData<>();
    private MutableLiveData<String> thongBaoLoi = new MutableLiveData<>();
    private MutableLiveData<Boolean> dangXuLy = new MutableLiveData<>();

    public LiveData<LoginResponse> layKetQuaDangNhap() {
        return ketQuaDangNhap;
    }

    public LiveData<String> layThongBaoLoi() {
        return thongBaoLoi;
    }

    public LiveData<Boolean> laDangXuLy() {
        return dangXuLy;
    }

    public void dangNhap(String tenDangNhap, String matKhau) {
        dangXuLy.setValue(true);
        AuthApiService apiService = RetrofitClient.getClient().create(AuthApiService.class);
        LoginRequest yeuCau = new LoginRequest(tenDangNhap, matKhau);

        apiService.login(yeuCau).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                dangXuLy.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    ketQuaDangNhap.setValue(response.body());
                } else {
                    thongBaoLoi.setValue("Sai tài khoản hoặc mật khẩu");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                dangXuLy.setValue(false);
                thongBaoLoi.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}