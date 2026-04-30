package vn.huy.quanlydaotao.data.local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import vn.huy.quanlydaotao.data.local.dao.BaiKiemTraDao;
import vn.huy.quanlydaotao.data.local.dao.BaiLamTamDao;
import vn.huy.quanlydaotao.data.local.dao.CauHoiDao;
import vn.huy.quanlydaotao.data.local.dao.ChiTietKetQuaDao;
import vn.huy.quanlydaotao.data.local.dao.DangKyLopDao;
import vn.huy.quanlydaotao.data.local.dao.KetQuaDao;
import vn.huy.quanlydaotao.data.local.dao.KhoaHocDao;
import vn.huy.quanlydaotao.data.local.dao.LichMeetDao;
import vn.huy.quanlydaotao.data.local.dao.LopHocDao;
import vn.huy.quanlydaotao.data.local.dao.BaiHocDao;
import vn.huy.quanlydaotao.data.local.entity.BaiKiemTraEntity;
import vn.huy.quanlydaotao.data.local.entity.BaiLamTamEntity;
import vn.huy.quanlydaotao.data.local.entity.CauHoiEntity;
import vn.huy.quanlydaotao.data.local.entity.ChiTietKetQuaEntity;
import vn.huy.quanlydaotao.data.local.entity.DangKyLopEntity;
import vn.huy.quanlydaotao.data.local.entity.KetQuaEntity;
import vn.huy.quanlydaotao.data.local.entity.KhoaHocEntity;
import vn.huy.quanlydaotao.data.local.entity.LichMeetEntity;
import vn.huy.quanlydaotao.data.local.entity.LopHocEntity;
import vn.huy.quanlydaotao.data.local.entity.BaiHocEntity;

@Database(entities = {KhoaHocEntity.class,
        LopHocEntity.class,
        BaiHocEntity.class,
        LichMeetEntity.class,
        DangKyLopEntity.class,
        BaiKiemTraEntity.class,
        CauHoiEntity.class,
        BaiLamTamEntity.class,
        KetQuaEntity.class,
        ChiTietKetQuaEntity.class}, version = 11, exportSchema = false)
public abstract class CoSoDuLieuApp extends RoomDatabase {
    private static CoSoDuLieuApp instance;

    public abstract KhoaHocDao khoaHocDao();
    public abstract LopHocDao lopHocDao();
    public abstract BaiHocDao baiHocDao();
    public abstract LichMeetDao lichMeetDao();
    public abstract DangKyLopDao dangKyLopDao();
    public abstract BaiKiemTraDao baiKiemTraDao();
    public abstract CauHoiDao cauHoiDao();
    public abstract BaiLamTamDao baiLamTamDao();
    public abstract KetQuaDao ketQuaDao();
    public abstract ChiTietKetQuaDao chiTietKetQuaDao();
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
