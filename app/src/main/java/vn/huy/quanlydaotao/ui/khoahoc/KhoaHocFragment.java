package vn.huy.quanlydaotao.ui.khoahoc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class KhoaHocFragment extends Fragment {
    private CourseAdapter adapter;
    private KhoaHocViewModel viewModel;

    public KhoaHocFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_khoa_hoc, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvCourses = view.findViewById(R.id.rvCourses);
        adapter = new CourseAdapter();
        rvCourses.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCourses.setAdapter(adapter);

        // Khởi tạo Manual DI (Trong thực tế nên dùng Hilt/Dagger)
        DichVuApi api = RetrofitClient.getClient().create(DichVuApi.class);
        KhoaHocRepositoryImpl repo = new KhoaHocRepositoryImpl(
                CoSoDuLieuApp.getInstance(requireContext()).khoaHocDao(), 
                api
        );
        LayDanhSachKhoaHocUseCase useCase = new LayDanhSachKhoaHocUseCase(repo);
        
        // Khởi tạo ViewModel đúng cách thông qua ViewModelProvider.Factory
        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new KhoaHocViewModel(useCase);
            }
        }).get(KhoaHocViewModel.class);

        viewModel.getDanhSachKhoaHoc().observe(getViewLifecycleOwner(), khoaHocs -> {
            if (khoaHocs != null) {
                adapter.setItems(khoaHocs);
            }
        });

        // Tải lại dữ liệu từ API
        viewModel.taiLaiDuLieu();
    }
}
