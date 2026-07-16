package fr.ada.java_blog.model;

public class Media {
    private Integer id;
    private TypeMedia type;
    private String url;

    public Media(Integer id, TypeMedia type, String url) {
        this.id = id;
        this.type = type;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public TypeMedia getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

}