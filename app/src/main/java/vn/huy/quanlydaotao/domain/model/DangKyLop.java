package vn.huy.quanlydaotao.domain.model;

public class DangKyLop {
    private int id;
    private int idNguoiDung;
    private int idLopHoc;
    private String ngayDangKy;

    public DangKyLop(int id, int idNguoiDung, int idLopHoc, String ngayDangKy) {
        this.id = id;
        this.idNguoiDung = idNguoiDung;
        this.idLopHoc = idLopHoc;
        this.ngayDangKy = ngayDangKy;
    }

    public int getId() {
        return id;
    }

    public int getIdNguoiDung() {
        return idNguoiDung;
    }

    public int getIdLopHoc() {
        return idLopHoc;
    }

    public String getNgayDangKy() {
        return ngayDangKy;
    }
}