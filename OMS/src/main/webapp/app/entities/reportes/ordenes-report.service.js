(function() {
    'use strict';
    angular
        .module('omsApp')
        .factory('OrdenesReport', OrdenesReport);

    OrdenesReport.$inject = ['$resource', 'DateUtils'];

    function OrdenesReport ($resource, DateUtils) {
        var resourceUrl =  'api/ordenes/';

        return $resource(resourceUrl, {}, {
            'rankingClientes': { 
                method: 'GET', 
                isArray: true,
                idProducto: null
            }
        });
    }
})();
