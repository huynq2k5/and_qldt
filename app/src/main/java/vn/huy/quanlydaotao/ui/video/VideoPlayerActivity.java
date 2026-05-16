package vn.huy.quanlydaotao.ui.video;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;
import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.local.CoSoDuLieuApp;
import vn.huy.quanlydaotao.data.local.TokenManager;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.api.RetrofitClient;
import vn.huy.quanlydaotao.data.repository.TienDoRepositoryImpl;
import vn.huy.quanlydaotao.domain.model.BaiHoc;
import vn.huy.quanlydaotao.domain.usecase.CapNhatTienDoUseCase;

public class VideoPlayerActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private List<BaiHoc> videoLessons;
    private VideoSwipeAdapter adapter;
    private TokenManager tokenManager;
    private CapNhatTienDoUseCase capNhatTienDoUseCase;
    private final Handler progressHandler = new Handler(Looper.getMainLooper());
    private Runnable progressRunnable;
    private int currentPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        setContentView(R.layout.activity_video_player);

        viewPager2 = findViewById(R.id.viewPagerVideo);
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        videoLessons = (ArrayList<BaiHoc>) getIntent().getSerializableExtra("danh_sach_video");

        setupDependencies();

        if (videoLessons != null) {
            adapter = new VideoSwipeAdapter(videoLessons);
            viewPager2.setAdapter(adapter);
            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    handleVideoPlayback(position);
                    startTimeTracking(position);
                }
            });
        }
    }

    private void setupDependencies() {
        tokenManager = new TokenManager(this);
        DichVuApi api = RetrofitClient.getClient().create(DichVuApi.class);
        TienDoRepositoryImpl repo = new TienDoRepositoryImpl(
                CoSoDuLieuApp.getInstance(this).tienDoDao(), api);
        capNhatTienDoUseCase = new CapNhatTienDoUseCase(repo);
    }

    private void startTimeTracking(int position) {
        stopTimeTracking();
        currentPosition = position;
        progressRunnable = () -> {
            if (videoLessons != null && currentPosition >= 0 && currentPosition < videoLessons.size()) {
                int idNguoiDung = tokenManager.layId();
                int idBaiHoc = videoLessons.get(currentPosition).getId();
                capNhatTienDoUseCase.thucHienCapNhat(idNguoiDung, idBaiHoc, 100).observe(VideoPlayerActivity.this, response -> {});
            }
        };
        progressHandler.postDelayed(progressRunnable, 10000);
    }

    private void stopTimeTracking() {
        if (progressRunnable != null) {
            progressHandler.removeCallbacks(progressRunnable);
        }
    }

    private void handleVideoPlayback(int position) {
        View child = viewPager2.getChildAt(0);
        if (child instanceof RecyclerView) {
            RecyclerView rv = (RecyclerView) child;
            for (int i = 0; i < adapter.getItemCount(); i++) {
                VideoSwipeAdapter.VideoViewHolder holder =
                        (VideoSwipeAdapter.VideoViewHolder) rv.findViewHolderForAdapterPosition(i);
                if (holder != null) {
                    if (i == position) {
                        holder.playVideo();
                    } else {
                        holder.pauseVideo();
                    }
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseAllVideos();
        stopTimeTracking();
    }

    @Override
    protected void onStop() {
        super.onStop();
        pauseAllVideos();
    }

    private void pauseAllVideos() {
        View child = viewPager2.getChildAt(0);
        if (child instanceof RecyclerView) {
            RecyclerView rv = (RecyclerView) child;
            for (int i = 0; i < adapter.getItemCount(); i++) {
                VideoSwipeAdapter.VideoViewHolder holder =
                        (VideoSwipeAdapter.VideoViewHolder) rv.findViewHolderForAdapterPosition(i);
                if (holder != null) {
                    holder.pauseVideo();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimeTracking();
        if (viewPager2 != null) {
            viewPager2.setAdapter(null);
        }
    }
}