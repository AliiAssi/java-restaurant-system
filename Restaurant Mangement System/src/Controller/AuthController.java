package Controller;

import Model.Admin;
import Model.AdminInfo;
import Model.CommandDesignPatternForOrderActions.Chef;
import Model.UserModelFromDataBase;
import View.AdminView.AdminView;
import View.ChefView.ChefView;
import View.auth.SignIn;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthController {
    SignIn view;
    UserModelFromDataBase model;
    public AuthController(SignIn view, UserModelFromDataBase model){
        this.view = view;
        this.model = model;
        view.setVisible(true);
        view.setSignInListener(new SignInButtonListener());
    }
    class SignInButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String gmail = view.getText_1();
            String password = view.getText_2();
            try {
                String type  = UserModelFromDataBase.authenticateUser(gmail, password);
                if(type.equals("no user founded")){
                    view.messageUser();
                } else if (type.equals("chef") || type.equals("chef-am") || type.equals("chef-pm") ) {
                    ChefView chefView = new ChefView();
                    Chef chef = new Chef(gmail,password);
                    ChefController chefController = new ChefController(chefView, chef);
                    view.setVisible(false);
                }
                else if (type.equals("admin") || type.equals("admin-")){
                    AdminView view = new AdminView();view.setVisible(true);
                    ArrayList<AdminInfo> adminInfos = new Admin().getAllAdmins();
                    view.setActionPanel();

                    AdminController controller = new AdminController(new Admin(),view);
                    DefaultTableModel model = controller.getAdminDefault(adminInfos);
                    view.setAdminsTable(model);
                }
                else {
                    view.setVisible(false);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
