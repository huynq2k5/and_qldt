package vn.huy.quanlydaotao.domain.model;

public class XoaDangKy {
    private final int idNguoiDung;
    private final int idLopHoc;

    public XoaDangKy(int idNguoiDung, int idLopHoc) {
        this.idNguoiDung = idNguoiDung;
        this.idLopHoc = idLopHoc;
    }

    public int getIdNguoiDung() {
        return idNguoiDung;
    }

    public int getIdLopHoc() {
        return idLopHoc;
    }
}