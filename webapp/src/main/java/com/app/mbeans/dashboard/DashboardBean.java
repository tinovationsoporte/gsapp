package com.app.mbeans.dashboard;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.hibernate.sql.ordering.antlr.GeneratedOrderByFragmentParser;
import org.primefaces.model.chart.PieChartModel;

import com.core.app.mbeans.MBeanAbstracto;
import com.modelo.datos.app.Area;
import com.modelo.datos.app.Prioridad;
import com.modelo.datos.app.StatusPeticion;
import com.modelo.datos.app.UsuarioSistema;
import com.modelo.datos.estructuras.Dashboard;
import com.modelo.datos.estructuras.ResumenDashboard;
import com.modelo.datos.estructuras.StatusPeticiones;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;
import com.objetos.transf.datos.app.dashboard.ConsultarDashboardPeticion;
import com.objetos.transf.datos.app.dashboard.ConsultarDashboardRespuesta;
import com.objetos.transf.datos.app.dashboard.ConsultarResumenDashboardPeticion;
import com.objetos.transf.datos.app.dashboard.ConsultarResumenDashboardRespuesta;
import com.objetos.transf.datos.app.dashboard.ConsultarStatusPeticionesPorRolPeticion;
import com.servicios.app.catalogos.ServicioCatalogo;
import com.servicios.app.dashboard.ServicioDashboard;
import com.servicios.excepcion.ExcepcionServicioFachada;

@ManagedBean(name="dashboardBean")
@ViewScoped
public class DashboardBean extends MBeanAbstracto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{servicioCatalogo}")
	private ServicioCatalogo servicioCatalogo;
	
	@ManagedProperty(value = "#{servicioDashboard}")
	private ServicioDashboard servicioDashboard;
	
	//Datos de los estatus en el dashboard de la ventana	
	private Integer registrosPorValidar;
	private Integer registrosPorPresupuestar;
	private Integer registrosAutorizados;
	private Integer registrosEnGestion;
	private Integer registrosTerminados;
	
	//Datos de busqueda
	private String folioABuscar;
	private List<Prioridad> listaPrioridades;
	private int idPrioridadSeleccionada;
	private List<Area> listaAreas;
	private Integer idAreaSeleccionada;
	private List<StatusPeticiones> listaEstatus;
	private Integer idEstatusSeleccionado;
	private Date fechaDeBusqueda;
	
	private Boolean mostrarCmbArea;
	
	private String totalFolios;
	
	//Datos que se utilizan para manipular la tabla
	private List<Dashboard> listaPeticiones;
	
	private UsuarioSistema usuario;
	
	private ConsultarDashboardPeticion consultarDashBoardPeticion;
	
	private List<ResumenDashboard> listaResumen;
	
	private PieChartModel chartModel;
	
	@PostConstruct
	public void inicializar() {
		
		System.out.println("\n\n\n\t **** dashboardBean.inicializar()");

		
		this.registrosPorValidar = 3;
		this.registrosPorPresupuestar = 1;
		this.registrosAutorizados = 1;
		this.registrosEnGestion = 0;
		this.registrosTerminados = 0;
		
		//this.folioABuscar = null;
		setIdAreaSeleccionada(0);
		setIdEstatusSeleccionado(0);
		setIdPrioridadSeleccionada(0);
		//this.fechaDeBusqueda = null;
		
		//System.out.println("idPrioridadSeleccionada -> " + getIdPrioridadSeleccionada());
		
		usuario = (UsuarioSistema) getManagedBean("#{aplicacion.usuario}", UsuarioSistema.class);		
		
		try {
		
			setMostrarCmbArea(usuario.getRol().getIdRol() == 1);
			
			this.listaPrioridades = crearListaPrioridades();			
			
			this.listaEstatus = crearListaEstatus(usuario.getRol().getId());
			
			
			//this.listaResumen = consultarResumenDashboard(usuario.getRol().getIdRol(), usuario.getArea().getIdArea());
			inicializarGrafica();
			
			if(getMostrarCmbArea()){
				this.listaAreas = crearListaAreas();
			}			
			
			consultarDashBoardPeticion = new ConsultarDashboardPeticion();
			
			consultarDashBoardPeticion.setIdRol(usuario.getRol().getId());			
			
			
			
			/*if(usuario.getArea().getEsDefault() != 1 && usuario.getRol().getIdRol() != 1 && usuario.getRol().getIdRol() != 2){				
				consultarDashBoardPeticion.setIdArea(usuario.getArea().getId());
				
				if(usuario.getRol().getIdRol()  == 3){
					consultarDashBoardPeticion.setIdAreaPresupuesto(usuario.getArea().getId());
				}
			}*/
			
			
			if(usuario.getRol().getIdRol() == 4){
				consultarDashBoardPeticion.setIdArea(usuario.getArea().getId());
			}else{
				if(usuario.getRol().getIdRol() == 3){				
					
					
					consultarDashBoardPeticion.setIdArea(usuario.getArea().getId());
					
					if(usuario.getArea().getEsPresupuesto()  == 1){
						consultarDashBoardPeticion.setIdAreaPresupuesto(usuario.getArea().getId());
						consultarDashBoardPeticion.setEsAreaPresupuesto(true);
					}else{
						
						consultarDashBoardPeticion.setEsAreaPresupuesto(false);
					}
				
				}				
			}
			
			
				
			
			
			
			System.out.println("\n\n\n\t***consultarDashboardPeticion (init) -> " + consultarDashBoardPeticion);

			this.listaPeticiones = consultarPeticiones(consultarDashBoardPeticion);
		
		} catch (Exception ex) {
			ex.printStackTrace();						
			FacesContext.getCurrentInstance().addMessage("dashboard_msges", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrion un error al inicializar los catalogosn" + ex.getMessage(),"" ));
		}

	}	
	
	
	
	public void buscarFolio(ActionEvent e){
		try{
			
			/*consultarDashBoardPeticion = new ConsultarDashboardPeticion();
			
			consultarDashBoardPeticion.setIdRol(usuario.getRol().getId());
			
			if(usuario.getArea().getEsDefault() != 1 && usuario.getRol().getIdRol() != 1 && usuario.getRol().getIdRol() != 2){				
				consultarDashBoardPeticion.setIdArea(usuario.getArea().getId());
			}*/
				
			if(getMostrarCmbArea()){				
				consultarDashBoardPeticion.setIdArea(getIdAreaSeleccionada() != 0 ? getIdAreaSeleccionada() : null);
			}
		
			
			consultarDashBoardPeticion.setFolio(getFolioABuscar() == null || getFolioABuscar().isEmpty() ? null : getFolioABuscar()  );
			
			
			//consultarDashBoardPeticion.setIdArea(getIdAreaSeleccionada() != 0 ? getIdAreaSeleccionada() : null);		
			System.out.println("\n\n\n\t***getIdEstatusSeleccionado -> " + getIdEstatusSeleccionado());
			consultarDashBoardPeticion.setIdEstatus(getIdEstatusSeleccionado() != 0 ? getIdEstatusSeleccionado() : null );			
			
			System.out.println("\n\n\n\t***getIdPrioridadSeleccionada -> " + getIdPrioridadSeleccionada());
			consultarDashBoardPeticion.setIdPrioridad(getIdPrioridadSeleccionada() != 0 ? getIdPrioridadSeleccionada() : null);

			//consultarDashBoardPeticion.setFecha();
			
			System.out.println("\n\n\n\t***consultarDashboardPeticion (despues) -> " + consultarDashBoardPeticion);
			System.out.println("\n\n");
			
			this.listaPeticiones = consultarPeticiones(consultarDashBoardPeticion);
			
			inicializarGrafica();
			
		}catch(Exception ex){
			ex.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("dashboard_msges", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrion un error al consultar la informacion. Causa: " + ex.getMessage(),"" ));
		}
		
	}
	
	
	public void limpiar(ActionEvent e) {

		
		setIdAreaSeleccionada(0);
		setIdEstatusSeleccionado(0);
		setIdPrioridadSeleccionada(0);
		setFolioABuscar(null);
		setFechaDeBusqueda(null);
		
		buscarFolio(e);
		
		
	}
	
	private List<Prioridad> crearListaPrioridades() throws Exception{
		System.out.println("\n\n\n\t***crearListaPrioridades() ");

		ConsultarCatalogoPeticion peticion = new ConsultarCatalogoPeticion();
		peticion.setEntidad(new Prioridad());
		peticion.setClase(Prioridad.class);
		return servicioCatalogo.consultarCatalogo(peticion ).getEntidades();
	}
	
	private List<Area> crearListaAreas() throws Exception{
		System.out.println("\n\n\n\t***crearListaAreas() ");
		ConsultarCatalogoPeticion peticion = new ConsultarCatalogoPeticion();
		peticion.setEntidad(new Area());
		peticion.setClase(Area.class);
		return servicioCatalogo.consultarCatalogo(peticion ).getEntidades();		
	}
	
	private List<StatusPeticiones> crearListaEstatus(Integer idRol) throws Exception{		
		System.out.println("\n\n\n\t***crearListaEstatus("+idRol+")");

		ConsultarStatusPeticionesPorRolPeticion peticion = new ConsultarStatusPeticionesPorRolPeticion();
		peticion.setIdRol(idRol);
		return servicioDashboard.consultarStatusPeticionPorRol(peticion).getListaStatusPeticiones();		
	}
	
	private List<Dashboard> consultarPeticiones(ConsultarDashboardPeticion peticion) throws Exception{
		
		ConsultarDashboardRespuesta respuesta = servicioDashboard.consultarDashboard(peticion);	
		
		return respuesta.getDashboardList();		
	}	
	
	
	private List<ResumenDashboard> consultarResumenDashboard(Integer idRol, Integer idArea, boolean esAreaPresupuesto) throws ExcepcionServicioFachada{
		System.out.println("***ConsultarResumenDashboard()");

		
		ConsultarResumenDashboardPeticion peticion = new ConsultarResumenDashboardPeticion();
		peticion.setIdRol(idRol);
		
		
		
		/*if(idRol == 1 || idRol == 2 ){
			peticion.setIdArea(null);
		}else{
			peticion.setIdArea(idArea);
		}*/
		
		
		if(usuario.getRol().getIdRol() == 4){
			peticion.setIdArea(usuario.getArea().getId());
		}else{
			if(usuario.getRol().getIdRol() == 3){				
				
				
				peticion.setIdArea(usuario.getArea().getId());
				
				if(usuario.getArea().getEsPresupuesto()  == 1){
					peticion.setIdAreaPresupuesto(usuario.getArea().getId());
					peticion.setEsAreaPresupuesto(true);
				}else{
					
					peticion.setEsAreaPresupuesto(false);
				}
			
			}				
		}
		
		
		
		System.out.println("ConsultarResumenDashboardPeticion =>" + peticion);
		
		ConsultarResumenDashboardRespuesta respuesta = servicioDashboard.consultarResumenDashboard(peticion);
		System.out.println("listaResumenDashboard.size =>" + respuesta.getListaResumenDashboard().size());
		return respuesta.getListaResumenDashboard();
		
	}
	
	private void inicializarGrafica() throws ExcepcionServicioFachada{
		this.listaResumen = consultarResumenDashboard(usuario.getRol().getIdRol(), usuario.getArea().getIdArea(), usuario.getArea().getEsPresupuesto() == 1);
		
		chartModel = new PieChartModel();
		Integer sumatoria = 0;
		
		
		if(listaResumen.size() > 0){
		
			for(ResumenDashboard resumen : listaResumen){			
				chartModel.set(resumen.getStatus(), resumen.getTotal());
				sumatoria+= resumen.getTotal();
			}
			
			totalFolios = sumatoria + " folios";			
			
		}else{
			
			chartModel.set("Sin folios asignados", sumatoria);
			
			totalFolios = sumatoria + " folios";	
		}	
		
	}
	
	
	public PieChartModel getChartModel() {
		return chartModel;
	}



	public void setChartModel(PieChartModel chartModel) {
		this.chartModel = chartModel;
	}



	public List<Dashboard> getListaPeticiones() {
		return listaPeticiones;
	}
	public void setListaPeticiones(List<Dashboard> listaPeticiones) {
		this.listaPeticiones = listaPeticiones;
	}
	
	public Integer getRegistrosPorValidar() {
		return registrosPorValidar;
	}
	public void setRegistrosPorValidar(Integer registrosPorValidar) {
		this.registrosPorValidar = registrosPorValidar;
	}
	public Integer getRegistrosPorPresupuestar() {
		return registrosPorPresupuestar;
	}
	public void setRegistrosPorPresupuestar(Integer registrosPorPresupuestar) {
		this.registrosPorPresupuestar = registrosPorPresupuestar;
	}
	public Integer getRegistrosAutorizados() {
		return registrosAutorizados;
	}
	public void setRegistrosAutorizados(Integer registrosAutorizados) {
		this.registrosAutorizados = registrosAutorizados;
	}
	public Integer getRegistrosEnGestion() {
		return registrosEnGestion;
	}
	public void setRegistrosEnGestion(Integer registrosEnGestion) {
		this.registrosEnGestion = registrosEnGestion;
	}
	public Integer getRegistrosTerminados() {
		return registrosTerminados;
	}
	public void setRegistrosTerminados(Integer registrosTerminados) {
		this.registrosTerminados = registrosTerminados;
	}
	public String getFolioABuscar() {
		return folioABuscar;
	}
	public void setFolioABuscar(String folioABuscar) {
		this.folioABuscar = folioABuscar;
	}
	
	public Integer getIdAreaSeleccionada() {
		return idAreaSeleccionada;
	}
	public void setIdAreaSeleccionada(Integer idAreaSeleccionada) {
		this.idAreaSeleccionada = idAreaSeleccionada;
	}
	public Integer getIdEstatusSeleccionado() {
		return idEstatusSeleccionado;
	}
	public void setIdEstatusSeleccionado(Integer idEstatusSeleccionado) {
		this.idEstatusSeleccionado = idEstatusSeleccionado;
	}
	public Date getFechaDeBusqueda() {
		return fechaDeBusqueda;
	}
	public void setFechaDeBusqueda(Date fechaDeBusqueda) {
		this.fechaDeBusqueda = fechaDeBusqueda;
	}	
	
	public List<Prioridad> getListaPrioridades() {
		return listaPrioridades;
	}

	public void setListaPrioridades(List<Prioridad> listaPrioridades) {
		this.listaPrioridades = listaPrioridades;
	}

	public List<Area> getListaAreas() {
		return listaAreas;
	}

	public void setListaAreas(List<Area> listaAreas) {
		this.listaAreas = listaAreas;
	}

	public List<StatusPeticiones> getListaEstatus() {
		return listaEstatus;
	}

	public void setListaEstatus(List<StatusPeticiones> listaEstatus) {
		this.listaEstatus = listaEstatus;
	}

	public int getIdPrioridadSeleccionada() {
		return idPrioridadSeleccionada;
	}

	public void setIdPrioridadSeleccionada(int idPrioridadSeleccionada) {
		this.idPrioridadSeleccionada = idPrioridadSeleccionada;
	}

	
	
	public Boolean getMostrarCmbArea() {
		return mostrarCmbArea;
	}

	public void setMostrarCmbArea(Boolean mostrarCmbArea) {
		this.mostrarCmbArea = mostrarCmbArea;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ServicioCatalogo getServicioCatalogo() {
		return servicioCatalogo;
	}

	public void setServicioCatalogo(ServicioCatalogo servicioCatalogo) {
		this.servicioCatalogo = servicioCatalogo;
	}

	public ServicioDashboard getServicioDashboard() {
		return servicioDashboard;
	}

	public void setServicioDashboard(ServicioDashboard servicioDashboard) {
		this.servicioDashboard = servicioDashboard;
	}



	public List<ResumenDashboard> getListaResumen() {
		return listaResumen;
	}



	public void setListaResumen(List<ResumenDashboard> listaResumen) {
		this.listaResumen = listaResumen;
	}



	public String getTotalFolios() {
		return totalFolios;
	}



	public void setTotalFolios(String totalFolios) {
		this.totalFolios = totalFolios;
	}
	
	
	
}
