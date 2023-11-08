/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.request;

/**
 *
 * @author Admin
 */
public class AddProductRequest {

    private String name;

    private String categoryName;

    private String companyName;

    public AddProductRequest(String name, String categoryName, String companyName) {
        this.name = name;
        this.categoryName = categoryName;
        this.companyName = companyName;
    }

    public AddProductRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
