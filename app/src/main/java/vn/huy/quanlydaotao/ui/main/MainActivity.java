package vn.huy.quanlydaotao.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.util.NetworkManager;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private ChipNavigationBar bottomMenu;
    private boolean isInternalNavigation = false;
    private MainViewModel mainViewModel;
    private NetworkManager networkManager;
    private Snackbar networkSnackbar;
    private boolean wasDisconnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);

        setupWindowConfiguration();

        setContentView(R.layout.activity_main);

        initViews();
        setupNavigation();
        setupNetworkMonitoring();
    }
    private void setupWindowConfiguration() {
        EdgeToEdge.enable(this);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);

        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        windowInsetsController.setAppearanceLightStatusBars(false);
    }
    private void initViews() {
        bottomMenu = findViewById(R.id.bottom_menu);
        View mainView = findViewById(R.id.main);

        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                // Giữ Top = 0 để Fragment lấn lên Status Bar theo yêu cầu của bạn
                v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
    }
    private void setupNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();

            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                if (bottomMenu != null) {
                    isInternalNavigation = true;
                    bottomMenu.setItemSelected(destination.getId(), true);
                    isInternalNavigation = false;
                }
            });
        }

        if (bottomMenu != null) {
            bottomMenu.setOnItemSelectedListener(this::handleBottomNavigation);
        }
    }
    private void handleBottomNavigation(int id) {
        if (isInternalNavigation) return;

        if (navController != null && navController.getCurrentDestination() != null
                && navController.getCurrentDestination().getId() != id) {

            NavOptions navOptions = new NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .setRestoreState(true)
                    .setPopUpTo(navController.getGraph().getStartDestinationId(), false, true)
                    .build();

            try {
                navController.navigate(id, null, navOptions);
            } catch (IllegalArgumentException e) {
                navController.navigate(id);
            }
        }
    }
    private void setupNetworkMonitoring() {
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        networkManager = new NetworkManager(this, isConnected -> {
            mainViewModel.setNetworkStatus(isConnected);

            if (!isConnected) {
                showStatusSnackBar("Không có kết nối mạng", Color.parseColor("#F59E0B"), Color.BLACK);
                wasDisconnected = true;
            } else {
                if (wasDisconnected) {
                    showStatusSnackBar("Đã khôi phục kết nối mạng", Color.parseColor("#10B981"), Color.WHITE);
                    wasDisconnected = false;
                }
            }
        });

        networkManager.startMonitoring();
    }
    private void showStatusSnackBar(String message, int backgroundColor, int textColor) {
        View rootView = findViewById(android.R.id.content);
        if (rootView == null) return;

        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);

        snackbar.setBackgroundTint(backgroundColor);
        snackbar.setTextColor(textColor);
        snackbar.setAnchorView(bottomMenu);
        snackbar.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (networkManager != null) {
            networkManager.stopMonitoring();
        }
    }
}