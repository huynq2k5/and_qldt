package vn.huy.quanlydaotao.ui.canhan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.local.CoSoDuLieuApp;
import vn.huy.quanlydaotao.data.local.TokenManager;
import vn.huy.quanlydaotao.ui.login.LoginActivity;

public class CaNhanFragment extends Fragment {

    private TokenManager tokenManager;

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
        tokenManager = new TokenManager(requireContext());
        setupProfile(view);
    }

    private void setupProfile(View view) {
        TextView tvProfileName = view.findViewById(R.id.tvProfileName);
        TextView tvProfileEmail = view.findViewById(R.id.tvProfileEmail);
        View btnLogout = view.findViewById(R.id.btnLogout);

        if (tvProfileName != null) tvProfileName.setText("Quang Huy");
        if (tvProfileEmail != null) tvProfileEmail.setText("huynq@daotao.vn");

        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> xacNhanDangXuat());
        }
    }

    private void xacNhanDangXuat() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Xác nhận đăng xuất")
                .setMessage("Bạn có chắc chắn muốn thoát khỏi ứng dụng không?")
                .setPositiveButton("Đăng xuất", (dialog, which) -> thucHienDangXuat())
                .setNegativeButton("Hủy", null)
                .show();
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