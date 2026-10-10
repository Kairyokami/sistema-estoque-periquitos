package com.periquitos.db;

import com.periquitos.model.Categoria;
import com.periquitos.model.Cliente;
import com.periquitos.model.Fornecedor;
import com.periquitos.model.Produto;
import com.periquitos.model.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:h2:file:./data/periquitos;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, "sa", "");
    }

    public static void initialize() {
        try (Connection connection = getConnection(); Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS usuarios (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nome VARCHAR(100), " +
                    "login VARCHAR(50) UNIQUE, " +
                    "senha VARCHAR(255), " +
                    "perfil VARCHAR(30), " +
                    "ativo BOOLEAN DEFAULT TRUE, " +
                    "data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

            stmt.execute("CREATE TABLE IF NOT EXISTS categorias (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nome VARCHAR(100), " +
                    "descricao VARCHAR(255))");

            stmt.execute("CREATE TABLE IF NOT EXISTS fornecedores (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nome VARCHAR(120), " +
                    "cnpj VARCHAR(30), " +
                    "telefone VARCHAR(30), " +
                    "email VARCHAR(100), " +
                    "endereco VARCHAR(255))");

            stmt.execute("CREATE TABLE IF NOT EXISTS clientes (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nome VARCHAR(120), " +
                    "cpf VARCHAR(20), " +
                    "telefone VARCHAR(30), " +
                    "endereco VARCHAR(255))");

            stmt.execute("CREATE TABLE IF NOT EXISTS produtos (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nome VARCHAR(120), " +
                    "codigo_barras VARCHAR(40), " +
                    "categoria_id INT, " +
                    "fornecedor_id INT, " +
                    "preco_compra DECIMAL(10,2), " +
                    "preco_venda DECIMAL(10,2), " +
                    "estoque_minimo INT DEFAULT 0, " +
                    "estoque_atual INT DEFAULT 0, " +
                    "ativo BOOLEAN DEFAULT TRUE, " +
                    "data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (categoria_id) REFERENCES categorias(id), " +
                    "FOREIGN KEY (fornecedor_id) REFERENCES fornecedores(id))");

            seedDefaultData();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inicializar banco de dados", e);
        }
    }

    private static void seedDefaultData() {
        try (Connection connection = getConnection()) {
            try (PreparedStatement checkStmt = connection.prepareStatement(
                    "SELECT COUNT(*) FROM usuarios WHERE login = ?")) {
                checkStmt.setString(1, "admin");
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    try (PreparedStatement insertStmt = connection.prepareStatement(
                            "INSERT INTO usuarios (nome, login, senha, perfil, ativo) VALUES (?, ?, ?, ?, true)")) {
                        insertStmt.setString(1, "Administrador");
                        insertStmt.setString(2, "admin");
                        insertStmt.setString(3, "admin123");
                        insertStmt.setString(4, "ADMIN");
                        insertStmt.executeUpdate();
                    }
                }
            }

            try (PreparedStatement checkCategory = connection.prepareStatement(
                    "SELECT COUNT(*) FROM categorias")) {
                ResultSet rs = checkCategory.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    String[] dados = {"Bebidas", "Limpeza", "Padaria", "Higiene", "Mercearia"};
                    for (String nome : dados) {
                        try (PreparedStatement insert = connection.prepareStatement(
                                "INSERT INTO categorias (nome, descricao) VALUES (?, ?)")) {
                            insert.setString(1, nome);
                            insert.setString(2, "Categoria de " + nome.toLowerCase());
                            insert.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao popular dados iniciais", e);
        }
    }

    public static List<Categoria> listarCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias ORDER BY nome";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("id"));
                categoria.setNome(rs.getString("nome"));
                categoria.setDescricao(rs.getString("descricao"));
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar categorias", e);
        }
        return categorias;
    }

    public static List<Fornecedor> listarFornecedores() {
        List<Fornecedor> fornecedores = new ArrayList<>();
        String sql = "SELECT * FROM fornecedores ORDER BY nome";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setId(rs.getInt("id"));
                fornecedor.setNome(rs.getString("nome"));
                fornecedor.setCnpj(rs.getString("cnpj"));
                fornecedor.setTelefone(rs.getString("telefone"));
                fornecedor.setEmail(rs.getString("email"));
                fornecedor.setEndereco(rs.getString("endereco"));
                fornecedores.add(fornecedor);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar fornecedores", e);
        }
        return fornecedores;
    }

    public static List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY nome";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEndereco(rs.getString("endereco"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar clientes", e);
        }
        return clientes;
    }

    public static List<Produto> listarProdutos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos ORDER BY nome";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
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
}
