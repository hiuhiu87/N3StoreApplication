/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package app.repository;

import app.model.Voucher;
import java.util.ArrayList;

/**
 *
 * @author NGUYỄN ĐỨC LƯƠNG
 */
public interface VoucherInterface {

    ArrayList<Voucher> getList(String name);

    int add(Voucher v);

    int remove(Voucher v);

    int update(Voucher v, int id);
}
