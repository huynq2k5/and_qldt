package vn.huy.quanlydaotao.ui.canhan;

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
import vn.huy.quanlydaotao.domain.model.ChungChi;

public class ChungChiAdapter extends RecyclerView.Adapter<ChungChiAdapter.ViewHolder> {

    private List<ChungChi> list = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ChungChi item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<ChungChi> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chung_chi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChungChi item = list.get(position);
        
        holder.tvTenKhoaHoc.setText(item.getTenKhoaHoc());
        holder.tvMaQr.setText("Mã xác thực: " + item.getMaQr());
        holder.tvNgayCap.setText("Ngày cấp: " + item.getNgayCap());

        holder.btnXemAnh.setOnClickListener(v -> {
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
        TextView tvTenKhoaHoc, tvMaQr, tvNgayCap;
        MaterialButton btnXemAnh;

        ViewHolder(View itemView) {
            super(itemView);
            tvTenKhoaHoc = itemView.findViewById(R.id.tvTenKhoaHocChungChi);
            tvMaQr = itemView.findViewById(R.id.tvMaQrChungChi);
            tvNgayCap = itemView.findViewById(R.id.tvNgayCapChungChi);
            btnXemAnh = itemView.findViewById(R.id.btnXemChungChiGoc);
        }
    }
}