package vn.huy.quanlydaotao.domain.model;

public class KhoaHoc {
    private int id;
    private String tenKhoaHoc;
    private String moTa;
    private String ngayTao;
    private String duongDanAnh;

    public KhoaHoc(int id, String tenKhoaHoc, String moTa, String ngayTao, String duongDanAnh) {
        this.id = id;
        this.tenKhoaHoc = tenKhoaHoc;
        this.moTa = moTa;
        this.ngayTao = ngayTao;
        this.duongDanAnh = duongDanAnh;
    }

    public int getId() { return id; }
    public String getTenKhoaHoc() { return tenKhoaHoc; }
    public String getMoTa() { return moTa; }
    public String getNgayTao() { return ngayTao; }
    public String getDuongDanAnh() { return duongDanAnh; }
}