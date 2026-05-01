package vn.huy.quanlydaotao.ui.baihoc;

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
import vn.huy.quanlydaotao.domain.model.BaiHoc;

public class BaiHocAdapter extends RecyclerView.Adapter<BaiHocAdapter.ViewHolder> {
    private List<BaiHoc> items = new ArrayList<>();

    public void setItems(List<BaiHoc> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bai_hoc, parent, false);
        return new ViewHolder(view);
    }

    public interface OnBaiHocClickListener {
        void onVideoGroupClick(List<BaiHoc> allVideos);
        void onPdfClick(BaiHoc pdfLesson);
    }

    private OnBaiHocClickListener listener;

    public void setOnBaiHocClickListener(OnBaiHocClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHoc item = items.get(position);
        holder.tvTieuDe.setText(item.getTieuDe());

        if ("video".equals(item.getLoaiNoiDung())) {
            holder.imgType.setImageResource(R.drawable.play_circle);
            holder.tvLoai.setText("Danh sách video bài giảng");

            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    List<BaiHoc> videos = new ArrayList<>();
                    for (BaiHoc bh : items) {
                        if ("video".equals(bh.getLoaiNoiDung())) videos.add(bh);
                    }
                    listener.onVideoGroupClick(videos);
                }
            });
        } else {
            holder.imgType.setImageResource(R.drawable.books);
            holder.tvLoai.setText("Tài liệu PDF");

            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    // Chỉ cần gọi callback này cho PDF
                    listener.onPdfClick(item);
                }
            });
        }
    }

    @Override
    public int getItemCount() { return items.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTieuDe, tvLoai;
        ImageView imgType;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTieuDe = itemView.findViewById(R.id.tvTieuDe);
            tvLoai = itemView.findViewById(R.id.tvLoai);
            imgType = itemView.findViewById(R.id.imgType);
        }
    }
}