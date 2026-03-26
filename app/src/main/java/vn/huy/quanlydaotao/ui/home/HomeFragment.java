package vn.huy.quanlydaotao.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.local.CoSoDuLieuApp;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.api.RetrofitClient;
import vn.huy.quanlydaotao.data.repository.KhoaHocRepositoryImpl;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachKhoaHocUseCase;
import vn.huy.quanlydaotao.ui.khoahoc.KhoaHocViewModel;

public class HomeFragment extends Fragment {

    private KhoaHocViewModel khoaHocViewModel;
    private HomeCourseAdapter courseAdapter; // Sử dụng đúng Adapter cho Home

    public HomeFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupHeader(view);
        setupHomeData(view);
    }

    private void setupHeader(View view) {
        TextView tvUsername = view.findViewById(R.id.tvUsername);
        if (tvUsername != null) tvUsername.setText("Huy Nguyễn");

        // ... Các xử lý click khác giữ nguyên ...
    }

    private void setupHomeData(View view) {
        // 1. Setup RecyclerView cuộn ngang
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

        // 2. Khởi tạo Manual DI (Giống KhoaHocFragment)
        DichVuApi api = RetrofitClient.getClient().create(DichVuApi.class);
        KhoaHocRepositoryImpl repo = new KhoaHocRepositoryImpl(
                CoSoDuLieuApp.getInstance(requireContext()).khoaHocDao(),
                api
        );
        LayDanhSachKhoaHocUseCase useCase = new LayDanhSachKhoaHocUseCase(repo);

        // 3. Khởi tạo ViewModel bằng Factory ẩn danh (Để tránh Crash)
        khoaHocViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new KhoaHocViewModel(useCase);
            }
        }).get(KhoaHocViewModel.class);

        // 4. Observe dữ liệu
        khoaHocViewModel.getDanhSachKhoaHoc().observe(getViewLifecycleOwner(), danhSach -> {
            if (danhSach != null) {
                courseAdapter.setItems(danhSach);
            }
        });

        // Tải lại dữ liệu từ API
        khoaHocViewModel.taiLaiDuLieu();
    }
}