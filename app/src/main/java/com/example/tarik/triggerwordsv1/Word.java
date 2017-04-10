package com.example.tarik.triggerwordsv1;

/**
 * Created by huanghe on 1/04/2017.
 */

public class Word {
    private String name;
    private int imageId;

    public Word (String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setName() {
        this.name = name;
    }

    public void setImageId() {
        this.imageId = imageId;
    }
}
