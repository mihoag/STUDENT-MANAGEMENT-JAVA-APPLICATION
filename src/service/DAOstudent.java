package service;

import java.lang.reflect.Array;
import java.util.ArrayList;

import DB.DBconnection;
import model.student;

public class DAOstudent implements DAOinterface<student>{

	private static DAOstudent daoStd = null;
    private static DBconnection db = null;
	
	public static DAOstudent getInstance()
	{
		if(daoStd == null)
		{
			daoStd = new DAOstudent();
			db = DBconnection.getInstance();
			
		}
		return daoStd;
	}
	@Override
	public boolean insert(student t) {
		// TODO Auto-generated method stub
		try {
			/// check ton tai MHS
			if(isExist(t))
			{
				System.out.println("MHS da ton tai");
				return false;
			}
		    ArrayList<student> arr = selectAll();
		    arr.add(t);
		    db.writeFile(arr);
		    System.out.println("Them thanh cong");
		    return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	     
	}

	@Override
	public void delete(student t) {
		// TODO Auto-generated method stub
		  ArrayList<student> arr = selectAll();
		 // arr.re
		  arr.remove(t);
		  try {
			db.writeFile(arr);
			System.out.println("Xoa thanh cong");
			return;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	@Override
	public ArrayList<student> selectAll() {
		// TODO Auto-generated method stub
		try {
			ArrayList<student> arr = (ArrayList<student>)db.readFile();
			if(arr == null)
			{
				return new ArrayList<>();
			}
			return arr;
		} catch (Exception e) {
		}
		return new ArrayList<>();
	}

	@Override
	public void edit(student t) {
		// TODO Auto-generated method stub
		try {
		    ArrayList<student> arr = selectAll();
		    for(student st : arr)
		    {
		    	if(st.getMHS().equals(t.getMHS()))
		    	{
		           st.setTenHS(t.getTenHS());
		           st.setHinhanh(t.getHinhanh());
		           st.setDiachi(t.getDiachi());
		           st.setDiem(t.getDiem());
		           st.setGhichu(t.getGhichu());
		    	}
		    }
		    db.writeFile(arr);
		   // System.out.println("Edit thanh cong");
		    return;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Edit that bai");
		
	}
	@Override
	public boolean isExist(student t) {
		// TODO Auto-generated method stub
		
		try {
			ArrayList<student> arr = selectAll();
	        for(student st : arr)
	        {
	        	if(st.getMHS().equals(t.getMHS()))
	        	{
	        		return true;
	        	}
	        }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return false;
	}
	
	// getStudent from id
	public student getStudent(String id)
	{
		ArrayList<student> arr = selectAll();
		for(student st : arr)
		{
			if(st.getMHS().equals(id))
			{
				return st;
			}
		}
		return null;
	}

	
}
