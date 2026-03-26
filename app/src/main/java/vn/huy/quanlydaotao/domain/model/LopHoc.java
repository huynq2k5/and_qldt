package vn.huy.quanlydaotao.domain.model;

public class LopHoc {
    private final int id;
    private final String tenLop;
    private final String ngayBatDau;
    private final String ngayKetThuc;
    private final int idKhoaHoc;

    public LopHoc(int id, String tenLop, String ngayBatDau, String ngayKetThuc, int idKhoaHoc) {
        this.id = id;
        this.tenLop = tenLop;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.idKhoaHoc = idKhoaHoc;
    }

    public int getId() {
        return id;
    }

    public String getTenLop() {
        return tenLop;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public int getIdKhoaHoc() {
        return idKhoaHoc;
    }
}
