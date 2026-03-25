package vn.huy.quanlydaotao.ui.luyentap;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.ui.quiz.QuizActivity;

public class LuyenTapFragment extends Fragment {

    private RecyclerView rvPracticeQuizzes;
    private PracticeAdapter adapter;

    public LuyenTapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_luyen_tap, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPracticeQuizzes = view.findViewById(R.id.rvPracticeQuizzes);
        setupRecyclerView();
        loadData();
    }

    private void setupRecyclerView() {
        adapter = new PracticeAdapter();
        rvPracticeQuizzes.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPracticeQuizzes.setAdapter(adapter);

        adapter.setOnItemClickListener(item -> {
            showConfirmDialog(item);
        });
    }

    private void loadData() {
        List<PracticeAdapter.PracticeItem> items = new ArrayList<>();
        items.add(new PracticeAdapter.PracticeItem("Kiểm tra kiến thức cơ bản", "Hơn 50 câu hỏi trắc nghiệm tổng hợp", R.drawable.books));
        items.add(new PracticeAdapter.PracticeItem("Luyện tập chuyên sâu", "Các chủ đề nâng cao về kinh doanh", R.drawable.medal));
        items.add(new PracticeAdapter.PracticeItem("Thi thử chứng chỉ", "Mô phỏng kỳ thi thật trong 60 phút", R.drawable.graduation_cap));
        adapter.setItems(items);
    }

    private void showConfirmDialog(PracticeAdapter.PracticeItem item) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Xác nhận")
                .setMessage("Bạn có muốn bắt đầu bài " + item.getTitle() + " không?")
                .setPositiveButton("Bắt đầu", (dialog, which) -> {
                    Intent intent = new Intent(getActivity(), QuizActivity.class);
                    // Có thể truyền thêm dữ liệu bài kiểm tra qua intent nếu cần
                    intent.putExtra("QUIZ_TITLE", item.getTitle());
                    startActivity(intent);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
