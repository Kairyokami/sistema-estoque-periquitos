package com.periquitos.dao;

import com.periquitos.db.DatabaseManager;
import com.periquitos.model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void salvar(Produto produto) {
        String sql = "INSERT INTO produtos (nome, codigo_barras, categoria_id, fornecedor_id, preco_compra, preco_venda, estoque_minimo, estoque_atual, ativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getCodigoBarras());
            stmt.setInt(3, produto.getCategoriaId());
            stmt.setInt(4, produto.getFornecedorId());
            stmt.setBigDecimal(5, produto.getPrecoCompra());
            stmt.setBigDecimal(6, produto.getPrecoVenda());
            stmt.setInt(7, produto.getEstoqueMinimo());
            stmt.setInt(8, produto.getEstoqueAtual());
            stmt.setBoolean(9, produto.isAtivo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar produto", e);
        }
    }

    public List<Produto> listar() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos ORDER BY nome";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setCodigoBarras(rs.getString("codigo_barras"));
                produto.setCategoriaId(rs.getInt("categoria_id"));
                produto.setFornecedorId(rs.getInt("fornecedor_id"));
                produto.setPrecoCompra(rs.getBigDecimal("preco_compra"));
                produto.setPrecoVenda(rs.getBigDecimal("preco_venda"));
                produto.setEstoqueMinimo(rs.getInt("estoque_minimo"));
                produto.setEstoqueAtual(rs.getInt("estoque_atual"));
                produto.setAtivo(rs.getBoolean("ativo"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos", e);
        }
        return produtos;
    }

    public void atualizarEstoque(int produtoId, int quantidade) {
        String sql = "UPDATE produtos SET estoque_atual = estoque_atual + ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantidade);
            stmt.setInt(2, produtoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar estoque", e);
        }
    }
}
