(function() {
    'use strict';
    angular
        .module('omsApp')
        .factory('Ordenes', Ordenes);

    Ordenes.$inject = ['$resource', 'DateUtils'];

    function Ordenes ($resource, DateUtils) {
        var resourceUrl =  'api/ordenes/:id';

        return $resource(resourceUrl, {}, {
            'query': { 
                method: 'GET', 
                isArray: true,
                idProducto: null
            },
            'abiertas': {
                url: 'api/ordenes/abiertas',
                method: 'GET', 
                isArray: true,
                idProducto: null
            },
            'get': {
                method: 'GET',
                transformResponse: function (data) {
//                    if (data) {
//                        data = angular.fromJson(data);
//                        data.fechaRevDisponibilidad = DateUtils.convertDateTimeFromServer(data.fechaRevDisponibilidad);
//                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();