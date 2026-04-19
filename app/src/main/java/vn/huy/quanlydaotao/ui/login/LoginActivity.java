package vn.huy.quanlydaotao.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;

import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.remote.dto.LoginResponse;
import vn.huy.quanlydaotao.ui.main.MainActivity;
import vn.huy.quanlydaotao.data.local.TokenManager;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private TokenManager quanLyToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        quanLyToken = new TokenManager(this);
        
        if (quanLyToken.tokenHopLe()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        } else {
            quanLyToken.xoaToken();
        }

        setContentView(R.layout.activity_login);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        EditText edtTenDangNhap = findViewById(R.id.edtUsername);
        EditText edtMatKhau = findViewById(R.id.edtPassword);
        Button btnDangNhap = findViewById(R.id.btnLogin);
        ProgressBar pbTai = findViewById(R.id.pbLoading);

        // Lắng nghe trạng thái đang xử lý để ẩn/hiện ProgressBar
        loginViewModel.laDangXuLy().observe(this, dangXuLy -> {
            if (dangXuLy != null) {
                if (dangXuLy) {
                    pbTai.setVisibility(View.VISIBLE);
                    btnDangNhap.setEnabled(false); // Vô hiệu hóa nút khi đang tải
                    btnDangNhap.setAlpha(0.5f);
                } else {
                    pbTai.setVisibility(View.GONE);
                    btnDangNhap.setEnabled(true);
                    btnDangNhap.setAlpha(1.0f);
                }
            }
        });

        loginViewModel.layKetQuaDangNhap().observe(this, phanHoi -> {
            if (phanHoi != null && phanHoi.laThanhCong()) {
                LoginResponse.DuLieuNguoiDung user = phanHoi.layDuLieu();

                quanLyToken.luuThongTinDangNhap(
                        user.layToken(),
                        user.layId(),
                        user.layHoTen(),
                        user.layTenVaiTro(),
                        user.layEmail(),
                        user.layTokenMobile()
                );

                Toast.makeText(this, "Chào mừng " + user.layHoTen(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });

        loginViewModel.layThongBaoLoi().observe(this, loi -> {
            if (loi != null) {
                Toast.makeText(this, loi, Toast.LENGTH_LONG).show();
            }
        });

        btnDangNhap.setOnClickListener(v -> {
            String ten = edtTenDangNhap.getText().toString().trim();
            String mk = edtMatKhau.getText().toString().trim();

            if (ten.isEmpty() || mk.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
            } else {
                loginViewModel.dangNhap(ten, mk);
            }
        });
    }
}