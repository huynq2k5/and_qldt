package vn.huy.quanlydaotao.ui.hoctructuyen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import vn.huy.quanlydaotao.R;

public class MusicAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] songNames;
    private int selectedPosition = -1;
    private boolean isPlaying = false; // Biến cờ nhận diện trạng thái phát ngầm của bài hát

    public MusicAdapter(@NonNull Context context, String[] songNames) {
        super(context, R.layout.item_music, songNames);
        this.context = context;
        this.songNames = songNames;
    }

    // Cập nhật hàm này để nhận thêm trạng thái phát nhạc từ Service truyền sang
    public void updatePlayingState(int position, boolean isPlaying) {
        this.selectedPosition = position;
        this.isPlaying = isPlaying;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_music, parent, false);
        }

        TextView tvSongName = convertView.findViewById(R.id.tvSongName);
        ImageView ivPlayStatus = convertView.findViewById(R.id.ivPlayStatus);

        if (tvSongName != null) {
            tvSongName.setText(songNames[position]);
        }

        if (ivPlayStatus != null) {
            // Chỉ hiển thị nút Pause ĐỎ khi trùng vị trí VÀ nhạc ĐANG PHÁT thực tế
            if (position == selectedPosition && isPlaying) {
                ivPlayStatus.setImageResource(android.R.drawable.ic_media_pause);
                ivPlayStatus.setColorFilter(0xFFEF4444); // Màu đỏ
            } else {
                ivPlayStatus.setImageResource(android.R.drawable.ic_media_play);
                ivPlayStatus.setColorFilter(0xFF10B981); // Màu xanh lá
            }
        }

        return convertView;
    }
}