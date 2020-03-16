package app.inisiator.myapplication;

public class UserNotif {
    private int id;
    private String isi_notif;
    private String asal_notif;
    private String type_notif;
    private String catatan;

    public UserNotif(int id, String isi_notif, String asal_notif, String type_notif, String catatan) {
        this.id = id;
        this.isi_notif = isi_notif;
        this.asal_notif = asal_notif;
        this.type_notif = type_notif;
        this.catatan = catatan;
    }

    public int getId() {
        return id;
    }

    public String getIsi_notif() {
        return isi_notif;
    }

    public String getAsal_notif() {
        return asal_notif;
    }

    public String getType_notif() {
        return type_notif;
    }

    public String getCatatan() {
        return catatan;
    }
}
