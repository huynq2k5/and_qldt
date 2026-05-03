package vn.huy.quanlydaotao.ui.main;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import vn.huy.quanlydaotao.R;
import vn.huy.quanlydaotao.data.local.TokenManager;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.api.RetrofitClient;
import vn.huy.quanlydaotao.data.repository.FCMRepositoryImpl;
import vn.huy.quanlydaotao.domain.repository.IFCMRepository;
import vn.huy.quanlydaotao.domain.usecase.UpdateFcmTokenUseCase;

public class MyFcmService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            String tieuDe = remoteMessage.getNotification().getTitle();
            String noiDung = remoteMessage.getNotification().getBody();
            guiThongBao(tieuDe, noiDung);
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("FCM_TOKEN", "Token moi duoc cap: " + token);
        TokenManager tokenManager = new TokenManager(this);
        int idUser = tokenManager.layId();

        if (idUser > 0) {
            DichVuApi api = RetrofitClient.getClient().create(DichVuApi.class);
            IFCMRepository repo = new FCMRepositoryImpl(api);
            UpdateFcmTokenUseCase useCase = new UpdateFcmTokenUseCase(repo);

            useCase.execute(idUser, token);
        }
    }

    private void guiThongBao(String title, String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_IMMUTABLE);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        String channelId = "dao_tao_notification_channel";
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.icon_app)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Thông báo đào tạo",
                    NotificationManager.IMPORTANCE_HIGH);

            // Thiết lập âm thanh cho Channel (Bắt buộc cho Android 8.0+)
            channel.setSound(defaultSoundUri, null);

            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }
}