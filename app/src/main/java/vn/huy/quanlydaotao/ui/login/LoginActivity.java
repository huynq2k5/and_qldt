package vn.huy.quanlydaotao.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.Executor;

import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.api.RetrofitClient;
import vn.huy.quanlydaotao.data.remote.dto.LoginResponse;
import vn.huy.quanlydaotao.data.repository.FCMRepositoryImpl;
import vn.huy.quanlydaotao.domain.repository.IFCMRepository;
import vn.huy.quanlydaotao.domain.usecase.UpdateFcmTokenUseCase;
import vn.huy.quanlydaotao.ui.main.MainActivity;
import vn.huy.quanlydaotao.data.local.TokenManager;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private TokenManager quanLyToken;
    private EditText edtTenDangNhap, edtMatKhau;
    private Button btnDangNhap, btnFingerprintLogin;
    private ProgressBar pbTai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        quanLyToken = new TokenManager(this);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        if (quanLyToken.tokenHopLe()) {
            if (quanLyToken.laVanTayDaBat() && kiemTraPhanCungVanTay()) {
                thucHienXacThucVanTay();
            } else {
                chuyenSangMain();
            }
            return;
        }

        initLoginUI();
    }

    private void initLoginUI() {
        setContentView(R.layout.activity_login);

        edtTenDangNhap = findViewById(R.id.edtUsername);
        edtMatKhau = findViewById(R.id.edtPassword);
        btnDangNhap = findViewById(R.id.btnLogin);
        btnFingerprintLogin = findViewById(R.id.btnFingerprintLogin);
        pbTai = findViewById(R.id.pbLoading);

        setupListeners();
        observeViewModel();
    }

    private void setupListeners() {
        btnDangNhap.setOnClickListener(v -> {
            String ten = edtTenDangNhap.getText().toString().trim();
            String mk = edtMatKhau.getText().toString().trim();

            if (ten.isEmpty() || mk.isEmpty()) {
                showStatusSnackBar("Vui lòng nhập đầy đủ thông tin", Color.parseColor("#B3261E"), Color.WHITE);
            } else {
                loginViewModel.dangNhap(ten, mk);
            }
        });

        if (btnFingerprintLogin != null) {
            btnFingerprintLogin.setOnClickListener(v -> handleFingerprintClick());
        }
    }

    private void observeViewModel() {
        loginViewModel.laDangXuLy().removeObservers(this);
        loginViewModel.layKetQuaDangNhap().removeObservers(this);
        loginViewModel.layThongBaoLoi().removeObservers(this);

        loginViewModel.laDangXuLy().observe(this, dangXuLy -> {
            if (dangXuLy != null) {
                pbTai.setVisibility(dangXuLy ? View.VISIBLE : View.GONE);
                btnDangNhap.setEnabled(!dangXuLy);
                btnDangNhap.setAlpha(dangXuLy ? 0.5f : 1.0f);
            }
        });

        loginViewModel.layKetQuaDangNhap().observe(this, phanHoi -> {
            if (phanHoi != null) {
                if (phanHoi.laThanhCong()) {
                    LoginResponse.DuLieuNguoiDung user = phanHoi.layDuLieu();
                    quanLyToken.luuThongTinDangNhap(
                            user.layToken(), user.layId(), user.layHoTen(),
                            user.layTenVaiTro(), user.layEmail(), user.layTokenMobile()
                    );

                    FirebaseMessaging.getInstance().getToken()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful() && task.getResult() != null) {
                                    String fcmToken = task.getResult();
                                    DichVuApi api = RetrofitClient.getClient().create(DichVuApi.class);
                                    IFCMRepository repo = new FCMRepositoryImpl(api);
                                    UpdateFcmTokenUseCase useCase = new UpdateFcmTokenUseCase(repo);
                                    useCase.execute(user.layId(), fcmToken);
                                    Log.d("FCM_TOKEN", "Token fcm tai login: " + fcmToken);
                                }
                                chuyenSangMain();
                            });
                } else {
                    showStatusSnackBar(phanHoi.layThongBao(), Color.parseColor("#B3261E"), Color.WHITE);
                }
            }
        });

        loginViewModel.layThongBaoLoi().observe(this, loi -> {
            if (loi != null)
                showStatusSnackBar(loi, Color.parseColor("#B3261E"), Color.WHITE);
        });
    }
    private void handleFingerprintClick() {
        if (!quanLyToken.tokenHopLe()) {
            showStatusSnackBar("Phiên đăng nhập hết hạn. Vui lòng đăng nhập bằng mật khẩu!",
                    Color.parseColor("#B3261E"), Color.WHITE);
            return;
        }

        if (!quanLyToken.laVanTayDaBat()) {
            showStatusSnackBar("Bạn chưa bật tính năng xác thực vân tay!",
                    Color.parseColor("#F59E0B"), Color.BLACK);
            return;
        }

        if (kiemTraPhanCungVanTay()) {
            thucHienXacThucVanTay();
        } else {
            showStatusSnackBar("Thiết bị không hỗ trợ hoặc chưa đăng ký vân tay!",
                    Color.parseColor("#B3261E"), Color.WHITE);
        }
    }
    private boolean kiemTraPhanCungVanTay() {
        BiometricManager biometricManager = BiometricManager.from(this);
        int canAuthenticate = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG);
        return canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS;
    }

    private void thucHienXacThucVanTay() {
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor,
                new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        // Khi nhấn "Đóng" hoặc lỗi, nạp lại UI và gán lại toàn bộ chức năng
                        initLoginUI();
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        chuyenSangMain();
                    }
                });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Xác thực vân tay")
                .setSubtitle("Vui lòng quét vân tay để tiếp tục")
                .setNegativeButtonText("Dùng mật khẩu")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }

    private void chuyenSangMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void showStatusSnackBar(String message, int backgroundColor, int textColor) {
        View rootView = findViewById(android.R.id.content);
        if (rootView == null) return;
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(backgroundColor);
        snackbar.setTextColor(textColor);
        snackbar.show();
    }
}