package vn.huy.quanlydaotao.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.local.CoSoDuLieuApp;
import vn.huy.quanlydaotao.data.local.TokenManager;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.api.RetrofitClient;
import vn.huy.quanlydaotao.data.repository.KhoaHocRepositoryImpl;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachKhoaHocUseCase;
import vn.huy.quanlydaotao.ui.khoahoc.KhoaHocViewModel;

public class HomeFragment extends Fragment {

    private KhoaHocViewModel khoaHocViewModel;
    private HomeCourseAdapter courseAdapter;
    private TextView tvActiveCourseTitle;
    private LinearProgressIndicator courseProgress;
    private TokenManager tokenManager;

    public HomeFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupHomeData(view);
    }

    private void initViews(View view) {
        tokenManager = new TokenManager(requireContext());

        tvActiveCourseTitle = view.findViewById(R.id.tvActiveCourseTitle);
        courseProgress = view.findViewById(R.id.courseProgress);

        TextView tvUsername = view.findViewById(R.id.tvUsername);
        TextView tvGreeting = view.findViewById(R.id.tvGreeting);

        if (tvUsername != null) {
            tvUsername.setText(tokenManager.layHoTen());
        }

        if (tvGreeting != null) {
            tvGreeting.setText("Vai trò: " + tokenManager.layVaiTro());
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

                tvActiveCourseTitle.setText(danhSach.get(0).getTenKhoaHoc());
                courseProgress.setProgress(65, true);
            }
        });

        khoaHocViewModel.taiLaiDuLieu();
    }
}