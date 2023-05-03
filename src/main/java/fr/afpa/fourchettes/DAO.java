package fr.afpa.fourchettes;

import java.util.ArrayList;

public abstract class DAO<T> {
    
    public abstract T find(int id);

    public abstract void delete(int id);

    public abstract ArrayList<T> findAll();

}
