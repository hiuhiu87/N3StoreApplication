/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

import java.util.Date;

/**
 *
 * @author ACER
 */
public class KhachHang {
    
    private int id;
    private String FULLNAME;
    private String EMAIL;
  
    private String PHONE_NUMBER;
    private String Address;
  private Date BIRTHDATE;

    @Override
    public String toString() {
        return "KhachHang{" + "id=" + id + ", FULLNAME=" + FULLNAME + ", EMAIL=" + EMAIL + ", PHONE_NUMBER=" + PHONE_NUMBER + ", Address=" + Address + ", BIRTHDATE=" + BIRTHDATE + '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFULLNAME(String FULLNAME) {
        this.FULLNAME = FULLNAME;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public void setPHONE_NUMBER(String PHONE_NUMBER) {
        this.PHONE_NUMBER = PHONE_NUMBER;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public void setBIRTHDATE(Date BIRTHDATE) {
        this.BIRTHDATE = BIRTHDATE;
    }

    public int getId() {
        return id;
    }

    public String getFULLNAME() {
        return FULLNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getPHONE_NUMBER() {
        return PHONE_NUMBER;
    }

    public String getAddress() {
        return Address;
    }

    public Date getBIRTHDATE() {
        return BIRTHDATE;
    }

    public KhachHang(int id, String FULLNAME, String EMAIL, String PHONE_NUMBER, String Address, Date BIRTHDATE) {
        this.id = id;
        this.FULLNAME = FULLNAME;
        this.EMAIL = EMAIL;
        this.PHONE_NUMBER = PHONE_NUMBER;
        this.Address = Address;
        this.BIRTHDATE = BIRTHDATE;
    }

    public KhachHang() {
    }
   
      public Object[] toDaTaRow() {//lay du lieu vao dong cua bang
        return new Object[]{this.id,this.FULLNAME,this.EMAIL,this.PHONE_NUMBER,this.Address,this.BIRTHDATE};
    }
}
