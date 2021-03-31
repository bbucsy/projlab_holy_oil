package hu.holyoil.repository;

public interface IReadWriteRepository<E> extends IReadRepository <E>{

    void Add(String name, E element);

    void Remove(String id);

}
