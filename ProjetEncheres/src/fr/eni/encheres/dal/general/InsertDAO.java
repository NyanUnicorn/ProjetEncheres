package fr.eni.encheres.dal.general;

import fr.eni.encheres.dal.DalException;

public interface InsertDAO<T> {
	void Insert( T item) throws DalException;
}
