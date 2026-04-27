package vn.huy.quanlydaotao.ui.lophoc;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;
import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.domain.model.LopHoc;

public class LopHocAdapter extends RecyclerView.Adapter<LopHocAdapter.ViewHolder> {

    private List<LopHoc> items = new ArrayList<>();
    private OnLopHocClickListener listener;

    public interface OnLopHocClickListener {
        void onLopHocClick(LopHoc lopHoc);
    }

    public void setOnLopHocClickListener(OnLopHocClickListener listener) {
        this.listener = listener;
    }

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

        if (item.getDaDangKy() == 1) {
            holder.btnAction.setText("Vào học ngay");
            holder.btnAction.setBackgroundColor(Color.parseColor("#1E3A8A"));
        } else {
            holder.btnAction.setText("Đăng ký lớp");
            holder.btnAction.setBackgroundColor(Color.parseColor("#2eb85c"));
        }

        holder.btnAction.setOnClickListener(v -> {
            if (listener != null) {
                listener.onLopHocClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenLop, tvThoiGian;
        MaterialButton btnAction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenLop = itemView.findViewById(R.id.tvTenLop);
            tvThoiGian = itemView.findViewById(R.id.tvThoiGian);
            btnAction = itemView.findViewById(R.id.btnVaoHoc);
        }
    }
}