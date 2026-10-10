package com.periquitos.view;

import com.periquitos.dao.ClienteDAO;
import com.periquitos.model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClienteFrame extends JFrame {
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Nome", "CPF", "Telefone", "Endereço"}, 0);

    public ClienteFrame() {
        setTitle("Periquitos - Clientes");
        setSize(900, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        JTextField txtNome = new JTextField();
        JTextField txtCpf = new JTextField();
        JTextField txtTelefone = new JTextField();
        JTextField txtEndereco = new JTextField();

        form.add(new JLabel("Nome:"));
        form.add(txtNome);
        form.add(new JLabel("CPF:"));
        form.add(txtCpf);
        form.add(new JLabel("Telefone:"));
        form.add(txtTelefone);
        form.add(new JLabel("Endereço:"));
        form.add(txtEndereco);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            Cliente cliente = new Cliente(txtNome.getText(), txtCpf.getText(), txtTelefone.getText(), txtEndereco.getText());
            clienteDAO.salvar(cliente);
            txtNome.setText("");
            txtCpf.setText("");
            txtTelefone.setText("");
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
        List<Cliente> clientes = clienteDAO.listar();
        for (Cliente cliente : clientes) {
            model.addRow(new Object[]{
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getCpf(),
                    cliente.getTelefone(),
                    cliente.getEndereco()
            });
        }
    }
}


