package com.periquitos.view;

import com.periquitos.dao.ProdutoDAO;
import com.periquitos.model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EstoqueFrame extends JFrame {
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Nome", "Código", "Estoque atual", "Estoque mínimo", "Preço venda"}, 0);

    public EstoqueFrame() {
        setTitle("Periquitos - Estoque");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTable tabela = new JTable(model);
        JButton btnAtualizar = new JButton("Atualizar estoque");
        btnAtualizar.addActionListener(e -> carregar());

        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        painel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        painel.add(btnAtualizar, BorderLayout.SOUTH);

        add(painel);
        carregar();
    }

    private void carregar() {
        model.setRowCount(0);
        List<Produto> produtos = produtoDAO.listar();
        for (Produto produto : produtos) {
            model.addRow(new Object[]{
                    produto.getId(),
                    produto.getNome(),
                    produto.getCodigoBarras(),
                    produto.getEstoqueAtual(),
                    produto.getEstoqueMinimo(),
                    produto.getPrecoVenda()
            });
        }
    }
}
