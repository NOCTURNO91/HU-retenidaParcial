"use strict";

angular.module( "app.novedadesMasivas" )
       .controller( "modalSolucionMasivaController", [ "$scope", "$uibModalInstance", "UtilService", "Alert", "$http", "$timeout", "NovedadesMasivasApi", "Listas", "Confirm", "params", modalSolucionMasivaController ] );

function modalSolucionMasivaController( $scope, $uibModalInstance, UtilService, Alert, $http, $timeout, NovedadesMasivasApi, Listas, Confirm, params )
{
    $scope.idTipoSolucion = "";
    
    $scope.TIPO_SOL_PROCEDIMIENTO = "0";
    $scope.TIPO_SOL_REOFRECIMIENTO = "0";
    $scope.TIPO_SOL_REM_SOLUCION = "0";
    
    $scope.init = function()
    {
        var callbackParametrosSistema = function( valParametros )
        {
            $scope.TIPO_SOL_PROCEDIMIENTO = valParametros[0];
            $scope.TIPO_SOL_REOFRECIMIENTO = valParametros[1];
            $scope.TIPO_SOL_REM_SOLUCION = valParametros[2];
            
            $scope._cargarListas();
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
        
        $scope.listaHorasOfrecimiento = [];
        $scope.listaDiasNoHabiles = [];
        
        for ( var i = 1; i <= 12; i++ )
        {
            $scope.listaHorasOfrecimiento.push( {
                id: i,
                descripcion: i < 10 ? ( "0" + i ) : ( "" + i ) 
            } );
        }
        
        var length = 0;
        $scope.esMasiva = true;
        
        for ( var remeIdInt in params.remesa  )
        {
            length++;
            $scope.remesa = params.remesa[remeIdInt];
        }
        
        if ( length === 1 ) {
            $scope.esMasiva = false;
        }
        
        $scope.minCaracteresObs = params.minCaracteresObs;
        
        $scope.solucion = {
            tipoSolucion: {}
        };
        
        var listNomParametros = [
            "LIVA_TIPO_SOL_PROC",
            "LIVA_TIPO_SOL_REOF",
            "LIVA_TIPO_SOL_REM_SOL"
        ];
        
        $scope.limpiar();
        UtilService.obtenerParametrosSistema( $http, listNomParametros, Alert, callbackParametrosSistema );
        
        $timeout( function() { $( "input[name='switch']" ).bootstrapSwitch(); }, 100 );
    };
    
    $scope._cargarListas = function()
    {
        var numListas = 0;
        
        var callbackListas = function()
        {
            numListas++;
            
            if ( numListas === 6 ) {
                Util.ocultarCargando();
            }
        };
        
        var callbackTipoSolucion = function( listaTipoSolucion )
        {
            $scope.listaTipoSolucion = listaTipoSolucion.filter( function( currentVal ) {
                    
                return ( "" + currentVal.liva_id_int !== $scope.TIPO_SOL_REM_SOLUCION );
            }  );
            
            callbackListas();
        };
        
        var callbackTipoDireccion = function( listaTipoNomenclatura )
        {
            $scope.listaTipoNomenclatura = listaTipoNomenclatura;
            callbackListas();
        };
        
        var callbackTipoPropiedad = function( listaTipoPropiedad )
        {
            $scope.listaTipoPropiedad = listaTipoPropiedad;
            callbackListas();
        };
        
        var callbackTipoSolucionProc = function( listaTipoSolProc )
        {
            $scope.listaTipoSolProc = listaTipoSolProc;
            callbackListas();
        };
        
        var callbackCanalSolucion = function( listaCanalesSolucion )
        {
            $scope.listaCanalesSolucion = listaCanalesSolucion;
            callbackListas();
        };
        
        var paramsTipoSolucion = {
            abreviatura: "TIPO_SOLUCION_NOV",
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
        
        Listas.obtenerListaStandard( $http, paramsTipoSolucion, Alert, callbackTipoSolucion );
        Listas.obtenerListaStandard( $http, paramsTipoDireccion, Alert, callbackTipoDireccion );
        Listas.obtenerListaStandard( $http, paramsTipoPropiedad, Alert, callbackTipoPropiedad );
        Listas.obtenerListaStandard( $http, paramsTipoSolucionProc, Alert, callbackTipoSolucionProc );
        Listas.obtenerListaStandard( $http, paramsCanalSolucion, Alert, callbackCanalSolucion );
        $scope._consultarDiasNoHabiles( callbackListas );
    };
    
    $scope._consultarDiasNoHabiles = function( callback )
    {
        var success = function( response )
        {
            // Util.ocultarCargando();
            
            if ( response.codigo === 0 )
            {
                $scope.listaDiasNoHabiles = response.list;
            
                if ( typeof callback === "function" ) {
                    callback();
                }
            }
            else
            {
                Util.ocultarCargando();
                Alert.abrir( response.mensaje );
            }
        };

        var error = function( response )
        {
            Util.ocultarCargando();
            Alert.abrir( "Error enviando los datos." );
        };
        
        // Util.mostrarCargando();
        NovedadesMasivasApi.get( { action: "consultarDiasNoHabiles" } )
                           .$promise.then( success, error );
    };
    
    $scope.seleccionarTipoSolucion = function()
    {
        console.log("aqui ejecuta")
        var tipoSolSel = $scope.solucion.tipoSolucion.selected || {};
        var idTipoSolucion = tipoSolSel.liva_id_int || "";
        
        $scope.limpiar();
        $scope.idTipoSolucion = "" + idTipoSolucion;
        
        if ( "" + idTipoSolucion === $scope.TIPO_SOL_REM_SOLUCION )
        {
            $scope.solucion.remesaSolucion.disabled = false;
        }
        else if ( "" + idTipoSolucion === $scope.TIPO_SOL_REOFRECIMIENTO )
        {
            $scope.solucion.reofrecimiento.disabled = false;
            $timeout( function() { $( "input#txtReofContactoCliente" ).focus(); }, 100 );
        }
        else if ( "" + idTipoSolucion === $scope.TIPO_SOL_PROCEDIMIENTO )
        {
            $scope.solucion.procedimiento.disabled = false;
            $timeout( function() { $( "input#txtProcContactoCliente" ).focus(); }, 100 );
        }
    };
    
    $scope.cancel = function()
    {
        $uibModalInstance.dismiss( "cancel" );
    };
    
    $scope.openFechaOfrecimiento = function()
    {
        $scope.solucion.reofrecimiento.isOpenFechaOfrecimiento = !$scope.solucion.reofrecimiento.isOpenFechaOfrecimiento;
    };
    
    $scope.checkNuevaDireccion = function()
    {
        $scope.solucion.reofrecimiento.nuevaDireccion = !$scope.solucion.reofrecimiento.nuevaDireccion;
        
        if ( $scope.solucion.reofrecimiento.nuevaDireccion === false )
        {
            $scope.solucion.reofrecimiento.tipoDireccion = "";
            $scope.limpiarDireccion();
        }
    };
    
    $scope.limpiarDireccion = function()
    {
        $scope.solucion.reofrecimiento.tipoNomenclatura = {};
        $scope.solucion.reofrecimiento.tipoPropiedad = {};
        $scope.solucion.reofrecimiento.dirUrbanaUno = "";
        $scope.solucion.reofrecimiento.dirUrbanaDos = "";
        $scope.solucion.reofrecimiento.dirUrbanaTres = "";
        $scope.solucion.reofrecimiento.dirUrbanaCuatro = "";
        $scope.solucion.reofrecimiento.caseNuevaDireccion = "";
    };
    
    $scope.obtenerDireccionUrbana = function()
    {
        var tipoNomenSel = $scope.solucion.reofrecimiento.tipoNomenclatura.selected || {};
        var tipoPropSel = $scope.solucion.reofrecimiento.tipoPropiedad.selected || {};
        
        var direccion = ( tipoNomenSel.liva_abreviatura || "" ) + " " +
                        $.trim( $scope.solucion.reofrecimiento.dirUrbanaUno || "" ) + " # " +
                        $.trim( $scope.solucion.reofrecimiento.dirUrbanaDos || "" ) + " - " +
                        $.trim( $scope.solucion.reofrecimiento.dirUrbanaTres || "" ) + " " +
                        ( tipoPropSel.liva_abreviatura || "" ) + " " +
                        $.trim( $scope.solucion.reofrecimiento.dirUrbanaCuatro || "" );
             
        $scope.solucion.reofrecimiento.caseNuevaDireccion = direccion.toUpperCase();
    };
    
    
    $scope.grabar = function()
    {
        
        var tipoSolucionSel = $scope.solucion.tipoSolucion.selected || {};
        var idTipoSolucion = tipoSolucionSel.liva_id_int || 0;
        
        
        var execute = function (opc)
        {
            // ACEPTAR
            if (opc === 1) {
                    if ( $scope.solucion.reofrecimiento.disabled === false ) {
                        $scope._grabarReofrecimiento();
                    }
                    else if ( $scope.solucion.procedimiento.disabled === false ) {
                        $scope._grabarProcedimiento();
                    }
            }else{
                return;
            }
        };
        
        if ( idTipoSolucion === 0 )
        {
            var callback = function()
            {
                $timeout( function() {
                    $( "#cbxTipoSolucion input[type='text']" ).focus();
                }, 100 );
            };
            
            Alert.abrir( "El tipo de soluc\u00f3n es obligatoria.", callback );
        }
        else
        {
             //Estado de la remesa en distribuccion
            if($scope.remesa.novedadesRezagos >= 2 && $scope.remesa.seguimientosUltimNovedad >= 2  && $scope.remesa.esonIdInt == "206" && ($scope.remesa.codEstadoNovedad == "3" || $scope.remesa.codEstadoNovedad == "4") ){
                 Confirm.abrir("Mercanc\u00EDa pr\u00F3xima a cumplir tiempo establecido para ser marcada como no distribuible, \u00bfEst\u00e1 seguro de ingresar un nuevo seguimiento ?", execute);
            }else if($scope.remesa.novedadesRezagos >= 1   && $scope.remesa.esonIdInt == "294" && ($scope.remesa.codEstadoNovedad == "3" || $scope.remesa.codEstadoNovedad == "4") ){
                Confirm.abrir("Mercanc\u00EDa en rezago \u00bfDesea continuar con la gesti\u00F3n de la novedad ?", execute);
            }else{
                execute(1);
            }
        }
    };
    
    $scope._grabarReofrecimiento = function()
    {
        if ( $scope._validarObligatoriosReofrecimiento() === false ) {
            return;
        }
        
        var success = function( response )
        {
            Util.ocultarCargando();

            if ( response.codigo === 0 ) {
                Alert.abrir( "Datos guardados con \u00e9xito.", $uibModalInstance.close );
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
        
        var arrSeguimientos = [];
        
        var fechaOfrecimiento = typeof $scope.solucion.reofrecimiento.fechaOfrecimiento === "string" 
                                ? $scope.solucion.reofrecimiento.fechaOfrecimiento
                                : moment( $scope.solucion.reofrecimiento.fechaOfrecimiento ).format( "DD/MM/YYYY" );
        
        var objRemesas = params.remesa;
        
        for ( var remeIdInt in objRemesas )
        {
            arrSeguimientos.push( {
                canoIdInt: objRemesas[remeIdInt].canoIdInt,
                caseTipoSolucion: $scope.solucion.tipoSolucion.selected.liva_id_int,
                caseCodigoNovedad: objRemesas[remeIdInt].consecutivoNovedad,
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
            } );
        }
        
        Util.mostrarCargando();
        NovedadesMasivasApi.save( { action: "ingresarSeguimientosMasivos" }, arrSeguimientos )
                           .$promise.then( success, error );
    };
    
    $scope._validarObligatoriosReofrecimiento = function()
    {
        var callback;
        var contactoCliente = $.trim( $scope.solucion.reofrecimiento.caseContactoCliente || "" );
        
        if ( contactoCliente.length === 0 )
        {
            callback = function()
            {
                $timeout( function() { $( "input#txtReofContactoCliente" ).focus(); }, 100 );
            };
            
            Alert.abrir( "El contacto cliente es obligatorio.", callback );
            return false;
        }
        
        var frioParticipe = $.trim( $scope.solucion.reofrecimiento.caseFrioParticipe || "" );
        var caseSeguimiento = $.trim( $scope.solucion.reofrecimiento.caseSeguimiento || "" );
        
        if ( caseSeguimiento.length < $scope.minCaracteresObs )
        {
            callback = function()
            {
                $timeout( function() { $( "textarea#txtReofDetalle" ).focus(); }, 100 );
            };
            
            Alert.abrir( "El detalle es obligatorio y debe ser mayor o igual a " + $scope.minCaracteresObs + " caracteres.", callback );
            return false;
        }
        
        //TODO VALIDATION FUNCIONARIO QUE RESPALDA SOLUCION
        if(frioParticipe.length === 0 )
        {
            callback = function()
            {
                $timeout( function() { $( "input#txtReofFrioTcc" ).focus(); }, 100 );
            };
            
            Alert.abrir( "El funcionario TCC que respalda la soluci\u00f3n es obligatorio.", callback );
            return false;
        }
        
        var fechaActual = new Date();
        
        fechaActual.setHours( 0 );
        fechaActual.setMinutes( 0 );
        fechaActual.setSeconds( 0 );
        fechaActual.setMilliseconds( 0 );
        
        var momentActual = moment( fechaActual );
        var momentFin = moment( $scope.solucion.reofrecimiento.fechaOfrecimiento );

        if ( momentFin.diff( momentActual ) < 0 )
        {
            Alert.abrir( "La fecha de ofrecimiento no puede ser menor que la fecha actual." );
            return false;
        }
        
        var horaReofIniSel = $scope.solucion.reofrecimiento.horaOfrecimientoIni.selected || {};
        var horaReofIni = horaReofIniSel.id || -1;
        
        var horaReofFinSel = $scope.solucion.reofrecimiento.horaOfrecimientoFin.selected || {};
        var horaReofFin = horaReofFinSel.id || -1;
        
        if ( horaReofIni === -1 || horaReofFin === -1 )
        {
            callback = function()
            {
                $timeout( function() {
                    $( "#cbxReofHoraIni input[type='text']" ).focus();
                }, 100 );
            };
            
            Alert.abrir( "El rango de horas de reofrecimiento es obligatorio.", callback );
            return false;
        }
        else
        {
            horaReofIni = $scope._procesarHora( $scope.solucion.reofrecimiento.horaOfrecIniAmPm, horaReofIni );
            horaReofFin = $scope._procesarHora( $scope.solucion.reofrecimiento.horaOfrecFinAmPm, horaReofFin );
            
            if ( horaReofFin < horaReofIni )
            {
                callback = function()
                {
                    $timeout( function() {
                        $( "#cbxReofHoraFin input[type='text']" ).focus();
                    }, 100 );
                };

                Alert.abrir( "La hora final no puede ser mayor que la hora inicial.", callback );
                return false;
            }
        }
        
        if ( $scope._validarDireccionReofrecimiento() === false ) {
            return false;
        }
        
        $scope.solucion.reofrecimiento.caseContactoCliente = contactoCliente.toUpperCase();
        $scope.solucion.reofrecimiento.caseFrioParticipe = frioParticipe;
        $scope.solucion.reofrecimiento.caseSeguimiento = caseSeguimiento.toUpperCase();
        $scope.solucion.reofrecimiento.caseHoraInicioReofrecimiento = "01/01/1980 " + ( horaReofIni < 10 ? "0" + horaReofIni : horaReofIni ) + ":00";
        $scope.solucion.reofrecimiento.caseHoraFinalReofrecimiento = "01/01/1980 " + ( horaReofFin < 10 ? "0" + horaReofFin : horaReofFin ) + ":00";
        $scope.solucion.reofrecimiento.caseUsuario = params.usuario.user;
        
        return true;
    };
    
    $scope._procesarHora = function( amPm, valHora )
    {
        if ( amPm === "pm" ) {
            valHora += 12;
        }
        else if ( valHora === 12 ) {
            valHora = 0;
        }
        
        if ( valHora === 24 ) {
            valHora = 12;
        }
        
        return valHora;
    };
    
    $scope._validarDireccionReofrecimiento = function()
    {
        if ( $scope.solucion.reofrecimiento.nuevaDireccion === true )
        {
            var direccion;
            
            if ( $scope.solucion.reofrecimiento.tipoDireccion.length === 0 )
            {
                Alert.abrir( "Debe seleccionar un tipo de direcci\u00f3n." );
                return false;
            }
            
            if ( $scope.solucion.reofrecimiento.tipoDireccion === "urbana" )
            {
                var msjError; // "Todos los campos de la direcci\u00f3n son obligatorios.";
                var callback;
                
                var tipoNomenSel = $scope.solucion.reofrecimiento.tipoNomenclatura.selected || {};
                var tipoNomenclatura = ( tipoNomenSel.liva_abreviatura || "" ) ;
                
                if ( tipoNomenclatura.length === 0 )
                {
                    msjError = "El tipo de v\u00eda es obligatoria.";
                    
                    callback = function()
                    {
                        $timeout( function() {
                            $( "#cbxTipoNomenclatura input[type='text']" ).focus();
                        }, 100 );
                    };
                    
                    Alert.abrir( msjError, callback );
                    return false;
                }
                
                direccion = $.trim( $scope.solucion.reofrecimiento.dirUrbanaUno || "" );
                
                if ( direccion.length === 0 )
                {
                    msjError = "El nombre de la v\u00eda es obligatoria.";
                    
                    callback = function()
                    {
                        $timeout( function() { $( "input#txtDirUrbanaUno" ).focus(); }, 100 );
                    };
                    
                    Alert.abrir( msjError, callback );
                    return false;
                }
                
                direccion = $.trim( $scope.solucion.reofrecimiento.dirUrbanaDos || "" );
                
                if ( direccion.length === 0 )
                {
                    msjError = "El n\u00famero de v\u00eda es obligatoria.";
                    
                    callback = function()
                    {
                        $timeout( function() { $( "input#txtDirUrbanaDos" ).focus(); }, 100 );
                    };
                    
                    Alert.abrir( msjError, callback );
                    return false;
                }
                
                direccion = $.trim( $scope.solucion.reofrecimiento.dirUrbanaTres || "" );
                
                if ( direccion.length === 0 )
                {
                    msjError = "El n\u00famero de v\u00eda es obligatoria.";
                    
                    callback = function()
                    {
                        $timeout( function() { $( "input#txtDirUrbanaTres" ).focus(); }, 100 );
                    };
                    
                    Alert.abrir( msjError, callback );
                    return false;
                }
                
                // var tipoPropSel = $scope.solucion.reofrecimiento.tipoPropiedad.selected || {};
                // var tipoPropiedad = ( tipoPropSel.liva_abreviatura || "" );
                
                /*
                if ( tipoPropiedad.length === 0 )
                {
                    callback = function()
                    {
                        $timeout( function() {
                            $( "#cbxTipoPropiedad input[type='text']" ).focus();
                        }, 100 );
                    };
                    
                    Alert.abrir( msjError, callback );
                    return false;
                }
                */
            }
            else
            {
                direccion = $.trim( $scope.solucion.reofrecimiento.caseNuevaDireccion || "" );
                
                if ( direccion.length === 0 )
                {
                    callback = function()
                    {
                        $timeout( function() { $( "input#txtDirRural" ).focus(); }, 100 );
                    };
                    
                    Alert.abrir( "La direcci\u00f3n es obligatoria.", callback );
                    return false;
                }
            }
        }
        
        return true;
    };
    
    $scope._grabarProcedimiento = function()
    {
        if ( $scope._validarObligatoriosProcedimiento() === false ) {
            return;
        }
        
        var success = function( response )
        {
            Util.ocultarCargando();

            if ( response.codigo === 0 ) {
                Alert.abrir( "Datos guardados con \u00e9xito.", $uibModalInstance.close );
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
        
        var arrSeguimientos = [];
        
        var objRemesas = params.remesa;
        
        for ( var remeIdInt in objRemesas )
        {
            arrSeguimientos.push( {
                canoIdInt: objRemesas[remeIdInt].canoIdInt,
                caseTipoSolucion: $scope.solucion.tipoSolucion.selected.liva_id_int,
                caseCodigoNovedad: objRemesas[remeIdInt].consecutivoNovedad,
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
            } );
        }
        
        Util.mostrarCargando();
        NovedadesMasivasApi.save( { action: "ingresarSeguimientosMasivos" }, arrSeguimientos )
                           .$promise.then( success, error );
    };
    
    $scope._validarObligatoriosProcedimiento = function()
    {
        var callback;
        var contactoCliente = $.trim( $scope.solucion.procedimiento.caseContactoCliente || "" );
        
        if ( contactoCliente.length === 0 )
        {
            callback = function()
            {
                $timeout( function() { $( "input#txtProcContactoCliente" ).focus(); }, 100 );
            };
            
            Alert.abrir( "El contacto cliente es obligatorio.", callback );
            return false;
        }
        
        var frioParticipe = $.trim( $scope.solucion.procedimiento.caseFrioParticipe || "" );
        
        //TODO VALIDACION FUNCIONARIO QUE RESPALDA SOLUCION
        if(frioParticipe.length == 0 )
        {
            
            callback = function()
            {
                $timeout( function() { $( "input#txtProcFrioTcc" ).focus(); }, 100 );
            };

            Alert.abrir("El funcionario TCC que respalda la soluci\u00f3n es obligatorio.", callback);
            return false;
        } 
        
       //TODO VALIDACION CANAL SOLUCION
       var canalSolucionSelect = $scope.solucion.procedimiento.canalSolucion.selected || {};
       var idCanalSolucion = canalSolucionSelect.liva_id_int || 0;
       
       if(idCanalSolucion == 0 ){
           callback = function()
            {
                $timeout( function() {
                    $( "#cbxCanalSol input[type='text']" ).focus();
                }, 100 );
            };
           Alert.abrir("El canal soluci\u00f3n es obligatorio.", callback);
            return false;
       }
        
        var caseSeguimiento = $.trim( $scope.solucion.procedimiento.caseSeguimiento || "" );
        
        if ( caseSeguimiento.length < $scope.minCaracteresObs )
        {
            callback = function()
            {
                $timeout( function() { $( "textarea#txtProcDetalle" ).focus(); }, 100 );
            };
            
            Alert.abrir( "El detalle es obligatorio y debe ser mayor o igual a " + $scope.minCaracteresObs + " caracteres.", callback );
            return false;
        }
        
        var tipoSolProcSel = $scope.solucion.procedimiento.tipoSolProc.selected || {};
        var idTipoSolProc = tipoSolProcSel.liva_id_int || "0";
        
        if ( idTipoSolProc === "0" )
        {
            callback = function()
            {
                $timeout( function() {
                    $( "#cbxTipoSolProc input[type='text']" ).focus();
                }, 100 );
            };
            
            Alert.abrir( "El tipo de soluci\u00f3n procedimiento es obligatorio.", callback );
            return false;
        }
        
        var frioParticipe = $.trim( $scope.solucion.procedimiento.caseFrioParticipe || "" );
        
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
    
    $scope.limpiar = function()
    {
        $scope.switchOnText = "SI";
        $scope.switchOffText = "NO";
        
        $scope.solucion.remesaSolucion = {
            disabled: true
        };
        
        var fechaOfrecimiento = moment().add( 1, "days" );
        
        if ( $scope.listaDiasNoHabiles.length > 0 ) {
            fechaOfrecimiento = $scope.obtenerDiaHabil( fechaOfrecimiento );
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
    
    $scope.obtenerDiaHabil = function( fecha )
    {
        var i;
        
        var numDia;
        var fechaFormat;
        var diaNoHabil = true;
        
        while ( diaNoHabil === true )
        {
            numDia = fecha.day();
            
            // 6: Sábado.
            if ( numDia === 6 ) {
                fecha = fecha.add( 1, "days" );
            }
            else
            {
                diaNoHabil = false;
                fechaFormat = fecha.format( "DD/MM/YYYY" );
                
                for ( i in $scope.listaDiasNoHabiles )
                {
                    if ( fechaFormat === $scope.listaDiasNoHabiles[i] )
                    {
                        diaNoHabil = true;
                        break;
                    }
                }
                
                if ( diaNoHabil === true ) {
                    fecha = fecha.add( 1, "days" );
                }
            }
        }
        
        return fecha;
    };

    $scope.init();
}