package vn.huy.quanlydaotao.ui.luyentap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.domain.model.BaiKiemTra;

public class PracticeAdapter extends RecyclerView.Adapter<PracticeAdapter.ViewHolder> {
    private List<BaiKiemTra> items = new ArrayList<>();
    private OnPracticeClickListener listener;

    public interface OnPracticeClickListener {
        void onStart(BaiKiemTra item);
        void onViewResult(int idKetQua);
    }

    public void setOnPracticeClickListener(OnPracticeClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<BaiKiemTra> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bai_kiem_tra, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiKiemTra item = items.get(position);

        holder.tvTitle.setText(item.getTieuDe());
        holder.tvDesc.setText("Môn: " + item.getTenKhoaHoc() + " | " + item.getThoiGianLam() + " phút");

        if (item.getIdKetQua() != null && item.getIdKetQua() > 0) {
            holder.btnViewResult.setVisibility(View.VISIBLE);
            holder.btnStartQuiz.setText("Thi lại");
        } else {
            holder.btnViewResult.setVisibility(View.GONE);
            holder.btnStartQuiz.setText("Vào thi");
        }

        holder.btnStartQuiz.setOnClickListener(v -> {
            if (listener != null) listener.onStart(item);
        });

        holder.btnViewResult.setOnClickListener(v -> {
            if (listener != null) listener.onViewResult(item.getIdKetQua());
        });
    }

    @Override
    public int getItemCount() { return items.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc;
        MaterialButton btnStartQuiz, btnViewResult;
        ImageView imgIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTopicTitle);
            tvDesc = itemView.findViewById(R.id.tvTopicDesc);
            btnStartQuiz = itemView.findViewById(R.id.btnStartQuiz);
            btnViewResult = itemView.findViewById(R.id.btnViewResult);
            imgIcon = itemView.findViewById(R.id.imgTopicIcon);
        }
    }
}