package vn.huy.quanlydaotao.ui.thongbao;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.local.CoSoDuLieuApp;
import vn.huy.quanlydaotao.data.local.TokenManager;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.api.RetrofitClient;
import vn.huy.quanlydaotao.data.repository.ThongBaoRepositoryImpl;
import vn.huy.quanlydaotao.domain.model.ThongBao;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachThongBaoUseCase;

public class ThongBaoActivity extends AppCompatActivity {
    private RecyclerView rvThongBao;
    private ImageButton btnBack;
    private LinearLayout layoutEmpty;
    private ThongBaoViewModel viewModel;
    private TokenManager tokenManager;
    private ThongBaoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_bao);

        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController != null) {
            windowInsetsController.setAppearanceLightStatusBars(false);
        }

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            setupEdgeToEdge(mainView);
        }

        initViews();
        setupViewModel();
        setupRecyclerView();
        observeViewModel();

        btnBack.setOnClickListener(v -> finish());
    }

    private void initViews() {
        rvThongBao = findViewById(R.id.rvThongBao);
        btnBack = findViewById(R.id.btnBack);
        layoutEmpty = findViewById(R.id.layoutEmpty);
    }

    private void setupViewModel() {
        tokenManager = new TokenManager(this);
        DichVuApi api = RetrofitClient.getClient().create(DichVuApi.class);
        CoSoDuLieuApp db = CoSoDuLieuApp.getInstance(this);
        ThongBaoRepositoryImpl repo = new ThongBaoRepositoryImpl(db.thongBaoDao(), api);
        LayDanhSachThongBaoUseCase useCase = new LayDanhSachThongBaoUseCase(repo);
        ThongBaoViewModelFactory factory = new ThongBaoViewModelFactory(useCase);
        viewModel = new ViewModelProvider(this, factory).get(ThongBaoViewModel.class);
    }

    private void setupRecyclerView() {
        adapter = new ThongBaoAdapter();
        rvThongBao.setLayoutManager(new LinearLayoutManager(this));
        rvThongBao.setAdapter(adapter);

        adapter.setOnThongBaoClickListener(thongBao -> {
            
        });
    }

    private void observeViewModel() {
        int idNguoiDung = tokenManager.layId();
        viewModel.layDanhSachThongBao(idNguoiDung).observe(this, this::hienThiGiaoDien);
    }

    private void hienThiGiaoDien(List<ThongBao> danhSach) {
        if (danhSach == null || danhSach.isEmpty()) {
            rvThongBao.setVisibility(View.GONE);
            if (layoutEmpty != null) {
                layoutEmpty.setVisibility(View.VISIBLE);
            }
        } else {
            if (layoutEmpty != null) {
                layoutEmpty.setVisibility(View.GONE);
            }
            rvThongBao.setVisibility(View.VISIBLE);
            adapter.setItems(danhSach);
        }
    }

    private void setupEdgeToEdge(View view) {
        View header = view.findViewById(R.id.layoutHeader);
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());

            if (header != null) {
                header.setPadding(
                        header.getPaddingLeft(),
                        systemBars.top + 20,
                        header.getPaddingRight(),
                        header.getPaddingBottom()
                );
            }

            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);

            return WindowInsetsCompat.CONSUMED;
        });
    }
}