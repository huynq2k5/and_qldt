package vn.huy.quanlydaotao.ui.ketqua;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.local.CoSoDuLieuApp;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.api.RetrofitClient;
import vn.huy.quanlydaotao.data.repository.ChiTietKetQuaRepositoryImpl;
import vn.huy.quanlydaotao.domain.usecase.LayKetQuaUseCase;

public class ChiTietKetQuaActivity extends AppCompatActivity {
    private RecyclerView rvChiTiet;
    private ChiTietKetQuaAdapter adapter;
    private KetQuaViewModel viewModel;
    private int idKetQua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1. Kích hoạt Edge-to-Edge
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chi_tiet_ket_qua);

        // 2. Thiết lập WindowInsetsController để icon status bar luôn màu trắng
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController != null) {
            windowInsetsController.setAppearanceLightStatusBars(false);
        }

        // 3. Áp dụng logic Edge-to-Edge cho root view
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            setupEdgeToEdge(mainView);
        }

        idKetQua = getIntent().getIntExtra("ID_KET_QUA", 0);
        initViews();
        setupViewModel();
        observeData();
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

            // Đặt padding dưới cùng cho root view để tránh thanh điều hướng hệ thống
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);

            return WindowInsetsCompat.CONSUMED;
        });
    }

    private void initViews() {
        // Sử dụng ImageButton quay lại thay vì Toolbar mặc định
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        rvChiTiet = findViewById(R.id.rvChiTietCauHoi);
        rvChiTiet.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChiTietKetQuaAdapter();
        rvChiTiet.setAdapter(adapter);
    }

    private void setupViewModel() {
        DichVuApi api = RetrofitClient.getClient().create(DichVuApi.class);
        CoSoDuLieuApp db = CoSoDuLieuApp.getInstance(this);
        ChiTietKetQuaRepositoryImpl repo = new ChiTietKetQuaRepositoryImpl(db.chiTietKetQuaDao(), api);
        LayKetQuaUseCase useCase = new LayKetQuaUseCase(repo);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new KetQuaViewModel(useCase);
            }
        }).get(KetQuaViewModel.class);
    }

    private void observeData() {
        viewModel.xemKetQua(idKetQua).observe(this, data -> {
            if (data != null && data.getDanhSachChiTiet() != null) {
                adapter.setData(data.getDanhSachChiTiet());
            }
        });
    }
}