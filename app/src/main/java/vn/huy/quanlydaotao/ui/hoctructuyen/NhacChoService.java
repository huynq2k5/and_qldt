package vn.huy.quanlydaotao.ui.hoctructuyen;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import vn.huy.quanlydaotao.R;
import java.util.Random;

public class NhacChoService extends Service {

    private WindowManager windowManager;
    private View floatingView;
    private WindowManager.LayoutParams params;
    private static final String CHANNEL_ID = "NhacChoServiceChannel";
    private static final int NOTIFICATION_ID = 999;

    private MediaPlayer mediaPlayer;
    private String[] danhSachTenBaiHat = {"Full Mooom Magic", "In the Morning Light", "Romance"};
    private int[] danhSachIdNhac = {R.raw.fullmoommagic, R.raw.inthemorninglight, R.raw.romance};
    private int viTriBaiHatHienTai = 0;
    private Random random = new Random();

    private View musicPanelView;
    private WindowManager.LayoutParams panelParams;
    private boolean isPanelOpen = false;
    private int layoutTypeGlobal;
    private MusicAdapter musicAdapter;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        taoNotificationChannel();
        Notification notification = taoNotification();
        startForeground(NOTIFICATION_ID, notification);
        hienThiBongBongNoi();

        viTriBaiHatHienTai = random.nextInt(danhSachIdNhac.length);
        phatNhacCho(danhSachIdNhac[viTriBaiHatHienTai]);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    private void taoNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Kênh nhạc chờ cuộc họp",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    private Notification taoNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Đang kết nối phòng học")
                .setContentText("Nhạc chờ đang phát ngầm...")
                .setSmallIcon(R.drawable.ic_time)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
    }

    private void phatNhacCho(int rawId) {
        try {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
                mediaPlayer = null;
            }

            mediaPlayer = new MediaPlayer();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                android.media.AudioAttributes audioAttributes = new android.media.AudioAttributes.Builder()
                        .setUsage(android.media.AudioAttributes.USAGE_MEDIA)
                        .setContentType(android.media.AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build();
                mediaPlayer.setAudioAttributes(audioAttributes);
            } else {
                mediaPlayer.setAudioStreamType(android.media.AudioManager.STREAM_MUSIC);
            }

            android.content.res.AssetFileDescriptor afd = getResources().openRawResourceFd(rawId);
            if (afd != null) {
                mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                afd.close();
            }

            mediaPlayer.setLooping(false);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    phatNgauNhienBaiTiepTheo();
                }
            });

            mediaPlayer.prepare();
            mediaPlayer.start();

            if (musicAdapter != null) {
                musicAdapter.updatePlayingState(viTriBaiHatHienTai, true);
            }

        } catch (Exception e) {
            android.util.Log.e("NhacCho", "Lỗi phát nhạc: " + e.getMessage());
        }
    }

    private void phatNgauNhienBaiTiepTheo() {
        if (danhSachIdNhac.length <= 1) {
            phatNhacCho(danhSachIdNhac[0]);
            return;
        }

        int viTriMoi;
        do {
            viTriMoi = random.nextInt(danhSachIdNhac.length);
        } while (viTriMoi == viTriBaiHatHienTai);

        viTriBaiHatHienTai = viTriMoi;
        phatNhacCho(danhSachIdNhac[viTriBaiHatHienTai]);
    }

    private void hienThiBongBongNoi() {
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this, R.style.Theme_Quanlydaotao);
        floatingView = LayoutInflater.from(contextThemeWrapper).inflate(R.layout.layout_floating_bubble, null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutTypeGlobal = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutTypeGlobal = WindowManager.LayoutParams.TYPE_PHONE;
        }

        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layoutTypeGlobal,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 100;
        params.y = 100;

        windowManager.addView(floatingView, params);

        final View cardBubble = floatingView.findViewById(R.id.cvFloatingBubble);
        if (cardBubble != null) {
            cardBubble.setOnTouchListener(new View.OnTouchListener() {
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;
                private static final int MAX_CLICK_DISTANCE = 10;
                private View dismissView;
                private WindowManager.LayoutParams dismissParams;
                private boolean isDismissViewAdded = false;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            initialX = params.x;
                            initialY = params.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();

                            if (dismissView == null) {
                                ContextThemeWrapper dismissThemeWrapper = new ContextThemeWrapper(NhacChoService.this, R.style.Theme_Quanlydaotao);
                                dismissView = LayoutInflater.from(dismissThemeWrapper).inflate(R.layout.layout_dismiss_circle, null);
                                dismissParams = new WindowManager.LayoutParams(
                                        WindowManager.LayoutParams.MATCH_PARENT,
                                        WindowManager.LayoutParams.WRAP_CONTENT,
                                        layoutTypeGlobal,
                                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                                        PixelFormat.TRANSLUCENT
                                );
                                dismissParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                            }

                            if (!isDismissViewAdded) {
                                windowManager.addView(dismissView, dismissParams);
                                isDismissViewAdded = true;
                            }
                            return true;

                        case MotionEvent.ACTION_MOVE:
                            int rawMoveX = (int) event.getRawX();
                            int rawMoveY = (int) event.getRawY();

                            DisplayMetrics metrics = new DisplayMetrics();
                            windowManager.getDefaultDisplay().getMetrics(metrics);
                            int sHeight = metrics.heightPixels;
                            int sWidth = metrics.widthPixels;

                            int dismissCenterX = sWidth / 2;
                            int dismissCenterY = sHeight - 40 - (60 * (int)metrics.density / 2);

                            double khoangCach = Math.sqrt(Math.pow(rawMoveX - dismissCenterX, 2) + Math.pow(rawMoveY - dismissCenterY, 2));

                            if (khoangCach < 250) {
                                params.x = dismissCenterX - (floatingView.getWidth() / 2);
                                params.y = dismissCenterY - (floatingView.getHeight() / 2);
                                cardBubble.setAlpha(0.5f);
                            } else {
                                params.x = initialX + (int) (event.getRawX() - initialTouchX);
                                params.y = initialY + (int) (event.getRawY() - initialTouchY);
                                cardBubble.setAlpha(1.0f);
                            }

                            windowManager.updateViewLayout(floatingView, params);

                            if (isPanelOpen && musicPanelView != null && panelParams != null) {
                                panelParams.x = params.x + floatingView.getWidth() + 10;
                                panelParams.y = params.y;
                                windowManager.updateViewLayout(musicPanelView, panelParams);
                            }
                            return true;

                        case MotionEvent.ACTION_UP:
                            if (isDismissViewAdded && dismissView != null) {
                                windowManager.removeView(dismissView);
                                isDismissViewAdded = false;
                            }

                            int rawUpX = (int) event.getRawX();
                            int rawUpY = (int) event.getRawY();

                            DisplayMetrics upMetrics = new DisplayMetrics();
                            windowManager.getDefaultDisplay().getMetrics(upMetrics);
                            int upHeight = upMetrics.heightPixels;
                            int upWidth = upMetrics.widthPixels;

                            int upDismissCenterX = upWidth / 2;
                            int upDismissCenterY = upHeight - 40 - (60 * (int)upMetrics.density / 2);

                            double khoangCachCuoi = Math.sqrt(Math.pow(rawUpX - upDismissCenterX, 2) + Math.pow(rawUpY - upDismissCenterY, 2));

                            if (khoangCachCuoi < 250) {
                                stopSelf();
                                return true;
                            }

                            float diffX = event.getRawX() - initialTouchX;
                            float diffY = event.getRawY() - initialTouchY;
                            if (Math.abs(diffX) < MAX_CLICK_DISTANCE && Math.abs(diffY) < MAX_CLICK_DISTANCE) {
                                daoTrangThaiPanelNhac(params.x, params.y);
                            }
                            return true;
                    }
                    return false;
                }
            });
        }
    }

    private void daoTrangThaiPanelNhac(int bubbleX, int bubbleY) {
        if (isPanelOpen) {
            if (musicPanelView != null) {
                windowManager.removeView(musicPanelView);
            }
            isPanelOpen = false;
        } else {
            ContextThemeWrapper panelThemeWrapper = new ContextThemeWrapper(this, R.style.Theme_Quanlydaotao);
            musicPanelView = LayoutInflater.from(panelThemeWrapper).inflate(R.layout.layout_music_panel, null);

            panelParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    layoutTypeGlobal,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                            | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                    PixelFormat.TRANSLUCENT
            );

            panelParams.gravity = Gravity.TOP | Gravity.START;
            panelParams.x = bubbleX + floatingView.getWidth() + 10;
            panelParams.y = bubbleY;

            windowManager.addView(musicPanelView, panelParams);
            isPanelOpen = true;

            musicPanelView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        if (isPanelOpen && musicPanelView != null) {
                            windowManager.removeView(musicPanelView);
                            isPanelOpen = false;
                        }
                        return true;
                    }
                    return false;
                }
            });

            ListView lvMusic = musicPanelView.findViewById(R.id.lvMusic);
            if (lvMusic != null) {
                musicAdapter = new MusicAdapter(this, danhSachTenBaiHat);
                lvMusic.setAdapter(musicAdapter);

                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    musicAdapter.updatePlayingState(viTriBaiHatHienTai, true);
                } else {
                    musicAdapter.updatePlayingState(viTriBaiHatHienTai, false);
                }

                lvMusic.setOnItemClickListener((parent, view, position, id) -> {
                    if (position == viTriBaiHatHienTai) {
                        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                            musicAdapter.updatePlayingState(position, false);
                        } else if (mediaPlayer != null) {
                            mediaPlayer.start();
                            musicAdapter.updatePlayingState(position, true);
                        } else {
                            phatNhacCho(danhSachIdNhac[position]);
                        }
                    } else {
                        viTriBaiHatHienTai = position;
                        phatNhacCho(danhSachIdNhac[position]);
                    }
                });
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isPanelOpen && musicPanelView != null) {
            windowManager.removeView(musicPanelView);
        }
        if (floatingView != null && windowManager != null) {
            windowManager.removeView(floatingView);
        }
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}