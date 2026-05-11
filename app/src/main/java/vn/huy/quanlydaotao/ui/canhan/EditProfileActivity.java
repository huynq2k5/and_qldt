package vn.huy.quanlydaotao.ui.canhan;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.local.TokenManager;
import vn.huy.quanlydaotao.data.repository.NguoiDungRepositoryImpl;
import vn.huy.quanlydaotao.data.remote.api.RetrofitClient;
import vn.huy.quanlydaotao.domain.usecase.SuaThongTinUserUseCase;

public class EditProfileActivity extends AppCompatActivity {

    private EditText edtName, edtEmail;
    private ImageButton btnBack;
    private MaterialButton btnSave;
    private EditProfileViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        setupViewModel();
        initViews();
        setupEvents();
        observeViewModel();
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            setupEdgeToEdge(mainView);
        }
    }
    private void setupViewModel() {
        TokenManager tokenManager = new TokenManager(this);
        vn.huy.quanlydaotao.data.remote.api.DichVuApi api = RetrofitClient.getClient().create(vn.huy.quanlydaotao.data.remote.api.DichVuApi.class);
        vn.huy.quanlydaotao.domain.repository.INguoiDungRepository repo = new NguoiDungRepositoryImpl(api);
        SuaThongTinUserUseCase useCase = new SuaThongTinUserUseCase(repo);

        viewModel = new EditProfileViewModel(useCase, tokenManager);
    }
    private void initViews() {
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);

        edtName.setText(viewModel.getCurrentName());
        edtEmail.setText(viewModel.getCurrentEmail());
    }

    private void setupEvents() {
        btnBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty()) {
                Snackbar.make(v, "Vui lòng không để trống thông tin", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(Color.parseColor("#B3261E"))
                        .setTextColor(Color.WHITE)
                        .show();
                return;
            }

            viewModel.updateProfile(name, email).observe(this, response -> {
                if (response != null && response.isSuccess()) {
                    viewModel.saveLocal(name, email);

                    Snackbar.make(v, response.getMessage(), Snackbar.LENGTH_SHORT)
                            .addCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar transientBottomBar, int event) {
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            })
                            .setBackgroundTint(Color.parseColor("#10B981"))
                            .setTextColor(Color.WHITE)
                            .show();
                } else {
                    String msg = (response != null) ? response.getMessage() : "Lỗi kết nối server";
                    Snackbar.make(v, msg, Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.parseColor("#B3261E"))
                            .setTextColor(Color.WHITE)
                            .show();
                }
            });
        });
    }

    private void observeViewModel() {
        viewModel.getLoading().observe(this, isLoading -> {
            btnSave.setEnabled(!isLoading);
            btnSave.setText(isLoading ? "Đang lưu..." : "Lưu thay đổi");
        });
    }
    private void setupEdgeToEdge(View view) {
        View header = view.findViewById(R.id.layoutHeader);
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());

            if (header != null) {
                header.setPadding(
                        header.getPaddingLeft(),
                        systemBars.top + 20, // Cộng thêm 20px để khoảng cách thoáng hơn
                        header.getPaddingRight(),
                        header.getPaddingBottom()
                );
            }

            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);

            return WindowInsetsCompat.CONSUMED;
        });
    }
}