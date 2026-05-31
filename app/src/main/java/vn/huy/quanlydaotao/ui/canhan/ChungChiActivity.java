package vn.huy.quanlydaotao.ui.canhan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
import vn.huy.quanlydaotao.data.repository.ChungChiRepositoryImpl;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachChungChiUseCase;

public class ChungChiActivity extends AppCompatActivity {
    private RecyclerView rvChungChi;
    private ChungChiAdapter adapter;
    private ChungChiViewModel viewModel;
    private int idNguoiDung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chung_chi);

        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController != null) {
            windowInsetsController.setAppearanceLightStatusBars(false);
        }

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            setupEdgeToEdge(mainView);
        }

        idNguoiDung = getIntent().getIntExtra("ID_NGUOI_DUNG", 0);
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
                        systemBars.top + 20,
                        header.getPaddingRight(),
                        header.getPaddingBottom()
                );
            }

            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
    }

    private void initViews() {
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        rvChungChi = findViewById(R.id.rvChungChi);
        rvChungChi.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChungChiAdapter();
        rvChungChi.setAdapter(adapter);

        adapter.setOnItemClickListener(item -> {
            if (item.getUrlHinhAnh() != null && !item.getUrlHinhAnh().isEmpty()) {
                Uri uri = Uri.parse(item.getUrlHinhAnh());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private void setupViewModel() {
        DichVuApi api = RetrofitClient.getClient().create(DichVuApi.class);
        CoSoDuLieuApp db = CoSoDuLieuApp.getInstance(this);
        ChungChiRepositoryImpl repo = new ChungChiRepositoryImpl(db.chungChiDao(), api);
        LayDanhSachChungChiUseCase useCase = new LayDanhSachChungChiUseCase(repo);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new ChungChiViewModel(useCase);
            }
        }).get(ChungChiViewModel.class);
    }

    private void observeData() {
        viewModel.getDanhSachChungChi().observe(this, data -> {
            if (data != null) {
                adapter.setData(data);
            }
        });

        if (idNguoiDung > 0) {
            viewModel.taiLaiDuLieu(idNguoiDung);
        }
    }
}