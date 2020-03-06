package app.inisiator.myapplication.models;

public class AvailArticle {
    private String title, thumbnail, preview, time, url;

    public AvailArticle(String title, String thumbnail, String preview, String time, String url) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.preview = preview;
        this.time = time;
        this.url = url;
    }

    public AvailArticle(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
