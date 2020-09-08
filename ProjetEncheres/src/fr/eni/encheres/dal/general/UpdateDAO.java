package fr.eni.encheres.dal.general;

import fr.eni.encheres.dal.DalException;

public interface UpdateDAO<T> {

	void Update(T _item) throws DalException;
}
