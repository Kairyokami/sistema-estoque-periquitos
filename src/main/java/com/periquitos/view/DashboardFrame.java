package com.periquitos.view;

import com.periquitos.dao.UsuarioDAO;
import com.periquitos.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Periquitos - Login");
        setSize(420, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Mercadinho Periquitos");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("Usuário:"), gbc);

        JTextField txtLogin = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtLogin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Senha:"), gbc);

        JPasswordField txtSenha = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(txtSenha, gbc);

        JButton btnEntrar = new JButton("Entrar");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(btnEntrar, gbc);

        btnEntrar.addActionListener(e -> {
            String login = txtLogin.getText();
            String senha = new String(txtSenha.getPassword());

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario = usuarioDAO.autenticar(login, senha);

            if (usuario != null) {
                JOptionPane.showMessageDialog(this, "Bem-vindo(a), " + usuario.getNome() + "!");
                dispose();
                new DashboardFrame(usuario).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos!");
            }
        });

        add(panel);
    }
}
