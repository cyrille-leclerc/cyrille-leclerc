package cyrille.util.concurrent.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListHelper<E> {

    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    // ...

    public boolean putIfAbsent(E x) {
        synchronized (list) {
            boolean absent = !list.contains(x);
            if (absent)
                list.add(x);
            return absent;
        }
    }
}