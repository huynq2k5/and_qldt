package vn.huy.quanlydaotao.ui.hoctructuyen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.local.CoSoDuLieuApp;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.api.RetrofitClient;
import vn.huy.quanlydaotao.data.repository.LichMeetRepositoryImpl;
import vn.huy.quanlydaotao.data.repository.LopHocRepositoryImpl;
import vn.huy.quanlydaotao.domain.model.LichMeet;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachLichMeetUseCase;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachLopHocUseCase;

public class HocTrucTuyenFragment extends Fragment {

    private LichMeetAdapter adapter;
    private HocTrucTuyenViewModel viewModel;
    private int idLopHoc;

    public HocTrucTuyenFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idLopHoc = getArguments().getInt("id_lop_hoc", 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hoc_truc_tuyen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        android.util.Log.d("HUY_DEBUG", "Fragment đã onViewCreated");

        View header = view.findViewById(R.id.layoutHeader);
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            if (header != null) {
                header.setPadding(header.getPaddingLeft(), systemBars.top,
                        header.getPaddingRight(), header.getPaddingBottom());
            }
            return windowInsets;
        });

        setupRecyclerView(view);
        setupViewModel();
        observeData();
    }

    private void setupRecyclerView(View view) {
        RecyclerView rvLichMeet = view.findViewById(R.id.rvLichMeet);
        adapter = new LichMeetAdapter();
        rvLichMeet.setLayoutManager(new LinearLayoutManager(getContext()));
        rvLichMeet.setAdapter(adapter);

        adapter.setOnBtnJoinClickListener(link -> {
            if (link != null && !link.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Link họp không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupViewModel() {
        DichVuApi api = RetrofitClient.getClient().create(DichVuApi.class);
        CoSoDuLieuApp db = CoSoDuLieuApp.getInstance(requireContext());

        LichMeetRepositoryImpl lichRepo = new LichMeetRepositoryImpl(db.lichMeetDao(), api);
        LayDanhSachLichMeetUseCase lichUseCase = new LayDanhSachLichMeetUseCase(lichRepo);

        LopHocRepositoryImpl lopRepo = new LopHocRepositoryImpl(db.lopHocDao(), api);
        LayDanhSachLopHocUseCase lopUseCase = new LayDanhSachLopHocUseCase(lopRepo);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new HocTrucTuyenViewModel(lichUseCase, lopUseCase);
            }
        }).get(HocTrucTuyenViewModel.class);
    }

    private void observeData() {
            int testId = (idLopHoc > 0) ? idLopHoc : 1;

            android.util.Log.d("HUY_DEBUG", "Bắt đầu gọi hiển thị lịch cho ID: " + testId);
            hienThiLichTheoLop(testId);
    }

    private void hienThiLichTheoLop(int id) {
        viewModel.getDanhSachLichMeet(idLopHoc).observe(getViewLifecycleOwner(), lichMeets -> {
            if (lichMeets != null && !lichMeets.isEmpty()) {
                adapter.setItems(lichMeets);

                View layoutLive = getView().findViewById(R.id.layoutLiveNow);
                if (checkNeuCoLopLive(lichMeets)) {
                    layoutLive.setVisibility(View.VISIBLE);
                } else {
                    layoutLive.setVisibility(View.GONE);
                }
            }
        });
        viewModel.taiLaiDuLieu(id);
    }
    private boolean checkNeuCoLopLive(List<LichMeet> lichMeets) {
        if (lichMeets == null || lichMeets.isEmpty()) return false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        long gioHienTai = System.currentTimeMillis();
        long motTiengRuoi = 90 * 60 * 1000;

        for (LichMeet item : lichMeets) {
            try {
                Date thoiDiemBatDau = sdf.parse(item.getThoiGian());
                if (thoiDiemBatDau != null) {
                    long batDauMilis = thoiDiemBatDau.getTime();
                    long ketThucMilis = batDauMilis + motTiengRuoi;

                    if (gioHienTai >= batDauMilis && gioHienTai <= ketThucMilis) {
                        doDuLieuVaoCardLive(item);
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void doDuLieuVaoCardLive(LichMeet item) {
        View view = getView();
        if (view == null) return;

        TextView tvLiveTitle = view.findViewById(R.id.tvLiveTitle);
        com.google.android.material.button.MaterialButton btnJoin = view.findViewById(R.id.btnJoinLive);

        tvLiveTitle.setText(item.getTieuDe());
        btnJoin.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLinkMeet()));
            startActivity(intent);
        });
    }
}