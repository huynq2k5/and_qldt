package vn.huy.quanlydaotao.ui.canhan;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

import vn.huy.quanlydaotao.R;

public class EditProfileActivity extends AppCompatActivity {

    private EditText edtName, edtEmail;
    private ImageButton btnBack;
    private MaterialButton btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initViews();
        setupEvents();
    }

    private void initViews() {
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);

        // Nạp dữ liệu giả định ban đầu
        edtName.setText("Nguyễn Quang Huy");
        edtEmail.setText("huy.nguyen@vlute.edu.vn");
    }

    private void setupEvents() {
        // Sự kiện nút quay lại
        btnBack.setOnClickListener(v -> finish());

        // Sự kiện lưu thông tin
        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Vui lòng không để trống thông tin", Toast.LENGTH_SHORT).show();
            } else {
                // Xử lý logic cập nhật database tại đây
                Toast.makeText(this, "Đã cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}