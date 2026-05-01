package vn.huy.quanlydaotao.ui.canhan;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.local.CoSoDuLieuApp;
import vn.huy.quanlydaotao.data.local.TokenManager;
import vn.huy.quanlydaotao.ui.login.LoginActivity;
import vn.huy.quanlydaotao.ui.main.DialogHelper;

public class CaNhanFragment extends Fragment {

    private TokenManager tokenManager;
    private LinearLayout btnEditProfile;

    public CaNhanFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ca_nhan, container, false);
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
        tokenManager = new TokenManager(requireContext());
        setupProfile(view);
    }

    private void setupProfile(View view) {
        TextView tvProfileName = view.findViewById(R.id.tvProfileName);
        TextView tvProfileEmail = view.findViewById(R.id.tvProfileEmail);
        View btnLogout = view.findViewById(R.id.btnLogout);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(v -> { startActivity(new Intent(getContext(), EditProfileActivity.class)); });

        if (tvProfileName != null) tvProfileName.setText(tokenManager.layHoTen());
        if (tvProfileEmail != null) tvProfileEmail.setText(tokenManager.layEmail());

        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> xacNhanDangXuat());
        }
    }

    private void xacNhanDangXuat() {
        DialogHelper.showConfirmDialog(
                requireContext(),
                "Xác nhận đăng xuất",
                "Bạn có muốn đăng xuất không?",
                "Đăng xuất",
                "Hủy",
                () -> {
                    thucHienDangXuat();
                }
        );
    }

    private void thucHienDangXuat() {
        tokenManager.xoaToken();

        new Thread(() -> {
            CoSoDuLieuApp.getInstance(requireContext()).clearAllTables();
        }).start();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        if (getActivity() != null) {
            getActivity().finish();
        }

        Toast.makeText(getContext(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();
    }
}