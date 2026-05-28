package vn.huy.quanlydaotao.ui.lophoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.shimmer.ShimmerFrameLayout;
import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.local.CoSoDuLieuApp;
import vn.huy.quanlydaotao.data.local.TokenManager;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.api.RetrofitClient;
import vn.huy.quanlydaotao.data.repository.LopHocRepositoryImpl;
import vn.huy.quanlydaotao.data.repository.XoaDangKyRepositoryImpl;
import vn.huy.quanlydaotao.domain.model.LopHoc;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachLopHocUseCase;
import vn.huy.quanlydaotao.domain.usecase.XoaDangKyUseCase;
import vn.huy.quanlydaotao.ui.lophoc.dangky.DangKyLopBottomSheet;
import vn.huy.quanlydaotao.ui.main.DialogHelper;

public class LopHocFragment extends Fragment {

    public static final String ARG_KHOA_HOC_ID = "id_khoa_hoc";
    private LopHocViewModel viewModel;
    private XoaDangKyUseCase xoaDangKyUseCase;
    private LopHocAdapter adapter;
    private int idKhoaHoc;
    private int idNguoiDung;
    private ShimmerFrameLayout shimmerLopHoc;
    private RecyclerView rvLopHoc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idKhoaHoc = getArguments().getInt(ARG_KHOA_HOC_ID);
        }
        idNguoiDung = new TokenManager(requireContext()).layId();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lop_hoc, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupEdgeToEdge(view);
        setupRecyclerView();
        setupViewModel();
        observeData();
    }

    private void initViews(View view) {
        shimmerLopHoc = view.findViewById(R.id.shimmerLopHoc);
        rvLopHoc = view.findViewById(R.id.rvLopHoc);

        if (shimmerLopHoc != null) {
            shimmerLopHoc.startShimmer();
        }

        view.findViewById(R.id.btnBack).setOnClickListener(v ->
                requireActivity().getOnBackPressedDispatcher().onBackPressed()
        );
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
            return WindowInsetsCompat.CONSUMED;
        });
    }

    private void setupRecyclerView() {
        rvLopHoc.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LopHocAdapter();
        rvLopHoc.setAdapter(adapter);

        adapter.setOnLopHocClickListener(new LopHocAdapter.OnLopHocClickListener() {
            @Override
            public void onVaoHocClick(LopHoc lopHoc) {
                Bundle bundle = new Bundle();
                bundle.putInt("id_khoa_hoc", idKhoaHoc);
                Navigation.findNavController(requireView()).navigate(R.id.navigation_bai_hoc, bundle);
            }

            @Override
            public void onHuyDangKyClick(LopHoc lopHoc) {
                DialogHelper.showConfirmDialog(
                        requireContext(),
                        "Hủy đăng ký lớp học",
                        "Bạn có chắc chắn muốn hủy đăng ký lớp: " + lopHoc.getTenLop() + "?",
                        "Hủy đăng ký",
                        "Quay lại",
                        () -> thucHienHuyDangKy(lopHoc.getId())
                );
            }

            @Override
            public void onDangKyClick(LopHoc lopHoc) {
                DangKyLopBottomSheet sheet = DangKyLopBottomSheet.newInstance(lopHoc.getId(), lopHoc.getTenLop());
                sheet.setOnDangKyThanhCongListener(() -> lamMoiDanhSach());
                sheet.show(getChildFragmentManager(), "DangKySheet");
            }
        });
    }

    private void setupViewModel() {
        DichVuApi api = RetrofitClient.getClient().create(DichVuApi.class);
        CoSoDuLieuApp db = CoSoDuLieuApp.getInstance(requireContext());

        LopHocRepositoryImpl repoLop = new LopHocRepositoryImpl(db.lopHocDao(), api);
        LayDanhSachLopHocUseCase useCaseLop = new LayDanhSachLopHocUseCase(repoLop);

        XoaDangKyRepositoryImpl repoXoa = new XoaDangKyRepositoryImpl(api);
        xoaDangKyUseCase = new XoaDangKyUseCase(repoXoa);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends androidx.lifecycle.ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new LopHocViewModel(useCaseLop);
            }
        }).get(LopHocViewModel.class);
    }

    private void observeData() {
        viewModel.layDanhSachLopHoc(idKhoaHoc, idNguoiDung).observe(getViewLifecycleOwner(), lops -> {
            if (lops != null && !lops.isEmpty()) {
                adapter.setItems(lops);

                if (shimmerLopHoc != null) {
                    shimmerLopHoc.stopShimmer();
                    shimmerLopHoc.setVisibility(View.GONE);
                }
                rvLopHoc.setVisibility(View.VISIBLE);
            }
        });
    }

    private void thucHienHuyDangKy(int idLopHoc) {
        if (shimmerLopHoc != null) {
            rvLopHoc.setVisibility(View.GONE);
            shimmerLopHoc.setVisibility(View.VISIBLE);
            shimmerLopHoc.startShimmer();
        }

        xoaDangKyUseCase.execute(idNguoiDung, idLopHoc).observe(getViewLifecycleOwner(), response -> {
            if (response != null && "success".equals(response.getStatus())) {
                lamMoiDanhSach();
                Toast.makeText(getContext(), "Đã hủy đăng ký lớp học thành công", Toast.LENGTH_SHORT).show();
            } else {
                if (shimmerLopHoc != null) {
                    shimmerLopHoc.stopShimmer();
                    shimmerLopHoc.setVisibility(View.GONE);
                }
                rvLopHoc.setVisibility(View.VISIBLE);

                String errorMsg = response != null ? response.getMessage() : "Hủy đăng ký thất bại, vui lòng thử lại";
                Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void lamMoiDanhSach() {
        viewModel.taiLaiDuLieu(idKhoaHoc, idNguoiDung);
    }
}