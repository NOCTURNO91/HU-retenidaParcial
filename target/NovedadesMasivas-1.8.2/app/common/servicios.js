var moduloCommon = angular.module('app.common', []);

//----------------------------------------------
//Servicio de ventana de confirmación.
//----------------------------------------------
moduloCommon.service("Confirm", function ($uibModal) {

    this.abrir = function (mensaje, callback)
    {
        //------------------------
        // Abre ventana modal
        //------------------------
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'app/common/partial-confirm.html',
            controller: 'ConfirmController',
            size: 'xs',
            backdrop: false,
            keyboard: false,
            resolve: {
                mensaje: function () {
                    return mensaje;
                }
            }
        });

        modalInstance.result.then(function (retorno)
        {
            if (typeof callback === "function") {
                callback(retorno);
            }
        });
    };
});

//----------------------------------------------
//Servicio de alert.
//----------------------------------------------
moduloCommon.service("Alert", function ($uibModal) {

    this.abrir = function (mensaje, callback) {

        //------------------------
        // Abre ventana modal
        //------------------------
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'app/common/partial-alert.html',
            controller: 'AlertController',
            size: 'xs',
            backdrop: false,
            keyboard: false,
            resolve: {
                mensaje: function () {
                    return mensaje;
                },
                callback: function () {
                    return callback;
                }
            }
        });

        modalInstance.result.then(function (retorno) {

            return retorno;
        });

    };

});

moduloCommon.service("UtilService", function () {

    this.traerUsuario = function ($http, Alert, callback)
    {
        var url = Constants.URL_REST + "/util/traerUsuario";

        var success = function (response) {

            Util.ocultarCargando();
            var usuario = {};


            if (response.data.list !== null && response.data.list.length > 0) {
                usuario = response.data.list[0];
            }

            if (callback !== null) {
                callback(usuario);
            }
        };

        var error = function () {
            Util.ocultarCargando();
        };

        Util.mostrarCargando();
        $http.get(url).then(success, error);
    };

    this.obtenerParametrosSistema = function ($http, nomParametros, Alert, callback) {

        var url = Constants.URL_REST + "/util/obtenerParametrosSistema";

        var success = function (response) {

            Util.ocultarCargando();

            console.log("obtenerParametrosSistema", response);

            if (response.data.codigo === 0) {

                if (typeof callback === "function") {
                    callback(response.data.object);
                }
            } else {
                Alert.abrir(response.data.mensaje);
            }
        };

        var error = function () {
            Util.ocultarCargando();
        };

        Util.mostrarCargando();
        $http.post(url, nomParametros).then(success, error);
    };

});

moduloCommon.service("Listas", function () {

    this.obtenerLista = function ($http, params, Alert, callback)
    {
        var url = Constants.URL_REST + "/listas/obtenerLista";

        if (typeof params.obtenerListaDos === "boolean" && params.obtenerListaDos === true) {
            url = Constants.URL_REST + "/listas/obtenerListaDos";
        }

        var success = function (response)
        {
            if (params.ocultarCargando === true) {
                Util.ocultarCargando();
            }

            if (response.data.codigo === 0)
            {
                if (typeof callback === "function") {
                    callback(response.data.list);
                }
            } else {
                Alert.abrir(response.data.mensaje);
            }
        };

        var error = function () {
            Util.ocultarCargando();
        };

        if (typeof params.mostrarCargando !== "boolean") {
            params.mostrarCargando = true;
        }

        if (typeof params.ocultarCargando !== "boolean") {
            params.ocultarCargando = true;
        }

        if (params.mostrarCargando === true) {
            Util.mostrarCargando();
        }

        $http.post(url, params).then(success, error);
    };

    this.listarCentrosOperacion = function ($http, Alert, params, callback)
    {
        if (typeof params !== "object") {
            params = {};
        }

        if (typeof params.mostrarCargando !== "boolean") {
            params.mostrarCargando = true;
        }

        if (typeof params.ocultarCargando !== "boolean") {
            params.ocultarCargando = true;
        }

        var url = Constants.URL_REST + "/listas/listarCentrosOperacion";

        var success = function (response)
        {
            if (params.ocultarCargando === true) {
                Util.ocultarCargando();
            }

            if (response.data.codigo === 0)
            {
                if (typeof callback === "function") {
                    callback(response.data.list);
                }
            } else {
                Alert.abrir(response.data.mensaje);
            }
        };

        var error = function () {
            Util.ocultarCargando();
        };

        if (params.mostrarCargando === true) {
            Util.mostrarCargando();
        }

        $http.get(url).then(success, error);
    };

    this.obtenerListaStandard = function ($http, params, Alert, callback)
    {
        if (typeof params.mostrarCargando !== "boolean") {
            params.mostrarCargando = true;
        }
        
        if (typeof params.ocultarCargando !== "boolean") {
            params.ocultarCargando = true;
        }

        var url = Constants.URL_REST + "/listas/obtenerListaStandard/" + params.abreviatura;
        var success = function (response)
        {
            if (params.ocultarCargando === true) {
                Util.ocultarCargando();
            }

            if (response.data.codigo === 0)
            {
                if (typeof callback === "function") {
                    callback(response.data.list);
                }
            } else {
                Alert.abrir(response.data.mensaje);
            }
        };

        var error = function () {
            Util.ocultarCargando();
        };

        if (params.mostrarCargando === true) {
            Util.mostrarCargando();
        }

        $http.get(url).then(success, error);
    };

    this.obtenerUnidadesNegocio = function ($http, Alert, params, callback)
    {
        if (typeof params !== "object") {
            params = {};
        }

        if (typeof params.mostrarCargando !== "boolean") {
            params.mostrarCargando = true;
        }

        if (typeof params.ocultarCargando !== "boolean") {
            params.ocultarCargando = true;
        }

        var url = Constants.URL_REST + "/unidadesnegocio/obtenerUnidades";

        var success = function (response)
        {
            if (params.ocultarCargando === true) {
                Util.ocultarCargando();
            }

            if (response.data.codigo === 0)
            {
                if (typeof callback === "function") {
                    callback(response.data.list);
                }
            } else {
                Alert.abrir(response.data.mensaje);
            }
        };

        var error = function () {
            Util.ocultarCargando();
        };

        if (params.mostrarCargando === true) {
            Util.mostrarCargando();
        }

        $http.get(url).then(success, error);
    };

    this.consultar = function ($http, params, Alert, callback)
    {
        if (typeof params.mostrarCargando !== "boolean") {
            params.mostrarCargando = true;
        }

        if (typeof params.ocultarCargando !== "boolean") {
            params.ocultarCargando = true;
        }

        var url = Constants.URL_REST + "/listas/consultar";

        var success = function (response)
        {
            if (params.ocultarCargando === true) {
                Util.ocultarCargando();
            }

            if (response.data.codigo === 0)
            {
                if (typeof callback === "function") {
                    callback(response.data.list);
                }
            } else {
                Alert.abrir(response.data.mensaje);
            }
        };

        var error = function () {
            Util.ocultarCargando();
        };

        if (params.mostrarCargando === true) {
            Util.mostrarCargando();
        }

        $http.post(url, params).then(success, error);
    };

});

moduloCommon.factory("Excel", function ($window) {

    var uri = 'data:application/vnd.ms-excel;base64,';
    var template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>';
    var base64 = function (s) {
        return $window.btoa(unescape(encodeURIComponent(s)));
    };
    var format = function (s, c) {
        return s.replace(/{(\w+)}/g, function (m, p) {
            return c[p];
        })
    };

    return {
        tableToExcel: function (tableId, worksheetName)
        {
            var table = $(tableId);
            var ctx = {
                worksheet: worksheetName,
                table: table.html()
            };

            var thisFormat = format(template, ctx);
            return uri + base64(thisFormat);
        }
    };
});

moduloCommon.controller('ConfirmController', function ($scope, $uibModalInstance, mensaje) {

    $scope.mensaje = mensaje;

    $scope.aceptar = function () {
        $uibModalInstance.close(1);
    };

    $scope.cancelar = function () {
        $uibModalInstance.close(2);
    };
});

moduloCommon.controller("AlertController", function ($scope, $uibModalInstance, mensaje, callback) {

    if (typeof mensaje === "string") {
        $scope.mensaje = mensaje;
    } else {
        $scope.objMensaje = mensaje;
    }

    $scope.aceptar = function ()
    {
        if (typeof callback === "function") {
            callback();
        }

        $uibModalInstance.close(1);
    };
});