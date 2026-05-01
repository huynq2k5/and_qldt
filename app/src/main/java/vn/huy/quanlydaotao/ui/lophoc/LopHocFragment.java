package vn.huy.quanlydaotao.ui.lophoc;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachLopHocUseCase;
import vn.huy.quanlydaotao.ui.lophoc.dangky.DangKyLopBottomSheet;

public class LopHocFragment extends Fragment {

    public static final String ARG_KHOA_HOC_ID = "id_khoa_hoc";
    private LopHocViewModel viewModel;
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

        view.findViewById(R.id.btnBack).setOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());
    }

    private void setupEdgeToEdge(View view) {
        View header = view.findViewById(R.id.layoutHeader);
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            if (header != null) {
                header.setPadding(header.getPaddingLeft(), systemBars.top + 20, header.getPaddingRight(), header.getPaddingBottom());
            }
            return WindowInsetsCompat.CONSUMED;
        });
    }

    private void setupRecyclerView() {
        rvLopHoc.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LopHocAdapter();
        rvLopHoc.setAdapter(adapter);

        adapter.setOnLopHocClickListener(lopHoc -> {
            if (lopHoc.getDaDangKy() == 1) {
                Bundle bundle = new Bundle();

                bundle.putInt("id_khoa_hoc", idKhoaHoc);

                Navigation.findNavController(requireView()).navigate(R.id.navigation_bai_hoc, bundle);
            } else {
                DangKyLopBottomSheet sheet = DangKyLopBottomSheet.newInstance(lopHoc.getId(), lopHoc.getTenLop());
                sheet.setOnDangKyThanhCongListener(() -> {
                    lamMoiDanhSach();
                });
                sheet.show(getChildFragmentManager(), "DangKySheet");
            }
        });
    }

    private void setupViewModel() {
        DichVuApi api = RetrofitClient.getClient().create(DichVuApi.class);
        LopHocRepositoryImpl repo = new LopHocRepositoryImpl(CoSoDuLieuApp.getInstance(requireContext()).lopHocDao(), api);
        LayDanhSachLopHocUseCase useCase = new LayDanhSachLopHocUseCase(repo);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends androidx.lifecycle.ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new LopHocViewModel(useCase);
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

    public void lamMoiDanhSach() {
        viewModel.taiLaiDuLieu(idKhoaHoc, idNguoiDung);
    }
}