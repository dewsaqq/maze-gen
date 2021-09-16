package helper;

import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class CollectionHelper {
    public static <E> E getRandomSetElement(Set<E> set) {
        return set.stream().skip(new Random().nextInt(set.size())).findFirst().orElse(null);
    }

    public static <E> E getRandomListElement(List<E> list) {
        return list.get(new Random().nextInt(list.size()));
    }
}
