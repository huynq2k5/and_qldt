package vn.huy.quanlydaotao.ui.canhan;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.local.TokenManager;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.api.RetrofitClient;
import vn.huy.quanlydaotao.data.repository.DoiPassRepositoryImpl;
import vn.huy.quanlydaotao.domain.usecase.DoiPassUseCase;

public class DoiPassBottomSheet extends BottomSheetDialogFragment {
    private DoiPassViewModel viewModel;
    private TextInputEditText edtOld, edtNew, edtConfirm;
    private MaterialButton btnConfirm;

    public static DoiPassBottomSheet newInstance() {
        return new DoiPassBottomSheet();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_bottom_sheet_doi_pass, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewModel();
        initViews(view);
        setupEvents();
    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        android.app.Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setSoftInputMode(android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            View parent = (View) view.getParent();
            com.google.android.material.bottomsheet.BottomSheetBehavior<View> behavior =
                    com.google.android.material.bottomsheet.BottomSheetBehavior.from(parent);
            behavior.setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED);
            behavior.setSkipCollapsed(true);
        }
    }

    private void initViews(View v) {
        edtOld = v.findViewById(R.id.edtOldPassword);
        edtNew = v.findViewById(R.id.edtNewPassword);
        edtConfirm = v.findViewById(R.id.edtConfirmPassword);
        btnConfirm = v.findViewById(R.id.btnConfirmChange);
    }

    private void setupEvents() {
        TokenManager tokenManager = new TokenManager(requireContext());
        int idUser = tokenManager.layId();

        btnConfirm.setOnClickListener(v -> {
            String oldP = edtOld.getText().toString().trim();
            String newP = edtNew.getText().toString().trim();
            String confP = edtConfirm.getText().toString().trim();

            if (oldP.isEmpty() || newP.isEmpty() || confP.isEmpty()) {
                showSnackbar("Vui lòng nhập đủ các trường", false);
                return;
            }

            if (!newP.equals(confP)) {
                showSnackbar("Xác nhận mật khẩu không khớp", false);
                return;
            }

            viewModel.thucHienDoiPass(idUser, oldP, newP).observe(getViewLifecycleOwner(), response -> {
                viewModel.setProcessing(false);
                if (response != null) {
                    showSnackbar(response.getMessage(), response.isSuccess());
                    if (response.isSuccess()) {

                        new Handler(Looper.getMainLooper()).postDelayed(this::dismiss, 1000);
                    }
                } else {
                    showSnackbar("Không nhận được phản hồi từ máy chủ", false);
                }
            });
        });

        viewModel.getIsProcessing().observe(getViewLifecycleOwner(), isProcessing -> {
            btnConfirm.setEnabled(!isProcessing);
            btnConfirm.setText(isProcessing ? "Đang xử lý..." : "Cập nhật mật khẩu");
        });
    }

    private void showSnackbar(String message, boolean isSuccess) {
        View view = getView();
        if (view == null) return;

        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);

        if (isSuccess) {
            snackbar.setBackgroundTint(Color.parseColor("#10B981"));
        } else {
            snackbar.setBackgroundTint(Color.parseColor("#EF4444")); // Màu đỏ
        }

        snackbar.setTextColor(Color.WHITE);
        snackbar.setAnchorView(btnConfirm);

        snackbar.show();
    }

    private void setupViewModel() {
        DichVuApi api = RetrofitClient.getClient().create(DichVuApi.class);
        DoiPassRepositoryImpl repo = new DoiPassRepositoryImpl(api);
        DoiPassUseCase useCase = new DoiPassUseCase(repo);
        viewModel = new ViewModelProvider(this, new DoiPassViewModelFactory(useCase)).get(DoiPassViewModel.class);
    }
}