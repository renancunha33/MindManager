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
public class convenioDao {
    
    private Connection conexao;
    
    public convenioDao () throws SQLException{
        this.conexao = criarConexao.getConexao();
    }
    
    public void cadastroConvenio(atributos convenio) throws SQLException{
        String sql = "insert into convenio (cnpj_convenio,ds_convenio"+
                ",tel_convenio,email_convenio,obs_convenio) values(?,?,?,?,?)";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        stmt.setString(1, convenio.getCnpj());
        stmt.setString(2, convenio.getDescricaoConvenio());
        stmt.setString(3, convenio.getTelConvenio());
        stmt.setString(4, convenio.getEmailConvenio());
        stmt.setString(5, convenio.getObsConvenio());
        
        stmt.execute();
        stmt.close();
    }
    
    public void excluirConvenio (atributos at) throws SQLException {
        String sql = "Delete from convenio where codigo_convenio=?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setLong(1, at.getCodigoConvenio());
        stmt.execute();
        stmt.close();
        
    }
    
    public void alteraConvenio(atributos at) throws SQLException{
        String sql = "update convenio set cnpj_convenio=?,ds_convenio=?,tel_convenio=?" +
                ",email_convenio=?,obs_convenio=? where codigo_convenio=?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        
        
        stmt.setString(1, at.getCnpj());
        stmt.setString(2, at.getDescricaoConvenio());
        stmt.setString(3, at.getTelConvenio());
        stmt.setString(4, at.getEmailConvenio());
        stmt.setString(5, at.getObsConvenio());
        stmt.setLong(6, at.getCodigoConvenio());
        
        
        stmt.execute();
        stmt.close();
    }
    
    public List <atributos> getListaDescricao(String ds) throws SQLException{
        String sql = "select *from convenio where ds_convenio like ? order by ds_convenio";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, ds);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
             at.setCodigoConvenio(Long.valueOf(rs.getString("codigo_convenio")));
             at.setCnpj(rs.getString("cnpj_convenio"));
             at.setDescricaoConvenio(rs.getString("ds_convenio"));
             at.setTelConvenio(rs.getString("tel_convenio"));
             at.setEmailConvenio(rs.getString("email_convenio"));
             at.setObsConvenio(rs.getString("obs_convenio"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
    
    public List <atributos> getListaCodigo(String codigo) throws SQLException{
        String sql = "select *from convenio where codigo_convenio like ?";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, codigo);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
             at.setCodigoConvenio(Long.valueOf(rs.getString("codigo_convenio")));
             at.setCnpj(rs.getString("cnpj_convenio"));
             at.setDescricaoConvenio(rs.getString("ds_convenio"));
             at.setTelConvenio(rs.getString("tel_convenio"));
             at.setEmailConvenio(rs.getString("email_convenio"));
             at.setObsConvenio(rs.getString("obs_convenio"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
    
     public List <atributos> getListaCnpj(String cnpj) throws SQLException{
        String sql = "select *from convenio where cnpj_convenio like ? order by ds_convenio";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, cnpj);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
             at.setCodigoConvenio(Long.valueOf(rs.getString("codigo_convenio")));
             at.setCnpj(rs.getString("cnpj_convenio"));
             at.setDescricaoConvenio(rs.getString("ds_convenio"));
             at.setTelConvenio(rs.getString("tel_convenio"));
             at.setEmailConvenio(rs.getString("email_convenio"));
             at.setObsConvenio(rs.getString("obs_convenio"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
    
      
}
