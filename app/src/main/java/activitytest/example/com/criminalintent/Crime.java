package activitytest.example.com.criminalintent;

import java.util.UUID;

/**
 * Created by pan on 2017/8/24.
 */

public class Crime {
    private UUID mId;
    private String mTitle;

    public Crime(){
        mId = UUID.randomUUID();
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
