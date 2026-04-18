package vn.huy.quanlydaotao.ui.baihoc;

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

import java.util.ArrayList;
import java.util.List;

import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.local.CoSoDuLieuApp;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.api.RetrofitClient;
import vn.huy.quanlydaotao.data.repository.BaiHocRepositoryImpl;
import vn.huy.quanlydaotao.domain.model.BaiHoc;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachBaiHocUseCase;
import vn.huy.quanlydaotao.ui.video.VideoPlayerActivity;

public class BaiHocFragment extends Fragment {
    private int idKhoaHoc;
    private BaiHocAdapter adapter;
    private BaiHocViewModel viewModel;
    private List<BaiHoc> realVideoList = new ArrayList<>();

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
        RecyclerView rv = view.findViewById(R.id.rvBaiHoc);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BaiHocAdapter();
        rv.setAdapter(adapter);

        adapter.setOnBaiHocClickListener(new BaiHocAdapter.OnBaiHocClickListener() {
            @Override
            public void onVideoGroupClick(List<BaiHoc> allVideos) {
                if (realVideoList.isEmpty()) return; // Bảo vệ nếu không có video

                android.content.Intent intent = new android.content.Intent(getContext(), VideoPlayerActivity.class);
                // QUAN TRỌNG: Truyền realVideoList (danh sách thật) chứ không phải allVideos (danh sách lọc từ adapter)
                intent.putExtra("danh_sach_video", new java.util.ArrayList<>(realVideoList));
                startActivity(intent);
            }

            @Override
            public void onPdfClick(BaiHoc pdfLesson) {
                // Xử lý sau
            }
        });

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
            if (items != null) {
                List<BaiHoc> displayList = new ArrayList<>();
                realVideoList.clear(); // Xóa danh sách cũ trước khi nạp mới

                for (BaiHoc item : items) {
                    if ("video".equals(item.getLoaiNoiDung())) {
                        realVideoList.add(item); // Cất video thật vào đây
                    } else {
                        displayList.add(item);
                    }
                }

                if (!realVideoList.isEmpty()) {
                    BaiHoc videoRepresent = new BaiHoc(
                            -1,
                            idKhoaHoc,
                            "Tổng số video (" + realVideoList.size() + ")",
                            "video",
                            "" // Cái này chỉ để hiển thị, không dùng để phát
                    );
                    displayList.add(0, videoRepresent);
                }
                adapter.setItems(displayList);
            }
        });
        viewModel.taiLaiDuLieu(idKhoaHoc);
    }
}