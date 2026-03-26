package vn.huy.quanlydaotao.data.local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import vn.huy.quanlydaotao.data.local.dao.KhoaHocDao;
import vn.huy.quanlydaotao.data.local.dao.LopHocDao;
import vn.huy.quanlydaotao.data.local.entity.KhoaHocEntity;
import vn.huy.quanlydaotao.data.local.entity.LopHocEntity;

@Database(entities = {KhoaHocEntity.class, LopHocEntity.class}, version = 2, exportSchema = false)
public abstract class CoSoDuLieuApp extends RoomDatabase {
    private static CoSoDuLieuApp instance;

    public abstract KhoaHocDao khoaHocDao();
    public abstract LopHocDao lopHocDao();

    public static synchronized CoSoDuLieuApp getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CoSoDuLieuApp.class, "quanlydaotao_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
