package vn.huy.quanlydaotao.domain.model;

public class LichSuKT {
    private final int id;
    private final int idNguoiDung;
    private final int idBaiKiemTra;
    private final float diemSo;
    private final String trangThai;
    private final String thoiGianNop;

    public LichSuKT(int id, int idNguoiDung, int idBaiKiemTra, float diemSo, String trangThai, String thoiGianNop) {
        this.id = id;
        this.idNguoiDung = idNguoiDung;
        this.idBaiKiemTra = idBaiKiemTra;
        this.diemSo = diemSo;
        this.trangThai = trangThai;
        this.thoiGianNop = thoiGianNop;
    }

    public int getId() {
        return id;
    }

    public int getIdNguoiDung() {
        return idNguoiDung;
    }

    public int getIdBaiKiemTra() {
        return idBaiKiemTra;
    }

    public float getDiemSo() {
        return diemSo;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public String getThoiGianNop() {
        return thoiGianNop;
    }
}