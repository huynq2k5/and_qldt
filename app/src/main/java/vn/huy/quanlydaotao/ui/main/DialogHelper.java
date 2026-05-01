package vn.huy.quanlydaotao.ui.main;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import vn.huy.quanlydaotao.R;

public class DialogHelper {

    public interface DialogCallback {
        void onConfirmed();
    }

    public static void showConfirmDialog(
            Context context,
            String title,
            String message,
            String positiveText,
            String negativeText,
            DialogCallback callback) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_center, null);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        TextView tvTitle = view.findViewById(R.id.tvDialogTitle);
        TextView tvMessage = view.findViewById(R.id.tvDialogMessage);
        AppCompatButton btnNeg = view.findViewById(R.id.btnNegative);
        AppCompatButton btnPos = view.findViewById(R.id.btnPositive);

        tvTitle.setText(title);
        tvMessage.setText(message);
        btnPos.setText(positiveText);
        btnNeg.setText(negativeText);

        btnNeg.setOnClickListener(v -> dialog.dismiss());

        btnPos.setOnClickListener(v -> {
            dialog.dismiss();
            if (callback != null) {
                callback.onConfirmed();
            }
        });

        dialog.show();
    }
    public static void showNotificationDialog(
            Context context,
            String title,
            String message,
            String buttonText,
            DialogCallback callback) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_info, null);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        TextView tvTitle = view.findViewById(R.id.tvDialogTitle);
        TextView tvMessage = view.findViewById(R.id.tvDialogMessage);
        AppCompatButton btnPos = view.findViewById(R.id.btnPositive);

        tvTitle.setText(title);
        tvMessage.setText(message);
        btnPos.setText(buttonText);

        btnPos.setOnClickListener(v -> {
            dialog.dismiss();
            if (callback != null) {
                callback.onConfirmed();
            }
        });

        dialog.show();
    }
}