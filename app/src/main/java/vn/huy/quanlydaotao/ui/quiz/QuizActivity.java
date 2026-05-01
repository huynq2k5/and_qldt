package vn.huy.quanlydaotao.ui.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.local.CoSoDuLieuApp;
import vn.huy.quanlydaotao.data.local.TokenManager;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.api.RetrofitClient;
import vn.huy.quanlydaotao.data.repository.CauHoiRepositoryImpl;
import vn.huy.quanlydaotao.domain.model.CauHoi;
import vn.huy.quanlydaotao.domain.usecase.LayCauHoiUseCase;
import vn.huy.quanlydaotao.domain.usecase.NopBaiUseCase;
import vn.huy.quanlydaotao.ui.ketqua.KetQuaActivity;
import vn.huy.quanlydaotao.ui.main.DialogHelper;

public class QuizActivity extends AppCompatActivity {

    private QuizViewModel viewModel;
    private TextView tvQuestionText, tvTimer;
    private RadioGroup rgAnswers;
    private RadioButton rbA, rbB, rbC, rbD;
    private int idBkt;
    private View layoutLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        androidx.core.view.WindowInsetsControllerCompat windowInsetsController = androidx.core.view.WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());

        windowInsetsController.setAppearanceLightStatusBars(false);

        idBkt = getIntent().getIntExtra("ID_BAI_KIEM_TRA", 0);
        int timeLimit = getIntent().getIntExtra("THOI_GIAN", 60);

        initViews();
        setupViewModel();
        observeViewModel();

        viewModel.startTimer(timeLimit);
    }

    private void initViews() {
        tvQuestionText = findViewById(R.id.tvQuestionText);
        tvTimer = findViewById(R.id.tvQuizTimer);
        rgAnswers = findViewById(R.id.rgAnswers);
        rbA = findViewById(R.id.rbOption1);
        rbB = findViewById(R.id.rbOption2);
        rbC = findViewById(R.id.rbOption3);
        rbD = findViewById(R.id.rbOption4);
        layoutLoading = findViewById(R.id.layoutLoading);

        findViewById(R.id.btnBackQuiz).setOnClickListener(v -> finish());
        findViewById(R.id.btnNextQuestion).setOnClickListener(v -> viewModel.nextQuestion());
        findViewById(R.id.btnPrevQuestion).setOnClickListener(v -> viewModel.prevQuestion());
        findViewById(R.id.btnQuestionList).setOnClickListener(v -> showQuestionListDialog());
        findViewById(R.id.btnSubmitQuiz).setOnClickListener(v -> {
            DialogHelper.showConfirmDialog(
                    this,
                    "Nộp bài kiểm tra",
                    "Bạn có chắc chắn muốn kết thúc bài làm và nộp kết quả ngay bây giờ không?",
                    "Nộp bài",
                    "Làm tiếp",
                    () -> {
                        TokenManager tm = new TokenManager(this);
                        viewModel.submitQuiz(tm.layId(), idBkt);
                    }
            );
        });
    }

    private void setupViewModel() {
        DichVuApi api = RetrofitClient.getClient().create(DichVuApi.class);
        CoSoDuLieuApp db = CoSoDuLieuApp.getInstance(this);

        // Khởi tạo các Repository
        CauHoiRepositoryImpl repoCauHoi = new CauHoiRepositoryImpl(db.cauHoiDao(), api);
        // Giả sử Huy đã có KetQuaRepositoryImpl như các turn trước
        vn.huy.quanlydaotao.data.repository.KetQuaRepositoryImpl repoKetQua =
                new vn.huy.quanlydaotao.data.repository.KetQuaRepositoryImpl(db.ketQuaDao(), api);

        // Khởi tạo UseCases
        LayCauHoiUseCase layCauHoiUseCase = new LayCauHoiUseCase(repoCauHoi);
        NopBaiUseCase nopBaiUseCase = new NopBaiUseCase(repoKetQua);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new QuizViewModel(layCauHoiUseCase, db.baiLamTamDao(), nopBaiUseCase);
            }
        }).get(QuizViewModel.class);
    }

    private void observeViewModel() {
        TokenManager tm = new TokenManager(this);
        viewModel.loadQuestions(tm.layId(), idBkt);
        viewModel.loadSavedAnswers();

        viewModel.getQuestions(idBkt).observe(this, questions -> {
            if (questions != null && !questions.isEmpty()) {
                viewModel.setQuestionList(questions);
                updateUI(questions.get(viewModel.getCurrentIndex().getValue()));
            }
        });

        viewModel.getCurrentIndex().observe(this, index -> {
            List<CauHoi> questions = viewModel.getQuestionList();
            if (questions != null && index < questions.size()) {
                updateUI(questions.get(index));
            }
        });

        viewModel.getRemainingTime().observe(this, time -> tvTimer.setText(time));

        viewModel.getSubmissionResult().observe(this, ketQua -> {
            if (ketQua != null) {
                if ("success".equals(ketQua.getStatus())) {
                    Intent intent = new Intent(QuizActivity.this, KetQuaActivity.class);
                    intent.putExtra("ID_KET_QUA", ketQua.getIdKetQua());
                    startActivity(intent);
                    finish();
                } else {
                    DialogHelper.showNotificationDialog(
                            this,
                            "Thông báo",
                            "Nộp bài thất bại: " + ketQua.getStatus(),
                            "Đóng",
                            null
                    );
                }
            } else {
                DialogHelper.showNotificationDialog(
                        this,
                        "Lỗi kết nối",
                        "Không thể kết nối đến máy chủ. Vui lòng kiểm tra lại mạng mạng.",
                        "Thử lại",
                        null
                );
            }
        });

        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                layoutLoading.setVisibility(View.VISIBLE);
            } else {
                layoutLoading.setVisibility(View.GONE);
            }
        });

        viewModel.getValidationError().observe(this, errorMessage -> {
            if (errorMessage != null) {
                DialogHelper.showNotificationDialog(
                        this,
                        "Thông báo",
                        errorMessage,
                        "Làm bài tiếp",
                        null
                );
            }
        });
    }

    private void setupRadioGroupListener() {
        rgAnswers.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == -1) return;

            int currentIdx = viewModel.getCurrentIndex().getValue();
            List<CauHoi> questions = viewModel.getQuestionList();

            if (questions != null && currentIdx < questions.size()) {
                int idCauHoi = questions.get(currentIdx).getId();
                String answer = "";
                if (checkedId == R.id.rbOption1) answer = "a";
                else if (checkedId == R.id.rbOption2) answer = "b";
                else if (checkedId == R.id.rbOption3) answer = "c";
                else if (checkedId == R.id.rbOption4) answer = "d";

                viewModel.saveAnswer(idCauHoi, answer);
            }
        });
    }

    private void updateUI(CauHoi cauHoi) {
        if (cauHoi == null) return;

        int currentPos = viewModel.getCurrentIndex().getValue() != null ? viewModel.getCurrentIndex().getValue() : 0;
        String displayContent = "Câu " + (currentPos + 1) + ": " + cauHoi.getNoiDung();
        tvQuestionText.setText(displayContent);

        rbA.setText(cauHoi.getCauA());
        rbB.setText(cauHoi.getCauB());
        rbC.setText(cauHoi.getCauC());
        rbD.setText(cauHoi.getCauD());

        // Bước 1: Ngắt listener để việc clearCheck không vô tình kích hoạt lưu dữ liệu trống
        rgAnswers.setOnCheckedChangeListener(null);

        // Bước 2: Xóa sạch trạng thái cũ
        rgAnswers.clearCheck();

        // Bước 3: Kiểm tra xem câu này đã có đáp án lưu trong ViewModel chưa
        Map<Integer, String> answers = viewModel.getUserAnswers().getValue();
        if (answers != null && answers.containsKey(cauHoi.getId())) {
            String savedAnswer = answers.get(cauHoi.getId());
            switch (savedAnswer) {
                case "a": rbA.setChecked(true); break;
                case "b": rbB.setChecked(true); break;
                case "c": rbC.setChecked(true); break;
                case "d": rbD.setChecked(true); break;
            }
        }
        setupRadioGroupListener();
    }

    private void showQuestionListDialog() {
        com.google.android.material.bottomsheet.BottomSheetDialog dialog =
                new com.google.android.material.bottomsheet.BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_questions, null);

        androidx.recyclerview.widget.RecyclerView rvNumbers = view.findViewById(R.id.rvQuestionNumbers);
        QuestionNumberAdapter adapter = new QuestionNumberAdapter();

        List<CauHoi> questions = viewModel.getQuestionList();
        int total = (questions != null) ? questions.size() : 0;
        int current = viewModel.getCurrentIndex().getValue();

        List<Integer> answeredIndices = new ArrayList<>();
        Map<Integer, String> answers = viewModel.getUserAnswers().getValue();

        if (questions != null && answers != null) {
            for (int i = 0; i < questions.size(); i++) {
                if (answers.containsKey(questions.get(i).getId())) {
                    answeredIndices.add(i);
                }
            }
        }

        rvNumbers.setLayoutManager(new androidx.recyclerview.widget.GridLayoutManager(this, 5));
        rvNumbers.setAdapter(adapter);
        adapter.setData(total, current, answeredIndices);

        adapter.setOnItemClickListener(position -> {
            viewModel.setCurrentIndex(position);
            dialog.dismiss();
        });

        dialog.setContentView(view);
        dialog.show();
    }
}