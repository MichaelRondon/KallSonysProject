(function() {
    'use strict';
    angular
        .module('omsApp')
        .factory('Producto', Producto);

    Producto.$inject = ['$resource', 'DateUtils'];

    function Producto ($resource, DateUtils) {
        var resourceUrl =  'api/productos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaRevDisponibilidad = DateUtils.convertDateTimeFromServer(data.fechaRevDisponibilidad);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
