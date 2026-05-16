package vn.huy.quanlydaotao.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import vn.huy.quanlydaotao.data.local.entity.TienDoEntity;

@Dao
public interface TienDoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TienDoEntity tienDo);

    @Query("SELECT EXISTS(SELECT * FROM tien_do WHERE id_nguoi_dung = :idNguoiDung AND id_bai_hoc = :idBaiHoc)")
    boolean hasTienDo(int idNguoiDung, int idBaiHoc);

    @Query("UPDATE tien_do SET phan_tram = :phanTram, ngay_cap_nhat = :ngayCapNhat WHERE id_nguoi_dung = :idNguoiDung AND id_bai_hoc = :idBaiHoc")
    void updateTienDoLocal(int idNguoiDung, int idBaiHoc, int phanTram, String ngayCapNhat);
}