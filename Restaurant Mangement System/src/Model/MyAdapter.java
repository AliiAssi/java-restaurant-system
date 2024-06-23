package Model;

import Controller.Generic_utils.EmpItemForDeletion;

public class MyAdapter implements  RestaurantEmployee, EmpItemForDeletion {

    @Override
    public String getGmail() {
        return null;
    }

    @Override
    public void setGmail(String email) {

    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public String getChefTime() {
        return null;
    }

    @Override
    public void setChefTime(String workingTime) {

    }

    @Override
    public boolean changeOrderStatus(int id) {
        return false;
    }

    @Override
    public boolean deleteOrder(int id) {
        return false;
    }

    @Override
    public String DeleteItem() {
        return "";
    }
}
