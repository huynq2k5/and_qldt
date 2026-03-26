package vn.huy.quanlydaotao.ui.lophoc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.domain.model.LopHoc;

public class LopHocAdapter extends RecyclerView.Adapter<LopHocAdapter.ViewHolder> {

    private List<LopHoc> items = new ArrayList<>();

    public void setItems(List<LopHoc> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lop_hoc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LopHoc item = items.get(position);
        holder.tvTenLop.setText(item.getTenLop());
        holder.tvThoiGian.setText(item.getNgayBatDau() + " - " + item.getNgayKetThuc());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenLop, tvThoiGian;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenLop = itemView.findViewById(R.id.tvTenLop);
            tvThoiGian = itemView.findViewById(R.id.tvThoiGian);
        }
    }
}
