(function() {
    'use strict';
    angular
        .module('omsApp')
        .factory('Orden', Orden);

    Orden.$inject = ['$resource'];

    function Orden ($resource) {
        var resourceUrl =  'api/ordenes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'ordenTotal': {
                url: 'api/ordenes/total/:id',
                method: 'GET', 
                isArray: false
            },
            'ordenDetalle': {
                url: 'api/ordenes/detalle/:id',
                method: 'GET', 
                isArray: true
            },
            'update': { method:'PUT' }
        });
    }
})();
