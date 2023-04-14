angular.module( "app.novedadesMasivas" )
       .controller( "modalObservacionesController", [ "$scope", "$uibModalInstance", "Alert", "$http", "Listas", "params", modalObservacionesController ] );
       
function modalObservacionesController( $scope, $uibModalInstance, Alert, $http, Listas, params )
{
    $scope.init = function()
    {
        $scope.tipo = params.tipo;
        $scope.infoTitulo = params.infoTitulo;
        $scope.observaciones = "";
        $scope.causalNovedad = {};
        
        var callbackCausalesNovedad = function( listaCausalesNovedad )
        {
            $scope.listaCausalesNovedad = listaCausalesNovedad;
        };
        
        var paramsCausalesNovedad = {
            abreviatura: "CAU_NOV"
        };
        
        Listas.obtenerListaStandard( $http, paramsCausalesNovedad, Alert, callbackCausalesNovedad );
    };
    
    $scope.guardar = function()
    {
        var observaciones = $.trim( $scope.observaciones || "" );
        
        /*
        if ( observaciones.length < 30 )
        {
            Alert.abrir( "Las observaciones son obligatorias y deben ser mayor o igual a 30 caracteres." );
            return;
        }
        */
        
        var causalNovSel = $scope.causalNovedad.selected || {};
        var livaCausalNov = causalNovSel.liva_id_int || 0;
        
        var paramsEnv = {
            livaCausalNov: livaCausalNov,
            observaciones: observaciones.toUpperCase()
        };
        
        $uibModalInstance.close( paramsEnv );
    };
    
    $scope.validarDetalle = function( jqInput )
    {
        $scope.observaciones = jqInput.val();
    };
    
    $scope.cancel = function()
    {
        $uibModalInstance.dismiss( "cancel" );
    };
    
    $scope.init();
}