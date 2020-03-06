package app.inisiator.myapplication.models;

public class TopThree {
    String nama, point, photo;

    public TopThree(String nama, String point, String photo) {
        this.nama = nama;
        this.point = point;
        this.photo = photo;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
