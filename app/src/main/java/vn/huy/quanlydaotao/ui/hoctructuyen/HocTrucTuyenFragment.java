package vn.huy.quanlydaotao.ui.hoctructuyen;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.local.CoSoDuLieuApp;
import vn.huy.quanlydaotao.data.local.TokenManager;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.api.RetrofitClient;
import vn.huy.quanlydaotao.data.repository.LichMeetRepositoryImpl;
import vn.huy.quanlydaotao.domain.model.LichMeet;
import vn.huy.quanlydaotao.domain.usecase.LayLichMeetUseCase;
import vn.huy.quanlydaotao.ui.main.MainViewModel;

public class HocTrucTuyenFragment extends Fragment {

    private LichMeetAdapter adapter;
    private HocTrucTuyenViewModel viewModel;
    private TokenManager tokenManager;
    private int idNguoiDung;
    private MainViewModel mainViewModel;

    public HocTrucTuyenFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

        tokenManager = new TokenManager(requireContext());
        idNguoiDung = tokenManager.layId();

        setupRecyclerView(view);
        setupViewModel();

        if (tokenManager.tokenHopLe() && idNguoiDung > 0) {
            observeData();
        } else {
            Toast.makeText(getContext(), "Phiên đăng nhập hết hạn!", Toast.LENGTH_SHORT).show();
            //có thể điều hướng về màn hình Login ở đây nếu cần
        }

        setupRecyclerView(view);
        setupViewModel();
        observeData();
    }

    private void setupRecyclerView(View view) {
        RecyclerView rvLichMeet = view.findViewById(R.id.rvLichMeet);
        adapter = new LichMeetAdapter();
        rvLichMeet.setLayoutManager(new LinearLayoutManager(getContext()));
        rvLichMeet.setAdapter(adapter);

        adapter.setOnBtnJoinClickListener(this::handleJoinMeeting);
    }

    private void setupViewModel() {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        DichVuApi api = RetrofitClient.getClient().create(DichVuApi.class);
        CoSoDuLieuApp db = CoSoDuLieuApp.getInstance(requireContext());
        LichMeetRepositoryImpl repo = new LichMeetRepositoryImpl(db.lichMeetDao(), api);
        LayLichMeetUseCase useCase = new LayLichMeetUseCase(repo);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new HocTrucTuyenViewModel(useCase);
            }
        }).get(HocTrucTuyenViewModel.class);
    }

    private void observeData() {
        viewModel.getDanhSachLichMeet(idNguoiDung).observe(getViewLifecycleOwner(), lichMeets -> {
            View layoutEmpty = getView().findViewById(R.id.layoutEmpty);
            View rvLichMeet = getView().findViewById(R.id.rvLichMeet);
            View layoutLive = getView().findViewById(R.id.layoutLiveNow);
            View tvListTitle = getView().findViewById(R.id.tvListTitle);

            if (lichMeets == null || lichMeets.isEmpty()) {
                if (layoutEmpty != null) layoutEmpty.setVisibility(View.VISIBLE);
                if (rvLichMeet != null) rvLichMeet.setVisibility(View.GONE);
                if (layoutLive != null) layoutLive.setVisibility(View.GONE);
                if (tvListTitle != null) tvListTitle.setVisibility(View.GONE);
            } else {
                if (layoutEmpty != null) layoutEmpty.setVisibility(View.GONE);
                if (rvLichMeet != null) rvLichMeet.setVisibility(View.VISIBLE);
                if (tvListTitle != null) tvListTitle.setVisibility(View.VISIBLE);

                adapter.setItems(lichMeets);
                updateLiveCard(lichMeets);
            }
        });
    }

    private void updateLiveCard(List<LichMeet> lichMeets) {
        View layoutLive = getView().findViewById(R.id.layoutLiveNow);
        LichMeet currentLive = findCurrentLiveClass(lichMeets);

        if (currentLive != null) {
            layoutLive.setVisibility(View.VISIBLE);
            doDuLieuVaoCardLive(currentLive);
        } else {
            layoutLive.setVisibility(View.GONE);
        }
    }

    private LichMeet findCurrentLiveClass(List<LichMeet> lichMeets) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        long now = System.currentTimeMillis();
        long duration = 90 * 60 * 1000; // 1 tiếng 30 phút

        for (LichMeet item : lichMeets) {
            try {
                Date startTime = sdf.parse(item.getThoiGian());
                if (startTime != null) {
                    long startMilis = startTime.getTime();
                    if (now >= startMilis && now <= (startMilis + duration)) {
                        return item;
                    }
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
        return null;
    }

    private void doDuLieuVaoCardLive(LichMeet item) {
        TextView tvTitle = getView().findViewById(R.id.tvLiveTitle);
        View btnJoin = getView().findViewById(R.id.btnJoinLive);

        if (tvTitle != null) tvTitle.setText(item.getTieuDe());

        if (btnJoin != null) {
            btnJoin.setOnClickListener(v -> handleJoinMeeting(item.getLinkMeet()));
        }
    }

    private void handleJoinMeeting(String link) {
        Boolean isConnected = mainViewModel.getIsConnected().getValue();
        View rootView = getView();
        if (rootView == null) return;

        if (isConnected != null && !isConnected) {
            showStatusSnackbar(rootView, "Không có mạng, không thể vào học lúc này!", "#F59E0B", Color.BLACK);
            return;
        }

        if (link == null || link.isEmpty()) {
            showStatusSnackbar(rootView, "Lỗi: Link lớp học không tồn tại!", "#B3261E", Color.WHITE);
            return;
        }

        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
        } catch (Exception e) {
            showStatusSnackbar(rootView, "Thiết bị không có ứng dụng mở link này!", "#B3261E", Color.WHITE);
        }
    }

    private void showStatusSnackbar(View view, String message, String colorHex, int textColor) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.parseColor(colorHex));
        snackbar.setTextColor(textColor);
        snackbar.setAnchorView(getView().findViewById(R.id.bottom_menu));
        snackbar.show();
    }
}