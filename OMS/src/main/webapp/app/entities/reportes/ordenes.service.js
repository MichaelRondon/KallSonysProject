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
            'rankingCerradas': {
                url: 'api/ordenes/rankingOrdenesFechas',
                method: 'GET', 
                isArray: true,
                fechaInicio: null,
                fechaFin: null
            },
            'rankingClientes': {
                url: 'api/ordenes/rankingClientesFechas',
                method: 'GET', 
                isArray: true,
                fechaInicio: null,
                fechaFin: null
            },
            'cerradas': {
                url: 'api/ordenes/cerradas',
                method: 'GET', 
//                isArray: true,
                fecha: null
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