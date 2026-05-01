package vn.huy.quanlydaotao.ui.ketqua;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.airbnb.lottie.LottieAnimationView;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.xml.KonfettiView;
import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.local.CoSoDuLieuApp;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.api.RetrofitClient;
import vn.huy.quanlydaotao.data.repository.ChiTietKetQuaRepositoryImpl;
import vn.huy.quanlydaotao.domain.model.ChiTietKetQua;
import vn.huy.quanlydaotao.domain.usecase.LayKetQuaUseCase;

public class KetQuaActivity extends AppCompatActivity {

    private LottieAnimationView lottieResult;
    private TextView tvFinalScore, tvStatusLabel, tvTimeNop;
    private KonfettiView konfettiView;
    private KetQuaViewModel viewModel;
    private int idKetQua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ket_qua);
        View mainView = findViewById(android.R.id.content);
        androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
            androidx.core.graphics.Insets systemBars = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        androidx.core.view.WindowInsetsControllerCompat windowInsetsController = androidx.core.view.WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());

        windowInsetsController.setAppearanceLightStatusBars(false);

        idKetQua = getIntent().getIntExtra("ID_KET_QUA", 0);

        mappingWidgets();
        setupViewModel();
        observeViewModel();
    }

    private void mappingWidgets() {
        lottieResult = findViewById(R.id.lottieResult);
        tvFinalScore = findViewById(R.id.tvFinalScore);
        tvStatusLabel = findViewById(R.id.tvStatusLabel);
        tvTimeNop = findViewById(R.id.tvTimeNop);
        konfettiView = findViewById(R.id.konfettiView);

        findViewById(R.id.btnFinishResult).setOnClickListener(v -> finish());

        lottieResult.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
                lottieResult.postDelayed(() -> lottieResult.playAnimation(), 3000);
            }
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        findViewById(R.id.btnViewDetail).setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(KetQuaActivity.this, ChiTietKetQuaActivity.class);
            intent.putExtra("ID_KET_QUA", idKetQua);
            startActivity(intent);
        });
    }

    private void setupViewModel() {
        DichVuApi api = RetrofitClient.getClient().create(DichVuApi.class);
        CoSoDuLieuApp db = CoSoDuLieuApp.getInstance(this);
        ChiTietKetQuaRepositoryImpl repo = new ChiTietKetQuaRepositoryImpl(db.chiTietKetQuaDao(), api);
        LayKetQuaUseCase useCase = new LayKetQuaUseCase(repo);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new KetQuaViewModel(useCase);
            }
        }).get(KetQuaViewModel.class);
    }

    private void observeViewModel() {
        if (idKetQua <= 0) return;

        viewModel.xemKetQua(idKetQua).observe(this, ketQua -> {
            if (ketQua != null) {
                updateUI(ketQua);
            }
        });
    }

    private void updateUI(ChiTietKetQua ketQua) {
        double score = ketQua.getDiemSo();
        tvFinalScore.setText(score == (long) score ? String.format("%d", (long) score) : String.format("%.1f", score));
        try {
            java.text.SimpleDateFormat sdfInput = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
            java.text.SimpleDateFormat sdfOutput = new java.text.SimpleDateFormat("HH:mm:ss - dd/MM/yyyy", java.util.Locale.getDefault());
            java.util.Date date = sdfInput.parse(ketQua.getThoiGianNop());
            String formattedDate = (date != null) ? sdfOutput.format(date) : ketQua.getThoiGianNop();
            tvTimeNop.setText("Thời gian nộp: " + formattedDate);
        } catch (Exception e) {
            tvTimeNop.setText("Thời gian nộp: " + ketQua.getThoiGianNop());
        }

        if ("dat".equals(ketQua.getTrangThai())) {
            tvStatusLabel.setText("CHÚC MỪNG BẠN ĐÃ THI ĐẠT");
            tvStatusLabel.setTextColor(Color.parseColor("#4CAF50"));
            lottieResult.setAnimation(R.raw.lottie_success);
            triggerConfetti();
        } else {
            tvStatusLabel.setText("CHƯA ĐẠT");
            tvStatusLabel.setTextColor(Color.parseColor("#F44336"));
            lottieResult.setAnimation(R.raw.lottie_fail);
        }
        lottieResult.playAnimation();
    }

    private void shootFirework(double x, double y) {
        EmitterConfig emitterConfig = new Emitter(100L, TimeUnit.MILLISECONDS).max(100);
        Party party = new PartyFactory(emitterConfig)
                .spread(360)
                .setSpeedBetween(15f, 40f)
                .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                .position(x, y)
                .build();
        konfettiView.start(party);
    }

    private void triggerConfetti() {
        shootFirework(0.5, 0.3);
        konfettiView.postDelayed(() -> shootFirework(0.2, 0.4), 800);
        konfettiView.postDelayed(() -> shootFirework(0.8, 0.4), 1600);
    }
}