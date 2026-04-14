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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHoc item = items.get(position);
        holder.tvTieuDe.setText(item.getTieuDe());
        if ("video".equals(item.getLoaiNoiDung())) {
            holder.imgType.setImageResource(R.drawable.play_circle);
            holder.tvLoai.setText("Video bài giảng");
        } else {
            holder.imgType.setImageResource(R.drawable.books);
            holder.tvLoai.setText("Tài liệu PDF");
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