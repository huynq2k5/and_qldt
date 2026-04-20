package vn.huy.quanlydaotao.domain.model;

public class LichMeet {
    private final int id;
    private final String tenLop;
    private final String tieuDe;
    private final String linkMeet;
    private final String thoiGian;

    public LichMeet(int id, String tenLop, String tieuDe, String linkMeet, String thoiGian) {
        this.id = id;
        this.tenLop = tenLop;
        this.tieuDe = tieuDe;
        this.linkMeet = linkMeet;
        this.thoiGian = thoiGian;
    }

    public int getId() {
        return id;
    }

    public String getTenLop() {
        return tenLop;
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