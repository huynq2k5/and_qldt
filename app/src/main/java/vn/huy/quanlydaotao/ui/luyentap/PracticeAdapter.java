package vn.huy.quanlydaotao.ui.luyentap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.domain.model.BaiKiemTra;

public class PracticeAdapter extends RecyclerView.Adapter<PracticeAdapter.ViewHolder> {

    private List<BaiKiemTra> items = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(BaiKiemTra item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<BaiKiemTra> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_topic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiKiemTra item = items.get(position);

        // Hiển thị tiêu đề bài kiểm tra
        holder.tvTitle.setText(item.getTieuDe());

        // Hiển thị tên môn học và thời gian làm bài
        String desc = "Môn: " + item.getTenKhoaHoc() + " | " + item.getThoiGianLam() + " phút";
        holder.tvDesc.setText(desc);

        // Sử dụng icon mặc định cho bài kiểm tra
        holder.imgIcon.setImageResource(R.drawable.graduation_cap);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView tvTitle, tvDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgTopicIcon);
            tvTitle = itemView.findViewById(R.id.tvTopicTitle);
            tvDesc = itemView.findViewById(R.id.tvTopicDesc);
        }
    }
}