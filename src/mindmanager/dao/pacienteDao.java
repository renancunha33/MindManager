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
public class pacienteDao {
    private Connection conexao;
    
    public pacienteDao() throws SQLException{
        this.conexao = criarConexao.getConexao();
    }
    
    //Salva os dados do paciente no banco de dados
     public void cadastroPaciente (atributos user) throws SQLException{
        String sql = "insert into paciente (nome_paciente,sexo_paciente,dia_paciente,mes_paciente,ano_paciente,dtnasc_paciente,rg_paciente,cpf_paciente" +
                ",nacionalidade_paciente,profissao_paciente,estcivil_paciente,conjugue_paciente,responsavel_paciente,rgresp_paciente,"+
                "convenio_paciente,motivo_paciente,obs_paciente,telres_paciente,telcom_paciente,cel_paciente,id_paciente,"+
                "telrec_paciente,email_paciente,rua_paciente,numero_paciente,compl_paciente,cep_paciente,bairro_paciente,"+
                "cidade_paciente,uf_paciente,pais_paciente) values (?,?,?,?,?,?,?,?,?,?,?"+
                ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        stmt.setString(1, user.getNomePaciente());
        stmt.setString(2, user.getSexoPaciente());
        stmt.setString(3, user.getDiaPaciente());
        stmt.setString(4, user.getMesPaciente());
        stmt.setString(5, user.getAnoPaciente());
        stmt.setString(6, user.getDtNascPaciente());
        stmt.setString(7, user.getRgPaciente());
        stmt.setString(8, user.getCpfPaciente());
        stmt.setString(9, user.getNacPaciente());
        stmt.setString(10, user.getProfissaoPaciente());
        stmt.setString(11, user.getEstCivilPaciente());
        stmt.setString(12, user.getConjuguePaciente());
        stmt.setString(13, user.getRespPaciente());
        stmt.setString(14, user.getRgRespPaciente());
        stmt.setString(15, user.getDescricaoConvenio());
        stmt.setString(16, user.getMotivoPaciente());
        stmt.setString(17, user.getObsPaciente());
        stmt.setString(18, user.getTelResPaciente());
        stmt.setString(19, user.getTelComPaciente());
        stmt.setString(20, user.getCelPaciente());
        stmt.setString(21, user.getIdPaciente()); 
        stmt.setString(22, user.getTelRecPaciente());
        stmt.setString(23, user.getEmailPaciente());
        stmt.setString(24, user.getRuaPaciente());
        stmt.setString(25, user.getNumeroPaciente());
        stmt.setString(26, user.getComplPaciente());
        stmt.setString(27, user.getCepPaciente());
        stmt.setString(28, user.getBairroPaciente());
        stmt.setString(29, user.getCidadePaciente());
        stmt.setString(30, user.getUfPaciente());
        stmt.setString(31, user.getPaisPaciente());
       stmt.execute();
       stmt.close();
       
    }
     
     //Exclui os dados do paciente
      public void excluirPaciente (atributos at) throws SQLException {
        String sql = "Delete from paciente where codigo_paciente=?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setLong(1, at.getCodigoPaciente());
        stmt.execute();
        stmt.close();
        
    }
      //Altera os dados do paciente
      public void alteraPaciente(atributos at) throws SQLException{
        String sql = "update paciente set nome_paciente=?,sexo_paciente=?,dia_paciente=?,mes_paciente=?,ano_paciente=?,dtnasc_paciente=?,rg_paciente=?,cpf_paciente=?" +
                ",nacionalidade_paciente=?,profissao_paciente=?,estcivil_paciente=?,conjugue_paciente=?,responsavel_paciente=?,rgresp_paciente=?,"+
                "convenio_paciente=?,motivo_paciente=?,obs_paciente=?,telres_paciente=?,telcom_paciente=?,cel_paciente=?,id_paciente=?,"+
                "telrec_paciente=?,email_paciente=?,rua_paciente=?,numero_paciente=?,compl_paciente=?,cep_paciente=?,bairro_paciente=?,"+
                "cidade_paciente=?,uf_paciente=?,pais_paciente=? where codigo_paciente=?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        
        stmt.setString(1, at.getNomePaciente());
        stmt.setString(2, at.getSexoPaciente());
        stmt.setString(3, at.getDiaPaciente());
        stmt.setString(4, at.getMesPaciente()); 
        stmt.setString(5, at.getAnoPaciente());  
        stmt.setString(6, at.getDtNascPaciente());
        stmt.setString(7, at.getRgPaciente());
        stmt.setString(8, at.getCpfPaciente());
        stmt.setString(9, at.getNacPaciente());
        stmt.setString(10, at.getProfissaoPaciente());
        stmt.setString(11, at.getEstCivilPaciente());
        stmt.setString(12, at.getConjuguePaciente());
        stmt.setString(13, at.getRespPaciente());
        stmt.setString(14,at.getRgRespPaciente());
        stmt.setString(15, at.getConvenioPaciente());
        stmt.setString(16, at.getMotivoPaciente());
        stmt.setString(17, at.getObsPaciente());
        stmt.setString(18, at.getTelResPaciente());
        stmt.setString(19, at.getTelComPaciente());
        stmt.setString(20, at.getCelPaciente());
        stmt.setString(21, at.getIdPaciente()); 
        stmt.setString(22, at.getTelRecPaciente());
        stmt.setString(23, at.getEmailPaciente());
        stmt.setString(24, at.getRuaPaciente());
        stmt.setString(25, at.getNumeroPaciente());
        stmt.setString(26, at.getComplPaciente());
        stmt.setString(27, at.getCepPaciente());
        stmt.setString(28, at.getBairroPaciente());
        stmt.setString(29, at.getCidadePaciente());
        stmt.setString(30, at.getUfPaciente());
        stmt.setString(31, at.getPaisPaciente());
        stmt.setLong(32, at.getCodigoPaciente());
        
        
        stmt.execute();
        stmt.close();
    }
      
      //Salva Anamnese no banco de dados
      public void adicionaAnamnese(atributos at) throws SQLException{
        String sql = "update paciente set anamnese_paciente=? where nome_paciente=?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        stmt.setString(1, at.getAnamnese());
        stmt.setString(2, at.getNomePaciente());
        
        
        stmt.execute();
        stmt.close();
    }
      
      //Salva laudo no banco de dados
       public void adicionaLaudo(atributos at) throws SQLException{
        String sql = "update paciente set laudo_paciente=? where nome_paciente=?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        stmt.setString(1, at.getLaudo());
        stmt.setString(2, at.getNomePaciente());
        
        
        stmt.execute();
        stmt.close();
    }

     //Realiza a pesquisa de paciente por nome
      public List <atributos> getListaNomePaciente(String nome) throws SQLException{
        String sql = "select *from paciente where nome_paciente like ? order by nome_paciente";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, nome);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
             at.setCodigoPaciente(Long.valueOf(rs.getString("codigo_paciente")));
             at.setNomePaciente(rs.getString("nome_paciente"));
             at.setSexoPaciente(rs.getString("sexo_paciente"));
             at.setDiaPaciente(rs.getString("dia_paciente"));
             at.setMesPaciente(rs.getString("mes_paciente"));  
             at.setAnoPaciente(rs.getString("ano_paciente")); 
             at.setDtNascPaciente(rs.getString("dtnasc_paciente"));
             at.setRgPaciente(rs.getString("rg_paciente"));
             at.setCpfPaciente(rs.getString("cpf_paciente"));
             at.setNacPaciente(rs.getString("nacionalidade_paciente"));
             at.setProfissaoPaciente(rs.getString("profissao_paciente"));
             at.setEstCivilPaciente(rs.getString("estcivil_paciente"));
             at.setConjuguePaciente(rs.getString("conjugue_paciente"));
             at.setRespPaciente(rs.getString("responsavel_paciente"));
             at.setRgRespPaciente(rs.getString("rgresp_paciente"));
             at.setConvenioPaciente(rs.getString("convenio_paciente"));
             at.setMotivoPaciente(rs.getString("motivo_paciente"));
             at.setObsPaciente(rs.getString("obs_paciente"));
             at.setTelResPaciente(rs.getString("telres_paciente"));
             at.setTelComPaciente(rs.getString("telcom_paciente"));
             at.setCelPaciente(rs.getString("cel_paciente"));
             at.setIdPaciente(rs.getString("id_paciente"));
             at.setTelRecPaciente(rs.getString("telrec_paciente"));
             at.setEmailPaciente(rs.getString("email_paciente"));
             at.setRuaPaciente(rs.getString("rua_paciente"));
             at.setNumeroPaciente(rs.getString("numero_paciente"));
             at.setComplPaciente(rs.getString("compl_paciente"));
             at.setCepPaciente(rs.getString("cep_paciente"));
             at.setBairroPaciente(rs.getString("bairro_paciente"));
             at.setCidadePaciente(rs.getString("cidade_paciente"));
             at.setUfPaciente(rs.getString("uf_paciente"));
             at.setPaisPaciente(rs.getString("pais_paciente"));
             at.setLaudo(rs.getString("laudo_paciente"));
             at.setAnamnese(rs.getString("anamnese_paciente"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
      //Realiza a pesquisa da paciente por código
      public List <atributos> getListaCodigoPaciente(String cd) throws SQLException{
        String sql = "select *from paciente where codigo_paciente like ?";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, cd);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
             at.setCodigoPaciente(Long.valueOf(rs.getString("codigo_paciente")));
             at.setNomePaciente(rs.getString("nome_paciente"));
             at.setSexoPaciente(rs.getString("sexo_paciente"));
             at.setDiaPaciente(rs.getString("dia_paciente"));
             at.setMesPaciente(rs.getString("mes_paciente"));  
             at.setAnoPaciente(rs.getString("ano_paciente")); 
             at.setDtNascPaciente(rs.getString("dtnasc_paciente"));
             at.setRgPaciente(rs.getString("rg_paciente"));
             at.setCpfPaciente(rs.getString("cpf_paciente"));
             at.setNacPaciente(rs.getString("nacionalidade_paciente"));
             at.setProfissaoPaciente(rs.getString("profissao_paciente"));
             at.setEstCivilPaciente(rs.getString("estcivil_paciente"));
             at.setConjuguePaciente(rs.getString("conjugue_paciente"));
             at.setRespPaciente(rs.getString("responsavel_paciente"));
             at.setRgRespPaciente(rs.getString("rgresp_paciente"));
             at.setConvenioPaciente(rs.getString("convenio_paciente"));
             at.setMotivoPaciente(rs.getString("motivo_paciente"));
             at.setObsPaciente(rs.getString("obs_paciente"));
             at.setTelResPaciente(rs.getString("telres_paciente"));
             at.setTelComPaciente(rs.getString("telcom_paciente"));
             at.setCelPaciente(rs.getString("cel_paciente"));
             at.setIdPaciente(rs.getString("id_paciente"));
             at.setTelRecPaciente(rs.getString("telrec_paciente"));
             at.setEmailPaciente(rs.getString("email_paciente"));
             at.setRuaPaciente(rs.getString("rua_paciente"));
             at.setNumeroPaciente(rs.getString("numero_paciente"));
             at.setComplPaciente(rs.getString("compl_paciente"));
             at.setCepPaciente(rs.getString("cep_paciente"));
             at.setBairroPaciente(rs.getString("bairro_paciente"));
             at.setCidadePaciente(rs.getString("cidade_paciente"));
             at.setUfPaciente(rs.getString("uf_paciente"));
             at.setPaisPaciente(rs.getString("pais_paciente"));
             at.setLaudo(rs.getString("laudo_paciente"));
             at.setAnamnese(rs.getString("anamnese_paciente"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
      
      //Realiza a pesquisa de paciente por RG
      public List <atributos> getListaRGPaciente(String rg) throws SQLException{
        String sql = "select *from paciente where rg_paciente like ? order by nome_paciente";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, rg);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
             at.setCodigoPaciente(Long.valueOf(rs.getString("codigo_paciente")));
             at.setNomePaciente(rs.getString("nome_paciente"));
             at.setSexoPaciente(rs.getString("sexo_paciente"));
             at.setDiaPaciente(rs.getString("dia_paciente"));
             at.setMesPaciente(rs.getString("mes_paciente"));  
             at.setAnoPaciente(rs.getString("ano_paciente")); 
             at.setDtNascPaciente(rs.getString("dtnasc_paciente"));
             at.setRgPaciente(rs.getString("rg_paciente"));
             at.setCpfPaciente(rs.getString("cpf_paciente"));
             at.setNacPaciente(rs.getString("nacionalidade_paciente"));
             at.setProfissaoPaciente(rs.getString("profissao_paciente"));
             at.setEstCivilPaciente(rs.getString("estcivil_paciente"));
             at.setConjuguePaciente(rs.getString("conjugue_paciente"));
             at.setRespPaciente(rs.getString("responsavel_paciente"));
             at.setRgRespPaciente(rs.getString("rgresp_paciente"));
             at.setConvenioPaciente(rs.getString("convenio_paciente"));
             at.setMotivoPaciente(rs.getString("motivo_paciente"));
             at.setObsPaciente(rs.getString("obs_paciente"));
             at.setTelResPaciente(rs.getString("telres_paciente"));
             at.setTelComPaciente(rs.getString("telcom_paciente"));
             at.setCelPaciente(rs.getString("cel_paciente"));
             at.setIdPaciente(rs.getString("id_paciente"));
             at.setTelRecPaciente(rs.getString("telrec_paciente"));
             at.setEmailPaciente(rs.getString("email_paciente"));
             at.setRuaPaciente(rs.getString("rua_paciente"));
             at.setNumeroPaciente(rs.getString("numero_paciente"));
             at.setComplPaciente(rs.getString("compl_paciente"));
             at.setCepPaciente(rs.getString("cep_paciente"));
             at.setBairroPaciente(rs.getString("bairro_paciente"));
             at.setCidadePaciente(rs.getString("cidade_paciente"));
             at.setUfPaciente(rs.getString("uf_paciente"));
             at.setPaisPaciente(rs.getString("pais_paciente"));
             at.setLaudo(rs.getString("laudo_paciente"));
             at.setAnamnese(rs.getString("anamnese_paciente"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
      
      //Realiza a pesquisa de paciente por cpf
      public List <atributos> getListaCPFPaciente(String cpf) throws SQLException{
        String sql = "select *from paciente where cpf_paciente like ? order by nome_paciente";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, cpf);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
             at.setCodigoPaciente(Long.valueOf(rs.getString("codigo_paciente")));
             at.setNomePaciente(rs.getString("nome_paciente"));
             at.setSexoPaciente(rs.getString("sexo_paciente"));
             at.setDiaPaciente(rs.getString("dia_paciente"));
             at.setMesPaciente(rs.getString("mes_paciente"));  
             at.setAnoPaciente(rs.getString("ano_paciente")); 
             at.setDtNascPaciente(rs.getString("dtnasc_paciente"));
             at.setRgPaciente(rs.getString("rg_paciente"));
             at.setCpfPaciente(rs.getString("cpf_paciente"));
             at.setNacPaciente(rs.getString("nacionalidade_paciente"));
             at.setProfissaoPaciente(rs.getString("profissao_paciente"));
             at.setEstCivilPaciente(rs.getString("estcivil_paciente"));
             at.setConjuguePaciente(rs.getString("conjugue_paciente"));
             at.setRespPaciente(rs.getString("responsavel_paciente"));
             at.setRgRespPaciente(rs.getString("rgresp_paciente"));
             at.setConvenioPaciente(rs.getString("convenio_paciente"));
             at.setMotivoPaciente(rs.getString("motivo_paciente"));
             at.setObsPaciente(rs.getString("obs_paciente"));
             at.setTelResPaciente(rs.getString("telres_paciente"));
             at.setTelComPaciente(rs.getString("telcom_paciente"));
             at.setCelPaciente(rs.getString("cel_paciente"));
             at.setIdPaciente(rs.getString("id_paciente"));
             at.setTelRecPaciente(rs.getString("telrec_paciente"));
             at.setEmailPaciente(rs.getString("email_paciente"));
             at.setRuaPaciente(rs.getString("rua_paciente"));
             at.setNumeroPaciente(rs.getString("numero_paciente"));
             at.setComplPaciente(rs.getString("compl_paciente"));
             at.setCepPaciente(rs.getString("cep_paciente"));
             at.setBairroPaciente(rs.getString("bairro_paciente"));
             at.setCidadePaciente(rs.getString("cidade_paciente"));
             at.setUfPaciente(rs.getString("uf_paciente"));
             at.setPaisPaciente(rs.getString("pais_paciente"));
             at.setLaudo(rs.getString("laudo_paciente"));
             at.setAnamnese(rs.getString("anamnese_paciente"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
      
      //Realiza a pesquisa de convênios
      public List <atributos> getListaConvenio(String conv) throws SQLException{
      String sql = "select *from convenio where ds_convenio like ? order by ds_convenio";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, conv);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
             at.setCodigoConvenio(Long.valueOf(rs.getString("codigo_convenio")));
             at.setDescricaoConvenio(rs.getString("ds_convenio"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
}