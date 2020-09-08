package fr.eni.encheres.dal.general;

import fr.eni.encheres.dal.DalException;

public interface SelectByIdDAO<T> {

	T SelectById(int _id) throws DalException;
}
