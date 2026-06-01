package vn.huy.quanlydaotao.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.local.CoSoDuLieuApp;
import vn.huy.quanlydaotao.data.local.TokenManager;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.api.RetrofitClient;
import vn.huy.quanlydaotao.data.repository.KhoaHocRepositoryImpl;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachKhoaHocUseCase;
import vn.huy.quanlydaotao.ui.canhan.ChungChiActivity;
import vn.huy.quanlydaotao.ui.canhan.DoiPassBottomSheet;
import vn.huy.quanlydaotao.ui.canhan.EditProfileActivity;
import vn.huy.quanlydaotao.ui.canhan.LichSuActivity;
import vn.huy.quanlydaotao.ui.khoahoc.KhoaHocViewModel;
import vn.huy.quanlydaotao.ui.thongbao.ThongBaoActivity;
import vn.huy.quanlydaotao.ui.main.DialogHelper;
import vn.huy.quanlydaotao.ui.video.DownloadCacheManager;

public class HomeFragment extends Fragment {

    private KhoaHocViewModel khoaHocViewModel;
    private HomeCourseAdapter courseAdapter;
    private TokenManager tokenManager;
    private ShimmerFrameLayout shimmerHomeCourses;
    private RecyclerView rvHomeCourses;
    private ImageView btnNotification;

    public HomeFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View header = view.findViewById(R.id.layoutHeader);
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            if (header != null) {
                header.setPadding(header.getPaddingLeft(), systemBars.top,
                        header.getPaddingRight(), header.getPaddingBottom());
            }
            return windowInsets;
        });
        initViews(view);
        setupHomeData(view);
    }

    private void initViews(View view) {
        tokenManager = new TokenManager(requireContext());

        shimmerHomeCourses = view.findViewById(R.id.shimmerHomeCourses);
        rvHomeCourses = view.findViewById(R.id.rvHomeCourses);
        if (shimmerHomeCourses != null) {
            shimmerHomeCourses.startShimmer();
        }

        TextView tvUsername = view.findViewById(R.id.tvUsername);
        TextView tvGreeting = view.findViewById(R.id.tvGreeting);

        if (tvUsername != null) {
            tvUsername.setText(tokenManager.layHoTen());
        }

        if (tvGreeting != null) {
            tvGreeting.setText("" + tokenManager.layVaiTro());
        }

        btnNotification = view.findViewById(R.id.btnNotification);
        btnNotification.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ThongBaoActivity.class);
            startActivity(intent);
        });

        MaterialCardView btnSettings = view.findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(v -> {
            DoiPassBottomSheet doiPassBottomSheet = DoiPassBottomSheet.newInstance();
            doiPassBottomSheet.show(getChildFragmentManager(), "DoiPassBottomSheet");
        });

        MaterialCardView btnAccount = view.findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), EditProfileActivity.class));
        });

        MaterialCardView btnKQT = view.findViewById(R.id.btnKQT);
        btnKQT.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), LichSuActivity.class);
            intent.putExtra("ID_NGUOI_DUNG", tokenManager.layId());
            startActivity(intent);
        });

        MaterialCardView btnCC = view.findViewById(R.id.btnCC);
        btnCC.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChungChiActivity.class);
            intent.putExtra("ID_NGUOI_DUNG", tokenManager.layId());
            startActivity(intent);
        });

        MaterialCardView btnNhac = view.findViewById(R.id.btnNhac);
        if (btnNhac != null) {
            btnNhac.setOnClickListener(v -> {
                boolean isMusicEnabled = tokenManager.laNhacChoDaBat();
                boolean newStatus = !isMusicEnabled;
                tokenManager.thietLapNhacCho(newStatus);

                String msg = newStatus ? "Đã bật tính năng nhạc chờ cuộc họp" : "Đã tắt tính năng nhạc chờ cuộc họp";
                String colorHex = newStatus ? "#10B981" : "#000000";

                Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.parseColor(colorHex));
                snackbar.setTextColor(Color.WHITE);
                View bottomMenu = getActivity() != null ? getActivity().findViewById(R.id.bottom_menu) : null;
                if (bottomMenu != null) {
                    snackbar.setAnchorView(bottomMenu);
                }
                snackbar.show();
            });
        }

        MaterialCardView btnDevice = view.findViewById(R.id.btnDevice);
        if (btnDevice != null) {
            btnDevice.setOnClickListener(v -> {
                DialogHelper.showConfirmDialog(
                        requireContext(),
                        "Xóa bộ nhớ đệm",
                        "Bạn có chắc chắn muốn giải phóng dung lượng và xóa toàn bộ video đã lưu tạm thời không?",
                        "OK",
                        "Hủy bỏ",
                        () -> {
                            new Thread(() -> {
                                DownloadCacheManager.getInstance(requireContext()).clearCache();
                                if (getActivity() != null) {
                                    getActivity().runOnUiThread(() -> {
                                        Snackbar snackbar = Snackbar.make(view, "Đã dọn dẹp không gian lưu trữ video hoàn tất", Snackbar.LENGTH_SHORT);
                                        snackbar.setBackgroundTint(Color.parseColor("#10B981"));
                                        snackbar.setTextColor(Color.WHITE);
                                        View bottomMenu = getActivity().findViewById(R.id.bottom_menu);
                                        if (bottomMenu != null) {
                                            snackbar.setAnchorView(bottomMenu);
                                        }
                                        snackbar.show();
                                    });
                                }
                            }).start();
                        }
                );
            });
        }
    }

    private void setupHomeData(View view) {
        RecyclerView rvHomeCourses = view.findViewById(R.id.rvHomeCourses);
        if (rvHomeCourses != null) {
            rvHomeCourses.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            courseAdapter = new HomeCourseAdapter();
            rvHomeCourses.setAdapter(courseAdapter);

            courseAdapter.setOnItemClickListener(khoaHoc -> {
                Bundle bundle = new Bundle();
                bundle.putInt("id_khoa_hoc", khoaHoc.getId());
                androidx.navigation.Navigation.findNavController(view)
                        .navigate(R.id.navigation_lop_hoc, bundle);
            });
        }

        DichVuApi api = RetrofitClient.getClient().create(DichVuApi.class);
        KhoaHocRepositoryImpl repo = new KhoaHocRepositoryImpl(
                CoSoDuLieuApp.getInstance(requireContext()).khoaHocDao(),
                api
        );
        LayDanhSachKhoaHocUseCase useCase = new LayDanhSachKhoaHocUseCase(repo);

        khoaHocViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new KhoaHocViewModel(useCase);
            }
        }).get(KhoaHocViewModel.class);

        khoaHocViewModel.getDanhSachKhoaHoc().observe(getViewLifecycleOwner(), danhSach -> {
            if (danhSach != null && !danhSach.isEmpty()) {
                courseAdapter.setItems(danhSach);

                if (shimmerHomeCourses != null) {
                    shimmerHomeCourses.stopShimmer();
                    shimmerHomeCourses.setVisibility(View.GONE);
                }
                if (rvHomeCourses != null) {
                    rvHomeCourses.setVisibility(View.VISIBLE);
                }
            }
        });

        khoaHocViewModel.taiLaiDuLieu();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (shimmerHomeCourses != null && shimmerHomeCourses.getVisibility() == View.VISIBLE) {
            shimmerHomeCourses.startShimmer();
        }
    }

    @Override
    public void onPause() {
        if (shimmerHomeCourses != null) {
            shimmerHomeCourses.stopShimmer();
        }
        super.onPause();
    }
}