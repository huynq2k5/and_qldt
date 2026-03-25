package vn.huy.quanlydaotao.ui.canhan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.huy.quanlydaotao.R;

public class CaNhanFragment extends Fragment {

    public CaNhanFragment() {
        // Required empty public constructor
    }

    public static CaNhanFragment newInstance() {
        return new CaNhanFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ca_nhan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupProfile(view);
    }

    private void setupProfile(View view) {
        TextView tvProfileName = view.findViewById(R.id.tvProfileName);
        TextView tvProfileEmail = view.findViewById(R.id.tvProfileEmail);

        if (tvProfileName != null) {
            tvProfileName.setText("Huy Nguyễn");
        }
        if (tvProfileEmail != null) {
            tvProfileEmail.setText("huy.nguyen@daotao.vn");
        }

        // Setup click listeners for demo
        View btnEdit = view.findViewById(R.id.imgProfileAvatar); // Just as example
        if (btnEdit != null) {
            btnEdit.setOnClickListener(v -> 
                Toast.makeText(getContext(), "Tính năng chỉnh sửa đang phát triển", Toast.LENGTH_SHORT).show()
            );
        }
    }
}
