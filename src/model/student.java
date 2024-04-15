package model;

import java.io.Serializable;

public class student implements Serializable  {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   private String MHS;
   private String TenHS;
   private Double Diem;
   private String hinhanh;
   private String diachi;
   private String ghichu;
   
   
   public student()
   {
	   
   }

   

public student(String mHS, String tenHS, Double diem, String hinhanh, String diachi, String ghichu) {
	super();
	MHS = mHS;
	TenHS = tenHS;
	Diem = diem;
	this.hinhanh = hinhanh;
	this.diachi = diachi;
	this.ghichu = ghichu;
}


public String getMHS() {
	return MHS;
}


public void setMHS(String mHS) {
	MHS = mHS;
}


public String getTenHS() {
	return TenHS;
}


public void setTenHS(String tenHS) {
	TenHS = tenHS;
}


public Double getDiem() {
	return Diem;
}


public void setDiem(Double diem) {
	Diem = diem;
}


public String getHinhanh() {
	return hinhanh;
}


public void setHinhanh(String hinhanh) {
	this.hinhanh = hinhanh;
}


public String getDiachi() {
	return diachi;
}


public void setDiachi(String diachi) {
	this.diachi = diachi;
}


public String getGhichu() {
	return ghichu;
}


public void setGhichu(String ghichu) {
	this.ghichu = ghichu;
}


@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    student student = (student) obj;
    return MHS.equals(student.getMHS());
}

@Override
	public String toString() {
		// TODO Auto-generated method stub
		return MHS + "-" + TenHS; 
	}

}
