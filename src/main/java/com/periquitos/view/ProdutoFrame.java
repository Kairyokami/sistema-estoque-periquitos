package com.periquitos.view;

import com.periquitos.db.DatabaseManager;
import com.periquitos.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame(Usuario usuario) {
        setTitle("Periquitos - Dashboard");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblUsuario = new JLabel("Usuário logado: " + usuario.getNome() + " (" + usuario.getPerfil() + ")");
        lblUsuario.setFont(new Font("SansSerif", Font.BOLD, 14));

        JPanel painel = new JPanel(new GridLayout(2, 3, 20, 20));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painel.add(criarBotao("Produtos", "Gerenciar produtos e preços", () -> new ProdutoFrame().setVisible(true)));
        painel.add(criarBotao("Estoque", "Consultar e ajustar estoque", () -> new EstoqueFrame().setVisible(true)));
        painel.add(criarBotao("Fornecedores", "Cadastrar fornecedores", () -> new FornecedorFrame().setVisible(true)));
        painel.add(criarBotao("Clientes", "Cadastrar clientes", () -> new ClienteFrame().setVisible(true)));
        painel.add(criarBotao("Categorias", "Organizar produtos", () -> new CategoriaFrame().setVisible(true)));
        painel.add(criarBotao("Relatórios", "Resumo geral", () -> JOptionPane.showMessageDialog(this, "Relatórios básicos em desenvolvimento.")));

        JPanel principal = new JPanel(new BorderLayout());
        principal.add(lblUsuario, BorderLayout.NORTH);
        principal.add(painel, BorderLayout.CENTER);

        add(principal);
    }

    private JButton criarBotao(String titulo, String descricao, Runnable acao) {
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout());
        btn.setPreferredSize(new Dimension(220, 120));

        JLabel tituloLabel = new JLabel(titulo, SwingConstants.CENTER);
        tituloLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        JLabel descricaoLabel = new JLabel(descricao, SwingConstants.CENTER);
        descricaoLabel.setForeground(Color.GRAY);

        JPanel content = new JPanel(new GridLayout(2, 1));
        content.add(tituloLabel);
        content.add(descricaoLabel);
        btn.add(content, BorderLayout.CENTER);

        btn.addActionListener(e -> acao.run());
        return btn;
    }
}
