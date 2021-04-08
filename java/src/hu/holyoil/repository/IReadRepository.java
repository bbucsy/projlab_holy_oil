package hu.holyoil.repository;

import java.util.List;

public interface IReadRepository <E> {

    E Get(String id);

    List<E> GetAll();

}
