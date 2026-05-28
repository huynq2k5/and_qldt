package vn.huy.quanlydaotao.ui.lophoc.dangky;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.local.TokenManager;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.api.RetrofitClient;
import vn.huy.quanlydaotao.data.repository.DangKyLopRepositoryImpl;
import vn.huy.quanlydaotao.domain.usecase.DangKyLopUseCase;

public class DangKyLopBottomSheet extends BottomSheetDialogFragment {

    private int idLopHoc;
    int idNguoiDung;
    private String tenLop;
    private DangKyLopViewModel viewModel;

    public interface OnDangKyThanhCongListener {
        void onDangKyThanhCong();
    }
    private OnDangKyThanhCongListener listener;

    public void setOnDangKyThanhCongListener(OnDangKyThanhCongListener listener) {
        this.listener = listener;
    }

    public static DangKyLopBottomSheet newInstance(int idLopHoc, String tenLop) {
        DangKyLopBottomSheet fragment = new DangKyLopBottomSheet();
        Bundle args = new Bundle();
        args.putInt("id_lop", idLopHoc);
        args.putString("ten_lop", tenLop);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idLopHoc = getArguments().getInt("id_lop");
            tenLop = getArguments().getString("ten_lop");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_bottom_sheet_dang_ky, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TokenManager tokenManager = new TokenManager(requireContext());
        idNguoiDung = tokenManager.layId();
        setupViewModel();

        TextView tvSummary = view.findViewById(R.id.tvSummary);
        MaterialButton btnConfirm = view.findViewById(R.id.btnConfirmRegister);
        CheckBox cbCommit = view.findViewById(R.id.cbCommit);

        tvSummary.setText("Bạn đang thực hiện đăng ký vào lớp: " + tenLop);

        btnConfirm.setEnabled(false);
        btnConfirm.setBackgroundColor(android.graphics.Color.parseColor("#A0A0A0"));

        cbCommit.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                btnConfirm.setEnabled(true);
                btnConfirm.setBackgroundColor(android.graphics.Color.parseColor("#1E3A8A")); // Màu xanh đăng ký bình thường
            } else {
                btnConfirm.setEnabled(false);
                btnConfirm.setBackgroundColor(android.graphics.Color.parseColor("#A0A0A0")); // Màu xám khi chưa check
            }
        });

        btnConfirm.setOnClickListener(v -> {
            android.util.Log.d("HUY_DEBUG", "Gửi đăng ký: UserID=" + idNguoiDung + ", LopID=" + idLopHoc);
            viewModel.thucHienDangKy(idNguoiDung, idLopHoc).observe(getViewLifecycleOwner(), response -> {
                viewModel.setProcessing(false);
                if (response != null) {
                    if ("success".equals(response.getStatus())) {
                        Toast.makeText(getContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        if (listener != null) {
                            listener.onDangKyThanhCong();
                        }
                        dismiss();
                    } else {
                        Toast.makeText(getContext(), "Server báo lỗi: " + response.getMessage(), Toast.LENGTH_LONG).show();
                        android.util.Log.e("HUY_DEBUG", "Server trả về lỗi: " + response.getMessage());
                    }
                } else {
                    Toast.makeText(getContext(), "Không nhận được phản hồi từ server", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void setupViewModel() {
        DichVuApi api = RetrofitClient.getClient().create(DichVuApi.class);

        DangKyLopRepositoryImpl repo = new DangKyLopRepositoryImpl(api);
        DangKyLopUseCase useCase = new DangKyLopUseCase(repo);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends androidx.lifecycle.ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new DangKyLopViewModel(useCase);
            }
        }).get(DangKyLopViewModel.class);
    }
}