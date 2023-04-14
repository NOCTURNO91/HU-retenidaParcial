"use strict";

angular.module("app.novedadesMasivas", [])
        .controller("novedadesMasivasController", ["$scope", "$uibModal", "$http", "Alert", "UtilService", "UsuarioApi", "Listas", "ClientesApi", "NovedadesMasivasApi", "$window", "$timeout", "Lightbox", "Confirm", "$localStorage", novedadesMasivasController])
        .controller("modalUnidadesRemesaController", ["$scope", "$uibModalInstance", "$uibModal", "Alert", "NovedadesMasivasApi", "params", modalUnidadesRemesaController])
        .controller("modalTrazabilidadUnidadesRemesaController", ["$scope", "$uibModalInstance", "Alert", "NovedadesMasivasApi", "params", modalTrazabilidadUnidadesRemesaController])
        .controller("modalNovedadesUnidadesRemesaController", ["$scope", "$uibModalInstance", "Alert", "NovedadesMasivasApi", "Lightbox", "params", modalNovedadesUnidadesRemesaController])
        .controller("modalDocumentosClienteRemesaController", ["$scope", "$uibModalInstance", "Alert", "NovedadesMasivasApi", "params", modalDocumentosClienteRemesaController])
        .controller("modalFormatosImpresionController", ["$scope", "$uibModalInstance", "Alert", "NovedadesMasivasApi", "$window", "params", modalFormatosImpresionController])
        .controller("modalOpcionesExportacionController", ["$scope", "$uibModalInstance", "Alert", "NovedadesMasivasApi", "$window", "$timeout", "$interval", "params", modalOpcionesExportacionController]);

function novedadesMasivasController($scope, $uibModal, $http, Alert, UtilService, UsuarioApi, Listas, ClientesApi, NovedadesMasivasApi, $window, $timeout, Lightbox, Confirm, $localStorage) {

    $scope.NOMBRE_APLICACION_NOVEDADES = "NovedadesMasivasGOS";

    $scope.roles = [];
    $scope.IS_GESTION_AGENDAMIENTO = false;
    
    //HU:12772  @DATE:23/08/2021  @Author:Manuel Gallardo -- Softcaribbean 
    $scope.IS_ADMINISTRADOR = false;
    

    $scope.INHABILITAR_BOTON_SOLUCION_NOVEDAD = false;
    $scope.INHABILITAR_BOTON_EJECUTAR_NOVEDAD = false;

    $scope.botones = {
        btnConsultar: false,
        btnIngresarNovedad: false,
        btnSolucionarNovedad: false,
        btnEjecutarNovedad: false,
        btnImprimirRotulos: false,
        btnExportarExcel: false,
        btnVerUnidadesGrid: false,
        btnVerDocumentosClienteGrid: false,
        btnVerNovedadesRemesaGrid: false,
        btnVerCumplidoRemesaGrid: false
    };

    
    //----------------------------------------------------------------------------------------------------

    $scope.columnasGrid = [
        {name: 'selected', displayName: '', enableCellEdit: false, width: 50, type: 'boolean', enableFiltering: false, allowCellFocus: false,
            pinnedLeft: true, cellTemplate: 'check.template.html', headerCellTemplate: 'seltodos.template.html'
        },
        {name: 'verUnidades', displayName: '', enableCellEdit: false, width: 50, enableFiltering: false, allowCellFocus: false,
            pinnedLeft: true, cellTemplate: 'btn.unidades.html'
        },
        {name: 'documentosCliente', displayName: '', enableCellEdit: false, width: 50, enableFiltering: false, allowCellFocus: false,
            pinnedLeft: true, cellTemplate: 'btn.documentoscliente.html'
        },
        {name: 'novedades', displayName: '', enableCellEdit: false, width: 50, enableFiltering: false, allowCellFocus: false,
            pinnedLeft: true, cellTemplate: 'btn.novedades.html'
        },
        {name: 'cumplido', displayName: '', enableCellEdit: false, width: 50, enableFiltering: false, allowCellFocus: false,
            pinnedLeft: true, cellTemplate: 'btn.cumplido.html'
        },
        {name: 'numRemesa', displayName: 'N\u00fam remesa', enableCellEdit: false, width: 100, allowCellFocus: false},
        {name: 'unidadNegocio', displayName: 'Un. negocio', enableCellEdit: false, width: 150, allowCellFocus: false},
        {name: 'descLicencia', displayName: 'Licencia', enableCellEdit: false, width: 150, allowCellFocus: false},
        {name: 'unidadesRemesa', displayName: 'Unidades', enableCellEdit: false, width: 60, allowCellFocus: false},
        {name: 'fechaRemesa', displayName: 'Fecha', width: 100, enableCellEdit: false, allowCellFocus: false},
        {name: 'estadoRemesa', width: 200, enableCellEdit: false},
        {name: 'descProceso', displayName: 'Proceso remesa', width: 200, enableCellEdit: false},
        {name: 'consecutivoNovedad', width: 100, enableCellEdit: false, allowCellFocus: false},
        {name: 'estadoNovedad', width: 100, enableCellEdit: false, allowCellFocus: false},
        {name: 'unidadesNovedad', width: 100, enableCellEdit: false},
        {name: 'usuarioPlantea', displayName: 'Usuario plantea', width: 200, enableCellEdit: false},
        {name: 'formaPago', width: 200, enableCellEdit: false},
        {name: 'cuenta', width: 200, enableCellEdit: false},
        {name: 'tipoTransporte', width: 70, enableCellEdit: false},
        {name: 'tipoServicioOperativo', width: 70, enableCellEdit: false},
        {name: 'ceOpOrigen', width: 100, enableCellEdit: false},
        {name: 'ciudadOrigen', width: 100, enableCellEdit: false},
        {name: 'identificacionRemitente', width: 200, enableCellEdit: false},
        {name: 'nombreRemitente', width: 200, enableCellEdit: false},
        {name: 'sedeRemitente', width: 200, enableCellEdit: false},
        {name: 'direccionRemitente', width: 100, enableCellEdit: false},
        {name: 'telefonoRemitente', width: 200, enableCellEdit: false},
        {name: 'ceOpDestino', width: 200, enableCellEdit: false},
        {name: 'ciudadDestino', width: 200, enableCellEdit: false},
        {name: 'tipoCobertura', width: 200, enableCellEdit: false},
        {name: 'identificacionDestinatario', width: 150, enableCellEdit: false},
        {name: 'nombreDestinatario', width: 150, enableCellEdit: false},
        {name: 'sedeDestinatario', width: 100, enableCellEdit: false},
        {name: 'direccionDestinatario', width: 100, enableCellEdit: false},
        {name: 'telefonoDestinatario', width: 100, enableCellEdit: false},
        // {name: 'descTipoUnidad', width: 50, enableCellEdit: false},
        {name: 'pesoReal', width: 100, enableCellEdit: false},
        {name: 'pesoVolumen', width: 100, enableCellEdit: false},
        {name: 'observaciones', width: 300, enableCellEdit: false},
        {name: 'usuario', width: 100, enableCellEdit: false},
        {name: 'viaje', width: 100, enableCellEdit: false},
        {name: 'van', width: 100, enableCellEdit: false},
        {name: 'fechaProgramacionNacional', width: 100, enableCellEdit: false},
        {name: 'movilLocal', width: 100, enableCellEdit: false},
        {name: 'viajeLocal', width: 100, enableCellEdit: false},
        {name: 'rutaLocal', width: 100, enableCellEdit: false},
        //{name: 'posicion', width: 100, enableCellEdit: false},
        {name: 'fechaProgramacionLocal', width: 100, enableCellEdit: false}
    ];


    $scope.opcionesGrid = {
        enableRowHeaderSelection: true,
        enableSorting: true,
        enableGridMenu: true,
        enableCellEditOnFocus: true,
        enableFiltering: true,
        enableRowSelection: true,
        rowEditWaitInterval: -1,
        rowHeight: 35,
        columnDefs: $scope.columnasGrid,

        onRegisterApi: function (gridApi) {

            $scope.gridApi = gridApi;

            gridApi.edit.on.afterCellEdit($scope, function (rowEntity, colDef, newValue, oldValue) {


                // Solo escucho cambios en la columna de ruta
                if (colDef.name === "ruta") {

                    const idx = $scope.listaRutasAsignacion.findIndex(function (ruta) {
                        return ruta.ID_RUTA === newValue;
                    });

                    if (idx >= 0) {  }

                } else if (colDef.name === "codigoRuta") {

                    if (newValue !== oldValue) { }

                } else if (colDef.name === "viaje") {

                    // Si se necesita una validacion

                } else if (colDef.name === "selected") {

                    if (newValue !== oldValue) { }
                }

                // limpiar inputs asignacion de serivicios
                if (newValue !== oldValue) {

                }

                $timeout(function () {
                    $scope.$apply();
                }, 150);
            });


            // Para guardar el estado de la grid
            // Setup events so we're notified when grid state changes.
            $scope.gridApi.colMovable.on.columnPositionChanged($scope, saveState);
            $scope.gridApi.colResizable.on.columnSizeChanged($scope, saveState);
            $scope.gridApi.core.on.columnVisibilityChanged($scope, saveState);
            $scope.gridApi.core.on.filterChanged($scope, saveState);
            $scope.gridApi.core.on.sortChanged($scope, saveState);
            $scope.gridApi.pinning.on.columnPinned($scope, saveState);

            // Restore previously saved state.
            restoreState();
        }
    };

    function saveState() {
        var state = $scope.gridApi.saveState.save();
        $localStorage.gridStateMasivas = state;
    }

    function restoreState() {
        $timeout(function () {

            var state = $localStorage.gridStateMasivas;

            if (state){
                $scope.gridApi.saveState.restore($scope, state);
            }

        }, 100);
    }

    $scope.seleccionadosGrid = 0;
    
    //----------------------------------------------------------------------------------------------------

    $scope.validarRangoDeFechasHabiles = function (rangoDias) {

        var success = function (res) {
            if (res.codigo === 0) {
                $scope.DIAS_HABIL_DEVOL_FEREAL = res.list;
                $scope.NUM_DIAS_HABIL_DEVOL_FEREAL = rangoDias;
                console.log('$scope.DIAS_HABIL_DEVOL_FEREAL', $scope.DIAS_HABIL_DEVOL_FEREAL);
            }
        }

        var error = function (res) {
        }

        const params = {
            fechaHabil: moment.unix(new Date()).format("X"), // Valida el dia en el cual iniciara la busqueda hacia atr�s
            rangoDiasFechaReal: rangoDias // variable de sistem DIAS_HABIL_DEVOL_FEREAL
        };

        return NovedadesMasivasApi.consultar({action: "consultardiashabiles"}, params).$promise.then(success, error)
    }

    $scope.setSeleccionarTodosServicios = function (seleccionado) {

        var conteo = 0;

        var filasVisibles = $scope.gridApi.core.getVisibleRows();

        filasVisibles.forEach(function (fila, idx) {

            fila.entity.selected = seleccionado;
            conteo += 1;

        });

        if (seleccionado === false) {
            $scope.seleccionadosGrid = 0;
        } else {
            $scope.seleccionadosGrid = conteo;
        }

        $scope.validarDeshabilitarOtrasFunciones();

    };

    $scope.setSeleccionarUno = function (seleccionado) {

        if (seleccionado)
            $scope.seleccionadosGrid += 1;
        else
            $scope.seleccionadosGrid -= 1;

        $scope.validarDeshabilitarOtrasFunciones();

    };


    $scope.init = function () {
        var callback = function (usuario) {

            var callbackParametrosSistema = function (valParametros) {

                var urlServer = valParametros[1];
                $scope.SERVER = urlServer.substring(0, 40);
                $scope._idUnnePaqueteria = valParametros[0];
                $scope.URL_ROTULOS = valParametros[1];
                $scope.ETIQUETA_DEFECTO = valParametros[2];
                $scope.URL_SERVICIO_CERT = valParametros[3];
                $scope.OPMO_IMPR_ETIQUETAS = valParametros[4];
                $scope.PROC_DSTR_MCIA = valParametros[5];
                $scope.PROC_RECI_MERCANCIA = valParametros[6];
                $scope.LIVA_EST_NOV_SOLUCIONADA = valParametros[7];
                $scope.LIVA_EST_NOV_EJECUTADA = valParametros[8];
                $scope.LIVA_EST_NOV_ANULADA = valParametros[9];
                $scope.SERV_GENERADOR_ROTULOS_UC = valParametros[10];
                $scope.ROTU_ID_INT_UC = valParametros[11];
                $scope.LIVA_ESON_REM_ANULADA = valParametros[12];
                $scope.MIN_CARACTERES_OBS_NOVEDAD = parseInt(valParametros[13]);
                $scope.PAGINADO_NOVEDADES_MASIVAS = parseInt(valParametros[14]);
                $scope.URL_SERVIDOR_GOS = valParametros[15]
                $scope.ID_NOVEDAD_GEST_CITA = valParametros[16];
                $scope.ID_NOVEDAD_GEST_MALLA = valParametros[17];
                $scope.ROL_GESTION_AGENDAMIENTO = valParametros[18];

                $scope.DIAS_HABIL_DEVOL_FEREAL =  valParametros[19];
                $scope.NUM_DIAS_HABIL_DEVOL_FEREAL = valParametros[20];
                $scope.EST_FECHA_NOVEDAD_INACTIVA =  valParametros[21];
                
                $scope.ESON_REME_ENT_CNX =  valParametros[22];
                $scope.CIERRE_FECHA_NOVEDAD = valParametros[23];
                $scope.ROL_ADMIN_NOV_MASIVA = valParametros[24]; //HU:12772  @DATE:23/08/2021  @Author:Manuel Gallardo -- Softcaribbean 
                

                if (isNaN($scope.MIN_CARACTERES_OBS_NOVEDAD) === true) {
                    $scope.MIN_CARACTERES_OBS_NOVEDAD = 50;
                }

                if (isNaN($scope.PAGINADO_NOVEDADES_MASIVAS) === true) {
                    $scope.PAGINADO_NOVEDADES_MASIVAS = 100;
                }

                $scope.consultarRoles(); 
                $scope._cargarListas();
                $scope.validarRangoDeFechasHabiles(valParametros[19]);
            };

            var listNomParametros = [
                "UNNE_PAQ", // 0
                "SERV_GENERADOR_ROTULOS", // 1
                "MODELO_ROTULO_PLAT", // 2
                "URL_SERVICIO_CERT", // 3
                "OPMO_IMPR_ETIQUETAS", // 4
                "PROC_DSTR_MCIA", // 5
                "PROC_RECI_MERCANCIA", // 6
                "LIVA_EST_NOV_SOLUCIONADA", // 7
                "LIVA_EST_NOV_EJECUTADA", // 8
                "LIVA_EST_NOV_ANULADA", // 9
                "SERV_GENERADOR_ROTULOS_UC", //10
                "ROTU_ID_INT_UC", // 11
                "ESON_REM_ANU_SAS", // 12
                "MIN_CARACTERES_OBS_NOVEDAD", // 13
                "PAGINADO_NOVEDADES_MASIVAS", // 14
                "URL_SERVIDOR_GOS", //15
                "GEST_AGEN_CITA", //16 --ID NOVEDAD GEST CITA
                "GEST_AGEN_MALLA", //17 --- ID NOVEDAD GEST MALLA
                "ROL_GESTION_AGENDAMIENTO", //18
                "DIAS_HABIL_DEVOL_FEREAL", //19,
                "NUM_DIAS_HABIL_DEVOL_FEREAL", //20
                "EST_FECHA_NOVEDAD_INACTIVA", //21
                "ESON_REME_ENT_CNX",//22
                "CIERRE_FECHA_NOVEDAD", //23
                "ROL_ADMIN_NOV_MASIVA" //24 HU:12772  @DATE:23/08/2021  @Author:Manuel Gallardo -- Softcaribbean 
            ];

            $scope.usuario = usuario;
            UtilService.obtenerParametrosSistema($http, listNomParametros, Alert, callbackParametrosSistema);
        };

        $scope.format = "dd/MM/yyyy";
        $scope.dateOptions = {
            formatYear: "yyyy",
            formatMonth: "MM",
            startingDay: 1
        };

        $scope.datePicker = {
            clearText: "Limpiar",
            closeText: "Cerrar",
            currentText: "Hoy"
        };

        $scope._idUnnePaqueteria = "";

        $scope._cargarCiudades = false;
        $scope._preventDefaultCiudad = false;

        $scope._cargarClientes = false;
        $scope._preventDefaultClientes = false;

        $scope.listaRemitentes = [];
        $scope.listaDestinatarios = [];

        $scope.listaFiltroNovedad = [{
                id: 1,
                descripcion: "CON NOVEDAD"
            },
            {
                id: 2,
                descripcion: "SIN NOVEDAD"
            }
        ];

        UtilService.traerUsuario($http, Alert, callback);
        // alert( "CARGAR LISTAS!!" );

        $scope.generarIdHeaders();
        $scope.limpiar();
    };

    function validarHabilitarGestionAgendamiento(roles) {
        for (var i in $scope.roles) {            
            if ($scope.ROL_GESTION_AGENDAMIENTO == $scope.roles[i]) {                
                $scope.IS_GESTION_AGENDAMIENTO = true;
                break;
            }
        }
    }
    
    function validarRolAdministrador(roles){
        for (var i in $scope.roles) {
            if($scope.ROL_ADMIN_NOV_MASIVA == $scope.roles[i] ){
                $scope.IS_ADMINISTRADOR = true;
                break;
            }        
        }
    }

    $scope.consultarRoles = function () {
        var success = function (response) {
            $scope.roles = response;

            validarHabilitarGestionAgendamiento($scope.roles);
            validarRolAdministrador($scope.roles);
        };

        var error = function (response)
        {


            Alert.abrir(response.data.mensaje ? response.data.mensaje : "Ocurrio un error de Comunicacion.");

        };

        UsuarioApi.roles({action: "/roles"})
                .$promise.then(success, error);

    };

    $scope.generarIdHeaders = function ()
    {
        /*
         $( "table#tbHeaders tr#trHeaders th" ).each( function( i ) {
         
         this.id = "header-" + i;
         } );
         */

        $("tr#result th").each(function (i) {

            this.id = "res-" + i;

        });
    };


    //------

    $scope.selectRow = function () {

    };

    //------

    $scope._cargarListas = function ()
    {
        var numListas = 0;

        var callbackListas = function ()
        {
            numListas++;

            if (numListas === 15)
            {
                Util.ocultarCargando();
                $scope.seleccionarValoresDefecto();

                var urlParams = Util.getUrlParams($window.location.hash.substring(21));

                if (urlParams.rem)
                {
                    var rem = JSON.parse(atob(urlParams.rem));

                    $scope.paramsConsulta.numRemesa = [{
                            text: rem.numRemesa
                        }];
                }
            }
            $scope.obtenerTiposServicioOperativo();
        };

        var callbackUnidadNegocio = function (listaUnidadesNegocio)
        {
            $scope.listaUnidadesNegocio = listaUnidadesNegocio;
            callbackListas();

            if (listaUnidadesNegocio.length === 0) {
                Alert.abrir("No se encontraron unidades de negocio parametrizadas al usuario.");
            } else {
                $scope.obtenerTiposServicioOperativo();
            }

        };

        var callbackTipoTransporte = function (listaTiposTransporte)
        {
            $scope.listaTiposTransporte = listaTiposTransporte;
            callbackListas();
        };

        var callbackEstadosRemesa = function (listaEstadosRemesa)
        {
            $scope.listaEstadosRemesa = listaEstadosRemesa;
            callbackListas();
        };

        var callbackCeOp = function (listaCentros)
        {
            $scope.listaCentrosOrigen = listaCentros;
            callbackListas();
        };

        var callbackTipoServicio = function (listaTipoServicio)
        {

            $scope.listaTipoServicio = listaTipoServicio;
            callbackListas();
        };

        var callbackTipoDocumento = function (listaTipoDocumento)
        {
            $scope.listaTipoDocumento = listaTipoDocumento;
            callbackListas();
        };

        var callbackTipoUnidad = function (listaTipoUnidad)
        {
            $scope.listaTipoUnidad = listaTipoUnidad;
            callbackListas();
        };

        var callbackEstadosUnidad = function (listaEstadosUnidad)
        {
            $scope.listaEstadosUnidad = listaEstadosUnidad;
            callbackListas();
        };

        var callbackCiudades = function (listaCiudades)
        {
            $scope._cargarCiudades = true;
            $scope.listaCiudadesOrigen = [].concat(listaCiudades);
            $scope.listaCiudadesDestino = [].concat(listaCiudades);

            callbackListas();
        };

        var callbackClientes = function (listaClientes)
        {
            $scope.listaRemitentes = [].concat(listaClientes);
            $scope.listaDestinatarios = [].concat(listaClientes);

            callbackListas();
        };

        var callbackProcesos = function (listaProcesos)
        {
            $scope.listaProcesos = listaProcesos;
            callbackListas();
        };

        var callbackTiposCobertura = function (listaTiposCobertura)
        {
            $scope.listaTiposCobertura = listaTiposCobertura;
            callbackListas();
        };

        var callbackCeOpUsuario = function (listaCentros)
        {
            $scope.listaCentrosDestino = [].concat(listaCentros);
            callbackListas();

            if (listaCentros.length === 0) {
                Alert.abrir("No se encontraron centros de operaci\u00f3n asociados al usuario.");
            }
        };

        var callbackEstadoNovedad = function (listaEstadoNovedad)
        {
            $scope.listaEstadoNovedad = listaEstadoNovedad;
            callbackListas();
        };


        var paramsUnidadNegocio = {
            tipoLista: "UNNE_USUARIO",
            usuario: $scope.usuario.user,
            mostrarCargando: false,
            ocultarCargando: false
        };

        var paramsTipoTransporte = {
            tipoLista: "TIPO_TRANSPORTE",
            mostrarCargando: false,
            ocultarCargando: false
        };

        var paramsEstadosRemesa = {
            tipoLista: "ESTADOS_REMESA",
            mostrarCargando: false,
            ocultarCargando: false
        };

        var paramsCeOp = {
            mostrarCargando: false,
            ocultarCargando: false
        };

        var paramsTipoServicio = {
            obtenerListaDos: true,
            tipoLista: "TIPO_SERV_OP",
            //expansionUno: busqueda,
            //expansionDos: idCeOps

            /*abreviatura: "TIPO_SERV_OP",
             mostrarCargando: false,
             ocultarCargando: false*/
        };

        var paramsTipoDocumento = {
            tipoLista: "TIPO_DOCUMENTO",
            mostrarCargando: false,
            ocultarCargando: false
        };

        var paramsTipoUnidad = {
            tipoLista: "TIPO_UNIDADES",
            mostrarCargando: false,
            ocultarCargando: false
        };

        var paramsEstadosUnidad = {
            tipoLista: "ESTADOS_UNIDAD",
            mostrarCargando: false,
            ocultarCargando: false
        };

        var parametrosCiudades = {
            tipoLista: "CIUDADES_DOS",
            expansionUno: "0",
            mostrarCargando: false,
            ocultarCargando: false
        };

        var paramsProcesos = {
            tipoLista: "PROCESO",
            mostrarCargando: false,
            ocultarCargando: false
        };

        var paramsTiposCobertura = {
            tipoLista: "TIPOS_COBERTURA",
            obtenerListaDos: true,
            mostrarCargando: false,
            ocultarCargando: false
        };

        var paramsCeOpUsuario = {
            tipoLista: "CEOP_USUARIO",
            usuario: $scope.usuario.user,
            mostrarCargando: false,
            ocultarCargando: false
        };

        var paramsEstadoNovedad = {
            abreviatura: "EST_NVD_LGD",
            mostrarCargando: false,
            ocultarCargando: false
        };

        Util.mostrarCargando();

        Listas.consultar($http, paramsUnidadNegocio, Alert, callbackUnidadNegocio);
        Listas.obtenerLista($http, paramsTipoTransporte, Alert, callbackTipoTransporte);
        Listas.obtenerLista($http, paramsEstadosRemesa, Alert, callbackEstadosRemesa);
        Listas.listarCentrosOperacion($http, Alert, paramsCeOp, callbackCeOp);
        Listas.obtenerLista($http, paramsTipoServicio, Alert, callbackTipoServicio);

        //Listas.obtenerListaStandard($http, paramsTipoServicio, Alert, callbackTipoServicio);

        Listas.consultar($http, paramsTipoDocumento, Alert, callbackTipoDocumento);
        Listas.consultar($http, paramsTipoUnidad, Alert, callbackTipoUnidad);
        Listas.consultar($http, paramsEstadosUnidad, Alert, callbackEstadosUnidad);
        Listas.obtenerLista($http, parametrosCiudades, Alert, callbackCiudades);
        Listas.obtenerLista($http, paramsProcesos, Alert, callbackProcesos);
        Listas.obtenerLista($http, paramsTiposCobertura, Alert, callbackTiposCobertura);
        Listas.consultar($http, paramsCeOpUsuario, Alert, callbackCeOpUsuario);
        Listas.obtenerListaStandard($http, paramsEstadoNovedad, Alert, callbackEstadoNovedad);
        $scope._consultarPermisos(callbackListas);

        $scope._cargarClientes = true;
        $scope._consultarCliente("TCC", false, callbackClientes);
    };

    $scope._consultarPermisos = function (callback)
    {
        try
        {
            var success = function (response)
            {
                if (response.codigo === 0)
                {
                    for (var attr in $scope.botones) {
                        $scope.botones[attr] = true;
                    }

                    for (var i in response.list) {
                        $scope.botones[response.list[i]] = false;
                    }

                    if (typeof callback === "function") {
                        callback();
                    }
                } else
                {
                    Util.ocultarCargando();
                    Alert.abrir(response.mensaje);
                }
            };

            var error = function (response)
            {
                Util.ocultarCargando();


                Alert.abrir("Error consultando permisos.");
            };

            var params = {
                usuario: $scope.usuario.user,
                aplicacion: $scope.NOMBRE_APLICACION_NOVEDADES
            };

            NovedadesMasivasApi.consultar({action: "consultarPermisos"}, params)
                    .$promise.then(success, error);
        } catch (ex)
        {

        }
    };

    $scope.ajustarColumnas = function ()
    {
        // $scope.generarIdHeaders();

        var lastId;
        var lastHeader;

        $("tr#header th").each(function (i) {

            this.id = "head-" + i;
            lastId = i;

            $("tr#header th#head-" + i + " div").width($("tr#result th#res-" + i + " div").width());
        });

        lastHeader = $("tr#header th#head-" + lastId + " div");
        lastHeader.width(lastHeader.width() + 2);

        $("tr#result").css("visibility", "collapse");
        $("tr#header").css("display", "inline");

        $("div#divResultados").scroll(function () {

            // $( "div#divHeader" ).scrollLeft( this.scrollLeft );
            document.getElementById("divHeader").scrollLeft = this.scrollLeft;
        })
                .width($("div#divHeader").width() + 11);
    };

    /*
     $scope.ajustarColumnas = function()
     {
     $( "table#tbResultados tr#tr-0 td" ).each( function( i ) {
     
     var header = $( "th#header-" + i );
     var thisInstance = $( this );
     
     if ( header.width() > thisInstance.width() ) {
     thisInstance.css( "width", header.width() + "px" );
     }
     else if ( header.width() < thisInstance.width() ) {
     header.css( "width", thisInstance.width() + "px" );
     }
     
     } );
     };
     */

    $scope.sombrearFila = function ($index)
    {
        if ($scope._sombreado >= 0) {
            $("tr#tr-" + $scope._sombreado).removeClass("tr-clicked");
        }

        $("tr#tr-" + $index).addClass("tr-clicked");
        $scope._sombreado = $index;
    };

    $scope.limpiarTipoNovedad = function ()
    {
        if ($scope.paramsConsulta._tipoNovedadAnt === "") {
            $scope.paramsConsulta._tipoNovedadAnt = $scope.paramsConsulta.tipoNovedad;
        } else
        {
            if ($scope.paramsConsulta._tipoNovedadAnt === $scope.paramsConsulta.tipoNovedad)
            {
                $scope.paramsConsulta.tipoNovedad = "";
                $scope.paramsConsulta._tipoNovedadAnt = "";
            } else {
                $scope.paramsConsulta._tipoNovedadAnt = $scope.paramsConsulta.tipoNovedad;
            }
        }

        // $scope.limpiarGrid();
    };

    $scope.limpiarGrid = function ()
    {
        $scope.listaRemesas = [];
        $scope._listaRemesas = [];

        $scope.remesasSel = {};
        $scope._seleccionarTodasRemesas = false;
    };

    $scope.seleccionarCeOpsOrig = function ()
    {
        $scope.isCeOpsOrigSelec = !$scope.isCeOpsOrigSelec;

        $scope.ceOpOrigen.selected = [];

        if ($scope.isCeOpsOrigSelec === true) {
            $scope.ceOpOrigen.selected = [].concat($scope.listaCentrosOrigen);
        }
    };

    $scope.seleccionarCeOpsDest = function ()
    {
        $scope.isCeOpsDestSelec = !$scope.isCeOpsDestSelec;

        $scope.ceOpDestino.selected = [];

        if ($scope.isCeOpsDestSelec === true)
        {
            $scope.ceOpDestino.selected = [].concat($scope.listaCentrosDestino);
            $scope.obtenerUbicacionesTcc('0');
        } else
        {
            $scope.listaUbicacionesTcc = [];
            $scope.ubicaciones = {selected: []};
        }
    };

    $scope.obtenerUbicacionesTcc = function (busqueda, limpiar)
    {
        var idCeOpDestino = "0";

        if (typeof limpiar !== "boolean") {
            limpiar = true;
        }

        if ($scope.ceOpDestino.selected.length > 0)
        {
            idCeOpDestino = $scope.ceOpDestino.selected[0].idInt;

            for (var i = 1; i < $scope.ceOpDestino.selected.length; i++) {
                idCeOpDestino += "," + $scope.ceOpDestino.selected[i].idInt;
            }
        }

        if (idCeOpDestino !== "0") {
            $scope._obtenerUbicacionesTcc(busqueda, idCeOpDestino, limpiar);
        }
    };

    $scope.obtenerTiposServicioOperativo = async function (mostrarCargando, callback) {
        // aqui
        if (typeof mostrarCargando !== "boolean") {
            mostrarCargando = true;
        }

        var unneSel = $scope.unidadNegocio.selected || [];
        var unneIdInt = "";

        $scope.listaTipoServicio = [];
        $scope.tipoServicio = {};

        unneSel.forEach(function (unne, idx) {

            if (idx === 0) {
                unneIdInt += unne.idInt;
            } else {
                unneIdInt += "," + unne.idInt;
            }
        });

        if (unneIdInt.length === 0) {
            $scope.licencia.selected = null;
            return;
        }

        if (unneIdInt.length >= 1) {
            $scope.licencia.selected = null;
        }

        if (mostrarCargando === true) {
            Util.mostrarCargando();
        }

        var params = {
            obtenerListaDos: true,
            tipoLista: "TIPO_SERV_OP",
            mostrarCargando: false,
            expansionUno: unneIdInt
        };

        var paramsLicenciaTrasporte = {
            obtenerListaDos: true,
            tipoLista: "LICENCIA_TRANSPORTE",

        };

        Listas.obtenerLista($http, params, Alert, function (listaTipoServOper) {

            $scope.listaTipoServicio = listaTipoServOper;

            if (mostrarCargando === true) {
                Util.ocultarCargando();
            }


        });

        Listas.obtenerLista($http, paramsLicenciaTrasporte, Alert, function (listaLicencia) {


            if (unneIdInt == 1) { // paqueteria

                $scope.listaLicencias = listaLicencia.filter(v => v.idInt == 3);

                $scope.licencia.selected = {
                    idInt: 3,
                    descripcion: "PAQUETERIA"
                };

            } else if (unneIdInt == 2) { //mensajeria

                $scope.listaLicencias = listaLicencia.filter(v => v.idInt == 1 || v.idInt == 2);

            } else if (unneIdInt == "1,2") {

                $scope.listaLicencias = listaLicencia.filter(v => v.idInt == 1 || v.idInt == 2 || v.idInt == 3);

            }

            if (mostrarCargando === true) {
                Util.ocultarCargando();
            }

        });


    };

    $scope._obtenerUbicacionesTcc = function (busqueda, idCeOps, limpiar)
    {
        if (busqueda.length === 0) {
            return;
        }

        if (limpiar === true)
        {
            $scope.listaUbicacionesTcc = [];
            $scope.ubicaciones = {selected: []};
        }

        var callbackUbicaciones = function (listaUbicacionesTcc)
        {
            $scope.listaUbicacionesTcc = listaUbicacionesTcc;
        };

        var params = {
            obtenerListaDos: true,
            tipoLista: "TIPO_UBICACION_TCC",
            expansionUno: busqueda,
            expansionDos: idCeOps
        };

        Listas.obtenerLista($http, params, Alert, callbackUbicaciones);
    };

    $scope.limpiar = function (seleccionarValoresDefecto)
    {
        if (typeof seleccionarValoresDefecto !== "boolean") {
            seleccionarValoresDefecto = true;
        }

        $scope.pagIni = 0;
        $scope.pagFin = 0;

        $scope.totalUnidades = 0;
        $scope._totalUnidades = 0;

        $scope.totalRegistros = 0;
        $scope._totalRegistros = 0;

        $scope._sombreado = -1;
        $scope.formatoTCC = false;

        $scope.isCeOpsOrigSelec = false;
        $scope.isCeOpsDestSelec = false;

        $scope.unidadNegocio = {
            selected: []
        };

        $scope.tipoTransporte = {};
        $scope.estadoRemesa = {selected: []};
        $scope.ceOpOrigen = {selected: []};
        $scope.ceOpDestino = {selected: []};
        $scope.ciudadOrigen = {};
        $scope.ciudadDestino = {};
        $scope.tipoServicio = {};
        $scope.tipoDocumento = {};
        $scope.cuentaRemitente = {};
        $scope.sedeRemitente = {};
        $scope.tipoUnidad = {};
        $scope.cuentaDestinatario = {};
        $scope.sedeDestinatario = {};
        $scope.remitente = {};
        $scope.destinatario = {};
        $scope.estadoUnidad = {};
        $scope.estadoNovedad = {selected: []};
        $scope.ubicaciones = {selected: []};
        $scope.licencia = {};

        $scope.filtroNovedad = {};
        $scope.ceOpDestinoNacional = {};
        $scope.ceOpOrigenNacional = {};
        $scope.proceso = {};
        $scope.tipoCobertura = {
            selected: []
        };

        $scope.paramsConsulta = {
            fechaInicio: new Date(),
            isOpenFechaInicio: false,
            fechaFin: new Date(),
            isOpenFechaFin: false,
            numRemesa: [],
            codUnidadContenedora: "",
            codRecogida: "",
            numDocReferencia: "",
            rangoRemesaInicio: "",
            rangoRemesaFin: "",
            numDocRemitente: "",
            nombreRemitente: "",
            numDocDestinatario: "",
            nombreDestinatario: "",
            codigoUnidad: "",
            vehiculo: "",
            numDespachoCliente: "",
            idCuentaRemitente: "",
            tipoNovedad: "",
            _tipoNovedadAnt: "",

            codVan: "",
            numViaje: "",
            numViajeLocal: "",

            fechaInicioViaje: "", // new Date(),
            isOpenFechaInicioViaje: false,
            fechaFinViaje: "",
            isOpenFechaFinViaje: false,
            fechaInicioEntrega: "",
            isOpenFechaInicioEntrega: false,
            fechaFinEntrega: "",
            isOpenFechaFinEntrega: false
        };

        $scope.remesasSel = {};

        $scope.listaCuentasRemitente = [];
        $scope.listaCuentasDestinatario = [];
        $scope.listaSedesRemitente = [];
        $scope.listaSedesDestinatario = [];

        $scope.listaRemesas = [];
        $scope._listaRemesas = [];

        $scope._seleccionarTodasRemesas = false;

        if (seleccionarValoresDefecto === true) {
            $scope.seleccionarValoresDefecto();
        }

        $scope.opcionesGrid.data = [];
    };

    $scope.seleccionarValoresDefecto = function ()
    {
        var i;

        for (i in $scope.listaCentrosDestino)
        {
            if ($scope.usuario.guicentroopera === "" + $scope.listaCentrosDestino[i].idInt)
            {
                $scope.ceOpDestino.selected = [$scope.listaCentrosDestino[i]];
                $scope.obtenerUbicacionesTcc("0");

                break;
            }
        }

        for (i in $scope.listaUnidadesNegocio)
        {
            if ($scope.usuario.guiuninegocio === "" + $scope.listaUnidadesNegocio[i].idInt)
            {
                $scope.unidadNegocio.selected = [$scope.listaUnidadesNegocio[i]];
                break;
            }
        }
    };

    $scope.paginarConsulta = function (numPag)
    {


        if (typeof numPag !== "number") {
            numPag = 1;
        }

        if (numPag > $scope.pagFin) {
            return;
        }

        $scope.consultar(numPag);
    };

    $scope.consultar = function (numPag, limpiarSel) {

        if (typeof numPag !== "number") {
            numPag = 1;
        }

        if (typeof limpiarSel !== "boolean") {
            limpiarSel = false;
        }

        if (numPag < 1) {
            return;
        }

        if ($scope._validarObligatorios() === false) {
            return;
        }

        var unidadNegSel = $scope.unidadNegocio.selected || [];
        var tipoTransporteSel = $scope.tipoTransporte.selected || {};
        var estadoRemesaSel = $scope.estadoRemesa.selected || [];
        var ceOpOrigenSel = $scope.ceOpOrigen.selected || [];
        var ciudOrigenSel = $scope.ciudadOrigen.selected || {};
        var ciudDestinoSel = $scope.ciudadDestino.selected || {};
        var tipoSevicioSel = $scope.tipoServicio.selected || {};
        var tipoDocSel = $scope.tipoDocumento.selected || {};
        var remitenteSel = $scope.remitente.selected || {};
        var destinatarioSel = $scope.destinatario.selected || {};
        var tipoUnidadSel = $scope.tipoUnidad.selected || {};
        // var cuentaRemitenteSel = $scope.cuentaRemitente.selected || {};
        var sedeRemitenteSel = $scope.sedeRemitente.selected || {};
        // var cuentaDestinatarioSel = $scope.cuentaDestinatario.selected || {};
        var sedeDestinatarioSel = $scope.sedeDestinatario.selected || {};
        var estadoUnidadSel = $scope.estadoUnidad.selected || {};
        var procesoSel = $scope.proceso.selected || {};
        var tipoCoberturaSel = $scope.tipoCobertura.selected || [];
        var estadoNovedadSel = $scope.estadoNovedad.selected || [];
        var ubicacionesSel = $scope.ubicaciones.selected || [];
        var licenciaSel = $scope.licencia.selected || {};

        var numDocReferencia = $.trim(($scope.paramsConsulta.numDocReferencia || ""));
        var idTipoDocReferencia = tipoDocSel.idInt || "0";

        if (numDocReferencia > 0 && idTipoDocReferencia === "0") {

            Alert.abrir("El tipo de documento de referencia es obligatorio.");
            return;

        }

        var filtroNovedadSel = $scope.filtroNovedad.selected || {};
        var idFiltroNovedad = filtroNovedadSel.id || 0;

        var params = $.extend({}, $scope.paramsConsulta);

        var numDespachoCliente = $.trim(($scope.paramsConsulta.numDespachoCliente || ""));
        var cuentaRemitente = $.trim(($scope.paramsConsulta.idCuentaRemitente || ""));
        var numRemesa = "";

        var success = function (response)
        {
            Util.ocultarCargando();

            $scope.pagIni = 0;
            $scope.pagFin = 0;

            $scope.totalUnidades = 0;
            $scope._totalUnidades = 0;

            $scope.totalRegistros = 0;
            $scope._totalRegistros = 0;

            if (limpiarSel === true) {
                $scope.remesasSel = {};
            }

            $scope.listaRemesas = [];
            $scope._listaRemesas = [];

            $scope._sombreado = -1;
            $scope._seleccionarTodasRemesas = false;

            if (response.codigo === 0) {

                $scope.paramsExport = {};

                if (response.list.length === 0) {
                    $scope.opcionesGrid.data = [];
                    Alert.abrir("No se encontraron registros con los par\u00e1metros ingresados.");
                } else
                {
                    $scope.pagIni = numPag;

                    $scope.totalUnidades = response.object.totalUnidades;
                    $scope._totalUnidades = response.object.totalUnidades;

                    $scope.totalRegistros = response.object.totalRegistros;
                    $scope._totalRegistros = response.object.totalRegistros;

                    $scope.pagFin = Math.ceil($scope.totalRegistros / $scope.PAGINADO_NOVEDADES_MASIVAS);

                    if (limpiarSel === false)
                    {
                        var i;
                        var remeIdInt;

                        for (i = 0; i < response.list.length; i++)
                        {
                            for (remeIdInt in $scope.remesasSel)
                            {
                                if (response.list[i].remeIdInt === remeIdInt)
                                {
                                    response.list[i].selected = true;
                                    $scope.remesasSel[remeIdInt] = response.list[i];
                                }
                            }
                        }
                    }

                    $scope.listaRemesas = response.list;
                    $scope._listaRemesas = [].concat(response.list);

                    // Asigno la data a la grid
                    $scope.opcionesGrid.data = $scope.listaRemesas;

                    $scope.paramsExport = params;
                }

                $timeout($scope.ajustarColumnas, 100);
            } else {
                Alert.abrir(response.mensaje);
            }

            unidadNegSel = null;
            tipoTransporteSel = null;
            estadoRemesaSel = null;
            ceOpOrigenSel = null;
            ciudOrigenSel = null;
            ciudDestinoSel = null;
            tipoSevicioSel = null;
            tipoDocSel = null;
            remitenteSel = null;
            destinatarioSel = null;
            tipoUnidadSel = null;
            sedeRemitenteSel = null;
            sedeDestinatarioSel = null;
            estadoUnidadSel = null;
            procesoSel = null;
            tipoCoberturaSel = null;
            estadoNovedadSel = null;
            ubicacionesSel = null;
            licenciaSel = null;

            $scope.seleccionadosGrid = 0;
        };

        var error = function (response)
        {
            Util.ocultarCargando();
            Alert.abrir("Error enviando los datos.");
        };

        Util.mostrarCargando();

        if ($scope.paramsConsulta.numRemesa.length > 0)
        {
            numRemesa = $scope.paramsConsulta.numRemesa[0].text;

            if ($scope.formatoTCC === true) {
                numRemesa = numRemesa.substr(8, numRemesa.length);
            }

            for (var i = 1; i < $scope.paramsConsulta.numRemesa.length; i++)
            {
                if ($scope.formatoTCC === true) {
                    numRemesa += "," + $scope.paramsConsulta.numRemesa[i].text.substr(8, $scope.paramsConsulta.numRemesa[i].text.length);
                } else {
                    numRemesa += "," + $scope.paramsConsulta.numRemesa[i].text;
                }
            }
        }

        var idUnidadesNegocio = "0";

        if (unidadNegSel.length > 0)
        {
            idUnidadesNegocio = unidadNegSel[0].idInt;

            for (var i = 1; i < unidadNegSel.length; i++) {
                idUnidadesNegocio += "," + unidadNegSel[i].idInt;
            }
        }

        var idTiposCobertura = "0";

        if (tipoCoberturaSel.length > 0)
        {
            idTiposCobertura = tipoCoberturaSel[0].idInt;

            for (var i = 1; i < tipoCoberturaSel.length; i++) {
                idTiposCobertura += "," + tipoCoberturaSel[i].idInt;
            }
        }

        ///
        var idEstadoRemesa = "0";

        if (estadoRemesaSel.length > 0)
        {
            idEstadoRemesa = estadoRemesaSel[0].idInt;

            for (var i = 1; i < estadoRemesaSel.length; i++) {
                idEstadoRemesa += "," + estadoRemesaSel[i].idInt;
            }
        }

        var idCeOpOrigen = "0";

        if (ceOpOrigenSel.length > 0)
        {
            idCeOpOrigen = ceOpOrigenSel[0].locaIdInt;

            for (var i = 1; i < ceOpOrigenSel.length; i++) {
                idCeOpOrigen += "," + ceOpOrigenSel[i].locaIdInt;
            }
        }

        var idCeOpDestino = "0";

        if ($scope.ceOpDestino.selected.length > 0)
        {
            idCeOpDestino = $scope.ceOpDestino.selected[0].idInt;

            for (var i = 1; i < $scope.ceOpDestino.selected.length; i++) {
                idCeOpDestino += "," + $scope.ceOpDestino.selected[i].idInt;
            }
        }

        var idEstadosNovedad = "0";

        if (estadoNovedadSel.length > 0)
        {
            idEstadosNovedad = estadoNovedadSel[0].liva_id_int;

            for (var i = 1; i < estadoNovedadSel.length; i++) {
                idEstadosNovedad += "," + estadoNovedadSel[i].liva_id_int;
            }
        }

        var idUbicaciones = "0";

        if (ubicacionesSel.length > 0)
        {
            idUbicaciones = ubicacionesSel[0].idInt;

            for (var i = 1; i < ubicacionesSel.length; i++) {
                idUbicaciones += "," + ubicacionesSel[i].idInt;
            }
        }

        var codRecogida = $.trim($scope.paramsConsulta.codRecogida || "");
        var codUnidadContenedora = $.trim($scope.paramsConsulta.codUnidadContenedora || "");

        params.idUnidadNegocio = 0;
        params.idUnidadesNegocio = idUnidadesNegocio;
        params.idTipoTransporte = tipoTransporteSel.idInt || "0";
        params.idEstadoRemesa = idEstadoRemesa; // estadoRemesaSel.idInt || "0";
        params.idCeOpOrigen = idCeOpOrigen;
        params.idCeOpDestino = idCeOpDestino;
        params.idCiudadOrigen = ciudOrigenSel.idInt || "0";
        params.idCiudadDestino = ciudDestinoSel.idInt || "0";
        params.idTipoServicioOperativo = tipoSevicioSel.liva_id_int || 0;
        params.idTipoDocReferencia = idTipoDocReferencia;
        params.idRemitente = remitenteSel.cotiNumeroIdentificacion || "0";
        params.idDestinatario = destinatarioSel.cotiNumeroIdentificacion || "0";
        params.idTipoUnidad = tipoUnidadSel.idInt || "0";
        params.idCuentaRemitente = parseInt(cuentaRemitente); // cuentaRemitenteSel.idInt || "0";
        params.idSedeRemitente = sedeRemitenteSel.idInt || "0";
        params.idCuentaDestintario = 0; // cuentaDestinatarioSel.idInt || "0";
        params.idSedeDestinatario = sedeDestinatarioSel.idInt || "0";
        params.idEstadoUnidad = estadoUnidadSel.idInt || "0";
        params.numRemesa = numRemesa;
        params.codRecogida = codRecogida;
        params.codUnidadContenedora = codUnidadContenedora;
        params.codigoUnidad = $.trim(($scope.paramsConsulta.codigoUnidad || ""));
        params.numDespachoCliente = parseInt(numDespachoCliente);
        params.numDocReferencia = numDocReferencia;
        params.fechaInicio = null;
        params.fechaFin = null;
        params.numPag = numPag;
        params.numRegs = $scope.PAGINADO_NOVEDADES_MASIVAS;
        params.tipoNovedad = "";
        params.procIdInt = procesoSel.idInt || "0";
        params.idTiposCobertura = idTiposCobertura;
        params.conNovedad = idFiltroNovedad;
        params.idEstadosNovedad = idEstadosNovedad;
        params.idUbicaciones = idUbicaciones;
        params.licencia = licenciaSel.idInt || "0";

        if ($scope.paramsConsulta.tipoNovedad === "nacional") {
            params.tipoNovedad = "N";
        } else if ($scope.paramsConsulta.tipoNovedad === "local") {
            params.tipoNovedad = "L";
        }


        if (isNaN(params.numDespachoCliente) === true) {
            params.numDespachoCliente = 0;
        }

        if (isNaN(params.idCuentaRemitente) === true) {
            params.idCuentaRemitente = 0;
        }

        // S� o se tiene en cuenta el rango de fechas, si no se ingreso
        // numero de remesa ni codigo de unidad.
        if (params.numRemesa.length === 0 && params.codigoUnidad.length === 0 && numDespachoCliente.length === 0)
        {
            params.fechaInicio = typeof $scope.paramsConsulta.fechaInicio === "string"
                    ? $scope.paramsConsulta.fechaInicio
                    : moment($scope.paramsConsulta.fechaInicio).format("DD/MM/YYYY");

            params.fechaFin = typeof $scope.paramsConsulta.fechaFin === "string"
                    ? $scope.paramsConsulta.fechaFin
                    : moment($scope.paramsConsulta.fechaFin).format("DD/MM/YYYY");
        }

        params.fechaInicioEntrega = null;
        params.fechaFinEntrega = null;
        params.fechaInicioViajeNacional = null;
        params.fechaFinViajeNacional = null;

        /*
         if ( $scope.paramsConsulta.tipoNovedad === "nacional" ) {
         
         var ceopDestSel = $scope.ceOpDestinoNacional.selected || {};
         var ceopOrigNacionalSel = $scope.ceOpOrigenNacional.selected || {};
         
         params.idCeOpDestNacional = ceopDestSel.idInt || 0;
         params.idCeOpOrigNacional = ceopOrigNacionalSel.locaIdInt || 0;
         params.codVanNacional = $.trim( $scope.paramsConsulta.codVan || "" );
         params.numViajeNacional = $.trim( $scope.paramsConsulta.numViaje || "" );
         
         params.fechaInicioViajeNacional = typeof $scope.paramsConsulta.fechaInicioViaje === "string" 
         ? $scope.paramsConsulta.fechaInicioViaje
         : moment( $scope.paramsConsulta.fechaInicioViaje ).format( "DD/MM/YYYY" );
         
         params.fechaFinViajeNacional = typeof $scope.paramsConsulta.fechaFinViaje === "string" 
         ? $scope.paramsConsulta.fechaFinViaje
         : moment( $scope.paramsConsulta.fechaFinViaje ).format( "DD/MM/YYYY" );
         
         params.fechaInicioEntrega = null;
         params.fechaFinEntrega = null;
         
         } else if ( $scope.paramsConsulta.tipoNovedad === "local" ) {
         
         params.vehiculo = $.trim( $scope.paramsConsulta.vehiculo || "" );
         params.numViajeLocal = $.trim( $scope.paramsConsulta.numViajeLocal || "" );
         params.fechaInicioEntrega = typeof $scope.paramsConsulta.fechaInicioEntrega === "string" 
         ? $scope.paramsConsulta.fechaInicioEntrega
         : moment( $scope.paramsConsulta.fechaInicioEntrega ).format( "DD/MM/YYYY" );
         
         params.fechaFinEntrega = typeof $scope.paramsConsulta.fechaFinEntrega === "string" 
         ? $scope.paramsConsulta.fechaFinEntrega
         : moment( $scope.paramsConsulta.fechaFinEntrega ).format( "DD/MM/YYYY" );
         
         params.fechaInicioViajeNacional = null;
         params.fechaFinViajeNacional = null;
         }*/

        var ceopDestSel = $scope.ceOpDestinoNacional.selected || {};
        var ceopOrigNacionalSel = $scope.ceOpOrigenNacional.selected || {};

        params.idCeOpDestNacional = ceopDestSel.idInt || 0;
        params.idCeOpOrigNacional = ceopOrigNacionalSel.locaIdInt || 0;
        params.codVanNacional = $.trim($scope.paramsConsulta.codVan || "");
        params.numViajeNacional = $.trim($scope.paramsConsulta.numViaje || "");

        params.fechaInicioViajeNacional = typeof $scope.paramsConsulta.fechaInicioViaje === "string"
                ? $scope.paramsConsulta.fechaInicioViaje
                : moment($scope.paramsConsulta.fechaInicioViaje).format("DD/MM/YYYY");

        params.fechaFinViajeNacional = typeof $scope.paramsConsulta.fechaFinViaje === "string"
                ? $scope.paramsConsulta.fechaFinViaje
                : moment($scope.paramsConsulta.fechaFinViaje).format("DD/MM/YYYY");

        //params.fechaInicioEntrega = null;
        //params.fechaFinEntrega = null;


        params.vehiculo = $.trim($scope.paramsConsulta.vehiculo || "");
        params.numViajeLocal = $.trim($scope.paramsConsulta.numViajeLocal || "");
        params.fechaInicioEntrega = typeof $scope.paramsConsulta.fechaInicioEntrega === "string"
                ? $scope.paramsConsulta.fechaInicioEntrega
                : moment($scope.paramsConsulta.fechaInicioEntrega).format("DD/MM/YYYY");

        params.fechaFinEntrega = typeof $scope.paramsConsulta.fechaFinEntrega === "string"
                ? $scope.paramsConsulta.fechaFinEntrega
                : moment($scope.paramsConsulta.fechaFinEntrega).format("DD/MM/YYYY");

        //params.fechaInicioViajeNacional = null;
        //params.fechaFinViajeNacional = null;

        NovedadesMasivasApi.consultar({action: "consultar"}, params).$promise.then(success, error);

    };

    $scope._validarObligatorios = function ()
    {
        var callback;
        var paramsFechas;

        var unidadNegSel = $scope.unidadNegocio.selected || [];

        if (unidadNegSel.length === 0)
        {
            callback = function ()
            {
                $timeout(function () {
                    $("#cbxUnne input[type='text']").focus();
                }, 100);
            };

            Alert.abrir("La unidad de negocio es obligatoria.", callback);
            return false;
        }

        if ($scope.ceOpDestino.selected.length === 0)
        {
            callback = function ()
            {
                $timeout(function () {
                    $("#cbxCeOpDest input[type='text']").focus();
                }, 100);
            };

            Alert.abrir("El centro de operaci\u00f3n destino es obligatorio.", callback);
            return false;
        }

        if ($scope.paramsConsulta.tipoNovedad === "nacional")
        {
            /*
             var ceopDestSel = $scope.ceOpDestinoNacional.selected || {};
             var locaIdInt = ceopDestSel.idInt || 0;
             
             if ( locaIdInt === 0 )
             {
             callback = function()
             {
             $timeout( function() {
             $( "#cbxCeopDestNacional input[type='text']" ).focus();
             }, 100 );
             };
             
             Alert.abrir( "El centro de operaci\u00f3n destino es obligatorio.", callback );
             return false;
             }
             */

            paramsFechas = {
                fechaIni: $scope.paramsConsulta.fechaInicioViaje,
                fechaFin: $scope.paramsConsulta.fechaFinViaje,
                msjFechaMayor: "La fecha inicio de viaje no puede ser mayor que la fecha fin.",
                msjMayorMes: "El rango de fechas de la fecha inicio de viaje y la fecha fin, no puede superar el mes."
            };

            if ($scope._validarRangoFechas(paramsFechas) === false) {
                return false;
            }
        } else if ($scope.paramsConsulta.tipoNovedad === "local")
        {
            paramsFechas = {
                fechaIni: $scope.paramsConsulta.fechaInicioEntrega,
                fechaFin: $scope.paramsConsulta.fechaFinEntrega,
                msjFechaMayor: "La fecha inicio de entrega no puede ser mayor que la fecha fin.",
                msjMayorMes: "El rango de fechas de la fecha inicio de entrega y la fecha fin, no puede superar el mes."
            };

            if ($scope._validarRangoFechas(paramsFechas) === false) {
                return false;
            }

            /*
             var vehiculo = $.trim( $scope.paramsConsulta.vehiculo || "" );
             
             if ( vehiculo.length === 0 )
             {
             callback = function()
             {
             $timeout( function() {
             $( "input#txtVehLocal" ).focus();
             }, 100 );
             };
             
             Alert.abrir( "El m\u00f3vil local es obligatorio.", callback );
             return false;
             }
             */
        }

        paramsFechas = {
            fechaIni: $scope.paramsConsulta.fechaInicio,
            fechaFin: $scope.paramsConsulta.fechaFin,
            msjFechaMayor: "La fecha inicial no puede ser mayor que la fecha final.",
            msjMayorMes: "El rango de fechas no puede superar los 4 mes."
        };

        return $scope._validarRangoFechas(paramsFechas);
    };

    $scope._validarRangoFechas = function (params)
    {
        var momentIni = moment(params.fechaIni);
        var momentFin = moment(params.fechaFin);

        if (momentFin.diff(momentIni) < 0)
        {
            Alert.abrir(params.msjFechaMayor);
            return false;
        } else if (momentFin.diff(momentIni, "months", true) > 4)
        {
            Alert.abrir(params.msjMayorMes);
            return false;
        }

        return true;
    };

    $scope._obtenerCiudades = function (busqueda, callback)
    {
        if ($scope._cargarCiudades === false) {
            return;
        }

        if ($scope._preventDefaultCiudad === true)
        {
            $scope._preventDefaultCiudad = false;
            return;
        }

        var parametros = {
            tipoLista: "CIUDADES_DOS",
            expansionUno: "0"
        };

        if (typeof busqueda === "string" && busqueda.length > 0)
        {
            parametros.mostrarCargando = false;
            parametros.expansionUno = busqueda.toUpperCase();
        }

        Listas.obtenerLista($http, parametros, Alert, callback);
    };

    $scope.obtenerCiudadesOrigen = function (busqueda)
    {
        var callback = function (listaCiudades)
        {
            $scope.listaCiudadesOrigen = listaCiudades;
        };

        $scope._obtenerCiudades(busqueda, callback);
    };

    $scope.obtenerCiudadesDestino = function (busqueda)
    {
        var callback = function (listaCiudades)
        {
            $scope.listaCiudadesDestino = listaCiudades;
        };

        $scope._obtenerCiudades(busqueda, callback);
    };

    $scope._preventConsultaCiudades = function ()
    {
        $scope._preventDefaultCiudad = true;
    };

    $scope._preventConsultaClientes = function (tipoCliente)
    {
        // R: Remitente.
        // D: Destinatario.

        $scope._preventDefaultClientes = true;

        if (typeof tipoCliente !== "string") {
            return;
        }

        // var unidadNegSel = $scope.unidadNegocio.selected || {};
        var unneIdInt = 0; // unidadNegSel.idInt || 0;

        /*
         if ( unneIdInt === 0 ) {
         return;
         }
         */

        if (tipoCliente === "R") {
            $scope._consultarCuentasRemitente(unneIdInt);
        } else if (tipoCliente === "D") {
            $scope._consultarCuentasDestinatario(unneIdInt);
        }
    };

    $scope._consultarCuentasRemitente = function (unneIdInt)
    {
        $scope.cuentaRemitente = {};
        $scope.sedeRemitente = {};

        $scope.listaCuentasRemitente = [];
        $scope.listaSedesRemitente = [];

        var remitenteSel = $scope.remitente.selected || {};
        var tercIdInt = remitenteSel.tercIdInt || "0";

        var callbackCuentas = function (listaCuentasRemitente)
        {
            var callbackSedes = function (listaSedesRemitente)
            {
                $scope.listaSedesRemitente = listaSedesRemitente;
            };

            $scope.listaCuentasRemitente = listaCuentasRemitente;
            $scope._consultarSedes(tercIdInt, callbackSedes);
        };

        $scope._consultarCuentas(tercIdInt, unneIdInt, callbackCuentas);
    };

    $scope._consultarCuentasDestinatario = function (unneIdInt)
    {
        $scope.cuentaDestinatario = {};
        $scope.sedeDestinatario = {};

        $scope.listaCuentasDestinatario = [];
        $scope.listaSedesDestinatario = [];

        var destinatarioSel = $scope.destinatario.selected || {};
        var tercIdInt = destinatarioSel.tercIdInt || "0";

        var callbackCuentas = function (listaCuentasDestinatario)
        {
            var callbackSedes = function (listaSedesDestinatario)
            {
                $scope.listaSedesDestinatario = listaSedesDestinatario;
            };

            $scope.listaCuentasDestinatario = listaCuentasDestinatario;
            $scope._consultarSedes(tercIdInt, callbackSedes);
        };

        $scope._consultarCuentas(tercIdInt, unneIdInt, callbackCuentas);
    };

    $scope._consultarCuentas = function (tercIdInt, unneIdInt, callback)
    {
        if (unneIdInt === 0 || tercIdInt === "0") {
            return;
        }

        if (typeof callback === "function") {
            callback([]);
        }

        /*
         var paramsTipoDocumento = {
         tipoLista: "CUENTAS_CLIENTE",
         tercIdInt: tercIdInt,
         unneIdInt: unneIdInt
         };
         
         Listas.consultar( $http, paramsTipoDocumento, Alert, callback );
         */
    };

    $scope._consultarSedes = function (tercIdInt, callback)
    {
        if (tercIdInt === "0") {
            return;
        }

        var paramsTipoDocumento = {
            tipoLista: "SEDES_CLIENTE",
            tercIdInt: tercIdInt
        };

        Listas.consultar($http, paramsTipoDocumento, Alert, callback);
    };

    $scope.openFechaInicio = function ()
    {
        $scope.paramsConsulta.isOpenFechaInicio = !$scope.paramsConsulta.isOpenFechaInicio;
    };

    $scope.openFechaFin = function ()
    {
        $scope.paramsConsulta.isOpenFechaFin = !$scope.paramsConsulta.isOpenFechaFin;
    };

    $scope.preventFechaInicioFocus = function ()
    {
        $scope.paramsConsulta.isOpenFechaInicio = false;
    };

    $scope.preventFechaFinFocus = function ()
    {
        $scope.paramsConsulta.isOpenFechaFin = false;
    };

    $scope.openFechaInicioViaje = function ()
    {
        $scope.paramsConsulta.isOpenFechaInicioViaje = !$scope.paramsConsulta.isOpenFechaInicioViaje;
    };

    $scope.openFechaFinViaje = function ()
    {
        $scope.paramsConsulta.isOpenFechaFinViaje = !$scope.paramsConsulta.isOpenFechaFinViaje;
    };

    $scope.preventFechaInicioViajeFocus = function ()
    {
        $scope.paramsConsulta.isOpenFechaInicioViaje = false;
    };

    $scope.preventFechaFinViajeFocus = function ()
    {
        $scope.paramsConsulta.isOpenFechaFinViaje = false;
    };

    $scope.openFechaInicioEntrega = function ()
    {
        $scope.paramsConsulta.isOpenFechaInicioEntrega = !$scope.paramsConsulta.isOpenFechaInicioEntrega;
    };

    $scope.openFechaFinEntrega = function ()
    {
        $scope.paramsConsulta.isOpenFechaFinEntrega = !$scope.paramsConsulta.isOpenFechaFinEntrega;
    };

    $scope.preventFechaInicioEntregaFocus = function ()
    {
        $scope.paramsConsulta.isOpenFechaInicioEntrega = false;
    };

    $scope.preventFechaFinEntregaFocus = function ()
    {
        $scope.paramsConsulta.isOpenFechaFinEntrega = false;
    };

    $scope.abrirCosultaRemitentes = function ()
    {
        var modalInst = $scope._abrirConsultaClientes();

        modalInst.result.then(function (cliente) {

            $scope.paramsConsulta.numDocRemitente = cliente.cotiNumeroIdentificacion;
            $scope.paramsConsulta.nombreRemitente = cliente.nombreCliente;

        });
    };

    $scope.abrirCosultaDestinatarios = function ()
    {
        var modalInst = $scope._abrirConsultaClientes();

        modalInst.result.then(function (cliente) {

            $scope.paramsConsulta.numDocDestinatario = cliente.cotiNumeroIdentificacion;
            $scope.paramsConsulta.nombreDestinatario = cliente.nombreCliente;

        });
    };

    $scope._abrirConsultaClientes = function ()
    {
        return $uibModal.open({
            animation: true,
            templateUrl: "app/novedadesmasivas/partial-modal-consulta-clientes.html",
            controller: "modalConsultaClientesController",
            // size: "lg",
            // windowClass: "app-modal-window",
            backdrop: false,
            keyboard: false,
            resolve: {
                params: function ()
                {
                    return {
                    };
                }
            }
        });
    };

    $scope.consultarRemitente = function (busqueda)
    {
        var callback = function (listaRemitentes)
        {
            $scope.listaRemitentes = listaRemitentes;
        };

        $scope._consultarCliente(busqueda, false, callback);
    };

    $scope.consultarDestinatario = function (busqueda)
    {
        var callback = function (listaDestinatarios)
        {
            $scope.listaDestinatarios = listaDestinatarios;
        };

        $scope._consultarCliente(busqueda, false, callback);
    };

    $scope._consultarCliente = function (busqueda, mostrarCargando, callback)
    {
        if ($scope._cargarClientes === false) {
            return;
        }

        if ($scope._preventDefaultClientes === true)
        {
            $scope._preventDefaultClientes = false;
            return;
        }

        if (busqueda.length < 3) {
            return;
        }

        if (typeof mostrarCargando !== "boolean") {
            mostrarCargando = false;
        }

        var success = function (response)
        {
            if (mostrarCargando === true) {
                Util.ocultarCargando();
            }

            if (response.codigo === 0)
            {
                if (typeof callback === "function") {
                    callback(response.list);
                }
            } else {
                Alert.abrir(response.mensaje);
            }
        };

        var error = function (response)
        {
            if (mostrarCargando === true) {
                Util.ocultarCargando();
            }

            Alert.abrir("Error enviando los datos.");
        };

        var value = {
            clienteCriterios: {
                terc_id_int: null,
                ciud_id_int: "",
                dsusuario: "%%",
                terc_id: busqueda,
                num_columna: 2,
                num_orden: 1,
                tedi_direccion_estandar: "",
                empr_razon_social: "",
                temc_valor: "",
                cote_id_int: 0,
                client_marca_comercial: "",
                tipoconsulta: 0,
                llamada_id: ""
            },
            begin: 1,
            until: 10,
            order: 1,
            column_order: 1
        };

        if (isNaN(parseInt(busqueda)) === true)
        {
            value.clienteCriterios.client_marca_comercial = busqueda;
            value.clienteCriterios.terc_id = null;
        }

        if (mostrarCargando === true) {
            Util.mostrarCargando();
        }

        ClientesApi.save({action: "consultarXCriterios"}, value)
                .$promise.then(success, error);
    };

    $scope.leerNumDespacho = function ($event)
    {
        // ENTER
        if (Util.getKeyCode($event) === 13)
        {
            var numDespacho = $.trim(($scope.paramsConsulta.numDespachoCliente || ""));

            if (numDespacho.length > 0) {
                $scope.consultar();
            }
        }
    };

    $scope.seleccionarRemesa = function (remesa) {

        if (typeof remesa.selected !== "boolean") {
            remesa.selected = false;
        }

        remesa.selected = !remesa.selected;

        if (remesa.selected === true) {
            $scope.remesasSel[remesa.remeIdInt] = remesa;
        } else {
            delete($scope.remesasSel[remesa.remeIdInt]);
        }

    };

    $scope.setSeleccionarTodasRemesas = function (seleccionado) {
        /*
         $scope._seleccionarTodasRemesas = !$scope._seleccionarTodasRemesas;
         
         for ( var i in $scope._listaRemesas )
         {
         $scope._listaRemesas[i].selected = !$scope._seleccionarTodasRemesas;
         $scope.seleccionarRemesa( $scope._listaRemesas[i] );
         }
         */

        var conteo = 0;

        var filasVisibles = $scope.gridApi.core.getVisibleRows();

        filasVisibles.forEach(function (fila, idx) {

            fila.entity.selected = seleccionado;
            conteo += 1;

        });


    };

    $scope.selFormatoTCC = function ()
    {
        $scope.formatoTCC = !$scope.formatoTCC;
    };

    $scope.validarNumRemesa = function ($tag)
    {
        if ($scope.formatoTCC === true)
        {
            var callback = function ()
            {
                $scope.paramsConsulta.numRemesa.pop();
            };

            $scope._validarFormatoTcc($tag.text, callback);
        }
    };

    $scope._validarFormatoTcc = function (numRemesa, callback)
    {
        // Fotmato TCC = ddmmyyyy[numero de remesa]

        // Si no cuenta con al menos 9 dogitos, no es valido.
        if (numRemesa.length <= 8)
        {
            Alert.abrir("La remesa " + numRemesa + " no cuenta con el formato TCC.", callback);
            return false;
        }

        return true;
    };

    $scope.verUnidades = function (remesa)
    {
        $uibModal.open({
            animation: true,
            size: "lg",
            windowClass: "modal-tcc",
            templateUrl: "app/novedadesmasivas/partial-modal-unidades-remesa.html",
            controller: "modalUnidadesRemesaController",
            backdrop: false,
            keyboard: false,
            resolve: {
                params: function ()
                {
                    return remesa;
                }
            }
        });
    };

    $scope.verDocumentosCliente = function (remesa)
    {
        $uibModal.open({
            animation: true,
            size: "lg",
            windowClass: "modal-tcc",
            templateUrl: "app/novedadesmasivas/partial-modal-documentos-cliente-remesa.html",
            controller: "modalDocumentosClienteRemesaController",
            backdrop: false,
            keyboard: false,
            resolve: {
                params: function ()
                {
                    return remesa;
                }
            }
        });
    };

    $scope.abrirNovedades = function (remesa)
    {
        var url = Constants.URL_NOVEDADES_REME +
                "&numeroReme=" + remesa.numRemesa +
                "&fechaReme=" + remesa.fechaRemesaDos;

        $window.open(url, "_blank");
    };

    $scope.verFormatosImpresion = function () {

        var remesasSel = $scope.opcionesGrid.data.filter(function (servicio) {
            return servicio.selected === true;
        });

        if (Util.getObjectSize(remesasSel) === 0) {

            Alert.abrir("Debe seleccionar una o m\u00e1s remesas.");
            return;
        }

        $uibModal.open({
            animation: true,
            size: "lg",
            windowClass: "modal-tcc",
            templateUrl: "app/novedadesmasivas/partial-modal-formatos-impresion.html",
            controller: "modalFormatosImpresionController",
            backdrop: false,
            keyboard: false,
            resolve: {
                params: function ()
                {
                    return {
                        url: $scope.URL_ROTULOS,
                        remesa: remesasSel,
                        usuario: $scope.usuario.user,
                        locaIdInt: $scope.usuario.guicentroopera,
                        idEtiquetaDefecto: $scope.ETIQUETA_DEFECTO
                    };
                }
            }
        });
    };

    $scope.verCertificado = function (remesa)
    {
        $window.open(
                $scope.URL_SERVICIO_CERT + remesa.numRemesa + "/" + remesa.fechaRemesaDos + "/0",
                "",
                "width=1100,height=" + $(document).height() + ",menubar=0,scrollbars=1"
                );
    };

    $scope.verCumplidoRemesa = function (remesa)
    {
        var success = function (response)
        {
            Util.ocultarCargando();

            if (response.codigo === 0)
            {
                if (response.object)
                {
                    var images = [
                        {
                            url: "data:image/jpeg;base64," + response.object,
                            caption: "Remesa " + remesa.numRemesa
                                    // 'thumbUrl': 'thumb1.jpg' // used only for this example
                        }
                    ];

                    Lightbox.openModal(images, 0);
                }
            } else {
                Alert.abrir(response.mensaje);
            }
        };

        var error = function (response)
        {
            Util.ocultarCargando();
            Alert.abrir("Error enviando los datos.");
        };

        Util.mostrarCargando();

        NovedadesMasivasApi.listar({action: "obtenerCumplidoRemesa", imagen: remesa.cumplido})
                .$promise.then(success, error);
    };

    $scope.abrirSolucion = function () {

        var remesasSel = $scope.opcionesGrid.data.filter(function (servicio) {
            return servicio.selected === true;
        });

        if (Util.getObjectSize(remesasSel) === 0)
        {
            Alert.abrir("Para ingresar soluciones masivas, debe seleccionar uno o m\u00e1s registros.");
            return;
        }

        for (var remeIdInt in remesasSel)
        {
            if (parseInt(remesasSel[remeIdInt].canoIdInt) === 0)
            {
                Alert.abrir("No es posible ingresar soluciones masivas, porque una o m\u00e1s remesas no tienen novedad.");
                return;
            }

            if ("" + remesasSel[remeIdInt].idEstadoNovedad === $scope.LIVA_EST_NOV_SOLUCIONADA)
            {
                Alert.abrir("No es posible ingresar soluciones masivas, porque una o m\u00e1s novedades se encuentran en estado SOLUCIONADA.");
                return;
            }

            if ("" + remesasSel[remeIdInt].idEstadoNovedad === $scope.LIVA_EST_NOV_EJECUTADA)
            {
                Alert.abrir("No es posible ingresar soluciones masivas, porque una o m\u00e1s novedades se encuentran en estado EJECUTADA.");
                return;
            }
        }

        var modalInstance = $uibModal.open({
            animation: true,
            size: "lg",
            windowClass: "modal-tcc",
            templateUrl: "app/novedadesmasivas/partial-modal-solucion-masiva.html",
            controller: "modalSolucionMasivaController",
            backdrop: true,
            keyboard: true,
            resolve: {
                params: function ()
                {
                    return {
                        remesa: remesasSel,
                        usuario: $scope.usuario,
                        minCaracteresObs: $scope.MIN_CARACTERES_OBS_NOVEDAD
                    };
                }
            }
        });

        var success = function ()
        {
            $scope.consultar($scope.pagIni);
        };

        var cancel = function () {};

        modalInstance.result.then(success, cancel);
    };

    $scope.validacionEstadoGlobalRemesa = function(remesasSel) {

        Util.mostrarCargando();

        const numerosRemesas = [];
        const remesas = remesasSel.map((value) => {
            numerosRemesas.push(value.numRemesa);
        });
        
        const params = {
            numRemesa: [...new Set(numerosRemesas)].join(),
        }
        
        Util.ocultarCargando();
        return NovedadesMasivasApi.consultar({action: "validarestadoglobalremesa"}, params).$promise;
    }

    $scope.abrirIngresoNovedad = async function () {

        var remesasSel = $scope.opcionesGrid.data.filter(function (servicio) {
            return servicio.selected === true;
        });

        if (Util.getObjectSize(remesasSel) === 0) {
            Alert.abrir("Debe seleccionar una o m\u00e1s remesas.");
            return;
        }

        var remeIdInt;
        var remeIdIntAnt = null;

        var paqueteriaCount = 0;
        var mensajeriaCount = 0;

        /*/ si cuenta con reexpedici�n o no
         var isReex = 0;
         var nReex = 0;*/
        
        const validacion = await $scope.validacionEstadoGlobalRemesa(remesasSel);

        if (validacion.list.length >= 1) {
            Alert.abrir("No es posible ingresar novedades masivas, porque una o m\u00e1s remesas se encuentran en estado diferente a EJECUTADA o el campo este vacio.");
            return false;
        }

        for (remeIdInt in remesasSel) {

            /*if(remesasSel[remeIdInt].reex == "R") {
             isReex = isReex + 1;
             }
             if(remesasSel[remeIdInt].reex == null) {
             nReex = nReex + 1;
             }*/

            if (remesasSel[remeIdInt].unidadNegocio === "PAQUETERIA") {
                paqueteriaCount++;
            } else if (remesasSel[remeIdInt].unidadNegocio === "MENSAJERIA") {
                mensajeriaCount++;
            }

            if (remeIdIntAnt === null) {
                remeIdIntAnt = remeIdInt;
            }

            if (remesasSel[remeIdInt].estadoNovedad !== "EJECUTADA" && remesasSel[remeIdInt].estadoNovedad !== null) {
                Alert.abrir("No es posible ingresar novedades masivas, porque una o m\u00e1s remesas se encuentran en estado diferente a EJECUTADA o el campo este vacio.");
                return;
            }

            if (remesasSel[remeIdInt].esonIdInt === 196) {
                Alert.abrir("No es posible el planteamiento de novedades, porque una o m\u00e1s remesas se encuentran en estado operativo de remesa "+remesasSel[remeIdInt].estadoRemesa+ ".");
                return;
            }

            if(remesasSel[remeIdInt].esonIdInt === 134 || remesasSel[remeIdInt].esonIdInt === 209 || remesasSel[remeIdInt].esonIdInt === 212 || remesasSel[remeIdInt].esonIdInt === 213 || remesasSel[remeIdInt].esonIdInt === 254 || remesasSel[remeIdInt].esonIdInt === 261 || remesasSel[remeIdInt].esonIdInt === 262 || remesasSel[remeIdInt].esonIdInt === 264 ) {
                Alert.abrir("La (s) remesa (s) seleccionada (s), se encuentra en un estado terminal y no se le puede grabar una novedad");
                return;
            }

            if (remesasSel[remeIdInt].countProcesosAsoc === '0') {
                Alert.abrir("La (s) remesa (s) seleccionada (s) tiene un estado operativo sin proceso para mostrar las novedades, por favor valide en intente nuevamente");
                return;
            }

            if ("" + remesasSel[remeIdInt].esonIdInt === $scope.LIVA_ESON_REM_ANULADA)
            {
                Alert.abrir("No es posible ingresar novedades masivas, porque una o m\u00e1s remesas se encuentran en estado ANULADA.");
                return;
            }

            if (remesasSel[remeIdInt].procIdInt === remesasSel[remeIdIntAnt].procIdInt) {
                // validaci�n necesario porque "EN DISTRIBUCION" y "ENTREGADA A CONEXIONES" comparten el mismo procIdInt
                if (remesasSel[remeIdInt].estadoRemesa === 'EN DISTRIBUCION' && remesasSel[remeIdIntAnt].estadoRemesa === 'ENTREGADA A CONEXIONES') {
                    Alert.abrir("Todas las remesas seleccionadas deben tener el mismo proceso.");
                    return;
                }

                remeIdIntAnt = remeIdInt;

            } else
            {
                Alert.abrir("Todas las remesas seleccionadas deben tener el mismo proceso.");
                return;
            }
        }

        var procIdInt = remesasSel[remeIdIntAnt].procIdInt;


        if (paqueteriaCount > 0 && mensajeriaCount > 0) {
            Alert.abrir("Debe seleccionar remesas del mismo tipo de unidad de negocio.");
            return false;
        }

        /*if (isReex > 0 && nReex > 0) {
         Alert.abrir("No se debe seleccionar remesas que cuenten con estados diferentes de REEXPEDICION .");
         return false;
         }*/


        var tipoUnidadDeGegocioSel;

        if (paqueteriaCount > 0) {
            tipoUnidadDeGegocioSel = 'PAQUETERIA';
        }

        if (mensajeriaCount > 0) {
            tipoUnidadDeGegocioSel = "MENSAJERIA";
        }
        var modalInstance = $uibModal.open({
            animation: true,
            size: "lg",
            windowClass: "modal-tcc",
            templateUrl: "app/novedadesmasivas/partial-modal-ingreso-novedad.html",
            controller: "modalIngresoNovedadController",
            backdrop: false,
            keyboard: false,
            resolve: {
                params: function () {
                    return {
                        remesas: remesasSel,
                        usuario: $scope.usuario,
                        procIdInt: procIdInt,
                        minCaracteresObs: $scope.MIN_CARACTERES_OBS_NOVEDAD,
                        tipoUnidadDeGegocioSel: tipoUnidadDeGegocioSel,
                        estados: $scope.EST_FECHA_NOVEDAD_INACTIVA,
                        diasHabiles: $scope.DIAS_HABIL_DEVOL_FEREAL,
                        numeroDiasHabiles: $scope.NUM_DIAS_HABIL_DEVOL_FEREAL,
                        estadoEntregadoConexiones: $scope.ESON_REME_ENT_CNX,
                        horaMaxima: $scope.CIERRE_FECHA_NOVEDAD,
                        isAdmin : $scope.IS_ADMINISTRADOR,
                        procesoDistribucionMercancia: $scope.PROC_DSTR_MCIA
                    };
                }
            }
        });

        var success = function ()
        {
            $scope.consultar();
        };

        var cancel = function () {};

        modalInstance.result.then(success, cancel);
    };

    $scope.ejecutarNovedad = function () {

        var remesasSel = $scope.opcionesGrid.data.filter(function (servicio) {
            return servicio.selected === true;
        });
        


        if (Util.getObjectSize(remesasSel) === 0) {
            Alert.abrir("Para ejecutar novedades masivas, debe seleccionar uno o m\u00e1s registros.");
            return;
        }

        for (var remeIdInt in remesasSel) {

            if (parseInt(remesasSel[remeIdInt].canoIdInt) === 0) {
                Alert.abrir("No es posible ejecutar novedades masivas, porque una o m\u00e1s remesas no tienen novedad.");
                return;
            }

            if ("" + remesasSel[remeIdInt].idEstadoNovedad !== $scope.LIVA_EST_NOV_SOLUCIONADA) {


                Alert.abrir("No es posible ejecutar novedades masivas, porque una o m\u00e1s novedades no se encuentran en estado SOLUCIONADA.");
                return;
            }

            if ("" + remesasSel[remeIdInt].idEstadoNovedad === $scope.LIVA_EST_NOV_EJECUTADA) {

                Alert.abrir("No es posible ejecutar novedades masivas, porque una o m\u00e1s novedades se encuentran en estado EJECUTADA.");
                return;
            }
            
            
            console.log("remesasSel[remeIdInt].numeroRemesaSolucion->"+ remesasSel[remeIdInt].numeroRemesaSolucion);//ACA VALIDAMOS QUE SEA NUMEROREMESASOLUCION 
            console.log("remesasSel[remeIdInt].tipoDeSolucion v3->"+ remesasSel[remeIdInt].tipoDeSolucion);//-La remesa tenga como tipo de soluci�n �Remesa Soluci�n�
            console.log("remesasSel[remeIdInt].idEstadoNovedad->"+ remesasSel[remeIdInt].idEstadoNovedad);
            console.log("$scope.LIVA_EST_NOV_SOLUCIONADA->"+ $scope.LIVA_EST_NOV_SOLUCIONADA);
            // HU 12632
            if( remesasSel[remeIdInt].tipoDeSolucion !== null){
              if ("" + remesasSel[remeIdInt].idEstadoNovedad === $scope.LIVA_EST_NOV_SOLUCIONADA  && parseInt(remesasSel[remeIdInt].tipoDeSolucion) === 5724 &&  remesasSel[remeIdInt].numeroRemesaSolucion === null) { 
                Alert.abrir("La(s) remesa(s) no tiene(n) remesa soluci\u00f3n asociada y no permite la ejecuci\u00f3n de la novedad.");
                return;
              }  
            }
            
        }

        /*
         var callback = function( opc )
         {
         // ACEPTAR
         if ( opc === 1 ) {
         $scope.abrirMotivoEjecucionAnulacion( "E" );
         }
         };
         
         Confirm.abrir( "\u00bfEst\u00e1 seguro de ejecutar las novedades seleccionadas?", callback );
         */

        $scope.abrirMotivoEjecucionAnulacion("E");
    };


    $scope.anularNovedad = function () {

        var remesasSel = $scope.opcionesGrid.data.filter(function (servicio) {
            return servicio.selected === true;
        });

        if (Util.getObjectSize(remesasSel) === 0) {

            Alert.abrir("Para anular novedades masivas, debe seleccionar uno o m\u00e1s registros.");
            return;

        }

        for (var remeIdInt in remesasSel) {

            if (parseInt(remesasSel[remeIdInt].canoIdInt) === 0)
            {
                Alert.abrir("No es posible anular novedades masivas, porque una o m\u00e1s remesas no tienen novedad.");
                return;
            }

            if ("" + remesasSel[remeIdInt].idEstadoNovedad === $scope.LIVA_EST_NOV_ANULADA)
            {
                Alert.abrir("No es posible anular novedades masivas, porque una o m\u00e1s novedades se encuentran en estado ANULADA.");
                return;
            }
        }

        var callback = function (opc)
        {
            // ACEPTAR
            if (opc === 1) {
                $scope.abrirMotivoEjecucionAnulacion("A");
            }
        };

        Confirm.abrir("\u00bfEst\u00e1 seguro de anular las novedades seleccionadas?", callback);
    };


    $scope.abrirMotivoEjecucionAnulacion = function (tipo) {

        var remesasSel = $scope.opcionesGrid.data.filter(function (servicio) {
            return servicio.selected === true;
        });

        // E: Ejecucion.
        // A: Anulcacion.

        var modalInstance = $uibModal.open({
            animation: true,
            size: "lg",
            // windowClass: "modal-tcc",
            templateUrl: "app/novedadesmasivas/partial-modal-observaciones.html",
            controller: "modalObservacionesController",
            backdrop: false,
            keyboard: false,
            resolve: {
                params: function ()
                {
                    var length = 0;
                    var infoTitulo = "Ejecuci\u00f3n de novedades. ";

                    if (tipo === "A") {
                        infoTitulo = "Anulaci\u00f3n de novedades. ";
                    }

                    for (var remeIdInt in remesasSel)
                    {
                        length++;
                        infoTitulo += "C\u00f3digo: " + remesasSel[remeIdInt].consecutivoNovedad + " - Remesa: " + remesasSel[remeIdInt].numRemesa;
                    }

                    return {
                        infoTitulo: (length === 1) ? infoTitulo : "",
                        tipo: tipo
                    };
                }
            }
        });

        var success = function (obj)
        {
            var livaCausalNov = obj.livaCausalNov;
            var observaciones = obj.observaciones;

            $scope._ejecutarAnularNovedad(tipo, livaCausalNov, observaciones);
        };

        var cancel = function () {};

        modalInstance.result.then(success, cancel);
    };


    $scope._ejecutarAnularNovedad = function (tipo, livaCausalNov, observaciones) {

        var remesasSel = $scope.opcionesGrid.data.filter(function (servicio) {
            return servicio.selected === true;
        });

        var objRemesas = remesasSel;

        var success = function (response)
        {
            Util.ocultarCargando();

            if (response.codigo === 0) {
                Alert.abrir("Datos guardados con \u00e9xito.", $scope.consultar);
            } else {
                Alert.abrir(response.mensaje);
            }
        };

        var error = function (response)
        {
            Util.ocultarCargando();
            Alert.abrir("Error enviando los datos.");
        };

        var arrNovedades = [];

        for (var remeIdInt in objRemesas)
        {
            arrNovedades.push({
                canoIdInt: objRemesas[remeIdInt].canoIdInt,
                livaCausalNov: livaCausalNov,
                observaciones: observaciones,
                idCeOpEjecuta: $scope.usuario.guicentroopera,
                usuario: $scope.usuario.user
            });
        }

        var action = (tipo === "E") ? "ejecutarNovedad" : "anularNovedad";

        Util.mostrarCargando();
        NovedadesMasivasApi.save({action: action}, arrNovedades)
                .$promise.then(success, error);
    };

    $scope.filtrarPorNovedad = function ()
    {
        $scope.totalUnidades = 0;
        $scope.listaRemesas = [];

        var filtroNovedadSel = $scope.filtroNovedad.selected || {};
        var idFiltroNovedad = filtroNovedadSel.id || 0;

        if (idFiltroNovedad === 0)
        {
            $scope.totalUnidades = $scope._totalUnidades;
            $scope.listaRemesas = [].concat($scope._listaRemesas);
        } else
        {
            for (var i = 0; i < $scope._listaRemesas.length; i++)
            {
                // 1: CON NOVEDAD
                // 2: SIN NOVEDAD
                if ((idFiltroNovedad === 1 && $scope._listaRemesas[i].tieneNovedad === true) ||
                        (idFiltroNovedad === 2 && $scope._listaRemesas[i].tieneNovedad === false))
                {
                    $scope.totalUnidades += $scope._listaRemesas[i].unidadesRemesa;
                    $scope.listaRemesas.push($scope._listaRemesas[i]);
                }
            }
        }
    };

    $scope.abrirDetalleNovedad = function ($index, remesa) {
        // camilo


        var urlComponenenteDetalleNovedad = $scope.URL_SERVIDOR_GOS + "/novedades-normales/#!/detallenovedad"
        var windowInst = $window.open(
                urlComponenenteDetalleNovedad,
                "",
                "width=" + $(document).width() + ",height=" + $(document).height() + ",menubar=0,scrollbars=1"
                );

        windowInst.remesa = {
            canoIdInt: remesa.canoIdInt,
            numRemesa: remesa.numRemesa,
            fechaRemesa: remesa.fechaRemesa
        };


        /*
         var idx = 0;
         var listaRemesas = [];
         
         var modalInstance = $uibModal.open({
         animation: true,
         size: "lg",
         windowClass: "modal-tcc",
         templateUrl: "app/novedadesmasivas/partial-modal-detalle-novedad.html",
         controller: "modalDetalleNovedadController",
         backdrop: true,
         keyboard: true,
         resolve: {
         params: function ()
         {
         return {
         listaRemesas: listaRemesas,
         pos: idx,
         remesa: remesa,
         parent: {
         sombrearFila: function (i) {}, // $scope.sombrearFila,
         verUnidades: $scope.verUnidades,
         verDocumentosCliente: $scope.verDocumentosCliente,
         verCertificado: $scope.verCertificado,
         botones: $scope.botones
         }
         };
         }
         }
         });
         
         var success = function () {};
         
         var cancel = function () {};
         
         modalInstance.result.then(success, cancel);
         */
    };

    $scope.verOpcionesExportacion = function () {


        /*var data = $scope.opcionesGrid.data.map((row) => {
         console.log(row.remeIdInt);
         
         if (row.remeIdInt !== undefined) {
         
         return data += "," + row.remeIdInt;
         
         }
         
         });*/

        var remeIdInts = $scope.opcionesGrid.data[0].remeIdInt;
        for (var i = 1; i < $scope.opcionesGrid.data.length; i++) {

            remeIdInts += "," + $scope.opcionesGrid.data[i].remeIdInt;
        }


        $uibModal.open({
            animation: true,
            size: "lg",
            // windowClass: "modal-tcc",
            templateUrl: "app/novedadesmasivas/partial-modal-opciones-exportacion.html",
            controller: "modalOpcionesExportacionController",
            backdrop: false,
            keyboard: false,
            resolve: {
                params: function ()
                {
                    return {
                        usuario: $scope.usuario.user,
                        paramsExport: $scope.paramsExport,
                        remeIdInts
                    };
                }
            }
        });
    };

    /**
     * Metodo para validar usuarios que no puedan plantear novedades de servicios especiales.
     * @param {type} entity
     * @returns {Boolean}
     */
    $scope.validarDeshabilitarOtrasFunciones = function () {

        console.log("Validacion!");
        var filasVisibles = $scope.gridApi.core.getVisibleRows();

        for (var i in filasVisibles) {

            var fila = filasVisibles[i];

            if (fila.entity.selected === true && !$scope.IS_GESTION_AGENDAMIENTO
                    && (fila.entity.codigoNovedadComplemento == $scope.ID_NOVEDAD_GEST_CITA
                            || fila.entity.codigoNovedadComplemento == $scope.ID_NOVEDAD_GEST_MALLA)) {

                $scope.INHABILITAR_BOTON_EJECUTAR_NOVEDAD = true;
                $scope.INHABILITAR_BOTON_SOLUCION_NOVEDAD = true;

                console.log("serv especial")
                return false;
            } else {
                $scope.INHABILITAR_BOTON_EJECUTAR_NOVEDAD = false;
                $scope.INHABILITAR_BOTON_SOLUCION_NOVEDAD = false;
            }
        }
        // $scope.opcionesGrid.data.filter(function (servicio) {


        //});
    }

    $scope.init();
}

function modalUnidadesRemesaController($scope, $uibModalInstance, $uibModal, Alert, NovedadesMasivasApi, params)
{
    $scope.init = function ()
    {
        $scope.numRemesa = params.numRemesa;

        $scope.listaUnidades = [];
        $scope._listaUnidades = [];

        $scope.consultarUnidades();
    };

    $scope.consultarUnidades = function ()
    {
        var success = function (response)
        {
            Util.ocultarCargando();

            if (response.codigo === 0)
            {
                $scope.listaUnidades = [];
                $scope._listaUnidades = [];

                if (response.list.length > 0)
                {
                    $scope.listaUnidades = response.list;
                    $scope._listaUnidades = [].concat(response.list);
                }
            } else {
                Alert.abrir(response.mensaje);
            }
        };

        var error = function (response)
        {
            Util.ocultarCargando();
            Alert.abrir("Error enviando los datos.");
        };

        Util.mostrarCargando();
        NovedadesMasivasApi.listar({action: "consultarUnidadesRemesa", remeIdInt: params.remeIdInt})
                .$promise.then(success, error);
    };

    $scope.verTrazaUnidad = function (unidad)
    {
        $uibModal.open({
            animation: true,
            size: "lg",
            windowClass: "modal-tcc",
            templateUrl: "app/novedadesmasivas/partial-modal-traza-unidades-remesa.html",
            controller: "modalTrazabilidadUnidadesRemesaController",
            backdrop: false,
            keyboard: false,
            resolve: {
                params: function ()
                {
                    return unidad;
                }
            }
        });
    };

    $scope.verNovedadesUnidad = function (unidad)
    {
        $uibModal.open({
            animation: true,
            size: "lg",
            windowClass: "modal-tcc",
            templateUrl: "app/novedadesmasivas/partial-modal-novedades-unidades-remesa.html",
            controller: "modalNovedadesUnidadesRemesaController",
            backdrop: false,
            keyboard: false,
            resolve: {
                params: function ()
                {
                    return unidad;
                }
            }
        });
    };

    $scope.cancel = function ()
    {
        $uibModalInstance.dismiss("cancel");
    };

    $scope.init();
}

function modalTrazabilidadUnidadesRemesaController($scope, $uibModalInstance, Alert, NovedadesMasivasApi, params)
{
    $scope.init = function ()
    {
        $scope.numUnidad = params.codigoUnidad;

        $scope.listaTrazaUnidades = [];
        $scope._listaTrazaUnidades = [];

        $scope.consultarTrazabilidad();
    };

    $scope.consultarTrazabilidad = function ()
    {
        var success = function (response)
        {
            Util.ocultarCargando();

            if (response.codigo === 0)
            {
                $scope.listaTrazaUnidades = [];
                $scope._listaTrazaUnidades = [];

                if (response.list.length > 0)
                {
                    $scope.listaTrazaUnidades = response.list;
                    $scope._listaTrazaUnidades = [].concat(response.list);
                }
            } else {
                Alert.abrir(response.mensaje);
            }
        };

        var error = function (response)
        {
            Util.ocultarCargando();
            Alert.abrir("Error enviando los datos.");
        };

        Util.mostrarCargando();
        NovedadesMasivasApi.listar({action: "consultarTrazabilidadUnidades", ipidIdInt: params.idIup})
                .$promise.then(success, error);
    };

    $scope.cancel = function ()
    {
        $uibModalInstance.dismiss("cancel");
    };

    $scope.init();
}

function modalNovedadesUnidadesRemesaController($scope, $uibModalInstance, Alert, NovedadesMasivasApi, Lightbox, params)
{
    $scope.init = function ()
    {
        $scope.numUnidad = params.codigoUnidad;

        $scope.listaNovedadesUnidad = [];
        $scope._listaNovedadesUnidad = [];

        $scope.consultarNovedadesUnidades();
    };

    $scope.consultarNovedadesUnidades = function ()
    {
        var success = function (response)
        {
            Util.ocultarCargando();

            if (response.codigo === 0)
            {
                $scope.listaTrazaUnidades = [];
                $scope._listaTrazaUnidades = [];

                if (response.list.length > 0)
                {
                    $scope.listaNovedadesUnidad = response.list;
                    $scope._listaNovedadesUnidad = [].concat(response.list);
                }
            } else {
                Alert.abrir(response.mensaje);
            }
        };

        var error = function (response)
        {
            Util.ocultarCargando();
            Alert.abrir("Error enviando los datos.");
        };

        var paramsEnv = {
            codigoUnidad: params.codigoUnidad
        };

        Util.mostrarCargando();
        NovedadesMasivasApi.consultar({action: "consultarNovedadesUnidades"}, paramsEnv)
                .$promise.then(success, error);
    };

    $scope.obtenerImagenes = function (novedadUnidad)
    {
        var success = function (response)
        {
            Util.ocultarCargando();

            if (response.codigo === 0)
            {
                if (response.list.length === 0)
                {
                    Alert.abrir("No se encontraron im\u00e1genes de novedad para la unidad " + novedadUnidad.codigoUnidad);
                    return;
                }

                Lightbox.openModal(response.list, 0);
            } else {
                Alert.abrir(response.mensaje);
            }
        };

        var error = function (response)
        {
            Util.ocultarCargando();
            Alert.abrir("Error enviando los datos.");
        };

        Util.mostrarCargando();

        NovedadesMasivasApi.consultar({action: "obtenerImagenesNovedadUnidad"}, novedadUnidad)
                .$promise.then(success, error);
    };

    $scope.cancel = function ()
    {
        $uibModalInstance.dismiss("cancel");
    };

    $scope.init();
}

function modalDocumentosClienteRemesaController($scope, $uibModalInstance, Alert, NovedadesMasivasApi, params)
{
    $scope.init = function ()
    {
        $scope.numRemesa = params.numRemesa;

        $scope.listaDocumentos = [];
        $scope._listaDocumentos = [];

        $scope.consultarUnidades();
    };

    $scope.consultarUnidades = function ()
    {
        var success = function (response)
        {
            Util.ocultarCargando();

            if (response.codigo === 0)
            {
                $scope.listaDocumentos = [];
                $scope._listaDocumentos = [];

                if (response.list.length > 0)
                {
                    $scope.listaDocumentos = response.list;
                    $scope._listaDocumentos = [].concat(response.list);
                }
            } else {
                Alert.abrir(response.mensaje);
            }
        };

        var error = function (response)
        {
            Util.ocultarCargando();
            Alert.abrir("Error enviando los datos.");
        };

        Util.mostrarCargando();
        NovedadesMasivasApi.listar({action: "consultarDocumentosClienteRemesa", remeIdInt: params.remeIdInt})
                .$promise.then(success, error);
    };

    $scope.cancel = function ()
    {
        $uibModalInstance.dismiss("cancel");
    };

    $scope.init();
}

function modalFormatosImpresionController($scope, $uibModalInstance, Alert, NovedadesMasivasApi, $window, params) {

    $scope.init = function ()
    {
        $scope.consultarFormatos();
    };

    $scope.consultarFormatos = function ()
    {
        var success = function (response)
        {
            Util.ocultarCargando();

            if (response.codigo === 0)
            {
                if (response.list.length === 0) {
                    Alert.abrir("No se encontraron datos con los par\u00e1metros ingresados.");
                } else {
                    $scope._mapearListaFormatos(response.list);
                }
            } else {
                Alert.abrir(response.mensaje);
            }
        };

        var error = function (response)
        {
            Util.ocultarCargando();
            Alert.abrir("Error enviando los datos.");
        };

        Util.mostrarCargando();
        NovedadesMasivasApi.listar({action: "consultarFormatosImpresion"})
                .$promise.then(success, error);
    };

    $scope._mapearListaFormatos = function (listaFormatos)
    {
        var objFormatos = {};

        var unneDescripcion;
        var tiunDescripcion;

        var objRotu;
        $scope.rotulosSel = {};

        for (var i in listaFormatos)
        {
            unneDescripcion = listaFormatos[i].unneDescripcion;
            tiunDescripcion = listaFormatos[i].tiunDescripcion;

            if (!objFormatos[unneDescripcion]) {
                objFormatos[unneDescripcion] = {};
            }

            if (!objFormatos[unneDescripcion][tiunDescripcion]) {
                objFormatos[unneDescripcion][tiunDescripcion] = [];
            }

            objRotu = {
                unneIdInt: listaFormatos[i].unneIdInt,
                tiunIdInt: listaFormatos[i].tiunIdInt,
                rotuIdInt: listaFormatos[i].rotuIdInt,
                rotuDescripcion: listaFormatos[i].rotuDescripcion
            };

            objFormatos[unneDescripcion][tiunDescripcion].push(objRotu);

            if (!$scope.rotulosSel[unneDescripcion]) {
                $scope.rotulosSel[unneDescripcion] = {};
            }

            if (!$scope.rotulosSel[unneDescripcion][tiunDescripcion]) {
                $scope.rotulosSel[unneDescripcion][tiunDescripcion] = {};
            }

            if ("" + listaFormatos[i].rotuIdInt === "" + params.idEtiquetaDefecto) {
                $scope.rotulosSel[unneDescripcion][tiunDescripcion].selected = objRotu;
            }
        }

        $scope.listaFormatos = objFormatos;
    };

    $scope.generarRotulos = function ()
    {
        var ids = "";
        var modelos = "";

        var rotuSel;
        var unneDescripcion;
        var tiunDescripcion;

        for (var numRemesa in params.remesa) {
            ids += params.remesa[numRemesa].remeIdInt + "-DEFINITIVO,";
        }

        for (unneDescripcion in $scope.rotulosSel)
        {
            for (tiunDescripcion in $scope.rotulosSel[unneDescripcion])
            {
                rotuSel = $scope.rotulosSel[unneDescripcion][tiunDescripcion].selected || {};

                if (rotuSel.rotuIdInt)
                {
                    modelos += rotuSel.unneIdInt + "," +
                            rotuSel.tiunIdInt + "," +
                            rotuSel.rotuIdInt + "-";
                }
            }
        }

        if (modelos.length === 0)
        {
            Alert.abrir("Debe seleccionar uno o m\u00e1s formatos de impresi\u00f3n.");
            return;
        }

        /*
         var success = function( response )
         {
         Util.ocultarCargando();
         
         if ( response.codigo === 0 )
         {
         var url = params.url + "" + params.usuario;
         $window.open( url, "_blank" );
         }
         else {
         Alert.abrir( response.mensaje );
         }
         };
         
         var error = function( response )
         {
         Util.ocultarCargando();
         Alert.abrir( "Error enviando los datos." );
         };
         
         var paramsGeneracion = {
         action: "encolarRotulo",
         ids: ids,
         modelos: modelos,
         usuario: params.usuario
         };
         
         Util.mostrarCargando();
         GeneracionRotulosApi.listar( paramsGeneracion )
         .$promise.then( success, error );
         */

        var url = params.url + "" + ids + "/" + modelos;
        var win = $window.open(url, "_blank");

        if (win !== null) {
            $scope.guardarLog();
        }
    };

    $scope.guardarLog = function ()
    {
        var success = function (response)
        {
            Util.ocultarCargando();

            if (response.codigo === 0) {
                Alert.abrir("Datos guardados con \u00e9xito.");
            } else {
                Alert.abrir(response.mensaje);
            }
        };

        var error = function (response)
        {
            Util.ocultarCargando();
            Alert.abrir("Error enviando los datos.");
        };

        var arrRemesaLog = [];

        for (var numRemesa in params.remesa)
        {
            arrRemesaLog.push({
                remeIdInt: params.remesa[numRemesa].remeIdInt,
                usuario: params.usuario,
                locaIdInt: params.locaIdInt
            });
        }

        Util.mostrarCargando();
        NovedadesMasivasApi.consultar({action: "guardarLog"}, arrRemesaLog)
                .$promise.then(success, error);
    };

    $scope.cancel = function ()
    {
        $uibModalInstance.dismiss("cancel");
    };

    $scope.init();
}

function modalOpcionesExportacionController($scope, $uibModalInstance, Alert, NovedadesMasivasApi, $window, $timeout, $interval, params)
{
    var _sesId = "" + (new Date()).getTime();

    $scope.init = function ()
    {
        $scope.opcion = {};

        $scope.listaOpciones = [{
                id: 1,
                abreviatura: "R",
                descripcion: "REMESA"
            },
            {
                id: 2,
                abreviatura: "U",
                descripcion: "UNIDADES"
            }];
    };



    $scope.generarExcel = function ()
    {

        var opcionSel = $scope.opcion.selected || {};
        var tipoExportacion = opcionSel.abreviatura || "";

        if (tipoExportacion === "")
        {
            Alert.abrir("Debe seleccionar un tipo de exportaci\u00f3n.");
            return;
        }

        var success = function (response)
        {
            if (response.codigo === 0)
            {
                $scope._verificarExportacion();
                $scope.promiseExp = $interval($scope._verificarExportacion, 10 * 1000); // Cada 10 segundos.
            } else
            {
                Util.ocultarCargando();
                Alert.abrir(response.mensaje);
            }
        };

        var error = function (response)
        {
            Util.ocultarCargando();

            //console.log(response);
            Alert.abrir("Error enviando los datos.");
        };

        Util.mostrarCargando();

        $("button#btnGenerar").blur();


        var requestParams = {
            action: "exportarExcel",
            tipoExportacion: tipoExportacion,
            sessionId: _sesId
        };

        NovedadesMasivasApi.consultar(requestParams, params.paramsExport)
                .$promise.then(success, error);
    };

    $scope._verificarExportacion = function ()
    {
        var success = function (response)
        {
            if (response.codigo === 0)
            {
                if (typeof response.object === "string")
                {
                    Util.ocultarCargando();

                    var url = "rest/novedades/descargarExcel/" + response.object;
                    $window.location = url;

                    if ($scope.promiseExp) {
                        $interval.cancel($scope.promiseExp);
                    }
                }
            } else
            {
                Util.ocultarCargando();
                Alert.abrir(response.mensaje);

                if ($scope.promiseExp) {
                    $interval.cancel($scope.promiseExp);
                }
            }
        };

        var error = function (response)
        {
            Util.ocultarCargando();


            Alert.abrir("Error enviando los datos.");

            if ($scope.promiseExp) {
                $interval.cancel($scope.promiseExp);
            }
        };

        // Util.mostrarCargando();

        NovedadesMasivasApi.listar({action: "verificarExportacion", sessionId: _sesId})
                .$promise.then(success, error);
    };

    $scope.cancel = function ()
    {
        $uibModalInstance.dismiss("cancel");
    };

    $timeout($scope.init, 100);
}
