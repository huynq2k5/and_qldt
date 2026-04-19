package vn.huy.quanlydaotao.ui.hoctructuyen;

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
import vn.huy.quanlydaotao.domain.model.LichMeet;

public class LichMeetAdapter extends RecyclerView.Adapter<LichMeetAdapter.ViewHolder> {

    private List<LichMeet> items = new ArrayList<>();
    private OnBtnJoinClickListener listener;

    public interface OnBtnJoinClickListener {
        void onJoinClick(String link);
    }

    public void setOnBtnJoinClickListener(OnBtnJoinClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<LichMeet> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lich_meet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LichMeet item = items.get(position);
        holder.tvTitle.setText(item.getTieuDe());

        String rawTime = item.getThoiGian();
        if (rawTime != null && rawTime.length() >= 16) {
            String hour = rawTime.substring(11, 16);
            String date = rawTime.substring(8, 10) + "/" + rawTime.substring(5, 7);

            holder.tvMeetHour.setText(hour);
            holder.tvMeetDate.setText(date);
        }

        holder.btnJoin.setOnClickListener(v -> {
            if (listener != null) {
                listener.onJoinClick(item.getLinkMeet());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvMeetHour, tvMeetDate;
        TextView btnJoin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvMeetTitle);
            tvMeetHour = itemView.findViewById(R.id.tvMeetHour);
            tvMeetDate = itemView.findViewById(R.id.tvMeetDate);
            btnJoin = itemView.findViewById(R.id.btnJoinMeet);
        }
    }
}