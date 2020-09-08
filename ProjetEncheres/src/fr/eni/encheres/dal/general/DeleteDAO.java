package fr.eni.encheres.dal.general;

import fr.eni.encheres.dal.DalException;

public interface DeleteDAO<T> {

	void Delete(T item) throws DalException;
}
