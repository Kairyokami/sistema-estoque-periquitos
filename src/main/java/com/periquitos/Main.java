package com.periquitos;

import com.formdev.flatlaf.FlatDarkLaf;
import com.periquitos.db.DatabaseManager;
import com.periquitos.view.LoginFrame;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        FlatDarkLaf.setup();

        DatabaseManager.initialize();

        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
