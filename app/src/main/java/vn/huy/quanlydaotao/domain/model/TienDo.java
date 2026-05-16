package vn.huy.quanlydaotao.domain.model;

public class TienDo {
    private int id;
    private int idNguoiDung;
    private int idBaiHoc;
    private int phanTram;
    private String ngayCapNhat;

    public TienDo(int id, int idNguoiDung, int idBaiHoc, int phanTram, String ngayCapNhat) {
        this.id = id;
        this.idNguoiDung = idNguoiDung;
        this.idBaiHoc = idBaiHoc;
        this.phanTram = phanTram;
        this.ngayCapNhat = ngayCapNhat;
    }

    public int getId() {
        return id;
    }

    public int getIdNguoiDung() {
        return idNguoiDung;
    }

    public int getIdBaiHoc() {
        return idBaiHoc;
    }

    public int getPhanTram() {
        return phanTram;
    }

    public String getNgayCapNhat() {
        return ngayCapNhat;
    }
}