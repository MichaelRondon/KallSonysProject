(function () {
    'use strict';

    angular
            .module('myapp')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
                .state('producto', {
                    url: '/producto',
                    views: {
                        'content@': {
                            templateUrl: 'modules/products/productos.html',
                            controller: 'ProductoController'
                        }
                    }
                });
    }

})();
