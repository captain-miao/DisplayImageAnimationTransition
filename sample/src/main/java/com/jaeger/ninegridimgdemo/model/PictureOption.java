package com.jaeger.ninegridimgdemo.model;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author YanLu
 * @since 16/7/6
 */

public class PictureOption extends BaseModel implements Parcelable {
    public static final Creator<PictureOption> CREATOR;
    public int index;
    public List<String> pictures;
    public List<Rect> rects;

    static {
        CREATOR = new Creator<PictureOption>() {
            public PictureOption createFromParcel(Parcel parcel) {
                return new PictureOption(parcel);
            }

            public PictureOption[] newArray(int i) {
                return new PictureOption[i];
            }
        };
    }

    public PictureOption(int index, List<String> list) {
        this.index = index;
        this.pictures = list;
    }

    public PictureOption(int index, List<String> list, List<Rect> rects) {
        this(index, list);
        this.rects = rects;
    }

    protected PictureOption(Parcel parcel) {
        this.index = parcel.readInt();
        this.pictures = parcel.createStringArrayList();
        this.rects = parcel.createTypedArrayList(Rect.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.index);
        parcel.writeStringList(this.pictures);
        parcel.writeTypedList(this.rects);
    }

}
