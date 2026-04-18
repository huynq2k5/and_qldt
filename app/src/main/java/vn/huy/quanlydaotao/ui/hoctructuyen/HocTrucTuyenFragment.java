package vn.huy.quanlydaotao.ui.hoctructuyen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import vn.huy.quanlydaotao.R;

public class HocTrucTuyenFragment extends Fragment {

    public HocTrucTuyenFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hoc_truc_tuyen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View header = view.findViewById(R.id.layoutHeader);

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());

            if (header != null) {
                int pLeft = header.getPaddingLeft();
                int pRight = header.getPaddingRight();
                int pBottom = header.getPaddingBottom();

                header.setPadding(pLeft, systemBars.top + 20, pRight, pBottom);
            }

            return WindowInsetsCompat.CONSUMED; // Trả về CONSUMED để báo đã xử lý xong
        });
        setupData(view);
    }

    private void setupData(View view) {

    }
}