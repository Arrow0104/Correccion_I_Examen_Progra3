package service_layer;

import utilities.ChangeType;

public interface IServiceObserver<T> {
    void onDataChanged(ChangeType type, T entity);
}

