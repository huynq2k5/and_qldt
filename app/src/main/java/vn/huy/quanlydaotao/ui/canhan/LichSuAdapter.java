package vn.huy.quanlydaotao.ui.canhan;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.domain.model.LichSuKT;

public class LichSuAdapter extends RecyclerView.Adapter<LichSuAdapter.ViewHolder> {

    private List<LichSuKT> list = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(LichSuKT item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<LichSuKT> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lich_su, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LichSuKT item = list.get(position);
        
        holder.tvMaBaiKiemTra.setText("Mã bài thi: #" + item.getIdBaiKiemTra());
        holder.tvDiemSo.setText("Điểm số: " + item.getDiemSo());
        holder.tvThoiGianNop.setText("Thời gian nộp: " + item.getThoiGianNop());

        if ("dat".equals(item.getTrangThai())) {
            holder.tvTrangThai.setText("ĐẠT");
            holder.tvTrangThai.setTextColor(Color.parseColor("#4CAF50"));
        } else {
            holder.tvTrangThai.setText("KHÔNG ĐẠT");
            holder.tvTrangThai.setTextColor(Color.parseColor("#F44336"));
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaBaiKiemTra, tvTrangThai, tvDiemSo, tvThoiGianNop;

        ViewHolder(View itemView) {
            super(itemView);
            tvMaBaiKiemTra = itemView.findViewById(R.id.tvMaBaiKiemTra);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
            tvDiemSo = itemView.findViewById(R.id.tvDiemSo);
            tvThoiGianNop = itemView.findViewById(R.id.tvThoiGianNop);
        }
    }
}