package sdk.models;

/**
 * Created by sorenkolbyejensen on 14/11/2016.
 */
public class Delete {

    public int getCount() {
        return count;
    }

    public String getResult() {
        return result;
    }

    public boolean isDeleted() {
        return softdelete;
    }


    public void setCount(int count) {
        this.count = count;
    }

    private int count;

    private String result;

    private boolean softdelete;



}
