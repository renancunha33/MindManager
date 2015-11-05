/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mindmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mindmanager.bancodedados.criarConexao;
import mindmanager.logica.atributos;

/**
 *
 * @author EagleTech
 */
public class contaDao {
    
     private Connection conexao;
    
    public contaDao () throws SQLException{
        this.conexao = criarConexao.getConexao();
    }
    
     public void cadastroConta(atributos conta) throws SQLException{
        String sql = "insert into conta (ds_conta,valor_conta,dia_conta,mes_conta,ano_conta,vencimento_conta"+
                ",obs_conta,status_conta) values(?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        stmt.setString(1, conta.getDsContas());
        stmt.setString(2, conta.getValorContas());
        stmt.setString(3, conta.getDiaConta());
        stmt.setString(4, conta.getMesConta());
        stmt.setString(5, conta.getAnoConta());
        stmt.setString(6, conta.getVencimentoContas());
        stmt.setString(7, conta.getObsContas());
        stmt.setString(8, conta.getStatusContas());
       
        
        stmt.execute();
        stmt.close();
    }
     
      public void excluirConta (atributos at) throws SQLException {
        String sql = "Delete from conta where codigo_conta=?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setLong(1, at.getCodigoContas());
        stmt.execute();
        stmt.close();
        
    }
      
       public void alteraConta(atributos at) throws SQLException{
        String sql = "update conta set ds_conta=?," +
                "valor_conta=?,dia_conta=?,mes_conta=?,ano_conta=?,vencimento_conta=?,obs_conta=?" +
                ",status_conta=? where codigo_conta=?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        
        stmt.setString(1, at.getDsContas());
        stmt.setString(2, at.getValorContas());
        stmt.setString(3, at.getDiaConta());
        stmt.setString(4, at.getMesConta());
        stmt.setString(5, at.getAnoConta());
        stmt.setString(6, at.getVencimentoContas());
        stmt.setString(7, at.getObsContas());
        stmt.setString(8, at.getStatusContas());
        stmt.setLong(9, at.getCodigoContas());
        
        
        stmt.execute();
        stmt.close();
    }
       
        public List <atributos> getListaDescricao(String ds) throws SQLException{
        String sql = "select *from conta where ds_conta like ? order by ds_conta";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, ds);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
             at.setCodigoContas(Long.valueOf(rs.getString("codigo_conta")));
             at.setDsContas(rs.getString("ds_conta"));
             at.setValorContas(rs.getString("valor_conta"));
             at.setDiaConta(rs.getString("dia_conta"));
             at.setMesConta(rs.getString("mes_conta"));
             at.setAnoConta(rs.getString("ano_conta"));
             at.setVencimentoContas(rs.getString("vencimento_conta"));
             at.setObsContas(rs.getString("obs_conta"));
             at.setStatusContas(rs.getString("status_conta"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
        
        public List <atributos> getListaCodigo(String codigo) throws SQLException{
        String sql = "select *from conta where codigo_conta like ? order by codigo_conta";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, codigo);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
             at.setCodigoContas(Long.valueOf(rs.getString("codigo_conta")));
             at.setDsContas(rs.getString("ds_conta"));
             at.setValorContas(rs.getString("valor_conta"));
             at.setDiaConta(rs.getString("dia_conta"));
             at.setMesConta(rs.getString("mes_conta"));
             at.setAnoConta(rs.getString("ano_conta"));
             at.setVencimentoContas(rs.getString("vencimento_conta"));
             at.setObsContas(rs.getString("obs_conta"));
             at.setStatusContas(rs.getString("status_conta"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
        
        public List <atributos> getListaStatus(String status) throws SQLException{
        String sql = "select *from conta where status_conta like ? order by vencimento_conta";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, status);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
             at.setCodigoContas(Long.valueOf(rs.getString("codigo_conta")));
             at.setDsContas(rs.getString("ds_conta"));
             at.setValorContas(rs.getString("valor_conta"));
             at.setDiaConta(rs.getString("dia_conta"));
             at.setMesConta(rs.getString("mes_conta"));
             at.setAnoConta(rs.getString("ano_conta"));
             at.setVencimentoContas(rs.getString("vencimento_conta"));
             at.setObsContas(rs.getString("obs_conta"));
             at.setStatusContas(rs.getString("status_conta"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
    
}
