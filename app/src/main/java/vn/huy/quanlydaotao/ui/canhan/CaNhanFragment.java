package vn.huy.quanlydaotao.ui.canhan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.snackbar.Snackbar;

import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.local.CoSoDuLieuApp;
import vn.huy.quanlydaotao.data.local.TokenManager;
import vn.huy.quanlydaotao.ui.login.LoginActivity;
import vn.huy.quanlydaotao.ui.main.DialogHelper;

public class CaNhanFragment extends Fragment {

    private TokenManager tokenManager;
    private LinearLayout btnEditProfile;
    private MaterialSwitch switchBiometric;

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
        setupEdgeToEdge(view);
        tokenManager = new TokenManager(requireContext());
        setupProfile(view);
    }

    private void setupEdgeToEdge(View view) {
        View header = view.findViewById(R.id.layoutHeader);
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            if (header != null) {
                header.setPadding(header.getPaddingLeft(), systemBars.top + 20,
                        header.getPaddingRight(), header.getPaddingBottom());
            }
            return WindowInsetsCompat.CONSUMED;
        });
    }

    private void setupProfile(View view) {
        TextView tvProfileName = view.findViewById(R.id.tvProfileName);
        TextView tvProfileEmail = view.findViewById(R.id.tvProfileEmail);
        View btnLogout = view.findViewById(R.id.btnLogout);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        LinearLayout btnBiometric = view.findViewById(R.id.btnBiometric);
        switchBiometric = view.findViewById(R.id.switchBiometric);

        if (tvProfileName != null) tvProfileName.setText(tokenManager.layHoTen());
        if (tvProfileEmail != null) tvProfileEmail.setText(tokenManager.layEmail());

        if (switchBiometric != null) {
            switchBiometric.setChecked(tokenManager.laVanTayDaBat());
        }

        if (btnBiometric != null) {
            btnBiometric.setOnClickListener(v -> luuTrangThaiBiometric());
        }

        if (btnEditProfile != null) {
            btnEditProfile.setOnClickListener(v -> {
                startActivity(new Intent(getContext(), EditProfileActivity.class));
            });
        }

        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> xacNhanDangXuat());
        }
    }

    private void luuTrangThaiBiometric() {
        if (switchBiometric != null) {
            boolean isChecked = !switchBiometric.isChecked();
            switchBiometric.setChecked(isChecked);
            tokenManager.thietLapVanTay(isChecked);

            String status = isChecked ? "Đã bật xác thực vân tay" : "Đã tắt xác thực vân tay";
            View rootView = getView();
            if (rootView != null) {
                Snackbar snackbar = Snackbar.make(rootView, status, Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(android.graphics.Color.parseColor("#10B981"));
                snackbar.setTextColor(android.graphics.Color.WHITE);

                View bottomMenu = requireActivity().findViewById(R.id.bottom_menu);
                if (bottomMenu != null) {
                    snackbar.setAnchorView(bottomMenu);
                }

                snackbar.show();
            }
        }
    }

    private void xacNhanDangXuat() {
        DialogHelper.showConfirmDialog(
                requireContext(),
                "Xác nhận đăng xuất",
                "Bạn có muốn đăng xuất không?",
                "Đăng xuất",
                "Hủy",
                this::thucHienDangXuat
        );
    }

    private void thucHienDangXuat() {
        tokenManager.xoaToken();
        new Thread(() -> CoSoDuLieuApp.getInstance(requireContext()).clearAllTables()).start();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        if (getActivity() != null) {
            getActivity().finish();
        }
        Toast.makeText(getContext(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();
    }
}