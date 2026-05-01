package vn.huy.quanlydaotao.ui.luyentap;

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
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.local.CoSoDuLieuApp;
import vn.huy.quanlydaotao.data.local.TokenManager;
import vn.huy.quanlydaotao.data.remote.api.RetrofitClient;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.repository.BaiKiemTraRepositoryImpl;
import vn.huy.quanlydaotao.domain.model.BaiKiemTra;
import vn.huy.quanlydaotao.domain.usecase.LayBaiKiemTraUseCase;
import vn.huy.quanlydaotao.ui.main.DialogHelper;
import vn.huy.quanlydaotao.ui.quiz.QuizActivity;

public class LuyenTapFragment extends Fragment {

    private RecyclerView rvPracticeQuizzes;
    private PracticeAdapter adapter;
    private LuyenTapViewModel viewModel;
    private TokenManager tokenManager;

    public LuyenTapFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_luyen_tap, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tokenManager = new TokenManager(requireContext());
        rvPracticeQuizzes = view.findViewById(R.id.rvPracticeQuizzes);

        setupEdgeToEdge(view);
        setupRecyclerView();
        setupViewModel();
        observeData();
    }

    private void setupEdgeToEdge(View view) {
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

    private void setupRecyclerView() {
        adapter = new PracticeAdapter();
        rvPracticeQuizzes.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPracticeQuizzes.setAdapter(adapter);

        adapter.setOnItemClickListener(this::showConfirmDialog);
    }

    private void setupViewModel() {
        // Khởi tạo các thành phần theo Clean Architecture
        DichVuApi api = RetrofitClient.getClient().create(DichVuApi.class);
        CoSoDuLieuApp db = CoSoDuLieuApp.getInstance(requireContext());
        BaiKiemTraRepositoryImpl repo = new BaiKiemTraRepositoryImpl(db.baiKiemTraDao(), api);
        LayBaiKiemTraUseCase useCase = new LayBaiKiemTraUseCase(repo);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new LuyenTapViewModel(useCase);
            }
        }).get(LuyenTapViewModel.class);
    }

    private void observeData() {
        int idUser = tokenManager.layId();

        // Tìm các View để xử lý ẩn hiện
        View layoutEmpty = getView().findViewById(R.id.layoutEmpty);
        // Nếu bạn có tiêu đề danh sách, hãy tìm nó để ẩn cùng lúc
        // View tvListTitle = getView().findViewById(R.id.tvListTitle);

        viewModel.getDanhSachBaiKiemTra().observe(getViewLifecycleOwner(), items -> {
            if (items == null || items.isEmpty()) {
                // Khi không có dữ liệu
                if (layoutEmpty != null) layoutEmpty.setVisibility(View.VISIBLE);
                if (rvPracticeQuizzes != null) rvPracticeQuizzes.setVisibility(View.GONE);
                // if (tvListTitle != null) tvListTitle.setVisibility(View.GONE);
            } else {
                // Khi có dữ liệu
                if (layoutEmpty != null) layoutEmpty.setVisibility(View.GONE);
                if (rvPracticeQuizzes != null) rvPracticeQuizzes.setVisibility(View.VISIBLE);
                // if (tvListTitle != null) tvListTitle.setVisibility(View.VISIBLE);

                adapter.setItems(items);
            }
        });

        if (idUser > 0) {
            viewModel.lamMoiDuLieu(idUser);
        }
    }

    private void showConfirmDialog(BaiKiemTra item) {
        DialogHelper.showConfirmDialog(
                requireContext(),
                "Bắt đầu kiểm tra",
                "Bạn có muốn bắt đầu bài: " + item.getTieuDe() + "?\nThời gian: " + item.getThoiGianLam() + " phút.",
                "Bắt đầu",
                "Hủy",
                () -> {
                    Intent intent = new Intent(getActivity(), QuizActivity.class);
                    // Truyền ID bài kiểm tra để màn hình Quiz lấy danh sách câu hỏi tương ứng
                    intent.putExtra("ID_BAI_KIEM_TRA", item.getId());
                    intent.putExtra("QUIZ_TITLE", item.getTieuDe());
                    intent.putExtra("THOI_GIAN", item.getThoiGianLam());
                    startActivity(intent);
                }
        );
    }
}