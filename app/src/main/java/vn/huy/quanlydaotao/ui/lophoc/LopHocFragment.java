package vn.huy.quanlydaotao.ui.lophoc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public LopHocFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idKhoaHoc = getArguments().getInt(ARG_KHOA_HOC_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lop_hoc, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View header = view.findViewById(R.id.layoutHeader);

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());

            if (header != null) {

                int pLeft = header.getPaddingLeft();
                int pRight = header.getPaddingRight();
                int pBottom = header.getPaddingBottom();

                header.setPadding(pLeft, systemBars.top + 20, pRight, pBottom);
            }

            return WindowInsetsCompat.CONSUMED; // Trả về CONSUMED để báo đã xử lý xong
        });
        view.findViewById(R.id.btnBack).setOnClickListener(v -> requireActivity().onBackPressed());

        RecyclerView rvLopHoc = view.findViewById(R.id.rvLopHoc);
        rvLopHoc.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LopHocAdapter();
        rvLopHoc.setAdapter(adapter);
        adapter.setOnLopHocClickListener(lopHoc -> {
            TokenManager tokenManager = new TokenManager(requireContext());
            int idNguoiDung = tokenManager.layId();
            android.util.Log.d("HUY_DEBUG", "2. ID người dùng lấy từ Token: " + idNguoiDung);
            new Thread(() -> {
                try {
                    CoSoDuLieuApp db = CoSoDuLieuApp.getInstance(requireContext());
                    if (db.dangKyLopDao() == null) {
                        android.util.Log.e("HUY_DEBUG", "LỖI: dangKyLopDao bị NULL!");
                    }
                    boolean daDangKy = db.dangKyLopDao().isDaDangKy(idNguoiDung, lopHoc.getId());

                    requireActivity().runOnUiThread(() -> {
                        if (daDangKy) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("id_khoa_hoc", lopHoc.getIdKhoaHoc());

                            androidx.navigation.Navigation.findNavController(view)
                                    .navigate(R.id.navigation_bai_hoc, bundle);
                        } else {
                            DangKyLopBottomSheet sheet = DangKyLopBottomSheet.newInstance(
                                    lopHoc.getId(),
                                    lopHoc.getTenLop()
                            );
                            sheet.show(getChildFragmentManager(), "DangKySheet");
                        }
                    });
                } catch (Exception e) {
                    android.util.Log.e("HUY_DEBUG", "LỖI TRONG THREAD: " + e.getMessage());
                    e.printStackTrace();
                }
            }).start();
        });
        // Manual DI for ViewModel
        DichVuApi api = RetrofitClient.getClient().create(DichVuApi.class);
        LopHocRepositoryImpl repo = new LopHocRepositoryImpl(
                CoSoDuLieuApp.getInstance(requireContext()).lopHocDao(),
                api
        );
        LayDanhSachLopHocUseCase useCase = new LayDanhSachLopHocUseCase(repo);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new LopHocViewModel(useCase);
            }
        }).get(LopHocViewModel.class);

        viewModel.layDanhSachLopHoc(idKhoaHoc).observe(getViewLifecycleOwner(), lops -> {
            if (lops != null) {
                adapter.setItems(lops);
            }
        });

        viewModel.taiLaiDuLieu(idKhoaHoc);
    }
}
