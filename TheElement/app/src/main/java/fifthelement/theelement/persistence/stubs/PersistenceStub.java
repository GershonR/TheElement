package fifthelement.theelement.persistence.stubs;

/* Interface for the Persistence layer
 * acts as a db that will contain hardcoded (predefined) items
 */

import java.util.List;

import fifthelement.theelement.objects.MusicItem;

public interface PersistenceStub<T extends MusicItem> {
    List<T> getAllItems();      // some unordered list.

    List<T> getAllItemsSorted();

    T getByID(String ID);

    T storeItem(T item);        // checks & ignores duplicates

    T updateItem(T item);       // replaces old item with new one

    T delete(T item);           // delete's using ID
}

