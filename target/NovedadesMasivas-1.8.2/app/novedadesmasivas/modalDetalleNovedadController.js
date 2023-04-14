angular.module( "app.novedadesMasivas" )
        .controller( "modalDetalleNovedadController", [ "$scope", "$uibModalInstance", "$uibModal", "params",   modalDetalleNovedadController ] );


function modalDetalleNovedadController( $scope, $uibModalInstance, $uibModal, params )
{
    $scope.botones = params.parent.botones;
    
    $scope.init = function ()
    {
        $scope.listaUnidades = [];
        $scope._listaUnidades = [];

        $scope.obtenerContador();
    };

    $scope.cancel = function ()
    {
        $uibModalInstance.dismiss("cancel");
    };

    $scope.obtenerContador = function ()
    {
        $scope.numNovedad = params.pos + 1;
        $scope.totalNovedades = params.listaRemesas.length;
        $scope.remesa = params.remesa;
    };

    $scope.obtenerAnterior = function ()
    {
        if ( params.pos - 1 < 0 ) {
            return;
        }

        params.pos--;
        params.remesa = params.listaRemesas[params.pos];
        params.parent.sombrearFila( params.pos );

        $scope.obtenerContador();
    };

    $scope.obtenerSiguiente = function ()
    {
        if ( params.pos + 1 >= params.listaRemesas.length ) {
            return;
        }

        params.pos++;
        params.remesa = params.listaRemesas[params.pos];
        params.parent.sombrearFila( params.pos );

        $scope.obtenerContador();
    };
    
    $scope.verUnidades = function( remesa )
    {
        params.parent.verUnidades( remesa );
    };
    
    $scope.verDocumentosCliente = function( remesa )
    {
        params.parent.verDocumentosCliente( remesa );
    };
    
    $scope.verCertificado = function( remesa )
    {
        params.parent.verCertificado( remesa );
    };

    $scope.init();
}