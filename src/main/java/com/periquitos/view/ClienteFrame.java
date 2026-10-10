package com.periquitos.view;

import com.periquitos.dao.FornecedorDAO;
import com.periquitos.model.Fornecedor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FornecedorFrame extends JFrame {
    private final FornecedorDAO fornecedorDAO = new FornecedorDAO();
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Nome", "CNPJ", "Telefone", "Email", "Endereço"}, 0);

    public FornecedorFrame() {
        setTitle("Periquitos - Fornecedores");
        setSize(900, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        JTextField txtNome = new JTextField();
        JTextField txtCnpj = new JTextField();
        JTextField txtTelefone = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtEndereco = new JTextField();

        form.add(new JLabel("Nome:"));
        form.add(txtNome);
        form.add(new JLabel("CNPJ:"));
        form.add(txtCnpj);
        form.add(new JLabel("Telefone:"));
        form.add(txtTelefone);
        form.add(new JLabel("Email:"));
        form.add(txtEmail);
        form.add(new JLabel("Endereço:"));
        form.add(txtEndereco);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            Fornecedor fornecedor = new Fornecedor(txtNome.getText(), txtCnpj.getText(), txtTelefone.getText(), txtEmail.getText(), txtEndereco.getText());
            fornecedorDAO.salvar(fornecedor);
            txtNome.setText("");
            txtCnpj.setText("");
            txtTelefone.setText("");
            txtEmail.setText("");
            txtEndereco.setText("");
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
        List<Fornecedor> fornecedores = fornecedorDAO.listar();
        for (Fornecedor fornecedor : fornecedores) {
            model.addRow(new Object[]{
                    fornecedor.getId(),
                    fornecedor.getNome(),
                    fornecedor.getCnpj(),
                    fornecedor.getTelefone(),
                    fornecedor.getEmail(),
                    fornecedor.getEndereco()
            });
        }
    }
}
