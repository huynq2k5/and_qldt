package vn.huy.quanlydaotao.ui.baihoc;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.local.CoSoDuLieuApp;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.api.RetrofitClient;
import vn.huy.quanlydaotao.data.repository.BaiHocRepositoryImpl;
import vn.huy.quanlydaotao.domain.model.BaiHoc;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachBaiHocUseCase;
import vn.huy.quanlydaotao.ui.main.MainViewModel;
import vn.huy.quanlydaotao.ui.video.VideoPlayerActivity;

public class BaiHocFragment extends Fragment {
    private int idKhoaHoc;
    private BaiHocAdapter adapter;
    private BaiHocViewModel viewModel;
    private MainViewModel mainViewModel;
    private final List<BaiHoc> realVideoList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idKhoaHoc = getArguments().getInt("id_khoa_hoc");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bai_hoc, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupWindowInsets(view);
        setupViews(view);
        setupViewModels();
        observeData();

        viewModel.taiLaiDuLieu(idKhoaHoc);
    }
    private void setupWindowInsets(View view) {
        View header = view.findViewById(R.id.layoutHeader);
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            if (header != null) {
                header.setPadding(header.getPaddingLeft(), systemBars.top + 20,
                        header.getPaddingRight(), header.getPaddingBottom());
            }
            return WindowInsetsCompat.CONSUMED;
        });
    }
    private void setupViews(View view) {
        view.findViewById(R.id.btnBack).setOnClickListener(v -> requireActivity().onBackPressed());

        RecyclerView rv = view.findViewById(R.id.rvBaiHoc);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BaiHocAdapter();
        rv.setAdapter(adapter);

        adapter.setOnBaiHocClickListener(new BaiHocAdapter.OnBaiHocClickListener() {
            @Override
            public void onVideoGroupClick(List<BaiHoc> allVideos) {
                handleVideoGroupClick();
            }

            @Override
            public void onPdfClick(BaiHoc pdfLesson) {
                handleOpenPdf(pdfLesson.getDuongDanTep());
            }
        });
    }
    private void setupViewModels() {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        DichVuApi api = RetrofitClient.getClient().create(DichVuApi.class);
        BaiHocRepositoryImpl repo = new BaiHocRepositoryImpl(
                CoSoDuLieuApp.getInstance(requireContext()).baiHocDao(), api);
        LayDanhSachBaiHocUseCase useCase = new LayDanhSachBaiHocUseCase(repo);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new BaiHocViewModel(useCase);
            }
        }).get(BaiHocViewModel.class);
    }
    private void observeData() {
        viewModel.getDanhSachBaiHoc(idKhoaHoc).observe(getViewLifecycleOwner(), items -> {
            if (items == null) return;

            List<BaiHoc> displayList = new ArrayList<>();
            realVideoList.clear();

            for (BaiHoc item : items) {
                if ("video".equals(item.getLoaiNoiDung())) {
                    realVideoList.add(item);
                } else {
                    displayList.add(item);
                }
            }

            if (!realVideoList.isEmpty()) {
                BaiHoc videoRepresent = new BaiHoc(-1, idKhoaHoc,
                        "Tổng số video (" + realVideoList.size() + ")", "video", "");
                displayList.add(0, videoRepresent);
            }
            adapter.setItems(displayList);
        });
    }
    private void handleVideoGroupClick() {
        if (realVideoList.isEmpty()) return;

        Boolean isConnected = mainViewModel.getIsConnected().getValue();
        View rootView = getView();
        if (rootView == null) return;

        if (isConnected != null && !isConnected) {
            showStatusSnackbar(rootView, "Không có mạng, không thể xem video bài giảng!", "#F59E0B", Color.BLACK);
            return;
        }

        Intent intent = new Intent(getContext(), VideoPlayerActivity.class);
        intent.putExtra("danh_sach_video", new ArrayList<>(realVideoList));
        startActivity(intent);
    }
    private void handleOpenPdf(String url) {
        Boolean isConnected = mainViewModel.getIsConnected().getValue();
        View rootView = getView();
        if (rootView == null) return;

        if (isConnected != null && !isConnected) {
            showStatusSnackbar(rootView, "Không có mạng, không thể tải tài liệu!", "#F59E0B", Color.BLACK);
            return;
        }

        if (url == null || url.isEmpty()) {
            showStatusSnackbar(rootView, "Lỗi: Tài liệu chưa có liên kết!", "#B3261E", Color.WHITE);
            return;
        }

        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            showStatusSnackbar(rootView, "Không tìm thấy ứng dụng đọc PDF!", "#B3261E", Color.WHITE);
        }
    }
    private void showStatusSnackbar(View view, String message, String colorHex, int textColor) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.parseColor(colorHex));
        snackbar.setTextColor(textColor);

        View bottomMenu = requireActivity().findViewById(R.id.bottom_menu);
        if (bottomMenu != null) {
            snackbar.setAnchorView(bottomMenu);
        }

        snackbar.show();
    }
}