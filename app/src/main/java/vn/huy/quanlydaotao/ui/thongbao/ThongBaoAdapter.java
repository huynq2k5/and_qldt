package vn.huy.quanlydaotao.ui.thongbao;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.domain.model.ThongBao;

public class ThongBaoAdapter extends RecyclerView.Adapter<ThongBaoAdapter.ViewHolder> {

    private List<ThongBao> items = new ArrayList<>();
    private OnThongBaoClickListener listener;

    public interface OnThongBaoClickListener {
        void onThongBaoClick(ThongBao thongBao);
    }

    public void setOnThongBaoClickListener(OnThongBaoClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<ThongBao> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thong_bao, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThongBao item = items.get(position);
        holder.tvTitle.setText(item.getTieuDe());
        holder.tvContent.setText(item.getNoiDung());
        holder.tvTime.setText(item.getThoiGian());

        if (item.getDaXem() == 1) {
            holder.viewStatus.setVisibility(View.INVISIBLE);
        } else {
            holder.viewStatus.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onThongBaoClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent, tvTime;
        View viewStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvTime = itemView.findViewById(R.id.tvTime);
            viewStatus = itemView.findViewById(R.id.viewStatus);
        }
    }
}