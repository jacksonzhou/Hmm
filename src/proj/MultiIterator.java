package proj;

import java.util.List;

public class MultiIterator<T> {
    // If 'cnt' is null, the state is invalid.
    private int [] cnt;
    private final int [] sizes;
    private final List<T> [] lists;
    
    public MultiIterator(List<T> [] lists)
    {
        this.lists = lists;
        this.sizes = new int[lists.length];
        
        for (int i = 0; i < sizes.length; i++) {
            sizes[i] = lists[i].size();
            // If any of the sizes is 0, we are immediately in the invalid state:
            if (sizes[i] == 0) {
                cnt = null;
                return;
            }
        }
        
        this.cnt = new int[lists.length];
    }
    
    public boolean isValid()
    {
        return cnt != null;
    }
    
    void nextState()
    {
        if (cnt == null) {
            throw new IllegalStateException(
                    "Can not call this method when the current state is invalid");
        }
        for (int i = cnt.length - 1; i >= 0 ; i--) {
            cnt[i]++;
            if (cnt[i] < sizes[i]) {
                return;
            }
            cnt[i] = 0;
        }
        // Ok, if we've fallen out of the loop without returning, this means
        // we've run out of the lists:
        cnt = null;
    }
    
    public T getValue(int idx)
    {
        return lists[idx].get(cnt[idx]);
    }
}
