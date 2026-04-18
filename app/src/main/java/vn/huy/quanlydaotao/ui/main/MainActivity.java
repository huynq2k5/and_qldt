package vn.huy.quanlydaotao.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import vn.huy.quanlydaotao.R;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private ChipNavigationBar bottomMenu;
    private boolean isInternalNavigation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Cài đặt SplashScreen
        SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Đặt lệnh này trước setContentView
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_main);

        // Xử lý Window Insets cho ID "main"
        // Sửa đoạn xử lý Window Insets trong onCreate của MainActivity
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                // CHỈ đặt padding Left, Right và Bottom (cho thanh điều hướng)
                // Tuyệt đối để Top = 0 để Fragment có thể lấn lên Status Bar
                v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        bottomMenu = findViewById(R.id.bottom_menu);
        
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            
            // Lắng nghe sự thay đổi destination để cập nhật Bottom Menu
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                if (bottomMenu != null) {
                    isInternalNavigation = true;
                    bottomMenu.setItemSelected(destination.getId(), true);
                    isInternalNavigation = false;
                }
            });
        }

        // Xử lý sự kiện khi click vào item trên Bottom Menu
        if (bottomMenu != null) {
            bottomMenu.setOnItemSelectedListener(id -> {
                // Nếu đây là sự thay đổi từ addOnDestinationChangedListener thì không làm gì cả
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
            });
        }
    }
}
