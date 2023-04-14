package co.com.tcc.novedadesmasivas.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Date;

/**
 *
 * @author Be-Smart_2
 */
public class Remesa implements SQLData, Serializable {

    private int pos;

    // Atributos de consulta
    private String fechaInicio;
    private String fechaFin;
    private String numRemesa;
    private String rangoRemesaInicio;
    private String rangoRemesaFin;
    private Integer idUnidadNegocio;
    private Integer idTipoTransporte;
    private String idEstadoRemesa;
    private String idCeOpOrigen;
    private String idCeOpDestino;
    private String idCiudadOrigen;
    private String idCiudadDestino;
    private Long idTipoServicioOperativo;
    private Long idTipoDocReferencia;
    private String numDocReferencia;
    private String idRemitente;
    private String idDestinatario;
    private Long idCuentaRemitente;
    private Long idCuentaDestintario;
    private String idSedeRemitente;
    private String idSedeDestinatario;
    private Long idTipoUnidad;
    private String codigoUnidad;
    private String movilLocal;
    private Long numDespachoCliente;
    private int numPag;
    private int numRegs;
    private Long idEstadoUnidad;
    private String tipoNovedad;
    private int idCeOpDestNacional;
    private int idCeOpOrigNacional;
    private String codVanNacional;
    private String numViajeNacional;
    private String fechaInicioViajeNacional;
    private String fechaFinViajeNacional;
    private String numViajeLocal;
    private String fechaInicioEntrega;
    private String fechaFinEntrega;
    private int licencia;

    // Atributos para presentacion en cliente.
    private String canoIdInt;
    private String remeIdInt;
    private String unidadNegocio;
    private String fechaRemesa;
    private String fechaRemesaDos;
    private int unidadesRemesa;
    private String estadoRemesa;
    private String tipoTransporte;
    private String tipoServicioOperativo;
    private String ceOpOrigen;
    private String ciudadOrigen;
    private String identificacionRemitente;
    private String nombreRemitente;
    private String sedeRemitente;
    private String direccionRemitente;
    private String telefonoRemitente;
    private String ceOpDestino;
    private String ciudadDestino;
    private String identificacionDestinatario;
    private String nombreDestinatario;
    private String sedeDestinatario;
    private String direccionDestinatario;
    private String telefonoDestinatario;
    private String unidades;
    private String pesoReal;
    private String pesoVolumen;
    private String vehiculo;
    private String ruta;
    private String viaje;
    private String posicion;
    private String reclamaEnBodega;
    private String observaciones;
    private boolean tieneNovedad;
    private String cumplido;
    private String cuenta;
    private String usuario;
    private boolean tieneNovedadUnidad;
    private String formaPago;
    private String consecutivoNovedad;
    private String viajeLocal;
    private String rutaLocal;
    private String numeroRemesaSolucion;
    private String tipoDeSolucion;
    private String fechaProgramacionLocal;
    private String fechaProgramacionNacional;
    private String van;
    private int unidadesNovedad;
    private String descLicencia;

    private String reex; // poblaci�n reexpedida (R = TRUE;s NULL = FALSE)
    private String countProcesosAsoc;

    private String movil;
    private String posicionRuta;

    private int procIdInt;
    private String fechaNovedad;
    private String codCausaNovedad;
    private String codEstadoNovedad;
    private int idEstadoNovedad;
    private String estadoNovedad;
    private String descProceso;
    private String descTipoUnidad;
    private String codRecogida;
    private String codUnidadContenedora;

    private int esonIdInt;
    private String grupoNovedad;
    private int numeroSeguimientos;
    private String novedadComplemento;
    private String nombreCeOpReporta;
    private String canalPlantea;
    private String usuarioPlantea;
    private String observacionesNovedad;
    private String nombreProceso;
    private String descPrioridad;
    private String imputabilidad;
    private String solucionNovedad;
    private String ceOpSoluciona;
    private String fechaFinSolucion;
    private String usuarioSoluciona;
    private String clienteSoluciona;
    private String descTipoSolucion;
    private String canalSoluciona;
    private String tipoRemesaSolucion;
    private String ultimoSeguimiento;
    private String ceOpUltimoSeguimiento;
    private String fechaUltimoSeguimiento;
    private String numRemesaSolucion;
    private String canoObservEjecucion;
    private String nombreCeOpEjecuta;
    private String fechaEjecucion;
    private String usuarioEjecucion;
    private String descCausalNov;
    private String fechaFinReofrecimiento;
    private String cierrePorProcedimiento;
    private String tipoCobertura;
    private int idCausalNovedad;
    private String motivoEjecucion;

    private String idUnidadesNegocio;
    private String idTiposCobertura;
    private int conNovedad;
    private String idEstadosNovedad;
    private String idUbicaciones;

    private String codigoBarras;
    private String longitud;
    private String latitud;

    private int idTipoServicioEspecial;

    private int codigoNovedadComplemento;
    private int codigoJustificacionNovedad;
    
    private int novedadesRezagos;
    private int seguimientosUltimNovedad;

    public int getSeguimientosUltimNovedad() {
        return seguimientosUltimNovedad;
    }

    public void setSeguimientosUltimNovedad(int seguimientosUltimNovedad) {
        this.seguimientosUltimNovedad = seguimientosUltimNovedad;
    }
    
    

    public int getNovedadesRezagos() {
        return novedadesRezagos;
    }

    public void setNovedadesRezagos(int novedadesRezagos) {
        this.novedadesRezagos = novedadesRezagos;
    }
    
    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getNumRemesa() {
        return numRemesa;
    }

    public void setNumRemesa(String numRemesa) {
        this.numRemesa = numRemesa;
    }

    public String getRangoRemesaInicio() {
        return rangoRemesaInicio;
    }

    public void setRangoRemesaInicio(String rangoRemesaInicio) {
        this.rangoRemesaInicio = rangoRemesaInicio;
    }

    public String getRangoRemesaFin() {
        return rangoRemesaFin;
    }

    public void setRangoRemesaFin(String rangoRemesaFin) {
        this.rangoRemesaFin = rangoRemesaFin;
    }

    public Integer getIdUnidadNegocio() {
        return idUnidadNegocio;
    }

    public void setIdUnidadNegocio(Integer idUnidadNegocio) {
        this.idUnidadNegocio = idUnidadNegocio;
    }

    public Integer getIdTipoTransporte() {
        return idTipoTransporte;
    }

    public void setIdTipoTransporte(Integer idTipoTransporte) {
        this.idTipoTransporte = idTipoTransporte;
    }

    public String getDescLicencia() {
        return descLicencia;
    }

    public void setDescLicencia(String descLicencia) {
        this.descLicencia = descLicencia;
    }
    
    public String getIdEstadoRemesa() {
        return idEstadoRemesa;
    }

    public void setIdEstadoRemesa(String idEstadoRemesa) {
        this.idEstadoRemesa = idEstadoRemesa;
    }

    public String getIdCeOpOrigen() {
        return idCeOpOrigen;
    }

    public void setIdCeOpOrigen(String idCeOpOrigen) {
        this.idCeOpOrigen = idCeOpOrigen;
    }

    public String getIdCeOpDestino() {
        return idCeOpDestino;
    }

    public void setIdCeOpDestino(String idCeOpDestino) {
        this.idCeOpDestino = idCeOpDestino;
    }

    public String getIdCiudadOrigen() {
        return idCiudadOrigen;
    }

    public void setIdCiudadOrigen(String idCiudadOrigen) {
        this.idCiudadOrigen = idCiudadOrigen;
    }

    public String getIdCiudadDestino() {
        return idCiudadDestino;
    }

    public void setIdCiudadDestino(String idCiudadDestino) {
        this.idCiudadDestino = idCiudadDestino;
    }

    public Long getIdTipoServicioOperativo() {
        return idTipoServicioOperativo;
    }

    public void setIdTipoServicioOperativo(Long idTipoServicioOperativo) {
        this.idTipoServicioOperativo = idTipoServicioOperativo;
    }

    public Long getIdTipoDocReferencia() {
        return idTipoDocReferencia;
    }

    public void setIdTipoDocReferencia(Long idTipoDocReferencia) {
        this.idTipoDocReferencia = idTipoDocReferencia;
    }

    public String getNumDocReferencia() {
        return numDocReferencia;
    }

    public void setNumDocReferencia(String numDocReferencia) {
        this.numDocReferencia = numDocReferencia;
    }

    public String getIdRemitente() {
        return idRemitente;
    }

    public void setIdRemitente(String idRemitente) {
        this.idRemitente = idRemitente;
    }

    public String getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(String idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public Long getIdCuentaRemitente() {
        return idCuentaRemitente;
    }

    public void setIdCuentaRemitente(Long idCuentaRemitente) {
        this.idCuentaRemitente = idCuentaRemitente;
    }

    public Long getIdCuentaDestintario() {
        return idCuentaDestintario;
    }

    public void setIdCuentaDestintario(Long idCuentaDestintario) {
        this.idCuentaDestintario = idCuentaDestintario;
    }

    public String getIdSedeRemitente() {
        return idSedeRemitente;
    }

    public void setIdSedeRemitente(String idSedeRemitente) {
        this.idSedeRemitente = idSedeRemitente;
    }

    public String getIdSedeDestinatario() {
        return idSedeDestinatario;
    }

    public void setIdSedeDestinatario(String idSedeDestinatario) {
        this.idSedeDestinatario = idSedeDestinatario;
    }

    public Long getIdTipoUnidad() {
        return idTipoUnidad;
    }

    public void setIdTipoUnidad(Long idTipoUnidad) {
        this.idTipoUnidad = idTipoUnidad;
    }

    public String getCodigoUnidad() {
        return codigoUnidad;
    }

    public void setCodigoUnidad(String codigoUnidad) {
        this.codigoUnidad = codigoUnidad;
    }

    public String getMovilLocal() {
        return movilLocal;
    }

    public void setMovilLocal(String movilLocal) {
        this.movilLocal = movilLocal;
    }

    public Long getNumDespachoCliente() {
        return numDespachoCliente;
    }

    public void setNumDespachoCliente(Long numDespachoCliente) {
        this.numDespachoCliente = numDespachoCliente;
    }

    public int getNumPag() {
        return numPag;
    }

    public void setNumPag(int numPag) {
        this.numPag = numPag;
    }

    public int getNumRegs() {
        return numRegs;
    }

    public void setNumRegs(int numRegs) {
        this.numRegs = numRegs;
    }

    public Long getIdEstadoUnidad() {
        return idEstadoUnidad;
    }

    public void setIdEstadoUnidad(Long idEstadoUnidad) {
        this.idEstadoUnidad = idEstadoUnidad;
    }

    public String getTipoNovedad() {
        return tipoNovedad;
    }

    public void setTipoNovedad(String tipoNovedad) {
        this.tipoNovedad = tipoNovedad;
    }

    public int getIdCeOpDestNacional() {
        return idCeOpDestNacional;
    }

    public void setIdCeOpDestNacional(int idCeOpDestNacional) {
        this.idCeOpDestNacional = idCeOpDestNacional;
    }

    public int getIdCeOpOrigNacional() {
        return idCeOpOrigNacional;
    }

    public void setIdCeOpOrigNacional(int idCeOpOrigNacional) {
        this.idCeOpOrigNacional = idCeOpOrigNacional;
    }

    public String getCodVanNacional() {
        return codVanNacional;
    }

    public void setCodVanNacional(String codVanNacional) {
        this.codVanNacional = codVanNacional;
    }

    public String getNumViajeNacional() {
        return numViajeNacional;
    }

    public void setNumViajeNacional(String numViajeNacional) {
        this.numViajeNacional = numViajeNacional;
    }

    public String getFechaInicioViajeNacional() {
        return fechaInicioViajeNacional;
    }

    public void setFechaInicioViajeNacional(String fechaInicioViajeNacional) {
        this.fechaInicioViajeNacional = fechaInicioViajeNacional;
    }

    public String getFechaFinViajeNacional() {
        return fechaFinViajeNacional;
    }

    public void setFechaFinViajeNacional(String fechaFinViajeNacional) {
        this.fechaFinViajeNacional = fechaFinViajeNacional;
    }

    public String getNumViajeLocal() {
        return numViajeLocal;
    }

    public void setNumViajeLocal(String numViajeLocal) {
        this.numViajeLocal = numViajeLocal;
    }

    public String getFechaInicioEntrega() {
        return fechaInicioEntrega;
    }

    public void setFechaInicioEntrega(String fechaInicioEntrega) {
        this.fechaInicioEntrega = fechaInicioEntrega;
    }

    public String getFechaFinEntrega() {
        return fechaFinEntrega;
    }

    public void setFechaFinEntrega(String fechaFinEntrega) {
        this.fechaFinEntrega = fechaFinEntrega;
    }

    public int getLicencia() {
        return licencia;
    }

    public void setLicencia(int licencia) {
        this.licencia = licencia;
    }
    
    

    public String getCanoIdInt() {
        return canoIdInt;
    }

    public void setCanoIdInt(String canoIdInt) {
        this.canoIdInt = canoIdInt;
    }

    public String getRemeIdInt() {
        return remeIdInt;
    }

    public void setRemeIdInt(String remeIdInt) {
        this.remeIdInt = remeIdInt;
    }

    public String getUnidadNegocio() {
        return unidadNegocio;
    }

    public void setUnidadNegocio(String unidadNegocio) {
        this.unidadNegocio = unidadNegocio;
    }

    public String getFechaRemesa() {
        return fechaRemesa;
    }

    public void setFechaRemesa(String fechaRemesa) {
        this.fechaRemesa = fechaRemesa;
    }

    public String getFechaRemesaDos() {
        return fechaRemesaDos;
    }

    public void setFechaRemesaDos(String fechaRemesaDos) {
        this.fechaRemesaDos = fechaRemesaDos;
    }

    public int getUnidadesRemesa() {
        return unidadesRemesa;
    }

    public void setUnidadesRemesa(int unidadesRemesa) {
        this.unidadesRemesa = unidadesRemesa;
    }

    public String getEstadoRemesa() {
        return estadoRemesa;
    }

    public void setEstadoRemesa(String estadoRemesa) {
        this.estadoRemesa = estadoRemesa;
    }

    public String getTipoTransporte() {
        return tipoTransporte;
    }

    public void setTipoTransporte(String tipoTransporte) {
        this.tipoTransporte = tipoTransporte;
    }

    public String getTipoServicioOperativo() {
        return tipoServicioOperativo;
    }

    public void setTipoServicioOperativo(String tipoServicioOperativo) {
        this.tipoServicioOperativo = tipoServicioOperativo;
    }

    public String getCeOpOrigen() {
        return ceOpOrigen;
    }

    public void setCeOpOrigen(String ceOpOrigen) {
        this.ceOpOrigen = ceOpOrigen;
    }

    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(String ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public String getIdentificacionRemitente() {
        return identificacionRemitente;
    }

    public void setIdentificacionRemitente(String identificacionRemitente) {
        this.identificacionRemitente = identificacionRemitente;
    }

    public String getNombreRemitente() {
        return nombreRemitente;
    }

    public void setNombreRemitente(String nombreRemitente) {
        this.nombreRemitente = nombreRemitente;
    }

    public String getSedeRemitente() {
        return sedeRemitente;
    }

    public void setSedeRemitente(String sedeRemitente) {
        this.sedeRemitente = sedeRemitente;
    }

    public String getDireccionRemitente() {
        return direccionRemitente;
    }

    public void setDireccionRemitente(String direccionRemitente) {
        this.direccionRemitente = direccionRemitente;
    }

    public String getTelefonoRemitente() {
        return telefonoRemitente;
    }

    public void setTelefonoRemitente(String telefonoRemitente) {
        this.telefonoRemitente = telefonoRemitente;
    }

    public String getCeOpDestino() {
        return ceOpDestino;
    }

    public void setCeOpDestino(String ceOpDestino) {
        this.ceOpDestino = ceOpDestino;
    }

    public String getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public String getIdentificacionDestinatario() {
        return identificacionDestinatario;
    }

    public void setIdentificacionDestinatario(String identificacionDestinatario) {
        this.identificacionDestinatario = identificacionDestinatario;
    }

    public String getNombreDestinatario() {
        return nombreDestinatario;
    }

    public void setNombreDestinatario(String nombreDestinatario) {
        this.nombreDestinatario = nombreDestinatario;
    }

    public String getSedeDestinatario() {
        return sedeDestinatario;
    }

    public void setSedeDestinatario(String sedeDestinatario) {
        this.sedeDestinatario = sedeDestinatario;
    }

    public String getDireccionDestinatario() {
        return direccionDestinatario;
    }

    public void setDireccionDestinatario(String direccionDestinatario) {
        this.direccionDestinatario = direccionDestinatario;
    }

    public String getTelefonoDestinatario() {
        return telefonoDestinatario;
    }

    public void setTelefonoDestinatario(String telefonoDestinatario) {
        this.telefonoDestinatario = telefonoDestinatario;
    }

    public String getUnidades() {
        return unidades;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }

    public String getPesoReal() {
        return pesoReal;
    }

    public void setPesoReal(String pesoReal) {
        this.pesoReal = pesoReal;
    }

    public String getPesoVolumen() {
        return pesoVolumen;
    }

    public void setPesoVolumen(String pesoVolumen) {
        this.pesoVolumen = pesoVolumen;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getViaje() {
        return viaje;
    }

    public void setViaje(String viaje) {
        this.viaje = viaje;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getReclamaEnBodega() {
        return reclamaEnBodega;
    }

    public void setReclamaEnBodega(String reclamaEnBodega) {
        this.reclamaEnBodega = reclamaEnBodega;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isTieneNovedad() {
        return tieneNovedad;
    }

    public void setTieneNovedad(boolean tieneNovedad) {
        this.tieneNovedad = tieneNovedad;
    }

    public String getCumplido() {
        return cumplido;
    }

    public void setCumplido(String cumplido) {
        this.cumplido = cumplido;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean isTieneNovedadUnidad() {
        return tieneNovedadUnidad;
    }

    public void setTieneNovedadUnidad(boolean tieneNovedadUnidad) {
        this.tieneNovedadUnidad = tieneNovedadUnidad;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getConsecutivoNovedad() {
        return consecutivoNovedad;
    }

    public void setConsecutivoNovedad(String consecutivoNovedad) {
        this.consecutivoNovedad = consecutivoNovedad;
    }

    public String getViajeLocal() {
        return viajeLocal;
    }

    public void setViajeLocal(String viajeLocal) {
        this.viajeLocal = viajeLocal;
    }

    public String getRutaLocal() {
        return rutaLocal;
    }

    public void setRutaLocal(String rutaLocal) {
        this.rutaLocal = rutaLocal;
    }

    public String getFechaProgramacionLocal() {
        return fechaProgramacionLocal;
    }

    public void setFechaProgramacionLocal(String fechaProgramacionLocal) {
        this.fechaProgramacionLocal = fechaProgramacionLocal;
    }

    public String getFechaProgramacionNacional() {
        return fechaProgramacionNacional;
    }

    public void setFechaProgramacionNacional(String fechaProgramacionNacional) {
        this.fechaProgramacionNacional = fechaProgramacionNacional;
    }

    public String getVan() {
        return van;
    }

    public void setVan(String van) {
        this.van = van;
    }

    public int getUnidadesNovedad() {
        return unidadesNovedad;
    }

    public void setUnidadesNovedad(int unidadesNovedad) {
        this.unidadesNovedad = unidadesNovedad;
    }

    public int getProcIdInt() {
        return procIdInt;
    }

    public void setProcIdInt(int procIdInt) {
        this.procIdInt = procIdInt;
    }

    public String getFechaNovedad() {
        return fechaNovedad;
    }

    public void setFechaNovedad(String fechaNovedad) {
        this.fechaNovedad = fechaNovedad;
    }

    public String getCodCausaNovedad() {
        return codCausaNovedad;
    }

    public void setCodCausaNovedad(String codCausaNovedad) {
        this.codCausaNovedad = codCausaNovedad;
    }

    public String getCodEstadoNovedad() {
        return codEstadoNovedad;
    }

    public void setCodEstadoNovedad(String codEstadoNovedad) {
        this.codEstadoNovedad = codEstadoNovedad;
    }

    public int getIdEstadoNovedad() {
        return idEstadoNovedad;
    }

    public void setIdEstadoNovedad(int idEstadoNovedad) {
        this.idEstadoNovedad = idEstadoNovedad;
    }

    public String getEstadoNovedad() {
        return estadoNovedad;
    }

    public void setEstadoNovedad(String estadoNovedad) {
        this.estadoNovedad = estadoNovedad;
    }

    public String getDescProceso() {
        return descProceso;
    }

    public void setDescProceso(String descProceso) {
        this.descProceso = descProceso;
    }

    public String getDescTipoUnidad() {
        return descTipoUnidad;
    }

    public void setDescTipoUnidad(String descTipoUnidad) {
        this.descTipoUnidad = descTipoUnidad;
    }

    public String getCodRecogida() {
        return codRecogida;
    }

    public void setCodRecogida(String codRecogida) {
        this.codRecogida = codRecogida;
    }

    public String getCodUnidadContenedora() {
        return codUnidadContenedora;
    }

    public void setCodUnidadContenedora(String codUnidadContenedora) {
        this.codUnidadContenedora = codUnidadContenedora;
    }

    public int getEsonIdInt() {
        return esonIdInt;
    }

    public void setEsonIdInt(int esonIdInt) {
        this.esonIdInt = esonIdInt;
    }

    public String getGrupoNovedad() {
        return grupoNovedad;
    }

    public void setGrupoNovedad(String grupoNovedad) {
        this.grupoNovedad = grupoNovedad;
    }

    public int getNumeroSeguimientos() {
        return numeroSeguimientos;
    }

    public void setNumeroSeguimientos(int numeroSeguimientos) {
        this.numeroSeguimientos = numeroSeguimientos;
    }

    public String getNovedadComplemento() {
        return novedadComplemento;
    }

    public void setNovedadComplemento(String novedadComplemento) {
        this.novedadComplemento = novedadComplemento;
    }

    public String getNombreCeOpReporta() {
        return nombreCeOpReporta;
    }

    public void setNombreCeOpReporta(String nombreCeOpReporta) {
        this.nombreCeOpReporta = nombreCeOpReporta;
    }

    public String getCanalPlantea() {
        return canalPlantea;
    }

    public void setCanalPlantea(String canalPlantea) {
        this.canalPlantea = canalPlantea;
    }

    public String getUsuarioPlantea() {
        return usuarioPlantea;
    }

    public void setUsuarioPlantea(String usuarioPlantea) {
        this.usuarioPlantea = usuarioPlantea;
    }

    public String getObservacionesNovedad() {
        return observacionesNovedad;
    }

    public void setObservacionesNovedad(String observacionesNovedad) {
        this.observacionesNovedad = observacionesNovedad;
    }

    public String getNombreProceso() {
        return nombreProceso;
    }

    public void setNombreProceso(String nombreProceso) {
        this.nombreProceso = nombreProceso;
    }

    public String getDescPrioridad() {
        return descPrioridad;
    }

    public void setDescPrioridad(String descPrioridad) {
        this.descPrioridad = descPrioridad;
    }

    public String getImputabilidad() {
        return imputabilidad;
    }

    public void setImputabilidad(String imputabilidad) {
        this.imputabilidad = imputabilidad;
    }

    public String getSolucionNovedad() {
        return solucionNovedad;
    }

    public void setSolucionNovedad(String solucionNovedad) {
        this.solucionNovedad = solucionNovedad;
    }

    public String getCeOpSoluciona() {
        return ceOpSoluciona;
    }

    public void setCeOpSoluciona(String ceOpSoluciona) {
        this.ceOpSoluciona = ceOpSoluciona;
    }

    public String getFechaFinSolucion() {
        return fechaFinSolucion;
    }

    public void setFechaFinSolucion(String fechaFinSolucion) {
        this.fechaFinSolucion = fechaFinSolucion;
    }

    public String getUsuarioSoluciona() {
        return usuarioSoluciona;
    }

    public void setUsuarioSoluciona(String usuarioSoluciona) {
        this.usuarioSoluciona = usuarioSoluciona;
    }

    public String getClienteSoluciona() {
        return clienteSoluciona;
    }

    public void setClienteSoluciona(String clienteSoluciona) {
        this.clienteSoluciona = clienteSoluciona;
    }

    public String getDescTipoSolucion() {
        return descTipoSolucion;
    }

    public void setDescTipoSolucion(String descTipoSolucion) {
        this.descTipoSolucion = descTipoSolucion;
    }

    public String getCanalSoluciona() {
        return canalSoluciona;
    }

    public void setCanalSoluciona(String canalSoluciona) {
        this.canalSoluciona = canalSoluciona;
    }

    public String getTipoRemesaSolucion() {
        return tipoRemesaSolucion;
    }

    public void setTipoRemesaSolucion(String tipoRemesaSolucion) {
        this.tipoRemesaSolucion = tipoRemesaSolucion;
    }

    public String getUltimoSeguimiento() {
        return ultimoSeguimiento;
    }

    public void setUltimoSeguimiento(String ultimoSeguimiento) {
        this.ultimoSeguimiento = ultimoSeguimiento;
    }

    public String getCeOpUltimoSeguimiento() {
        return ceOpUltimoSeguimiento;
    }

    public void setCeOpUltimoSeguimiento(String ceOpUltimoSeguimiento) {
        this.ceOpUltimoSeguimiento = ceOpUltimoSeguimiento;
    }

    public String getFechaUltimoSeguimiento() {
        return fechaUltimoSeguimiento;
    }

    public void setFechaUltimoSeguimiento(String fechaUltimoSeguimiento) {
        this.fechaUltimoSeguimiento = fechaUltimoSeguimiento;
    }

    public String getNumRemesaSolucion() {
        return numRemesaSolucion;
    }

    public void setNumRemesaSolucion(String numRemesaSolucion) {
        this.numRemesaSolucion = numRemesaSolucion;
    }

    public String getCanoObservEjecucion() {
        return canoObservEjecucion;
    }

    public void setCanoObservEjecucion(String canoObservEjecucion) {
        this.canoObservEjecucion = canoObservEjecucion;
    }

    public String getNombreCeOpEjecuta() {
        return nombreCeOpEjecuta;
    }

    public void setNombreCeOpEjecuta(String nombreCeOpEjecuta) {
        this.nombreCeOpEjecuta = nombreCeOpEjecuta;
    }

    public String getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(String fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public String getUsuarioEjecucion() {
        return usuarioEjecucion;
    }

    public void setUsuarioEjecucion(String usuarioEjecucion) {
        this.usuarioEjecucion = usuarioEjecucion;
    }

    public String getDescCausalNov() {
        return descCausalNov;
    }

    public void setDescCausalNov(String descCausalNov) {
        this.descCausalNov = descCausalNov;
    }

    public String getFechaFinReofrecimiento() {
        return fechaFinReofrecimiento;
    }

    public void setFechaFinReofrecimiento(String fechaFinReofrecimiento) {
        this.fechaFinReofrecimiento = fechaFinReofrecimiento;
    }

    public String getCierrePorProcedimiento() {
        return cierrePorProcedimiento;
    }

    public void setCierrePorProcedimiento(String cierrePorProcedimiento) {
        this.cierrePorProcedimiento = cierrePorProcedimiento;
    }

    public String getTipoCobertura() {
        return tipoCobertura;
    }

    public void setTipoCobertura(String tipoCobertura) {
        this.tipoCobertura = tipoCobertura;
    }

    public int getIdCausalNovedad() {
        return idCausalNovedad;
    }

    public void setIdCausalNovedad(int idCausalNovedad) {
        this.idCausalNovedad = idCausalNovedad;
    }

    public String getMotivoEjecucion() {
        return motivoEjecucion;
    }

    public void setMotivoEjecucion(String motivoEjecucion) {
        this.motivoEjecucion = motivoEjecucion;
    }

    public String getIdUnidadesNegocio() {
        return idUnidadesNegocio;
    }

    public void setIdUnidadesNegocio(String idUnidadesNegocio) {
        this.idUnidadesNegocio = idUnidadesNegocio;
    }

    public String getIdTiposCobertura() {
        return idTiposCobertura;
    }

    public void setIdTiposCobertura(String idTiposCobertura) {
        this.idTiposCobertura = idTiposCobertura;
    }

    public int getConNovedad() {
        return conNovedad;
    }

    public void setConNovedad(int conNovedad) {
        this.conNovedad = conNovedad;
    }

    public String getIdEstadosNovedad() {
        return idEstadosNovedad;
    }

    public void setIdEstadosNovedad(String idEstadosNovedad) {
        this.idEstadosNovedad = idEstadosNovedad;
    }

    public String getIdUbicaciones() {
        return idUbicaciones;
    }

    public void setIdUbicaciones(String idUbicaciones) {
        this.idUbicaciones = idUbicaciones;
    }

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getPosicionRuta() {
        return posicionRuta;
    }

    public void setPosicionRuta(String posicionRuta) {
        this.posicionRuta = posicionRuta;
    }

    public String getCountProcesosAsoc() {
        return countProcesosAsoc;
    }

    public void setCountProcesosAsoc(String countProcesosAsoc) {
        this.countProcesosAsoc = countProcesosAsoc;
    }

    public String getReex() {
        return reex;
    }

    public void setReex(String reex) {
        this.reex = reex;
    }

    private String sqlType = "OBJ_NOV_MASIVA_T";

    public String getSQLTypeName() throws SQLException {
        return sqlType;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public int getIdTipoServicioEspecial() {
        return idTipoServicioEspecial;
    }

    public void setIdTipoServicioEspecial(int idTipoServicioEspecial) {
        this.idTipoServicioEspecial = idTipoServicioEspecial;
    }

    public int getCodigoNovedadComplemento() {
        return codigoNovedadComplemento;
    }

    public void setCodigoNovedadComplemento(int codigoNovedadComplemento) {
        this.codigoNovedadComplemento = codigoNovedadComplemento;
    }
    
    public int getCodigoJustificacionNovedad() {
        return codigoJustificacionNovedad;
    }

    public void setCodigoJustificacionNovedad(int codigoJustificacionNovedad) {
        this.codigoJustificacionNovedad = codigoJustificacionNovedad;
    }

    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        sqlType = typeName;
    }

    public void writeSQL(SQLOutput stream) throws SQLException {
        Date fecha;

        stream.writeString(numRemesa);
        stream.writeInt(idUnidadNegocio);
        stream.writeInt(idTipoTransporte);

        fecha = Util.parseDate(fechaInicio);
        stream.writeTimestamp(fecha == null ? null : new java.sql.Timestamp(fecha.getTime()));

        fecha = Util.parseDate(fechaFin);
        stream.writeTimestamp(fecha == null ? null : new java.sql.Timestamp(fecha.getTime()));

        stream.writeString(idEstadoRemesa);
        stream.writeString(idCeOpOrigen);
        stream.writeString(idCeOpDestino);
        stream.writeBigDecimal(new BigDecimal(idCiudadOrigen));
        stream.writeBigDecimal(new BigDecimal(idCiudadDestino));
        stream.writeLong(idTipoServicioOperativo);
        stream.writeLong(idTipoDocReferencia);
        stream.writeString(numDocReferencia);
        stream.writeBigDecimal(new BigDecimal(idRemitente));
        stream.writeBigDecimal(new BigDecimal(idDestinatario));
        stream.writeLong(idCuentaRemitente);
        stream.writeLong(idCuentaDestintario);
        stream.writeBigDecimal(new BigDecimal(idSedeRemitente));
        stream.writeBigDecimal(new BigDecimal(idSedeDestinatario));
        stream.writeLong(idTipoUnidad);
        stream.writeString(codigoUnidad);
        stream.writeString(vehiculo);
        stream.writeLong(numDespachoCliente);
        stream.writeInt(numPag);
        stream.writeInt(numRegs);
        stream.writeLong(idEstadoUnidad);
        stream.writeString(tipoNovedad);
        stream.writeInt(idCeOpDestNacional);
        stream.writeInt(idCeOpOrigNacional);
        stream.writeString(codVanNacional);
        stream.writeString(numViajeNacional);
        
        fecha = Util.parseDate(fechaInicioViajeNacional);
        stream.writeTimestamp(fecha == null ? null : new java.sql.Timestamp(fecha.getTime()));

        fecha = Util.parseDate(fechaFinViajeNacional);
        stream.writeTimestamp(fecha == null ? null : new java.sql.Timestamp(fecha.getTime()));

        stream.writeString(numViajeLocal);

        fecha = Util.parseDate(fechaInicioEntrega);
        stream.writeTimestamp(fecha == null ? null : new java.sql.Timestamp(fecha.getTime()));

        fecha = Util.parseDate(fechaFinEntrega);
        stream.writeTimestamp(fecha == null ? null : new java.sql.Timestamp(fecha.getTime()));

        stream.writeInt(procIdInt);
        stream.writeString(codRecogida);
        stream.writeString(codUnidadContenedora);
        stream.writeString(idTiposCobertura);

        stream.writeInt(conNovedad);
        stream.writeString(idEstadosNovedad);
        stream.writeString(idUbicaciones);
        
        stream.writeInt(licencia);
    }

    /* Motodo para imprimir los atributos del objeto con los valores correspondientes
     * que le eston llegando desde el cliente */
    public String printR() {
        String result = "";

        // determine fields declared in this class only (no fields of superclass)
        Field[] fields = this.getClass().getDeclaredFields();

        //print field names paired with their values
        for (Field field : fields) {
            try {
                result += field.getName() + " : " + field.get(this) + "\n";
            } catch (IllegalAccessException ex) {
                System.out.println(ex);
            }
        }

        return result;
    }

    /**
     * @return the numeroRemesaSolucion
     */
    public String getNumeroRemesaSolucion() {
        return numeroRemesaSolucion;
    }

    /**
     * @param numeroRemesaSolucion the numeroRemesaSolucion to set
     */
    public void setNumeroRemesaSolucion(String numeroRemesaSolucion) {
        this.numeroRemesaSolucion = numeroRemesaSolucion;
    }

    /**
     * @return the tipoDeSolucion
     */
    public String getTipoDeSolucion() {
        return tipoDeSolucion;
    }

    /**
     * @param tipoDeSolucion the tipoDeSolucion to set
     */
    public void setTipoDeSolucion(String tipoDeSolucion) {
        this.tipoDeSolucion = tipoDeSolucion;
    }
}
