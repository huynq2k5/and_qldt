package vn.huy.quanlydaotao.ui.video;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;
import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.domain.model.BaiHoc;

public class VideoPlayerActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private List<BaiHoc> videoLessons;
    private VideoSwipeAdapter adapter;

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

        if (videoLessons != null) {
            adapter = new VideoSwipeAdapter(videoLessons);
            viewPager2.setAdapter(adapter);

            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    handleVideoPlayback(position);
                }
            });
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
        if (viewPager2 != null) {
            viewPager2.setAdapter(null);
        }
    }
}