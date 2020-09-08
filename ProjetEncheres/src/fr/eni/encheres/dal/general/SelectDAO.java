package fr.eni.encheres.dal.general;

import java.util.List;

import fr.eni.encheres.dal.DalException;

public interface SelectDAO<T> {
	List<T> Select() throws DalException;
}
