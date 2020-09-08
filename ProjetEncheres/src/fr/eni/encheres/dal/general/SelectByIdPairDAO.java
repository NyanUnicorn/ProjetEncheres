package fr.eni.encheres.dal.general;

import fr.eni.encheres.dal.DalException;

public interface SelectByIdPairDAO<T> {

	T SelectByIdPair(int _id0, int _id1) throws DalException;
}
