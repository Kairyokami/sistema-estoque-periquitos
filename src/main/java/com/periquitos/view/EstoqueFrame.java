package com.periquitos.view;

import com.periquitos.dao.CategoriaDAO;
import com.periquitos.dao.FornecedorDAO;
import com.periquitos.dao.ProdutoDAO;
import com.periquitos.db.DatabaseManager;
import com.periquitos.model.Categoria;
import com.periquitos.model.Fornecedor;
import com.periquitos.model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class ProdutoFrame extends JFrame {
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private final FornecedorDAO fornecedorDAO = new FornecedorDAO();

    private final JTextField txtNome = new JTextField();
    private final JTextField txtCodigo = new JTextField();
    private final JComboBox<Categoria> comboCategoria = new JComboBox<>();
    private final JComboBox<Fornecedor> comboFornecedor = new JComboBox<>();
    private final JTextField txtCompra = new JTextField();
    private final JTextField txtVenda = new JTextField();
    private final JTextField txtMinimo = new JTextField();
    private final JTextField txtEstoque = new JTextField();
    private final JCheckBox chkAtivo = new JCheckBox("Ativo");
    private final DefaultTableModel tabelaModel = new DefaultTableModel(
            new Object[]{"ID", "Nome", "Código", "Categoria", "Estoque", "Preço", "Ativo"}, 0);
    private final JTable tabela = new JTable(tabelaModel);

    public ProdutoFrame() {
        setTitle("Periquitos - Produtos");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Cadastro de produtos"));

        form.add(new JLabel("Nome:"));
        form.add(txtNome);
        form.add(new JLabel("Código de barras:"));
        form.add(txtCodigo);
        form.add(new JLabel("Categoria:"));
        form.add(comboCategoria);
        form.add(new JLabel("Fornecedor:"));
        form.add(comboFornecedor);
        form.add(new JLabel("Preço de compra:"));
        form.add(txtCompra);
        form.add(new JLabel("Preço de venda:"));
        form.add(txtVenda);
        form.add(new JLabel("Estoque mínimo:"));
        form.add(txtMinimo);
        form.add(new JLabel("Estoque atual:"));
        form.add(txtEstoque);
        form.add(new JLabel("Status:"));
        form.add(chkAtivo);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnAtualizar = new JButton("Atualizar lista");

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoes.add(btnSalvar);
        botoes.add(btnAtualizar);

        btnSalvar.addActionListener(e -> salvarProduto());
        btnAtualizar.addActionListener(e -> carregarProdutos());

        JScrollPane scroll = new JScrollPane(tabela);

        JPanel principal = new JPanel(new BorderLayout(10, 10));
        principal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        principal.add(form, BorderLayout.NORTH);
        principal.add(botoes, BorderLayout.CENTER);
        principal.add(scroll, BorderLayout.SOUTH);

        add(principal);

        carregarCategorias();
        carregarFornecedores();
        carregarProdutos();
    }

    private void carregarCategorias() {
        comboCategoria.removeAllItems();
        for (Categoria categoria : categoriaDAO.listar()) {
            comboCategoria.addItem(categoria);
        }
    }

    private void carregarFornecedores() {
        comboFornecedor.removeAllItems();
        for (Fornecedor fornecedor : fornecedorDAO.listar()) {
            comboFornecedor.addItem(fornecedor);
        }
    }

    private void carregarProdutos() {
        tabelaModel.setRowCount(0);
        List<Produto> produtos = produtoDAO.listar();
        for (Produto p : produtos) {
            String nomeCategoria = "";
            for (int i = 0; i < comboCategoria.getItemCount(); i++) {
                Categoria categoria = comboCategoria.getItemAt(i);
                if (categoria.getId() == p.getCategoriaId()) {
                    nomeCategoria = categoria.getNome();
                    break;
                }
            }

            tabelaModel.addRow(new Object[]{
                    p.getId(),
                    p.getNome(),
                    p.getCodigoBarras(),
                    nomeCategoria,
                    p.getEstoqueAtual(),
                    p.getPrecoVenda(),
                    p.isAtivo() ? "Sim" : "Não"
            });
        }
    }

    private void salvarProduto() {
        try {
            Produto produto = new Produto();
            produto.setNome(txtNome.getText());
            produto.setCodigoBarras(txtCodigo.getText());
            produto.setCategoriaId(((Categoria) comboCategoria.getSelectedItem()).getId());
            produto.setFornecedorId(((Fornecedor) comboFornecedor.getSelectedItem()).getId());
            produto.setPrecoCompra(new BigDecimal(txtCompra.getText()));
            produto.setPrecoVenda(new BigDecimal(txtVenda.getText()));
            produto.setEstoqueMinimo(Integer.parseInt(txtMinimo.getText()));
            produto.setEstoqueAtual(Integer.parseInt(txtEstoque.getText()));
            produto.setAtivo(chkAtivo.isSelected());

            produtoDAO.salvar(produto);
            JOptionPane.showMessageDialog(this, "Produto salvo com sucesso!");
            limparCampos();
            carregarProdutos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCodigo.setText("");
        txtCompra.setText("");
        txtVenda.setText("");
        txtMinimo.setText("0");
        txtEstoque.setText("0");
        chkAtivo.setSelected(true);
    }
}
