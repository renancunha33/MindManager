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
 * @author Eagle Tech
 */
public class funcionarioDao {
    
    private Connection conexao;
    
    public funcionarioDao() throws SQLException{
        this.conexao = criarConexao.getConexao();
    }
    
    public void cadastroFuncionario (atributos user) throws SQLException{
        String sql = "insert into funcionario(nome_funcionario,sexo_funcionario,dia_funcionario,"+
                "mes_funcionario,ano_funcionario,dtnasc_funcionario,rg_funcionario,cpf_funcionario" +
                ",nac_funcionario,estcivil_funcionario,cargo_funcionario,salario_funcionario,status_funcionario,"+
                "obs_funcionario,telres_funcionario,telcom_funcionario,cel_funcionario,id_funcionario,"+
                "telrec_funcionario,email_funcionario,cep_funcionario,rua_funcionario,numero_funcionario,compl_funcionario,bairro_funcionario,"+
                "cidade_funcionario,uf_funcionario,pais_funcionario) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?"+
                ",?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        stmt.setString(1, user.getNomeFuncionario());
         stmt.setString(2, user.getSexoFuncionario());
         stmt.setString(3, user.getDiaFuncionario());
         stmt.setString(4, user.getMesFuncionario());
         stmt.setString(5, user.getAnoFuncionario());
        stmt.setString(6, user.getDtNascFuncionario());
        stmt.setString(7, user.getRgFuncionario());
        stmt.setString(8, user.getCpfFuncionario());
        stmt.setString(9, user.getNacFuncionario());
        stmt.setString(10, user.getEstCivilFuncionario());
        stmt.setString(11, user.getCargoFuncionario());
        stmt.setString(12, user.getSalarioFuncionario());
        stmt.setString(13, user.getStatusFuncionario());
        stmt.setString(14, user.getObsFuncionario());
        stmt.setString(15, user.getTelResFuncionario());
        stmt.setString(16, user.getTelComFuncionario());
        stmt.setString(17, user.getCelFuncionario());
        stmt.setString(18, user.getIdFuncionario()); 
        stmt.setString(19, user.getTelRecFuncionario());
        stmt.setString(20, user.getEmailFuncionario());
        stmt.setString(21, user.getCepFuncionario());
        stmt.setString(22, user.getRuaFuncionario());
        stmt.setString(23, user.getNumFuncionario());
        stmt.setString(24, user.getComplFuncionario());
        stmt.setString(25, user.getBairroFuncionario());
        stmt.setString(26, user.getCidadeFuncionario());
        stmt.setString(27, user.getUfFuncionario());
        stmt.setString(28, user.getPaisFuncionario());
       stmt.execute();
       stmt.close();
       
    }
    
    public void excluirFuncionario (atributos at) throws SQLException {
        String sql = "Delete from funcionario where codigo_funcionario=?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setLong(1, at.getCodigoFuncionario());
        stmt.execute();
        stmt.close();
        
    }
    
     public void alteraFuncionario(atributos user) throws SQLException{
        String sql = "update funcionario set nome_funcionario=?,sexo_funcionario=?,dia_funcionario=?,mes_funcionario=?,ano_funcionario=?,"+
                "dtnasc_funcionario=?,rg_funcionario=?,cpf_funcionario=?" +
                ",nac_funcionario=?,estcivil_funcionario=?,cargo_funcionario=?,salario_funcionario=?,status_funcionario=?,"+
                "obs_funcionario=?,telres_funcionario=?,telcom_funcionario=?,cel_funcionario=?,id_funcionario=?,"+
                "telrec_funcionario=?,email_funcionario=?,cep_funcionario=?,rua_funcionario=?,numero_funcionario=?,compl_funcionario=?,bairro_funcionario=?,"+
                "cidade_funcionario=?,uf_funcionario=?,pais_funcionario=? where codigo_funcionario=?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        
         stmt.setString(1, user.getNomeFuncionario());
         stmt.setString(2, user.getSexoFuncionario());
         stmt.setString(3, user.getDiaFuncionario());
         stmt.setString(4, user.getMesFuncionario());
         stmt.setString(5, user.getAnoFuncionario());
        stmt.setString(6, user.getDtNascFuncionario());
        stmt.setString(7, user.getRgFuncionario());
        stmt.setString(8, user.getCpfFuncionario());
        stmt.setString(9, user.getNacFuncionario());
        stmt.setString(10, user.getEstCivilFuncionario());
        stmt.setString(11, user.getCargoFuncionario());
        stmt.setString(12, user.getSalarioFuncionario());
        stmt.setString(13, user.getStatusFuncionario());
        stmt.setString(14, user.getObsFuncionario());
        stmt.setString(15, user.getTelResFuncionario());
        stmt.setString(16, user.getTelComFuncionario());
        stmt.setString(17, user.getCelFuncionario());
        stmt.setString(18, user.getIdFuncionario()); 
        stmt.setString(19, user.getTelRecFuncionario());
        stmt.setString(20, user.getEmailFuncionario());
        stmt.setString(21, user.getCepFuncionario());
        stmt.setString(22, user.getRuaFuncionario());
        stmt.setString(23, user.getNumFuncionario());
        stmt.setString(24, user.getComplFuncionario());
        stmt.setString(25, user.getBairroFuncionario());
        stmt.setString(26, user.getCidadeFuncionario());
        stmt.setString(27, user.getUfFuncionario());
        stmt.setString(28, user.getPaisFuncionario());
        stmt.setLong(29, user.getCodigoFuncionario());
        
        
        stmt.execute();
        stmt.close();
    }
     
     public List <atributos> getListaNomeFuncionario(String nome) throws SQLException{
        String sql = "select *from funcionario where nome_funcionario like ? order by nome_funcionario";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, nome);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
             at.setCodigoFuncionario(Long.valueOf(rs.getString("codigo_funcionario")));
             at.setNomeFuncionario(rs.getString("nome_funcionario"));
              at.setSexoFuncionario(rs.getString("sexo_funcionario"));
              at.setDiaFuncionario(rs.getString("dia_funcionario"));
              at.setMesFuncionario(rs.getString("mes_funcionario"));
              at.setAnoFuncionario(rs.getString("ano_funcionario"));
             at.setDtNascFuncionario(rs.getString("dtnasc_funcionario"));
             at.setRgFuncionario(rs.getString("rg_funcionario"));
             at.setCpfFuncionario(rs.getString("cpf_funcionario"));
             at.setNacFuncionario(rs.getString("nac_funcionario"));
             at.setEstCivilFuncionario(rs.getString("estcivil_funcionario"));
             at.setCargoFuncionario(rs.getString("cargo_funcionario"));
             at.setSalarioFuncionario(rs.getString("salario_funcionario"));
             at.setStatusFuncionario(rs.getString("status_funcionario"));
             at.setObsFuncionario(rs.getString("obs_funcionario"));
             at.setTelResFuncionario(rs.getString("telres_funcionario"));
             at.setTelComFuncionario(rs.getString("telcom_funcionario"));
             at.setCelFuncionario(rs.getString("cel_funcionario"));
             at.setIdFuncionario(rs.getString("id_funcionario"));
             at.setTelRecFuncionario(rs.getString("telrec_funcionario"));
             at.setEmailFuncionario(rs.getString("email_funcionario"));
              at.setCepFuncionario(rs.getString("cep_funcionario"));
             at.setRuaFuncionario(rs.getString("rua_funcionario"));
             at.setNumFuncionario(rs.getString("numero_funcionario"));
             at.setComplFuncionario(rs.getString("compl_funcionario"));
             at.setBairroFuncionario(rs.getString("bairro_funcionario"));
             at.setCidadeFuncionario(rs.getString("cidade_funcionario"));
             at.setUfFuncionario(rs.getString("uf_funcionario"));
             at.setPaisFuncionario(rs.getString("pais_funcionario"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
     
     public List <atributos> getListaCodigo(String codigo) throws SQLException{
        String sql = "select *from funcionario where codigo_funcionario like ? order by nome_funcionario";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, codigo);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
              at.setCodigoFuncionario(Long.valueOf(rs.getString("codigo_funcionario")));
             at.setNomeFuncionario(rs.getString("nome_funcionario"));
              at.setSexoFuncionario(rs.getString("sexo_funcionario"));
              at.setDiaFuncionario(rs.getString("dia_funcionario"));
              at.setMesFuncionario(rs.getString("mes_funcionario"));
              at.setAnoFuncionario(rs.getString("ano_funcionario"));
             at.setDtNascFuncionario(rs.getString("dtnasc_funcionario"));
             at.setRgFuncionario(rs.getString("rg_funcionario"));
             at.setCpfFuncionario(rs.getString("cpf_funcionario"));
             at.setNacFuncionario(rs.getString("nac_funcionario"));
             at.setEstCivilFuncionario(rs.getString("estcivil_funcionario"));
             at.setCargoFuncionario(rs.getString("cargo_funcionario"));
             at.setSalarioFuncionario(rs.getString("salario_funcionario"));
             at.setStatusFuncionario(rs.getString("status_funcionario"));
             at.setObsFuncionario(rs.getString("obs_funcionario"));
             at.setTelResFuncionario(rs.getString("telres_funcionario"));
             at.setTelComFuncionario(rs.getString("telcom_funcionario"));
             at.setCelFuncionario(rs.getString("cel_funcionario"));
             at.setIdFuncionario(rs.getString("id_funcionario"));
             at.setTelRecFuncionario(rs.getString("telrec_funcionario"));
             at.setEmailFuncionario(rs.getString("email_funcionario"));
              at.setCepFuncionario(rs.getString("cep_funcionario"));
             at.setRuaFuncionario(rs.getString("rua_funcionario"));
             at.setNumFuncionario(rs.getString("numero_funcionario"));
             at.setComplFuncionario(rs.getString("compl_funcionario"));
             at.setBairroFuncionario(rs.getString("bairro_funcionario"));
             at.setCidadeFuncionario(rs.getString("cidade_funcionario"));
             at.setUfFuncionario(rs.getString("uf_funcionario"));
             at.setPaisFuncionario(rs.getString("pais_funcionario"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
     
     public List <atributos> getListaCargo(String cargo) throws SQLException{
        String sql = "select *from funcionario where cargo_funcionario like ? order by nome_funcionario";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, cargo);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
              at.setCodigoFuncionario(Long.valueOf(rs.getString("codigo_funcionario")));
             at.setNomeFuncionario(rs.getString("nome_funcionario"));
              at.setSexoFuncionario(rs.getString("sexo_funcionario"));
              at.setDiaFuncionario(rs.getString("dia_funcionario"));
              at.setMesFuncionario(rs.getString("mes_funcionario"));
              at.setAnoFuncionario(rs.getString("ano_funcionario"));
             at.setDtNascFuncionario(rs.getString("dtnasc_funcionario"));
             at.setRgFuncionario(rs.getString("rg_funcionario"));
             at.setCpfFuncionario(rs.getString("cpf_funcionario"));
             at.setNacFuncionario(rs.getString("nac_funcionario"));
             at.setEstCivilFuncionario(rs.getString("estcivil_funcionario"));
             at.setCargoFuncionario(rs.getString("cargo_funcionario"));
             at.setSalarioFuncionario(rs.getString("salario_funcionario"));
             at.setStatusFuncionario(rs.getString("status_funcionario"));
             at.setObsFuncionario(rs.getString("obs_funcionario"));
             at.setTelResFuncionario(rs.getString("telres_funcionario"));
             at.setTelComFuncionario(rs.getString("telcom_funcionario"));
             at.setCelFuncionario(rs.getString("cel_funcionario"));
             at.setIdFuncionario(rs.getString("id_funcionario"));
             at.setTelRecFuncionario(rs.getString("telrec_funcionario"));
             at.setEmailFuncionario(rs.getString("email_funcionario"));
              at.setCepFuncionario(rs.getString("cep_funcionario"));
             at.setRuaFuncionario(rs.getString("rua_funcionario"));
             at.setNumFuncionario(rs.getString("numero_funcionario"));
             at.setComplFuncionario(rs.getString("compl_funcionario"));
             at.setBairroFuncionario(rs.getString("bairro_funcionario"));
             at.setCidadeFuncionario(rs.getString("cidade_funcionario"));
             at.setUfFuncionario(rs.getString("uf_funcionario"));
             at.setPaisFuncionario(rs.getString("pais_funcionario"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
     
     public List <atributos> getListaSalario(String salario) throws SQLException{
        String sql = "select *from funcionario where salario_funcionario like ? order by nome_funcionario";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, salario);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
              at.setCodigoFuncionario(Long.valueOf(rs.getString("codigo_funcionario")));
             at.setNomeFuncionario(rs.getString("nome_funcionario"));
              at.setSexoFuncionario(rs.getString("sexo_funcionario"));
              at.setDiaFuncionario(rs.getString("dia_funcionario"));
              at.setMesFuncionario(rs.getString("mes_funcionario"));
              at.setAnoFuncionario(rs.getString("ano_funcionario"));
             at.setDtNascFuncionario(rs.getString("dtnasc_funcionario"));
             at.setRgFuncionario(rs.getString("rg_funcionario"));
             at.setCpfFuncionario(rs.getString("cpf_funcionario"));
             at.setNacFuncionario(rs.getString("nac_funcionario"));
             at.setEstCivilFuncionario(rs.getString("estcivil_funcionario"));
             at.setCargoFuncionario(rs.getString("cargo_funcionario"));
             at.setSalarioFuncionario(rs.getString("salario_funcionario"));
             at.setStatusFuncionario(rs.getString("status_funcionario"));
             at.setObsFuncionario(rs.getString("obs_funcionario"));
             at.setTelResFuncionario(rs.getString("telres_funcionario"));
             at.setTelComFuncionario(rs.getString("telcom_funcionario"));
             at.setCelFuncionario(rs.getString("cel_funcionario"));
             at.setIdFuncionario(rs.getString("id_funcionario"));
             at.setTelRecFuncionario(rs.getString("telrec_funcionario"));
             at.setEmailFuncionario(rs.getString("email_funcionario"));
              at.setCepFuncionario(rs.getString("cep_funcionario"));
             at.setRuaFuncionario(rs.getString("rua_funcionario"));
             at.setNumFuncionario(rs.getString("numero_funcionario"));
             at.setComplFuncionario(rs.getString("compl_funcionario"));
             at.setBairroFuncionario(rs.getString("bairro_funcionario"));
             at.setCidadeFuncionario(rs.getString("cidade_funcionario"));
             at.setUfFuncionario(rs.getString("uf_funcionario"));
             at.setPaisFuncionario(rs.getString("pais_funcionario"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
     
      public List <atributos> getListaStatus(String status) throws SQLException{
        String sql = "select *from funcionario where status_funcionario like ? order by nome_funcionario";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, status);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
              at.setCodigoFuncionario(Long.valueOf(rs.getString("codigo_funcionario")));
             at.setNomeFuncionario(rs.getString("nome_funcionario"));
              at.setSexoFuncionario(rs.getString("sexo_funcionario"));
              at.setDiaFuncionario(rs.getString("dia_funcionario"));
              at.setMesFuncionario(rs.getString("mes_funcionario"));
              at.setAnoFuncionario(rs.getString("ano_funcionario"));
             at.setDtNascFuncionario(rs.getString("dtnasc_funcionario"));
             at.setRgFuncionario(rs.getString("rg_funcionario"));
             at.setCpfFuncionario(rs.getString("cpf_funcionario"));
             at.setNacFuncionario(rs.getString("nac_funcionario"));
             at.setEstCivilFuncionario(rs.getString("estcivil_funcionario"));
             at.setCargoFuncionario(rs.getString("cargo_funcionario"));
             at.setSalarioFuncionario(rs.getString("salario_funcionario"));
             at.setStatusFuncionario(rs.getString("status_funcionario"));
             at.setObsFuncionario(rs.getString("obs_funcionario"));
             at.setTelResFuncionario(rs.getString("telres_funcionario"));
             at.setTelComFuncionario(rs.getString("telcom_funcionario"));
             at.setCelFuncionario(rs.getString("cel_funcionario"));
             at.setIdFuncionario(rs.getString("id_funcionario"));
             at.setTelRecFuncionario(rs.getString("telrec_funcionario"));
             at.setEmailFuncionario(rs.getString("email_funcionario"));
              at.setCepFuncionario(rs.getString("cep_funcionario"));
             at.setRuaFuncionario(rs.getString("rua_funcionario"));
             at.setNumFuncionario(rs.getString("numero_funcionario"));
             at.setComplFuncionario(rs.getString("compl_funcionario"));
             at.setBairroFuncionario(rs.getString("bairro_funcionario"));
             at.setCidadeFuncionario(rs.getString("cidade_funcionario"));
             at.setUfFuncionario(rs.getString("uf_funcionario"));
             at.setPaisFuncionario(rs.getString("pais_funcionario"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
     
     public List <atributos> getListaCpf(String nome) throws SQLException{
        String sql = "select *from funcionario where cpf_funcionario like ? order by nome_funcionario";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, nome);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
              at.setCodigoFuncionario(Long.valueOf(rs.getString("codigo_funcionario")));
             at.setNomeFuncionario(rs.getString("nome_funcionario"));
              at.setSexoFuncionario(rs.getString("sexo_funcionario"));
              at.setDiaFuncionario(rs.getString("dia_funcionario"));
              at.setMesFuncionario(rs.getString("mes_funcionario"));
              at.setAnoFuncionario(rs.getString("ano_funcionario"));
             at.setDtNascFuncionario(rs.getString("dtnasc_funcionario"));
             at.setRgFuncionario(rs.getString("rg_funcionario"));
             at.setCpfFuncionario(rs.getString("cpf_funcionario"));
             at.setNacFuncionario(rs.getString("nac_funcionario"));
             at.setEstCivilFuncionario(rs.getString("estcivil_funcionario"));
             at.setCargoFuncionario(rs.getString("cargo_funcionario"));
             at.setSalarioFuncionario(rs.getString("salario_funcionario"));
             at.setStatusFuncionario(rs.getString("status_funcionario"));
             at.setObsFuncionario(rs.getString("obs_funcionario"));
             at.setTelResFuncionario(rs.getString("telres_funcionario"));
             at.setTelComFuncionario(rs.getString("telcom_funcionario"));
             at.setCelFuncionario(rs.getString("cel_funcionario"));
             at.setIdFuncionario(rs.getString("id_funcionario"));
             at.setTelRecFuncionario(rs.getString("telrec_funcionario"));
             at.setEmailFuncionario(rs.getString("email_funcionario"));
              at.setCepFuncionario(rs.getString("cep_funcionario"));
             at.setRuaFuncionario(rs.getString("rua_funcionario"));
             at.setNumFuncionario(rs.getString("numero_funcionario"));
             at.setComplFuncionario(rs.getString("compl_funcionario"));
             at.setBairroFuncionario(rs.getString("bairro_funcionario"));
             at.setCidadeFuncionario(rs.getString("cidade_funcionario"));
             at.setUfFuncionario(rs.getString("uf_funcionario"));
             at.setPaisFuncionario(rs.getString("pais_funcionario"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
     
     
}
