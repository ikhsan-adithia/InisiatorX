package app.inisiator.myapplication.models;

public class LeaderBoard {
    private String name, point, photo;

    public LeaderBoard(String name, String point, String photo) {
        this.name = name;
        this.point = point;
        this.photo = photo;
    }

    public LeaderBoard() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
