package vn.huy.quanlydaotao.ui.video;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import java.util.List;
import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.domain.model.BaiHoc;

public class VideoSwipeAdapter extends RecyclerView.Adapter<VideoSwipeAdapter.VideoViewHolder> {

    private List<BaiHoc> videoList;

    public VideoSwipeAdapter(List<BaiHoc> videoList) {
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_container, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.setData(videoList.get(position));
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        StyledPlayerView playerView;
        TextView tvTitle, tvDesc;
        ProgressBar progressBar;
        ExoPlayer exoPlayer;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            playerView = itemView.findViewById(R.id.playerView);
            tvTitle = itemView.findViewById(R.id.tvVideoTitle);
            tvDesc = itemView.findViewById(R.id.tvVideoDesc);
            progressBar = itemView.findViewById(R.id.videoProgressBar);
        }

        void setData(BaiHoc baiHoc) {
            tvTitle.setText(baiHoc.getTieuDe());
            tvDesc.setText("Bài học video");

            if (exoPlayer != null) {
                exoPlayer.release();
            }

            exoPlayer = new ExoPlayer.Builder(itemView.getContext()).build();
            playerView.setPlayer(exoPlayer);

            // Cấu hình Controller
            playerView.setUseController(true);
            playerView.setControllerAutoShow(false); // Không tự hiện khi mới vào
            playerView.setControllerShowTimeoutMs(3000); // Tự ẩn sau 3 giây

            MediaItem mediaItem = MediaItem.fromUri(baiHoc.getDuongDanTep());
            exoPlayer.setMediaItem(mediaItem);
            exoPlayer.prepare();
            exoPlayer.setRepeatMode(Player.REPEAT_MODE_ONE);

            // LOGIC CHẠM 2 BƯỚC
            playerView.setOnClickListener(v -> {
                if (!playerView.isControllerFullyVisible()) {
                    // CHẠM LẦN 1: Nếu controller đang ẩn -> chỉ hiện nó lên
                    playerView.showController();
                } else {
                    // CHẠM LẦN 2: Nếu controller đang hiện -> mới thực hiện Play/Pause
                    if (exoPlayer.isPlaying()) {
                        exoPlayer.pause();
                    } else {
                        exoPlayer.play();
                    }
                }
            });

            exoPlayer.addListener(new Player.Listener() {
                @Override
                public void onPlaybackStateChanged(int playbackState) {
                    if (playbackState == Player.STATE_BUFFERING) {
                        progressBar.setVisibility(View.VISIBLE);
                    } else {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }

        public void playVideo() {
            if (exoPlayer != null) {
                exoPlayer.play();
            }
        }

        public void pauseVideo() {
            if (exoPlayer != null) {
                exoPlayer.pause();
            }
        }
    }
}