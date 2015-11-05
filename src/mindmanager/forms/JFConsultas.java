package mindmanager.forms;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import mindmanager.bancodedados.ConexaoImpressao;
import mindmanager.bancodedados.criarConexao;
import mindmanager.dao.consultaDao;
import mindmanager.logica.Impressao;
import mindmanager.logica.atributos;
import net.sf.jasperreports.engine.JRException;

public class JFConsultas extends javax.swing.JDialog {

    
     private Connection conexao;
    /**
     * Creates new form NewJDialog
     */
    public JFConsultas(java.awt.Frame parent, boolean modal) throws SQLException {
        super(parent, modal);
        initComponents();
        this.conexao = criarConexao.getConexao();
        
        jTAgenda.setEnabledAt(1, false);//Desabilita a aba com a lista de pacientes
        
         
          preencheConvenio(); 
         
          for(int i=2012;i<=2020;i++){
                  jCAno.addItem(String.valueOf(i));
              }
          
         for(int i=0;i<=9;i++){
             jCHoras.addItem(("0"+i));
         }
         
         for(int i=10;i<=23;i++){
             jCHoras.addItem(String.valueOf(i));
         }
         
         for(int i=0;i<=9;i++){
             jCMinutos.addItem(("0"+i));
         }
         
         for(int i=10;i<=59;i++){
             jCMinutos.addItem(String.valueOf(i));
         }
         
         jLDtConsulta.setVisible(false);
         jLHr.setVisible(false);
         
         jTConsultas.getColumnModel().getColumn(0).setPreferredWidth(65);
         jTConsultas.getColumnModel().getColumn(1).setPreferredWidth(10);
         jTConsultas.getColumnModel().getColumn(2).setPreferredWidth(190);
         jTConsultas.getColumnModel().getColumn(3).setPreferredWidth(190);
         jTConsultas.getColumnModel().getColumn(4).setPreferredWidth(80);
         listarConsultas();
           this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/mindmanager/imagens/logo_mindmanager(48x48).png")));
    }

     DefaultTableModel tmConsultas = new DefaultTableModel(null, new String []{"Data", "Horário","Paciente", "Psicólogo(a)", "Status", "Pagamento"});
    List<atributos> consultas;
    ListSelectionModel lsmConsultas;
    
    DefaultTableModel tmPacientes = new DefaultTableModel(null, new String []{"Código", "Nome"});
    List<atributos> pacientes;
    ListSelectionModel lsmPacientes;
    
    DefaultTableModel tmPsicologos = new DefaultTableModel(null, new String []{"Código", "Nome"});
    List<atributos> psicologos;
    ListSelectionModel lsmPsicologos;
    
      public void agendarConsulta(){
            String data="";
            String horario="";
            if(verificaDados()){
                
                
                try {
            atributos at = new atributos();
            at.setNomePaciente(jTNome.getText());
            at.setSexoPaciente(jCSexo.getSelectedItem().toString());
            at.setDtNascPaciente(jTDataNasc.getText());
            at.setDiaConsulta(jCDia.getSelectedItem().toString());
            at.setMesConsulta(jCMes.getSelectedItem().toString());
            at.setAnoConsulta(jCAno.getSelectedItem().toString());
                data = jCDia.getSelectedItem().toString();
      data = data+"/"+jCMes.getSelectedItem().toString();
        data = data+"/"+jCAno.getSelectedItem();toString();
                at.setDataConsulta(data);
            at.setHoraConsulta(jCHoras.getSelectedItem().toString());
            at.setMinutosConsulta(jCMinutos.getSelectedItem().toString());
            horario=jCHoras.getSelectedItem().toString()+":";
            horario=horario+jCMinutos.getSelectedItem().toString();
            at.setHorarioConsulta(horario);
            at.setNomePsicologo(jCPsicologo.getSelectedItem().toString());
            at.setStatusConsulta(jCStatusConsulta.getSelectedItem().toString());
            at.setValorConsulta(jTValor.getText());
            at.setStatusPagConsulta(jCStatusPagamento.getSelectedItem().toString());
            consultaDao dao = new consultaDao();
            dao.agendarConsulta(at);
            desabilitaDados();
            limparDados();
            jCBusca.setSelectedIndex(0);
            //listarConsultas();
            JOptionPane.showMessageDialog(null, "Consulta marcada com sucesso.","Agendamento",JOptionPane.INFORMATION_MESSAGE); 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro com o banco de dados.\n"+ex,"Erro",JOptionPane.ERROR_MESSAGE);
        }
            }
            
        }
        
        public void excluirConsulta() throws SQLException{
            UIManager.put("OptionPane.noButtonText", "Não");  
         UIManager.put("OptionPane.yesButtonText", "Sim");
          int resp = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir esta consulta?", "Confirmação", 0,3);
         if (resp == 0){
             atributos at = new atributos();
             at.setCodigoConsulta(Long.valueOf(jTCodigoConsulta.getText() ));
                consultaDao dao;
                dao = new consultaDao();
                dao.excluirConsulta(at);
                mostraPesquisaConsulta(consultas);
                JOptionPane.showMessageDialog(null,"Consulta excluída com sucesso.","Excluir",JOptionPane.INFORMATION_MESSAGE);
                limparDados();
                listarConsultas();
            desabilitaDados();
         }
        }
        
        private void alteraConsulta() throws SQLException {
            String data="";
            String horario="";
        if(jTConsultas.getSelectedRow() != -1 || dados ==1){
            UIManager.put("OptionPane.noButtonText", "Não");  
         UIManager.put("OptionPane.yesButtonText", "Sim");
            int resp = JOptionPane.showConfirmDialog(null, "Deseja realmente alterar esta consulta?", "Confirmação", 0,3);
         if (resp == 0){
              if(verificaDados()){
                atributos at = new atributos();
                consultaDao dao =  new consultaDao();
                at.setNomePacConsulta(jTNome.getText());
                at.setSexoPacConsulta(jCSexo.getSelectedItem().toString());
                at.setDataNascPacConsulta(jTDataNasc.getText());
                at.setCodigoConsulta(Long.valueOf(jTCodigoConsulta.getText()));
                at.setDiaConsulta(jCDia.getSelectedItem().toString());
                at.setMesConsulta(jCMes.getSelectedItem().toString());
                at.setAnoConsulta(jCAno.getSelectedItem().toString());
                data = jCDia.getSelectedItem().toString();
       data = data+"/"+jCMes.getSelectedItem().toString();
        data = data+"/"+jCAno.getSelectedItem();toString();
                at.setDataConsulta(data);
                 at.setHoraConsulta(jCHoras.getSelectedItem().toString());
            at.setMinutosConsulta(jCMinutos.getSelectedItem().toString());
            horario=jCHoras.getSelectedItem().toString();
            horario=horario+":";
            horario=horario+jCMinutos.getSelectedItem().toString();
            at.setHorarioConsulta(horario);
                at.setNomePsicologo(jCPsicologo.getSelectedItem().toString());
                at.setStatusConsulta(jCStatusConsulta.getSelectedItem().toString());
                at.setValorConsulta((jTValor.getText()));
                at.setStatusPagConsulta(jCStatusPagamento.getSelectedItem().toString());
                
               
                dao.alteraConsulta(at);
                 JOptionPane.showMessageDialog(null,"Consulta alterada com sucesso.","Alterar",JOptionPane.INFORMATION_MESSAGE);
                  listarConsultas();
                 desabilitaDados();  
                 limparDados();
            }
        }
         }
           
         }
        
          public void impressaoConsultas() {
	 
	    // note que estamos chamando o novo relatório
	    InputStream inputStream = getClass().getResourceAsStream( "/Consultas.jasper" );
	 
	    // mapa de parâmetros do relatório
	    Map parametros = new HashMap();
	 
	    /*
	     * Insere o parâmetro primeiroNome no mapa, com o valor F%
	     * ou seja, todos os clientes que tenham primeiro nome começando
	     * com a letra F.
	     */
	    parametros.put( "codigoConsulta", jTCodigoConsulta.getText() );
	 
	    // outros possíveis parâmetros aqui...
	 
	    try {
	 
	        // abre o relatório
	        Impressao.openReport( "Consultas", inputStream, parametros,
	                ConexaoImpressao.getMindConnection() );
                
	  this.dispose();
	    } catch ( SQLException exc ) {
	        exc.printStackTrace();
	    } catch ( JRException exc ) {
	        exc.printStackTrace();
	    }
	 
	}
          
          public void preencheConvenio(){
         try{
              String sql = "select *from psicologo order by nome_psicologo";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery();
         
         while(rs.next()){
             jCPsicologo.addItem(rs.getString("nome_psicologo"));
         }
         
         }catch(SQLException ex){
             JOptionPane.showMessageDialog(null,"Erro ao preencher os psicólogos.\nErro: "+ex);
         }
         
     }
        
        public void validaData(){

    if(jCDia.getSelectedIndex()!=0 && jCMes.getSelectedIndex()!=0 && jCAno.getSelectedIndex()!=0 ){
        int ano = Integer.parseInt(jCAno.getSelectedItem().toString()); 
          if(jCMes.getSelectedIndex()==2 && jCDia.getSelectedIndex()>29 ||(jCMes.getSelectedIndex()==4 && jCDia.getSelectedIndex()>30 ||jCMes.getSelectedIndex()==6 && jCDia.getSelectedIndex()>30
              ||jCMes.getSelectedIndex()==9 && jCDia.getSelectedIndex()>30 ||jCMes.getSelectedIndex()==11 && jCDia.getSelectedIndex()>30 
               ||jCDia.getSelectedIndex()==29 && jCMes.getSelectedIndex()==2 && ano%4!=0)){
           jLDtInvalida.setVisible(true);
           jLDtInvalida.setText("Data inválida");
           jLDtInvalida.setForeground(Color.RED);
       }else{
            jLDtInvalida.setText("");  
            jLDtInvalida.setVisible(false);
       }
    }else{
        jLDtInvalida.setText("");  
            jLDtInvalida.setVisible(false);
    }
       }
        
           
        
        protected void listarConsultas() throws SQLException{
        consultaDao dao = new consultaDao ();
        
        switch(jCBusca.getSelectedIndex()){
            case 0:
                 consultas  = dao.getListaConsultaPaciente("%" + jTBusca.getText() + "%");
        mostraPesquisaConsulta(consultas );
               
                break;
              
            case 1:
                consultas = dao.getListaConsultaData("%"+jTBusca.getText()+"%");
        mostraPesquisaConsulta(consultas);
                break;
      
            case 2:
                  consultas = dao.getListaConsultaHora("%"+jTBusca.getText()+"%");
        mostraPesquisaConsulta(consultas );
                break;    
            case 3:
                consultas  = dao.getListaConsultaPsicologo("%" + jTBusca.getText() + "%");
                mostraPesquisaConsulta(consultas );
                 break;
            case 4:
                consultas  = dao.getListaConsultaStatus("%" + jTBusca.getText() + "%");
                mostraPesquisaConsulta(consultas );
                 break;
            case 5:
                consultas  = dao.getListaConsultaPag("%" + jTBusca.getText() + "%");
                mostraPesquisaConsulta(consultas );
                 break;
           
        }
    }
        boolean msg=true;
        private void mostraPesquisaConsulta(List<atributos> consultas) {
      while(tmConsultas.getRowCount()>0){
          tmConsultas.removeRow(0);
      }
        if(consultas.isEmpty()){
            msg=false;
      }else{
            msg=true;
          String []linha = new String [] {null, null, null, null, null, null};
          for (int i = 0; i < consultas.size(); i++){
              tmConsultas.addRow(linha);
              tmConsultas.setValueAt(consultas.get(i).getDataConsulta(), i, 0);
              tmConsultas.setValueAt(consultas.get(i).getHorarioConsulta(), i, 1);
              tmConsultas.setValueAt(consultas.get(i).getNomePacConsulta(), i, 2);
              tmConsultas.setValueAt(consultas.get(i).getPsicologoConsulta(), i, 3);
              tmConsultas.setValueAt(consultas.get(i).getStatusConsulta(), i, 4);
              tmConsultas.setValueAt(consultas.get(i).getStatusPagConsulta(), i, 5);
          }
      }
    }
        protected void listarPacientes() throws SQLException{
        consultaDao dao = new consultaDao ();
         pacientes = dao.getListaPacientes("%" + jTNome.getText() + "%");
        mostraPesquisaPacientes(pacientes);
        
    }
        
        private void mostraPesquisaPacientes(List<atributos> pacientes) {
      while(tmPacientes.getRowCount()>0){
          tmPacientes.removeRow(0);
      }
        if(pacientes.isEmpty()){
          JOptionPane.showMessageDialog(null,"Nenhum paciente encontrado.","Pacientes",JOptionPane.INFORMATION_MESSAGE);
      }else{
              jTAgenda.setEnabledAt(1, true);
              jTAgenda.setSelectedComponent(jPPacientes);  
          String []linha = new String [] {null, null};
          for (int i = 0; i < pacientes.size(); i++){
              tmPacientes.addRow(linha);
              tmPacientes.setValueAt(pacientes.get(i).getCodigoPaciente(), i, 0);
              tmPacientes.setValueAt(pacientes.get(i).getNomePaciente(), i, 1);
      
          }
      }
    }
        
     
        
   
    
        //Desabilita os campos para edição
         public void desabilitaDados(){
            jCDia.setEnabled(false);
            jCMes.setEnabled(false);
            jCAno.setEnabled(false);
            jCHoras.setEnabled(false);
            jCMinutos.setEnabled(false);
            jCPsicologo.setEnabled(false);
            jCStatusConsulta.setEnabled(false);
            jTValor.setEditable(false);
            jCStatusPagamento.setEnabled(false);
            jBSalvar.setEnabled(false);
            jBExcluir.setEnabled(false);
            jBCancelar.setEnabled(false);
           jBImprimir.setEnabled(false);
           jBAlterar.setEnabled(false);
             jBBuscarPaciente.setEnabled(false);
        
           jBNovo.setEnabled(true);
        }
         
         //Habilita os campos para edição
         public void habilitaDados(){
            jCDia.setEnabled(true);
            jCMes.setEnabled(true);
            jCAno.setEnabled(true);
            jCHoras.setEnabled(true);
            jCMinutos.setEnabled(true);
            jCPsicologo.setEnabled(true);
            jCStatusConsulta.setEnabled(true);
            jTValor.setEditable(true);
            jCStatusPagamento.setEnabled(true);
            jBSalvar.setEnabled(true);
            jBCancelar.setEnabled(true);
             jBBuscarPaciente.setEnabled(true);
       
           jBNovo.setEnabled(false);
        }
       int dados =0;//Varíavel para saber se os dados foram selecionados na lista de pesquisa ou no form inicial
         public void recebeDados(atributos at){//Joga para os Text Fields os valores selecionados no form inicial
              habilitaDados();
            jBSalvar.setEnabled(false);
            jBAlterar.setEnabled(true);
            jBImprimir.setEnabled(true);
            jBExcluir.setEnabled(true);
             jTNome.setText(at.getNomePacConsulta());
             jCSexo.setSelectedItem(at.getSexoPacConsulta());
             jTDataNasc.setText(at.getDataNascPacConsulta());
        jTCodigoConsulta.setText(String.valueOf(at.getCodigoConsulta()));
        jCDia.setSelectedItem(at.getDiaConsulta());
        jCMes.setSelectedItem(at.getMesConsulta());
        jCAno.setSelectedItem(at.getAnoConsulta());
        jLDtConsulta.setText(at.getDataNascPacConsulta());
        jCHoras.setSelectedItem(at.getHoraConsulta());
        jLHr.setText(at.getHorarioConsulta());
        jCMinutos.setSelectedItem(at.getMinutosConsulta());
        jCPsicologo.setSelectedItem(at.getPsicologoConsulta());
        jCStatusConsulta.setSelectedItem(at.getStatusConsulta());
        jTValor.setText(at.getValorConsulta());
        jCStatusPagamento.setSelectedItem(at.getStatusPagConsulta());
        dados =1;
         jBBuscarPaciente.setEnabled(true);
       
         }
         
         //Joga os dados selecionados na pesquisa para os Text Fields
          private void linhaSelecionadaConsultas(JTable tabela){
          
        if(jTConsultas.getSelectedRow()!=-1){
            habilitaDados();
            jBSalvar.setEnabled(false);
            jBAlterar.setEnabled(true);
            jBImprimir.setEnabled(true);
            jBExcluir.setEnabled(true);
        jTNome.setText(consultas.get(tabela.getSelectedRow()).getNomePacConsulta());
        jCSexo.setSelectedItem(consultas.get(tabela.getSelectedRow()).getSexoPacConsulta());
        jTDataNasc.setText(consultas.get(tabela.getSelectedRow()).getDataNascPacConsulta());
        jTCodigoConsulta.setText(String.valueOf(consultas.get(tabela.getSelectedRow()).getCodigoConsulta()));
        jCDia.setSelectedItem(consultas.get(tabela.getSelectedRow()).getDiaConsulta());
        jCMes.setSelectedItem(consultas.get(tabela.getSelectedRow()).getMesConsulta());
        jCAno.setSelectedItem(consultas.get(tabela.getSelectedRow()).getAnoConsulta());
        jLDtConsulta.setText(consultas.get(tabela.getSelectedRow()).getDataConsulta());
        jCHoras.setSelectedItem(consultas.get(tabela.getSelectedRow()).getHoraConsulta());
        jLHr.setText(consultas.get(tabela.getSelectedRow()).getHorarioConsulta());
        jCMinutos.setSelectedItem(consultas.get(tabela.getSelectedRow()).getMinutosConsulta());
        jCPsicologo.setSelectedItem(consultas.get(tabela.getSelectedRow()).getPsicologoConsulta());
        jCStatusConsulta.setSelectedItem(consultas.get(tabela.getSelectedRow()).getStatusConsulta());
        jTValor.setText(consultas.get(tabela.getSelectedRow()).getValorConsulta());
        jCStatusPagamento.setSelectedItem(consultas.get(tabela.getSelectedRow()).getStatusPagConsulta());
        jTAgenda.setSelectedComponent(jPAgendamento);
        jBBuscarPaciente.setEnabled(true);
        
        } else{
           limparDados();
       }
      
       jTBusca.setText("");
    }
          
          //Joga para o agendamento de consulta o paciente selecionado
           private void linhaSelecionadaPacientes(JTable tabela){
          
        if(JTPacientes.getSelectedRow()!=-1){
         jTNome.setText(pacientes.get(tabela.getSelectedRow()).getNomePaciente());
         jCSexo.setSelectedItem(pacientes.get(tabela.getSelectedRow()).getSexoPaciente());
         jTDataNasc.setText(pacientes.get(tabela.getSelectedRow()).getDtNascPaciente());
        jTAgenda.setSelectedComponent(jPAgendamento);
        jTAgenda.setEnabledAt(1, false);
        } else{
           limparDados();
       }
      
       jTBusca.setText("");
    }
      
        //Limpa os dados 
         public void limparDados(){
            jTNome.setText("");
            jCSexo.setSelectedIndex(0);
            jTDataNasc.setText("");
            jTCodigoConsulta.setText("");
            jCDia.setSelectedIndex(0);
            jCMes.setSelectedIndex(0);
            jCAno.setSelectedIndex(0);
            jCHoras.setSelectedIndex(0);
            jCMinutos.setSelectedIndex(0);
            jCPsicologo.setSelectedIndex(0);
            jTValor.setText("");
            valor = "";
            jCStatusConsulta.setSelectedIndex(0);
            jLObrNome.setForeground(Color.black);
             jLObrDtNasc.setForeground(Color.black);
             jLObrPsicologo.setForeground(Color.black);
            jLObrDt.setForeground(Color.black);
            
            
        }
         
         //Verifica se todos os dados obrigatórios foram preenchidos
        public boolean verificaDados(){
        if(jTNome.getText().equals("") || jTDataNasc.getText().equals("")  || jCDia.getSelectedIndex()==0 ||
                jCMes.getSelectedIndex()==0 || jCAno.getSelectedIndex()==0 ||  jLDtInvalida.getText().equals("Data inválida")||
                jCPsicologo.getSelectedIndex()==0){
            JOptionPane.showMessageDialog(null,"Há campos obrigatórios não preenchidos.","Erro.", JOptionPane.WARNING_MESSAGE);
            
            if(jCPsicologo.getSelectedIndex()==0){
                jLObrPsicologo.setForeground(Color.red);
                 jTAgenda.setSelectedComponent(jPAgendamento);
            }else{
                 jLObrPsicologo.setForeground(Color.black);
            }
                      
            
            if(jCDia.getSelectedIndex()==0 || jCMes.getSelectedIndex()==0 || jCAno.getSelectedIndex()==0
                        || jLDtInvalida.getText().equals("Data inválida")){
                    jLObrDt.setForeground(Color.red);
                    jTAgenda.setSelectedComponent(jPAgendamento);
                    jCDia.requestFocus();
                }else if((jCDia.getSelectedIndex()!=0 && jCMes.getSelectedIndex()!=0 && jCAno.getSelectedIndex()==0
                        && !jLDtInvalida.getText().equals("Data inválida"))){
                     jLObrDt.setForeground(Color.black);
                }
                
                  if(jTDataNasc.getText().equals("")){
                jLObrDtNasc.setForeground(Color.red);
                 jTAgenda.setSelectedComponent(jPAgendamento);
            }else{
                 jLObrDtNasc.setForeground(Color.black);
            }
               
                  if(jTNome.getText().equals("")){
                jLObrNome.setForeground(Color.red);
                 jTAgenda.setSelectedComponent(jPAgendamento);
            }else{
                 jLObrNome.setForeground(Color.black);
            }
               
        return false;
        }else{
            jLObrNome.setForeground(Color.black);
             jLObrDtNasc.setForeground(Color.black);
             jLObrPsicologo.setForeground(Color.black);
             jLObrDt.setForeground(Color.black);
            return true;
        }
        
    }
    
        public void sair(){
            UIManager.put("OptionPane.noButtonText", "Não");  
         UIManager.put("OptionPane.yesButtonText", "Sim");
         int x = JOptionPane.showConfirmDialog(null, "Deseja encerrar o agendamento de consultas?", "Alerta",0, 2);
        
        if (x==0) {
        this.dispose();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTAgenda = new javax.swing.JTabbedPane();
        jPAgendamento = new javax.swing.JPanel();
        jPDadosPaciente = new javax.swing.JPanel();
        jLNome = new javax.swing.JLabel();
        jLDataNasc = new javax.swing.JLabel();
        jTNome = new javax.swing.JTextField();
        jLSexo = new javax.swing.JLabel();
        jTDataNasc = new javax.swing.JFormattedTextField();
        jCSexo = new javax.swing.JComboBox();
        jBBuscarPaciente = new javax.swing.JButton();
        jLObrNome = new javax.swing.JLabel();
        jLObrSexo = new javax.swing.JLabel();
        jLObrDtNasc = new javax.swing.JLabel();
        jPConsulta = new javax.swing.JPanel();
        jLHora = new javax.swing.JLabel();
        jLPsicologo = new javax.swing.JLabel();
        jLData = new javax.swing.JLabel();
        jLCodigoConsulta = new javax.swing.JLabel();
        jTCodigoConsulta = new javax.swing.JTextField();
        jLStatusConsulta = new javax.swing.JLabel();
        jCStatusConsulta = new javax.swing.JComboBox();
        jLValor = new javax.swing.JLabel();
        jTValor = new javax.swing.JTextField();
        jLStatusPagamento = new javax.swing.JLabel();
        jCStatusPagamento = new javax.swing.JComboBox();
        jCDia = new javax.swing.JComboBox();
        jCMes = new javax.swing.JComboBox();
        jCAno = new javax.swing.JComboBox();
        jCHoras = new javax.swing.JComboBox();
        jLHorario = new javax.swing.JLabel();
        jCMinutos = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLObrDt = new javax.swing.JLabel();
        jLObrHr = new javax.swing.JLabel();
        jLObrPsicologo = new javax.swing.JLabel();
        jLObrStatusC = new javax.swing.JLabel();
        jLStatusP = new javax.swing.JLabel();
        jLDtInvalida = new javax.swing.JLabel();
        jLDtConsulta = new javax.swing.JLabel();
        jLHr = new javax.swing.JLabel();
        jCPsicologo = new javax.swing.JComboBox();
        jPBotoes = new javax.swing.JPanel();
        jBAlterar = new javax.swing.JButton();
        jBSalvar = new javax.swing.JButton();
        jBCancelar = new javax.swing.JButton();
        jBImprimir = new javax.swing.JButton();
        jBNovo = new javax.swing.JButton();
        jBExcluir = new javax.swing.JButton();
        jBPesquisa = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPPacientes = new javax.swing.JPanel();
        jPDadosPacientes = new javax.swing.JPanel();
        jSDadosPacientes = new javax.swing.JScrollPane();
        JTPacientes = new javax.swing.JTable();
        jPConsultas = new javax.swing.JPanel();
        jPDadosConsulta = new javax.swing.JPanel();
        jSConsultas = new javax.swing.JScrollPane();
        jTConsultas = new javax.swing.JTable();
        jLBusca = new javax.swing.JLabel();
        jCBusca = new javax.swing.JComboBox();
        jBBusca = new javax.swing.JButton();
        jTBusca = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Agendamento de Consultas");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        jPDadosPaciente.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do Paciente"));

        jLNome.setText("Nome:");

        jLDataNasc.setText("Data de Nascimento:");

        jTNome.setEditable(false);

        jLSexo.setText("Sexo:");

        jTDataNasc.setEditable(false);

        jCSexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "F", "M" }));
        jCSexo.setEnabled(false);

        jBBuscarPaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/Start-Menu-Search-icon.png"))); // NOI18N
        jBBuscarPaciente.setText("Buscar");
        jBBuscarPaciente.setEnabled(false);
        jBBuscarPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscarPacienteActionPerformed(evt);
            }
        });

        jLObrNome.setText("(*)");

        jLObrSexo.setText("(*)");

        jLObrDtNasc.setText("(*)");

        javax.swing.GroupLayout jPDadosPacienteLayout = new javax.swing.GroupLayout(jPDadosPaciente);
        jPDadosPaciente.setLayout(jPDadosPacienteLayout);
        jPDadosPacienteLayout.setHorizontalGroup(
            jPDadosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPDadosPacienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPDadosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBBuscarPaciente)
                    .addGroup(jPDadosPacienteLayout.createSequentialGroup()
                        .addGroup(jPDadosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLObrNome)
                            .addComponent(jLObrSexo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPDadosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPDadosPacienteLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLSexo))
                            .addComponent(jLNome))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPDadosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPDadosPacienteLayout.createSequentialGroup()
                                .addComponent(jCSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLObrDtNasc)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLDataNasc)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTDataNasc, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE))
                            .addComponent(jTNome, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPDadosPacienteLayout.setVerticalGroup(
            jPDadosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDadosPacienteLayout.createSequentialGroup()
                .addComponent(jBBuscarPaciente)
                .addGap(17, 17, 17)
                .addGroup(jPDadosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLObrNome)
                    .addComponent(jLNome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDadosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLSexo)
                    .addComponent(jCSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLDataNasc)
                    .addComponent(jTDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLObrSexo)
                    .addComponent(jLObrDtNasc))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPConsulta.setBorder(javax.swing.BorderFactory.createTitledBorder("Sobre a consulta"));

        jLHora.setText("Horário:");

        jLPsicologo.setText("Psicólogo(a):");

        jLData.setText("Data:");

        jLCodigoConsulta.setText("Código:");

        jTCodigoConsulta.setEditable(false);

        jLStatusConsulta.setText("Status da consulta:");

        jCStatusConsulta.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Em espera", "Não compareceu", "Cancelado", "Atendido" }));
        jCStatusConsulta.setEnabled(false);

        jLValor.setText("Valor da consulta:");

        jTValor.setEditable(false);
        jTValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTValorKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTValorKeyReleased(evt);
            }
        });

        jLStatusPagamento.setText("Status de pagamento:");

        jCStatusPagamento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aguardando ", "Paga" }));
        jCStatusPagamento.setEnabled(false);

        jCDia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dia", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        jCDia.setEnabled(false);
        jCDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCDiaActionPerformed(evt);
            }
        });

        jCMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mês", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" }));
        jCMes.setEnabled(false);
        jCMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCMesActionPerformed(evt);
            }
        });

        jCAno.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ano" }));
        jCAno.setEnabled(false);
        jCAno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCAnoActionPerformed(evt);
            }
        });

        jCHoras.setEnabled(false);

        jLHorario.setText(":");

        jCMinutos.setEnabled(false);

        jLabel2.setText("/");

        jLabel3.setText("/");

        jLObrDt.setText("(*)");

        jLObrHr.setText("(*)");

        jLObrPsicologo.setText("(*)");

        jLObrStatusC.setText("(*)");

        jLStatusP.setText("(*)");

        jLDtInvalida.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLDtInvalida.setForeground(new java.awt.Color(255, 0, 51));

        jLDtConsulta.setEnabled(false);

        jLHr.setEnabled(false);

        jCPsicologo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Escolha" }));
        jCPsicologo.setEnabled(false);

        javax.swing.GroupLayout jPConsultaLayout = new javax.swing.GroupLayout(jPConsulta);
        jPConsulta.setLayout(jPConsultaLayout);
        jPConsultaLayout.setHorizontalGroup(
            jPConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPConsultaLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLStatusP)
                .addGroup(jPConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPConsultaLayout.createSequentialGroup()
                        .addGroup(jPConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLValor)
                            .addComponent(jLStatusPagamento))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jCStatusPagamento, 0, 1, Short.MAX_VALUE)
                            .addComponent(jTValor, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPConsultaLayout.createSequentialGroup()
                        .addComponent(jLObrStatusC)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLStatusConsulta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCStatusConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPConsultaLayout.createSequentialGroup()
                        .addGroup(jPConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPConsultaLayout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(jPConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPConsultaLayout.createSequentialGroup()
                                        .addComponent(jLObrHr)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLHora))
                                    .addGroup(jPConsultaLayout.createSequentialGroup()
                                        .addComponent(jLObrDt)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLData))
                                    .addComponent(jLCodigoConsulta)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPConsultaLayout.createSequentialGroup()
                                .addComponent(jLObrPsicologo)
                                .addGap(2, 2, 2)
                                .addComponent(jLPsicologo, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPConsultaLayout.createSequentialGroup()
                                .addComponent(jCHoras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLHorario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCMinutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLHr))
                            .addGroup(jPConsultaLayout.createSequentialGroup()
                                .addComponent(jCDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLDtInvalida, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPConsultaLayout.createSequentialGroup()
                                .addComponent(jTCodigoConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLDtConsulta))
                            .addComponent(jCPsicologo, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPConsultaLayout.setVerticalGroup(
            jPConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTCodigoConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLCodigoConsulta)
                    .addComponent(jLDtConsulta))
                .addGap(3, 3, 3)
                .addGroup(jPConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLData)
                    .addComponent(jCDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLObrDt)
                    .addComponent(jLDtInvalida, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLHora)
                    .addComponent(jCHoras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLHorario)
                    .addComponent(jCMinutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLObrHr)
                    .addComponent(jLHr))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLPsicologo)
                    .addComponent(jLObrPsicologo)
                    .addComponent(jCPsicologo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCStatusConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLStatusConsulta)
                    .addComponent(jLObrStatusC))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLValor)
                    .addComponent(jTValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLStatusPagamento)
                    .addComponent(jCStatusPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLStatusP))
                .addGap(20, 20, 20))
        );

        jPBotoes.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jBAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/pencil (25x25).png"))); // NOI18N
        jBAlterar.setText("Alterar");
        jBAlterar.setEnabled(false);
        jBAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAlterarActionPerformed(evt);
            }
        });

        jBSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/disk (25x25).png"))); // NOI18N
        jBSalvar.setText("Salvar");
        jBSalvar.setEnabled(false);
        jBSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalvarActionPerformed(evt);
            }
        });

        jBCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/ico_delete.png"))); // NOI18N
        jBCancelar.setText("Cancelar");
        jBCancelar.setEnabled(false);
        jBCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarActionPerformed(evt);
            }
        });

        jBImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/print-icon.png"))); // NOI18N
        jBImprimir.setText("Imprimir");
        jBImprimir.setEnabled(false);
        jBImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBImprimirActionPerformed(evt);
            }
        });

        jBNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/add (25x25).png"))); // NOI18N
        jBNovo.setText("Novo");
        jBNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNovoActionPerformed(evt);
            }
        });

        jBExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/lixo (25x25).png"))); // NOI18N
        jBExcluir.setText("Excluir");
        jBExcluir.setEnabled(false);
        jBExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBExcluirActionPerformed(evt);
            }
        });

        jBPesquisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/Start-Menu-Search-icon (1).png"))); // NOI18N
        jBPesquisa.setText("Pesquisar");
        jBPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPesquisaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPBotoesLayout = new javax.swing.GroupLayout(jPBotoes);
        jPBotoes.setLayout(jPBotoesLayout);
        jPBotoesLayout.setHorizontalGroup(
            jPBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPBotoesLayout.createSequentialGroup()
                .addGroup(jPBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPBotoesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jBPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPBotoesLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBNovo, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(jBSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPBotoesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jBExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPBotoesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(jBImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(jBCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPBotoesLayout.setVerticalGroup(
            jPBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPBotoesLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jBNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jBSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jBImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jBCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("(*) -> Campos obrigatórios");

        javax.swing.GroupLayout jPAgendamentoLayout = new javax.swing.GroupLayout(jPAgendamento);
        jPAgendamento.setLayout(jPAgendamentoLayout);
        jPAgendamentoLayout.setHorizontalGroup(
            jPAgendamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAgendamentoLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPAgendamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPDadosPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPAgendamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(41, 41, 41))
        );
        jPAgendamentoLayout.setVerticalGroup(
            jPAgendamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAgendamentoLayout.createSequentialGroup()
                .addGroup(jPAgendamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPAgendamentoLayout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jPBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addGroup(jPAgendamentoLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jPDadosPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jTAgenda.addTab("Agendar", jPAgendamento);

        jPDadosPacientes.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        JTPacientes.setModel(tmPacientes);
        JTPacientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTPacientesMouseClicked(evt);
            }
        });
        jSDadosPacientes.setViewportView(JTPacientes);

        javax.swing.GroupLayout jPDadosPacientesLayout = new javax.swing.GroupLayout(jPDadosPacientes);
        jPDadosPacientes.setLayout(jPDadosPacientesLayout);
        jPDadosPacientesLayout.setHorizontalGroup(
            jPDadosPacientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSDadosPacientes, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
        );
        jPDadosPacientesLayout.setVerticalGroup(
            jPDadosPacientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSDadosPacientes, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPPacientesLayout = new javax.swing.GroupLayout(jPPacientes);
        jPPacientes.setLayout(jPPacientesLayout);
        jPPacientesLayout.setHorizontalGroup(
            jPPacientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPacientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPDadosPacientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPPacientesLayout.setVerticalGroup(
            jPPacientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPacientesLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jPDadosPacientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTAgenda.addTab("Pacientes", jPPacientes);

        jPDadosConsulta.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jTConsultas.setModel(tmConsultas);
        jTConsultas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTConsultasMouseClicked(evt);
            }
        });
        jSConsultas.setViewportView(jTConsultas);

        javax.swing.GroupLayout jPDadosConsultaLayout = new javax.swing.GroupLayout(jPDadosConsulta);
        jPDadosConsulta.setLayout(jPDadosConsultaLayout);
        jPDadosConsultaLayout.setHorizontalGroup(
            jPDadosConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSConsultas, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
        );
        jPDadosConsultaLayout.setVerticalGroup(
            jPDadosConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSConsultas, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
        );

        jLBusca.setText("Busca Por:");

        jCBusca.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Paciente", "Data", "Horário", "Psicólogo(a)", "Status ", "Pagamento" }));
        jCBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBuscaActionPerformed(evt);
            }
        });

        jBBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/Start-Menu-Search-icon.png"))); // NOI18N
        jBBusca.setText("Pesquisar");
        jBBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscaActionPerformed(evt);
            }
        });

        jTBusca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTBuscaKeyReleased(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/Arrow-Right-icon2.png"))); // NOI18N
        jButton1.setToolTipText("Voltar ");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPConsultasLayout = new javax.swing.GroupLayout(jPConsultas);
        jPConsultas.setLayout(jPConsultasLayout);
        jPConsultasLayout.setHorizontalGroup(
            jPConsultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPConsultasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPConsultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPConsultasLayout.createSequentialGroup()
                        .addComponent(jLBusca)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTBusca, javax.swing.GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBBusca))
                    .addComponent(jPDadosConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPConsultasLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPConsultasLayout.setVerticalGroup(
            jPConsultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPConsultasLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPConsultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBBusca)
                    .addComponent(jLBusca)
                    .addComponent(jCBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPDadosConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTAgenda.addTab("Consultas", jPConsultas);

        getContentPane().add(jTAgenda);
        jTAgenda.setBounds(10, 10, 847, 460);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/bg.jpg"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 870, 480);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-883)/2, (screenSize.height-518)/2, 883, 518);
    }// </editor-fold>//GEN-END:initComponents

    private void jBBuscarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarPacienteActionPerformed
        try {
            jTNome.setText("");
            jTDataNasc.setText("");
            listarPacientes();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Problemas no botão de pesquisar paciente.\n" + ex);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nenhhum paciente encontrado.\n" + e);
        }
    }//GEN-LAST:event_jBBuscarPacienteActionPerformed
String valor;
int ponto;
    private void jTValorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTValorKeyPressed
        if (evt.getKeyCode() == 10) {
            jCStatusPagamento.requestFocus();
        }

        if (evt.getKeyCode() == 16 || evt.getKeyCode() == 18) {
            jCStatusPagamento.requestFocus();
        }


        if (jTValor.getText().length() < 10) {
            if (evt.getKeyCode() >= 48 && evt.getKeyCode() <= 57 || evt.getKeyCode() >= 96 && evt.getKeyCode() <= 105
                    || evt.getKeyCode() == 8 || evt.getKeyCode() >= 37 && evt.getKeyCode() <= 40 || evt.getKeyCode() == 10
                    || evt.getKeyCode() == 46) {

                valor = jTValor.getText();

                if (evt.getKeyCode() == 46) {
                    ponto++;
                }

                if (jTValor.getText().length() == 0) {
                    if (evt.getKeyCode() == 46) {
                        ponto = 2;
                    }
                }

                if (evt.getKeyCode() == 46 && ponto >= 2) {
                    JOptionPane.showMessageDialog(this, "Digite apenas números.", "Atenção", JOptionPane.WARNING_MESSAGE);
                    jTValor.setText("");
                    jTValor.setText(valor);
                }

                if (!jTValor.getText().contains(".")) {
                    ponto = 0;
                }


            } else {
                JOptionPane.showMessageDialog(this, "Digite apenas número.", "Atenção", JOptionPane.WARNING_MESSAGE);
                jTValor.setText("");
                jTValor.setText(valor);
            }

        } else {
            jTValor.setText("");
            jTValor.setText(valor);
        }

        if (jTValor.getText().length() > 9) {
            if (evt.getKeyCode() != 10) {
                jTValor.setText("");
                jTValor.setText(valor);
            }

        }


        if (evt.getKeyCode() == 38) {
            jCStatusConsulta.requestFocus();
        }
    }//GEN-LAST:event_jTValorKeyPressed

    private void jTValorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTValorKeyReleased
        if (jTValor.getText().length() < 10) {
            if (evt.getKeyCode() >= 48 && evt.getKeyCode() <= 57 || evt.getKeyCode() >= 96 && evt.getKeyCode() <= 105
                    || evt.getKeyCode() == 8 || evt.getKeyCode() >= 37 && evt.getKeyCode() <= 40 || evt.getKeyCode() == 10
                    || evt.getKeyCode() == 46) {

                valor = jTValor.getText();

                if (evt.getKeyCode() == 46) {
                    ponto++;
                }

                if (jTValor.getText().length() == 0) {
                    if (evt.getKeyCode() == 46) {
                        ponto = 2;
                    }
                }

                if (evt.getKeyCode() == 46 && ponto >= 2) {
                    JOptionPane.showMessageDialog(this, "Digite apenas números.", "Atenção", JOptionPane.WARNING_MESSAGE);
                    jTValor.setText("");
                    jTValor.setText(valor);
                }

                if (!jTValor.getText().contains(".")) {
                    ponto = 0;
                }


            } else {
                JOptionPane.showMessageDialog(this, "Digite apenas números.", "Atenção", JOptionPane.WARNING_MESSAGE);
                jTValor.setText("");
                jTValor.setText(valor);
            }

        } else {
            jTValor.setText("");
            jTValor.setText(valor);
        }

        if (jTValor.getText().length() > 9) {
            if (evt.getKeyCode() != 10) {
                jTValor.setText("");
                jTValor.setText(valor);
            }

        }

    }//GEN-LAST:event_jTValorKeyReleased

    private void jCDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCDiaActionPerformed
        validaData();
    }//GEN-LAST:event_jCDiaActionPerformed

    private void jCMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCMesActionPerformed
        validaData();
    }//GEN-LAST:event_jCMesActionPerformed

    private void jCAnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCAnoActionPerformed
        validaData();
    }//GEN-LAST:event_jCAnoActionPerformed

    private void jBAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAlterarActionPerformed
        try {
            alteraConsulta();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Problema no botão alterar.\n"+ex);
        }
    }//GEN-LAST:event_jBAlterarActionPerformed

    private void jBSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalvarActionPerformed
        agendarConsulta();
    }//GEN-LAST:event_jBSalvarActionPerformed

    private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
        limparDados();
        desabilitaDados();
        jTAgenda.setSelectedComponent(jPAgendamento);
    }//GEN-LAST:event_jBCancelarActionPerformed

    private void jBImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBImprimirActionPerformed
         if (verificaDados()) {
           impressaoConsultas();
        } else {
            JOptionPane.showMessageDialog(null, "Todos os campos obrigatórios devem ser preenchidos para impressão.", "Atenção", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBImprimirActionPerformed

    private void jBNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNovoActionPerformed
        habilitaDados();
        limparDados();
        jBAlterar.setEnabled(false);

        jTAgenda.setSelectedComponent(jPAgendamento);
        // jTData.requestFocus();
    }//GEN-LAST:event_jBNovoActionPerformed

    private void jBExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBExcluirActionPerformed
        try {
            excluirConsulta();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro no botão Excluir.\n" + ex);
        }
    }//GEN-LAST:event_jBExcluirActionPerformed

    private void jTConsultasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTConsultasMouseClicked
        linhaSelecionadaConsultas(jTConsultas);
    }//GEN-LAST:event_jTConsultasMouseClicked

    private void jCBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBuscaActionPerformed
        try {
            listarConsultas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Problema ao selecionar tipo de pesquisa.\n"+ex);
        }
    }//GEN-LAST:event_jCBuscaActionPerformed

    private void jBBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscaActionPerformed
        try {
            listarConsultas();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Problemas no botão de pesquisa.\n" + ex);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nenhhuma consulta agendada.\n" + e);
        }

        if (msg == false) {
            JOptionPane.showMessageDialog(null, "Nenhuma consulta agendada.", "Pesquisa", JOptionPane.INFORMATION_MESSAGE);
        }
        jTBusca.requestFocus();

    }//GEN-LAST:event_jBBuscaActionPerformed

    private void jTBuscaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscaKeyReleased
        if (evt.getKeyCode() >= 60 && evt.getKeyCode() <= 90 || evt.getKeyCode() >= 37 && evt.getKeyCode() <= 40
                || evt.getKeyCode() >= 48 && evt.getKeyCode() <= 57 || evt.getKeyCode() >= 96 && evt.getKeyCode() <= 105
                || evt.getKeyCode() == 8 || evt.getKeyCode() == 10) {
            try {
                listarConsultas();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Problemas no campo de pesquisa.\n " + ex);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Nenhhuma consulta agendada.\n" + e);
            }
            jTBusca.requestFocus();
        }
    }//GEN-LAST:event_jTBuscaKeyReleased

    private void JTPacientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTPacientesMouseClicked
        linhaSelecionadaPacientes(JTPacientes);
    }//GEN-LAST:event_JTPacientesMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        sair();
    }//GEN-LAST:event_formWindowClosing

    private void jBPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPesquisaActionPerformed
        jTAgenda.setSelectedComponent(jPConsultas);
    }//GEN-LAST:event_jBPesquisaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTAgenda.setSelectedComponent(jPAgendamento);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFConsultas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFConsultas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFConsultas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFConsultas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JFConsultas dialog = null;
                try {
                    dialog = new JFConsultas(new javax.swing.JFrame(), true);
                } catch (SQLException ex) {
                   JOptionPane.showMessageDialog(null,"Erro ao abrir formulário de agendamento de consultas.\n"+ex);
                }
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JTPacientes;
    private javax.swing.JButton jBAlterar;
    private javax.swing.JButton jBBusca;
    private javax.swing.JButton jBBuscarPaciente;
    private javax.swing.JButton jBCancelar;
    private javax.swing.JButton jBExcluir;
    private javax.swing.JButton jBImprimir;
    private javax.swing.JButton jBNovo;
    private javax.swing.JButton jBPesquisa;
    private javax.swing.JButton jBSalvar;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jCAno;
    private javax.swing.JComboBox jCBusca;
    private javax.swing.JComboBox jCDia;
    private javax.swing.JComboBox jCHoras;
    private javax.swing.JComboBox jCMes;
    private javax.swing.JComboBox jCMinutos;
    private javax.swing.JComboBox jCPsicologo;
    private javax.swing.JComboBox jCSexo;
    private javax.swing.JComboBox jCStatusConsulta;
    private javax.swing.JComboBox jCStatusPagamento;
    private javax.swing.JLabel jLBusca;
    private javax.swing.JLabel jLCodigoConsulta;
    private javax.swing.JLabel jLData;
    private javax.swing.JLabel jLDataNasc;
    private javax.swing.JLabel jLDtConsulta;
    private javax.swing.JLabel jLDtInvalida;
    private javax.swing.JLabel jLHora;
    private javax.swing.JLabel jLHorario;
    private javax.swing.JLabel jLHr;
    private javax.swing.JLabel jLNome;
    private javax.swing.JLabel jLObrDt;
    private javax.swing.JLabel jLObrDtNasc;
    private javax.swing.JLabel jLObrHr;
    private javax.swing.JLabel jLObrNome;
    private javax.swing.JLabel jLObrPsicologo;
    private javax.swing.JLabel jLObrSexo;
    private javax.swing.JLabel jLObrStatusC;
    private javax.swing.JLabel jLPsicologo;
    private javax.swing.JLabel jLSexo;
    private javax.swing.JLabel jLStatusConsulta;
    private javax.swing.JLabel jLStatusP;
    private javax.swing.JLabel jLStatusPagamento;
    private javax.swing.JLabel jLValor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPAgendamento;
    private javax.swing.JPanel jPBotoes;
    private javax.swing.JPanel jPConsulta;
    private javax.swing.JPanel jPConsultas;
    private javax.swing.JPanel jPDadosConsulta;
    private javax.swing.JPanel jPDadosPaciente;
    private javax.swing.JPanel jPDadosPacientes;
    private javax.swing.JPanel jPPacientes;
    private javax.swing.JScrollPane jSConsultas;
    private javax.swing.JScrollPane jSDadosPacientes;
    private javax.swing.JTabbedPane jTAgenda;
    private javax.swing.JTextField jTBusca;
    private javax.swing.JTextField jTCodigoConsulta;
    private javax.swing.JTable jTConsultas;
    private javax.swing.JFormattedTextField jTDataNasc;
    private javax.swing.JTextField jTNome;
    private javax.swing.JTextField jTValor;
    // End of variables declaration//GEN-END:variables
}
