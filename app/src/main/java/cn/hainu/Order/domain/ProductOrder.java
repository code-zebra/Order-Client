package cn.hainu.Order.domain;

import org.litepal.crud.DataSupport;
import java.util.List;

/**
 * 订单中的菜品信息
 */
public class ProductOrder extends DataSupport{
    private int id; //订单ID
    private List<Order> orders; //与Order表建立多对多关联关系
    private String foodName;//菜名
    private int num;//购买数量
    private double price;//单项菜品总价

    public int getId() {
        return id;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getNum() {
        return num;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
