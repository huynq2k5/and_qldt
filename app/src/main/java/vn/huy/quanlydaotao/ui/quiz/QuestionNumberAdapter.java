package vn.huy.quanlydaotao.ui.quiz;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import vn.huy.quanlydaotao.R;

public class QuestionNumberAdapter extends RecyclerView.Adapter<QuestionNumberAdapter.ViewHolder> {

    private int totalQuestions = 0;
    private int currentIndex = 0;
    private List<Integer> answeredQuestions = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setData(int total, int current, List<Integer> answered) {
        this.totalQuestions = total;
        this.currentIndex = current;
        this.answeredQuestions = answered;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_number, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNumber.setText(String.valueOf(position + 1));

        if (position == currentIndex) {
            holder.cvNumber.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.primary));
            holder.tvNumber.setTextColor(Color.WHITE);
            holder.cvNumber.setStrokeWidth(0);
        } else if (answeredQuestions.contains(position)) {
            holder.cvNumber.setCardBackgroundColor(Color.parseColor("#E8F5E9"));
            holder.tvNumber.setTextColor(Color.parseColor("#4CAF50"));
            holder.cvNumber.setStrokeColor(Color.parseColor("#4CAF50"));
            holder.cvNumber.setStrokeWidth(2);
        } else {
            holder.cvNumber.setCardBackgroundColor(Color.WHITE);
            holder.tvNumber.setTextColor(Color.parseColor("#757575"));
            holder.cvNumber.setStrokeColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.primary));
            holder.cvNumber.setStrokeWidth(2);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return totalQuestions;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumber;
        MaterialCardView cvNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            cvNumber = itemView.findViewById(R.id.cvNumber);
        }
    }
}