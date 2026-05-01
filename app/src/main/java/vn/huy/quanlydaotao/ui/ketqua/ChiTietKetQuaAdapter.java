package vn.huy.quanlydaotao.ui.ketqua;

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
import vn.huy.quanlydaotao.domain.model.ChiTietKetQua;

public class ChiTietKetQuaAdapter extends RecyclerView.Adapter<ChiTietKetQuaAdapter.ViewHolder> {

    private List<ChiTietKetQua.CauHoiChiTiet> list = new ArrayList<>();

    public void setData(List<ChiTietKetQua.CauHoiChiTiet> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chi_tiet_cau_hoi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChiTietKetQua.CauHoiChiTiet item = list.get(position);
        holder.tvSttCauHoi.setText("Câu hỏi " + (position + 1));
        holder.tvNoiDungCauHoi.setText(item.getNoiDung());
        holder.tvDapAnSinhVien.setText("Bạn chọn: " + item.getCauTraLoi());
        holder.tvDapAnDung.setText("Đáp án đúng: " + item.getDapAnDung());

        if (item.getLaDapAn() == 1) {
            holder.tvDapAnSinhVien.setTextColor(Color.parseColor("#4CAF50"));
        } else {
            holder.tvDapAnSinhVien.setTextColor(Color.parseColor("#F44336"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSttCauHoi, tvNoiDungCauHoi, tvDapAnSinhVien, tvDapAnDung;

        ViewHolder(View itemView) {
            super(itemView);
            tvSttCauHoi = itemView.findViewById(R.id.tvSttCauHoi);
            tvNoiDungCauHoi = itemView.findViewById(R.id.tvNoiDungCauHoi);
            tvDapAnSinhVien = itemView.findViewById(R.id.tvDapAnSinhVien);
            tvDapAnDung = itemView.findViewById(R.id.tvDapAnDung);
        }
    }
}