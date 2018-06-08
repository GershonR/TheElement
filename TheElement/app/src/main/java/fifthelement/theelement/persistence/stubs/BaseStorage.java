package fifthelement.theelement.persistence.stubs;
/* Stub. acts as a itembase
 * Handles basic storing
 */

import java.util.ArrayList;
import java.util.List;

import fifthelement.theelement.objects.MusicItem;

public abstract class BaseStorage<T extends MusicItem> implements PersistenceStub<T> {
    private List<T> storage;

    public BaseStorage() {
        this.storage = new ArrayList<T>();
    }

    public BaseStorage(List<T> itemList) {
        this.storage = itemList;
    }

    @Override
    public List<T> getAllItems() {
        return storage;
    }

    @Override
    public List<T> getAllItemsSorted() {
        // TODO: implement a Collections.sort()
        return null;
    }

    @Override
    // uses some cool magik to search through the List
    // nvm. Unless we up our support to API 24 then we can't do magik
    public T getByID(String ID) {
        //return storage.stream().filter(item -> ID.equals(item.getID())).findAny().orElse(null);
        T foundData = null;
        for (T item : storage) {
            if (item.getID().equals(ID)) {
                foundData = item;
                break;
            }
        }
        return foundData;
    }

    @Override
    public T storeItem(T item) {
        storage.add(item);
        return item;
    }

    @Override
    // IF item doesn't exist then nothing is done
    // use store() to store item
    public T updateItem(T item) {
        int indexOfData = storage.indexOf(getByID(item.getID()));
        if (indexOfData != -1) {
            storage.remove(indexOfData);
            storage.add(item);
            return item;
        }
        return null;
    }

    public T delete(T item) {
        storage.remove(item);
        return item;
    }

}

