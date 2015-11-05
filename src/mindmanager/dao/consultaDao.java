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
public class consultaDao {
     private Connection conexao;
    
    public consultaDao() throws SQLException{
        this.conexao = criarConexao.getConexao();
    }
    
     public void agendarConsulta (atributos at) throws SQLException{
        String sql = "insert into consultas (nomepac_consulta,sexopac_consulta,datanascpac_consulta" +
                ",dia_consulta,mes_consulta,ano_consulta,data_consulta,hora_consulta,minuto_consulta,horario_consulta"+
                ",psicologo_consulta,status_consulta,valor_consulta,statuspag_consulta)" +
                "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        stmt.setString(1, at.getNomePaciente());
        stmt.setString(2, at.getSexoPaciente());
        stmt.setString(3, at.getDtNascPaciente());
        stmt.setString(4, at.getDiaConsulta());
        stmt.setString(5, at.getMesConsulta());
        stmt.setString(6, at.getAnoConsulta());
        stmt.setString(7, at.getDataConsulta());
        stmt.setString(8, at.getHoraConsulta());
        stmt.setString(9, at.getMinutosConsulta());
        stmt.setString(10, at.getHorarioConsulta()); 
        stmt.setString(11, at.getNomePsicologo());
        stmt.setString(12, at.getStatusConsulta());
        stmt.setString(13, at.getValorConsulta());
        stmt.setString(14, at.getStatusPagConsulta());
       stmt.execute();
       stmt.close();
       
    }
     
     public void excluirConsulta (atributos at) throws SQLException {
        String sql = "Delete from consultas where codigo_consulta=?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setLong(1, at.getCodigoConsulta());
        stmt.execute();
        stmt.close();
        
    }
     
     public void alteraConsulta(atributos at) throws SQLException{
        String sql = "update consultas set nomepac_consulta=?," +
                "sexopac_consulta=?,datanascpac_consulta=?,dia_consulta=?,mes_consulta=?,"+
                "ano_consulta=?,data_consulta=?,hora_consulta=?,minuto_consulta=?,horario_consulta=?,"+
                "psicologo_consulta=?,status_consulta=?,valor_consulta=?"+
                ",statuspag_consulta=? where codigo_consulta=?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        
        stmt.setString(1, at.getNomePacConsulta());
        stmt.setString(2, at.getSexoPacConsulta());
        stmt.setString(3, at.getDataNascPacConsulta());
        stmt.setString(4, at.getDiaConsulta());
        stmt.setString(5, at.getMesConsulta()); 
        stmt.setString(6, at.getAnoConsulta()); 
        stmt.setString(7, at.getDataConsulta());
        stmt.setString(8, at.getHoraConsulta());
        stmt.setString(9, at.getMinutosConsulta());
        stmt.setString(10, at.getHorarioConsulta());
        stmt.setString(11, at.getNomePsicologo());
        stmt.setString(12, at.getStatusConsulta());
        stmt.setString(13, at.getValorConsulta());
        stmt.setString(14, at.getStatusPagConsulta());
        stmt.setLong(15, at.getCodigoConsulta());
        
        
        stmt.execute();
        stmt.close();
    }
     
    
     public List <atributos> getListaConsultaHora(String hora) throws SQLException{
        String sql = "select *from consultas where horario_consulta like ? order by horario_consulta";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, hora);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
             at.setNomePacConsulta(rs.getString("nomepac_consulta"));
             at.setSexoPacConsulta(rs.getString("sexopac_consulta"));
             at.setDataNascPacConsulta(rs.getString("datanascpac_consulta"));
             at.setCodigoConsulta(Long.valueOf(rs.getString("codigo_consulta")));
             at.setDiaConsulta(rs.getString("dia_consulta"));
             at.setMesConsulta(rs.getString("mes_consulta"));
             at.setAnoConsulta(rs.getString("ano_consulta"));
             at.setDataConsulta(rs.getString("data_consulta"));
             at.setHoraConsulta(rs.getString("hora_consulta"));
             at.setMinutosConsulta(rs.getString("minuto_consulta"));
             at.setHorarioConsulta(rs.getString("horario_consulta"));
             at.setPsicologoConsulta(rs.getString("psicologo_consulta"));
             at.setStatusConsulta(rs.getString("status_consulta"));
             at.setValorConsulta(rs.getString("valor_consulta"));
             at.setStatusPagConsulta(rs.getString("statuspag_consulta"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
     
     public List <atributos> getListaConsultaData(String data) throws SQLException{
        String sql = "select *from consultas where data_consulta like ? order by horario_consulta";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, data);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
             at.setNomePacConsulta(rs.getString("nomepac_consulta"));
             at.setSexoPacConsulta(rs.getString("sexopac_consulta"));
             at.setDataNascPacConsulta(rs.getString("datanascpac_consulta"));
             at.setCodigoConsulta(Long.valueOf(rs.getString("codigo_consulta")));
             at.setDiaConsulta(rs.getString("dia_consulta"));
             at.setMesConsulta(rs.getString("mes_consulta"));
             at.setAnoConsulta(rs.getString("ano_consulta"));
             at.setDataConsulta(rs.getString("data_consulta"));
             at.setHoraConsulta(rs.getString("hora_consulta"));
             at.setMinutosConsulta(rs.getString("minuto_consulta"));
             at.setHorarioConsulta(rs.getString("horario_consulta"));
             at.setPsicologoConsulta(rs.getString("psicologo_consulta"));
             at.setStatusConsulta(rs.getString("status_consulta"));
             at.setValorConsulta(rs.getString("valor_consulta"));
             at.setStatusPagConsulta(rs.getString("statuspag_consulta"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
     
     public List <atributos> getListaConsultaPsicologo(String psicologo) throws SQLException{
        String sql = "select *from consultas where psicologo_consulta like ?";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, psicologo);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
            at.setNomePacConsulta(rs.getString("nomepac_consulta"));
             at.setSexoPacConsulta(rs.getString("sexopac_consulta"));
             at.setDataNascPacConsulta(rs.getString("datanascpac_consulta"));
             at.setCodigoConsulta(Long.valueOf(rs.getString("codigo_consulta")));
             at.setDiaConsulta(rs.getString("dia_consulta"));
             at.setMesConsulta(rs.getString("mes_consulta"));
             at.setAnoConsulta(rs.getString("ano_consulta"));
             at.setDataConsulta(rs.getString("data_consulta"));
             at.setHoraConsulta(rs.getString("hora_consulta"));
             at.setMinutosConsulta(rs.getString("minuto_consulta"));
             at.setHorarioConsulta(rs.getString("horario_consulta"));
             at.setPsicologoConsulta(rs.getString("psicologo_consulta"));
             at.setStatusConsulta(rs.getString("status_consulta"));
             at.setValorConsulta(rs.getString("valor_consulta"));
             at.setStatusPagConsulta(rs.getString("statuspag_consulta"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
     
     public List <atributos> getListaConsultaPaciente(String paciente) throws SQLException{
        String sql = "select *from consultas where nomepac_consulta like ?";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, paciente);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
             at.setNomePacConsulta(rs.getString("nomepac_consulta"));
             at.setSexoPacConsulta(rs.getString("sexopac_consulta"));
             at.setDataNascPacConsulta(rs.getString("datanascpac_consulta"));
             at.setCodigoConsulta(Long.valueOf(rs.getString("codigo_consulta")));
             at.setDiaConsulta(rs.getString("dia_consulta"));
             at.setMesConsulta(rs.getString("mes_consulta"));
             at.setAnoConsulta(rs.getString("ano_consulta"));
             at.setDataConsulta(rs.getString("data_consulta"));
             at.setHoraConsulta(rs.getString("hora_consulta"));
             at.setMinutosConsulta(rs.getString("minuto_consulta"));
             at.setHorarioConsulta(rs.getString("horario_consulta"));
             at.setPsicologoConsulta(rs.getString("psicologo_consulta"));
             at.setStatusConsulta(rs.getString("status_consulta"));
             at.setValorConsulta(rs.getString("valor_consulta"));
             at.setStatusPagConsulta(rs.getString("statuspag_consulta"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
     
     public List <atributos> getListaConsultaStatus(String status) throws SQLException{
        String sql = "select *from consultas where status_consulta like ?";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, status);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
             at.setNomePacConsulta(rs.getString("nomepac_consulta"));
             at.setSexoPacConsulta(rs.getString("sexopac_consulta"));
             at.setDataNascPacConsulta(rs.getString("datanascpac_consulta"));
             at.setCodigoConsulta(Long.valueOf(rs.getString("codigo_consulta")));
             at.setDiaConsulta(rs.getString("dia_consulta"));
             at.setMesConsulta(rs.getString("mes_consulta"));
             at.setAnoConsulta(rs.getString("ano_consulta"));
             at.setDataConsulta(rs.getString("data_consulta"));
             at.setHoraConsulta(rs.getString("hora_consulta"));
             at.setMinutosConsulta(rs.getString("minuto_consulta"));
             at.setHorarioConsulta(rs.getString("horario_consulta"));
             at.setPsicologoConsulta(rs.getString("psicologo_consulta"));
             at.setStatusConsulta(rs.getString("status_consulta"));
             at.setValorConsulta(rs.getString("valor_consulta"));
             at.setStatusPagConsulta(rs.getString("statuspag_consulta"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
     
     public List <atributos> getListaConsultaPag(String pag) throws SQLException{
        String sql = "select *from consultas where statuspag_consulta like ?";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, pag);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
             at.setNomePacConsulta(rs.getString("nomepac_consulta"));
             at.setSexoPacConsulta(rs.getString("sexopac_consulta"));
             at.setDataNascPacConsulta(rs.getString("datanascpac_consulta"));
             at.setCodigoConsulta(Long.valueOf(rs.getString("codigo_consulta")));
             at.setDiaConsulta(rs.getString("dia_consulta"));
             at.setMesConsulta(rs.getString("mes_consulta"));
             at.setAnoConsulta(rs.getString("ano_consulta"));
             at.setDataConsulta(rs.getString("data_consulta"));
             at.setHoraConsulta(rs.getString("hora_consulta"));
             at.setMinutosConsulta(rs.getString("minuto_consulta"));
             at.setHorarioConsulta(rs.getString("horario_consulta"));
             at.setPsicologoConsulta(rs.getString("psicologo_consulta"));
             at.setStatusConsulta(rs.getString("status_consulta"));
             at.setValorConsulta(rs.getString("valor_consulta"));
             at.setStatusPagConsulta(rs.getString("statuspag_consulta"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
     
      public List <atributos> getListaPacientes(String pacientes) throws SQLException{
      String sql = "select *from paciente where nome_paciente like ? order by nome_paciente";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, pacientes);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
             at.setCodigoPaciente(Long.valueOf(rs.getString("codigo_paciente")));
             at.setNomePaciente(rs.getString("nome_paciente"));
             at.setSexoPaciente(rs.getString("sexo_paciente"));
             at.setDtNascPaciente(rs.getString("dtnasc_paciente"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
     
      public List <atributos> getListaPsicologos(String psicologos) throws SQLException{
       String sql = "select *from psicologo where nome_psicologo like ? order by nome_psicologo";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         stmt.setString(1, psicologos);
         ResultSet rs = stmt.executeQuery();
         
         List<atributos> minhaLista = new ArrayList<atributos>();
         
         while(rs.next()){
             atributos at = new atributos();
             at.setCodigoPsicologo(Long.valueOf(rs.getString("codigo_psicologo")));
             at.setNomePsicologo(rs.getString("nome_psicologo"));
             minhaLista.add(at);
         }
         rs.close();
         stmt.close();
         return minhaLista;
    }
     
    
}
