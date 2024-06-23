// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import Model.UserModelFromDataBase;
import View.*;
import Controller.*;
import View.auth.SignIn;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SignIn signIn = new SignIn();
            UserModelFromDataBase model = UserModelFromDataBase.getInstance();
            AuthController controller = new AuthController(signIn, model);
        });
    }
}