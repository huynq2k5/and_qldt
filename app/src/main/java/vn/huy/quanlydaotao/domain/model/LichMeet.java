package vn.huy.quanlydaotao.domain.model;

public class LichMeet {
    private int id;
    private int idLopHoc;
    private String tieuDe;
    private String linkMeet;
    private String thoiGian;

    public LichMeet(int id, int idLopHoc, String tieuDe, String linkMeet, String thoiGian) {
        this.id = id;
        this.idLopHoc = idLopHoc;
        this.tieuDe = tieuDe;
        this.linkMeet = linkMeet;
        this.thoiGian = thoiGian;
    }

    public int getId() {
        return id;
    }

    public int getIdLopHoc() {
        return idLopHoc;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public String getLinkMeet() {
        return linkMeet;
    }

    public String getThoiGian() {
        return thoiGian;
    }
}
