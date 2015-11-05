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
public class psicologoDao {
    
    private Connection conexao;
    
    public psicologoDao () throws SQLException{
        this.conexao = criarConexao.getConexao();
    }
    
     public void cadastroPsicologo(atributos psicologo) throws SQLException{
        String sql = "insert into psicologo (crp_psicologo,nome_psicologo,dia_psicologo,"+
                "mes_psicologo,ano_psicologo,dtnasc_psicologo,rg_psicologo,cpf_psicologo,"+
                "obs_psicologo,telres_psicologo,telcom_psicologo"+
                ",cel_psicologo,id_psicologo,telrec_psicologo,email_psicologo) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        stmt.setString(1, psicologo.getCrp());
        stmt.setString(2, psicologo.getNomePsicologo());
        stmt.setString(3, psicologo.getDiaPsicologo());
        stmt.setString(4, psicologo.getMesPsicologo());
        stmt.setString(5, psicologo.getAnoPsicologo());
        stmt.setString(6, psicologo.getDtNascPsicologo());
        stmt.setString(7, psicologo.getRgPsicologo());
        stmt.setString(8, psicologo.getCpfPsicologo());
        stmt.setString(9, psicologo.getObsPsicologo());
        stmt.setString(10, psicologo.getTelResPsicologo());
        stmt.setString(11, psicologo.getTelComPsicologo());
        stmt.setString(12, psicologo.getCelPsicologo());
        stmt.setString(13, psicologo.getIdPsicologo());
        stmt.setString(14, psicologo.getTelRecPsicologo());
        stmt.setString(15, psicologo.getEmailPsicologo());
        
        stmt.execute();
        stmt.close();
    }
     
     public void excluirPsicologo (atributos at) throws SQLException {
        String sql = "Delete from psicologo where codigo_psicologo=?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setLong(1, at.getCodigoPsicologo());
        stmt.execute();
        stmt.close();
        
    }
     
     public void alteraPsicologo(atributos at) throws SQLException{
        String sql = "update psicologo set crp_psicologo=?," +
                "nome_psicologo=?,dia_psicologo=?,mes_psicologo=?,ano_psicologo=?,dtnasc_psicologo=?,rg_psicologo=?,cpf_psicologo=?" +
                ",obs_psicologo=?, telres_psicologo=?,telcom_psicologo=?,cel_psicologo=?"+
                ",id_psicologo=?,telrec_psicologo=?,email_psicologo=? where codigo_psicologo=?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        
        stmt.setString(1, at.getCrp());
        stmt.setString(2, at.getNomePsicologo());
        stmt.setString(3, at.getDiaPsicologo());
        stmt.setString(4, at.getMesPsicologo());
        stmt.setString(5, at.getAnoPsicologo());
        stmt.setString(6, at.getDtNascPsicologo());
        stmt.setString(7, at.getRgPsicologo());
        stmt.setString(8, at.getCpfPsicologo());
        stmt.setString(9, at.getObsPsicologo());
        stmt.setString(10, at.getTelResPsicologo());
        stmt.setString(11, at.getTelComPsicologo());
        stmt.setString(12, at.getCelPsicologo());
        stmt.setString(13, at.getIdPsicologo());
        stmt.setString(14, at.getTelRecPsicologo());
        stmt.setString(15, at.getEmailPsicologo());
        stmt.setLong(16, at.getCodigoPsicologo());
        
        
        stmt.execute();
        stmt.close();
    }
     
     public List <atributos> getListaNome(String nome) throws SQLException{
        String sql = "select *from psicologo where nome_psicologo like ? order by nome_psicologo";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, nome);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
             at.setCodigoPsicologo(Long.valueOf(rs.getString("codigo_psicologo")));
             at.setCrp(rs.getString("crp_psicologo"));
             at.setNomePsicologo(rs.getString("nome_psicologo"));
             at.setDiaPsicologo(rs.getString("dia_psicologo"));
             at.setMesPsicologo(rs.getString("mes_psicologo"));
             at.setAnoPsicologo(rs.getString("ano_psicologo"));
             at.setDtNascPsicologo(rs.getString("dtnasc_psicologo"));
             at.setRgPsicologo(rs.getString("rg_psicologo"));
             at.setCpfPsicologo(rs.getString("cpf_psicologo"));
             at.setObsPsicologo(rs.getString("obs_psicologo"));
             at.setTelResPsicologo(rs.getString("telres_psicologo"));
             at.setTelComPsicologo(rs.getString("telcom_psicologo"));
             at.setCelPsicologo(rs.getString("cel_psicologo"));
             at.setIdPsicologo(rs.getString("id_psicologo"));
             at.setTelRecPsicologo(rs.getString("telrec_psicologo"));
             at.setEmailPsicologo(rs.getString("email_psicologo"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
     
     public List <atributos> getListaCodigo(String codigo) throws SQLException{
        String sql = "select *from psicologo where codigo_psicologo like ?";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, codigo);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
            at.setCodigoPsicologo(Long.valueOf(rs.getString("codigo_psicologo")));
             at.setCrp(rs.getString("crp_psicologo"));
             at.setNomePsicologo(rs.getString("nome_psicologo"));
             at.setDiaPsicologo(rs.getString("dia_psicologo"));
             at.setMesPsicologo(rs.getString("mes_psicologo"));
             at.setAnoPsicologo(rs.getString("ano_psicologo"));
             at.setDtNascPsicologo(rs.getString("dtnasc_psicologo"));
             at.setRgPsicologo(rs.getString("rg_psicologo"));
             at.setCpfPsicologo(rs.getString("cpf_psicologo"));
             at.setObsPsicologo(rs.getString("obs_psicologo"));
             at.setTelResPsicologo(rs.getString("telres_psicologo"));
             at.setTelComPsicologo(rs.getString("telcom_psicologo"));
             at.setCelPsicologo(rs.getString("cel_psicologo"));
             at.setIdPsicologo(rs.getString("id_psicologo"));
             at.setTelRecPsicologo(rs.getString("telrec_psicologo"));
             at.setEmailPsicologo(rs.getString("email_psicologo"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
     
     public List <atributos> getListaCrp(String crp) throws SQLException{
        String sql = "select *from psicologo where crp_psicologo like ? order by nome_psicologo";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, crp);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
             at.setCodigoPsicologo(Long.valueOf(rs.getString("codigo_psicologo")));
             at.setCrp(rs.getString("crp_psicologo"));
             at.setNomePsicologo(rs.getString("nome_psicologo"));
             at.setDiaPsicologo(rs.getString("dia_psicologo"));
             at.setMesPsicologo(rs.getString("mes_psicologo"));
             at.setAnoPsicologo(rs.getString("ano_psicologo"));
             at.setDtNascPsicologo(rs.getString("dtnasc_psicologo"));
             at.setRgPsicologo(rs.getString("rg_psicologo"));
             at.setCpfPsicologo(rs.getString("cpf_psicologo"));
             at.setObsPsicologo(rs.getString("obs_psicologo"));
             at.setTelResPsicologo(rs.getString("telres_psicologo"));
             at.setTelComPsicologo(rs.getString("telcom_psicologo"));
             at.setCelPsicologo(rs.getString("cel_psicologo"));
             at.setIdPsicologo(rs.getString("id_psicologo"));
             at.setTelRecPsicologo(rs.getString("telrec_psicologo"));
             at.setEmailPsicologo(rs.getString("email_psicologo"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
    
}
