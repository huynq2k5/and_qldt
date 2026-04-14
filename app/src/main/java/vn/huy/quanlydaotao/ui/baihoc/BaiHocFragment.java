package vn.huy.quanlydaotao.ui.baihoc;

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
import vn.huy.quanlydaotao.data.repository.BaiHocRepositoryImpl;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachBaiHocUseCase;

public class BaiHocFragment extends Fragment {
    private int idKhoaHoc;
    private BaiHocAdapter adapter;
    private BaiHocViewModel viewModel;

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
        view.findViewById(R.id.btnBack).setOnClickListener(v -> requireActivity().onBackPressed());
        RecyclerView rv = view.findViewById(R.id.rvBaiHoc);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BaiHocAdapter();
        rv.setAdapter(adapter);

        DichVuApi api = RetrofitClient.getClient().create(DichVuApi.class);
        BaiHocRepositoryImpl repo = new BaiHocRepositoryImpl(CoSoDuLieuApp.getInstance(requireContext()).baiHocDao(), api);
        LayDanhSachBaiHocUseCase useCase = new LayDanhSachBaiHocUseCase(repo);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new BaiHocViewModel(useCase);
            }
        }).get(BaiHocViewModel.class);

        viewModel.getDanhSachBaiHoc(idKhoaHoc).observe(getViewLifecycleOwner(), items -> {
            if (items != null) adapter.setItems(items);
        });
        viewModel.taiLaiDuLieu(idKhoaHoc);
    }
}