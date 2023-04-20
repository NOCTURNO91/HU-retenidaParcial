/* global Util, conta */

"use strict";
angular.module("app.novedadesMasivas")
        .controller("modalIngresoNovedadController", ["$scope", "$uibModalInstance", "Alert", "NovedadesMasivasApi", "$http", "Listas", "UtilService", "$timeout", "params", modalIngresoNovedadController]);


function modalIngresoNovedadController($scope, $uibModalInstance, Alert, NovedadesMasivasApi, $http, Listas, UtilService, $timeout, params) {
    $scope.idTipoSolucion = "";
    $scope.solucionAutomatica = false;

    //12772
    $scope.administrador = false;
    $scope.isProcesoDistribucion = false;
    $scope.showListaJustificacionNovedad = false;
    $scope.justificacionNovSel = 0;
    
    $scope.isvisibleIup = false;
    $scope.mensaje = false;
    
    

    $scope.init = function () {

        $scope.validacionReex = params.remesas[0].reex;
        $scope.validarEntregadoConexiones = params.estadoEntregadoConexiones;
        $scope.validacionFechaReal = params.remesas[0].esonIdInt;
        $scope.estadosRemesa = params.estados.split(',');        
        $scope.fechasHabiles = params.diasHabiles;
        $scope.numeroFechasHabiles = params.numeroDiasHabiles;
        $scope.minCaracteresObs = params.minCaracteresObs;
        $scope.numRemesas = Util.getObjectSize(params.remesas);
        
        
        //HU:12772  @DATE:23/08/2021  @Author:Manuel Gallardo -- Softcaribbean        
        $scope.administrador = params.isAdmin;
        $scope.procDistribucionMercanciaIdInt = params.procesoDistribucionMercancia;

        
        if ($scope.numRemesas === 1) {
            for (var remeIdInt in params.remesas) {
                $scope.remesa = params.remesas[remeIdInt];
            }
        }
        
        $scope.desactivarFechaReal = false;
        $scope.estadosRemesa.forEach(function (value) {
            if (parseInt(value) === $scope.validacionFechaReal){
                
                var horaMaxima = new Date();
                horaMaxima.setHours(parseInt(params.horaMaxima));
                horaMaxima.setMinutes(0);
                horaMaxima.setSeconds(0);
                var horaActual = new Date();
                if ( horaActual <= horaMaxima ) {
                    $scope.horaHabilFechaNovedad = true;
                } else {
                    $scope.desactivarFechaReal = true;
                }
            }
        });

        /*if ($scope.validacionFechaReal === null) {
            $scope.desactivarFechaReal = true;
        } else {
            $scope.desactivarFechaReal = false;
        }*/

        $scope.listaNovedadesPpales = [];
        $scope.listaTipoSolucion = [];
        $scope.listaComplementosNovedad = [];
        $scope.listaJustificacionNovedad = []; 

        $scope.listaHorasOfrecimiento = [];
        $scope.listaDiasNoHabiles = [];

        for (var i = 1; i <= 12; i++) {
            $scope.listaHorasOfrecimiento.push({
                id: i,
                descripcion: i < 10 ? ("0" + i) : ("" + i)
            });
        }

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

        $scope.paramsIngreso = {
            novedadPpal: {},
            complementoNovedad: {},
            ejecucionAutomatica: false,
            causalNovedad: {},
            observaciones: "",
            fechaNovedad: new Date(),
            fechaRealNovedad: new Date(),
            isOpenFechaRealNovedad: false,
            unidades: 0,
            totalUnidades: 0,
            motivoEjecucion: "",
            justificacionNovedad: {}
        };

        $scope.solucion = {
            tipoSolucion: {}
        };

        $scope.habilitarEjecAutomatica = $scope._validarTipoSolucion();

        var callbackParametrosSistema = function (valParametros) {
            $scope.TIPO_SOL_PROCEDIMIENTO = valParametros[0];
            $scope.TIPO_SOL_REOFRECIMIENTO = valParametros[1];
            $scope.TIPO_SOL_REM_SOLUCION = valParametros[2];

            $scope._cargarListas();
        };

        var listNomParametros = [
            "LIVA_TIPO_SOL_PROC",
            "LIVA_TIPO_SOL_REOF",
            "LIVA_TIPO_SOL_REM_SOL"
        ];

        $scope._calcularTotalUnidades();
        UtilService.obtenerParametrosSistema($http, listNomParametros, Alert, callbackParametrosSistema);
    };

    $scope._calcularTotalUnidades = function () {
        var unidades = 0;

        for (var i in params.remesas) {
            unidades += params.remesas[i].unidadesRemesa;
        }

        $scope.paramsIngreso.unidades = unidades;
        $scope.paramsIngreso.totalUnidades = unidades;
    };

    $scope.validarRangoDeFechasHabiles = function (fechaValidar) {

        const params = {
            fechaHabil: fechaValidar,
        };
        return NovedadesMasivasApi.consultar({action: "consultardiashabiles"}, params)
                .$promise
    }

    $scope._cargarListas = function () {
        var numListas = 0;

        var callbackListas = function () {
            numListas++;

            if (numListas === 8) {
                Util.ocultarCargando();
            }
        };

        var callbackNovedadPrincipal = function (listaNovedadesPpales) {

            if (params.tipoUnidadDeGegocioSel === "PAQUETERIA") {

                var d = listaNovedadesPpales.filter(v => v.idInt !== "-2" && v.idInt !== "-1");
                $scope.listaNovedadesPpales = d;
                callbackListas();

            } else if (params.tipoUnidadDeGegocioSel === "MENSAJERIA") {

                var c = listaNovedadesPpales.filter(v => v.idInt !== "-2" && v.idInt !== "-1");
                $scope.listaNovedadesPpales = c;
                callbackListas();

            }

        };

        var callbackTipoSolucion = function (listaTipoSolucion) {
            $scope.listaTipoSolucion = listaTipoSolucion.filter(function (currentVal) {
                return ("" + currentVal.liva_id_int !== $scope.TIPO_SOL_REM_SOLUCION) && ("" + currentVal.liva_id_int !== $scope.TIPO_SOL_PROCEDIMIENTO);
            });

            callbackListas();
        };

        var callbackCausalesNovedad = function (listaCausalesNovedad) {
            $scope.listaCausalesNovedad = listaCausalesNovedad;
            callbackListas();
        };
        
        //HU:12772  @DATE:23/08/2021  @Author:Manuel Gallardo -- Softcaribbean 
        var callbackJustificacionNovedad = function (listaJustificacionNovedad){ 
            $scope.listaJustificacionNovedad = listaJustificacionNovedad; 
            callbackListas(); 
        }; 

        var callbackTipoDireccion = function (listaTipoNomenclatura) {
            $scope.listaTipoNomenclatura = listaTipoNomenclatura;
            callbackListas();
        };

        var callbackTipoPropiedad = function (listaTipoPropiedad) {
            $scope.listaTipoPropiedad = listaTipoPropiedad;
            callbackListas();
        };

        var callbackTipoSolucionProc = function (listaTipoSolProc) {
            $scope.listaTipoSolProc = listaTipoSolProc;
            callbackListas();
        };

        var callbackCanalSolucion = function (listaCanalesSolucion) {
            $scope.listaCanalesSolucion = listaCanalesSolucion;
            callbackListas();
        };


        var paramsNovedadPrincipal = {
            tipoLista: "NOVEDAD_PRINCIPAL",
            expansionUno: params.procIdInt,
            obtenerListaDos: true,
            mostrarCargando: false,
            ocultarCargando: false
        };

        var paramsTipoSolucion = {
            abreviatura: "TIPO_SOLUCION_NOV",
            mostrarCargando: false,
            ocultarCargando: false
        };

        var paramsCausalesNovedad = {
            abreviatura: "CAU_NOV",
            mostrarCargando: false,
            ocultarCargando: false
        };
        
        var paramsJustificacionNovedad = { 
            abreviatura: "LST_PLA_NOV", 
            mostrarCargando: false, 
            ocultarCargando: false 
        }; 

        var paramsTipoDireccion = {
            abreviatura: "DIR_VIA_1",
            mostrarCargando: false,
            ocultarCargando: false
        };

        var paramsTipoPropiedad = {
            abreviatura: "DIR_TIPO_PROP",
            mostrarCargando: false,
            ocultarCargando: false
        };

        var paramsTipoSolucionProc = {
            abreviatura: "TIPO_SOL_PROC",
            mostrarCargando: false,
            ocultarCargando: false
        };

        var paramsCanalSolucion = {
            abreviatura: "CANA_NOV",
            mostrarCargando: false,
            ocultarCargando: false
        };

        Util.mostrarCargando();

        Listas.obtenerLista($http, paramsNovedadPrincipal, Alert, callbackNovedadPrincipal);
        Listas.obtenerListaStandard($http, paramsTipoSolucion, Alert, callbackTipoSolucion);
        Listas.obtenerListaStandard($http, paramsCausalesNovedad, Alert, callbackCausalesNovedad);
        Listas.obtenerListaStandard($http, paramsJustificacionNovedad, Alert, callbackJustificacionNovedad); 
        Listas.obtenerListaStandard($http, paramsTipoDireccion, Alert, callbackTipoDireccion);
        Listas.obtenerListaStandard($http, paramsTipoPropiedad, Alert, callbackTipoPropiedad);
        Listas.obtenerListaStandard($http, paramsTipoSolucionProc, Alert, callbackTipoSolucionProc);
        Listas.obtenerListaStandard($http, paramsCanalSolucion, Alert, callbackCanalSolucion);
        $scope._consultarDiasNoHabiles(callbackListas);
    };

    $scope._consultarDiasNoHabiles = function (callback) {
        var success = function (response) {
            // Util.ocultarCargando();

            if (response.codigo === 0) {
                $scope.listaDiasNoHabiles = response.list;

                if (typeof callback === "function") {
                    callback();
                }
            } else {
                Util.ocultarCargando();
                Alert.abrir(response.mensaje);
            }
        };

        var error = function (response) {
            Util.ocultarCargando();
            Alert.abrir("Error enviando los datos.");
        };

        // Util.mostrarCargando();
        NovedadesMasivasApi.get({action: "consultarDiasNoHabiles"})
                .$promise.then(success, error);
    };

    $scope.obtenerComplementosNovedad = function (listaComplementos) {
        var novedadPpalSel = $scope.paramsIngreso.novedadPpal.selected || {};
        var idInt = novedadPpalSel.idInt || 0;

        console.log("$scope.paramsIngreso.complementoNovedad", $scope.paramsIngreso.complementoNovedad);
        $scope.paramsIngreso.complementoNovedad = {};
        $scope.listaComplementosNovedad = [];
        
        
        console.log("$scope.listaComplementosNovedad", $scope.listaComplementosNovedad);

        if (idInt === 0) {
            return;
        }

        var callbackComplementoNovedad = function (listaComplementos) {
            $scope.listaComplementosNovedad = listaComplementos;
        };

        var paramsComplementoNovedad = {
            tipoLista: "COMPLEMENTO_NOV",
            idPadre: idInt,
            obtenerListaDos: true,
            expansionUno: params.procIdInt,
        };

        Listas.obtenerLista($http, paramsComplementoNovedad, Alert, callbackComplementoNovedad);
    };
    

    //HU:12772  @DATE:23/08/2021  @Author:Manuel Gallardo -- Softcaribbean 
    $scope.guardarValorJustificacioNovedad = function ( ){ 
        $scope.justificacionNovSel = $scope.paramsIngreso.justificacionNovedad.selected.liva_valor || { };

    };


    $scope.validarSolucionAutomatica = function (item) {
        
        $scope.validadorCodNov = item.idInt;
        console.log($scope.validadorCodNov);
        
        $scope.complementoPrcIdInt =  $scope.paramsIngreso.complementoNovedad.selected.procIdInt;

        if( $scope.complementoPrcIdInt == $scope.procDistribucionMercanciaIdInt  ){
            $scope.isProcesoDistribucion  = true;
        } else {
            $scope.isProcesoDistribucion  = false;
        }

        if ( $scope.isProcesoDistribucion  && $scope.administrador ){
            $scope.showListaJustificacionNovedad = true;   
        } else {
            $scope.showListaJustificacionNovedad = false;
        }
        
        if ($scope.validadorCodNov == 987){
            $scope.isvisibleIup = true;
         
        }else{
            
            $scope.isvisibleIup = false;
        }




        // En el campo expansion 1 guardo el indicador de novedad Con solucion a
        if (item.expansionUno > 0) {

            $scope.solucionAutomatica = true;

            $scope.solucion.tipoSolucion = {};
            $scope.solucion.reofrecimiento.caseContactoCliente = "";
            $scope.solucion.reofrecimiento.caseFrioParticipe = "";
            $scope.solucion.reofrecimiento.caseSeguimiento = "";
            $scope.solucion.reofrecimiento.nuevaDireccion = false;
            $scope.solucion.reofrecimiento.tipoDireccion = "";
            $scope.solucion.procedimiento.caseContactoCliente = "";
            $scope.solucion.procedimiento.caseFrioParticipe = "";
            $scope.solucion.procedimiento.caseSeguimiento = "";
            $scope.solucion.procedimiento.tipoSolProc = {};
            $scope.solucion.procedimiento.canalSolucion = {};
            $scope.paramsIngreso.causalNovedad = {};
            $scope.paramsIngreso.motivoEjecucion = "";
            $scope.paramsIngreso.ejecucionAutomatica = false;

        } else {
            console.log("validar");
            $scope.solucionAutomatica = false;
        }
        
        if ($scope.validadorCodNov==987){
            console.log("Aqui se consume la funcion");
            //consumo de funcion
            $scope._consultarIUPRemesa("12345");
        }
    };

    


    $scope.grabar = function () {
        var ingresoNovedad = $scope._validarObligatorios();

        if (ingresoNovedad === null) {
            return;
        }
        

        if($scope.isProcesoDistribucion && !$scope.administrador){
            Alert.abrir("El usuario no es administrador, no puede grabar novedades con proceso distribuci\u00F3n.", $uibModalInstance.close);
            return;
        }

        var success = function (response) {
            Util.ocultarCargando();

            if (response.codigo === 0) {
                var msj = $.trim(response.mensaje || "");

                if (msj.length === 0) {
                    Alert.abrir("Datos guardados con \u00e9xito.", $uibModalInstance.close);
                } else {
                    Alert.abrir(msj, $uibModalInstance.close);
                }
            } else {
                Alert.abrir(response.mensaje);
            }
        };

        var error = function (response) {
            Util.ocultarCargando();
            Alert.abrir("Error enviando los datos.");
        };

        var arrSeguimientos = [];

        if ($scope._validarTipoSolucion() === true) {
            if ($scope.idTipoSolucion === $scope.TIPO_SOL_REOFRECIMIENTO) {
                arrSeguimientos = $scope._grabarReofrecimiento();
            } else if ($scope.idTipoSolucion === $scope.TIPO_SOL_PROCEDIMIENTO) {
                arrSeguimientos = $scope._grabarProcedimiento();
            }

            if (arrSeguimientos === null) {
                return;
            }
        }


        ingresoNovedad.arrSeguimientos = arrSeguimientos;

        const filtrarRemesa = [];
        const map = new Map();
        for (const item of ingresoNovedad.arrRemesas) {


            if (!map.has(item.numRemesa)) {
                map.set(item.numRemesa, true);
                filtrarRemesa.push({
                    codCausaNovedad: item.codCausaNovedad,
                    fechaNovedad: item.fechaNovedad,
                    fechaRemesa: item.fechaRemesa,
                    idCausalNovedad: item.idCausalNovedad,
                    idCiudadOrigen: item.idCiudadOrigen,
                    motivoEjecucion: item.motivoEjecucion,
                    numRemesa: item.numRemesa,
                    observaciones: item.observaciones,
                    procIdInt: item.procIdInt,
                    remeIdInt: item.remeIdInt,
                    unidadesRemesa: item.unidadesRemesa,
                    usuario: item.usuario,
                    codigoBarras: item.codigoBarras,
                    codigoJustificacionNovedad: item.justificacionNov //HU:12772  @DATE:23/08/2021  @Author:Manuel Gallardo -- Softcaribbean 
                });
            }
        }

        // Se filtran remesas duplicadas
        ingresoNovedad.arrRemesas = filtrarRemesa;

        Util.mostrarCargando();
        NovedadesMasivasApi.save({action: "grabarNovedades", remeIdInt: null, ipidIdInt: null}, ingresoNovedad).$promise.then(success, error);
    };

    $scope.arre =[]
    
    $scope.mensaje = {
        uno:false,
        dos:false,
        tres:false
    }    
    
    $scope.contador = 0
    
    $scope.checkIUP = function(detalle, valor){
        if($scope.arre.includes(detalle)){
            
            for( var i = 0; i < $scope.arre.length; i++){ 
                if ( $scope.arre[i] === detalle) { 
                    $scope.arre.splice(i, 1); 
                }
            }
            
        }else{
            $scope.arre.push(detalle)
        }
        
        
        console.log($scope.mensaje)
        $scope.contador = $scope.arre.length
        
        
        
        console.log($scope.arre);
        console.log($scope.contador);
    }
    
    $scope.checktodo = function () {
        $scope.mensaje.uno = true
        $scope.mensaje.dos = true
        $scope.mensaje.tres = true
    };
    
    
    
    $scope._validarObligatorios = function () {
        var novedadPpalSel = $scope.paramsIngreso.novedadPpal.selected || {};
        var novedadPpalIdInt = novedadPpalSel.idInt || 0;

           if($scope.contador==0){
            Alert.abrir("Debe ser obligatorio seleccionar  minimo 1 IUP");
            return null;
        }

        if (novedadPpalIdInt === 0) {
            Alert.abrir("La novedad principal es obligatoria.");

            $scope.solucion.tipoSolucion = {};
            return null;
        }

        var complementoNovSel = $scope.paramsIngreso.complementoNovedad.selected || {};
        var complementoNovIdInt = complementoNovSel.idInt || 0;

        if (complementoNovIdInt === 0) {
            Alert.abrir("El complemento de novedad es obligatorio.");

            $scope.solucion.tipoSolucion = {};
            return null;
        }

        //12722        
        if( $scope.showListaJustificacionNovedad && $scope.justificacionNovSel == 0 ) {
            Alert.abrir("La justificaci\u00F3n del planteamiento de la novedad es obligatorio.");
            return null;
        }

        var observaciones = $.trim($scope.paramsIngreso.observaciones || "");

        if (observaciones.length < $scope.minCaracteresObs) {
            Alert.abrir("Las observaciones son obligatorias y deben ser mayor o igual a " + $scope.minCaracteresObs + " caracteres.");
            return null;
        }

        var unidades = parseInt($scope.paramsIngreso.unidades);

        if (isNaN(unidades) === true || unidades < 1) {
            Alert.abrir("Las unidades son obligatorias y deben ser mayor que 0.");

            $scope.solucion.tipoSolucion = {};
            return null;
        } else if (unidades > parseInt($scope.paramsIngreso.totalUnidades)) {
            Alert.abrir("Las unidades no pueden ser mayores que el n\u00famero total de unidades de la remesa.");
            return null;
        }

        if ($scope._validarFechas() === false) {
            return null;
        }

        var idCausalNovedad = 0;
        var motivoEjecucion = "";

        if ($scope.paramsIngreso.ejecucionAutomatica === true) {
            var causalNovedadSel = $scope.paramsIngreso.causalNovedad.selected || {};

            idCausalNovedad = causalNovedadSel.liva_id_int || 0;
            motivoEjecucion = $.trim($scope.paramsIngreso.motivoEjecucion || "").toUpperCase();
        }

        var arrRemesas = [];
        var arrNovedades = [];

        var fechaNovedad = typeof $scope.paramsIngreso.fechaRealNovedad === "string" ?
                $scope.paramsIngreso.fechaRealNovedad :
                moment($scope.paramsIngreso.fechaRealNovedad).format("DD/MM/YYYY");

        for (var i in params.remesas) {
            arrRemesas.push({
                idCiudadOrigen: params.usuario.idciudad,
                fechaNovedad: fechaNovedad,
                codCausaNovedad: complementoNovIdInt,
                observaciones: observaciones.toUpperCase(),
                numRemesa: params.remesas[i].numRemesa,
                fechaRemesa: params.remesas[i].fechaRemesa,
                usuario: params.usuario.user,
                remeIdInt: params.remesas[i].remeIdInt,
                unidadesRemesa: ($scope.numRemesas === 1) ? unidades : params.remesas[i].unidadesRemesa,
                procIdInt: params.procIdInt,
                idCausalNovedad: idCausalNovedad,
                motivoEjecucion: motivoEjecucion,
                codigoBarras: params.remesas[i].movilLocal,

                //HU:12772  @DATE:23/08/2021  @Author:Manuel Gallardo -- Softcaribbean
                justificacionNov: $scope.justificacionNovSel
            });

            if ($scope.paramsIngreso.ejecucionAutomatica === true) {
                arrNovedades.push({
                    canoIdInt: "0",
                    livaCausalNov: idCausalNovedad,
                    observaciones: motivoEjecucion,
                    idCeOpEjecuta: params.usuario.guicentroopera,
                    usuario: params.usuario.user
                });
            }
        }

        return {
            arrRemesas: arrRemesas,
            arrNovedades: arrNovedades,
            arrSeguimientos: []
        };
    };

    $scope._validarFechas = function () {
        var momentIni = moment($scope.paramsIngreso.fechaRealNovedad);
        var momentFin = moment(new Date());

        if (momentFin.diff(momentIni) < 0) {
            Alert.abrir("La fecha real de novedad no puede ser mayor que la fecha actual.");
            return false;
        }

        return true;
    };

    $scope.validarSeleccionTipoSolucion = function () {
        if ($scope._validarIngreso() === true) {
            $scope.habilitarEjecAutomatica = $scope._validarTipoSolucion();
            $scope.seleccionarTipoSolucion();
        } else {
            Alert.abrir("Para seleccionar un tipo de soluci\u00f3n, debe diligenciar los campos de la secci\u00f3n Ingreso.");

            $scope.solucion.tipoSolucion = {};
            $scope.habilitarEjecAutomatica = false;
        }

        if ($scope.habilitarEjecAutomatica === false) {
            $scope.paramsIngreso.ejecucionAutomatica = false;
        }
    };

    $scope.chkEjecucionAutomaticaClick = function () {
        $scope.paramsIngreso.ejecucionAutomatica = !$scope.paramsIngreso.ejecucionAutomatica;
    };

    $scope._validarTipoSolucion = function () {
        var tipoSolucionSel = $scope.solucion.tipoSolucion.selected || {};
        var idTipoSol = tipoSolucionSel.liva_id_int || 0;

        return (idTipoSol > 0);
    };

    $scope._validarIngreso = function () {
        var novedadPpalSel = $scope.paramsIngreso.novedadPpal.selected || {};
        var novedadPpalIdInt = novedadPpalSel.idInt || 0;

        if (novedadPpalIdInt === 0) {
            return false;
        }

        var complementoNovSel = $scope.paramsIngreso.complementoNovedad.selected || {};
        var complementoNovIdInt = complementoNovSel.idInt || 0;

        if (complementoNovIdInt === 0) {
            return false;
        }
        
        var observaciones = $.trim($scope.paramsIngreso.observaciones || "");

        if (observaciones.length < $scope.minCaracteresObs) {
            return false;
        }

        var unidades = parseInt($scope.paramsIngreso.unidades);

        if (isNaN(unidades) === true || unidades < 1) {
            return false;
        }

        return true;
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss("cancel");
    };

    $scope.openFechaNovedad = function () {
        $scope.paramsIngreso.isOpenFechaRealNovedad = !$scope.paramsIngreso.isOpenFechaRealNovedad;
    };

    $scope.preventFechaNovedadFocus = function () {
        $scope.paramsIngreso.isOpenFechaRealNovedad = false;
    };

    $scope.validarDiasHabilesFechaReal = function () {



        //console.log($scope.fechasHabiles);
        //console.log($scope.numeroFechasHabiles);


        //console.log('fechaNovedad ', $scope.paramsIngreso.fechaNovedad);
        //console.log('fechaRealNovedad ', $scope.paramsIngreso.fechaRealNovedad);
        

        var diasHabilesRetorno = +$scope.numeroFechasHabiles + $scope.fechasHabiles.length;
        var fechaMinimaEntregadoConexiones = moment().subtract(diasHabilesRetorno, 'd');
        var fechaMinima = moment();
        
        if ($scope.horaHabilFechaNovedad) {

            var fechaMinimaFechaNovedad = moment().subtract(1, 'd');
            
            $scope.fechasHabiles.forEach(value => {
                if (moment(value.diasHabiles).format('YYYY-MM-DD') === moment(fechaMinimaFechaNovedad).format('YYYY-MM-DD')) {
                    fechaMinimaFechaNovedad = moment(fechaMinimaFechaNovedad).subtract(1, 'd');
                }
            });
            
            if (moment($scope.paramsIngreso.fechaRealNovedad).format('YYYY-MM-DD') < fechaMinimaFechaNovedad.format('YYYY-MM-DD')) {
                Alert.abrir('La fecha de planteamiento de novedad esta por fuera de fecha permitida, La fecha minima para el planteamiento de la novedad es '+fechaMinimaFechaNovedad.format('YYYY-MM-DD')+'.');
                $scope.paramsIngreso.fechaRealNovedad = new Date();
                return false;
            }
            
        }
        
        //console.log(fechaMinima.format('YYYY-MM-DD'), '..<.. ',moment($scope.paramsIngreso.fechaRealNovedad).format('YYYY-MM-DD'));
        
        //console.log(params.remesas);

        /*if(moment($scope.paramsIngreso.fechaRealNovedad).format('YYYY-MM-DD') >=  moment($scope.paramsIngreso.fechaNovedad).format('YYYY-MM-DD') ) {
            Alert.abrir('La fecha real de la novedad, no puede ser menor a la fecha de la remesa.');
            $scope.paramsIngreso.fechaRealNovedad = new Date();
            return false;
        }*/
        for (var i = 0; i < params.remesas.length; i++) {
            console.log(moment($scope.paramsIngreso.fechaRealNovedad).format('YYYY-MM-DD') ,' < ', moment(params.remesas[i].fechaRemesa, 'DD/MM/YYYY').format('YYYY-MM-DD'))
            if(moment($scope.paramsIngreso.fechaRealNovedad).format('YYYY-MM-DD') < moment(params.remesas[i].fechaRemesa, 'DD/MM/YYYY').format('YYYY-MM-DD')) {
                Alert.abrir('Fecha de planteamiento de novedad, no puede ser menor a la(s) fecha(s) de la(s) remesa(s) seleccionadas.');
                $scope.paramsIngreso.fechaRealNovedad = new Date();
                return false;
                break;
            }
        }

        if ($scope.validacionFechaReal == $scope.validarEntregadoConexiones) {
            if(fechaMinimaEntregadoConexiones.format('YYYY-MM-DD') > moment($scope.paramsIngreso.fechaRealNovedad).format('YYYY-MM-DD')) {
                Alert.abrir('La fecha de planteamiento en sistema esta por fuera de fecha permitida, La fecha minima para el planteamiento de la novedad es '+fechaMinimaEntregadoConexiones.format('YYYY-MM-DD')+'.');
                $scope.paramsIngreso.fechaRealNovedad = new Date();
                return false;
            }
        }/* else {
            if(fechaMinima.format('YYYY-MM-DD') > moment($scope.paramsIngreso.fechaRealNovedad).format('YYYY-MM-DD')) {
                Alert.abrir('La fecha de planteamiento en sistema esta por fuera de fecha permitida, La fecha minima para el planteamiento de la novedad es '+fechaMinima.format('YYYY-MM-DD')+'.');
                $scope.paramsIngreso.fechaRealNovedad = new Date();
                return false;
            }
        }*/
        
        

        for (var j = 0; j < $scope.fechasHabiles.length; j++) {
            if (moment($scope.fechasHabiles[j].diasHabiles).format('YYYY-MM-DD') === moment($scope.paramsIngreso.fechaRealNovedad).format('YYYY-MM-DD')) {
                Alert.abrir('No se puede realizar el planteamiento de novedad al estar por fuera de fecha permitida, El dia seleccionado no es habil.');
                $scope.paramsIngreso.fechaRealNovedad = new Date();
                return false;
                break;
            }
        }


    }


    //----------------------------------

    // Soluciones

    $scope.limpiarSolucion = function () {
        var fechaOfrecimiento = moment().add(1, "days");

        if ($scope.listaDiasNoHabiles.length > 0) {
            fechaOfrecimiento = $scope.obtenerDiaHabil(fechaOfrecimiento);
        }

        $scope.solucion.reofrecimiento = {
            disabled: true,
            fechaOfrecimiento: fechaOfrecimiento.toDate(),
            isOpenFechaOfrecimiento: false,
            caseContactoCliente: "",
            caseFrioParticipe: "",
            caseFrioTcc: params.usuario.user + " - " + params.usuario.nombre,
            caseSeguimiento: "",
            horaOfrecimientoIni: {},
            horaOfrecIniAmPm: "am",
            horaOfrecimientoFin: {},
            horaOfrecFinAmPm: "am",
            nuevaDireccion: false,
            tipoDireccion: "",
            tipoNomenclatura: {},
            tipoPropiedad: {},
            dirUrbanaUno: "",
            dirUrbanaDos: "",
            dirUrbanaTres: "",
            dirUrbanaCuatro: "",
            caseNuevaDireccion: ""
        };

        $scope.solucion.procedimiento = {
            disabled: true,
            tipoSolProc: {},
            canalSolucion: {},
            caseContactoCliente: "",
            caseFrioParticipe: "",
            caseFrioTcc: params.usuario.user + " - " + params.usuario.nombre,
            caseSeguimiento: ""
        };
    };

    $scope._grabarReofrecimiento = function () {
        if ($scope._validarObligatoriosReofrecimiento() === false) {
            return null;
        }

        var arrSeguimientos = [];

        var fechaOfrecimiento = typeof $scope.solucion.reofrecimiento.fechaOfrecimiento === "string" ?
                $scope.solucion.reofrecimiento.fechaOfrecimiento :
                moment($scope.solucion.reofrecimiento.fechaOfrecimiento).format("DD/MM/YYYY");

        var objRemesas = params.remesas;

        for (var remeIdInt in objRemesas) {
            arrSeguimientos.push({
                canoIdInt: "0",
                caseTipoSolucion: $scope.solucion.tipoSolucion.selected.liva_id_int,
                caseCodigoNovedad: "0",
                caseCodigoSeguimiento: 0,
                caseCodCiudadPlantea: 0,
                caseCodCiudadSoluciona: params.usuario.guicentroopera,
                caseFrioTcc: $scope.solucion.reofrecimiento.caseFrioTcc,
                caseFrioParticipe: $scope.solucion.reofrecimiento.caseFrioParticipe,
                ticoIdInt: 0,
                caseEsSolucion: "1",
                caseContactoCliente: $scope.solucion.reofrecimiento.caseContactoCliente,
                caseSeguimiento: $scope.solucion.reofrecimiento.caseSeguimiento,
                caseFechaReofrecimiento: fechaOfrecimiento,
                caseHoraInicioReofrecimiento: $scope.solucion.reofrecimiento.caseHoraInicioReofrecimiento,
                caseHoraFinalReofrecimiento: $scope.solucion.reofrecimiento.caseHoraFinalReofrecimiento,
                caseNuevaDireccion: $scope.solucion.reofrecimiento.caseNuevaDireccion,
                caseUsuario: $scope.solucion.reofrecimiento.caseUsuario
            });
        }

        return arrSeguimientos;
    };

    $scope._validarObligatoriosReofrecimiento = function () {
        var callback;
        var contactoCliente = $.trim($scope.solucion.reofrecimiento.caseContactoCliente || "");

        if (contactoCliente.length === 0) {
            callback = function () {
                $timeout(function () {
                    $("input#txtReofContactoCliente").focus();
                }, 100);
            };

            Alert.abrir("El contacto cliente es obligatorio.", callback);
            return false;
        }

        var frioParticipe = $.trim($scope.solucion.reofrecimiento.caseFrioParticipe || "");
        var caseSeguimiento = $.trim($scope.solucion.reofrecimiento.caseSeguimiento || "");

        if (caseSeguimiento.length < $scope.minCaracteresObs) {
            callback = function () {
                $timeout(function () {
                    $("textarea#txtReofDetalle").focus();
                }, 100);
            };

            Alert.abrir("El detalle es obligatorio y debe ser mayor o igual a " + $scope.minCaracteresObs + " caracteres.", callback);
            return false;
        }

        var fechaActual = new Date();

        fechaActual.setHours(0);
        fechaActual.setMinutes(0);
        fechaActual.setSeconds(0);
        fechaActual.setMilliseconds(0);

        var momentActual = moment(fechaActual);
        var momentFin = moment($scope.solucion.reofrecimiento.fechaOfrecimiento);

        if (momentFin.diff(momentActual) < 0) {
            Alert.abrir("La fecha de ofrecimiento no puede ser menor que la fecha actual.");
            return false;
        }

        var horaReofIniSel = $scope.solucion.reofrecimiento.horaOfrecimientoIni.selected || {};
        var horaReofIni = horaReofIniSel.id || -1;

        var horaReofFinSel = $scope.solucion.reofrecimiento.horaOfrecimientoFin.selected || {};
        var horaReofFin = horaReofFinSel.id || -1;

        if (horaReofIni === -1 || horaReofFin === -1) {
            callback = function () {
                $timeout(function () {
                    $("#cbxReofHoraIni input[type='text']").focus();
                }, 100);
            };

            Alert.abrir("El rango de horas de reofrecimiento es obligatorio.", callback);
            return false;
        } else {
            horaReofIni = $scope._procesarHora($scope.solucion.reofrecimiento.horaOfrecIniAmPm, horaReofIni);
            horaReofFin = $scope._procesarHora($scope.solucion.reofrecimiento.horaOfrecFinAmPm, horaReofFin);

            if (horaReofFin < horaReofIni) {
                callback = function () {
                    $timeout(function () {
                        $("#cbxReofHoraFin input[type='text']").focus();
                    }, 100);
                };

                Alert.abrir("La hora final no puede ser mayor que la hora inicial.", callback);
                return false;
            }
        }

        if ($scope._validarDireccionReofrecimiento() === false) {
            return false;
        }

        $scope.solucion.reofrecimiento.caseContactoCliente = contactoCliente.toUpperCase();
        $scope.solucion.reofrecimiento.caseFrioParticipe = frioParticipe;
        $scope.solucion.reofrecimiento.caseSeguimiento = caseSeguimiento.toUpperCase();
        $scope.solucion.reofrecimiento.caseHoraInicioReofrecimiento = "01/01/1980 " + (horaReofIni < 10 ? "0" + horaReofIni : horaReofIni) + ":00";
        $scope.solucion.reofrecimiento.caseHoraFinalReofrecimiento = "01/01/1980 " + (horaReofFin < 10 ? "0" + horaReofFin : horaReofFin) + ":00";
        $scope.solucion.reofrecimiento.caseUsuario = params.usuario.user;

        return true;
    };

    $scope._procesarHora = function (amPm, valHora) {
        if (amPm === "pm") {
            valHora += 12;
        } else if (valHora === 12) {
            valHora = 0;
        }

        if (valHora === 24) {
            valHora = 12;
        }

        return valHora;
    };

    $scope._validarDireccionReofrecimiento = function () {
        if ($scope.solucion.reofrecimiento.nuevaDireccion === true) {
            var direccion;

            if ($scope.solucion.reofrecimiento.tipoDireccion.length === 0) {
                Alert.abrir("Debe seleccionar un tipo de direcci\u00f3n.");
                return false;
            }

            if ($scope.solucion.reofrecimiento.tipoDireccion === "urbana") {
                var msjError; // "Todos los campos de la direcci\u00f3n son obligatorios.";
                var callback;

                var tipoNomenSel = $scope.solucion.reofrecimiento.tipoNomenclatura.selected || {};
                var tipoNomenclatura = (tipoNomenSel.liva_abreviatura || "");

                if (tipoNomenclatura.length === 0) {
                    msjError = "El tipo de v\u00eda es obligatoria.";

                    callback = function () {
                        $timeout(function () {
                            $("#cbxTipoNomenclatura input[type='text']").focus();
                        }, 100);
                    };

                    Alert.abrir(msjError, callback);
                    return false;
                }

                direccion = $.trim($scope.solucion.reofrecimiento.dirUrbanaUno || "");

                if (direccion.length === 0) {
                    msjError = "El nombre de la v\u00eda es obligatoria.";

                    callback = function () {
                        $timeout(function () {
                            $("input#txtDirUrbanaUno").focus();
                        }, 100);
                    };

                    Alert.abrir(msjError, callback);
                    return false;
                }

                direccion = $.trim($scope.solucion.reofrecimiento.dirUrbanaDos || "");

                if (direccion.length === 0) {
                    msjError = "El n\u00famero de v\u00eda es obligatoria.";

                    callback = function () {
                        $timeout(function () {
                            $("input#txtDirUrbanaDos").focus();
                        }, 100);
                    };

                    Alert.abrir(msjError, callback);
                    return false;
                }

                direccion = $.trim($scope.solucion.reofrecimiento.dirUrbanaTres || "");

                if (direccion.length === 0) {
                    msjError = "El n\u00famero de v\u00eda es obligatoria.";

                    callback = function () {
                        $timeout(function () {
                            $("input#txtDirUrbanaTres").focus();
                        }, 100);
                    };

                    Alert.abrir(msjError, callback);
                    return false;
                }
            } else {
                direccion = $.trim($scope.solucion.reofrecimiento.caseNuevaDireccion || "");

                if (direccion.length === 0) {
                    callback = function () {
                        $timeout(function () {
                            $("input#txtDirRural").focus();
                        }, 100);
                    };

                    Alert.abrir("La direcci\u00f3n es obligatoria.", callback);
                    return false;
                }
            }
        }

        return true;
    };

    $scope.openFechaOfrecimiento = function () {
        $scope.solucion.reofrecimiento.isOpenFechaOfrecimiento = !$scope.solucion.reofrecimiento.isOpenFechaOfrecimiento;
    };

    $scope.checkNuevaDireccion = function () {
        $scope.solucion.reofrecimiento.nuevaDireccion = !$scope.solucion.reofrecimiento.nuevaDireccion;

        if ($scope.solucion.reofrecimiento.nuevaDireccion === false) {
            $scope.solucion.reofrecimiento.tipoDireccion = "";
            $scope.limpiarDireccion();
        }
    };

    $scope.limpiarDireccion = function () {
        $scope.solucion.reofrecimiento.tipoNomenclatura = {};
        $scope.solucion.reofrecimiento.tipoPropiedad = {};
        $scope.solucion.reofrecimiento.dirUrbanaUno = "";
        $scope.solucion.reofrecimiento.dirUrbanaDos = "";
        $scope.solucion.reofrecimiento.dirUrbanaTres = "";
        $scope.solucion.reofrecimiento.dirUrbanaCuatro = "";
        $scope.solucion.reofrecimiento.caseNuevaDireccion = "";
    };

    $scope.obtenerDireccionUrbana = function () {
        var tipoNomenSel = $scope.solucion.reofrecimiento.tipoNomenclatura.selected || {};
        var tipoPropSel = $scope.solucion.reofrecimiento.tipoPropiedad.selected || {};

        var direccion = (tipoNomenSel.liva_abreviatura || "") + " " +
                $.trim($scope.solucion.reofrecimiento.dirUrbanaUno || "") + " # " +
                $.trim($scope.solucion.reofrecimiento.dirUrbanaDos || "") + " - " +
                $.trim($scope.solucion.reofrecimiento.dirUrbanaTres || "") + " " +
                (tipoPropSel.liva_abreviatura || "") + " " +
                $.trim($scope.solucion.reofrecimiento.dirUrbanaCuatro || "");

        $scope.solucion.reofrecimiento.caseNuevaDireccion = direccion.toUpperCase();
    };

    $scope._grabarProcedimiento = function () {
        if ($scope._validarObligatoriosProcedimiento() === false) {
            return null;
        }

        var arrSeguimientos = [];

        var objRemesas = params.remesas;

        for (var remeIdInt in objRemesas) {
            arrSeguimientos.push({
                canoIdInt: "0",
                caseTipoSolucion: $scope.solucion.tipoSolucion.selected.liva_id_int,
                caseCodigoNovedad: "0",
                caseCodigoSeguimiento: 0,
                caseCodCiudadPlantea: 0,
                caseCodCiudadSoluciona: params.usuario.guicentroopera,
                caseFrioTcc: $scope.solucion.procedimiento.caseFrioTcc,
                caseFrioParticipe: $scope.solucion.procedimiento.caseFrioParticipe,
                ticoIdInt: 0,
                caseEsSolucion: "1",
                caseContactoCliente: $scope.solucion.procedimiento.caseContactoCliente,
                caseSeguimiento: $scope.solucion.procedimiento.caseSeguimiento,
                caseTipoSolucionProd: $scope.solucion.procedimiento.caseTipoSolucionProd,
                caseUsuario: $scope.solucion.procedimiento.caseUsuario,
                caseCanalSolucion: $scope.solucion.procedimiento.caseCanalSolucion
            });
        }

        return arrSeguimientos;
    };
    
    
        

    $scope._validarObligatoriosProcedimiento = function () {
        var callback;
        var contactoCliente = $.trim($scope.solucion.procedimiento.caseContactoCliente || "");

        if (contactoCliente.length === 0) {
            callback = function () {
                $timeout(function () {
                    $("input#txtProcContactoCliente").focus();
                }, 100);
            };

            Alert.abrir("El contacto cliente es obligatorio.", callback);
            return false;
        }
        
        
        
        

        var caseSeguimiento = $.trim($scope.solucion.procedimiento.caseSeguimiento || "");

        if (caseSeguimiento.length < $scope.minCaracteresObs) {
            callback = function () {
                $timeout(function () {
                    $("textarea#txtProcDetalle").focus();
                }, 100);
            };

            Alert.abrir("El detalle es obligatorio y debe ser mayor o igual a " + $scope.minCaracteresObs + " caracteres.", callback);
            return false;
        }

        var tipoSolProcSel = $scope.solucion.procedimiento.tipoSolProc.selected || {};
        var idTipoSolProc = tipoSolProcSel.liva_id_int || "0";

        if (idTipoSolProc === "0") {
            callback = function () {
                $timeout(function () {
                    $("#cbxTipoSolProc input[type='text']").focus();
                }, 100);
            };

            Alert.abrir("El tipo de soluci\u00f3n procedimiento es obligatorio.", callback);
            return false;
        }

        var frioParticipe = $.trim($scope.solucion.procedimiento.caseFrioParticipe || "");

        var canalSolSel = $scope.solucion.procedimiento.canalSolucion.selected || {};
        var idCanalSol = canalSolSel.liva_id_int || "0";

        $scope.solucion.procedimiento.caseContactoCliente = contactoCliente.toUpperCase();
        $scope.solucion.procedimiento.caseFrioParticipe = frioParticipe;
        $scope.solucion.procedimiento.caseSeguimiento = caseSeguimiento.toUpperCase();
        $scope.solucion.procedimiento.caseTipoSolucionProd = idTipoSolProc;
        $scope.solucion.procedimiento.caseUsuario = params.usuario.user;
        $scope.solucion.procedimiento.caseCanalSolucion = idCanalSol;

        return true;
    };

    $scope.seleccionarTipoSolucion = function () {
        var tipoSolSel = $scope.solucion.tipoSolucion.selected || {};
        var idTipoSolucion = tipoSolSel.liva_id_int || "";

        $scope.limpiarSolucion();
        $scope.idTipoSolucion = "" + idTipoSolucion;

        if ("" + idTipoSolucion === $scope.TIPO_SOL_REOFRECIMIENTO) {
            $scope.solucion.reofrecimiento.disabled = false;
            $timeout(function () {
                $("input#txtReofContactoCliente").focus();
            }, 100);
        } else if ("" + idTipoSolucion === $scope.TIPO_SOL_PROCEDIMIENTO) {
            $scope.solucion.procedimiento.disabled = false;
            $timeout(function () {
                $("input#txtProcContactoCliente").focus();
            }, 100);
        }
    };

    $scope.obtenerDiaHabil = function (fecha) {
        var i;

        var numDia;
        var fechaFormat;
        var diaNoHabil = true;

        while (diaNoHabil === true) {
            numDia = fecha.day();

            // 6: Sábado.
            if (numDia === 6) {
                fecha = fecha.add(1, "days");
            } else {
                diaNoHabil = false;
                fechaFormat = fecha.format("DD/MM/YYYY");

                for (i in $scope.listaDiasNoHabiles) {
                    if (fechaFormat === $scope.listaDiasNoHabiles[i]) {
                        diaNoHabil = true;
                        break;
                    }
                }

                if (diaNoHabil === true) {
                    fecha = fecha.add(1, "days");
                }
            }
        };

        return fecha;
    }

    $scope.obtenerUltimaNovedad = function(){
        
    return NovedadesMasivasApi.obtener({action: "obtenerUltimaNovedad", remeIdInt: null, ipidIdInt: null}, $scope.arrayRemesasValEstado).$promise;
       
    };
    
    //add funcion
    $scope._consultarIUPRemesa = function (ListaRemeIdInt){
        var success = function (response){
            console.log(response);
            console.log(response.codigo);
            console.log(response.mensaje);
            console.log(response.list);
            
        };
        
        var error = function (response){
            console.log(response);
            
        };
        
        NovedadesMasivasApi.obtener({action:"consultarIUPRemesa",remeIdInt: null, remeIdInt: null},ListaRemeIdInt).$promise.then(success, error);
        
    };
    
    //autor carlosQuiroz 
    // FECHA: 12/04/23
    // HU: INTERVENCION MCIA RETENIDA PARCIAL
    
    /*$scope._iupRemesa = function (){
        
        console.log($select.selected.descripcion);
        //$select.selected.descripcion
    };*/
    
    
    
    
    $scope.init();
}