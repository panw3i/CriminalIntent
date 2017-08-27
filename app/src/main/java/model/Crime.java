package model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by pan on 2017/8/24.
 */

public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public Crime(){
       this(UUID.randomUUID());   // 无参的构造方法自动调用有参数的构造方法 , UUID 是自动生成的

    }

    public Crime(UUID id){
        mId = id;
        mDate = new Date();   // 时间对象为 Crime 实例创建时自动创建
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }


}
