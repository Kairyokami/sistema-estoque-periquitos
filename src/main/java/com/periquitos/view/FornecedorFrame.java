package com.periquitos.view;

import com.periquitos.dao.CategoriaDAO;
import com.periquitos.model.Categoria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CategoriaFrame extends JFrame {
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Nome", "Descrição"}, 0);

    public CategoriaFrame() {
        setTitle("Periquitos - Categorias");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        JTextField txtNome = new JTextField();
        JTextField txtDescricao = new JTextField();

        form.add(new JLabel("Nome:"));
        form.add(txtNome);
        form.add(new JLabel("Descrição:"));
        form.add(txtDescricao);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            Categoria categoria = new Categoria(txtNome.getText(), txtDescricao.getText());
            categoriaDAO.salvar(categoria);
            txtNome.setText("");
            txtDescricao.setText("");
            carregar();
        });

        JTable tabela = new JTable(model);
        JScrollPane scroll = new JScrollPane(tabela);

        JPanel principal = new JPanel(new BorderLayout(10, 10));
        principal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        principal.add(form, BorderLayout.NORTH);
        principal.add(btnSalvar, BorderLayout.CENTER);
        principal.add(scroll, BorderLayout.SOUTH);

        add(principal);
        carregar();
    }

    private void carregar() {
        model.setRowCount(0);
        List<Categoria> categorias = categoriaDAO.listar();
        for (Categoria categoria : categorias) {
            model.addRow(new Object[]{categoria.getId(), categoria.getNome(), categoria.getDescricao()});
        }
    }
}
