package domain.augmentation.infrastructure;

public interface Configurable<T> {

    T createRandomConfiguration();

    T createBestConfiguration();

}
