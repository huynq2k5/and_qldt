package vn.huy.quanlydaotao.ui.home;

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
import vn.huy.quanlydaotao.domain.model.KhoaHoc;

public class HomeCourseAdapter extends RecyclerView.Adapter<HomeCourseAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(KhoaHoc khoaHoc);
    }

    private List<KhoaHoc> items = new ArrayList<>();
    private OnItemClickListener listener;

    public void setItems(List<KhoaHoc> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KhoaHoc item = items.get(position);
        holder.tvTitle.setText(item.getTenKhoaHoc());
        holder.tvInfo.setText(item.getNgayTao());
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
        TextView tvTitle, tvInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgHomeCourse);
            tvTitle = itemView.findViewById(R.id.tvHomeCourseTitle);
            tvInfo = itemView.findViewById(R.id.tvHomeCourseInfo);
        }
    }
}
