// modules
var app = angular

        .module("app", ["ui.router",
            "ui.select",
            "ngSanitize",
            "ngResource",
            "ngCookies",
            "smart-table",
            "ui.sortable",
            "ui.bootstrap",
            "ui.grid",
            'ui.grid.edit', 'ui.grid.cellNav', 'ui.grid.moveColumns', 'ui.grid.pinning', 'ui.grid.selection',
            'ui.grid.resizeColumns', 'ui.grid.rowEdit', 'ui.grid.saveState',
            "ngAnimate",
            "ngStorage",
            "bootstrapLightbox",
            "ngTagsInput",
            "app.common",
            "app.novedadesMasivas"
        ]);


app.controller("appController", ["$rootScope", function( $rootScope ) {

    $rootScope.VERSION = "1.8.2";
    $rootScope.currentUser = null;

}]);

app.directive('clearable', function() {
    var directive = {
        restrict: 'A',
        require: 'ngModel',
        link: link
    };

    return directive;

    function link(scope, elem, attrs, ctrl) {
        elem.addClass('clearable');

        elem.bind('input', function() {
            elem[toggleClass(elem.val())]('x');
        });

        elem.on('mousemove', function(e) {
            if (elem.hasClass('x')) {
                elem[toggleClass(this.offsetWidth - 25 < e.clientX - this.getBoundingClientRect().left)]('onX');
            }
        });

        elem.on('click', function(e) {
            if (elem.hasClass('onX')) {
                elem.removeClass('x onX').val(undefined);
                ctrl.$setViewValue(undefined);
                ctrl.$render();
                scope.$digest();
            }
        });

        function toggleClass(v) {
            return v ? 'addClass' : 'removeClass';
        }
    }
});

app.directive('numbersOnly', function() {
    return {
        restrict: 'A',
        link: function(scope, elm, attrs, ctrl) {
            elm.on('keydown', function(event) {
                // Backspace, enter, escape, arrows
                if ([8, 13, 27, 37, 38, 39, 40].indexOf(event.which) > -1) {
                    return true;
                }
                // Numbers
                else if (event.which >= 48 && event.which <= 57) {
                    return true;
                }
                // Numpad number
                else if (event.which >= 96 && event.which <= 105) {
                    return true;
                }
                /*
                else if ( [110, 190].indexOf(event.which) > -1 ) {
                    // dot and numpad dot
                    return true;
                }
                */
                else {
                    event.preventDefault();
                    return false;
                }
            });
        }
    };

});

app.directive('alphanumericOnly', function() {
    return {
        restrict: 'A',
        link: function(scope, elm, attrs, ctrl) {
            elm.on('keypress', function(event) {

                var regExp = /[0-9a-zA-z@\u00f1\u00d1\u00e1\u00e9\u00ed\u00f3\u00fa\u00c1\u00c9\u00cd\u00d3\u00da\u00fc\u00dc\\.,\s]+/g;

                if (regExp.test(event.key) === false) {
                    event.preventDefault();
                    return false;
                }

                if (typeof scope.validarDetalle === "function") {
                    scope.validarDetalle(elm);
                }

                return true;
            });
        }
    };

});

app.config(function($stateProvider, $urlRouterProvider, $httpProvider, LightboxProvider) {

    $urlRouterProvider.otherwise("/novedadesMasivas");

    $stateProvider

    // HOME STATES AND NESTED VIEWS ========================================
        .state("home", {
        url: "/home",
        templateUrl: "app/home/partial-home.html",
        data: {
            requireLogin: false
        }
    })

    .state("novedades", {
        url: "/novedades",
        templateUrl: "app/novedadesmasivas/partial-novedades-masivas.html",
        data: {
            requireLogin: false
        }
    })

    .state("novedadesMasivas", {
        url: "/novedadesMasivas",
        templateUrl: "app/novedadesmasivas/partial-novedades-masivas.html",
        data: {
            requireLogin: false
        }
    });

    LightboxProvider.calculateImageDimensionLimits = function(dimensions) {
        return {
            'maxWidth': dimensions.windowWidth >= 768 ? // default
                dimensions.windowWidth - 92 : dimensions.windowWidth - 52,
            'maxHeight': 1200 // custom
        };
    };

    // the modal height calculation has to be changed since our custom template is
    // taller than the default template
    LightboxProvider.calculateModalDimensions = function(dimensions) {
        var width = Math.max(400, dimensions.imageDisplayWidth + 32);

        if (width >= dimensions.windowWidth - 20 || dimensions.windowWidth < 768) {
            width = 'auto';
        }

        return {
            'width': width, // default
            'height': 'auto' // custom
        };
    };

});

app.run(function($rootScope, $state) {});
