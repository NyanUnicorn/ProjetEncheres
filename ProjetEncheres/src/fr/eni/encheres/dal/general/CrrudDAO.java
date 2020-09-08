package fr.eni.encheres.dal.general;

public interface CrrudDAO<T> extends InsertDAO<T>, SelectDAO<T>, SelectByIdDAO<T>, UpdateDAO<T>, DeleteDAO<T>{

}
