(function() {
    'use strict';
    angular
        .module('myapp')
        .factory('Producto', Producto);

    Producto.$inject = ['$resource'];

    function Producto ($resource) {
        var resourceUrl =  'laptop-michel:7076/api/productos/:id';

        return $resource(resourceUrl, {}, {
            'query': { 
                method: 'GET', 
                isArray: true,
                codigoProducto: null,
                nombreProducto: null,
                descripcion: null
            },
            'get': {
                method: 'GET'
            },
            'update': { method:'PUT' }
        });
    }
})();
