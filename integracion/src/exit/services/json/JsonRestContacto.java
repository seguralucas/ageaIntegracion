package exit.services.json;

import exit.services.excepciones.ExceptionAnioInvalido;
import exit.services.excepciones.ExceptionEstadoInvalido;
import exit.services.excepciones.ExceptionFormatoFecha;
import exit.services.excepciones.ExceptionIDNoNumerico;
import exit.services.excepciones.ExceptionIDNullIncidente;
import exit.services.excepciones.ExceptionLongitud;
import exit.services.excepciones.ExceptionModoContactoInvalido;
import exit.services.excepciones.ExceptionTipoIncidenteInvalido;

public class JsonRestContacto implements IJsonRestEstructura{
	/*************************************/
	
	/*************************************/

	/***********************************************/
	private JSONHandler json;
	private String  line;

	
	@Override
	public JSONHandler createJson(TipoTarea tarea)
			throws ExceptionLongitud, ExceptionEstadoInvalido, ExceptionTipoIncidenteInvalido, ExceptionIDNullIncidente,
			ExceptionModoContactoInvalido, ExceptionIDNoNumerico, ExceptionFormatoFecha, ExceptionAnioInvalido {

		return null;
	}
	private String bpSapId;
	private String bpWccId;
	private String estadoSuscriptor;
	private String nombre;
	private String apellido;
	private String tipoDocuemnto;
	private String nroDocumento;
	private String estadoCivil;
	private String fechaNacimiento;
	private String telefonia;
	private String email;
	private String nacionalidad;
	private String sexo;
	private String calle;
	private String numero;
	private String localidad;
	private String latitud;
	private String longitud;
	private String tipoSocio;
	private String adicionalesEnUso;
	private String campana;
	private String promocion;
	private String grupoDeCliente;

	@Override
	public void agregarCampo(String cabecera, String valor) {
		switch(cabecera){
		case "BP_SAP_ID": setBpSapId(valor); break;
		case "BP_WCC_ID": setBpWccId(valor); break;
		case "ESTADO_SUSCRIPTOR": setEstadoSuscriptor(valor); break;
		case "NOMBRE": setNombre(valor); break;
		case "APELLIDO": setApellido(valor); break;
		case "TIPO_DOCUMENTO": setTipoDocuemnto(valor); break;
		case "NRO_DOCUMENTO": setNroDocumento(valor); break;
		case "ESTADO_CIVIL": setEstadoCivil(valor); break;
		case "FECHA_NACIMIENTO": setFechaNacimiento(valor); break;
		case "TELEFONO": setTelefonia(valor); break;
		case "EMAIL": setEmail(valor); break;
		case "NACIONALIDAD": setNacionalidad(valor); break;
		case "SEXO": setSexo(valor); break;
		case "CALLE": setCalle(valor); break;
		case "NUMERO": setNumero(valor); break;
		case "LOCALIDAD": setLocalidad(valor); break;
		case "LATITUD": setLatitud(valor); break;
		case "LONGITUD": setLongitud(valor); break;
		case "TIPO_SOCIO": setTipoSocio(valor); break;
		case "ADICIONALES_EN_USO": setAdicionalesEnUso(valor); break;
		case "CAMPA�A": setCampana(valor); break;
		case "PROMOCION": setPromocion(valor); break;
		case "GRUPO_DE_CLIENTE": setGrupoDeCliente(valor); break;
		}
	}	



	@Override
	public String getLine() {
		return line;
	}

	@Override
	public void setLine(String line) {
		this.line=line;
	}

	public JSONHandler getJson() {
		return json;
	}

	public void setJson(JSONHandler json) {
		this.json = json;
	}

	public String getBpSapId() {
		return bpSapId;
	}

	public void setBpSapId(String bpSapId) {
		this.bpSapId = bpSapId;
	}

	public String getBpWccId() {
		return bpWccId;
	}

	public void setBpWccId(String bpWccId) {
		this.bpWccId = bpWccId;
	}

	public String getEstadoSuscriptor() {
		return estadoSuscriptor;
	}

	public void setEstadoSuscriptor(String estadoSuscriptor) {
		this.estadoSuscriptor = estadoSuscriptor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTipoDocuemnto() {
		return tipoDocuemnto;
	}

	public void setTipoDocuemnto(String tipoDocuemnto) {
		this.tipoDocuemnto = tipoDocuemnto;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getTelefonia() {
		return telefonia;
	}

	public void setTelefonia(String telefonia) {
		this.telefonia = telefonia;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getTipoSocio() {
		return tipoSocio;
	}

	public void setTipoSocio(String tipoSocio) {
		this.tipoSocio = tipoSocio;
	}

	public String getAdicionalesEnUso() {
		return adicionalesEnUso;
	}

	public void setAdicionalesEnUso(String adicionalesEnUso) {
		this.adicionalesEnUso = adicionalesEnUso;
	}

	public String getCampana() {
		return campana;
	}

	public void setCampana(String campana) {
		this.campana = campana;
	}

	public String getPromocion() {
		return promocion;
	}

	public void setPromocion(String promocion) {
		this.promocion = promocion;
	}

	public String getGrupoDeCliente() {
		return grupoDeCliente;
	}

	public void setGrupoDeCliente(String grupoDeCliente) {
		this.grupoDeCliente = grupoDeCliente;
	}




}
