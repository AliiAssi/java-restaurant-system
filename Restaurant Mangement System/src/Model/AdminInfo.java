package Model;

public class AdminInfo {
    private Admin admin;
    private String salary ;
     private String bonus;
     /**
      * we create this class for getting the chef informations salary and bonus and build it or add it with a chef attrinute
      * ChefInfo composition : chef.   salary.  bonus.
      * **/
    public AdminInfo(Admin admin,String salary,String bonus){
        this.admin = admin;
        this.salary = salary;
        this.bonus = bonus;
    }
    public Admin getAdmin(){
        return admin;
    }
    public String getSalary(){
        return  salary;
    }
    public String getBonus(){
        return bonus;
    }
}
