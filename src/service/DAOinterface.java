package service;

import java.util.ArrayList;

public interface DAOinterface<T> {
   public boolean insert(T t);
   public void delete(T t);
   public ArrayList<T> selectAll();
   public void edit(T t);
   public boolean isExist(T t);
}
