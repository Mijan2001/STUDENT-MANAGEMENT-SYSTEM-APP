package com.example.s_m_s;

public class Upload {

    private String imageName;
    private String imageUrl;

    public Upload(){

    }

    public Upload(String imageName, String imageUrl) {
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUri() {
        return imageUrl;
    }

    public void setImageUri(String imageUri) {
        this.imageUrl = imageUri;
    }
}